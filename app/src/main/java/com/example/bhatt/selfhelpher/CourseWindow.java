package com.example.bhatt.selfhelpher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CourseWindow extends AppCompatActivity {



    Videolistfragment fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_window);


        fragment1 = new Videolistfragment();
        getFragmentManager().beginTransaction().add(R.id.fragment1,fragment1).commit();

    }
}
