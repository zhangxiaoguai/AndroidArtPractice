package com.ericzhang.androidartpractice.chapter13;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;

public class Chapter13MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter13_main);
    }

    public void button1(View view) {
        throw new RuntimeException("自定义异常：这是自己抛出的异常！");
    }

}
