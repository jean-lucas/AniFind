package se3a04.anifind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se3a04.anifind.DataEntities.Animal;

/**
 * An ADT for a Result object. Holds a user session's answers and selected animal.
 */

/**
 * TODO -change compareTo method to return an int that is proportional to likeness
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

    //Returns an int value that is proportional to the likeness of the 2 Result obj's
    public int compareTo(Result otherResult){
        int likeness = 0;
        if (this.selectedAnimal.equals(otherResult.selectedAnimal))
            for (int i = 0; i<6; i++){
                if(this.answers.get(i).equals(otherResult.answers.get(i)))
                    likeness++;
            }
        return likeness;
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
