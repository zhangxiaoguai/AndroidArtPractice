package com.ericzhang.androidartpractice.chapter2.binder.remoteservice.manual;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.ericzhang.androidartpractice.chapter2.binder.entity.Compony;
import com.ericzhang.androidartpractice.chapter2.binder.manualbinder.compony.ComponyManagerManualImpl;

import java.util.ArrayList;
import java.util.List;

public class ComponyManagerManualService extends Service {

    private List<Compony> componyList = new ArrayList<>();

    public ComponyManagerManualService() {
    }

    private final ComponyManagerManualImpl componyManagerManual = new ComponyManagerManualImpl() {
        @Override
        public List<Compony> getComponyList() throws RemoteException {

            synchronized (componyList) {
                return componyList;
            }
        }

        @Override
        public void addCompony(Compony compony) throws RemoteException {
            synchronized (componyList) {
                if (!componyList.contains(compony)) {
                    componyList.add(compony);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Compony c = new Compony();
        c.setName("谷歌");
        c.setNumber(999);
        componyList.add(c);

        Compony c2 = new Compony();
        c2.setName("苹果");
        c2.setNumber(998);
        componyList.add(c2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return componyManagerManual;
    }
}
