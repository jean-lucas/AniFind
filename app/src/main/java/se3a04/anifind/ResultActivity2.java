package se3a04.anifind;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import se3a04.anifind.DataEntities.Animal;

public class ResultActivity2 extends AppCompatActivity {

    private Animal[] animals;
    private TextView animalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.animalList = (TextView) findViewById(R.id.animalResultList);


        Bundle b = getIntent().getExtras();


        //this need to be fixed. removed cast
        this.animals = (Animal[]) b.getSerializable("animals");



        for (Animal a: animals) {
            animalList.append(a.getName() + "\n");
        }

    }

    public void goHomeButton(View view) {
        finish();
    }
}
