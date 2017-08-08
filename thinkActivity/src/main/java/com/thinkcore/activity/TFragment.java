package com.thinkcore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.AutoLayoutFragment;

public class TFragment extends AutoLayoutFragment {
    protected View that;

    @Override
    public void onCreate(Bundle savedInstanceState) {// 当Activity中的onCreate方法执行完后调用
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onDetach() {// Fragment和Activity解除关联的时候调用
        super.onDetach();
    }
}
