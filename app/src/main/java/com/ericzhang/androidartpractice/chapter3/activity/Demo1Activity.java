package com.ericzhang.androidartpractice.chapter3.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter3.ui.HorizontalScrollViewEx;
import com.ericzhang.androidartpractice.chapter3.utils.MyUtils;

import java.util.ArrayList;

public class Demo1Activity extends Activity {

    private static final String TAG = "Demo1Activity";

    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();

        mListContainer = findViewById(R.id.container);
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;
        Log.e(TAG, "screenWidth: " + screenWidth);
        Log.e(TAG, "screenHeight: " + screenHeight);

        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Demo1Activity.this, "click item", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
