package com.thinkcore.activity;

import java.util.HashMap;

import com.thinkcore.TApplication;
import com.thinkcore.dialog.progress.TDialogManager;
import com.thinkcore.utils.TActivityUtils;
import com.thinkcore.utils.TToastUtils;
import com.thinkcore.view.autolayout.AutoLayoutActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


//界面
public abstract class TActivity extends AutoLayoutActivity {
    private String TAG = TActivity.class.getCanonicalName();

    public enum Status {
        NONE, CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTORYED
    }

    protected Context mContext;
    protected Status mStatus;
    protected HashMap<Integer, TActivityUtils.IActivityResult> mIActivityResult = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mStatus = Status.CREATED;

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
        mIActivityResult.clear();
        mStatus = Status.DESTORYED;
        mContext = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TActivityUtils.IActivityResult iActivityResult = mIActivityResult.get(requestCode);
        if (iActivityResult != null) {
            iActivityResult.onActivityResult(resultCode, data);
            mIActivityResult.remove(requestCode);
        }
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

    public HashMap<Integer, TActivityUtils.IActivityResult> getIActivityResult() {
        return mIActivityResult;
    }
}
