package com.example.simplequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLDataException;

public class CreateAccount extends AppCompatActivity {

    DatabaseManager dbManager;
    private String username, password, passwordConfirm;
    SharedPreferences loginPreference;
    SharedPreferences.Editor loginEditor;
    TextInputEditText usernameInput, passwordInput, passwordConfirmInput;
    Button signUpBtn, loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        dbManager = new DatabaseManager(this);

        getAllViews();
    }

    public void validateSignUp()
    {
        retrieveViewContent();
        try {
            dbManager.open();

            if(username.length() > 4 && passwordConfirm.equals(password) && password.length() > 4)
            {
                if(!dbManager.findUser(username))//does the user already exit
                {
                    //user exists
                    dbManager.insertUser(username,username,password,"false","adminOps" );
                    if (loginPreference == null)
                        loginPreference = getSharedPreferences("loginPreferences", MODE_PRIVATE);

                    loginEditor = loginPreference.edit();
                    loginEditor.putString("status", "true");
                    loginEditor.putString("Username",username);
                    loginEditor.commit();

                    Intent intent = new Intent(CreateAccount.this, Dashboard.class);
                    intent.getStringExtra(username);
                    startActivity(intent);
                    dbManager.close();
                    finish();
                }
                else{
                    usernameInput.setError("Pick another name");
                    dbManager.close();
                    Log.i("LOGIN ERROR: ","BAD PASSWORD CHOICE");
                }
            }
        } catch (SQLDataException throwables) {
            throwables.printStackTrace();
        }



    }

    public void getAllViews()
    {
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        passwordConfirmInput = findViewById(R.id.password_confirm);
        loginBtn = findViewById(R.id.loginPageBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        //usernameInput = findViewById(R.id.username);
    }
    public void retrieveViewContent()
    {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        passwordConfirm = passwordConfirmInput.getText().toString();
    }
    public void onClick(View view)
    {
        if(view.getId() == R.id.loginPageBtn)
        {
            Intent intent = new Intent(CreateAccount.this, Login.class);
            startActivity(intent);
            finish();
        }
        else if(view.getId() == R.id.signUpBtn)
        {
            validateSignUp();
        }
    }
}

