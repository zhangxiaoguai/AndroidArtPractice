package com.ericzhang.androidartpractice.chapter11;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class MyLocalIntentService extends IntentService {

    private static final String TAG = "MyLocalIntentService";

    public static final String ACTION_FOO = "com.ericzhang.androidartpractice.chapter11.action.FOO";
    public static final String ACTION_BAZ = "com.ericzhang.androidartpractice.chapter11.action.BAZ";

    public static final String EXTRA_PARAM1 = "com.ericzhang.androidartpractice.chapter11.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.ericzhang.androidartpractice.chapter11.extra.PARAM2";

    public MyLocalIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.e(TAG, "onHandleIntent,action:" + action);
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        try {
            Thread.sleep(2000);
            Log.e(TAG, "handleActionFoo,param1:" + param1 + ",param2:" + param2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        try {
            Thread.sleep(5000);
            Log.e(TAG, "handleActionBaz,param1:" + param1 + ",param2:" + param2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
