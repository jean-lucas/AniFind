package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.R;

public class QuestionActivity2 extends AppCompatActivity {

    private TextView currentTitle;
    private TextView currentQuestion;

    private LinearLayout selectBoxGroup;
    private RadioGroup radiouGroup;

    private CheckBox[] currentCheckBoxes;
    private RadioButton[] currentRadioButtons;

    private QA qa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentTitle = (TextView) findViewById(R.id.question_title);
        currentQuestion = (TextView) findViewById(R.id.question_text);

        selectBoxGroup = (LinearLayout) findViewById(R.id.possible_answers);
        radiouGroup = (RadioGroup) findViewById(R.id.possible_answers_radio);

//
        Bundle b = getIntent().getExtras();
        this.qa = (QA) b.getSerializable("qa");

        String[] possibleAnswers = qa.getValidAnswers();


        this.currentTitle.setText(qa.getTopic());
        this.currentQuestion.setText(qa.getQuestion());

        //check if we want radio buttons or select boxes
        if (qa.getMultiVal()) {
            this.radiouGroup.setVisibility(View.GONE);

            currentCheckBoxes = new CheckBox[possibleAnswers.length];

            setupViewContentSelectBoxes(possibleAnswers);
        }

        else {
            this.selectBoxGroup.setVisibility(View.GONE);

            currentRadioButtons = new RadioButton[possibleAnswers.length];

            setupViewContentRadioButtons(possibleAnswers);
        }







    }

    public void setupViewContentSelectBoxes(String[] possibleAnswerList) {


        for (int i =0; i < possibleAnswerList.length; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(possibleAnswerList[i]);
            cb.setTextColor(Color.BLACK);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            selectBoxGroup.addView(cb);

            currentCheckBoxes[i] = cb;
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


    public void evaluateAnswer(View view) {

        ArrayList<String> selectedAnswers = new ArrayList<String>();

        if (this.qa.getMultiVal()) {
            for (CheckBox cb : currentCheckBoxes) {
                if (cb.isChecked()) {
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

        this.qa.setGivenAnswerByTopic(answers);
        Intent result_intent = new Intent();
        result_intent.putExtra("current_qa", this.qa);

        setResult(1, result_intent);

        finish();
    }


}
