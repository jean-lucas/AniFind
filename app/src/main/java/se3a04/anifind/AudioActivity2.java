package se3a04.anifind;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import se3a04.anifind.DataEntities.QA;

public class AudioActivity2 extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private Button startRecordingBtn;
    private Button nextButton;
    private Button resetButton;

    private TextView title;
    private TextView question;
    private TextView hintExamples;
    private TextView finalRecognizedSpeech;

    private ArrayList<String> possibleMatches;

    private String[] validAnswers;
    private QA qa;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio2);

        Bundle b = getIntent().getExtras();
        this.qa = (QA) b.getSerializable("qa");

        possibleMatches  = new ArrayList<String>();

        String newTitle = qa.getTopic();
        String newQuestion = qa.getQuestion();
        this.validAnswers = qa.getValidAnswers();
        String[] answerHints = qa.getAnswerHints();





        startRecordingBtn = (Button)findViewById(R.id.clickToRecordBtn);
        nextButton = (Button) findViewById(R.id.changeAttribute);
        resetButton = (Button) findViewById(R.id.resetButton);

        title = (TextView) findViewById(R.id.attributeTitle);
        question = (TextView) findViewById(R.id.attributeQuestion);
        hintExamples = (TextView) findViewById(R.id.exampleText);



        setupViewContent(newTitle, newQuestion, answerHints);

        //hide button until valeus are generated
//        nextButton.setVisibility(View.GONE);

        finalRecognizedSpeech = (TextView)findViewById(R.id.parsedSpeechValues);


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

        this.title.setText(title);

        this.question.setText(question);

        //reset hints
        this.hintExamples.setText("Example: ");

        //add hints
        int counter = 0; //for formatting the commas
        for (String hint: hints) {
            counter++;
            if (counter == hints.length) {
                this.hintExamples.append(hint);
            }
            else {
                this.hintExamples.append(hint + ", ");
            }
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


            possibleMatches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            HashSet<String> validMatches = new HashSet<>();

            //get valid options based ont the QA type...

            Log.d("Audio_values", possibleMatches.toString());
            //filter for valid colors
            for (String s: possibleMatches) {
                String[] values = s.split(" ");

                for (String v: values) {
                    if (contains(v, validAnswers)) {
                        validMatches.add(v);
                    }
                }

            }


            for (String s: validMatches) {
                finalRecognizedSpeech.append(s + "\n");
            }

            nextButton.setVisibility(View.VISIBLE);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }







    private boolean contains(String target, String[] values) {

        for (String s: values) {
            if (s.equalsIgnoreCase(target)) {
                return true;
            }
        }

        return false;
    }

    public void evaluateAudioAnswer(View view) {

        finish();
    }

    public void resetCurrentValues(View view) {
        finalRecognizedSpeech.setText("");
    }
}
