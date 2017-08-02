package com.asllani94.quizitup;

import android.database.sqlite.SQLiteDatabase;
;
import java.util.LinkedList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;


public class QuestionOperations {

    // Database fields
    private DataBaseHelper dbHelper;
    private String[] STUDENT_TABLE_COLUMNS = { DataBaseHelper.Question_ID, DataBaseHelper.QUESTION_NAME,DataBaseHelper.CATEGORY,DataBaseHelper.ANSWER_NAME };


    private SQLiteDatabase database;

    public QuestionOperations(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addQuestion(String question, int category, int answer) {

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.QUESTION_NAME, question);
        values.put(DataBaseHelper.CATEGORY, category);
        values.put(DataBaseHelper.ANSWER_NAME, answer);


        database.insert(DataBaseHelper.QUESTIONS, null, values);




    }



    public LinkedList<Question> getAllStudents() {

        LinkedList<Question> questions=new LinkedList<>();

        Cursor cursor = database.query(DataBaseHelper.QUESTIONS,
                STUDENT_TABLE_COLUMNS, null, null, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question quest = parseStudent(cursor);
            questions.add(quest);
            cursor.moveToNext();
        }

        cursor.close();
        return questions;
    }
    public LinkedList<Question> getQuestionByCategory(int category) {

        LinkedList<Question> questions=new LinkedList<>();
        String sqlQuery = "select * from questions where Category='"+category+"';";



        Cursor cursor = database.rawQuery(sqlQuery,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question quest = parseStudent(cursor);
            questions.add(quest);
            cursor.moveToNext();
        }

        cursor.close();
        return questions;
    }

    private Question parseStudent(Cursor cursor) {
        Question student = new Question();
        student.setId((cursor.getInt(0)));
        student.setQuestion(cursor.getString(1));
        student.setCategory(cursor.getInt(2));
        student.setAnswer(cursor.getInt(3));
        student.setIsChecked(false);

        return student;
    }
}