package com.ericzhang.androidartpractice.chapter2.ipccategories.fileshare;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.binder.entity.Compony;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class File2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recoverFromFile();
    }

    private void recoverFromFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Compony compony;

                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                File cachedFile = new File(absolutePath + "/compony.text");

                if (cachedFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cachedFile));
                        compony = (Compony) objectInputStream.readObject();
                        Log.e("File2Activity", "compony: " + compony);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
