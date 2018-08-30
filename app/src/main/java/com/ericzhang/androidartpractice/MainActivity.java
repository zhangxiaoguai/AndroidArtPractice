package com.ericzhang.androidartpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.chapter1.Chapter1MainActivity1;
import com.ericzhang.androidartpractice.chapter11.Chapter11MainActivity;
import com.ericzhang.androidartpractice.chapter12.Chapter12MainActivity;
import com.ericzhang.androidartpractice.chapter13.Chapter13MainActivity;
import com.ericzhang.androidartpractice.chapter14.Chapter14MainActivity;
import com.ericzhang.androidartpractice.chapter15.Chapter15MainActivity;
import com.ericzhang.androidartpractice.chapter2.Chapter2MainActivity;
import com.ericzhang.androidartpractice.chapter3.Chapter3MainActivity;
import com.ericzhang.androidartpractice.chapter4.Chapter4MainActivity;
import com.ericzhang.androidartpractice.chapter5.Chapter5MainActivity;
import com.ericzhang.androidartpractice.chapter6.Chapter6MainActivity;
import com.ericzhang.androidartpractice.chapter7.Chapter7MainActivity;
import com.ericzhang.androidartpractice.chapter8.Chapter8MainActivity;
import com.ericzhang.androidartpractice.chapter9.Chapter9MainActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chapter1(View view) {
        startActivity(new Intent(this, Chapter1MainActivity1.class));
    }

    public void chapter2(View view) {
        Intent intent = new Intent(this, Chapter2MainActivity.class);
        startActivity(intent);
    }

    public void chapter3(View view) {
        Intent intent = new Intent(this, Chapter3MainActivity.class);
        startActivity(intent);
    }

    public void chapter4(View view) {
        Intent intent = new Intent(this, Chapter4MainActivity.class);
        startActivity(intent);
    }

    public void chapter5(View view) {
        Intent intent = new Intent(this, Chapter5MainActivity.class);
        startActivity(intent);
    }

    public void chapter6(View view) {
        Intent intent = new Intent(this, Chapter6MainActivity.class);
        startActivity(intent);
    }

    public void chapter7(View view) {
        Intent intent = new Intent(this, Chapter7MainActivity.class);
        startActivity(intent);
    }

    public void chapter8(View view) {
        Intent intent = new Intent(this, Chapter8MainActivity.class);
        startActivity(intent);
    }

    public void chapter9(View view) {
        Intent intent = new Intent(this, Chapter9MainActivity.class);
        startActivity(intent);
    }

    public void chapter11(View view) {
        Intent intent = new Intent(this, Chapter11MainActivity.class);
        startActivity(intent);
    }

    public void chapter12(View view) {
        Intent intent = new Intent(this, Chapter12MainActivity.class);
        startActivity(intent);
    }

    public void chapter13(View view) {
        Intent intent = new Intent(this, Chapter13MainActivity.class);
        startActivity(intent);
    }

    public void chapter14(View view) {
        startActivity(new Intent(this, Chapter14MainActivity.class));
    }

    public void chapter15(View view) {
        startActivity(new Intent(this, Chapter15MainActivity.class));
    }

}
