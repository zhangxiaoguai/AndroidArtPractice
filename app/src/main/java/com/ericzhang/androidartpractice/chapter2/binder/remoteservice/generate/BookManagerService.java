package com.ericzhang.androidartpractice.chapter2.binder.remoteservice.generate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;
import com.ericzhang.androidartpractice.chapter2.binder.bean.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {

    private static final String TAG = BookManagerService.class.getSimpleName();
    //    private List<Book> bookList = new ArrayList<>();
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    public BookManagerService() {
    }

    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            // 1.使用同步方法
//            synchronized (bookList) {
//                return bookList;
//            }

            // 2.使用CopyOnWriteArrayList
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            // 1.使用同步方法
//            synchronized (bookList) {
//                if (!bookList.contains(book)) {
//                    bookList.add(book);
//                }
//            }

            // 2.使用CopyOnWriteArrayList
            bookList.add(book);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Book book = new Book();
        book.setBookId(1);
        book.setBookName("Android艺术开发探索");
        bookList.add(book);

        Book book2 = new Book();
        book2.setBookId(2);
        book2.setBookName("Android源码设计模式");
        bookList.add(book2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
