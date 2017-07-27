package com.thinklib;

import com.thinkcore.TApplication;
import com.thinkcore.utils.network.TNetWorkUtil;

/**
 * Created by banketree on 2017/7/27.
 */

public class LibApplication extends TApplication {

    @Override
    public void onCreate() {
        super.onCreate();
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
    }
}
