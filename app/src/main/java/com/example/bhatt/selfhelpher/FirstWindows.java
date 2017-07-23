package com.example.bhatt.selfhelpher;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;

public class FirstWindows extends AppCompatActivity {

    SubSelect subSelect;
    MainWindow mainwindow;

    private Boolean IS_IN_DATABASE;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_windows);


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

                Toast.makeText(FirstWindows.this,"setting click",Toast.LENGTH_SHORT).show();

                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
