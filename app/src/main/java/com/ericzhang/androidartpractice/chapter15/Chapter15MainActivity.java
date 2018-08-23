package com.ericzhang.androidartpractice.chapter15;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import com.ericzhang.androidartpractice.R;

public class Chapter15MainActivity extends Activity {

    private static final String TAG = "Chapter15MainActivity";
    private View myTips;
    ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter15_main);

        // FIXME: 2018/8/23 创建了多个OnClickListener对象，最好不要这样写，而是让当前Activity继承OnClickListener

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
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


    }

}
