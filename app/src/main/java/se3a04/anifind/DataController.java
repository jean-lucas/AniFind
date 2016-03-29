package se3a04.anifind;

import android.content.Context;

import java.util.ArrayList;

import se3a04.anifind.DataEntities.Animal;

/**
 * Created by Mathew on 2016-03-29.
 */

/**
 * TODO -Everything *sadface*
 * TODO -Look into stupid context wizardry
 * TODO -create hash maps for animals and questions/answers
 */

public class DataController {

    private DatastoreAccess dataAccess;
    private Context context;

    public DataController(Context context){
        this.context = context;
        initialize();
    }

    private void initialize(){
        dataAccess = new DatastoreAccess(context);
    }

    public ArrayList<Animal> getAnimals(){
        return null;
    }

    public ArrayList<String> getQuestions(){
        return null;
    }

    public void setResults(ArrayList<String> newResults, Animal newObj){
        //TODO
    }

    private void resetAllDatastructures(){
        //TODO
    }
}
