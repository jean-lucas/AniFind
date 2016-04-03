package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.R;

public class ErrorReportActivity extends AppCompatActivity {


    private Animal animal;

    private TextView animal_name;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.animal_name = (TextView) findViewById(R.id.animalReport_name);
        this.message = (EditText) findViewById(R.id.message_content);

        Bundle b = getIntent().getExtras();
        this.animal = (Animal) b.getSerializable("animal");


        this.animal_name.setText(animal.getName());

    }

    public void submitError(View view) {

        //report message in inputText
        Intent intent = new Intent();
        intent.putExtra("errorSubmission", this.message.getText().toString());
        intent.putExtra("animal", this.animal);
        setResult(1, intent);

        finish();
    }

    public void cancelErrorSubmission(View view) {
        //dont report anything
        Intent intent = new Intent();
        intent.putExtra("errorSubmission", "");
        intent.putExtra("animal", this.animal);
        setResult(1, intent);

        finish();
    }


}
