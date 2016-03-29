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

    //the ACTUAL answers given by the user. Key is the topic, answers is the value
    private HashMap<String, String[]> answerGivenByUsers;

    public QA(String topic, String qs, String[] answers, String[] hints) {
        this.topic = topic;
        this.question = qs;
        this.validAnswers = answers;
        this.answerHints = hints;
        this.answerGivenByUsers = new HashMap<String, String[]>(); //initially empty
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

    public String[] getAnswersGivenByUsers(String topic) {
        return  this.answerGivenByUsers.get(topic);
    }

    public void setGivenAnswerByTopic(String topic, String[] answers) {
        this.answerGivenByUsers.put(topic, answers);
    }

}

