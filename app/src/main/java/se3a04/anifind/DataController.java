package se3a04.anifind;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import se3a04.anifind.DataEntities.Animal;
import se3a04.anifind.DataEntities.QA;
import se3a04.anifind.DataEntities.Result;

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
    private HashMap<String, Animal> animalMap;
    private HashMap<String, QA> qaMap;

    //list containing all past results
    private ArrayList<Result> listOfResults;

    public DataController(Context context){
        this.context = context;
        this.dataAccess = new DatastoreAccess(context);
        this.resultsData = new ResultsDataStructure();

        this.animalMap = new HashMap<String, Animal>();
        this.qaMap = new HashMap<String, QA>();
        this.listOfResults = new ArrayList<Result>();

        initialize();
    }

    private void initialize(){
//        dataAccess = new DatastoreAccess(context);
        animalParse(dataAccess.getAnimalContent());
        qaParse(dataAccess.getQuestionContent());
    }

    public void sessionCompleted(List<String[]> sessionAnswers, String selectedAnimal){
        Result sessionResult = new Result(sessionAnswers,selectedAnimal);
        listOfResults.add(sessionResult);   //adds the latest session's result to result list
        resultParse(dataAccess.getResultsContent());
        checkForUpdates();
        saveResults();
    }

    //Parses lines from animal data set and creates animal obj's and adds them to animalMap
    private void animalParse(List<String> animalLines){

        if (animalLines == null) {
            Log.d("NULL_LIST", "The list is null");
            return;
        }

        for (String line : animalLines) {
            List<String> attributes = Arrays.asList(line.split("\\s*,\\s*",-1));

            Log.d("attr", Integer.toString(attributes.size()));
            Log.d("attr",attributes.toString());
            Log.d("attr",attributes.get(0));
            Log.d("attr",attributes.get(1));
            Log.d("attr",attributes.get(2));
            Log.d("attr",attributes.get(3));
            Log.d("attr",attributes.get(4));
            Log.d("attr",attributes.get(5));
            Log.d("attr",attributes.get(6));
            //Create temp animal attributes
            String name = attributes.get(0);
            String lifestyle = attributes.get(5);
                //attributes 2-6 may be multi-valued
            String[] habitat = attributes.get(2).split("\\s*-\\s*",-1);
            String[] mobility = attributes.get(6).split("\\s*-\\s*", -1);
            String[] location = attributes.get(1).split("\\s*-\\s*", -1);
            String[] colors = attributes.get(3).split("\\s*-\\s*", -1);
            String[] size = attributes.get(4).split("\\s*-\\s*", -1);
            //String imgFile = attributes.get(7);

            //Use temp animal attributes to create new animal obj
            Animal newAnimal = new Animal(name,lifestyle,habitat,mobility,location,colors,size);

            //Add the new animal to animalMap
            animalMap.put(newAnimal.getName(), newAnimal);
        }
    }

    //Parses lines from question/answer data set and creates QA obj's and adds them to qaMap
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

            boolean multiV = true;
            if(topic.equals("Time"))
                multiV = false;

            //Use temp Q elements to create new QA obj
            QA newQA = new QA(topic, question, answers, hints, multiV);

            //Add the new QA obj to qaMap
            qaMap.put(newQA.getTopic(), newQA);
        }
    }

    public HashMap<String, Animal> getAnimals(){
        return animalMap;
    }

    public HashMap<String, QA> getQuestions(){
        return qaMap;
    }

    //Parses lines from past results data set and creates Result obj's and adds them to listOfResults
    private void resultParse(List<String> rLines){
        if (rLines == null) {
            Log.d("NULL_LIST", "The list is null");
            return;
        }

        for (String line : rLines){
            List<String> rElements = Arrays.asList(line.split("\\s*,\\s*"));

            Log.d("rElements",rElements.toString());
            //Create temp result elements
            String animalName = rElements.get(0);
//            rElements.remove(0);

            List<String[]> answers = new ArrayList<String[]>();
            String[] emptyS = {""};
            for(int i = 1; i<rElements.size(); i++){
                Log.d("element",rElements.get(i));
                String[] answerVals =  rElements.get(i).split("\\s*-\\s*", -1);
                answers.add(emptyS);
                answers.set(i-1,answerVals);
            }


            //Use temp result elements to create new Result obj
            Result newResult = new Result(answers,animalName);

            //Add the new Result obj to listOfResults
            listOfResults.add(newResult);
        }
    }

    private void checkForUpdates(){
        //TODO this method will compare past results with current QA data set and make necessary changes
    }

    private void saveResults(){
        List<String> resultLines = new ArrayList<String>();
        for(Result e : listOfResults){
            resultLines.add(e.toString());
            Log.d("RESULTS", e.toString());
        }
        dataAccess.writeResultsContent(resultLines);
    }

    private void resetAllDatastructures(){
        //TODO is this all this method needs to do?
        animalMap.clear();
        qaMap.clear();
        listOfResults.clear();
    }
}
