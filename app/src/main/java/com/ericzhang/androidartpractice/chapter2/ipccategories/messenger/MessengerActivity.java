package com.ericzhang.androidartpractice.chapter2.ipccategories.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;

public class MessengerActivity extends Activity {

    private Messenger messenger;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 996: {
                    Log.e("MessengerActivity", "receive message from server: " + msg.getData().getString("answer"));
                    break;
                }
                default: {
                    super.handleMessage(msg);
                }
            }
        }
    }

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

    public void sendMessage(View view) {
        try {
            Message msg = Message.obtain(null, 998);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello,this is client:" + count++);
            msg.setData(bundle);
            msg.replyTo = mGetReplyMessenger;
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
