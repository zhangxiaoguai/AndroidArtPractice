package com.ericzhang.androidartpractice.chapter2.binderpool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;

public class BinderPoolActivity extends Activity {

    private static final String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
    }

    public void doWork(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
                IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
                ISecurityCenter mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
                Log.e(TAG, "visit security center");
                String msg = "hello-world-安卓";
                Log.e(TAG, "content: " + msg);
                try {
                    String password = mSecurityCenter.encrypt(msg);
                    Log.e(TAG, "encrypt: " + password);
                    String content = mSecurityCenter.decrypt(password);
                    Log.e(TAG, "decrypt: " + content);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "visit compute center");
                IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
                ICompute mCompute = ComputeImpl.asInterface(computeBinder);
                try {
                    int result = mCompute.add(3, 5);
                    Log.e(TAG, "3 + 5 = " + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
