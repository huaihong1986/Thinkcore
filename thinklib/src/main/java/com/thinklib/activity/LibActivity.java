package com.thinklib.activity;

import com.thinkcore.activity.TActivity;
import com.thinkcore.event.TEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by banketree on 2017/7/27.
 */

public class LibActivity extends TActivity {

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processEvent(TEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void processStickyEvent(TEvent event) {
    }
}
