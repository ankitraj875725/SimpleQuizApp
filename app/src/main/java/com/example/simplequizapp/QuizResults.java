package com.example.simplequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizResults extends AppCompatActivity {

    TextView scoreText, topicText, percentageText, infoText;
    ListView correctAnswersView;
    ImageView topicImage;
    ArrayList<String> correctAnswers, correctAnswerQuestions;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    int topicPos;

    //@SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        Bundle bundle = getIntent().getExtras();
        getAllViews();
        int score = bundle.getInt("Score");
        int questionSize = bundle.getInt("NumberOfQuestions");
        double percent = ((score / 5.0) / questionSize) * 100.0;
        topicText.setText(bundle.getString("QuizTopic")+" Quiz");
        scoreText.setText("Score: " + score);

        try{
            if(percent==100.0)
                percentageText.setText(String.valueOf(percent).substring(0,4).replace(".","") + "%");
            else
                percentageText.setText(String.valueOf(percent).substring(0,4) + "%");

        }catch (StringIndexOutOfBoundsException e)
        {
            percentageText.setText(String.valueOf(percent)+"%");
        }

        topicPos = bundle.getInt("pos");
        correctAnswers = bundle.getStringArrayList("WrongAnswers");
        correctAnswerQuestions = bundle.getStringArrayList("WrongAnswerQuestions");

        switch (topicPos) {
            case 0:
                topicImage.setImageDrawable(getResources().getDrawable(R.drawable.code));
                break;
            case 1:
                topicImage.setImageDrawable(getResources().getDrawable(R.drawable.java));
                break;
            case 2:
                topicImage.setImageDrawable(getResources().getDrawable(R.drawable.data));
                break;
            case 3:
                topicImage.setImageDrawable(getResources().getDrawable(R.drawable.android));
                break;
            default:
                topicImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
        }

        if (percent < 50.0) {
            scoreText.setTextColor(getResources().getColor(R.color.timeout));
            percentageText.setTextColor(getResources().getColor(R.color.timeout));
        }
        if (percent == 100) {
            infoText.setText("Congratulations, you got all answers right");
            infoText.setTextColor(getResources().getColor(R.color.green));
        }
        createAnswerMap();
        SimpleAdapter adapter = new SimpleAdapter(this, arrayList,
                R.layout.correct_answer_list,
                new String[] {"question", "answer"},
                new int[] {R.id.question, R.id.answer});


        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, correctAnswers);
        correctAnswersView.setAdapter(adapter);

    }

    private void getAllViews() {
        scoreText = findViewById(R.id.score);
        topicText = findViewById(R.id.quiz_topic);
        infoText = findViewById(R.id.info);
        percentageText = findViewById(R.id.percent);
        topicImage = findViewById(R.id.quiz_image);
        correctAnswersView = findViewById(R.id.correct_answers);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.exit) {
            Intent intent = new Intent(QuizResults.this, Dashboard.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.restart) {
            Intent intent = new Intent(QuizResults.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("pos", topicPos);
            intent.putExtras(bundle);
            startActivity(intent);

            finish();
        }
    }

    public void createAnswerMap()
    {

        for (int i = 0; i < correctAnswers.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("question", correctAnswerQuestions.get(i));
            hashMap.put("answer", correctAnswers.get(i));
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
    }
}