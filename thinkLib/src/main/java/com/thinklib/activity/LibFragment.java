package com.thinklib.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkcore.activity.TFragment;
import com.thinklib.event.TEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import pub.devrel.easypermissions.EasyPermissions;

public class LibFragment extends TFragment {
    protected View that;

    @Override
    public void onCreate(Bundle savedInstanceState) {// 当Activity中的onCreate方法执行完后调用
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {// onCreate之后 显示的组件（为Fragment加载布局时调用）
        return super.onCreateView(inflater, container, savedInstanceState);// 父类返回null
    }

    @Override
    public void onAttach(Activity activity) { // onCreate之前触发（Fragment和Activity建立关联的时候调用）
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {// onCreateView 之后
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {// Fragment中的布局被移除时调用
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {// Fragment和Activity解除关联的时候调用
        super.onDetach();
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

//    private void smsTask() {
//        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_SMS)) {
//            // Have permission, do the thing!
//            Toast.makeText(getActivity(),
//        } else {
//            // Request one permission
//            EasyPermissions.requestPermissions(this, getString(R.string.rationale_sms),
//                    RC_SMS_PERM, Manifest.permission.READ_SMS);
//        }
//    }
}
