package com.ericzhang.androidartpractice.chapter7.anim;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 自定义View动画，3D翻转效果，来自Android ApiDemos
 */
public class Rotate3DAnimation extends Animation {

    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;

    private Camera mCamera;

    public Rotate3DAnimation(Context context, AttributeSet attrs, float mFromDegrees, float mToDegrees, float mCenterX, float mCenterY, float mDepthZ, boolean mReverse) {
        super(context, attrs);
        this.mFromDegrees = mFromDegrees;
        this.mToDegrees = mToDegrees;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mDepthZ = mDepthZ;
        this.mReverse = mReverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }

        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
