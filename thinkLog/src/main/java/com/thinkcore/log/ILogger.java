package com.thinkcore.log;

import android.content.Context;

interface ILogger {
    void v(String tag, String message);

    void d(String tag, String message);

    void i(String tag, String message);

    void w(String tag, String message);

    void e(String tag, String message);

    void open(Context context);

    void close();

    void println(int priority, String tag, String message);
}
