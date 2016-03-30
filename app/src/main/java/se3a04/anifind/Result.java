package se3a04.anifind;

import java.io.Serializable;
import java.util.ArrayList;

import se3a04.anifind.DataEntities.Animal;

/**
 * An ADT for a Result object. Holds a user session's answers and selected animal.
 */
public class Result {

    private ArrayList<String> answers;
    private Animal selectedAnimal;

    public Result(ArrayList<String> answers, Animal selectedAnimal){
        this.answers = answers;
        this.selectedAnimal = selectedAnimal;
    }

    public ArrayList<String> getAnswers(){
        return answers;
    }

    public Animal getAnimal(){
        return selectedAnimal;
    }

    public String toString(){
        String resultsOutput = "";
        for (String s : answers){
            resultsOutput += s + ",";
        }
        resultsOutput += selectedAnimal.getName();
        return resultsOutput;
    }
}
