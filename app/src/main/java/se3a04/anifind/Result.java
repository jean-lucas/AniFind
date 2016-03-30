package se3a04.anifind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se3a04.anifind.DataEntities.Animal;

/**
 * An ADT for a Result object. Holds a user session's answers and selected animal.
 */
public class Result {

    private List<String> answers;  //List of user's answers associated with the selected animal
    private String selectedAnimal;  //The name of the user selected animal

    public Result(List<String> answers, String selectedAnimalName){
        this.answers = answers;
        this.selectedAnimal = selectedAnimalName;
    }

    public List<String> getAnswers(){
        return answers;
    }

    public String getAnimalName(){
        return selectedAnimal;
    }

    public boolean compareTo(Result otherResult){
        boolean equal = false;
        if (this.answers.equals(otherResult) && this.selectedAnimal.equals(otherResult.selectedAnimal))
            equal = true;
        return equal;
    }

    public String toString(){
        String resultsOutput = "";

        resultsOutput += selectedAnimal;

        for (String s : answers){
            resultsOutput += "," + s;
        }

        return resultsOutput;
    }
}
