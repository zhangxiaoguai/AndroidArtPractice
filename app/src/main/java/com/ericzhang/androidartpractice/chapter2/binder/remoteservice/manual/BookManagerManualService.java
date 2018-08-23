package com.ericzhang.androidartpractice.chapter2.binder.remoteservice.manual;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;
import com.ericzhang.androidartpractice.chapter2.binder.manualbinder.book.BookManagerManualImpl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerManualService extends Service {

    private static final String TAG = BookManagerManualService.class.getSimpleName();
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    public BookManagerManualService() {
    }

    private final BookManagerManualImpl bookManagerManual = new BookManagerManualImpl() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " getBookList:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " addBook:"
                    + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            bookList.add(book);
        }
    };

    @Override
    public void onCreate() {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onCreate:"
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
        super.onCreate();

        Book book = new Book();
        book.setBookId(1);
        book.setBookName("阿里巴巴");
        bookList.add(book);

        Book book2 = new Book();
        book2.setBookId(2);
        book2.setBookName("腾讯");
        bookList.add(book2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onBind:"
                + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
        return bookManagerManual;
    }
}
