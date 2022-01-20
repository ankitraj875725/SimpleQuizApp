package com.example.simplequizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.SQLDataException;
import java.sql.SQLException;

public class DatabaseManager {

    QuizDatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context context)
    {
        this.context = context;
    }


    public void close()
    {
        dbHelper.close();
    }
    public DatabaseManager open() throws SQLDataException
    {
        dbHelper = new QuizDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return  this;
    }

    public void insertUser(String username, String email, String password, String isAdmin, String adminPassword)
    {
        ContentValues values = new ContentValues();
        values.put(dbHelper.USER,username);
        values.put(dbHelper.EMAIL,email);
        values.put(dbHelper.PASSWORD,password);
        values.put(dbHelper.ADMIN_PASSWORD,adminPassword);

        database.insert(dbHelper.LOGIN_TABLE,null, values);
    }
    public boolean findUser(String username, String password) {
        String[] cols = new String[]{dbHelper.USER,dbHelper.PASSWORD};
        try {
            Cursor cur = database.query(dbHelper.LOGIN_TABLE,cols,dbHelper.USER+"=\""+username+"\" AND "+dbHelper.PASSWORD+"=\""+password+"\"",null,null,null,null);
            cur.moveToFirst();
            if(cur.getCount()==0 || cur==null) {
                Log.i("USER CHECK :","USer doesnt EXIST");
                return false;
            }
            else {
                Log.i("USER CHECK :","USer DOES REALLY EXIST");
                return true;
            }
        }catch (SQLiteException e)
        {
            e.printStackTrace();
            return false;
        }
    }public boolean findUser(String username) {
        String[] cols = new String[]{dbHelper.USER};
        try {
            Cursor cur = database.query(dbHelper.LOGIN_TABLE,cols,dbHelper.USER+"=\""+username+"\"",null,null,null,null);
            cur.moveToFirst();
            if(cur.getCount()==0 || cur==null) {
                Log.i("USER CHECK :","USer does not EXIST");
                return false;
            }
            else {
                Log.i("USER CHECK :","USer DOES REALLY EXIST");
                return true;
            }
        }catch (SQLiteException e)
        {
            Log.i("USER CHECK :","USer doesnt EXIST");
            e.printStackTrace();
            return false;
        }
    }
    public void deleteUser(String where)
    {
        database.delete(dbHelper.LOGIN_TABLE, where,null);
    }
    public void insertQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctAns, String topic )
    {
        ContentValues values = new ContentValues();
        values.put(dbHelper.QUESTION_COL,question);
        values.put(dbHelper.TOPIC,topic);
        values.put(dbHelper.A_COL,optionA);
        values.put(dbHelper.B_COL,optionB);
        values.put(dbHelper.C_COL,optionC);
        values.put(dbHelper.D_COL,optionD);
        values.put(dbHelper.CORRECT_COL,correctAns);


        database.insert(dbHelper.TABLE_NAME,null, values);
    }
    public void deleteQuestion(String questionWhere)
    {
        database.delete(dbHelper.TABLE_NAME,questionWhere,null);
    }
    public void dropTable(String tableName)
    {
        String query = "DROP TABLE IF EXISTS "+tableName;
        database.execSQL(query);
    }

    public Cursor fetchQuiz(String topic)
    {
        Cursor cursor =
        database.query(dbHelper.TABLE_NAME,null,dbHelper.TOPIC+"=\""+topic+"\"",null,null,null,null);
        return cursor;
    }
}
