package com.example.bhatt.selfhelpher;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;





public class FirstWindows extends AppCompatActivity {

    SubSelect subSelect;
    MainWindow mainwindow;

    private Boolean IS_IN_DATABASE;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_windows);

        toolbar = (Toolbar)findViewById(R.id.custometool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));


        if (isNetworkStatusAvialable(getApplicationContext())){

            if (getIntent().getExtras() != null){

                String intent = getIntent().getExtras().getString("INTENT_TAG","NotfromIntent");

                Log.e("TAG_LOG_INTENT",getIntent().getExtras().getString("INTENT_TAG",intent));

                if (intent.equals("fromsetting")){


                    getSupportActionBar().hide();

                    subSelect = new SubSelect();
                    getFragmentManager().beginTransaction().add(R.id.sub_select_fragment1,subSelect).commit();


                }else {

                    if (savedInstanceState == null){
                        mainwindow = new MainWindow();
                        getFragmentManager().beginTransaction().add(R.id.mainwindow_fragment2,mainwindow).commit();
                    }

                }
            }else {

                checkindatabse();

                if (IS_IN_DATABASE){



                    if (savedInstanceState == null){
                        mainwindow = new MainWindow();
                        getFragmentManager().beginTransaction().add(R.id.mainwindow_fragment2,mainwindow).commit();
                    }
                }else {


                    getSupportActionBar().hide();


                    subSelect = new SubSelect();
                    getFragmentManager().beginTransaction().add(R.id.sub_select_fragment1,subSelect).commit();

                }
            }

        }else {
            Toast.makeText(FirstWindows.this,getResources().getString(R.string.nointenet_message),Toast.LENGTH_LONG).show();
        }


    }

    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
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


                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
