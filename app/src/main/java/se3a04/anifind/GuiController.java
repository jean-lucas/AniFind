package se3a04.anifind;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.Experts.ColourExpert;
import se3a04.anifind.Experts.Expert;
import se3a04.anifind.Experts.HabitatExpert;
import se3a04.anifind.Experts.LocationExpert;
import se3a04.anifind.Experts.MobilityExpert;
import se3a04.anifind.Experts.SizeExpert;
import se3a04.anifind.Experts.TimeExpert;


public class GuiController extends AppCompatActivity {


    //data required from dataCtrl
//    private DataController dataCtrl;
    private HashMap<String, Animal> listOfAnimals;
    private HashMap<String, QA> listOfQAs;

    //data required from bb
    private BlackBoard blackBoard;


    private ProgressBar loading_spinner;
    private TextView loading_text;





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
        loading_text = (TextView) findViewById(R.id.loading_text);
        loading_text.setText("App Ready");

        //go to HomeActivity
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
