package com.ericzhang.androidartpractice.chapter2.ipccategories.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServerService extends Service {

    private static final String TAG = TCPServerService.class.getSimpleName();

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private String[] mDefinedMessages = new String[]{
            "你好啊，哈哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗，我可是可以和多人同时聊天哦"
    };

    public TCPServerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new TCPServer()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed.set(true);
    }

    private class TCPServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.e(TAG, "TCPServer IOException, establish tcp server failed, port 8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestroyed.get()) {
                try {
                    final Socket client = serverSocket.accept();
                    Log.e(TAG, "accept");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(client.getInputStream()));
        // 用于向客户端发送消息
        PrintWriter printWriter =
                new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(client.getOutputStream())), true);

        printWriter.println("欢迎来到聊天室！");

        while (!mIsServiceDestroyed.get()) {
            String msg = bufferedReader.readLine();
            Log.e(TAG, " msg from client: " + msg);
            if (msg == null) {
                // 客户端断开链接
                break;
            }

            int index = new Random().nextInt(mDefinedMessages.length);
            String reply = mDefinedMessages[index] + ",time stamp:" + System.currentTimeMillis();
            printWriter.println("reply from server: " + reply);
        }
        Log.e(TAG, "client quit");

        bufferedReader.close();
        printWriter.close();
        client.close();
    }

}
