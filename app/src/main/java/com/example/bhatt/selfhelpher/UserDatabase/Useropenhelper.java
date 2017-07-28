package com.example.bhatt.selfhelpher.UserDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bhatt on 23-07-2017.
 */

public class Useropenhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final int DATABASE_VERSION = 1;

    public Useropenhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_QUERY = "CREATE TABLE "
                + UserContract.UserEntry.TABLE_NAME + "("
                + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserContract.UserEntry.SUBJECT + " TEXT, "
                + UserContract.UserEntry.COURSEID + " FLOAT, "
                + UserContract.UserEntry.PLAYLIST + " TEXT);";

        db.execSQL(SQL_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
