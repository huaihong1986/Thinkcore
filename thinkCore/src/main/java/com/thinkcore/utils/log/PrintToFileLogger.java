package com.thinkcore.utils.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thinkcore.TApplication;
import com.thinkcore.storage.Storage;
import com.thinkcore.storage.TFilePath;
import com.thinkcore.storage.TStorageUtils;
import com.thinkcore.utils.TStringUtils;
import com.thinkcore.utils.TTimeUtils;

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

    public PrintToFileLogger() {
        open();
    }

    public void open() {
        try {
            nameString = getCurrentTimeString() + ".log";
            TFilePath filePath = new TFilePath();
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

    private String getCurrentTimeString() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String result = simpleDateFormat.format(now);
        now = null;
        simpleDateFormat = null;
        return result.replaceAll(":", "-");
    }

    @Override
    public void d(String tag, String message) {
        if (TStringUtils.isEmpty(tag) || TStringUtils.isEmpty(message))
            return;
        println(DEBUG, tag, message);
    }

    @Override
    public void e(String tag, String message) {
        if (TStringUtils.isEmpty(tag) || TStringUtils.isEmpty(message))
            return;
        println(ERROR, tag, message);
    }

    @Override
    public void i(String tag, String message) {
        if (TStringUtils.isEmpty(tag) || TStringUtils.isEmpty(message))
            return;
        println(INFO, tag, message);
    }

    @Override
    public void v(String tag, String message) {
        if (TStringUtils.isEmpty(tag) || TStringUtils.isEmpty(message))
            return;
        println(VERBOSE, tag, message);
    }

    @Override
    public void w(String tag, String message) {
        if (TStringUtils.isEmpty(tag) || TStringUtils.isEmpty(message))
            return;
        println(WARN, tag, message);
    }

    @Override
    public void println(int priority, String tag, String message) {
        if (TStringUtils.isEmpty(tag) || TStringUtils.isEmpty(message))
            return;
        String printMessage = "";
        switch (priority) {
            case VERBOSE:
                printMessage = "[V]|"
                        + tag
                        + "|"
                        + TApplication.getInstance().getApplicationContext()
                        .getPackageName() + "|" + message;
                break;
            case DEBUG:
                printMessage = "[D]|"
                        + tag
                        + "|"
                        + TApplication.getInstance().getApplicationContext()
                        .getPackageName() + "|" + message;
                break;
            case INFO:
                printMessage = "[I]|"
                        + tag
                        + "|"
                        + TApplication.getInstance().getApplicationContext()
                        .getPackageName() + "|" + message;
                break;
            case WARN:
                printMessage = "[W]|"
                        + tag
                        + "|"
                        + TApplication.getInstance().getApplicationContext()
                        .getPackageName() + "|" + message;
                break;
            case ERROR:
                printMessage = "[E]|"
                        + tag
                        + "|"
                        + TApplication.getInstance().getApplicationContext()
                        .getPackageName() + "|" + message;
                break;
            default:

                break;
        }
        println(printMessage);

    }

    public void println(String message) {
        if (TStringUtils.isEmpty(nameString)
                || !TStorageUtils.isExternalStorageWrittenable())
            return;
        try {
            String content = TTimeUtils.getFullTime(System.currentTimeMillis()) + "=====>" + message;
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
}
