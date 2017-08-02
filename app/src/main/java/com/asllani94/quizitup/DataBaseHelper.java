package com.asllani94.quizitup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;



public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String QUESTIONS = "questions";
    public  static  final String ACHIEVE="achieve";
    public static final String ACHIEVE_ID = "AchieveID";
    public static final String LEVEL_ID = "LevelID";
    public static final String STATUS = "Status";


    public static final String Question_ID = "QuestionID";
    public static final String CATEGORY = "Category";
    public static final String QUESTION_NAME = "Question";
    public static final String ANSWER_NAME = "Answer";
    private static final String DATABASE_NAME = "lakrori.db";
    private static final int DATABASE_VERSION = 1;

// creation SQLite statement
private static final String DATABASE_CREATE = "create table " + QUESTIONS
        + "(" + Question_ID + " integer primary key autoincrement, "
        + QUESTION_NAME + " text not null, "+CATEGORY+" integer , "+ANSWER_NAME+" integer"+
        ");";

    public DataBaseHelper(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you should do some logging in here
        // ..

        db.execSQL("DROP TABLE IF EXISTS " + QUESTIONS);
        onCreate(db);
    }

}