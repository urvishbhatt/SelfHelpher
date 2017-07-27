package com.example.bhatt.selfhelpher;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.preference.TwoStatePreference;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;
import com.example.bhatt.selfhelpher.dataprovider.dataprovider;

public class SubSelect extends Fragment {

    private TextView healthtextView,wealthtextView,lovetextView,happinesstextView;
    private Intent intent;

    private View fragment1;

    Animation animation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment1 = inflater.inflate(R.layout.sub_select,container,false);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            animation = AnimationUtils.loadAnimation(getContext(),R.anim.slide_up);
        }


        String[] project = {
                CourseContract.CourseEntry.SUBJECT,
                CourseContract.CourseEntry.COURSEID,
                CourseContract.CourseEntry.PLAYLIST };


        Cursor cursor = getActivity().getContentResolver().query(
                CourseContract.CourseEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );




        int count = cursor.getCount();

        Toast.makeText(getActivity(), String.valueOf(count),Toast.LENGTH_SHORT).show();

        if (count == 0){

            cursor.close();
            EnterDatabase();

        }else {

            cursor.close();
        }

        intent = new Intent(getActivity(),Courselib.class);

        healthtextView = (TextView)fragment1.findViewById(R.id.health_textview);
        wealthtextView = (TextView)fragment1.findViewById(R.id.wealth_textview);
        lovetextView = (TextView)fragment1.findViewById(R.id.love_textview);
        happinesstextView = (TextView)fragment1.findViewById(R.id.happiness_textview);


        healthtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("subject","health");
                intent.putExtra("subjectno",1);

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

                intent.putExtra("subject","wealth");
                intent.putExtra("subjectno",2);

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

                intent.putExtra("subject","love");
                intent.putExtra("subjectno",3);

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
                intent.putExtra("subjectno",4);

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

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

                case "wealth" :
                    for (int j=0;wealthcourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,wealthcourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,wealthplalist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",wealthplalist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

                case "love" :
                    for (int j=0;lovecourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,lovecourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,loveplaylist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",loveplaylist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

                case "happiness" :
                    for (int j=0;happinesscourseid.length>j;j++){

                        values.put(CourseContract.CourseEntry.SUBJECT,coursename[i]);
                        values.put(CourseContract.CourseEntry.COURSEID,happinesscourseid[j]);
                        values.put(CourseContract.CourseEntry.PLAYLIST,happinessplaylist[j]);

                        Log.e("DATABASE",coursename[i]);
                        Log.e("DATABASE",happinessplaylist[j]);

                        getActivity().getContentResolver().insert(CourseContract.CourseEntry.CONTENT_URL,values);

                    }
                    break;

            }

        }

    }
}
