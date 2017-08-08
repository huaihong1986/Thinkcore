package com.testcore.ui;

import com.testcore.R;
import com.testcore.ui.switchButton.MainActivity;
import com.testcore.utils.NdkJniUtils;
import com.testcore.utils.ThemeUtils;
import com.thinkcore.activity.TActivityUtils;
import com.thinkcore.log.TLog;
import com.thinkcore.preference.IConfig;
import com.thinkcore.preference.TPreferenceConfig;
import com.thinkcore.preference.TPropertiesConfig;
import com.thinkcore.utils.TTimeUtils;
import com.thinklib.activity.LibActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TestTActivity extends LibActivity implements OnClickListener {
    private String TAG = TestTActivity.class.getCanonicalName();
    private static boolean mTheme = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(mTheme ? R.style.AppThemeLight : R.style.AppThemeDark);

        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_test);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        findViewById(R.id.Button_log).setOnClickListener(this);
        findViewById(R.id.Button_startResult).setOnClickListener(this);
        findViewById(R.id.Button_crash).setOnClickListener(this);
        findViewById(R.id.Button_http).setOnClickListener(this);
        findViewById(R.id.Button_download).setOnClickListener(this);
        findViewById(R.id.Button_dialog).setOnClickListener(this);
        findViewById(R.id.Button_theme).setOnClickListener(this);
        findViewById(R.id.Button_ripple).setOnClickListener(this);
        findViewById(R.id.Button_BetterSpinner).setOnClickListener(this);
        findViewById(R.id.Button_Switch).setOnClickListener(this);
        findViewById(R.id.Button_Refresh).setOnClickListener(this);
        findViewById(R.id.Button_Snackbar).setOnClickListener(this);
        findViewById(R.id.Button_RippleView).setOnClickListener(this);
        findViewById(R.id.Button_reveallayout).setOnClickListener(this);
        findViewById(R.id.Button_Discreteseekbar).setOnClickListener(this);
        findViewById(R.id.Button_compent).setOnClickListener(this);
        findViewById(R.id.Button_fd).setOnClickListener(this);
        findViewById(R.id.Button_setting).setOnClickListener(this);
        findViewById(R.id.Button_rangerbar).setOnClickListener(this);
        findViewById(R.id.Button_scroolview).setOnClickListener(this);
        findViewById(R.id.Button_plugin).setOnClickListener(this);

//		final View view = View.inflate(this, R.layout.splash, null);
//		setContentView(view);
//		// 渐变展示启动�?
//		AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
//		aa.setDuration(5000);
//		view.startAnimation(aa);
//		aa.setAnimationListener(new AnimationListener() {
//			@Override
//			public void onAnimationEnd(Animation arg0) {
//				finish();
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//		});
//
//		testComparatorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.Button_startResult) {
//            TActivityUtils.jumpToSystemCameraPickImageActivity(this, new TActivityUtils.IActivityResult() {
//                @Override
//                public void onActivityResult(int resultCode, Intent intent) {
//                    TLog.i("", "" + resultCode);
//                }
//            });

            TActivityUtils.jumpToActivityForResult(this, MainActivity.class, new TActivityUtils.IActivityResult() {
                @Override
                public void onActivityResult(int resultCode, Intent intent) {
                    TLog.i("", "" + resultCode);
                }
            });
        } else if (arg0.getId() == R.id.Button_log) {
            TLog.enablePrintToFileLogger(this, false);
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test1");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test2");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test3");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test4");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test5");

            TLog.enablePrintToFileLogger(this, true);
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test6");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test7");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test8");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test9");
            TLog.i("", TTimeUtils.getFullTime(System.currentTimeMillis()) + "test10");


            TLog.i("", "new TPreferenceConfig()");
            IConfig config = new TPreferenceConfig(this);
            config.open("test_preference");
            TLog.i("", "config.open(\"test_preference\");");
            TLog.i("", "config.isLoadConfig()--->" + config.isLoadConfig());

            if (config.isLoadConfig()) {
                config.setBoolean("test1", false);
                config.setInt("test2", 100);

                TLog.i("", "test1:" + config.getBoolean("test1", true));
                TLog.i("", "test2:" + config.getInt("test2", 0));
            }
            TLog.i("", "test_preference over");

            TLog.i("", "new TPropertiesConfig()");
            config = new TPropertiesConfig(this);
            config.open("test_properties");
            TLog.i("", "config.open(\"test_properties\");");
            TLog.i("", "config.isLoadConfig()--->" + config.isLoadConfig());

            if (config.isLoadConfig()) {
                config.setBoolean("test4", true);
                config.setInt("test5", 99);

                TLog.i("", "test4:" + config.getBoolean("test1", false));
                TLog.i("", "test5:" + config.getInt("test2", 0));
            }

            TLog.i("", "test_properties over");
        } else if (arg0.getId() == R.id.Button_crash) {
            int test = 10 / 0;
        } else if (arg0.getId() == R.id.Button_http) {
            TActivityUtils.jumpToActivity(this, HttpActivity.class);
        } else if (arg0.getId() == R.id.Button_download) {
            TActivityUtils.jumpToActivity(this, DownloadActivity.class);
        } else if (arg0.getId() == R.id.Button_dialog) {
            TActivityUtils.jumpToActivity(this, DialogActivity.class);
        } else if (arg0.getId() == R.id.Button_theme) {
            mTheme = !mTheme;

            ThemeUtils.recreateActivity(this);
        } else if (arg0.getId() == R.id.Button_ripple) {
            TActivityUtils.jumpToActivity(this, RippleActivity.class);
        } else if (arg0.getId() == R.id.Button_BetterSpinner) {
            TActivityUtils.jumpToActivity(this, BetterSpinnerActivity.class);
        } else if (arg0.getId() == R.id.Button_Switch) {
            TActivityUtils.jumpToActivity(this, MainActivity.class);
        } else if (arg0.getId() == R.id.Button_Refresh) {
            TActivityUtils.jumpToActivity(this, SwipyRefreshActivity.class);
        } else if (arg0.getId() == R.id.Button_Snackbar) {
            TActivityUtils.jumpToActivity(this, SnackbarActivity.class);
        } else if (arg0.getId() == R.id.Button_RippleView) {
            TActivityUtils.jumpToActivity(this, RippleViewActivity.class);
        } else if (arg0.getId() == R.id.Button_reveallayout) {
            TActivityUtils.jumpToActivity(this, com.testcore.ui.reveallayout.MainActivity.class);
        } else if (arg0.getId() == R.id.Button_Discreteseekbar) {
            TActivityUtils.jumpToActivity(this, DiscreteseekbarActivity.class);
        } else if (arg0.getId() == R.id.Button_compent) {
            TActivityUtils.jumpToActivity(this, com.testcore.ui.materialdesigndemo.MainActivity.class);
        } else if (arg0.getId() == R.id.Button_fd) {
            TActivityUtils.jumpToActivity(this, com.testcore.ui.fd.MainActivity.class);
        } else if (arg0.getId() == R.id.Button_setting) {
            TActivityUtils.jumpToActivity(this, com.testcore.ui.SettingsActivity.class);
        } else if (arg0.getId() == R.id.Button_rangerbar) {
            TActivityUtils.jumpToActivity(this, com.testcore.ui.rangebarsample.MainActivity.class);
        } else if (arg0.getId() == R.id.Button_scroolview) {
            TActivityUtils.jumpToActivity(this, com.testcore.ui.observablescroll.ViewPagerTabActivity.class);

            NdkJniUtils jni = new NdkJniUtils();
            String tet = jni.getCLanguageString();
            TLog.i(this, tet);
        } else if (arg0.getId() == R.id.Button_plugin) {
//			TActivityUtils.jumpToActivity(this, com.testcore.ui.plugin.PluginActivity.class);
        }
    }
}
