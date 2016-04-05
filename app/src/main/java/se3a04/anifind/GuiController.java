package se3a04.anifind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import se3a04.anifind.ActivitiesLogic.AudioActivity;
import se3a04.anifind.ActivitiesLogic.ErrorReportActivity;
import se3a04.anifind.ActivitiesLogic.HomeActivity;
import se3a04.anifind.ActivitiesLogic.MapsActivity;
import se3a04.anifind.ActivitiesLogic.QuestionActivity;
import se3a04.anifind.ActivitiesLogic.ResultActivity;
import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.Misc.CustomComparator;


/*
    TODO: fill up the animal dataset
    TODO: fix question dataset
    TODO: hints for audio need to be better
    TODO: parse valid answers from audio questions
    TODO: Fix size expert
    TODO: create logic for location expert
    TODO: if user selects Use current location, or Use current time, make sure the qa_map gets the right value saved!
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


    //userLocation will be provided through the mapActivity
    //contains: {"current",countryCode,countryName, cityName}
    private String[] userLocation = new String[4];

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



    //views for the starting activity
//    private ProgressBar loading_spinner;
//    private TextView loading_text;
    private Button goHome_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_controller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //create loading animation
//        loading_text = (TextView) findViewById(R.id.loading_text);
//        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        goHome_btn = (Button) findViewById(R.id.goHome_btn);

//        loading_spinner.setVisibility(View.VISIBLE);
        goHome_btn.setVisibility(View.GONE);


        //load all the required instatiations here..
        dataCtrl = new DataController(this.getApplicationContext());
        animal_map = dataCtrl.getAnimals();
        qa_map = dataCtrl.getQuestions();
        blackBoard = new BlackBoard();
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

//        loading_text.setText("App Ready");
//        loading_spinner.setVisibility(View.GONE);
        goHome_btn.setVisibility(View.VISIBLE);


        //now start the activities,
        //if we removed Location expert go straight to home,
        //else go to map activity first
        if (!qa_map.containsKey("Location")) {
            Intent intent = new Intent(GuiController.this, HomeActivity.class);
            startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
        }

        else {
            //get map stuff

            Intent intent = new Intent(GuiController.this, MapsActivity.class);
            startActivityForResult(intent, MAP_ACTIVITY_REQUEST_CODE);
        }
    }



    //Once an acitivy has finished, it will alert the GuiController with a result code and a requestCode
    //those values are then used to determine what logic is to be proceeded
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





    //results given in home activity
    private void homeActivityLogic(Intent data) {

        int identificationType = data.getIntExtra("identificationType", -1);


        //go to text questions
        if (identificationType == 0) {
            for (String qa_topic: qa_map.keySet()) {
                Intent intent = new Intent(GuiController.this, QuestionActivity.class);
                intent.putExtra("qa", qa_map.get(qa_topic));
                startActivityForResult(intent, QUESTION_ACTIVITY_REQUEST_CODE);
            }
        }

        //go to audio questions
        else {
            for (String qa_topic: qa_map.keySet()) {
                Intent intent = new Intent(GuiController.this, AudioActivity.class);
                intent.putExtra("qa", qa_map.get(qa_topic));
                startActivityForResult(intent, AUDIO_ACTIVITY_REQUEST_CODE);
            }
        }
    }


    //once all questions are finished, this function is called
    //it will be called one question at a time.
    private void questionActivityLogic(Intent data) {

        QA temp_qa = (QA) data.getSerializableExtra("current_qa");

        String temp_topic = temp_qa.getTopic();

        String[] temp_answers = temp_qa.getAnswersGivenByUsers();


        if (temp_answers.length > 0) {

            if (temp_topic.equalsIgnoreCase("Location") && temp_answers[0].equalsIgnoreCase("current")) {
                this.qa_map.get("Location").setGivenAnswerByTopic(userLocation);
            }
            else if (temp_topic.equalsIgnoreCase("Time") && temp_answers[0].equalsIgnoreCase("Use Current Time")) {
                this.qa_map.get("Time").setGivenAnswerByTopic(new String[]{"current"});
            }
            else {
                //update the answers from users with the qa_map in this class
                this.qa_map.get(temp_topic).setGivenAnswerByTopic(temp_answers);
            }
        }

        else {
            this.qa_map.get(temp_topic).setGivenAnswerByTopic(new String[] {""});
        }



        Log.d("QQ", "qq " +  this.qa_map.get(temp_topic).getAnswersGivenByUsers()[0]);




        question_counter++;

                //this is true if user has gone through all questions
        if (question_counter == qa_map.size()) {
            question_counter = 0;


            //now we can consult all the experts
            this.listOfAnimals = blackBoard.consultAllExperts(listOfAnimals, qa_map);


            //sort the list by points
            Collections.sort(this.listOfAnimals, new CustomComparator());
            ArrayList<Animal> bestResults = new ArrayList<> (this.listOfAnimals);

            Intent intent = new Intent(GuiController.this, ResultActivity.class);
            intent.putExtra("animals", bestResults);
            startActivityForResult(intent, RESULT_ACTIVITY_REQUEST_CODE);
        }
    }


    //get the selected animal from results or go to error report page
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

            //if we had something to store results it would go in here

            //restart the app
            recreate();
        }

    }


    //logic for sending an email to developers if user has encountered an issue
    //with the dataset
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

    //TODO FIX ME!
    //once user location is retrieved we can put this in the qa_map answer for Locaton
    private void mapActivityLogic(Intent data) {


        //get the location information retrieved from mapactivity,
        //and save it to the respective qa object
        this.userLocation =  data.getStringArrayExtra("loc");

//        qa_map.get("Location").setGivenAnswerByTopic(locations);


        //now start the app
        Intent intent = new Intent(GuiController.this, HomeActivity.class);
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
    }



    //home button listener : reset the activity
    public void goHomeButton(View view) {
        recreate();
    }
}
