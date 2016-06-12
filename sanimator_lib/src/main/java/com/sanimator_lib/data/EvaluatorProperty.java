package com.sanimator_lib.data;

import android.animation.TypeEvaluator;

import com.sanimator_lib.core.SEvaluatorListener;

/**
 * Created by zjl on 2016/6/12.
 */
public class EvaluatorProperty extends BaseProperty{
    public EvaluatorProperty(TypeEvaluator typeEvaluator, Object start, Object end, int duration, int delay, SEvaluatorListener listener) {
        this.typeEvaluator = typeEvaluator;
        this.listener = listener;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.delay = delay;
    }
    public TypeEvaluator typeEvaluator;
    public Object start;
    public Object end;
}
