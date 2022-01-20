package com.example.simplequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AdminArea extends AppCompatActivity {

    Button adminLogin, back;
    TextInputEditText passwordIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        adminLogin = findViewById(R.id.AdminLoginBtn);
        back = findViewById(R.id.back);
        passwordIn = findViewById(R.id.adminPassword);

    }
    public void onClick(View view)
    {
        if(view.getId() == R.id.AdminLoginBtn)
        {
            if(passwordIn.getText().toString().equals("Admin123"))
            {
                Intent intent = new Intent(AdminArea.this, AddQuestions.class);
                startActivity(intent);
                finish();
            }
            else
            {
                passwordIn.setError("Wrong password");
            }
        }
        else if(view.getId() == R.id.AdminLoginBtn)
        {
            Intent intent = new Intent(AdminArea.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}