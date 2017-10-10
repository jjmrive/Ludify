package com.jjmrive.ludify.visit;

import java.io.Serializable;
import java.util.ArrayList;


public class Question implements Serializable {

    private int idQuestion;
    private String question;
    private ArrayList<String> answers;
    private int correct;
    private int prize;
    private boolean result;

    public Question(int idQuestion, String question, ArrayList<String> answers, int correct, int prize){
        this.idQuestion = idQuestion;
        this.question = question;
        this.answers = answers;
        this.correct = correct;
        this.prize = prize;
        result = false;
    }

    public void setResult(boolean result){
        this.result = result;
    }

    public boolean getResult(){
        return result;
    }

    public int getPrize(){
        return prize;
    }

    public int getId(){
        return idQuestion;
    }

    public String getQuestion(){
        return question;
    }

    public ArrayList<String> getAnswers(){
        return answers;
    }

    public int getCorrect(){
        return correct;
    }

    @Override
    public boolean equals(Object o){
        boolean eq = false;
        Question qs = (Question) o;
        if (qs.idQuestion == this.idQuestion){
            eq = true;
        }
        return eq;
    }


}
