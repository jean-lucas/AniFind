package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.Result;
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
        this.animalList.setPadding(0,20,0,0);


        Bundle b = getIntent().getExtras();


        //this need to be fixed. removed cast
        animals = new ArrayList<Animal>();

        this.animals = (ArrayList<Animal>) b.getSerializable("animals");



//        TextView animal_name = new TextView(this.getApplicationContext());
//        ImageView animal_img = new ImageView(this.getApplicationContext());



        AssetManager am = this.getApplicationContext().getAssets();
        InputStream is = null;
        InputStream is_error = null;

        int rowCounter = 1;
        for (Animal animal: animals) {
            rowCounter++;
            is = null;
            String foldername = "animal_images/"+animal.getName().toLowerCase().replace(" ", "-");
            String filename = animal.getName().toLowerCase().replace(" ", "-");
            String extension = ".jpg";
            String fullpath = "";

            for (int i = 1; i <= 4; i++) {
                fullpath = foldername + "/" + filename + i + extension;
                try {
                    is_error = am.open(DEFAULT_IMAGE);
                    is = am.open(fullpath);
                    break;
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
            animal_img.setScaleType(ImageView.ScaleType.FIT_XY);
            animal_img.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            animal_img.setMinimumWidth(100);
            animal_img.setMinimumHeight(100);

            //make new TextView
            TextView animal_name = new TextView(ResultActivity2.this);
            animal_name.setText(animal.toString());
            animal_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            animal_name.setPadding(0, 5, 15, 5);
            animal_name.setGravity(Gravity.CENTER_VERTICAL);


            //make get hints clickable text
            TextView see_facts = new TextView(ResultActivity2.this);
            see_facts.setText("See facts");
            see_facts.setTypeface(null, Typeface.ITALIC);
            see_facts.setTextColor(Color.BLUE);
            see_facts.setPadding(20, 0, 5, 0);
            see_facts.setGravity(Gravity.CENTER_VERTICAL);
            see_facts.setOnClickListener(getAnimalDetails(animal, fullpath));

            //make new Row
            TableRow animal_row = new TableRow(ResultActivity2.this);
            animal_row.addView(animal_name);
            animal_row.addView(animal_img);
            animal_row.addView(see_facts);
            animal_row.setTag(animal.getName());
            animal_row.setMinimumWidth(400);
            animal_row.setPadding(5, 5, 5, 5);
            animal_row.setOnClickListener(highlightRow(animal_row, animalList));
            animal_row.setGravity(Gravity.CENTER_VERTICAL);

            if (rowCounter%2 == 0) {
                animal_row.setBackgroundColor(Color.parseColor("#f2f2f2"));
            }
            else {
                animal_row.setBackgroundColor(Color.WHITE);
            }
            animalList.addView(animal_row);
//            animalList.addView(animal_img);


        }

    }

    public View.OnClickListener highlightRow(final TableRow tr, final TableLayout list) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //reset all the colors
                for (int i = 0; i < list.getChildCount(); i++) {
                    TableRow row = (TableRow) list.getChildAt(i);
                    if (i%2 == 0) row.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    else  row.setBackgroundColor(Color.WHITE);
                }


                tr.setBackgroundColor(Color.parseColor("#ffff99"));

            }
        };
    }

    public View.OnClickListener getAnimalDetails(final Animal a, final String path) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity2.this, AnimalFactsActivity.class);
                intent.putExtra("animal", a);
                intent.putExtra("img_path",path);
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
