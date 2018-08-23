package com.ericzhang.androidartpractice.chapter2.ipccategories.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;

public class ContentProviderActivity extends Activity {

    private static final String TAG = ContentProviderActivity.class.getSimpleName();

    private Uri bookUri = Uri.parse("content://com.ericzhang.androidartpractice.chapter2.ipccategories.contentprovider/book");
    private Uri userUri = Uri.parse("content://com.ericzhang.androidartpractice.chapter2.ipccategories.contentprovider/user");

    private int bookCount = 6;
    private int userCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
    }

    public void addBook(View view) {
        ContentValues bookValues = new ContentValues();
        bookValues.put("_id", bookCount);
        bookValues.put("name", "new book:" + bookCount);
        bookCount++;
        getContentResolver().insert(bookUri, bookValues);

        ContentValues userValues = new ContentValues();
        userValues.put("_id", userCount);
        userValues.put("name", "new user:" + userCount);
        userValues.put("sex", userCount);
        userCount++;
        getContentResolver().insert(userUri, userValues);
    }

    public void deleteBook(View view) {
        Cursor bookCursor = getContentResolver().
                query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.setBookId(bookCursor.getInt(0));
            book.setBookName(bookCursor.getString(1));
            Log.e(TAG, "query book: " + book);
        }
        bookCursor.close();

        Cursor userCursor = getContentResolver().
                query(userUri, new String[]{"_id", "name"}, null, null, null);
        while (userCursor.moveToNext()) {
            Book book = new Book();
            book.setBookId(userCursor.getInt(0));
            book.setBookName(userCursor.getString(1));
            Log.e(TAG, "query user: " + book);
        }
        userCursor.close();
    }

    public void updateBook(View view) {
    }

    public void queryBook(View view) {
    }
}
