package com.example.bhatt.selfhelpher;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;
import com.example.bhatt.selfhelpher.dataprovider.Dataprovider;

public class SubSelect extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    Animation animation;
    String[] project;
    int count;
    private TextView healthtextView, wealthtextView, lovetextView, happinesstextView;
    private Intent intent;
    private View fragment1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment1 = inflater.inflate(R.layout.sub_select, container, false);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        }


        getLoaderManager().initLoader(1, null, this);


        intent = new Intent(getActivity(), Courselib.class);

        healthtextView = (TextView) fragment1.findViewById(R.id.health_textview);
        wealthtextView = (TextView) fragment1.findViewById(R.id.wealth_textview);
        lovetextView = (TextView) fragment1.findViewById(R.id.love_textview);
        happinesstextView = (TextView) fragment1.findViewById(R.id.happiness_textview);


        healthtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("subject", "health");
                intent.putExtra("subjectno", 1);

                healthtextView.startAnimation(animation);
                lovetextView.setAnimation(animation);
                wealthtextView.startAnimation(animation);
                happinesstextView.setAnimation(animation);
                startActivity(intent);
            }
        });

        wealthtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("subject", "wealth");
                intent.putExtra("subjectno", 2);

                healthtextView.startAnimation(animation);
                lovetextView.setAnimation(animation);
                wealthtextView.startAnimation(animation);
                happinesstextView.setAnimation(animation);
                startActivity(intent);
            }
        });

        lovetextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("subject", "love");
                intent.putExtra("subjectno", 3);

                healthtextView.startAnimation(animation);
                lovetextView.setAnimation(animation);
                wealthtextView.startAnimation(animation);
                happinesstextView.setAnimation(animation);
                startActivity(intent);
            }
        });

        happinesstextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("subject", "happiness");
                intent.putExtra("subjectno", 4);

                healthtextView.startAnimation(animation);
                lovetextView.setAnimation(animation);
                wealthtextView.startAnimation(animation);
                happinesstextView.setAnimation(animation);
                startActivity(intent);
            }
        });


        return fragment1;
    }


    private void EnterDatabase() {

        String[] coursename = Dataprovider.coursename;

        double[] helthcourseid = Dataprovider.helthcourseid;
        String[] helthplaylist = Dataprovider.helthplaylist;

        double[] wealthcourseid = Dataprovider.wealthcourseid;
        String[] wealthplalist = Dataprovider.wealthplalist;

        double[] lovecourseid = Dataprovider.lovecourseid;
        String[] loveplaylist = Dataprovider.loveplaylist;

        double[] happinesscourseid = Dataprovider.happinesscourseid;
        String[] happinessplaylist = Dataprovider.happinessplaylist;


        ContentValues values = new ContentValues();

        for (int i = 0; coursename.length > i; i++) {

            switch (coursename[i]) {

                case "health":

                    for (int j = 0; helthcourseid.length > j; j++) {

                        values.put(CourseContract.CourseEntry.SUBJECT, coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID, helthcourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST, helthplaylist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL, values);

                    }
                    break;

                case "wealth":
                    for (int j = 0; wealthcourseid.length > j; j++) {

                        values.put(CourseContract.CourseEntry.SUBJECT, coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID, wealthcourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST, wealthplalist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL, values);

                    }
                    break;

                case "love":
                    for (int j = 0; lovecourseid.length > j; j++) {

                        values.put(CourseContract.CourseEntry.SUBJECT, coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID, lovecourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST, loveplaylist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL, values);

                    }
                    break;

                case "happiness":
                    for (int j = 0; happinesscourseid.length > j; j++) {

                        values.put(CourseContract.CourseEntry.SUBJECT, coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID, happinesscourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST, happinessplaylist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL, values);

                    }
                    break;
            }
        }
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        project = new String[]{
                CourseContract.CourseEntry.SUBJECT,
                CourseContract.CourseEntry.COURSEID,
                CourseContract.CourseEntry.PLAYLIST};

        return new CursorLoader(
                getContext(),
                CourseContract.CourseEntry.CONTENT_URL,
                project,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

        int count = data.getCount();

        Log.e("new_onLoadFinished", String.valueOf(count));

        if (count == 0) {
            EnterDatabase();
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

}
