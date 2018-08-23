package com.ericzhang.androidartpractice.chapter2.binder.manualbinder.compony;

import android.os.Binder;
import android.os.Parcel;

import com.ericzhang.androidartpractice.chapter2.binder.entity.Compony;

public abstract class ComponyManagerManualImpl extends Binder implements IComponyManagerManual {

    public ComponyManagerManualImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static IComponyManagerManual asInterface(android.os.IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof IComponyManagerManual))) {
            return ((IComponyManagerManual) iin);
        }
        return new ComponyManagerManualImpl.Proxy(obj);
    }

    @Override
    public android.os.IBinder asBinder() {
        return this;
    }

    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getComponyList: {
                data.enforceInterface(DESCRIPTOR);
                java.util.List<Compony> _result = this.getComponyList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addCompony: {
                data.enforceInterface(DESCRIPTOR);
                com.ericzhang.androidartpractice.chapter2.binder.entity.Compony _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = com.ericzhang.androidartpractice.chapter2.binder.entity.Compony.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addCompony(_arg0);
                reply.writeNoException();
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    private static class Proxy implements IComponyManagerManual {
        private android.os.IBinder mRemote;

        Proxy(android.os.IBinder remote) {
            mRemote = remote;
        }

        @Override
        public android.os.IBinder asBinder() {
            return mRemote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public java.util.List<Compony> getComponyList() throws android.os.RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            java.util.List<Compony> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getComponyList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(Compony.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addCompony(Compony compony) throws android.os.RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((compony != null)) {
                    _data.writeInt(1);
                    compony.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addCompony, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
    }
}
