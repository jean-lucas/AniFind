package se3a04.anifind;

import android.content.Intent;
import android.location.Location;
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
import java.util.Collections;
import java.util.HashMap;


import se3a04.anifind.ActivitiesLogic.AudioActivity2;
import se3a04.anifind.ActivitiesLogic.ErrorReportActivity;
import se3a04.anifind.ActivitiesLogic.HomeActivity2;
import se3a04.anifind.ActivitiesLogic.MapsActivity;
import se3a04.anifind.ActivitiesLogic.QuestionActivity2;
import se3a04.anifind.ActivitiesLogic.ResultActivity2;
import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.Misc.CustomComparator;


/*
    TODO: fill up the animal dataset
    TODO: fix question dataset
    TODO: hints for audio need to be better
    TODO: parse valid answers from audio questions
    TODO: create error view
    TODO: get animals with most points for result
    TODO: add proper buttons in result activity
    TODO: Fix size expert
    TODO: create logic for location expert
    TODO: remove a question to be asked, if an expert is commented out
    TODO: if user selects Use current location, or Use current time, make sure the qa_map gets the right value saved!
    TODO: VERY IMPORTANT: Once the animal dataset is finished, get all possible attributes and store them as asnwersToBeParsedByAudio withing the QA object
    TODO: In Audioacitivy for the size question be specific on how large the sizes are, and make the speaker say Very small, Small, Medium, etc..
    TODO: In Audio activy tell the user when only ONE answer is allowed

    TODO: USER MANUAL
 */



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
    private final int MAP_ACTIVITY_REQUEST_CODE = 6;


    //identification type, can have only two options
    // 0 for text based questions
    // 1 for audio based questions
    private int identificationType = -1;

    //keeping track of how many questions have been attempted
    private int question_counter;


    //user location object
    Location loc;



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

        //if an expert is not activated, remove it from the qa_map;
        ArrayList<String> questionsToRemove = new ArrayList<String>();
        for (String topic: qa_map.keySet()) {
            if (blackBoard.getActiveExperts().split(topic).length == 1) {
                questionsToRemove.add(topic);
            }
        }

        for (String topic : questionsToRemove) qa_map.remove(topic);


        this.question_counter = 0;


        //everything loaded at this point

        loading_text.setText("App Ready");
        loading_spinner.setVisibility(View.GONE);
        goHome_btn.setVisibility(View.VISIBLE);


        //now start the activities,
        //if we removed Location expert go straight to home,
        //else go to map activity first
        if (!qa_map.containsKey("Location")) {
            Log.d("CHECK_ONE", "CALLED IF");
            Intent intent = new Intent(GuiController.this, HomeActivity2.class);
            startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
        }

        else {
            Log.d("CHECK_ONE", "CALLED ELSE");
            //get map stuff
            Intent intent = new Intent(GuiController.this, MapsActivity.class);
            startActivityForResult(intent, MAP_ACTIVITY_REQUEST_CODE);
        }
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
                    errorActivityLogic(data);
                    break;

                case MAP_ACTIVITY_REQUEST_CODE:
                    mapActivityLogic(data);
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


            //but now we need to sort the list by points!
            //only get the top 10 for now..
            Collections.sort(this.listOfAnimals, new CustomComparator());
            ArrayList<Animal> bestResults = new ArrayList<> (this.listOfAnimals.subList(0,9));

            Intent intent = new Intent(GuiController.this, ResultActivity2.class);
            intent.putExtra("animals", bestResults);
            startActivityForResult(intent, RESULT_ACTIVITY_REQUEST_CODE);



        }

    }


    //
    private void resultActivityLogic(Intent data) {

        Animal selectedAnimal = (Animal) data.getSerializableExtra("selectedAnimal");
        boolean needErrorForm = data.getBooleanExtra("needErrorForm", false);


        //start the errorActivity
        if (needErrorForm) {
            Intent intent = new Intent(GuiController.this, ErrorReportActivity.class);
            intent.putExtra("animal", selectedAnimal);
            startActivityForResult(intent, ERROR_ACTIVITY_REQUEST_CODE);
        }

        else {
            //do something else with animal here

            //restart the app
            recreate();
        }

    }


    private void errorActivityLogic(Intent data) {


        String errorReportSubmission = data.getStringExtra("errorSubmission");
        Animal animal = (Animal) data.getSerializableExtra("animal");

        //if submission is not blank than email it to the developer(s)
        //followed by restarting the app;
        if (!errorReportSubmission.equalsIgnoreCase("")) {
            //this code is taken from http://stackoverflow.com/questions/2197741
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"developers@anifind.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "AniFind Error Submission - " + animal.getName() );
            i.putExtra(Intent.EXTRA_TEXT   , "Submission message: \n\n" + errorReportSubmission);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(GuiController.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void mapActivityLogic(Intent data) {


        //get the location information retrieved from mapactivity,
        //and save it to the respective qa object
        String[] locations =  data.getStringArrayExtra("loc");

        //qa_map.get("Location").setGivenAnswerByTopic(locations);


        //now start the app
        Intent intent = new Intent(GuiController.this, HomeActivity2.class);
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
    }
    //reset the activity
    public void goHomeButton(View view) {
        recreate();
    }
}
