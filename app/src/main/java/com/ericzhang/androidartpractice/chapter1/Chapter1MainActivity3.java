package com.ericzhang.androidartpractice.chapter1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;

public class Chapter1MainActivity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1_main3);
    }

    public void start1(View view) {
        startActivity(new Intent(this, Chapter1MainActivity1.class));
    }
}
