package com.thinkcore.task;

import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TTask extends AsyncTask {
    private final static String TAG = TTask.class.getCanonicalName();
    private static ExecutorService mExecutorService;

    private static int Update = 1;

    static {
        // SINGLE_TASK_EXECUTOR = (ExecutorService) Executors
        // .newSingleThreadExecutor();
        mExecutorService = (ExecutorService) Executors.newFixedThreadPool(30);// 30个线程
        // mExecutorService = (ExecutorService) Executors.newCachedThreadPool();
    }

    ;

    public TTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }


    public void newExecute(Object[] params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//hasHoneycomb
            executeOnExecutor(mExecutorService, params);
        } else {
            execute(params);
        }
    }

    public void newExecute() {
        newExecute(null);
    }
}
