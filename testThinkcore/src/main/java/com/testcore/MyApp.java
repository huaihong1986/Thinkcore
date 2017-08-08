package com.testcore;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.thinkcore.crash.HttpReportSenderFactory;
import com.thinkcore.log.TLog;
import com.thinkcore.storage.TFilePath;
import com.thinklib.LibApplication;

import org.acra.*;
import org.acra.annotation.*;
import org.acra.builder.ReportExecutor;
import org.acra.sender.HttpSender;

@ReportsCrashes(
        formUri = "http://api.eotu.com:9090/app/addCollapse",
        httpMethod = HttpSender.Method.POST,
        reportSenderFactoryClasses = HttpReportSenderFactory.class
        //mailTo = "banketree@qq.com"
)
public class MyApp extends LibApplication {
    private static final String TAG = MyApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);

        TLog.enablePrintToFileLogger(this, true);
        TFilePath filePath = new TFilePath(this);
        String path = filePath.getInterAppDir();
        TLog.i(this, path);
        path = filePath.getExternalAppDir();
        TLog.i(this, path);
        path = filePath.getInterImageDir();
        TLog.i(this, path);
        path = filePath.getExternalImageDir();
        TLog.i(this, path);
        path = filePath.getInterAudioDir();
        TLog.i(this, path);
        path = filePath.getExternalAudioDir();
        TLog.i(this, path);
        path = filePath.getInterCacheDir();
        TLog.i(this, path);
        path = filePath.getExternalCacheDir();
        TLog.i(this, path);
        path = filePath.getInterDownloadDir();
        TLog.i(this, path);
        path = filePath.getExternalDownloadDir();
        TLog.i(this, path);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
