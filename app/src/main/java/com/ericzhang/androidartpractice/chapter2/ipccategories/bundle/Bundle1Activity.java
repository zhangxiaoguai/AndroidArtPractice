package com.ericzhang.androidartpractice.chapter2.ipccategories.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;

public class Bundle1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle1);
    }

    public void bundle2(View view) {
        Intent intent = new Intent(this, Bundle2Activity.class);

        Bundle bundle = new Bundle();
        bundle.putString("name", "EricZhang");
        Book book = new Book();
        book.setBookId(123);
        book.setBookName("金瓶梅");
        bundle.putParcelable("book", book);
        intent.putExtra("bundle", bundle);

        startActivity(intent);
    }
}
