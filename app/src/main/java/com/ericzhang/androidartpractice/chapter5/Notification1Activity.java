package com.ericzhang.androidartpractice.chapter5;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.ericzhang.androidartpractice.R;

public class Notification1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        Toast.makeText(this, getIntent().getStringExtra("count"), Toast.LENGTH_SHORT).show();
    }
}
