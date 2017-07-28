package com.thinklib;

import com.thinkcore.TApplication;
import com.thinkcore.event.TEvent;
import com.thinkcore.utils.network.TNetWorkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by banketree on 2017/7/27.
 */

public class LibApplication extends TApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConnect(TNetWorkUtil.netType type) {
        super.onConnect(type);
    }


    @Override
    public void onDisConnect() {
        super.onDisConnect();
    }

    @Override
    protected void onExitApplication() {
        super.onExitApplication();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void appExit(Boolean isBackground) {
        super.appExit(isBackground);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processEvent(TEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void processStickyEvent(TEvent event) {
    }
}
