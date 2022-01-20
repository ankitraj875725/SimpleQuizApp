package com.example.simplequizapp;

import java.util.ArrayList;

public class JavaQuestions extends Question {
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
        return "These are Java questions";
    }
}


/*{
            "String in Java is a?",
            "Which of these method of String class can be used to test to strings for equality?",
            "Which of these operators is used to allocate memory to array variable in Java?" ,
            "Which of these is necessary to specify at time of array initialization?",
            "Which of these class contains the methods used to write in a file?",
            "Which component is used to compile, debug and execute java program?",
            "What does LocalTime represent?",
            "Which of the following allows us to call generic methods as a normal method?"
    };*/

/* {
            {"Class"," Variable","Object","Character array"},
            {"isequal()","isequals()", "equal()","equals()"},
            {"malloc","alloc","new" ,"new malloc"},
            { "Row","Column","Both row and column","None of the above"},
            {"FileStream","FileInputStream","BufferedOutputStream","FileBufferStream"},
            {"JVM", "JDK", "JIK", "JRE"},
            {"Date without time", "Time without Date", "Date and Time", "Date and Time with timezone"},
            {"Type Interface", " Interface", "Inner class", "All of the mentioned"}};*/


/*{;
            "Class",
            "equals()",
            "new",
            "Row",
            "FileInputStream",
            "JDK",
            "Time without Date",
            "Type Interface"
    };*/
