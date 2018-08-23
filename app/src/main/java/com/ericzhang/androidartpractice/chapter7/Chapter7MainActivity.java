package com.ericzhang.androidartpractice.chapter7;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.ericzhang.androidartpractice.R;
import com.ericzhang.androidartpractice.chapter7.anim.Rotate3DAnimation;

public class Chapter7MainActivity extends Activity {

    private static final String TAG = "Chapter7MainActivity";

    private Button btn_view_anim;
    private Button btn_view_anim_code;
    private Button btn_view_anim_3d;
    private Button btn_frame_anim;
    private Button btn_property;
    private Button btn_property_background;
    private Button btn_property_set;
    private Button btn_x2_view;
    private Button btn_x2_property;
    private Button btn_x2_property2;

    private Animation viewAnimation;
    private AnimationSet viewAnimationSet;
    private Rotate3DAnimation rotate3DAnimation;
    private ObjectAnimator backgroundColorAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_main);

        // View动画，Frame动画，属性动画都需要运行在UI线程

        btn_view_anim = findViewById(R.id.btn_view_anim);
        btn_view_anim_code = findViewById(R.id.btn_view_anim_code);
        btn_view_anim_3d = findViewById(R.id.btn_view_anim_3d);
        btn_frame_anim = findViewById(R.id.btn_frame_anim);
        btn_property = findViewById(R.id.btn_property);
        btn_property_background = findViewById(R.id.btn_property_background);
        btn_property_set = findViewById(R.id.btn_property_set);
        btn_x2_view = findViewById(R.id.btn_x2_view);
        btn_x2_property = findViewById(R.id.btn_x2_property);
        btn_x2_property2 = findViewById(R.id.btn_x2_property2);
        btn_view_anim.animate().translationX(20f).withLayer().start();

        initAnim();

        btn_view_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_view_anim");
                btn_view_anim.startAnimation(viewAnimation);
            }
        });
        btn_view_anim_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_view_anim_code");
                btn_view_anim_code.startAnimation(viewAnimationSet);
            }
        });
        btn_view_anim_3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_view_anim_3d");

                btn_view_anim_3d.startAnimation(rotate3DAnimation);
            }
        });
        btn_frame_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_frame_anim");

                AnimationDrawable animationDrawable = (AnimationDrawable) btn_frame_anim.getBackground();
                if (animationDrawable.isRunning()) {
                    animationDrawable.stop();
                } else {
                    animationDrawable.start();
                }
            }
        });

        btn_x2_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_x2_view");

                // 1.View动画方式
                Animation btn_x2_animation = AnimationUtils.loadAnimation(Chapter7MainActivity.this, R.anim.scale_anim_x2);
                btn_x2_view.startAnimation(btn_x2_animation);

                // 字体大小整个都会被拉伸，对View的映像做操作
            }
        });
        btn_x2_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_x2_property");

                // 2.属性动画方式直接对width属性进行动画设置
                // 没有效果，width虽然有get/set方法(即：Button和TextView中虽然有getWidth()和setWidth()方法)，但是Button和TextView中的getWidth()和setWidth()操作的不是同一个属性
//                ObjectAnimator btn_x2_animation_object = ObjectAnimator.ofInt(btn_x2_property, "width", (int) (btn_x2_property.getWidth() * 1.2)).setDuration(6000);
//                btn_x2_animation_object.start();

                // 解决1：通过包装类来实现属性动画
                ViewWrap viewWrap = new ViewWrap(btn_x2_property);
                int originalWidth = btn_x2_property.getWidth();
                int targetWidth = (int) (originalWidth * 1.2);
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(viewWrap, "width", originalWidth, targetWidth);
                objectAnimator.setDuration(3000);// 这里持续时间过长会感觉抖动
                objectAnimator.start();

                // 对View的属性做操作，View的字体大小，影像等都不会受影响
            }
        });

        btn_x2_property2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: btn_x2_property2");

                // 解决2：通过ValueAnimator
                final int originalWidth = btn_x2_property2.getWidth();
                final int targetWidth = (int) (originalWidth * 1.2);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    private IntEvaluator mEvaluator = new IntEvaluator();

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (int) animation.getAnimatedValue();
                        Log.e(TAG, "onAnimationUpdate  currentValue:" + currentValue);

                        float fraction = animation.getAnimatedFraction();
                        btn_x2_property2.getLayoutParams().width = mEvaluator.evaluate(fraction, originalWidth, targetWidth);
                        btn_x2_property2.requestLayout();
                    }
                });
                // 这里持续时间过长会感觉抖动
                valueAnimator.setDuration(3000).start();
            }
        });
    }

    private void initAnim() {
        // 1.xml方式
        viewAnimation = AnimationUtils.loadAnimation(Chapter7MainActivity.this, R.anim.transition_anim);
        viewAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG, "viewAnimation onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG, "viewAnimation onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG, "viewAnimation onAnimationRepeat");
            }
        });

        // 2.代码动态方式
        viewAnimationSet = new AnimationSet(false);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 100, 0, 100);
        translateAnimation.setDuration(100);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 90);
        rotateAnimation.setDuration(400);

        viewAnimationSet.addAnimation(translateAnimation);
        viewAnimationSet.addAnimation(rotateAnimation);
        viewAnimationSet.setFillAfter(true);
        viewAnimationSet.setZAdjustment(Animation.ZORDER_NORMAL);
        viewAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG, "viewAnimationSet onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG, "viewAnimationSet onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG, "viewAnimationSet onAnimationRepeat");
            }
        });

        // 自定义View动画
        rotate3DAnimation = new Rotate3DAnimation(this, null, 0, 100, 0, 100, 50, true);
        rotate3DAnimation.setDuration(2000);

        // 属性动画
        // 默认时间间隔300ms，默认帧率10ms/帧
        backgroundColorAnim = ObjectAnimator.ofInt(btn_property_background, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        backgroundColorAnim.setDuration(3000);
        backgroundColorAnim.setEvaluator(new ArgbEvaluator());
        backgroundColorAnim.setRepeatCount(ValueAnimator.INFINITE);
        backgroundColorAnim.setRepeatMode(ValueAnimator.REVERSE);
        // 属性动画监听器
        // 可以继承AnimatorListenerAdapter实现自己想监听的事件
//        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter();
        // 或者直接设置
        backgroundColorAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "propertyAnimBackground: onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "propertyAnimBackground: onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "propertyAnimBackground: onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "propertyAnimBackground: onAnimationRepeat");
            }
        });
        // 监听整个动画过程，每播放一帧就会被调用一次
        backgroundColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "propertyAnimBackground: onAnimationUpdate");
            }
        });
    }

    public void layout_animation(View view) {
        startActivity(new Intent(this, LayoutAnimationActivity.class));
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
    }

    public void propertyAnimTranslationY(View view) {
        Log.e(TAG, "propertyAnimTranslationY");

        ObjectAnimator.ofFloat(btn_property, "translationY", -btn_property.getHeight()).start();
    }

    public void propertyAnimBackground(View view) {
        Log.e(TAG, "propertyAnimBackground");

        if (backgroundColorAnim.isRunning()) {
            backgroundColorAnim.cancel();
        } else {
            backgroundColorAnim.start();
        }
    }

    public void propertyAnimSet(View view) {
        Log.e(TAG, "propertyAnimSet");

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(btn_property_set, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(btn_property_set, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(btn_property_set, "rotation", 0, -90),
                ObjectAnimator.ofFloat(btn_property_set, "translationX", 0, 90),
                ObjectAnimator.ofFloat(btn_property_set, "translationY", 0, 90),
                ObjectAnimator.ofFloat(btn_property_set, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(btn_property_set, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(btn_property_set, "alpha", 1, 0.25f, 1)
        );
        set.setDuration(5 * 1000).start();
    }

    public void propertyAnimSetXML(View view) {
        Log.e(TAG, "propertyAnimSetXML");

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.property_animator_set);
        animatorSet.setTarget(view);
        animatorSet.start();
    }

    private static class ViewWrap {

        private View mTarget;

        public ViewWrap(View view) {
            mTarget = view;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

    }


    /**
     *
     * 假如对一个View做Alpha转化，需要先将View绘制出来，然后做Alpha转化，最后将转换后的效果绘制在界面上。
     * 通俗点说，做Alpha转化就需要对当前View绘制两遍，可想而知，绘制效率会大打折扣，耗时会翻倍，所以Alpha还是慎用。
     * 如果一定做Alpha转化的话，可以采用缓存的方式。
     * view.setLayerType(LAYER_TYPE_HARDWARE);
     * doSomeThing();
     * view.setLayerType(LAYER_TYPE_NONE);
     * 通过setLayerType方式可以将当前界面缓存在GPU中，这样不需要每次绘制原始界面，但是GPU内存是相当宝贵的，所以用完要马上释放掉。
     *
     * 使用View layers（硬件层），我们可以将view渲染入一个非屏幕区域缓冲区（off-screen buffer，前面透明度部分提到过），并且根据我们的需求来操控它。
     * 这个功能主要是针对动画，因为它能让复杂的动画效果更加的流畅。而不使用硬件层的话，View会在动画属性（例如coordinate, scale, alpha值等）改变之后进行一次刷新。
     * 而对于相对复杂的view，这一次刷新又会连带它所有的子view进行刷新，并各自重新绘制，相当的耗费性能。使用View layers，通过调用硬件层，GPU直接为我们的view创建一个结构，并且不会造成view的刷新。
     * 而我们可以在避免刷新的情况下对这个结构进行进行很多种的操作，例如x/y位置变换，旋转，透明度等等。总之，这意味着我们可以对一个让一个复杂view执行动画的同时，又不会刷新！这会让动画看起来更加的流畅。
     *
     * 详细参考FlashOnceAnimation
     *
     */

}
