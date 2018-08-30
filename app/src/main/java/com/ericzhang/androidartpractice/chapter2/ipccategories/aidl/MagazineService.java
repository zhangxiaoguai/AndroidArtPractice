package com.ericzhang.androidartpractice.chapter2.ipccategories.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MagazineService extends Service {

    private static final String TAG = MagazineService.class.getSimpleName();

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Magazine> magazineList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewMagazineArrivedListener> listenerList = new RemoteCallbackList<>();

    public MagazineService() {
    }

    private final IMagazineManager.Stub stub = new IMagazineManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 客户端绑定服务端时进行权限验证
            int result = checkCallingOrSelfPermission("com.ericzhang.androidartpractice.chapter2.ipccategories.aidl.permisson.ACCESS_MAGAZINE_SERVICE");
            if (result == PackageManager.PERMISSION_DENIED) {
                Log.e(TAG, "onTransact：没有对应的权限！");
                return false;
            }
            String packageName = null;
            String[] packagesForUid = getPackageManager().getPackagesForUid(getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                packageName = packagesForUid[0];
            }
            if (TextUtils.isEmpty(packageName) || !packageName.startsWith("com.ericzhang")) {
                Log.e(TAG, "onTransact：没有对应的权限！");
                return false;
            }

            Log.e(TAG, "onTransact：权限校验成功！");

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Magazine> getMagazineList() throws RemoteException {
            // 此处也可以进行权限验证
            // getCallingUid方法是Binder类2的方法

            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " getMagazineList: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return magazineList;
        }

        @Override
        public void addMagazine(Magazine magazine) throws RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " addMagazine: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

            onNewMagazineArrived(magazine);
        }

        @Override
        public void registerListener(IOnNewMagazineArrivedListener listener) throws RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " registerListener: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

//            if (listenerList.contains(listener)) {
//                Log.e(TAG, "registerListener: already exits.");
//            } else {
//                Log.e(TAG, "registerListener: add listener.");
//                listenerList.add(listener);
//            }
//            Log.e(TAG, "register listener,current size: " + listenerList.size());

            listenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewMagazineArrivedListener listener) throws RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " unregisterListener: "
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

//            if (listenerList.contains(listener)) {
//                listenerList.remove(listener);
//                Log.e(TAG, "unregister listener succeed.");
//            } else {
//                Log.e(TAG, "not fount, can not unregister.");
//            }
//            Log.e(TAG, "unregister listener,current size: " + listenerList.size());

            listenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onCreate: "
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

        super.onCreate();
        Magazine magazine = new Magazine();
        magazine.setName("Android");
        magazine.setPrice(10);
        magazineList.add(magazine);

        Magazine magazine2 = new Magazine();
        magazine2.setName("iOS");
        magazine2.setPrice(9);
        magazineList.add(magazine2);

        new Thread(new WorkerService()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed.set(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onBind: "
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

        // 客户端绑定服务端时进行权限验证
        int result = checkCallingOrSelfPermission("com.ericzhang.androidartpractice.chapter2.ipccategories.aidl.permisson.ACCESS_MAGAZINE_SERVICE");
        if (result == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG, "onBind：没有对应的权限！");
            return null;
        }

        Log.e(TAG, "onBind：权限校验成功！");
        return stub;
    }

    private void onNewMagazineArrived(Magazine magazine) throws RemoteException {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onNewMagazineArrived: "
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

        magazineList.add(magazine);
//        for (int i = 0; i < listenerList.size(); i++) {
//            listenerList.get(i).onNewMagazineArrived(magazine);
//        }

        // 此处调用的是客户端的方法，运行在客户端Binder线程池中，如果客户端方法比较耗时，此处调用应保证是在非UI线程调用
        final int N = listenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewMagazineArrivedListener listener = listenerList.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewMagazineArrived(magazine);
            }
        }
        listenerList.finishBroadcast();
    }

    private class WorkerService implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int price = magazineList.size() + 1;
                String name = "new magazine:" + price;
                Magazine magazine = new Magazine();
                magazine.setPrice(price);
                magazine.setName(name);

                try {
                    onNewMagazineArrived(magazine);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
