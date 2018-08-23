package com.ericzhang.androidartpractice;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.ericzhang.androidartpractice.chapter13.CrashHandler;

public class MyApp extends Application {

    private static final String TAG = "MyApp";
    private static MyApp mAppInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate:" + Process.myPid());
        super.onCreate();

        mAppInstance = this;

        CrashHandler mCrashHandler = CrashHandler.getInstance();
        mCrashHandler.init(this);
    }

    public static MyApp getAppInstance() {
        return mAppInstance;
    }
}
