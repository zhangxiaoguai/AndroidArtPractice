package com.ericzhang.androidartpractice.chapter15;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.ericzhang.androidartpractice.R;

public class Chapter15MainActivity extends Activity implements TestManager.OnDataArrivedListener {

    private static final String TAG = "Chapter15MainActivity";
    private View myTips;
    ViewStub viewStub;

    // 静态变量导致内存泄漏
    private static Context mContext;
    private static View mView;

    private ObjectAnimator rotationAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter15_main);

        // 模拟ANR
        imitateANR();

        // include/merge/ViewStub
        // FIXME: 2018/8/23 创建了多个OnClickListener对象，最好不要这样写，而是让当前Activity继承OnClickListener

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_back");
            }
        });

        findViewById(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: tv_title");
            }
        });

        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_menu");

                SystemClock.sleep(30 * 1000);
            }
        });

        findViewById(R.id.btn_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_back2");

                // ViewStub只能加载一次，之后就不属于整个View布局了
                if (viewStub == null) {
                    viewStub = findViewById(R.id.viewStub);
                    myTips = viewStub.inflate();
                }
            }
        });

        findViewById(R.id.tv_title2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: tv_title2");

                // 可以使用ViewStub.inflate()返回的view
                myTips.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_menu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_menu2");

                // 也可以使用ViewStub在布局中定义的inflatedId
                findViewById(R.id.tips).setVisibility(View.VISIBLE);
            }
        });

        // 静态变量持有当前Activity的引用
        mContext = this;
        mView = new View(this);

        // 单例模式导致内存泄漏
        // 缺少解注册的操作时会导致内存泄漏
        // TestManager的单例对象一直只有当前Activity的引用，单例模式的生命周期和Application保持一致
        TestManager.getInstance().registerListener(this);

        // 属性动画导致内存泄漏
        rotationAnimation = ObjectAnimator.ofFloat(btn_back, "rotation", 0, 361/*多一度热爱*/).setDuration(2000);
        rotationAnimation.setRepeatMode(ValueAnimator.REVERSE);
        rotationAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimation.start();

        Log.e(TAG, "onCreate:" + Thread.currentThread().getPriority());
        SystemClock.sleep(30);
    }

    private void imitateANR() {
//        SystemClock.sleep(300 * 1000);// 1可以模拟出ANR

        Thread runnable = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Runnable:" + Thread.currentThread().getPriority());
                SystemClock.sleep(300 * 1000);
            }
        });
        runnable.setPriority(Thread.MAX_PRIORITY);
        runnable.start();

        // TODO: 2018/8/24  2无法模拟出ANR现象，反倒是模拟出了内存泄漏现象
        // Activity内的匿名线程没有执行完（300s内点击了返回键），持有当前Activity的引用，导致当前Activity无法回收
        // 跟线程优先级也没关系
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mContext = null;
        mView = null;

        TestManager.getInstance().unregisterListener(this);

        if (rotationAnimation != null) {
            rotationAnimation.cancel();
        }
    }

    @Override
    public void onDataArrived(Object data) {
        Log.e(TAG, "onDataArrived");
    }
}
