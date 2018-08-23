package com.ericzhang.androidartpractice.chapter2.binder.manualbinder.book;

import android.os.Binder;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;

public abstract class BookManagerManualImpl extends Binder implements IBookManagerManual {

    private static final String TAG = BookManagerManualImpl.class.getSimpleName();

    public BookManagerManualImpl() {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " BookManagerManualImpl:"
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));

        this.attachInterface(this, DESCRIPTOR);
    }

    public static IBookManagerManual asInterface(android.os.IBinder obj) {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " asInterface:"
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
        if ((obj == null)) {
            return null;
        }
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof IBookManagerManual))) {
            return ((IBookManagerManual) iin);
        }
        return new BookManagerManualImpl.Proxy(obj);
    }

    @Override
    public android.os.IBinder asBinder() {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " asBinder:"
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
        return this;
    }

    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onTransact:"
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getBookList: {
                data.enforceInterface(DESCRIPTOR);
                java.util.List<com.ericzhang.androidartpractice.chapter2.binder.bean.Book> _result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onTransact end:"
                        + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
                return true;
            }
            case TRANSACTION_addBook: {
                data.enforceInterface(DESCRIPTOR);
                com.ericzhang.androidartpractice.chapter2.binder.bean.Book _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = com.ericzhang.androidartpractice.chapter2.binder.bean.Book.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addBook(_arg0);
                reply.writeNoException();
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    private static class Proxy implements IBookManagerManual {
        private android.os.IBinder mRemote;

        Proxy(android.os.IBinder remote) {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " Proxy:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            mRemote = remote;
        }

        @Override
        public android.os.IBinder asBinder() {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " Proxy.asBinder:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            return mRemote;
        }

        public java.lang.String getInterfaceDescriptor() {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " Proxy.getInterfaceDescriptor:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            return DESCRIPTOR;
        }

        @Override
        public java.util.List<com.ericzhang.androidartpractice.chapter2.binder.bean.Book> getBookList() throws android.os.RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " Proxy.getBookList:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            java.util.List<com.ericzhang.androidartpractice.chapter2.binder.bean.Book> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(com.ericzhang.androidartpractice.chapter2.binder.bean.Book.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " Proxy.getBookList end:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            return _result;
        }

        @Override
        public void addBook(com.ericzhang.androidartpractice.chapter2.binder.bean.Book book) throws android.os.RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " Proxy.addBook:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((book != null)) {
                    _data.writeInt(1);
                    book.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
    }

}
