package com.ericzhang.androidartpractice.chapter2.ipccategories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.ipccategories.aidl.AIDLActivity;
import com.ericzhang.androidartpractice.chapter2.ipccategories.bundle.Bundle1Activity;
import com.ericzhang.androidartpractice.chapter2.ipccategories.contentprovider.ContentProviderActivity;
import com.ericzhang.androidartpractice.chapter2.ipccategories.fileshare.File1Activity;
import com.ericzhang.androidartpractice.chapter2.ipccategories.messenger.MessengerActivity;
import com.ericzhang.androidartpractice.chapter2.ipccategories.socket.TCPClientActivity;

public class IPCCategoriesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_categories);
    }

    public void bundle(View view) {
        startActivity(new Intent(this, Bundle1Activity.class));
    }

    public void file(View view) {
        startActivity(new Intent(this, File1Activity.class));
    }

    public void messenger(View view) {
        startActivity(new Intent(this, MessengerActivity.class));
    }

    public void aidl(View view) {
        startActivity(new Intent(this, AIDLActivity.class));
    }

    public void contentprovider(View view) {
        startActivity(new Intent(this, ContentProviderActivity.class));
    }

    public void socket(View view) {
        startActivity(new Intent(this, TCPClientActivity.class));
    }
}
