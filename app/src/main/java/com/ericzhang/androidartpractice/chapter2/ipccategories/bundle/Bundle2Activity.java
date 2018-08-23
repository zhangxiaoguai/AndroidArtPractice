package com.ericzhang.androidartpractice.chapter2.ipccategories.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.ericzhang.androidartpractice.R;

public class Bundle2Activity extends Activity {

    private static final String TAG = Bundle2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle2);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String name = bundle.getString("name");
        Parcelable book = bundle.getParcelable("book");
        Log.e(TAG, "name: " + name);
        Log.e(TAG, "book: " + book);
    }
}
