package com.ericzhang.androidartpractice.chapter2.binder.manualbinder.book;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;

import java.util.List;

public interface IBookManagerManual extends IInterface {

    static final String DESCRIPTOR = "com.ericzhang.androidartpractice.chapter2.binder.bean.Book.IBookManagerManual";

    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;


    public List<Book> getBookList() throws RemoteException;

    public void addBook(Book book) throws RemoteException;

}
