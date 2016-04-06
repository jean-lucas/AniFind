package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.R;

public class QuestionActivity extends AppCompatActivity {



    //basic elements of the screen
    private TextView currentTitle;
    private TextView currentQuestion;

    private MultiAutoCompleteTextView autocomplete_location;

    //contains all the checkboxes for a multivalued question
    private LinearLayout selectBoxGroup;
//    private CheckBox[] currentCheckBoxes;
    private Set<CheckBox> checkBoxSet;

    //contains all the radiou buttons for a singlevalued question
    private RadioGroup radiouGroup;
    private RadioButton[] currentRadioButtons;

    //for when user wants to use the current time or current location
    private CheckBox useCurrentOption;
    private TextView useCurrentOptionSeperator;



    private QA qa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //setup all the views;
        currentTitle = (TextView) findViewById(R.id.question_title);
        currentQuestion = (TextView) findViewById(R.id.question_text);
        useCurrentOptionSeperator = (TextView) findViewById(R.id.useCurrentOptionSeperator);

        autocomplete_location = (MultiAutoCompleteTextView) findViewById(R.id.autocomplete_location);

        useCurrentOption = (CheckBox) findViewById(R.id.useCurrentOption);

        selectBoxGroup = (LinearLayout) findViewById(R.id.possible_answers);
        radiouGroup = (RadioGroup) findViewById(R.id.possible_answers_radio);

        //get required content
        Bundle b = getIntent().getExtras();
        this.qa = (QA) b.getSerializable("qa");



        String[] possibleAnswers = qa.getValidAnswers();


        this.currentTitle.setText(qa.getTopic());
        this.currentQuestion.setText(qa.getQuestion());


        //if the question allows for the user to use current location, than
        //set up the activity accordingling
        if (this.qa.getTopic().equalsIgnoreCase("Location")) {
            autocomplete_location.setVisibility(View.VISIBLE);
            radiouGroup.setVisibility(View.GONE);

//            currentCheckBoxes = new CheckBox[possibleAnswers.length];
            checkBoxSet = new HashSet<CheckBox>();
            setupViewContentLocation(possibleAnswers);
        }

        //check if we want radio buttons or select boxes, and remove
        //unecessary views;
        else if (qa.getMultiVal()) {
            this.radiouGroup.setVisibility(View.GONE);
            this.autocomplete_location.setVisibility(View.GONE);
            useCurrentOption.setVisibility(View.GONE);
            useCurrentOptionSeperator.setVisibility(View.GONE);

//            currentCheckBoxes = new CheckBox[possibleAnswers.length];
            checkBoxSet = new HashSet<CheckBox>();
            setupViewContentSelectBoxes(possibleAnswers);
        }

        else {
            this.selectBoxGroup.setVisibility(View.GONE);
            this.autocomplete_location.setVisibility(View.GONE);
            useCurrentOption.setVisibility(View.GONE);
            useCurrentOptionSeperator.setVisibility(View.GONE);

            currentRadioButtons = new RadioButton[possibleAnswers.length];
            setupViewContentRadioButtons(possibleAnswers);
        }







    }

    private void setupViewContentLocation(String[] possibleAnswerList) {

        useCurrentOption.setText("Use Current Location");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,possibleAnswerList);

        autocomplete_location.setAdapter(adapter);
        autocomplete_location.setThreshold(2);
        autocomplete_location.setHint("Countries, Continents, and Oceans");
        autocomplete_location.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autocomplete_location.requestFocus();

        autocomplete_location.setOnItemClickListener(toggleItems());

    }



    private void setupViewContentSelectBoxes(String[] possibleAnswerList) {

        for (int i =0; i < possibleAnswerList.length; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(possibleAnswerList[i]);
            cb.setTextColor(Color.BLACK);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            selectBoxGroup.addView(cb);

//            currentCheckBoxes[i] = cb;
            checkBoxSet.add(cb);
        }
    }

    public void setupViewContentRadioButtons(String[] possibleAnswerList) {

        for (int i = 0; i < possibleAnswerList.length; i++) {
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(possibleAnswerList[i]);
            rb.setTextColor(Color.BLACK);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            radiouGroup.addView(rb);

            currentRadioButtons[i] = rb;
        }

    }


    //add items as new checkboxes once they are clicked in the autocomeplete view
    public AdapterView.OnItemClickListener toggleItems() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                boolean newItem = true;

//                for (CheckBox cb: currentCheckBoxes) {
                for (CheckBox cb: checkBoxSet) {
                    if (cb == null)  break;

                    else if (cb.getText().toString().equalsIgnoreCase(item)) {
                        newItem = false;
                        break;
                    }
                }

                if (newItem) {
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setText(item);
                    cb.setTextColor(Color.BLACK);
                    cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    cb.setChecked(true);
                    selectBoxGroup.addView(cb);

                    checkBoxSet.add(cb);
//                    currentCheckBoxes[currentCheckBoxes.length - 1] = cb;
                }
            }
        };
    }

    //if we want to use current location/time than remove the items from the autocomplete
    public void toggleAutoComplete(View view) {

        if (selectBoxGroup.getVisibility() == View.VISIBLE) {
            selectBoxGroup.setVisibility(View.INVISIBLE);
            autocomplete_location.setVisibility(View.INVISIBLE);
        }
        else {
            selectBoxGroup.setVisibility(View.VISIBLE);
            autocomplete_location.setVisibility(View.VISIBLE);
        }
    }

    //user clicked the next button, so get all needed values
    public void evaluateAnswer(View view) {

        ArrayList<String> selectedAnswers = new ArrayList<String>();

        if (this.qa.getMultiVal()) {
            for (CheckBox cb: checkBoxSet) {
                if (cb == null) continue;
                else if (cb.isChecked()) {
                    selectedAnswers.add(cb.getText().toString());
                }
            }
        }

        else {
            for (RadioButton rb : currentRadioButtons) {
                if (rb.isChecked()) {
                    selectedAnswers.add(rb.getText().toString());
                }
            }
        }

        String[] answers = selectedAnswers.toArray(new String[selectedAnswers.size()]);




        //check if user wanted the single option, or if question corrsponds to size;
        if (useCurrentOption.isChecked()) {
            this.qa.setGivenAnswerByTopic(new String[]{"current"});
        }
        else if (this.qa.getTopic().equalsIgnoreCase("Size")) {
            this.qa.setGivenAnswerByTopic(evaluateAnswerForSize());
        }
        else {
            this.qa.setGivenAnswerByTopic(answers);
        }

        Intent result_intent = new Intent();
        result_intent.putExtra("current_qa", this.qa);

        setResult(1, result_intent);

        finish();
    }


    private String[] evaluateAnswerForSize() {

        String[] values = {"-1.0", "-1.0"};
        String selectedAnswer = "";
        for (RadioButton rb : currentRadioButtons) {
            if (rb.isChecked()) {
                selectedAnswer = rb.getText().toString();
            }
        }

        switch (selectedAnswer.split(":")[0]) {

            case "Very Small":
                values = new String[] {"0", "0.1"};
                break;

            case "Small":
                values = new String[] {"0.11", "0.5"};
                break;

            case "Medium":
                values = new String[] {"0.51", "2.0"};
                break;

            case "Large":
                values = new String[] {"2.1", "5"};
                break;

            case "Very Large":
                values = new String[] {"5.1", "500"};
                break;
        }

        return values;
    }
}
