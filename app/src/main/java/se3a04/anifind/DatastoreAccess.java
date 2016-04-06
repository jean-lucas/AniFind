package se3a04.anifind;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import se3a04.anifind.Misc.Encryption;

/**
 * Accesses text data files with read and write capability
 */

/**
 * TODO -state images filename in no-parameter constructor
 * TODO -read and write methods (Figure out wtf this context bullshit is...)
 * TODO -also need to figure out how to test java modules separately in Android Studio
 */

public class DatastoreAccess {

    private String animalFilename, resultsFilename, questionsFilename, imagesFilename;
    private Context mContext;

    public DatastoreAccess(Context context){
        animalFilename = "animal_facts.txt";
        resultsFilename = "data/old_results.txt";
        questionsFilename = "question_answer_content.txt";
        imagesFilename = null;  //TODO

        this.mContext = context;
    }



    private List<String> getFileContent(String filename){
        //TODO
        List<String> mLines = new ArrayList<>();
        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //removes first line in text file which is just titles of elements
        mLines.remove(0);

        mLines = Encryption.encrypt(mLines);
        return mLines;
    }

    public List<String> getAnimalContent(){
        return getFileContent(animalFilename);
    }

    public List<String> getQuestionContent(){
        return getFileContent(questionsFilename);
    }

    public List<String> getImagesContent(){
        return getFileContent(imagesFilename);
    }

    public List<String> getResultsContent(){
        return getFileContent(resultsFilename);
    }

}
