package com.ericzhang.androidartpractice.chapter4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.ericzhang.androidartpractice.R;

public class Chapter4MainActivity extends Activity {

    private static final String TAG = Chapter4MainActivity.class.getSimpleName();

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_main);
        tv = findViewById(R.id.tv);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        tv.measure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = tv.getMeasuredHeight();
        int measuredWidth = tv.getMeasuredWidth();
        Log.e(TAG, "手动测量,measuredHeight:" + measuredHeight);
        Log.e(TAG, "手动测量,measuredWidth:" + measuredWidth);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView没有换行的情况下结果是正确的
                int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
                int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
                tv.measure(widthMeasureSpec, heightMeasureSpec);
                int measuredHeight = tv.getMeasuredHeight();
                int measuredWidth = tv.getMeasuredWidth();
                Log.e(TAG, "手动测量,measuredHeight:" + measuredHeight);
                Log.e(TAG, "手动测量,measuredWidth:" + measuredWidth);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            int measuredHeight = tv.getMeasuredHeight();
            int measuredWidth = tv.getMeasuredWidth();
            Log.e(TAG, "onWindowFocusChanged,measuredHeight:" + measuredHeight);
            Log.e(TAG, "onWindowFocusChanged,measuredWidth:" + measuredWidth);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        tv.post(new Runnable() {
            @Override
            public void run() {
                int measuredHeight = tv.getMeasuredHeight();
                int measuredWidth = tv.getMeasuredWidth();
                Log.e(TAG, "view.post,measuredHeight:" + measuredHeight);
                Log.e(TAG, "view.post,measuredWidth:" + measuredWidth);
            }
        });

        ViewTreeObserver viewTreeObserver = tv.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measuredHeight = tv.getMeasuredHeight();
                int measuredWidth = tv.getMeasuredWidth();
                Log.e(TAG, "viewTreeObserver,measuredHeight:" + measuredHeight);
                Log.e(TAG, "viewTreeObserver,measuredWidth:" + measuredWidth);
            }
        });

    }
}
