package com.example.bhatt.selfhelpher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        textView = (TextView) findViewById(R.id.add_course);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Setting.this, FirstWindows.class);
                intent.putExtra("INTENT_TAG", "fromsetting");
                startActivity(intent);

            }
        });


    }
}
