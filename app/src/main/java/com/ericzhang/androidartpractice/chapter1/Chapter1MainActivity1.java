package com.ericzhang.androidartpractice.chapter1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Chapter1MainActivity1 extends Activity {

    private static final String TAG = "Chapter1MainActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1_main);

        Log.e(TAG, "onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "onDestroy");
    }

    public void start2(View view) {
        Intent intent = new Intent(this, Chapter1MainActivity2.class);

        // 隐式调用时最好加一个判断看是否有可以Handle该Intent的组件
        // 针对Service和BroadcastReceiver，PackageManager同样提供了类似的方法去获取成功匹配的组件信息
        PackageManager mPackageManager = getPackageManager();
        List<ResolveInfo> resolveIntentActivities = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 根据Intent查询可以处理这个Intent的所有BroadcastReceivers
        List<ResolveInfo> resolveBroadcastReceivers = mPackageManager.queryBroadcastReceivers(intent, PackageManager.MATCH_DEFAULT_ONLY);
        List<ResolveInfo> resolveIntentServices = mPackageManager.queryIntentServices(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // 查询系统中运行的ContentProvider，进程名：如果希望列出所有的，就为空；uid：如果进程名不为空，需要指定uid；标志位：match的属性
        List<ProviderInfo> providerInfoList = mPackageManager.queryContentProviders(null, 0, PackageManager.MATCH_ALL);
        // 查询可以处理这个Intent的所有ContentProviders
        List<ResolveInfo> resolveIntentContentProviders = mPackageManager.queryIntentContentProviders(intent, PackageManager.MATCH_DEFAULT_ONLY);

        ResolveInfo resolveActivity = mPackageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveIntentActivities != null && resolveIntentActivities.size() > 0) {
            startActivity(intent);
            return;
        }
        if (resolveActivity != null) {
            startActivity(intent);
            return;
        }

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.e(TAG, "onRestoreInstanceState");
    }
}
