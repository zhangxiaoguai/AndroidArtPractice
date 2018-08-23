package com.ericzhang.androidartpractice.chapter2.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.Chapter2MainActivity;
import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;
import com.ericzhang.androidartpractice.chapter2.binder.bean.IBookManager;
import com.ericzhang.androidartpractice.chapter2.binder.entity.Compony;
import com.ericzhang.androidartpractice.chapter2.binder.manualbinder.book.BookManagerManualImpl;
import com.ericzhang.androidartpractice.chapter2.binder.manualbinder.book.IBookManagerManual;
import com.ericzhang.androidartpractice.chapter2.binder.manualbinder.compony.ComponyManagerManualImpl;
import com.ericzhang.androidartpractice.chapter2.binder.manualbinder.compony.IComponyManagerManual;
import com.ericzhang.androidartpractice.chapter2.binder.remoteservice.generate.BookManagerService;
import com.ericzhang.androidartpractice.chapter2.binder.remoteservice.manual.BookManagerManualService;
import com.ericzhang.androidartpractice.chapter2.binder.remoteservice.manual.ComponyManagerManualService;

import java.util.List;

public class BinderActivity extends Activity {

    private static final String TAG = Chapter2MainActivity.class.getSimpleName();

    private IBookManager iBookManager;
    private IBookManagerManual iBookManagerManual;
    private IComponyManagerManual iComponyManagerManual;
    private int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);

        bindBookService();
        bindBookManualService();
        bindComponyManualService();
    }

    private void bindBookService() {
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mBookConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mBookConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(bookDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBookManager = null;
        }
    };

    private IBinder.DeathRecipient bookDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iBookManager == null) {
                return;
            }
            iBookManager.asBinder().unlinkToDeath(bookDeathRecipient, 0);
            iBookManager = null;
        }
    };

    private void bindBookManualService() {
        Intent intent = new Intent(this, BookManagerManualService.class);
        bindService(intent, mBookManualConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mBookManualConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
            // onServiceConnected此方法回调是在主线程

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onServiceConnected: mBookManualConnection " + "是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
            iBookManagerManual = BookManagerManualImpl.asInterface(service);
            try {
                service.linkToDeath(bookManualDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
//                }
//            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // onServiceDisconnected此方法回调是在主线程
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " onServiceDisconnected: mBookManualConnection");

            iBookManagerManual = null;
        }
    };

    private IBinder.DeathRecipient bookManualDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "myPid：" + android.os.Process.myPid() + " binderDied: bookManualDeathRecipient");
            if (iBookManagerManual == null) {
                return;
            }
            iBookManagerManual.asBinder().unlinkToDeath(bookManualDeathRecipient, 0);
            iBookManagerManual = null;
        }
    };

    private void bindComponyManualService() {
        Intent intent = new Intent(this, ComponyManagerManualService.class);
        bindService(intent, mComponyManualConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mComponyManualConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iComponyManagerManual = ComponyManagerManualImpl.asInterface(service);
            try {
                service.linkToDeath(componyManualDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iComponyManagerManual = null;
        }
    };

    private IBinder.DeathRecipient componyManualDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iComponyManagerManual == null) {
                return;
            }
            iComponyManagerManual.asBinder().unlinkToDeath(componyManualDeathRecipient, 0);
            iComponyManagerManual = null;
        }
    };

    public void getBookList(View view) {
        try {
            List<Book> bookList = iBookManager.getBookList();
            Log.e(TAG, "query book list,list type:" + bookList.getClass());
            for (Book book : bookList) {
                Log.e(TAG, "query book list:" + book.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addBook(View view) {
        try {
            Book book = new Book();
            book.setBookId(count++);
            book.setBookName(System.currentTimeMillis() + "");
            iBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBookListManual(View view) {
        // getBookListManual此方法回调是在主线程

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        Log.e(TAG, "getBookListManual 是否是主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
        try {
            List<Book> bookList = iBookManagerManual.getBookList();
            Log.e(TAG, "query book list,list type:" + bookList.getClass());
            for (Book book : bookList) {
                Log.e(TAG, "query book list:" + book.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//            }
//        }).start();
    }

    public void addBookManual(View view) {
        try {
            Book book = new Book();
            book.setBookId(count++);
            book.setBookName(System.currentTimeMillis() + "");
            iBookManagerManual.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getComponyListManual(View view) {
        try {
            List<Compony> componyList = iComponyManagerManual.getComponyList();
            Log.e(TAG, "query compony list,list type:" + componyList.getClass());
            for (Compony compony : componyList) {
                Log.e(TAG, "query compony list:" + compony.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addComponyManual(View view) {
        try {
            Compony c = new Compony();
            c.setName("智慧树");
            c.setNumber(count);
            iComponyManagerManual.addCompony(c);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mBookConnection);
        unbindService(mBookManualConnection);
        unbindService(mComponyManualConnection);
    }

    /*

   1.不使用new Thread().start();
   // 创建
07-14 20:23:29.406 13200-13200/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualImpl: myPid：13200 BookManagerManualImpl:是否是主线程：true
07-14 20:23:29.406 13200-13200/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualService: myPid：13200 onCreate:是否是主线程：true
07-14 20:23:29.408 13200-13200/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualService: myPid：13200 onBind:是否是主线程：true
07-14 20:23:29.413 13137-13137/com.ericzhang.androidartpractice E/Chapter2MainActivity: myPid：13137 onServiceConnected: mBookManualConnection 是否是主线程：true
07-14 20:23:29.415 13137-13137/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：13137 asInterface:是否是主线程：true
07-14 20:23:29.416 13137-13137/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：13137 Proxy:是否是主线程：true

    // 调用
07-14 20:24:05.991 13137-13137/com.ericzhang.androidartpractice E/Chapter2MainActivity: getBookListManual 是否是主线程：true
07-14 20:24:05.992 13137-13137/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：13137 Proxy.getBookList:是否是主线程：true
07-14 20:24:05.992 13200-13215/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualImpl: myPid：13200 onTransact:是否是主线程：false
07-14 20:24:05.993 13200-13215/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualService: myPid：13200 getBookList:是否是主线程：false
07-14 20:24:05.993 13200-13215/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualImpl: myPid：13200 onTransact end:是否是主线程：false
07-14 20:24:05.995 13137-13137/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：13137 Proxy.getBookList end:是否是主线程：true
07-14 20:24:05.995 13137-13137/com.ericzhang.androidartpractice E/Chapter2MainActivity: query book list,list type:class java.util.ArrayList
    query book list:Book{bookId=1, bookName='阿里巴巴'}
    query book list:Book{bookId=2, bookName='腾讯'}

    2.全部使用new Thread().start();
    // 创建
07-14 20:18:30.696 11954-11954/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualImpl: myPid：11954 BookManagerManualImpl:是否是主线程：true
07-14 20:18:30.697 11954-11954/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualService: myPid：11954 onCreate:是否是主线程：true
07-14 20:18:30.698 11954-11954/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualService: myPid：11954 onBind:是否是主线程：true
07-14 20:18:30.716 11913-11997/com.ericzhang.androidartpractice E/Chapter2MainActivity: myPid：11913 onServiceConnected: mBookManualConnection 是否是主线程：false
07-14 20:18:30.718 11913-11997/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：11913 asInterface:是否是主线程：false
    myPid：11913 Proxy:是否是主线程：false

    // 调用
07-14 20:19:38.102 11913-12243/com.ericzhang.androidartpractice E/Chapter2MainActivity: getBookListManual 是否是主线程：false
07-14 20:19:38.103 11913-12243/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：11913 Proxy.getBookList:是否是主线程：false
07-14 20:19:38.103 11954-11989/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualImpl: myPid：11954 onTransact:是否是主线程：false
07-14 20:19:38.103 11954-11989/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualService: myPid：11954 getBookList:是否是主线程：false
07-14 20:19:38.104 11954-11989/com.ericzhang.androidartpractice:remote.book.manual E/BookManagerManualImpl: myPid：11954 onTransact end:是否是主线程：false
07-14 20:19:38.105 11913-12243/com.ericzhang.androidartpractice E/BookManagerManualImpl: myPid：11913 Proxy.getBookList end:是否是主线程：false
07-14 20:19:38.106 11913-12243/com.ericzhang.androidartpractice E/Chapter2MainActivity: query book list,list type:class java.util.ArrayList
    query book list:Book{bookId=1, bookName='阿里巴巴'}
    query book list:Book{bookId=2, bookName='腾讯'}


     */


}
