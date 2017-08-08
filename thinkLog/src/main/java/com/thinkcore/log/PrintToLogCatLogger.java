package com.thinkcore.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


//打印到LogCat上面的日志类
public class PrintToLogCatLogger implements ILogger {
    @Override
    public void d(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        Log.e(tag, message);
    }

    @Override
    public void i(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        Log.i(tag, message);
    }

    @Override
    public void v(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        Log.v(tag, message);
    }

    @Override
    public void w(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        Log.w(tag, message);
    }

    @Override
    public void println(int priority, String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        Log.println(priority, tag, message);
    }

    @Override
    public void open(Context context) {
    }

    @Override
    public void close() {
    }
}
