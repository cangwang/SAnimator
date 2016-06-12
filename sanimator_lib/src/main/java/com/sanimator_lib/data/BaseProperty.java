package com.sanimator_lib.data;

import com.sanimator_lib.core.SAnimatorListener;

/**
 * Created by zjl on 2016/6/12.
 */
public class BaseProperty {
    public boolean isStart = false;
    public boolean isEnd = false;
    public int duration = -1;
    public int delay = -1;
    public SAnimatorListener listener;
}
