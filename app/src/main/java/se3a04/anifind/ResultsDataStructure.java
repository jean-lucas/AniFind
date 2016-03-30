package se3a04.anifind;

import java.util.ArrayList;

/**
 * Created by Mathew on 2016-03-29.
 */

/**
 * TODO -delete this class???
 */

public class ResultsDataStructure {

    private ArrayList<String> listOfPreviousResults;

    public ResultsDataStructure(){
        listOfPreviousResults = new ArrayList<String>();
    }

    public void addNewResult(String result){
        listOfPreviousResults.add(result);
    }

    public ArrayList<String> getCollection(){
        return listOfPreviousResults;
    }

}
