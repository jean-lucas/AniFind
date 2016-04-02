package se3a04.anifind.ActivitiesLogic;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import se3a04.anifind.AnimalFactsActivity;
import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.R;

public class ResultActivity2 extends AppCompatActivity {

    private ArrayList<Animal> animals;
    private TableLayout animalList;

    private final String DEFAULT_IMAGE = "animal_images/file_not_found.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.animalList = (TableLayout) findViewById(R.id.animalResultList);


        Bundle b = getIntent().getExtras();


        //this need to be fixed. removed cast
        animals = new ArrayList<Animal>();

        this.animals = (ArrayList<Animal>) b.getSerializable("animals");



//        TextView animal_name = new TextView(this.getApplicationContext());
//        ImageView animal_img = new ImageView(this.getApplicationContext());



        AssetManager am = this.getApplicationContext().getAssets();
        InputStream is = null;
        InputStream is_error = null;

        int rowCounter = 0;
        for (Animal a: animals) {
            rowCounter++;
            is = null;
            String foldername = "animal_images/"+a.getName().toLowerCase().replace(" ", "-");
            String filename = a.getName().toLowerCase().replace(" ", "-");
            String extension = ".jpg";

            for (int i = 1; i <= 4; i++) {
                try {
                    is_error = am.open(DEFAULT_IMAGE);

                    is = am.open(foldername + "/" + filename + i + extension);
                } catch (IOException e) {

                } finally {
                    if (is == null) {
                        is = is_error;
                    }
                }
            }

            //make new ImageView
            ImageView animal_img = new ImageView(ResultActivity2.this);
            animal_img.setImageDrawable(Drawable.createFromStream(is, null));
            animal_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            animal_img.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            animal_img.setMinimumWidth(40);
            animal_img.setMinimumHeight(40);

            //make new TextView
            TextView animal_name  =new TextView(ResultActivity2.this);
            animal_name.setText(a.toString());
            animal_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            animal_name.setPadding(0,5,15,5);

            //make new Row
            TableRow animal_row = new TableRow(ResultActivity2.this);
            animal_row.addView(animal_name);
            animal_row.addView(animal_img);
            animal_row.setTag(a.getName());
            animal_row.setMinimumWidth(400);
            animal_row.setPadding(5, 5, 5, 5);
            animal_row.setOnClickListener(getAnimalDetails());

            if (rowCounter%2 == 0) {
                animal_row.setBackgroundColor(Color.parseColor("#d9d9d9"));
            }
            else {
                animal_row.setBackgroundColor(Color.parseColor("#a6a6a6"));
            }
            animalList.addView(animal_row);
//            animalList.addView(animal_img);


        }

    }

    private View.OnClickListener getAnimalDetails() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity2.this, AnimalFactsActivity.class);
                startActivity(intent);
            }
        };
    }

    public void goHomeButton(View view) {
        Intent result_intent = new Intent();
        setResult(1, result_intent);
        finish();
    }
}
