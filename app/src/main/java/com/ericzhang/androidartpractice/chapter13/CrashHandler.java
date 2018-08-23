package com.ericzhang.androidartpractice.chapter13;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final boolean DEBUG = true;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashTest/log/";
    private static final String FILE_NAME_PREFIX = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler mInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 导出异常信息到SDCard中
        dumpExceptionToSDCard(ex);
        // 上传异常到服务器
        uploadExceptionToServer();

        ex.printStackTrace();
        // 如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.e(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            Log.e(TAG, "mkdirs: " + mkdirs);
        }

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        File file = new File(PATH + FILE_NAME_PREFIX + currentTime + FILE_NAME_SUFFIX);

        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printWriter.println(currentTime);
            dumpPhoneInfo(printWriter);
            printWriter.println();
            ex.printStackTrace(printWriter);
            printWriter.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
            e.printStackTrace();
        }
    }

    private void dumpPhoneInfo(PrintWriter printWriter) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

        // app版本号
        printWriter.print("App version: ");
        printWriter.print(packageInfo.versionName);
        printWriter.print("_");
        printWriter.println(packageInfo.versionCode);

        // Android版本号
        printWriter.print("OS version: ");
        printWriter.print(Build.VERSION.RELEASE);
        printWriter.print("_");
        printWriter.println(Build.VERSION.SDK_INT);

        // 手机制造商
        printWriter.print("Vendor: ");
        printWriter.println(Build.MANUFACTURER);

        // 手机型号
        printWriter.print("Model: ");
        printWriter.println(Build.MODEL);

        // CPU架构
        printWriter.print("CPU ABI: ");
        printWriter.println(Build.CPU_ABI);
    }

    private void uploadExceptionToServer() {
        // TODO: 2018/8/17  上传到服务器
    }

}
