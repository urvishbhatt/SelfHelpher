package com.example.bhatt.selfhelpher;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.preference.TwoStatePreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;
import com.example.bhatt.selfhelpher.dataprovider.dataprovider;

public class SubSelect extends AppCompatActivity {

    private TextView healthtextView,wealthtextView,lovetextView,happinesstextView;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_select);


        String[] project = {
                CourseContract.CourseEntry.SUBJECT,
                CourseContract.CourseEntry.COURSEID,
                CourseContract.CourseEntry.PLAYLIST };


        Cursor cursor = getContentResolver().query(
                CourseContract.CourseEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );


        int count = cursor.getCount();

        Toast.makeText(this, String.valueOf(count),Toast.LENGTH_SHORT).show();

        if (count == 0){

            cursor.close();
            EnterDatabase();

        }else {

            cursor.close();
        }




        intent = new Intent(SubSelect.this,Courselib.class);

        healthtextView = (TextView)findViewById(R.id.health_textview);
        wealthtextView = (TextView)findViewById(R.id.wealth_textview);
        lovetextView = (TextView)findViewById(R.id.love_textview);
        happinesstextView = (TextView)findViewById(R.id.happiness_textview);


        healthtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("title","health");
                intent.putExtra("subjectno",1);

                startActivity(intent);
            }
        });

        wealthtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("title","wealth");
                intent.putExtra("subjectno",2);

                startActivity(intent);
            }
        });

        lovetextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("title","love");
                intent.putExtra("subjectno",3);

                startActivity(intent);
            }
        });

        happinesstextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("title", "happiness");
                intent.putExtra("subjectno",4);

                startActivity(intent);
            }
        });


    }

    private void EnterDatabase() {

        String[] coursename = dataprovider.coursename;

        double[] helthcourseid = dataprovider.helthcourseid;
        String[] helthplaylist = dataprovider.helthplaylist;

        double[] wealthcourseid = dataprovider.wealthcourseid;
        String[] wealthplalist = dataprovider.wealthplalist;

        double[] lovecourseid = dataprovider.lovecourseid;
        String[] loveplaylist = dataprovider.loveplaylist;

        double[] happinesscourseid = dataprovider.happinesscourseid;
        String[] happinessplaylist = dataprovider.happinessplaylist;


        ContentValues values = new ContentValues();

        for (int i=0;coursename.length>i;i++){

            switch (coursename[i]){

                case "health" :

                    for (int j=0;helthcourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,helthcourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,helthplaylist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",helthplaylist[j]);

                        getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

                case "wealth" :
                    for (int j=0;wealthcourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,wealthcourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,wealthplalist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",wealthplalist[j]);

                        getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

                case "love" :
                    for (int j=0;lovecourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,lovecourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,loveplaylist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",loveplaylist[j]);

                        getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

                case "happiness" :
                    for (int j=0;happinesscourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,happinesscourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,happinessplaylist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",happinessplaylist[j]);

                        getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

            }

        }

    }
}
