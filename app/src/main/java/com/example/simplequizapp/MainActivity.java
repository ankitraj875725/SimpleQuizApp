package com.example.simplequizapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv_question, scoreView, questionNumView;
    Button btn_one, btn_two, btn_three, btn_four;
    ArrayList<String> wrongAnswers = new ArrayList<>();
    ArrayList<String> wrongAnswerQuestions = new ArrayList<>();
    ArrayList<Integer> queue = new ArrayList<>();
    Random random = new Random();
    SharedPreferences scoresPreferences;
    SharedPreferences.Editor scoresEditor;
    Timer countDown;
    String choice;
    int score = 0, left = 0;
    private Question question = new Question();
    private String answer;
    boolean isOver = false;
    private int questionLength, choicePos;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("BOUNDS DEBUG: ", "FOUND THAT THE BOUNDS RECIEVED WAS: " + questionLength);
        scoresPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);
        Bundle bundle;
        bundle = getIntent().getExtras();
        getAllViews();
        disableBtns();

        String[] topics = new String[]{"General", "Java", "Data Structures", "Android Dev"};
        choicePos = bundle.getInt("pos");
        choice = topics[choicePos];
        loadQuiz(choice);//populates the question class

    }

    public void onClick(View v) {

        Button clickedBtn = findViewById(v.getId());
        if (clickedBtn.getText().toString().equals(answer)) {
            increaseScore();
            Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();

        } else {
            int num = queue.get(left + 1);
            wrongAnswerQuestions.add(question.getQuestion(num));
            wrongAnswers.add(question.getCorrectAnswer(num));
        }
        NextQuestion(left);
    }

    private void GameOver() {
        isOver = true;

        if (scoresPreferences == null)
            scoresPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);

        scoresEditor = scoresPreferences.edit();
        double percentage = ((score / 5.0) / question.size()) * 100.0;
        scoresEditor.putString(choice, Double.toString(percentage).substring(0,4));
        Log.i("SHARED PREFERENCE PROB ", "1 " + percentage);
        scoresEditor.commit();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Your score: " + score)
                .setCancelable(false)
                .setPositiveButton("Result", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, QuizResults.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("pos", choicePos);
                        bundle.putString("QuizTopic", choice);
                        bundle.putInt("Score", score);
                        bundle.putInt("NumberOfQuestions", questionLength);
                        bundle.putStringArrayList("WrongAnswerQuestions", wrongAnswerQuestions);
                        bundle.putStringArrayList("WrongAnswers", wrongAnswers);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialogBuilder.show();
    }

    private void NextQuestion(int number) {

        if (number < 0) {
            GameOver();
        } else {
            questionNumView.setText((questionLength - number) + "/" + questionLength);
            int num = queue.get(number);
            tv_question.setText(question.getQuestion(num));
            btn_one.setText(question.getChoice1(num));
            btn_two.setText(question.getChoice2(num));
            btn_three.setText(question.getChoice3(num));
            btn_four.setText(question.getChoice4(num));

            answer = question.getCorrectAnswer(num);
        }
        left--;
    }

    private void increaseScore() {
        score += 5;
        scoreView.setText("Score: " + score);
    }


    private class Timer extends Thread {
        int seconds;
        TextView timerView;

        Timer(int seconds) {
            this.seconds = seconds;
            timerView = findViewById(R.id.timer);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            for (int i = seconds; i >= 0; i--) {
                if (!isOver) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, "Timer was interrupted", Toast.LENGTH_LONG);
                    }
                    setTimeText(i);
                    if (i == 10) {
                        timerView.setTextColor(getColor(R.color.timeout));
                    }
                }
            }

        }


        private void setTimeText(int seconds) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    int min = seconds / 60;
                    int sec = (seconds % 60);
                    String timeLeft;
                    if (sec < 10) {
                        timeLeft = min + ":0" + sec;
                        if (min == 0 && sec == 0) {
                            GameOver();
                        }
                    } else
                        timeLeft = min + ":" + sec;
                    timerView.setText(timeLeft);
                }
            });
        }
    }

    private void loadQuiz(String topic) {
        //connect to the Firebase FireStore
        FirebaseFirestore rootNode = FirebaseFirestore.getInstance();
        CollectionReference quizReference = rootNode.collection("Quiz/");

        quizReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    QuestionInstance que = documentSnapshot.toObject(QuestionInstance.class);

                    if (que.topic.equals(choice)) {
                        question.setQuestions(que.getQuestion());

                        String[] choices = new String[4];
                        choices[0] = que.getOption_a();
                        choices[1] = que.getOption_b();
                        choices[2] = que.getOption_c();
                        choices[3] = que.getOption_d();
                        question.setChoices(choices);
                        question.setCorrectAnswer(que.getCorrect_answer());
                    }

                }
                if (question.size() <= 0) {
                    EmptyQuizError();
                }
                questionLength = question.questions.size();
                left = question.size() - 1;
                queue = QueueBuilder();//creates a random queue for quiz questions
                Log.i("CONTENT LOADED ON QUIZ ", "Test chosen " + choice);

                //get all views


                if (queue.size() != 0) {
                    enableBtns();
                    NextQuestion(left);
                    countDown = new Timer(queue.size() * 10);
                    countDown.start();
                }
            }
        });
    }

    private ArrayList<Integer> QueueBuilder() {
        ArrayList<Integer> qNumbers = new ArrayList<>();

        Log.i("BOUNDS PROBLEM", "THE BOUNDS IN QUEUEBUILDER ARE NOW " + question.size());
        if (question.size() > 0) {
            Integer buff = random.nextInt(question.size());
            int condition = question.size();
            while (condition > 0) {
                if (!qNumbers.contains(buff)) {
                    qNumbers.add(buff);
                    condition--;
                    //buff = random.nextInt(question.size());
                }
                buff = random.nextInt(question.size());
            }

        }
        return qNumbers;
    }

    public void EmptyQuizError() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Sorry, questions haven't been added yet!")
                .setCancelable(false)
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, AddQuestions.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
        alertDialogBuilder.show();
    }

    private void getAllViews() {
        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_four = findViewById(R.id.btn_four);
        scoreView = findViewById(R.id.scoreText);
        tv_question = findViewById(R.id.tv_question);
        questionNumView = findViewById(R.id.question_number);
    }
    private void enableBtns()
    {
        btn_four.setEnabled(true);
        btn_three.setEnabled(true);
        btn_two.setEnabled(true);
        btn_one.setEnabled(true);
    }private void disableBtns()
    {
        btn_four.setEnabled(false);
        btn_three.setEnabled(false);
        btn_two.setEnabled(false);
        btn_one.setEnabled(false);
    }
}