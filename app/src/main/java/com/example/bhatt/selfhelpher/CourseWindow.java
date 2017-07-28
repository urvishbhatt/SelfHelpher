package com.example.bhatt.selfhelpher;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;
import com.example.bhatt.selfhelpher.UserDatabase.UserDataUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

public class CourseWindow extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {


    static boolean issecondfragment = false;
    Videolistfragment fragment1;
    Toolbar toolbar;
    private String plylistid, subject;
    private double Course_id;
    private Button buttn;
    private boolean DATA_IN_DATABASE = false;
    private String INTENT_TAG;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(CourseWindow.this, FirstWindows.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_window);

        toolbar = (Toolbar) findViewById(R.id.course_windows_appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buttn = (Button) findViewById(R.id.Enroll_button);
        buttn.setEnabled(true);
        buttn.setVisibility(View.VISIBLE);

        INTENT_TAG = getIntent().getStringExtra("INTENT_TAG");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (findViewById(R.id.fragment2) != null) {

            issecondfragment = true;
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (INTENT_TAG.equals("MainWindow")) {

                    Intent intent = new Intent(CourseWindow.this, FirstWindows.class);
                    startActivity(intent);

                } else {


                    finish();
                }

            }
        });


        if (INTENT_TAG.equals("MainWindow")) {

            plylistid = getIntent().getStringExtra("plylistid");

            buttn.setText(getResources().getText(R.string.cancel_enrollment));

            buttn.setEnabled(true);
            buttn.setVisibility(View.VISIBLE);


        } else {

            plylistid = getIntent().getStringExtra("plylistid");
            subject = getIntent().getStringExtra("subject");
            Course_id = getIntent().getDoubleExtra("Course_id", 0.0);


//            checkindatabse();
//            IToast();
            getLoaderManager().initLoader(4, null, this);
        }


        final Intent intent = new Intent(CourseWindow.this, FirstWindows.class);


        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(CourseWindow.this, R.anim.blink);
                buttn.startAnimation(animation);

                if ((buttn.getText().equals(getResources().getString(R.string.enroll)))) {


                    ContentValues values = new ContentValues();

                    values.put(UserContract.UserEntry.SUBJECT, subject);
                    values.put(UserContract.UserEntry.COURSEID, Course_id);
                    values.put(UserContract.UserEntry.PLAYLIST, plylistid);

                    getContentResolver().insert(UserContract.UserEntry.CONTENT_URL, values);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Enrollments");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, subject);
                    bundle.putDouble(FirebaseAnalytics.Param.INDEX, Course_id);
                    bundle.putString(FirebaseAnalytics.Param.CONTENT, plylistid);
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    startActivity(intent);

                } else {


                    String DELETEQUERY = UserContract.UserEntry.COURSEID + "=?";

                    new UserDataUtil(plylistid);

                    String[] selectionArgs = {plylistid};

                    int i = getContentResolver().delete(UserContract.UserEntry.CONTENT_URL, DELETEQUERY, selectionArgs);

                    if (i != 0) {

                        Toast.makeText(CourseWindow.this, getResources().getString(R.string.successfully_enrolled), Toast.LENGTH_SHORT).show();
                        buttn.setText(getResources().getText(R.string.enroll));
                    } else {
                        Toast.makeText(CourseWindow.this, getResources().getString(R.string.some_problem_occur), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        /**************************************************/

        fragment1 = new Videolistfragment();
        getFragmentManager().beginTransaction().add(R.id.fragment1, fragment1).commit();

        /*****************************************************/

    }

    private void IToast() {

        if (DATA_IN_DATABASE) {

            Toast.makeText(CourseWindow.this, getResources().getText(R.string.This_Course_is_already_in_database), Toast.LENGTH_SHORT).show();

        }
    }

    /*********************************************************************************************/
//    private void checkindatabse() {
//
//        String[] project = {UserContract.UserEntry.COURSEID};
//
//        Cursor cursor = getContentResolver().query(
//                UserContract.UserEntry.CONTENT_URL,
//                project,
//                null,
//                null,
//                null
//        );
//
//
//        int count = cursor.getCount();
//
//        if (count == 0) {
//
//            DATA_IN_DATABASE = false;
//
//        } else {
//
//            try {
//                int a = cursor.getColumnIndex(UserContract.UserEntry.COURSEID);
//
//                while (cursor.moveToNext()) {
//
//                    double course_id = cursor.getDouble(a);
//
//                    if (course_id == Course_id) {
//
//                        DATA_IN_DATABASE = true;
//                        buttn.setEnabled(false);
//                        buttn.setVisibility(View.GONE);
//
//                        break;
//                    }
//                }
//            } finally {
//
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//
//        }
//    }

    /************************************************************************************************/

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] project = {UserContract.UserEntry.COURSEID};

        return new CursorLoader(
                getApplicationContext(),
                UserContract.UserEntry.CONTENT_URL,
                project,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        int count = cursor.getCount();

        if (count == 0) {

            DATA_IN_DATABASE = false;

        } else {

            try {
                int a = cursor.getColumnIndex(UserContract.UserEntry.COURSEID);

                while (cursor.moveToNext()) {

                    double course_id = cursor.getDouble(a);

                    if (course_id == Course_id) {

                        DATA_IN_DATABASE = true;
                        buttn.setEnabled(false);
                        buttn.setVisibility(View.GONE);

                        break;
                    }
                }
            } finally {

                IToast();
            }

        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }


    /***********************************************************************************************/

}

