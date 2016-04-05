package se3a04.anifind.ActivitiesLogic;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.R;

public class ResultActivity2 extends AppCompatActivity {

    private ArrayList<Animal> animals;
    private TableLayout animalTable;
    private Button doneBtn;
    private Button errorBtn;
    private Button loadMoreBtn;

    private int initialResultSize = 5;
    private int maxResultSize = 20;

    private final int HIGHLIGHT_COLOR = Color.parseColor("#ffff99");

    private final String DEFAULT_IMAGE = "animal_images/file_not_found.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.doneBtn = (Button) findViewById(R.id.doneBtn);
        this.errorBtn = (Button) findViewById(R.id.errorBtn);
        this.loadMoreBtn = (Button) findViewById(R.id.loadMoreBtn);

        this.doneBtn.setVisibility(View.INVISIBLE);
        this.errorBtn.setVisibility(View.INVISIBLE);

        this.animalTable = (TableLayout) findViewById(R.id.animalResultList);
        this.animalTable.setPadding(0, 20, 0, 0);


        Bundle b = getIntent().getExtras();


        this.animals = (ArrayList<Animal>) b.getSerializable("animals");


        setupResults(5);

    }

    private void setupResults(int resultSize) {

        AssetManager am = this.getApplicationContext().getAssets();
        InputStream is = null;
        InputStream is_error = null;


        //make sure we dont get an overflow on the list
        if (resultSize > maxResultSize - 1) {
            resultSize =  maxResultSize;
            this.loadMoreBtn.setVisibility(View.GONE);
        }

        for (int i = resultSize - 5; i < resultSize; i++) {
            Animal animal = this.animals.get(i);
            is = null;
            String foldername = "animal_images/"+animal.getName().toLowerCase().replace(" ", "-");
            String filename = animal.getName().toLowerCase().replace(" ", "-");
            String extension = ".jpg";
            String fullpath = "";

            for (int j = 1; j <= 4; j++) {
                fullpath = foldername + "/" + filename + j + extension;
                try {
                    is_error = am.open(DEFAULT_IMAGE);
                    is = am.open(fullpath);
                    break;
                } catch (IOException e) {
//                    Toast.makeText(ResultActivity2.this, "error loading some images", Toast.LENGTH_SHORT).show();
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
            animal_row.setOnClickListener(highlightRow(animal_row, animalTable));
            animal_row.setGravity(Gravity.CENTER_VERTICAL);

            if (i%2 == 0) {
                animal_row.setBackgroundColor(Color.parseColor("#f2f2f2"));
            }
            else {
                animal_row.setBackgroundColor(Color.WHITE);
            }
            animalTable.addView(animal_row);
//            animalTable.addView(animal_img);


        }

    }

    //only once a user highlights an animal will the Done/Error buttons show up
    public View.OnClickListener highlightRow(final TableRow tr, final TableLayout list) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //make buttons visible
                doneBtn.setVisibility(View.VISIBLE);
                errorBtn.setVisibility(View.VISIBLE);

                //reset all the colors
                for (int i = 0; i < list.getChildCount(); i++) {
                    TableRow row = (TableRow) list.getChildAt(i);
                    if (i%2 == 0) row.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    else  row.setBackgroundColor(Color.WHITE);
                }


                tr.setBackgroundColor(HIGHLIGHT_COLOR);

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



    //User highlited an animal and pressed DONE
    public void finishIdentification(View view) {

        //get the animal corresponding to the highlighted one, and send it back to guicontroller
        Intent intent = new Intent();
        intent.putExtra("selectedAnimal", getHighlightedAnimal());
        intent.putExtra("needErrorForm", false);
        setResult(1, intent);
        finish();


    }


    // go back to guiController, and let the errorAcvtitivy be opened from there
    public void openErrorForm(View view) {
        Intent intent = new Intent();
        intent.putExtra("selectedAnimal", getHighlightedAnimal());
        intent.putExtra("needErrorForm", true);
        setResult(1, intent);
        finish();
    }




    //find which ANimal is highlighted in yellow
    private Animal getHighlightedAnimal() {
        Animal animal = null;
        String animal_name = "";
        //get the highlighted animal
        for (int i = 0 ; i < animalTable.getChildCount(); i++) {
            //check which one is yellow
            if ( ((ColorDrawable) animalTable.getChildAt(i).getBackground()).getColor() == HIGHLIGHT_COLOR)  {
                animal_name = animalTable.getChildAt(i).getTag().toString();
            }
        }

        for (Animal a: animals) {
            if (a.getName().equalsIgnoreCase(animal_name)) {
                animal = a;
            }
        }

        return animal;
    }

    public void loadMoreResults(View view) {
        this.initialResultSize += 5;
        setupResults(this.initialResultSize);

    }
}
