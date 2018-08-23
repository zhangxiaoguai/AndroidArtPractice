package com.ericzhang.androidartpractice.chapter5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ericzhang.androidartpractice.R;

public class MyAppwidgetProvider extends AppWidgetProvider {

    public static final String TAG = MyAppwidgetProvider.class.getSimpleName();
    public static final String CLICK_ACTION = "com.ericzhang.androidartpractice.chapter5.action.CLICK";

    public MyAppwidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive: action = " + intent.getAction());
        if (CLICK_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "click it", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon1);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.imageView1, rotateBitmap(srcBitmap, degree));
                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
                        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        appWidgetManager.updateAppWidget(new ComponentName(context, MyAppwidgetProvider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    /**
     * 每次桌面小部件更新时都会调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.e(TAG, "onUpdate");
        int counter = appWidgetIds.length;
        Log.e(TAG, "counter: " + counter);
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    /**
     * 桌面小部件更新
     */
    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.e(TAG, "onWidgetUpdate:  appWidgetId = " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Bitmap scrBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(scrBitmap, 0, 0, scrBitmap.getWidth(), scrBitmap.getHeight(), matrix, true);
        return tmpBitmap;
    }

}
