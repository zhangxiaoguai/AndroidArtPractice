package com.ericzhang.androidartpractice.chapter1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;

public class Chapter1MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1_main2);
    }

    public void start3(View view) {
        startActivity(new Intent(this, Chapter1MainActivity3.class));
    }
}
