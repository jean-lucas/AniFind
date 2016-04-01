package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.R;

public class QuestionActivity2 extends AppCompatActivity {

    private TextView currentTitle;
    private TextView currentQuestion;
    private LinearLayout selectBoxArea;
    private CheckBox[] currentCheckBoxes;

    private QA qa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentTitle = (TextView) findViewById(R.id.question_title);
        currentQuestion = (TextView) findViewById(R.id.question_text);
        selectBoxArea = (LinearLayout) findViewById(R.id.possible_answers);

//
        Bundle b = getIntent().getExtras();
        this.qa = (QA) b.getSerializable("qa");


        Log.d("QA_DEBUG", qa.getTopic());
        Log.d("QA_DEBUG", qa.getValidAnswers()[0]);

        String[] possibleAnswers = qa.getValidAnswers();


        currentCheckBoxes = new CheckBox[possibleAnswers.length];
//
        setupViewContent(qa.getTopic(), qa.getQuestion(), possibleAnswers);

    }

    public void setupViewContent(String title,String question, String[] possibleAnswerList) {
        this.currentTitle.setText(title);
        this.currentQuestion.setText(question);

        for (int i =0; i < possibleAnswerList.length; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(possibleAnswerList[i]);
            cb.setTextColor(Color.BLACK);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            selectBoxArea.addView(cb);

            currentCheckBoxes[i] = cb;
        }

    }

    public void evaluateAnswer(View view) {

        ArrayList<String> selectedAnswers = new ArrayList<String>();

        for (CheckBox cb: currentCheckBoxes) {

            if (cb.isChecked()) {
                selectedAnswers.add(cb.getText().toString());
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
