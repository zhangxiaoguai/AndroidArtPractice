package com.ericzhang.androidartpractice.chapter2.ipccategories.fileshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.binder.entity.Compony;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class File1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        persistToFile();
    }

    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 要持久化到本地，必须实现Serializable接口
                Compony compony = new Compony();
                compony.setName("鼎鑫水泥");
                compony.setNumber(888);

                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                File cachedFile = new File(absolutePath + "/compony.text");

                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(compony);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public void file2(View view) {
        startActivity(new Intent(this, File2Activity.class));
    }
}
