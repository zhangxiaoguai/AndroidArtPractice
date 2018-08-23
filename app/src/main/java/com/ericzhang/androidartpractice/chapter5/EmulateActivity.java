package com.ericzhang.androidartpractice.chapter5;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.ericzhang.androidartpractice.R;

public class EmulateActivity extends Activity {

    public static final String REMOTE_ACTION = "com.ryg.chapter.action_REMOTE";
    public static final String EXTRA_REMOTE_VIEWS = "extra_remoteViews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulate);
    }

    public void generateRemoteViews(View view) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
        remoteViews.setTextViewText(R.id.msg, "message from process:" + android.os.Process.myPid());
        remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, Notification1Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, new Intent(this, Notification2Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.openActivity2, pendingIntent2);
        Intent intent = new Intent(REMOTE_ACTION);
        intent.putExtra(EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
    }
}
