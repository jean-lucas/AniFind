package se3a04.anifind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;



public class GuiController extends AppCompatActivity {


    //data required from dataCtrl
    private DataController dataCtrl;
    private HashMap<String, Animal> animal_map;
    private HashMap<String, QA> qa_map;

    private ArrayList<Animal> listOfAnimals;

    //data required from bb
    private BlackBoard blackBoard;





    //request codes from different activities
    private final int HOME_ACTIVITY_REQUEST_CODE = 1;
    private final int QUESTION_ACTIVITY_REQUEST_CODE = 2;
    private final int AUDIO_ACTIVITY_REQUEST_CODE = 3;
    private final int RESULT_ACTIVITY_REQUEST_CODE = 4;
    private final int ERROR_ACTIVITY_REQUEST_CODE = 5;


    //identification type, can have only two options
    // 0 for text based questions
    // 1 for audio based questions
    private int identificationType = -1;

    //keeping track of how many questions have been attempted
    private int question_counter;



    //views for the starting activity
    private ProgressBar loading_spinner;
    private TextView loading_text;
    private Button goHome_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_controller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //create loading animation
        loading_text = (TextView) findViewById(R.id.loading_text);
        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        goHome_btn = (Button) findViewById(R.id.goHome_btn);

        loading_spinner.setVisibility(View.VISIBLE);
        goHome_btn.setVisibility(View.GONE);


        //load all the required instatiations here..
        dataCtrl = new DataController(this.getApplicationContext());
        blackBoard = new BlackBoard();

        animal_map = dataCtrl.getAnimals();
        qa_map = dataCtrl.getQuestions();
        listOfAnimals = new ArrayList<Animal>();

        //fill our list of animals
        for (String name: animal_map.keySet()) {
            listOfAnimals.add(animal_map.get(name));
        }



        this.question_counter = 0;


        //everything loaded at this point

        loading_text.setText("App Ready");
        loading_spinner.setVisibility(View.GONE);
        goHome_btn.setVisibility(View.VISIBLE);


        Intent intent = new Intent(GuiController.this, HomeActivity2.class);
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            switch (requestCode) {

                case HOME_ACTIVITY_REQUEST_CODE:
                    homeActivityLogic(data);
                    break;

                case QUESTION_ACTIVITY_REQUEST_CODE:
                    questionActivityLogic(data);
                    break;

                case AUDIO_ACTIVITY_REQUEST_CODE:
                    //do something
                    break;

                case RESULT_ACTIVITY_REQUEST_CODE:
                    resultActivityLogic(data);
                    break;

                case ERROR_ACTIVITY_REQUEST_CODE:
                    //do something
                    break;

                default:
                    break;
            }
        }
    }





    private void homeActivityLogic(Intent data) {

        int identificationType = data.getIntExtra("identificationType", -1);



        //go to text questions
        if (identificationType == 0) {
            for (String qa_topic: qa_map.keySet()) {
                Intent intent = new Intent(GuiController.this, QuestionActivity2.class);
                intent.putExtra("qa", qa_map.get(qa_topic));
                startActivityForResult(intent, QUESTION_ACTIVITY_REQUEST_CODE);
            }
        }

        //go to audio questions
        else {
            for (String qa_topic: qa_map.keySet()) {
                Intent intent = new Intent(GuiController.this, AudioActivity2.class);
                intent.putExtra("qa", qa_map.get(qa_topic));
                startActivityForResult(intent, AUDIO_ACTIVITY_REQUEST_CODE);
            }
        }
    }


    //once all questions are finished, this function is called
    //it will be called one question at a time, but sequentially.
    private void questionActivityLogic(Intent data) {

        QA temp_qa = (QA) data.getSerializableExtra("current_qa");

        String temp_topic = temp_qa.getTopic();
        String[] temp_answers = temp_qa.getAnswersGivenByUsers();

        //update the answers from users with the qa_map in this class
        this.qa_map.get(temp_topic).setGivenAnswerByTopic(temp_answers);


        question_counter++;

        //IDEALLY FROM HERE WE WOULD WANT TO CALL THE BLACKBOARD WITH OUR ANSWERS
        //AND GO TO A LOADING SCREEN FOLLOWED BY THE RESULTS ACTIVITY
        // THIS IS TEMPORARY -- JUST TO SEE IF ITS WORKING
        if (question_counter == qa_map.size()) {
            question_counter = 0;

            //now ask the expert
            //from there we get an update animal dataset
            //so we can put that in the extra for the result intent

            this.listOfAnimals = blackBoard.consultAllExperts(listOfAnimals, qa_map);

            //send an arraylist of the animals
//            Animal[] temp_animals = {animal_map.get("gorilla"), animal_map.get("kangoroo"), animal_map.get("goose")};

            Intent intent = new Intent(GuiController.this, ResultActivity2.class);
            intent.putExtra("animals", this.listOfAnimals);
            startActivityForResult(intent, RESULT_ACTIVITY_REQUEST_CODE);



        }

    }

    private void resultActivityLogic(Intent data) {
        Toast.makeText(GuiController.this, "Welcome to results", Toast.LENGTH_SHORT).show();
    }

    //reset the activity
    public void goHomeButton(View view) {
        recreate();
    }
}
