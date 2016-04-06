package se3a04.anifind.ActivitiesLogic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.R;

public class AudioActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private Button startRecordingBtn;
    private Button nextButton;
    private Button resetButton;

    private TextView title;
    private TextView question;

    //the recognized values after parsing
    private TextView finalRecognizedSpeechView; //this one is to be displayed
    private HashSet<String> finalRecognizedSpeechValues;//this one is to be sent back to guiCtrl

    private LinearLayout questionHintsLayout;
    private ArrayList<String> recordedValues;

    private String[] validAnswers;
    private QA qa;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Bundle b = getIntent().getExtras();
        this.qa = (QA) b.getSerializable("qa");

        recordedValues = new ArrayList<String>();

        String newTitle = qa.getTopic();
        String newQuestion = qa.getQuestion();
        this.validAnswers = qa.getValidAnswers();
        String[] answerHints = qa.getAnswerHints();





        startRecordingBtn = (Button)findViewById(R.id.clickToRecordBtn);
        nextButton = (Button) findViewById(R.id.changeAttribute);
        resetButton = (Button) findViewById(R.id.resetButton);

        title = (TextView) findViewById(R.id.attributeTitle);
        question = (TextView) findViewById(R.id.attributeQuestion);
//        hintExamples = (TextView) findViewById(R.id.exampleText);

        questionHintsLayout = (LinearLayout) findViewById(R.id.question_hints);



        setupViewContent(newTitle, newQuestion, answerHints);


        finalRecognizedSpeechView = (TextView)findViewById(R.id.parsedSpeechValues);
        finalRecognizedSpeechValues = new HashSet<String>();


        //intiate recording
        startRecordingBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });



    }

    public void setupViewContent(String title, String question, String[] hints ) {

        //set up title
        this.title.setText(title);

        //set up question
        this.question.setText(question);

        //set up hints
        for (int i = 0; i < hints.length; i++ ) {
            TextView hint = new TextView(AudioActivity.this);
            hint.setGravity(View.TEXT_ALIGNMENT_CENTER);
            hint.setText(hints[i]);

            questionHintsLayout.addView(hint);
        }

        //add text notifying users if only one value allowed

        if (!this.qa.getMultiVal()) {
            TextView warning = new TextView(AudioActivity.this);

            warning.setText("ONLY ONE VALUE ALLOWED");
            warning.setTextColor(Color.RED);
            warning.setTextSize(15);
            warning.setPadding(0, 10, 0, 0);
            questionHintsLayout.addView(warning);
        }
    }




    private  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {


            recordedValues = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            HashSet<String> validMatches = new HashSet<>();

            //get valid options based on the QA type...
            validMatches = parseAudioValues(qa.getTopic());


            //put the valid matches into our current set of finalValues
            finalRecognizedSpeechValues.addAll(validMatches);

            //for certain topics we need to only allow one value
            if (!qa.getMultiVal()) {
                if (finalRecognizedSpeechValues.size() > 1) {
                    Toast.makeText(AudioActivity.this, "Only one value allowed!", Toast.LENGTH_SHORT).show();
                    resetCurrentValues(finalRecognizedSpeechView);
                }
            }

            //finally add the values to the view, only if they are new values
            finalRecognizedSpeechView.setText("");
            for (String s: finalRecognizedSpeechValues) {
                finalRecognizedSpeechView.append(s + "\n");
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    //based on the current question, it knows what logic is need to parse audio values
    private HashSet<String> parseAudioValues(String topic) {
        HashSet<String> tempMatches = new HashSet<>();

        HashSet<String> testVal = new HashSet<>(Arrays.asList(validAnswers));

        switch(topic) {

            case "Colour":
            case "Location":
            case "Habitat":
            case "Mobility":
                for (String s : getValidWords()) tempMatches.add(s);
                break;

            case "Size":
                for (String s : getValidWordsWithReplace(":", "")) tempMatches.add(s);
                break;

        }
        return tempMatches;
    }



    //goes through the list of recorded values, and the lsit of valid values (taken from the qa object)
    //and decides whether a recorded word should be added as an answer
    private ArrayList<String> getValidWords(){

        ArrayList<String> goodMatches = new ArrayList<String>();

        for (String s: recordedValues) {
            String[] nWord_values = s.split(","); //for when an answer is more than one word
            for (String v0 : nWord_values) {
                for (String v1 : validAnswers) {

                    v0 = v0.toLowerCase(); //v0 corresponds to the recorded values
                    v1 = v1.toLowerCase(); //v1 corresponds to a value from qa object

                    if ( v0.contains(v1)) {
                        goodMatches.add(v1);
                    }
                }
            }
        }

        return goodMatches;
    }


    //sometimes the qa object's set of valid answer has a funky format,
    //for which makes it difficults to recognize answers properly.
    //this function allows us to slightly customize our valid answers
    private ArrayList<String> getValidWordsWithReplace(String oldVal, String newVal) {

        ArrayList<String> goodMatches = new ArrayList<String>();

        for (String s: recordedValues) {
            String[] nWord_values = s.split(","); //for when an answer is more than one word
            for (String v0 : nWord_values) {
                for (String v1 : validAnswers) {
                    //if we require to clean up the strings being compared, do it on this string
                    String cleanText  = v1.replace(oldVal, newVal).toLowerCase();

                    Log.d("Comparing", cleanText + " cointans ? " + v0);
                    if (cleanText.contains(v0)) {
                        goodMatches.add(v0);
                    }
                }
            }
        }
        return  goodMatches;
    }


    //when the 'Next' button is clicked
    public void evaluateAudioAnswer(View view) {
        String[] answers = finalRecognizedSpeechValues.toArray(new String[finalRecognizedSpeechValues.size()]);

        this.qa.setGivenAnswerByTopic(answers);

        Intent result_intent = new Intent();
        result_intent.putExtra("current_qa", this.qa);
        setResult(1, result_intent);

        finish();
    }


    //reset all the values that were recorded for that current question
    public void resetCurrentValues(View view) {
        this.finalRecognizedSpeechView.setText("");
        this.finalRecognizedSpeechValues.clear();
    }
}
