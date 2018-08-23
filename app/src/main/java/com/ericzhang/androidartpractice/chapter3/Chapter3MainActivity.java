package com.ericzhang.androidartpractice.chapter3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter3.activity.Demo1Activity;
import com.ericzhang.androidartpractice.chapter3.activity.Demo2Activity;
import com.ericzhang.androidartpractice.chapter3.activity.ScrollActivity;

public class Chapter3MainActivity extends Activity {

    private static final String TAG = "Chapter3MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter3_main);

        Button click = findViewById(R.id.btn_click);

        int top = click.getTop();
        int left = click.getLeft();
        int bottom = click.getBottom();
        int right = click.getRight();

        float x = click.getX();
        float y = click.getY();
        float translationX = click.getTranslationX();
        float translationY = click.getTranslationY();
        ViewGroup viewById = getWindow().getDecorView().findViewById(android.R.id.content);
        viewById.getChildAt(0);

        Log.e(TAG, "x = left + translationX: " + (x == (left + translationX)));
        Log.e(TAG, "y = top + translationY: " + (y == (top + translationY)));

        // View在平移的过程中，top和left是原始左上角的位置信息，其值并不会发生改变，此时发生改变的是x，y，translationX和translationY这四个参数。

        // MotionEvent
//        MotionEvent.ACTION_UP;
//        MotionEvent.ACTION_DOWN;
//        MotionEvent.ACTION_MOVE;

//        motionEvent.getX();
//        motionEvent.getRawX();
//        motionEvent.getY();
//        motionEvent.getRawY();

        // 一般是8dp
        int scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Log.e(TAG, "scaledTouchSlop: " + scaledTouchSlop);

//        // VelocityTracker
//        VelocityTracker velocityTracker = VelocityTracker.obtain();
//        velocityTracker.addMovement(motionEvent);

        // 使用时需要先计算一下才能取到值，computeCurrentVelocity
        // 取速度之前必须先计算速度
        // 1000ms内划过的像素数
//        velocityTracker.computeCurrentVelocity(1000);
//        float xVelocity = velocityTracker.getXVelocity();
//        float yVelocity = velocityTracker.getYVelocity();

        // 使用完毕回收
//        velocityTracker.clear();
//        velocityTracker.recycle();

        // GestureDetector，用法见CustomView，但并不是标准用法

        // Scroller，用法见CustomView，但并不是标准用法

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void scroll(View view) {
        startActivity(new Intent(this, ScrollActivity.class));
    }

    public void demo1(View view) {
        startActivity(new Intent(this, Demo1Activity.class));
    }

    public void demo2(View view) {
        startActivity(new Intent(this, Demo2Activity.class));
    }
}
