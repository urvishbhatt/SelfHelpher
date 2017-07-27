package com.example.bhatt.selfhelpher.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.bhatt.selfhelpher.R;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewAppWidgetConfigureActivity NewAppWidgetConfigureActivity}
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        CharSequence Total = NewAppWidgetConfigureActivity.getTotal();
        CharSequence health = NewAppWidgetConfigureActivity.getHelth();
        CharSequence wealth = NewAppWidgetConfigureActivity.getWealth();
        CharSequence love = NewAppWidgetConfigureActivity.getLove();
        CharSequence happiness = NewAppWidgetConfigureActivity.getHappiness();

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.widget_total, Total);
        views.setTextViewText(R.id.health_widget, health);
        views.setTextViewText(R.id.wealth_widget, wealth);
        views.setTextViewText(R.id.love_widget, love);
        views.setTextViewText(R.id.happiness_widget, happiness);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

