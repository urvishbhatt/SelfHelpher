package com.example.bhatt.selfhelpher;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class FirstWindows extends AppCompatActivity {

    SubSelect subSelect;
    MainWindow mainwindow;

    private Boolean IS_IN_DATABASE;

    private Menu menu;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_windows);



        if (getIntent().getExtras() != null){

            String intent = getIntent().getExtras().getString("INTENT_TAG","NotfromIntent");

            Log.e("TAG_LOG_INTENT",getIntent().getExtras().getString("INTENT_TAG",intent));

            if (intent.equals("fromsetting")){

                Toast.makeText(FirstWindows.this,"from setting",Toast.LENGTH_SHORT).show();
                subSelect = new SubSelect();
                getFragmentManager().beginTransaction().add(R.id.sub_select_fragment1,subSelect).commit();
            }else {
                mainwindow = new MainWindow();
                getFragmentManager().beginTransaction().add(R.id.mainwindow_fragment2,mainwindow).commit();
            }
        }else {

            checkindatabse();

            if (IS_IN_DATABASE){

                Toast.makeText(FirstWindows.this,"in database",Toast.LENGTH_SHORT).show();

                mainwindow = new MainWindow();
                getFragmentManager().beginTransaction().add(R.id.mainwindow_fragment2,mainwindow).commit();

            }else {

                Toast.makeText(FirstWindows.this,"Not in database",Toast.LENGTH_SHORT).show();

                subSelect = new SubSelect();
                getFragmentManager().beginTransaction().add(R.id.sub_select_fragment1,subSelect).commit();

            }
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

            IS_IN_DATABASE = false;

        }else {

            IS_IN_DATABASE = true;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainwindow,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.setting:

                Intent intent = new Intent(FirstWindows.this,Setting.class);

                intent.putExtra("INTENT__TAG","setting");

                Toast.makeText(FirstWindows.this,"setting click",Toast.LENGTH_SHORT).show();

                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
