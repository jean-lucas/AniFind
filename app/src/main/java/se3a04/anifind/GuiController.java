package se3a04.anifind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class GuiController extends AppCompatActivity {






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




        //load all the required instatiations here..
        //put loading screen
        //when data is ready go to onstart

    }


    @Override
    protected void onStart() {
        super.onStart();

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
