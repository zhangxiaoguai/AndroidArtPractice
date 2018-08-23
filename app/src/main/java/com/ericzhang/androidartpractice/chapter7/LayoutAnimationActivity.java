package com.ericzhang.androidartpractice.chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ericzhang.androidartpractice.R;

import java.util.ArrayList;

public class LayoutAnimationActivity extends Activity {

    private ListView list_view;
    private ListView list_view2;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);

        initView();
        initData();
    }

    private void initView() {
        list_view = findViewById(R.id.list_view);
        list_view2 = findViewById(R.id.list_view2);
    }

    private void initData() {
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add("name " + i);
        }
        adapter = new ArrayAdapter<>(this, R.layout.content_list_item, R.id.name, dataList);
        list_view.setAdapter(adapter);
    }

    public void layout_animation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.layout_anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        list_view2.setLayoutAnimation(controller);
        list_view2.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);

        // tip:
        // 直接调用Activity的onDestroy方法不会将Activity在任务栈中清除
        // 点击back键相当于调用Activity.finish()，作用1出栈，作用2调用Activity的onDestroy
    }
}
