package com.example.simplequizapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.Map;

public class AddQuestions extends AppCompatActivity {
    DatabaseManager dbManager = new DatabaseManager(this);
    Spinner topicsSpinner, optionsSpinner;
    TextInputEditText questionInput, optionAInput, optionBInput, optionCInput, optionDInput;
    String[] topics = {"General", "Java", "Data Structures", "Android Dev"};
    String[] options = {"Option A", "Option B", "Option C", "Option D"};

    FirebaseFirestore rootNode;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        topicsSpinner = findViewById(R.id.topics_spinner);
        optionsSpinner = findViewById(R.id.correct_spinner);
        questionInput = findViewById(R.id.question_in);
        optionAInput = findViewById(R.id.optionA_in);
        optionBInput = findViewById(R.id.optionB_in);
        optionCInput = findViewById(R.id.optionC_in);
        optionDInput = findViewById(R.id.optionD_in);


        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, topics);
        ArrayAdapter optionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options);

        topicsSpinner.setAdapter(spinnerAdapter);
        optionsSpinner.setAdapter(optionAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void commitEntries(View view) {

        try {
            dbManager.open();
        } catch (SQLDataException throwables) {
            throwables.printStackTrace();
        }
        String ans = optionsSpinner.getSelectedItem().toString();
        String top = topicsSpinner.getSelectedItem().toString();
        String correctAnswer;
        switch (ans) {
            case "Option A":
                correctAnswer = optionAInput.getText().toString();
                break;
            case "Option B":
                correctAnswer = optionBInput.getText().toString();
                break;
            case "Option C":
                correctAnswer = optionCInput.getText().toString();
                break;
            case "Option D":
                correctAnswer = optionDInput.getText().toString();
                break;
            default:
                correctAnswer = " ";
                Log.i("THE SPINNER: ", "RECEIVED THE OPTION A " + ans + " HENCE ANSWER IS " + correctAnswer);
                break;
        }

        if (questionInput.getText().toString().equals("") || optionAInput.getText().toString().equals("") || optionBInput.getText().toString().equals("") || optionCInput.getText().toString().equals("") || optionDInput.getText().toString().equals("")) {
            highlightEmpty();

        } else {
            QuestionInstance questionInstance = new QuestionInstance(questionInput.getText().toString(),optionAInput.getText().toString(),
                    optionBInput.getText().toString(),optionCInput.getText().toString(),optionDInput.getText().toString(),correctAnswer,top);
            rootNode = FirebaseFirestore.getInstance();
            /*Map<String, String> quizMap = new HashMap<>();
            quizMap.put("Question",questionInput.getText().toString());
            quizMap.put("opt_a",optionAInput.getText().toString());
            quizMap.put("opt_b",optionBInput.getText().toString());
            quizMap.put("opt_c",optionCInput.getText().toString());
            quizMap.put("opt_d",optionCInput.getText().toString());
            quizMap.put("correct",correctAnswer);
            quizMap.put("Topic",top);*/

            rootNode.collection("Quiz").add(questionInstance).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AddQuestions.this,"Successfully added to the firestore",Toast.LENGTH_LONG).show();
                }
            });

            //
            /*Log.i("SPINNER UPDATE: - - - ", "STRING ACQUIRED WAS: " + ans + " and " + top);
            dbManager.insertQuestion(questionInput.getText().toString(), optionAInput.getText().toString(),
                    optionBInput.getText().toString(), optionCInput.getText().toString(),
                    optionDInput.getText().toString(), correctAnswer, top);*/

            clearAllFields();
            unhighlightFields();
        }
        dbManager.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void highlightEmpty() {
        unhighlightFields();
        if (questionInput.getText().toString().equals(""))
            questionInput.setBackgroundColor(getResources().getColor(R.color.input_error));
        if (optionAInput.getText().toString().equals(""))
            optionAInput.setBackgroundColor(getResources().getColor(R.color.input_error));
        if (optionBInput.getText().toString().equals(""))
            optionBInput.setBackgroundColor(getResources().getColor(R.color.input_error));
        if (optionCInput.getText().toString().equals(""))
            optionCInput.setBackgroundColor(getResources().getColor(R.color.input_error));
        if (optionDInput.getText().toString().equals(""))
            optionDInput.setBackgroundColor(getResources().getColor(R.color.input_error));
        Log.i("EMPTY FIELDS HERE", "WE ARE HERE");
    }

    public void unhighlightFields() {
        if (!questionInput.getText().toString().equals(""))
            questionInput.setBackgroundColor(getResources().getColor(R.color.input_field));
        if (!optionAInput.getText().toString().equals(""))
            optionAInput.setBackgroundColor(getResources().getColor(R.color.input_field));
        if (!optionBInput.getText().toString().equals(""))
            optionBInput.setBackgroundColor(getResources().getColor(R.color.input_field));
        if (!optionCInput.getText().toString().equals(""))
            optionCInput.setBackgroundColor(getResources().getColor(R.color.input_field));
        if (!optionDInput.getText().toString().equals(""))
            optionDInput.setBackgroundColor(getResources().getColor(R.color.input_field));

        Log.i("EMPTY FIELDS HERE", "WE ARE HERE");
    }

    public void clearAllFields() {
        questionInput.setText(null);
        optionAInput.setText(null);
        optionBInput.setText(null);
        optionCInput.setText(null);
        optionDInput.setText(null);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();

    }
}