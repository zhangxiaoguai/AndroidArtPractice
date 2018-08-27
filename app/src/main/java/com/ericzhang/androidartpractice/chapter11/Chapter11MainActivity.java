package com.ericzhang.androidartpractice.chapter11;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ericzhang.androidartpractice.R;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Chapter11MainActivity extends Activity {

    private static final String TAG = "Chapter11MainActivity";

    private DownloadFilesTask downloadFilesTask;
    private DownloadFilesTask downloadFilesConcurrentTask1;
    private DownloadFilesTask downloadFilesConcurrentTask2;
    private DownloadFilesTask downloadFilesConcurrentTask3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter11_main);
    }

    public void AsyncTaskBegin(View view) {
        try {
            if (downloadFilesTask != null) {
                downloadFilesTask.cancel(true);
            }
            downloadFilesTask = new DownloadFilesTask(this);
            URL url1 = new URL("http://google.com");
            URL url2 = new URL("http://baidu.com");
            URL url3 = new URL("http://apple.com");
            // 三个任务串行执行
            downloadFilesTask.execute(url1, url2, url3);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void AsyncTaskCancel(View view) {
        if (downloadFilesTask != null) {
            downloadFilesTask.cancel(true);
        }
    }

    public void AsyncTaskConcurrentBegin(View view) {
        try {
            if (downloadFilesConcurrentTask1 != null) {
                downloadFilesConcurrentTask1.cancel(true);
            }
            if (downloadFilesConcurrentTask2 != null) {
                downloadFilesConcurrentTask2.cancel(true);
            }
            if (downloadFilesConcurrentTask3 != null) {
                downloadFilesConcurrentTask3.cancel(true);
            }
            downloadFilesConcurrentTask1 = new DownloadFilesTask(this);
            downloadFilesConcurrentTask2 = new DownloadFilesTask(this);
            downloadFilesConcurrentTask3 = new DownloadFilesTask(this);
            URL url1 = new URL("http://google.com");
            URL url2 = new URL("http://baidu.com");
            URL url3 = new URL("http://apple.com");
            // 三个任务并行执行，必须创建三个不同的AsyncTask对象，否则报错：java.lang.IllegalStateException: Cannot execute task: the task is already running.
            downloadFilesConcurrentTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url1);
            downloadFilesConcurrentTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url2);
            downloadFilesConcurrentTask3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url3);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void AsyncTaskConcurrentCancel(View view) {
        if (downloadFilesConcurrentTask1 != null) {
            downloadFilesConcurrentTask1.cancel(true);
        }
        if (downloadFilesConcurrentTask2 != null) {
            downloadFilesConcurrentTask2.cancel(true);
        }
        if (downloadFilesConcurrentTask3 != null) {
            downloadFilesConcurrentTask3.cancel(true);
        }
    }

    public void IntentServiceAction1(View view) {
        Intent intent = new Intent(this, MyLocalIntentService.class);
        intent.setAction(MyLocalIntentService.ACTION_BAZ);
        intent.putExtra(MyLocalIntentService.EXTRA_PARAM1, "Eric");
        intent.putExtra(MyLocalIntentService.EXTRA_PARAM2, "McGrady");
        startService(intent);
    }

    public void IntentServiceAction2(View view) {
        Intent intent = new Intent(this, MyLocalIntentService.class);
        intent.setAction(MyLocalIntentService.ACTION_FOO);
        intent.putExtra(MyLocalIntentService.EXTRA_PARAM1, "Google");
        intent.putExtra(MyLocalIntentService.EXTRA_PARAM2, "Apple");
        startService(intent);
    }

    public void ThreadPoolCategory(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "runnable begin,thread name:" + Thread.currentThread().getName());
                SystemClock.sleep(2000);
                Log.e(TAG, "runnable end,thread name:" + Thread.currentThread().getName());
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(runnable);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(runnable);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        // 两分钟后执行runnable
        scheduledExecutorService.schedule(runnable, 2, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(runnable, 1, 5, TimeUnit.SECONDS);

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(runnable);
    }

    // 有可能Activity结束了但是该Task仍未执行完毕，造成内存泄漏，该内部变量Task对外部Activity是强引用导致无法GC
    // Activity结束被回收的时候，GC会检查是否有对象对该Activity有引用
    // GC发现该AsyncTask对此Activity持有弱引用，不管当前内存是否足够，都会回收该Activity
    // 静态内部类不会持有外部类的隐式引用，非隐式内部类会持有对外部类的引用
    // 普通AsyncTask首先设置为static class，然后对该外部类使用弱引用WeakReference
    private static class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        private final WeakReference<Activity> activityWeakReference;

        public DownloadFilesTask(Activity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 主线程中执行
            Log.e(TAG, "onPreExecute,是否是主线程：" + (Looper.myLooper() == Looper.getMainLooper()));
        }

        @Override
        protected Long doInBackground(URL... urls) {
            // 线程池中执行
            Log.e(TAG, "doInBackground,是否是主线程：" + (Looper.myLooper() == Looper.getMainLooper()));

            int count = urls.length;
            long totalSize = 0;
            try {
                for (int i = 0; i < count; i++) {
                    Log.e(TAG, "doInBackground,执行：" + urls[i]);
                    Thread.sleep(5000);
                    totalSize += 100;
                    publishProgress((int) ((i + 1) / (float) count * 100));

                    // 如果任务取消则跳出循环
                    if (isCancelled()) {
                        Log.e(TAG, "doInBackground,isCancelled.");
                        break;
                    }

                    // 如果Activity退出则跳出循环
                    Activity activity = activityWeakReference.get();
                    if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                        Log.e(TAG, "doInBackground,activity is null");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 主线程中执行
            Log.e(TAG, "onProgressUpdate,是否是主线程：" + (Looper.myLooper() == Looper.getMainLooper()));
            int count = values.length;
            for (int i = 0; i < count; i++) {
                Log.e(TAG, "values: " + values[i]);
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            // 主线程中执行
            Log.e(TAG, "onPostExecute,是否是主线程：" + (Looper.myLooper() == Looper.getMainLooper()));
            Log.e(TAG, "onPostExecute(Long aLong)：" + aLong);// doInBackground返回的值

            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }

            // 继续正常逻辑，更新UI等
            Toast.makeText(activity, "总共下载长度：" + aLong, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(Long aLong) {
            super.onCancelled(aLong);
            // 主线程中执行，在onCancelled()之后执行
            Log.e(TAG, "onCancelled(Long aLong),是否是主线程：" + (Looper.myLooper() == Looper.getMainLooper()));
            Log.e(TAG, "onCancelled(Long aLong)：" + aLong);// doInBackground返回的值
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            // 主线程中执行，先于onCancelled(Long aLong)执行
            Log.e(TAG, "onCancelled(),是否是主线程：" + (Looper.myLooper() == Looper.getMainLooper()));
        }

    }
}


