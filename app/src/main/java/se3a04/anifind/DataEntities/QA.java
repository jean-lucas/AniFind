package se3a04.anifind.DataEntities;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * An ADT for question & answer objects
 */
public class QA {

    private String topic;
    private String question;
    private String[] validAnswers;
    private String[] answerHints;

    public QA(String topic, String qs, String[] answers, String[] hints) {
        this.topic = topic;
        this.question = qs;
        this.validAnswers = answers;
        this.answerHints = hints;
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

}

