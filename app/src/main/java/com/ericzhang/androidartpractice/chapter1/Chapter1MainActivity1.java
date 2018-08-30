package com.ericzhang.androidartpractice.chapter1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;

public class Chapter1MainActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1_main);
    }

    public void start2(View view) {
        Intent intent = new Intent(this, Chapter1MainActivity2.class);

        // 隐式调用时最好加一个判断看是否有可以Handle该Intent的组件
        // 针对Service和BroadcastReceiver，PackageManager同样提供了类似的方法去获取成功匹配的组件信息
//        PackageManager mPackageManager = getPackageManager();
//        List<ResolveInfo> resolveIntentActivities = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        ResolveInfo resolveActivity = mPackageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        List<ResolveInfo> resolveBroadcastReceivers = mPackageManager.queryBroadcastReceivers(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        List<ResolveInfo> resolveIntentServices = mPackageManager.queryIntentServices(intent, PackageManager.MATCH_DEFAULT_ONLY);
//
//        if (resolveIntentActivities != null && resolveIntentActivities.size() > 0) {
//            startActivity(intent);
//        }
//        if (resolveActivity != null) {
//            startActivity(intent);
//        }

        startActivity(intent);
    }
}
