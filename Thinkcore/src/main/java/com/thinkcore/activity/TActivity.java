package com.thinkcore.activity;

import java.util.ArrayList;

import com.thinkcore.TApplication;
import com.thinkcore.dialog.TDialogManager;
import com.thinkcore.event.TEvent;
import com.thinkcore.utils.TActivityUtils;
import com.thinkcore.utils.TToastUtils;
import com.thinkcore.view.autolayout.AutoLayout;
import com.thinkcore.view.autolayout.AutoLayoutActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


//界面
public abstract class TActivity extends Activity {
    private String TAG = TActivity.class.getCanonicalName();

    public enum Status {
        NONE, CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTORYED
    }

    protected Context mContext;
    protected Status mStatus;
    protected ArrayList<String> mActivityParameters = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mStatus = Status.CREATED;

        initActivityParameter(getIntent());
        TActivityManager.getInstance().addActivity(this);// 添加activity
    }


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        mStatus = Status.STARTED;
        super.onStart();
    }

    @Override
    protected void onResume() {
        mStatus = Status.RESUMED;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mStatus = Status.PAUSED;
        super.onPause();
    }

    @Override
    protected void onStop() {
        mStatus = Status.STOPPED;
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        TActivityManager.getInstance().removeActivity(this);

        TDialogManager.hideProgressDialog(this);

        if (mActivityParameters != null)
            mActivityParameters.clear();
        mActivityParameters = null;

        mStatus = Status.DESTORYED;

        mContext = null;
        super.onDestroy();
    }

//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        View view = AutoLayoutActivity.onCreateView(name, context, attrs);
//        if (view != null) return view;
//        return super.onCreateView(parent, name, context, attrs);
//    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = AutoLayoutActivity.onCreateView(name, context, attrs);
        if (view != null) return view;
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        AutoLayout.getInstance().auto(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        AutoLayout.getInstance().auto(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        AutoLayout.getInstance().auto(this);
    }

    //	AutoLayoutActivity.onCreateView()

    private void initActivityParameter(Intent intent) {
        if (mActivityParameters == null)
            return;
        mActivityParameters.clear(); // Activity固定参数
        mActivityParameters.add(intent
                .getStringExtra(TActivityUtils.FIELD_DATA0));
        mActivityParameters.add(intent
                .getStringExtra(TActivityUtils.FIELD_DATA1));
        mActivityParameters.add(intent
                .getStringExtra(TActivityUtils.FIELD_DATA2));
        mActivityParameters.add(intent
                .getStringExtra(TActivityUtils.FIELD_DATA3));
        mActivityParameters.add(intent
                .getStringExtra(TActivityUtils.FIELD_DATA4));
        mActivityParameters.add(intent
                .getStringExtra(TActivityUtils.FIELD_DATA5));
    }

    protected ArrayList<String> getActivityParameter() {
        return mActivityParameters;
    }

    public Status getStatus() {
        return mStatus;
    }

    public boolean isActivityByStatus() {
        return mStatus != Status.DESTORYED && mStatus != Status.PAUSED
                && mStatus != Status.STOPPED;
    }

    protected void makeText(String content) {
        TToastUtils.makeText(mContext, content);
    }

    public static String getResString(int id) {
        return TApplication.getInstance().getString(id);
    }
}
