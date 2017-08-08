package com.thinklib;


import android.app.Application;

import com.thinkcore.network.INetChangeListener;
import com.thinkcore.network.TNetWorkUtil;
import com.thinklib.event.TEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by banketree on 2017/7/27.
 */

public class LibApplication extends Application implements INetChangeListener {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onConnect(TNetWorkUtil.netType type) {
    }

    @Override
    public void onDisConnect() {
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processEvent(TEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void processStickyEvent(TEvent event) {
    }
}
