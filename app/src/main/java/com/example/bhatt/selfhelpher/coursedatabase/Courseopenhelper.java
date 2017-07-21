package com.example.bhatt.selfhelpher.coursedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.sql.Types.INTEGER;

/**
 * Created by bhatt on 20-07-2017.
 */

public class Courseopenhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "course.db";
    public static final int DATABASE_VERSION = 1;

    public Courseopenhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_QUERY = "CREATE TABLE "
                        + CourseContract.CourseEntry.TABLE_NAME + "("
                        + CourseContract.CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + CourseContract.CourseEntry.SUBJECT + " TEXT, "
                        + CourseContract.CourseEntry.COURSEID + " FLOAT, "
                        + CourseContract.CourseEntry.PLAYLIST + " TEXT);" ;

        db.execSQL(SQL_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
