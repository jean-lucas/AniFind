package se3a04.anifind;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import junit.framework.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        animalFilename = "../assets/animal_facts.txt";
        resultsFilename = "../assets/old_results.txt";
        questionsFilename = "../assets/question_answer_content.txt";
        imagesFilename = null;  //TODO

        this.mContext = context;
    }

    /*
    private ArrayList<String> getFileContent(String fileName) {
        ArrayList<String> content;

        Log.d("NULL_LIST", "S = " + in.nextLine());

        try {
            content = new ArrayList<String>();

            Scanner in = new Scanner(new File(fileName));

            in.nextLine(); //skip first line

            while(in.hasNext()) {

                String s = in.nextLine();
                content.add(s);
            }

            in.close();
        }

        catch (FileNotFoundException e) {
            return null;
        }

        return content;
    }
*/

    private List<String> getFileContent(String filename){
        //TODO
        List<String> mLines = new ArrayList<>();
        Log.d("NULL_LIST", "Starting 1 ...");
        AssetManager am = mContext.getAssets();
        Log.d("NULL_LIST", "Starting 2 ...");

        try {
            InputStream is = am.open(filename);
            Log.d("NULL_LIST", "check 1...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Log.d("NULL_LIST", "check 2...");
            String line;
            Log.d("NULL_LIST", "Starting...");

            while ((line = reader.readLine()) != null)
                Log.d("NULL_LIST", "S = " + line);
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("NULL_LIST", mLines.toString());
        //removes first line in text file which is just titles of elements
        mLines.remove(0);

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

    private void writeToFile(String filename, List<String> content){
        //TODO
    }

    public void writeAnimalContent(List<String> content){
        writeToFile(animalFilename,content);
    }

    public void writeResultsContent(List<String> content){
        writeToFile(resultsFilename,content);
    }

}
