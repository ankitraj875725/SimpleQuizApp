package com.example.simplequizapp;

import android.util.Log;

import java.util.ArrayList;

public class Question {
    public ArrayList<String> questions = new ArrayList<>();

    public ArrayList<String[]> choices = new ArrayList<>();

    public ArrayList<String> correctAnswer = new ArrayList<>();

    public void setQuestions(String questions) {
        this.questions.add(questions);
    }

    public void setChoices(String[] choices) {
        this.choices.add(choices);
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer.add(correctAnswer);
    }

    public int size()
    {
        Log.i("SIZE ---- -- --:","SIZE OF THE QUESTIONS ARRAY IS "+questions.size());
        return questions.size();
    }

    public String getQuestion(int a) {
        String question = questions.get(a);
        return question;
    }

    public String getChoice1(int a) {
        String choice = choices.get(a)[0];
        return choice;
    }

    public String getChoice2(int a) {
        String choice = choices.get(a)[1];
        return choice;
    }

    public String getChoice3(int a) {
        String choice = choices.get(a)[2];
        return choice;
    }

    public String getChoice4(int a) {
        String choice = choices.get(a)[3];
        return choice;
    }

    public String getCorrectAnswer(int a) {
        String answer = correctAnswer.get(a);
        return answer;

    }

    @Override
    public String toString()
    {
        return "These are quiz questions";
    }
}