package com.ericzhang.androidartpractice.chapter2.binder.manualbinder.compony;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.ericzhang.androidartpractice.chapter2.binder.entity.Compony;

import java.util.List;

public interface IComponyManagerManual extends IInterface {

    static final String DESCRIPTOR = "com.ericzhang.androidartpractice.chapter2.binder.manualbinder.compony.IComponyManagerManual";

    static final int TRANSACTION_getComponyList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addCompony = IBinder.FIRST_CALL_TRANSACTION + 1;

    List<Compony> getComponyList() throws RemoteException;

    void addCompony(Compony compony) throws RemoteException;

}
