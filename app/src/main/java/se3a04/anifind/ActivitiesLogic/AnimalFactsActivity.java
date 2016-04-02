package se3a04.anifind.ActivitiesLogic;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.R;

public class AnimalFactsActivity extends AppCompatActivity {


    private Animal animal;
    private String filepath;

    private TextView animal_name;
    private ImageView animal_img;
    private TableLayout animal_facts;


    private final String DEFAULT_IMAGE = "animal_images/file_not_found.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_facts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Bundle b = getIntent().getExtras();

        this.animal = (Animal) b.getSerializable("animal");
        this.filepath = b.getString("img_path");

        this.animal_name = (TextView) findViewById(R.id.animal_name);
        this.animal_img = (ImageView) findViewById(R.id.animal_img);
        this.animal_facts = (TableLayout) findViewById(R.id.animal_facts);



        setupActivityViews();


    }

    public void setupActivityViews() {

        //set title
        animal_name.setText(animal.getName());

        //set img
        AssetManager am = this.getApplicationContext().getAssets();
        InputStream is = null;
        InputStream is_error = null;

        try {
            is_error = am.open(DEFAULT_IMAGE);
            is = am.open(filepath);
        } catch (IOException e) {
            is = is_error;
        } finally {
            if (is == null) {
                is = is_error;
            }
        }

        animal_img.setImageDrawable(Drawable.createFromStream(is, null));
//        animal_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);



        //set facts

        HashMap<String, String[]> facts = animal.getFacts();

        for (String topic: facts.keySet()) {
            TextView topic_title = new TextView(AnimalFactsActivity.this);
            TextView topic_content = new TextView(AnimalFactsActivity.this);

            topic_title.setText(topic + ": ");
            topic_title.setTypeface(Typeface.DEFAULT_BOLD);

            for (String s: facts.get(topic)) {
                topic_content.append(s + "\n");
            }

            TableRow tr = new TableRow(AnimalFactsActivity.this);
            tr.addView(topic_title);
            tr.addView(topic_content);
            tr.setOrientation(TableRow.HORIZONTAL);
            tr.setMinimumWidth(400);
            tr.setPadding(0,10,0,10);


            this.animal_facts.addView(tr);
        }




    }
}
