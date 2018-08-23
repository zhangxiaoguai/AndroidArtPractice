package com.ericzhang.androidartpractice.chapter2.ipccategories.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;

import java.util.List;

public class AIDLActivity extends Activity {

    private static final String TAG = "AIDLActivity";
    private IMagazineManager iMagazineManager;
    private int count;

    private static final int MESSAGE_NEW_MAGAZINE_ARRIVED = 88;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_MAGAZINE_ARRIVED:
                    Log.e(TAG, "handleMessage,receive new magazine:" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private IOnNewMagazineArrivedListener iOnNewMagazineArrivedListener = new IOnNewMagazineArrivedListener.Stub() {
        @Override
        public void onNewMagazineArrived(Magazine magazine) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_MAGAZINE_ARRIVED, magazine).sendToTarget();
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onServiceConnected: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            iMagazineManager = IMagazineManager.Stub.asInterface(service);

            try {
                iMagazineManager.registerListener(iOnNewMagazineArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onServiceDisconnected: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            iMagazineManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        Intent intent = new Intent(this, MagazineService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (iMagazineManager != null && iMagazineManager.asBinder().isBinderAlive()) {
            try {
                Log.e(TAG, "onDestroy,unregister listener:" + iOnNewMagazineArrivedListener);
                iMagazineManager.unregisterListener(iOnNewMagazineArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        unbindService(serviceConnection);
    }

    public void getMagazineList(View view) {
        // 默认是主线程

        // 如果此处调用的服务端方法是耗时操作，应该使用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e(TAG, "myPid：" + android.os.Process.myPid() + " getMagazineList: "
                            + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
                    List<Magazine> magazineList = iMagazineManager.getMagazineList();
                    for (Magazine magazine : magazineList) {
                        Log.e(TAG, "query magazine list:" + magazine.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addMagazine(View view) {
        try {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " addMagazine: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            Magazine magazine = new Magazine();
            magazine.setName("Windows");
            magazine.setPrice(999 + count++);
            iMagazineManager.addMagazine(magazine);
            Log.e(TAG, "add magazine:" + magazine.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
