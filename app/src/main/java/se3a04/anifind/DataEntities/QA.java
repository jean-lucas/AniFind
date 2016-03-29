package se3a04.anifind.DataEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * An ADT for question & answer objects
 */
public class QA {

    private String topic;
    private String question;
    private String[] validAnswers;  //set of possible answers from user
    private String[] answerHints;   //helpful hints of answers given in the AudioActivity


    private String[] answerGivenByUsers;

    public QA(String topic, String qs, String[] answers, String[] hints) {
        this.topic = topic;
        this.question = qs;
        this.validAnswers = answers;
        this.answerHints = hints;
        this.answerGivenByUsers = null; //initially empty
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

    public void setGivenAnswerByTopic(String[] answers) {
        this.answerGivenByUsers = answers;
    }

}

