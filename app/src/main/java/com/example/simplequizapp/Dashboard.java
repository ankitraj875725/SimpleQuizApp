package com.example.simplequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginEditor;
    String username;
    GridView topicsGrid;
    ExtendedFloatingActionButton fab;
    TextView welcomeText;
    ArrayList<TopicItem> topicsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if(loginPreferences==null)
            loginPreferences = getSharedPreferences("loginPreferences",MODE_PRIVATE);
        username = loginPreferences.getString("Username","0");
        String javaScore = loginPreferences.getString("Java","0");
        String generalQuizScore = loginPreferences.getString("General","0");
        String androidScore = loginPreferences.getString("Android Dev","0");
        String DSScore = loginPreferences.getString("Data Structures","0");


        //fab = findViewById(R.id.addQuestionBtn);
        welcomeText = findViewById(R.id.welcome_user);
        welcomeText.setText("Welcome, "+username);

        topicsList.add(new TopicItem("General  "+generalQuizScore+"%", R.drawable.code));
        topicsList.add(new TopicItem("Java  "+javaScore+"%",R.drawable.java));
        topicsList.add(new TopicItem("Data Structures   "+DSScore+"%", R.drawable.data));
        topicsList.add(new TopicItem("Android Dev   "+androidScore+"%", R.drawable.android));

        topicsGrid = findViewById(R.id.gridView);

        GridAdapter topicsAdapter = new GridAdapter(this,R.layout.grid_item,topicsList);
        topicsGrid.setAdapter(topicsAdapter);
        topicsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void logout(View view)
    {
        if(view.getId()==R.id.logoutBtn)
        {
            loginEditor = loginPreferences.edit();
            loginEditor.putString("status","false");
            loginEditor.putString("Username", "");
            loginEditor.putString("Java","0");
            loginEditor.putString("General","0");
            loginEditor.putString("Data Structures","0");
            loginEditor.putString("Android Dev","0");
            loginEditor.commit();

            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onResume() {
        if(loginPreferences==null)
            loginPreferences = getSharedPreferences("loginPreferences",MODE_PRIVATE);
        String javaScore = loginPreferences.getString("Java","0");
        String generalQuizScore = loginPreferences.getString("General","0");
        String androidScore = loginPreferences.getString("Android Dev","0");
        String DSScore = loginPreferences.getString("Data Structures","0");

        topicsList.add(new TopicItem("General  "+generalQuizScore+"%", R.drawable.code));
        topicsList.add(new TopicItem("Java  "+javaScore+"%",R.drawable.java));
        topicsList.add(new TopicItem("Data Structures   "+DSScore+"%", R.drawable.data));
        topicsList.add(new TopicItem("Android Dev   "+androidScore+"%", R.drawable.android));
        super.onResume();
    }
}