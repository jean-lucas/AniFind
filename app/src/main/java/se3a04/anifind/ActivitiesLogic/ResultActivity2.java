package se3a04.anifind.ActivitiesLogic;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.R;

public class ResultActivity2 extends AppCompatActivity {

    private ArrayList<Animal> animals;
    private LinearLayout animalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.animalList = (LinearLayout) findViewById(R.id.animalResultList);


        Bundle b = getIntent().getExtras();


        //this need to be fixed. removed cast
        animals = new ArrayList<Animal>();

        this.animals = (ArrayList<Animal>) b.getSerializable("animals");



//        TextView animal_name = new TextView(this.getApplicationContext());
        ImageView animal_img = new ImageView(this.getApplicationContext());



        AssetManager am = this.getApplicationContext().getAssets();
        InputStream is =null;
        try {
            is = am.open("centipede/centipede1.jpg");
        }

        catch (IOException e) {
            Log.d("ERRRRRR","EE");
        }

//        Bitmap bmImg = BitmapFactory.decodeFile("");

        if (is == null) {
            Log.d("ISNULL" , "NULL");
        }
        animal_img.setImageDrawable(Drawable.createFromStream(is, null));
        animal_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        animalList.addView(animal_img);
        for (Animal a: animals) {
            TextView animal_name =new TextView(ResultActivity2.this);

            animal_name.setText(a.getName());


            animalList.addView(animal_name);

//            animalList.append(a.toString() + "\n");
        }

    }

    public void goHomeButton(View view) {
        Intent result_intent = new Intent();
        setResult(1, result_intent);
        finish();
    }
}
