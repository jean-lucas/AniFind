package se3a04.anifind;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;

/**
 * Created by Mathew on 2016-03-29.
 */

/**
 * TODO -data set updating/learning
 * TODO -Look into context wizardry
 */

public class DataController {

    //needed to retrieve data required from text files
    private DatastoreAccess dataAccess;

    //needed to update and analyse results for possible changes to data sets
    private ResultsDataStructure resultsData;

    //needs to be used when reading in data from text file
    private Context context;

    //data structures that contain all animals, and all questions and associated answers
    private HashMap<String, Animal> listOfAnimals;
    private HashMap<String, QA> listOfQAs;

    //list containing all past results
    private ArrayList<Result> listOfResults;

    public DataController(Context context){
        this.context = context;
        this.dataAccess = new DatastoreAccess(context);
        this.resultsData = new ResultsDataStructure();

        this.listOfAnimals = new HashMap<String, Animal>();
        this.listOfQAs = new HashMap<String, QA>();
        this.listOfResults = new ArrayList<Result>();

        initialize();
    }

    private void initialize(){
//        dataAccess = new DatastoreAccess(context);
        animalParse(dataAccess.getAnimalContent());
        qaParse(dataAccess.getQuestionContent());
    }

    public void sessionCompleted(Result sessionResult){
        listOfResults.add(sessionResult);   //adds the latest session's result to result list
        resultParse(dataAccess.getResultsContent());
        checkForUpdates();
    }

    //Parses lines from animal data set and creates animal obj's and adds them to listOfAnimals
    private void animalParse(List<String> animalLines){

        if (animalLines == null) {
            Log.d("NULL_LIST", "The list is null");
            return;
        }

        for (String line : animalLines) {
            List<String> attributes = Arrays.asList(line.split("\\s*,\\s*"));

            //Create temp animal attributes
            String name = attributes.get(0);
            String lifestyle = attributes.get(1);
                //attributes 2-6 may be multi-valued
            String[] habitat = attributes.get(2).split("\\s*-\\s*",-1);
            String[] mobility = attributes.get(3).split("\\s*-\\s*",-1);
            String[] location = attributes.get(4).split("\\s*-\\s*",-1);
            String[] colors = attributes.get(5).split("\\s*-\\s*",-1);
            String[] size = attributes.get(6).split("\\s*-\\s*",-1);
            String imgFile = attributes.get(7);

            //Use temp animal attributes to create new animal obj
            Animal newAnimal = new Animal(name,lifestyle,habitat,mobility,location,colors,size,imgFile);

            //Add the new animal to listOfAnimals
            listOfAnimals.put(newAnimal.getName(),newAnimal);
        }
    }

    //Parses lines from question/answer data set and creates QA obj's and adds them to listOfQAs
    private void qaParse(List<String> qaLines){

        if (qaLines == null) {
            Log.d("NULL_LIST", "The list is null");
            return;
        }


        for (String line : qaLines){
            List<String> qElements = Arrays.asList(line.split("\\s*-\\s*"));

            //Create temp question elements
            String topic = qElements.get(0);
            String question = qElements.get(1);
            String[] answers = qElements.get(2).split("\\s*,\\s*", -1);
            String[] hints = qElements.get(3).split("\\s*,\\s*", -1);

            //Use temp Q elements to create new QA obj
            QA newQA = new QA(topic, question, answers, hints);

            //Add the new QA obj to listOfQAs
            listOfQAs.put(newQA.getTopic(),newQA);
        }
    }

    public HashMap<String, Animal> getAnimals(){
        return listOfAnimals;
    }

    public HashMap<String, QA> getQuestions(){
        return listOfQAs;
    }

    //Parses lines from past results data set and creates Result obj's and adds them to listOfResults
    private void resultParse(List<String> rLines){
        if (rLines == null) {
            Log.d("NULL_LIST", "The list is null");
            return;
        }

        for (String line : rLines){
            List<String> rElements = Arrays.asList(line.split("\\s*,\\s*"));

            //Create temp result elements
            String animalName = rElements.get(0);
            rElements.remove(0);

            //Use temp result elements to create new Result obj
            Result newResult = new Result(rElements,animalName);

            //Add the new Result obj to listOfResults
            listOfResults.add(newResult);
        }
    }

    private void checkForUpdates(){
        //TODO this method will compare past results with current QA data set and make necessary changes
    }

    private void resetAllDatastructures(){
        //TODO is this all this method needs to do?
        listOfAnimals.clear();
        listOfQAs.clear();
        listOfResults.clear();
    }
}
