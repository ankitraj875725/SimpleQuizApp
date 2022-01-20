package com.example.simplequizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "quiz.db";
    public static int DB_VERSION = 1;

    public static String TABLE_NAME = "Questions";
    public static String QUESTION_COL = "question";
    public static String TOPIC = "topic",A_COL = "option_a",B_COL = "option_b",C_COL = "option_c",D_COL = "option_d",CORRECT_COL = "correct_ans";

    public static String LOGIN_TABLE = "Login";
    public static String USER = "username";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String LOGIN_TYPE = "login_type";
    public static String ADMIN_PASSWORD = "admin_password";

    public static String CREATE_Q_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+QUESTION_COL+
            " TEXT,"+TOPIC+" TEXT, "+A_COL+" TEXT, "+B_COL+" TEXT, "+C_COL+" TEXT, "+D_COL+" TEXT, "+CORRECT_COL+" TEXT)";

    public static String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS "+LOGIN_TABLE+ "("+USER+
            " TEXT, "+EMAIL+" TEXT PRIMARY KEY,"+PASSWORD+" TEXT, "+LOGIN_TYPE+" TEXT DEFAULT 'admin',"+ADMIN_PASSWORD+" TEXT)";

    public QuizDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Q_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+LOGIN_TABLE);
    }
}
