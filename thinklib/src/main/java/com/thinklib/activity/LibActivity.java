package com.thinklib.activity;

import android.support.annotation.NonNull;

import com.thinkcore.activity.TActivity;
import com.thinkcore.event.TEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import pub.devrel.easypermissions.EasyPermissions;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
