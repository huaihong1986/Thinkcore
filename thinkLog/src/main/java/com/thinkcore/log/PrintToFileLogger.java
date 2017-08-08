package com.thinkcore.log;

import android.content.Context;
import android.text.TextUtils;

import com.thinkcore.storage.Storage;
import com.thinkcore.storage.TFilePath;
import com.thinkcore.storage.TStorageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

//打印到sdcard上面的日志类
public class PrintToFileLogger implements ILogger {
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private String nameString = "", cacheDirName = "";
    private Storage storage = null;

    public PrintToFileLogger(Context context) {
        open(context);
    }

    public void open(Context context) {
        try {
            nameString = getCurrentTimeString() + ".log";
            TFilePath filePath = new TFilePath(context);
            boolean result = false;
            if (TStorageUtils.isExternalStoragePresent()) {
                storage = filePath.getExternalStorage();
            } else {
                storage = filePath.getInternalStorage();
            }

            cacheDirName = filePath.getCacheDir();
            result = storage.createFile(cacheDirName, nameString, "");
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void d(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        println(DEBUG, tag, message);
    }

    @Override
    public void e(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        println(ERROR, tag, message);
    }

    @Override
    public void i(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        println(INFO, tag, message);
    }

    @Override
    public void v(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        println(VERBOSE, tag, message);
    }

    @Override
    public void w(String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        println(WARN, tag, message);
    }

    @Override
    public void println(int priority, String tag, String message) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return;
        String printMessage = "";
        switch (priority) {
            case VERBOSE:
                printMessage = "[V]|"
                        + tag
                        + "|" + message;
                break;
            case DEBUG:
                printMessage = "[D]|"
                        + tag
                        + "|" + message;
                break;
            case INFO:
                printMessage = "[I]|"
                        + tag
                        + "|" + message;
                break;
            case WARN:
                printMessage = "[W]|"
                        + tag
                        + "|" + message;
                break;
            case ERROR:
                printMessage = "[E]|"
                        + tag
                        + "|" + message;
                break;
            default:

                break;
        }
        println(printMessage);

    }

    public void println(String message) {
        if (TextUtils.isEmpty(nameString)
                || !TStorageUtils.isExternalStorageWrittenable())
            return;
        try {
            String content = getFullTime(System.currentTimeMillis()) + "=====>" + message;
            if (storage != null) {
                if (storage.isFileExist(cacheDirName, nameString)) {
                    storage.appendFile(cacheDirName,
                            nameString, content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        nameString = "";
    }

    public String getFullTime(long time) {
        SimpleDateFormat fulTimeFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String strTime;
        Date date = new Date(time);
        strTime = fulTimeFormat.format(date);
        date = null;
        fulTimeFormat = null;
        return strTime;
    }

    private String getCurrentTimeString() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String result = simpleDateFormat.format(now);
        now = null;
        simpleDateFormat = null;
        return result.replaceAll(":", "-");
    }
}
