package com.ericzhang.androidartpractice.chapter5;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.ericzhang.androidartpractice.R;

import static com.ericzhang.androidartpractice.chapter5.EmulateActivity.EXTRA_REMOTE_VIEWS;
import static com.ericzhang.androidartpractice.chapter5.EmulateActivity.REMOTE_ACTION;

public class Chapter5MainActivity extends Activity {

    private int count;

    private LinearLayout mRemoteViewsContent;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent.getParcelableExtra(EXTRA_REMOTE_VIEWS);
            if (remoteViews != null) {
                updateUI(remoteViews);
            }
        }
    };

    private void updateUI(RemoteViews remoteViews) {
        // 1
//        View view = remoteViews.apply(this, mRemoteViewsContent);
//        mRemoteViewsContent.addView(view);

        // 2
        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
        remoteViews.reapply(this, view);
        mRemoteViewsContent.addView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_main);
        initViews();
    }

    private void initViews() {
        mRemoteViewsContent = findViewById(R.id.remote_views_content);
        IntentFilter filter = new IntentFilter(REMOTE_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     * 正常notification用法
     */
    public void notification(View view) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mNotificationBuilder = new Notification.Builder(this);
        mNotificationBuilder.setContentTitle("第五章")
                .setContentText("系统默认通知")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("通知到来")// todo ticker没起作用
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());// todo when没起作用
        Notification notification = mNotificationBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(this, Notification1Activity.class);
        intent.putExtra("count", count++);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = pendingIntent;
        mNotificationManager.notify(1, notification);
    }

    /**
     * notification结合RemoteViews用法
     */
    public void notificationOnRemoteViews(View view) {
        Intent intent = new Intent(this, Notification1Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.msg, "第五章RemoteViews在通知栏的使用");
        remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
        Intent intent2 = new Intent(this, Notification2Activity.class);
        intent.putExtra("count", count++);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, pendingIntent2);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("hello world")// todo ticker没起作用
                .setWhen(System.currentTimeMillis())// todo when没起作用
                .setAutoCancel(true)
                .setCustomContentView(remoteViews)
                .setDeleteIntent(pendingIntent);
        Notification notification = builder.build();
        notification.contentIntent = pendingIntent;

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, notification);
    }

    public void emulateSystemNotification(View view) {
        startActivity(new Intent(this, EmulateActivity.class));
    }
}
