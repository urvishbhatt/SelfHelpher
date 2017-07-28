package com.example.bhatt.selfhelpher.UserDatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by bhatt on 23-07-2017.
 */

public class UserContentProvider extends ContentProvider {

    public static final String LOG_TAG = UserContentProvider.class.getSimpleName();
    private static final int USER = 100;
    private static final int USER_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.USER_COURSE, USER);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.USER_COURSE + "/#", USER_ID);
    }

    public String[] DELETEQUERY = {UserContract.UserEntry.COURSEID + " = "};
    private Useropenhelper useropenhelper;

    @Override
    public boolean onCreate() {

        useropenhelper = new Useropenhelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = useropenhelper.getReadableDatabase();

        Cursor cursor = null;

        int match = sUriMatcher.match(uri);

        switch (match) {

            case USER:

                cursor = database.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case USER_ID:

                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USER:
                return insertMovie(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMovie(Uri uri, ContentValues contentValues) {

        SQLiteDatabase database = useropenhelper.getReadableDatabase();

        long id = database.insert(UserContract.UserEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Faild to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = useropenhelper.getWritableDatabase();

//        String[] FINAL_DELETE_QUERY = DELETEQUERY+selectionArgs;

        selection = UserContract.UserEntry.COURSEID + "=?";
//        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

        selection = UserContract.UserEntry.PLAYLIST + "=?";
//                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
        selectionArgs = new String[]{UserDataUtil.getYoutubeplaylist()};

        Log.e("selection", selection);
        Log.e("selectionArgs", selectionArgs[0]);

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USER:
                // Delete all rows that match the selection and selection args

                Log.e("selection", selection);
                Log.e("selectionArgs", selectionArgs[0]);


                return database.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
            case USER_ID:
                // Delete a single row given by the ID in the URI
                selection = UserContract.UserEntry.PLAYLIST + "=?";
//                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                selectionArgs = new String[]{UserDataUtil.getYoutubeplaylist()};

                Log.e("selection", selection);
                Log.e("selectionArgs", selectionArgs[0]);

                return database.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

//        return 0;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
