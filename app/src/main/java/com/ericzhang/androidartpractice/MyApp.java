package com.ericzhang.androidartpractice;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.ericzhang.androidartpractice.chapter13.CrashHandler;
import com.ericzhang.androidartpractice.chapter15.MyBlockCanaryContext;
import com.ericzhang.androidartpractice.chapter3.utils.MyUtils;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        mAppInstance = this;

        Log.e(TAG, "getPackageName:" + getPackageName());
        Log.e(TAG, "getCurrentProcessName:" + MyUtils.getCurrentProcessName());

        // 主进程
        if (TextUtils.equals(getPackageName(), MyUtils.getCurrentProcessName())) {
            Log.e(TAG, "MyApp.onCreate,主进程：" + getPackageName());
            BlockCanary.install(this, new MyBlockCanaryContext()).start();
            initCrashHandler();
        }
    }

    private void initCrashHandler() {
        CrashHandler mCrashHandler = CrashHandler.getInstance();
        mCrashHandler.init(this);
    }

    public static MyApp getAppInstance() {
        return mAppInstance;
    }

}
