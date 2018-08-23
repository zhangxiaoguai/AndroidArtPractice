package com.ericzhang.androidartpractice.chapter8;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ericzhang.androidartpractice.R;

import java.lang.reflect.Field;

public class Chapter8MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private static final String TAG = "Chapter8MainActivity";

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    private Button mFloatingButton;
    private Dialog mDialog;

    private int xInView = 0;
    private int yInView = 0;
    private int xDownInScreen = 0;
    private int yDownInScreen = 0;
    private int xInScreen = 0;
    private int yInScreen = 0;
    private int statusBarHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter8_main);

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    public void button1(View view) {
        // 避免new之后创建多个
        if (mFloatingButton == null) {
            mFloatingButton = new Button(this);
            mFloatingButton.setText("FloatingButton");
            mFloatingButton.setOnTouchListener(this);
            mFloatingButton.setOnClickListener(this);
            mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            // type必须设置，代表Window的类型
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            mLayoutParams.gravity = Gravity.START | Gravity.TOP;
            mLayoutParams.x = 500;
            mLayoutParams.y = 500;
            mWindowManager.addView(mFloatingButton, mLayoutParams);
        }
    }

    public void button2(View view) {
        if (mDialog == null) {
            // 当Dialog使用application的context时需要指定Window类型为系统类型
            mDialog = new Dialog(this.getApplicationContext());
            TextView mTextView = new TextView(this);
            mTextView.setText("this is toast!");
            mDialog.setContentView(mTextView);
            // 指定Window类型为系统类型
            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
            // 需要申请SystemAlert权限
        }
        mDialog.show();
    }

    public void button3(View view) {
        // 如果new一个toast时就不能使用系统默认的toast样式，就不能只指定Text
//        Toast toast = new Toast(this);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setText("居中默认吐司");// 设置的是系统默认的布局中的TextView
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();

        Toast toast = Toast.makeText(this, "居中默认吐司", Toast.LENGTH_SHORT);
        // 只有通过默认的Toast.makeText创建的Toast才能调用setText方法
        toast.setText("居中默认吐司2");
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void button4(View view) {
        View target = LayoutInflater.from(this).inflate(R.layout.layout_notification, null);
        Toast toast = new Toast(this);
        toast.setView(target);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 只有在View不会null的时候才可以调用remove否则会报错
        if (mFloatingButton != null) {
            mWindowManager.removeView(mFloatingButton);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean flag = true;
        Log.e(TAG, "onTouch");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = (int) event.getX();
                yInView = (int) event.getY();
                xDownInScreen = (int) event.getRawX();
                yDownInScreen = (int) (event.getRawY() - getStatusBarHeight());
                xInScreen = (int) event.getRawX();
                yInScreen = (int) (event.getRawY() - getStatusBarHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = (int) event.getRawX();
                yInScreen = (int) (event.getRawY() - getStatusBarHeight());
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    flag = false;
                }
                break;
        }
        return flag;
    }

    private void updateViewPosition() {
        mLayoutParams.x = xInScreen - xInView;
        mLayoutParams.y = yInScreen - yInView;
        mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick");

    }

    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

}
