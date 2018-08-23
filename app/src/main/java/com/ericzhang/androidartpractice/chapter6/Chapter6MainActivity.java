package com.ericzhang.androidartpractice.chapter6;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericzhang.androidartpractice.R;

public class Chapter6MainActivity extends Activity {

    private int count = 0;
    private ImageView imageView1;
    private TextView textView1;
    private TextView textViewTransition;
    private TextView scale;
    private TextView clip;
    private TextView custom_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_main);

        // ImageView只有设置src才有drawable，设置background不行
        // ImageView设置src与background的区别
        imageView1 = findViewById(R.id.imageView1);
        textView1 = findViewById(R.id.textView1);
        textViewTransition = findViewById(R.id.textViewTransition);
        scale = findViewById(R.id.scale);
        clip = findViewById(R.id.clip);
        custom_circle = findViewById(R.id.custom_circle);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                int level = count % 3;
                imageView1.setImageLevel(level);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                int level = count % 3;
                Drawable drawable = textView1.getBackground();
                drawable.setLevel(level);
            }
        });
        textViewTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) textViewTransition.getBackground();
                transitionDrawable.startTransition(1000);
            }
        });
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleDrawable scaleDrawable = (ScaleDrawable) scale.getBackground();
                scaleDrawable.setLevel(++count);
            }
        });
        clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipDrawable clipDrawable = (ClipDrawable) clip.getBackground();
                // 0~10000裁剪范围，0代表全部裁剪，10000代表不裁剪
                if (count > 10000) {
                    count = 0;
                } else {
                    count += 500;
                }
                clipDrawable.setLevel(count);
            }
        });
        custom_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 自定义Drawable无法在xml文件中直接使用
                CustomCircle customCircle = new CustomCircle(Color.RED);
                custom_circle.setBackground(customCircle);
            }
        });
    }
}
