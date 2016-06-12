package com.sanimator_lib;

import android.animation.TypeEvaluator;
import android.view.View;

import com.sanimator_lib.core.SAnimatorListener;
import com.sanimator_lib.core.SEvaluatorListener;
import com.sanimator_lib.data.BaseProperty;
import com.sanimator_lib.data.EvaluatorProperty;
import com.sanimator_lib.data.NormalProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjl on 2016/4/22.
 */
public class SAnimator {
    private List<BaseProperty> mTogetherPropList = new ArrayList<>();
    private SAnimatorListener mListener;
    private long mLastTime;
    private int mDuration;
    private int mDelay;

    public void ofFloat(String propertyName, float startVal, float endVal, int duration, int delay, SAnimatorListener listener) {
        NormalProperty obj = new NormalProperty(propertyName, startVal, endVal, duration, delay, listener);
        mTogetherPropList.add(obj);
    }

    public void ofSelfEvaluator(TypeEvaluator evaluator, Object start, Object end, int duration, int delay, SEvaluatorListener listener) {
        EvaluatorProperty obj = new EvaluatorProperty(evaluator, start, end, duration, delay, listener);
        mTogetherPropList.add(obj);
    }

    public void setDuration(int millisecond) {
        mDuration = millisecond;
        for (BaseProperty obj : mTogetherPropList) {
            if (obj.duration < 0) {
                obj.duration = mDuration;
            }
        }
    }

    private void setDelay(int millisecond) {
        mDelay = millisecond;
        for (BaseProperty obj : mTogetherPropList) {
            if (obj.delay < 0) {
                obj.delay = mDelay;
            }
        }
    }

    public void addListener(SAnimatorListener animatorListener) {
        mListener = animatorListener;
    }

    public void destory() {
        mTogetherPropList.clear();
    }

    public void start() {
        mLastTime = System.currentTimeMillis();
        if (null != mListener) {
            mListener.onAnimationStart(this);
        }
    }

    public boolean update(View view) {
        long delta = System.currentTimeMillis() - mLastTime;
        boolean isEnd = true;
        for (BaseProperty obj : mTogetherPropList) {
            if (!obj.isEnd) {
                isEnd = false;
                if (delta >= obj.delay || obj.delay <= 0) {
                    float startTime = delta - obj.delay;
                    if (startTime <= obj.duration && obj.duration > 0) {
                        float rate = startTime / (obj.duration + 0.0f);
                        if (!obj.isStart) {
                            obj.isStart = true;
                            if (obj.listener != null) {
                                obj.listener.onAnimationStart(this);
                            }
                        }
                        if (obj instanceof NormalProperty) {
                            NormalProperty normalProp = (NormalProperty)obj;
                            float val = rate * normalProp.diffVal;
                            float curVal = normalProp.startVal + val;
                            if ("scaleX".equals(normalProp.propName)) {
                                view.setScaleX(curVal);
                            } else if ("scaleY".equals(normalProp.propName)) {
                                view.setScaleY(curVal);
                            } else if ("alpha".equals(normalProp.propName)) {
                                view.setAlpha(curVal);
                            } else if ("x".equals(normalProp.propName)) {
                                view.setX(curVal);
                            } else if ("y".equals(normalProp.propName)) {
                                view.setY(curVal);
                            } else if ("rotation".equals(normalProp.propName)) {
                                view.setRotation(curVal);
                            }
                        } else if (obj instanceof EvaluatorProperty) {
                            EvaluatorProperty evalProp = (EvaluatorProperty)obj;
                            if (null != evalProp.listener && null != evalProp.typeEvaluator) {
                                ((SEvaluatorListener)evalProp.listener).onAnimationUpdate(evalProp.typeEvaluator.evaluate(rate, evalProp.start, evalProp.end));
                            }else if (null == evalProp.typeEvaluator) {
                                ((SEvaluatorListener)evalProp.listener).onAnimationUpdate(rate);
                            }
                        }
                    } else {
                        obj.isEnd = true;
                        if (obj instanceof NormalProperty) {
                            NormalProperty normalProp = (NormalProperty)obj;
                            if ("scaleX".equals(normalProp.propName)) {
                                view.setScaleX(normalProp.endVal);
                            } else if ("scaleY".equals(normalProp.propName)) {
                                view.setScaleY(normalProp.endVal);
                            } else if ("alpha".equals(normalProp.propName)) {
                                view.setAlpha(normalProp.endVal);
                            } else if ("x".equals(normalProp.propName)) {
                                view.setX(normalProp.endVal);
                            } else if ("y".equals(normalProp.propName)) {
                                view.setY(normalProp.endVal);
                            } else if ("rotation".equals(normalProp.propName)) {
                                view.setRotation(normalProp.endVal);
                            }
                        }
                        if (obj.listener != null) {
                            obj.listener.onAnimationEnd(this);
                        }
                    }
                }
            }
        }
        if (isEnd) {
            if (mListener != null) {
                mListener.onAnimationEnd(this);
            }
        }
        return isEnd;
    }
}
