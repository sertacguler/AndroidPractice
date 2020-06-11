package com.example.mycroft.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mycroft on 22.3.2018.
 */

public class DbHalper extends SQLiteOpenHelper {

    private static final String DB_NAME="ASDF";
    private static final String DB_TABLE="Raad";
    private static final String DB_COLUMN="RaadName";
    private static final int DB_VER = 1;

    public DbHalper(Context context){
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query  = String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",DB_TABLE,DB_COLUMN);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXIST %s ",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(String task)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);
        db.insertWithOnConflict(DB_TABLE,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateTask(String oldTask, String newTask)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, newTask);
        db.update(DB_TABLE, values, DB_COLUMN + " = ?", new String[]{oldTask});
        db.close();
    }

    public void deleteTask(String task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " = ?", new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList()
    {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUMN},null,null,null,null,null );
        while (cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
}