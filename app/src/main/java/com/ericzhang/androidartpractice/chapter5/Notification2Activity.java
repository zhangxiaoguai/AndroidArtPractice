package com.ericzhang.androidartpractice.chapter5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ericzhang.androidartpractice.R;

public class Notification2Activity extends Activity {

    private static final String TAG = "Notification2Acitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2_acitivity);
        Log.d(TAG, "onCreate");
        Toast.makeText(this, getIntent().getStringExtra("count"),
                Toast.LENGTH_SHORT).show();
    }

    public void onButtonClick(View view) {

    }
}
