package com.asllani94.quizitup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

/**
 * Created by arnold on 15-10-03.
 */
public class AchievmentOperations {

    private DataBaseHelper dbHelper;
   // private String[] STUDENT_TABLE_COLUMNS = { DataBaseHelper.Question_ID, DataBaseHelper.QUESTION_NAME,DataBaseHelper.CATEGORY,DataBaseHelper.ANSWER_NAME };
   private String[] ACHIEVE_TABLE_COLUMNS = { DataBaseHelper.ACHIEVE_ID, DataBaseHelper.LEVEL_ID,DataBaseHelper.STATUS };


    private SQLiteDatabase database;

    public AchievmentOperations(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addAchievment(int Level, int Status) {

        ContentValues values = new ContentValues();


        values.put(dbHelper.LEVEL_ID, Level);
        values.put(dbHelper.STATUS, Status);


        database.insert(dbHelper.ACHIEVE, null, values);

    }




    public boolean  isLevelOpen(int level) {
        String sqlQuery = "select * from achieve where LevelID='"+level+"';";

        Cursor cursor = database.rawQuery(sqlQuery,null);
Achievment ach=new Achievment();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
             ach = parseAchievment(cursor);

            cursor.moveToNext();
        }



        cursor.close();
        if (ach.getStatus()==1)
            return  true;
        else
        return  false;

    }
    private Achievment parseAchievment(Cursor cursor) {
        Achievment student = new Achievment();
        student.setID((cursor.getInt(0)));
        student.setLevelID(cursor.getInt(1));
        student.setStatus(cursor.getInt(2));

        return student;
    }



}
