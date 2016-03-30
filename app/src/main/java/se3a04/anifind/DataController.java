package se3a04.anifind;

import android.content.Context;

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
 * TODO -No longer everything *slightly sadface*
 * TODO -Create results data structure
 * TODO -Look into stupid context wizardry
 */

public class DataController {

    //needed to retrieve data required from text files
    private DatastoreAccess dataAccess;

    //needs to be used when reading in data from text file
    private Context context;

    //data structures that contain all animals, and all questions and associated answers
    private HashMap<String, Animal> listOfAnimals;
    private HashMap<String, QA> listOfQAs;

    public DataController(Context context){
        this.context = context;
        dataAccess = new DatastoreAccess(context);

        this.listOfAnimals = new HashMap<String, Animal>();
        this.listOfQAs = new HashMap<String, QA>();
        initialize();
    }

    private void initialize(){
//        dataAccess = new DatastoreAccess(context);
        animalParse(dataAccess.getAnimalContent());
        qaParse(dataAccess.getQuestionContent());
    }

    private void animalParse(List<String> animalLines){
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

    private void qaParse(List<String> qaLines){
        for (String line : qaLines){
            List<String> qElements = Arrays.asList(line.split("\\s*-\\s*"));

            //Create temp question elements
            String topic = qElements.get(0);
            String question = qElements.get(1);
            String[] answers = qElements.get(2).split("\\s*,\\s*",-1);
            String[] hints = qElements.get(3).split("\\s*,\\s*",-1);

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

    public void setResults(ArrayList<String> newResults, Animal newObj){
        //TODO
    }

    private void resetAllDatastructures(){
        //TODO
    }
}
