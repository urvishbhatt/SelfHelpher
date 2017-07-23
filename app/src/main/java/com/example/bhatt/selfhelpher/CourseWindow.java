package com.example.bhatt.selfhelpher;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;
import com.example.bhatt.selfhelpher.UserDatabase.Useropenhelper;
import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;

public class CourseWindow extends AppCompatActivity {



    Videolistfragment fragment1;

    private String plylistid,title;
    private double Course_id;

    private Button buttn;

    private boolean DATA_IN_DATABASE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_window);

        buttn = (Button)findViewById(R.id.Enroll_button);
        buttn.setEnabled(true);
        buttn.setVisibility(View.VISIBLE);

        plylistid = getIntent().getStringExtra("plylistid");
        title = getIntent().getStringExtra("title");
        Course_id = getIntent().getDoubleExtra("Course_id",0.0);


        /*****************************************/
        checkindatabse();
        IToast();
        /*****************************************/


        final Intent intent = new Intent(CourseWindow.this,MainWindow.class);


        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(CourseWindow.this,"Buttonclick",Toast.LENGTH_SHORT).show();

                ContentValues values = new ContentValues();

                values.put(UserContract.UserEntry.SUBJECT,title);
                values.put(UserContract.UserEntry.COURSEID,Course_id);
                values.put(UserContract.UserEntry.PLAYLIST,plylistid);

                getContentResolver().insert(UserContract.UserEntry.CONTENT_URL,values);

                Toast.makeText(CourseWindow.this,"Enter into database",Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }
        });


        /**************************************************/


        fragment1 = new Videolistfragment();
        getFragmentManager().beginTransaction().add(R.id.fragment1,fragment1).commit();


        /*****************************************************/

    }

    private void IToast() {

        if (DATA_IN_DATABASE){

            Toast.makeText(CourseWindow.this,"in database",Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(CourseWindow.this,"Not in database",Toast.LENGTH_SHORT).show();

        }
    }

    private void checkindatabse() {

        String[] project = { UserContract.UserEntry.COURSEID };

        Cursor cursor = getContentResolver().query(
                UserContract.UserEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );


        int count = cursor.getCount();

        if (count == 0){

            DATA_IN_DATABASE = false;

        }else {

            try{
                int a = cursor.getColumnIndex(UserContract.UserEntry.COURSEID);

                while(cursor.moveToNext()){

                    double course_id = cursor.getDouble(a);

                    if (course_id == Course_id){

                        DATA_IN_DATABASE = true;
                        buttn.setEnabled(false);
                        buttn.setVisibility(View.GONE);


                        break;
                    }
                }
            }

            finally {

                if (cursor != null){
                    cursor.close();
                }
            }

        }


    }


}
