package com.sanimator_lib.data;

import com.sanimator_lib.core.SAnimatorListener;

/**
 * Created by zjl on 2016/6/12.
 */
public class NormalProperty extends BaseProperty{
    public NormalProperty(String propertyName, float startVal, float endVal, int duration, int delay, SAnimatorListener listener) {
        this.propName = propertyName;
        this.startVal = startVal;
        this.endVal = endVal;
        this.diffVal = endVal - startVal;
        this.duration = duration;
        this.delay = delay;
        this.listener = listener;
    }
    public String propName;
    public float startVal;
    public float endVal;
    public float diffVal;
}
