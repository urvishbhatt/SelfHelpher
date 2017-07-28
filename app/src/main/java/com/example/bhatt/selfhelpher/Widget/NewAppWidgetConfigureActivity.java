package com.example.bhatt.selfhelpher.Widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.bhatt.selfhelpher.R;
import com.example.bhatt.selfhelpher.UserDatabase.UserContract;
import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;


/**
 * The configuration screen for the {@link NewAppWidget NewAppWidget} AppWidget.
 */
public class NewAppWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.example.bhatt.selfhelpher.Widget.NewAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    static int course_total, user_total;
    static int course_health, user_health;
    static int course_wealth, user_wealth;
    static int course_love, user_love;
    static int course_happiness, user_happiness;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = NewAppWidgetConfigureActivity.this;


            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            NewAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };


    public NewAppWidgetConfigureActivity() {
        super();
    }

    public static String getTotal() {
        return String.valueOf(user_total) + "/" + String.valueOf(course_total);
    }

    public static String getHelth() {
        return "health:-" + String.valueOf(user_health) + "/" + String.valueOf(course_health);
    }

    public static String getWealth() {
        return "wealth:-" + String.valueOf(user_wealth) + "/" + String.valueOf(course_wealth);
    }

    public static String getLove() {
        return "love:-" + String.valueOf(user_love) + "/" + String.valueOf(course_love);
    }

    public static String getHappiness() {
        return "happiness:-" + String.valueOf(user_happiness) + "/" + String.valueOf(course_happiness);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.new_app_widget_configure);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);


        CourseCounter();
        UserCounter();


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }


    }

    private void CourseCounter() {

        String[] project = {
                CourseContract.CourseEntry.SUBJECT};


        Cursor cursor = getContentResolver().query(
                CourseContract.CourseEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );

        try {
            int a = cursor.getColumnIndex(CourseContract.CourseEntry.SUBJECT);


            while (cursor.moveToNext()) {

                String name = cursor.getString(a);

                switch (name) {
                    case "health":
                        course_health++;
                        break;

                    case "wealth":
                        course_wealth++;
                        break;

                    case "love":
                        course_love++;
                        break;

                    case "happiness":
                        course_happiness++;
                        break;
                }
            }

        } finally {

            course_total = course_health + course_wealth + course_love + course_happiness;
            cursor.close();

        }


    }

    private void UserCounter() {

        String[] project = {
                UserContract.UserEntry.SUBJECT};


        Cursor cursor = getContentResolver().query(
                UserContract.UserEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );

        try {
            int a = cursor.getColumnIndex(UserContract.UserEntry.SUBJECT);


            while (cursor.moveToNext()) {

                String name = cursor.getString(a);

                switch (name) {
                    case "health":
                        user_health++;
                        break;

                    case "wealth":
                        user_wealth++;
                        break;

                    case "love":
                        user_love++;
                        break;

                    case "happiness":
                        user_happiness++;
                        break;
                }
            }

        } finally {

            user_total = user_health + user_wealth + user_love + user_happiness;
            cursor.close();

        }


    }
}