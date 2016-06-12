package com.sanimator_lib;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by zjl on 2016/5/25.
 */
public class SAnimImageView extends ImageView {
    private float mPosX;
    private float mPosY;
    private float mScaleX;
    private float mScaleY;
    private float mAlpha;
    private float mRotation;

    public SAnimImageView(Context context) {
        super(context);
    }

    @Override
    public void setX(float x) {
        mPosX = x;
    }

    @Override
    public float getX() {
        return mPosX;
    }

    @Override
    public void setY(float y) {
        mPosY = y;
    }

    @Override
    public float getY() {
        return mPosY;
    }

    @Override
    public void setScaleX(float scaleX) {
        mScaleX = scaleX;
    }

    @Override
    public float getScaleX() {
        return mScaleX;
    }

    @Override
    public void setScaleY(float scaleY) {
        mScaleY = scaleY;
    }

    @Override
    public float getScaleY() {
        return mScaleY;
    }

    @Override
    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    @Override
    public float getAlpha() {
        return mAlpha;
    }

    @Override
    public void setRotation(float rotation) {
        mRotation = rotation;
    }

    @Override
    public float getRotation() {
        return mRotation;
    }
}
