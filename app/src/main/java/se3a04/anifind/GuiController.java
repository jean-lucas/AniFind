package se3a04.anifind;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;


import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;



public class GuiController extends AppCompatActivity {


    //data required from dataCtrl
//    private DataController dataCtrl;
    private HashMap<String, Animal> listOfAnimals;
    private HashMap<String, QA> listOfQAs;

    //data required from bb
    private BlackBoard blackBoard;


    private ProgressBar loading_spinner;
    private TextView loading_text;


    //request codes from different activities
    private final int HOME_ACTIVITY_REQUEST_CODE = 1;
    private final int QUESTION_ACTIVITY_REQUEST_CODE = 2;
    private final int AUDIO_ACTIVITY_REQUEST_CODE = 3;
    private final int RESULT_ACTIVITY_REQUEST_CODE = 4;
    private final int ERROR_ACTIVITY_REQUEST_CODE = 5;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_controller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //create loading animation
        loading_text = (TextView) findViewById(R.id.loading_text);
        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loading_spinner.setVisibility(View.VISIBLE);

        //load all the required instatiations here..
//        dataCtrl = new DataController();
        blackBoard = new BlackBoard();

//        listOfAnimals = dataCtrl.getAllAnimals();
//        listOfQAs = dataCtrl.getAllQA();





    }


    @Override
    protected void onStart() {
        super.onStart();
        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loading_spinner.setVisibility(View.GONE);
        loading_text = (TextView) findViewById(R.id.loading_text);
        loading_text.setText("App Ready");

        //go to HomeActivity

        Intent intent = new Intent();
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case HOME_ACTIVITY_REQUEST_CODE:
                //do something
                break;

            case QUESTION_ACTIVITY_REQUEST_CODE:
                //do something
                break;

            case AUDIO_ACTIVITY_REQUEST_CODE:
                //do something
                break;

            case RESULT_ACTIVITY_REQUEST_CODE:
                //do something
                break;

            case ERROR_ACTIVITY_REQUEST_CODE:
                //do something
                break;

            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //at this point, all questions should be finished
        //so consult blackboard
        //and get best results

        //follwed by going to resultsActivity
    }

}
