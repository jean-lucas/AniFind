package se3a04.anifind.DataEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * An ADT for question & answer objects
 */
public class QA implements Serializable {

    private String topic;
    private String question;
    private String[] validAnswers;  //set of possible answers from user
    private String[] answerHints;   //helpful hints of answers given in the AudioActivity

    private String[] answerGivenByUsers;

    private boolean multiVal;

    public QA(String topic, String qs, String[] answers, String[] hints, boolean multiVal) {
        this.topic = topic;
        this.question = qs;
        this.validAnswers = answers;
        this.answerHints = hints;
        this.answerGivenByUsers = null; //initially empty
        this.multiVal = multiVal;
    }


    public String getTopic() {
        return this.topic;
    }

    public String getQuestion() {
        return this.question;
    }

    public String[] getValidAnswers() {
        return this.validAnswers;
    }

    public String[] getAnswerHints() {
        return this.answerHints;
    }

    public String[] getAnswersGivenByUsers() {
        return this.answerGivenByUsers;
    }

    public boolean getMultiVal(){ return this.multiVal; }

    public void setGivenAnswerByTopic(String[] answers) {
        this.answerGivenByUsers = answers;
    }

    //for debugging purposes
    public String getAnswersGivenToString() {
        String s = "";
        if (this.answerGivenByUsers == null) return s;

        for (String a : this.answerGivenByUsers) {
            s += a +", ";
        }

        return s;
    }

}

