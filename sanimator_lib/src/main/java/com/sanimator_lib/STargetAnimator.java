package com.sanimator_lib;

import android.view.View;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zjl on 2016/4/22.
 */
public class STargetAnimator {
    private Queue<SAnimator> mSequnenceAnimQueue = new LinkedList<>();
    private View mTargetView;
    private SAnimator mCurAnimator;
    private boolean mIsEndRemove = true;

    public void addSequenceAnimator(SAnimator animator) {
        if (animator == null) {
//            MLog.error(this, "animator is null");
        }
        mSequnenceAnimQueue.add(animator);
    }

    public void setTargetView(View view) {
        if (view == null) {
//            MLog.error(this, "View is null");
        }
        mTargetView = view;
    }



    public View getTargetView() {
        return mTargetView;
    }

    public void setEndRemove(boolean value) {
        mIsEndRemove = value;
    }

    public boolean isEndRemove() {
        return mIsEndRemove;
    }

    public void start() {
        doNextAnim();
    }

    public boolean update() {
        if (null != mCurAnimator) {
            boolean isEnd = mCurAnimator.update(mTargetView);
            if (isEnd) {
                doNextAnim();
            }
            return (mCurAnimator == null);
        } else {
            return true;
        }
    }

    private void doNextAnim() {
        mCurAnimator = mSequnenceAnimQueue.poll();
        if (null != mCurAnimator) {
            mCurAnimator.start();
        }
    }

    public void destory() {
        for (SAnimator animator : mSequnenceAnimQueue) {
            animator.destory();
        }
        mSequnenceAnimQueue.clear();
    }
}
