package com.ericzhang.androidartpractice.chapter9;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.MainActivity;
import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter11.MyLocalIntentService;

public class Chapter9MainActivity extends Activity {

    private static final String TAG = "Chapter9MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter9_main);
    }

    public void startActivity(View view) {
        // just examples
        startActivity(new Intent(this, MainActivity.class));
    }

    public void startService(View view) {
        // just examples
        startService(new Intent(this, MyLocalIntentService.class));
    }

    public void bindService(View view) {
        // just examples
        bindService(new Intent(this, MyLocalIntentService.class), null, BIND_AUTO_CREATE);
    }

    public void registerReceiver(View view) {
        // just examples
        IntentFilter filter = new IntentFilter();
        registerReceiver(new MyReceiver(), filter);
    }

    public void sendBroadcast(View view) {
        sendBroadcast(new Intent());
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive");
        }
    }

}
