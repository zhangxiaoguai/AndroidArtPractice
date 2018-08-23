package com.ericzhang.androidartpractice.chapter2.ipccategories.socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClientActivity extends Activity {

    private static final String TAG = TCPClientActivity.class.getSimpleName();

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    Log.e(TAG, "handleMessage: MESSAGE_RECEIVE_NEW_MSG:" + msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    Log.e(TAG, "handleMessage: MESSAGE_SOCKET_CONNECTED:" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
    }

    public void startService(View view) {
        startService(new Intent(this, TCPServerService.class));
    }

    public void connectServer(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage2Server(View view) {
        // 如果此处不开新线程而是直接使用mPrintWriter会报android.os.NetworkOnMainThreadException异常
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = "客户端消息，时间戳：" + System.currentTimeMillis();
                    if (mPrintWriter != null) {
                        mPrintWriter.println(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.e(TAG, "connectTCPServer: success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.e(TAG, "connectTCPServer: reconnected");
                e.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                while (!TCPClientActivity.this.isFinishing()) {
                    String messageFromServer = bufferedReader.readLine();
                    Log.e(TAG, "message from server: " + messageFromServer);
                    if (messageFromServer != null) {
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, messageFromServer).sendToTarget();
                    }

                }
                Log.e(TAG, "connectTCPServer: client quit...");
                bufferedReader.close();
                mPrintWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
