package com.ericzhang.androidartpractice.chapter2.ipccategories.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    private static int count;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 998:
                    Log.e(TAG, "receive message from client: " + msg.getData().getString("msg"));

                    try {
                        Messenger client = msg.replyTo;
                        Message replyMsg = Message.obtain(null, 996);
                        Bundle bundle = new Bundle();
                        bundle.putString("answer", "hello,this is server,I`ll reply to you later." + count++);
                        replyMsg.setData(bundle);
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

}
