package com.ericzhang.androidartpractice.chapter2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.binder.BinderActivity;
import com.ericzhang.androidartpractice.chapter2.binderpool.BinderPoolActivity;
import com.ericzhang.androidartpractice.chapter2.ipccategories.IPCCategoriesActivity;

public class Chapter2MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2_main);
    }

    public void binder(View view) {
        startActivity(new Intent(this, BinderActivity.class));
    }

    public void ipccategories(View view) {
        startActivity(new Intent(this, IPCCategoriesActivity.class));
    }

    public void binderpool(View view) {
        startActivity(new Intent(this, BinderPoolActivity.class));
    }
}
