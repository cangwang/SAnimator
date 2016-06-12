package com.sanimator_lib.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Surface;
import android.widget.ImageView;

import com.sanimator_lib.STargetAnimator;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zjl on 2016/6/12.
 */
public class DrawingThread extends Thread {
    private static final int FRAME = 30;
    private Surface mHolder;
    private Paint mPaint;
    private Rect mSurfaceRect;
    private Matrix mMatrix;
    private boolean mIsStop = false;
    private Vector<STargetAnimator> mTargetAnimVec = new Vector<>();
    private AtomicBoolean mIsDirty = new AtomicBoolean(true);

    public DrawingThread(Surface holder) {
        super("DrawingThread");
        mHolder = holder;
        mSurfaceRect = new Rect();
        mMatrix = new Matrix();
        mPaint = new Paint();
//            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
//            mPaint.setAntiAlias(true);
//            mPaint.setDither(true);
    }

    public void updateSize(int width, int height) {
        mSurfaceRect.set(0, 0, width, height);
    }

    public void addTargetAnimator(STargetAnimator animator) {
        if (null != animator) {
            synchronized (mTargetAnimVec) {
                mTargetAnimVec.add(animator);
            }
        }
    }

    public void setDirty(boolean isDirty) {
        mIsDirty.set(isDirty);
    }

    @Override
    public void run() {
        while (mIsStop == false) {
            try {
                draw();
                sleep(FRAME);
            } catch (InterruptedException e) {
//                    MLog.error(this, e);
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        //渲染一帧
        if (mIsDirty.getAndSet(false) == true) {
            Canvas c = mHolder.lockCanvas(mSurfaceRect);
            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (c != null) {
                synchronized (mTargetAnimVec) {
                    Iterator<STargetAnimator> iter = mTargetAnimVec.iterator();
                    while(iter.hasNext()) {
                        mIsDirty.set(true);
                        STargetAnimator animator = iter.next();
                        boolean isEnd = animator.update();
                        if (animator.isEndRemove() == true && isEnd == true) {
                            animator.destory();
                            iter.remove();
                        } else {
                            ImageView imageView = (ImageView)animator.getTargetView();
                            if(imageView.getDrawable() instanceof BitmapDrawable) {
                                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                                int alpha = (int)(imageView.getAlpha() * 255);
                                mPaint.setAlpha(alpha);
                                mMatrix.reset();
                                mMatrix.postScale(imageView.getScaleX(), imageView.getScaleY(), image.getWidth() / 2, image.getHeight());
                                mMatrix.postTranslate(imageView.getX(), imageView.getY());
                                mMatrix.preRotate(imageView.getRotation(), image.getWidth()/2, image.getHeight()/2);
                                c.drawBitmap(image, mMatrix, mPaint);
                            }
                        }
                    }
                }
            }
            mHolder.unlockCanvasAndPost(c);
        }
    }

    public void setStop(boolean isStop) {
        mIsStop = isStop;
        destory();
    }

    public void destory() {
        synchronized (mTargetAnimVec) {
            for (STargetAnimator targetAnimator : mTargetAnimVec) {
                targetAnimator.destory();
            }
            mTargetAnimVec.clear();
        }
    }
}
