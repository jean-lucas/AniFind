package se3a04.anifind;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    //Start question button is clicked
    public void startQuestionActivity(View view) {

        int identificationCode = 0;
        Intent result_intent = new Intent();
        result_intent.putExtra("identificationType", (int)identificationCode);

        setResult(1, result_intent);

        finish();

    }

    //Start audio question button is clicked
    public void startAudioActivity(View view) {


        int identificationCode = 1;
        Intent result_intent = new Intent();
        result_intent.putExtra("identificationType", (int)identificationCode);

        setResult(1, result_intent);

        finish();

    }
}
