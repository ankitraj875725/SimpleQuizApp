package com.example.simplequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLDataException;

public class Login extends AppCompatActivity {

    SharedPreferences loginPreference;
    SharedPreferences.Editor loginEditor;
    DatabaseManager dbManager = new DatabaseManager(this);
    String username, password;
    TextView error_message;
    TextInputEditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPreference = getSharedPreferences("loginPreferences", MODE_PRIVATE);
        CheckLoginStatus();
        getAllViews();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.loginBtn) {
            validateLogin();
        } else if (view.getId() == R.id.signInBtn) {
            Intent intent = new Intent(Login.this, CreateAccount.class);
            startActivity(intent);
            finish();
        }
        else if(view.getId() == R.id.adminAreaBtn)
        {
            Intent intent = new Intent(Login.this,AdminArea.class);
            startActivity(intent);
        }
    }

    private void validateLogin() {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        try {
            dbManager.open();

            if (dbManager.findUser(username, password) && !usernameInput.getText().toString().equals("") && !passwordInput.getText().toString().equals(""))//find this user
            {
                //go to dashboard
                if (loginPreference == null)
                    loginPreference = getSharedPreferences("loginPreferences", MODE_PRIVATE);

                loginEditor = loginPreference.edit();
                loginEditor.putString("status", "true");
                loginEditor.putString("Username", username);
                loginEditor.commit();

                Intent intent = new Intent(Login.this, Dashboard.class);
                intent.getStringExtra(username);
                startActivity(intent);
                dbManager.close();
                finish();
            } else {
                error_message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                error_message.setText("User not found");
                dbManager.close();
            }

        } catch (SQLDataException e) {
            e.printStackTrace();
        }

    }

    private void getAllViews() {
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        error_message = findViewById(R.id.login_error);
    }

    public void CheckLoginStatus() {
        if (loginPreference == null)
            loginPreference = getSharedPreferences("loginPreferences", MODE_PRIVATE);

        boolean isLogged = Boolean.parseBoolean(loginPreference.getString("status", "false"));
        username = loginPreference.getString("Username", "");
        if (isLogged) {
            Intent intent = new Intent(Login.this, Dashboard.class);
            //intent.getStringExtra(username);
            startActivity(intent);
            finish();

        }
    }
}