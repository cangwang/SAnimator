package com.sanimator_lib;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;


//import com.yylive.framework.util.log.MLog;

import com.sanimator_lib.core.DrawingThread;


/**
 * Created by zjl on 2016/4/26.
 */
public class SAnimatorTexture extends TextureView implements TextureView.SurfaceTextureListener {
    private DrawingThread mThread;

    public SAnimatorTexture(Context context) {
        super(context);
        init();
    }

    public SAnimatorTexture(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
        setOpaque(false);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        MLog.info(this, "onSurfaceTextureAvailable ========== VISIBLE");
//        setVisibility(VISIBLE);
        mThread = new DrawingThread(new Surface(surface));
        mThread.updateSize(width, height);
        mThread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        mThread.updateSize(width, height);
        if (mThread != null) {
            mThread.setDirty(true);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        MLog.info(this, "onSurfaceTextureDestroyed ========== INVISIBLE");
//        setVisibility(INVISIBLE);
        setBackgroundDrawable(null);
        if (mThread != null) {
            mThread.setStop(true);
            mThread = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void addTargetAnimator(STargetAnimator animator) {
        if (mThread != null) {
            mThread.addTargetAnimator(animator);
            mThread.setDirty(true);
        }
    }

    public void destory() {
        if (mThread != null) {
            mThread.setStop(true);
            mThread = null;
        }
    }
}
