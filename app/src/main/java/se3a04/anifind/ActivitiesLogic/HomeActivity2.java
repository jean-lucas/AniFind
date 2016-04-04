package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import se3a04.anifind.R;

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

    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            MediaPlayer player = MediaPlayer.create(HomeActivity2.this, R.raw.nature);
            player.setLooping(true); // Set looping
            player.setVolume(1.0f, 1.0f);
            player.start();

            return null;
        }

    }

    BackgroundSound bBackgroundSound = new BackgroundSound();

    public void onResume() {
        super.onResume();
        bBackgroundSound.execute((Void[]) null);
    }

    public void onPause() {
        super.onPause();
        bBackgroundSound.cancel(true);
    }
}
