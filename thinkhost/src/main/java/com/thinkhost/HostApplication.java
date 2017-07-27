package com.thinkhost;

import android.content.Context;
import android.content.res.Configuration;

import com.qihoo360.replugin.RePlugin;
import com.thinklib.LibApplication;

/**
 * Created by banketree on 2017/7/27.
 */

//         RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("demo1", "com.qihoo360.replugin.sample.demo1.MainActivity"));
public class HostApplication extends LibApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // ======= REPLUGIN =======
        RePlugin.App.attachBaseContext(this);
        // ========================
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // ======= REPLUGIN =======
        RePlugin.App.onCreate();
        // ========================
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // ======= REPLUGIN =======
        RePlugin.App.onLowMemory();
        // ========================
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        // ======= REPLUGIN =======
        RePlugin.App.onTrimMemory(level);
        // ========================
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // ======= REPLUGIN =======
        RePlugin.App.onConfigurationChanged(newConfig);
        // ========================
    }
}
