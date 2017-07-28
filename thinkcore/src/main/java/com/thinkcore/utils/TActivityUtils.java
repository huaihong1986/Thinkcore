package com.thinkcore.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thinkcore.TApplication;
import com.thinkcore.activity.TActivity;
import com.thinkcore.activity.TAppActivity;
import com.thinkcore.utils.TAndroidVersionUtils;
import com.thinkcore.utils.log.TLog;
import com.thinkcore.utils.task.TTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.LocalActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class TActivityUtils {
    private static final String TAG = TActivityUtils.class.getSimpleName();

    // 跳转到Activity
    public static void jumpToActivity(Context context, Intent datatIntent) {//
        context.startActivity(datatIntent);
    }

    // 跳转到Activity
    public static void jumpPostToActivity(final Context context,
                                          final Intent datatIntent, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jumpToActivity(context, datatIntent);
            }
        }.newExecute(null);
    }

    // 跳转到Activity
    public static void jumpToActivity(Context context,
                                      Class<? extends Activity> targetClass) {
        Intent datatIntent = new Intent(context, targetClass);
        context.startActivity(datatIntent);
    }

    public static void jumpToActivity(Context context,
                                      Class<? extends Activity> targetClass, Bundle bundle) {
        Intent datatIntent = new Intent(context, targetClass);
        if (bundle != null)
            datatIntent.putExtras(bundle);
        context.startActivity(datatIntent);
    }

    // 跳转到Activity
    public static void jumpPostToActivity(final Context context,
                                          final Class<? extends Activity> targetClass, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Intent datatIntent = new Intent(context, targetClass);
                context.startActivity(datatIntent);
            }
        }.newExecute(null);
    }

    public static void jumpPostToActivity(final Context context,
                                          final Class<? extends Activity> targetClass, final Bundle bundle, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Intent datatIntent = new Intent(context, targetClass);
                if (bundle != null)
                    datatIntent.putExtras(bundle);
                context.startActivity(datatIntent);
            }
        }.newExecute(null);
    }

    // 跳转到Activity
    public static void jumpToNewActivity(Context context,
                                         Class<? extends Activity> targetClass) {
        Intent datatIntent = new Intent(context, targetClass);
        datatIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(datatIntent);
    }

    public static void jumpToNewActivity(Context context,
                                         Class<? extends Activity> targetClass, final Bundle bundle) {
        Intent datatIntent = new Intent(context, targetClass);
        datatIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null)
            datatIntent.putExtras(bundle);
        context.startActivity(datatIntent);
    }

    public static void jumpToNewTopActivity(Context context,
                                            Class<? extends Activity> targetClass) {
        jumpToNewTopActivity(context, targetClass, null);
    }

    public static void jumpToNewTopActivity(Context context,
                                            Class<? extends Activity> targetClass, final Bundle bundle) {
        Intent datatIntent = new Intent(context, targetClass);
        datatIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            datatIntent.putExtras(bundle);
        context.startActivity(datatIntent);
    }

    public static void jumpPostToNewActivity(final Context context,
                                             final Class<? extends Activity> targetClass, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jumpToNewActivity(context, targetClass, null);
            }
        }.newExecute();
    }

    public static void jumpPostToNewActivity(final Context context,
                                             final Class<? extends Activity> targetClass, final Bundle bundle, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jumpToNewActivity(context, targetClass, bundle);
            }
        }.newExecute();
    }

    public static void jumpPostToNewTopActivity(final Context context,
                                                final Class<? extends Activity> targetClass, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jumpToNewTopActivity(context, targetClass);
            }
        }.newExecute();
    }

    public static void jumpPostToNewTopActivity(final Context context,
                                                final Class<? extends Activity> targetClass, final Bundle bundle, final int second) {
        new TTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(second * 1000);
                } catch (Exception e) {
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jumpToNewTopActivity(context, targetClass, bundle);
            }
        }.newExecute();
    }

    // 跳转到Activity
    public static void jumpToActivityForResult(TActivity activity,
                                               Class<? extends Activity> targetClass, IActivityResult iActivityResult) {
        if (iActivityResult == null)
            return;
        Random random = new Random();
        int resultId = random.nextInt(10000);
        activity.getIActivityResult().put(resultId, iActivityResult);
        jumpToActivityForResult(activity, targetClass, resultId);
    }

    public static void jumpToActivityForResult(TAppActivity activity,
                                               Class<? extends Activity> targetClass, IActivityResult iActivityResult) {
        jumpToActivityForResult(activity, targetClass, null, iActivityResult);
    }

    public static void jumpToActivityForResult(TAppActivity activity,
                                               Class<? extends Activity> targetClass, final Bundle bundle, IActivityResult iActivityResult) {
        if (iActivityResult == null)
            return;
        Random random = new Random();
        int resultId = random.nextInt(10000);
        activity.getIActivityResult().put(resultId, iActivityResult);
        jumpToActivityForResult(activity, targetClass, bundle, resultId);
    }

    public static void jumpToActivityForResult(Activity activity,
                                               Class<? extends Activity> targetClass, int resultId) {
        jumpToActivityForResult(activity, targetClass, null, resultId);
    }

    public static void jumpToActivityForResult(Activity activity,
                                               Class<? extends Activity> targetClass, final Bundle bundle, int resultId) {
        Intent datatIntent = new Intent(activity, targetClass);
        if (bundle != null)
            datatIntent.putExtras(bundle);
        activity.startActivityForResult(datatIntent, resultId);
    }


    // 跳转到系统短信Activity
    public static void jumpToSystemSMSActivity(Context context, String number) {
        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.putExtra("address", number);
        mIntent.setType("vnd.android-dir/mms-sms");
        context.startActivity(mIntent);
    }

    // 跳转到另一个apk中Activity new android:exported="true"
    // ComponentName("C的包名", "C的包名+C");
    public static void jumpToActivity(Context context,
                                      ComponentName componentName) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setComponent(componentName);
        mIntent.setAction("android.intent.action.VIEW");
        context.startActivity(mIntent);
    }

    /**
     * 回到home，后台运行
     *
     * @param context
     */
    public static void jumpToHomeActivity(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    public static void jumpToNetworkSettingActivity(Context context) {
        Intent intent = null;

        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        try {
            if (TAndroidVersionUtils.hasHoneycomb()) {
                intent = new Intent(
                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            } else {
                intent = new Intent();
                ComponentName comp = new ComponentName("com.android.settings",
                        "com.android.settings.WirelessSettings");
                intent.setComponent(comp);
                intent.setAction("android.intent.action.VIEW");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            TLog.w(TAG, "open network settings failed, please check...");
            e.printStackTrace();
        }
    }

    public static void jumpToSystemLocPickImageActivity(TActivity activity,
                                                        IActivityResult iActivityResult) {
        Random random = new Random();
        int resultId = random.nextInt(10000);
        activity.getIActivityResult().put(resultId, iActivityResult);
        jumpToSystemLocPickImageActivity(activity, iActivityResult);
    }

    public static void jumpToSystemLocPickImageActivity(TAppActivity activity,
                                                        IActivityResult iActivityResult) {
        Random random = new Random();
        int resultId = random.nextInt(10000);
        activity.getIActivityResult().put(resultId, iActivityResult);
        jumpToSystemLocPickImageActivity(activity, iActivityResult);
    }

    public static void jumpToSystemLocPickImageActivity(Activity activity,
                                                        int requestCode) {
        Intent intent = null;
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jumpToSystemCameraPickImageActivity(TActivity activity,
                                                           IActivityResult iActivityResult) {
        Random random = new Random();
        int resultId = random.nextInt(10000);
        activity.getIActivityResult().put(resultId, iActivityResult);
        jumpToSystemCameraPickImageActivity(activity, iActivityResult);
    }

    public static void jumpToSystemCameraPickImageActivity(TAppActivity activity,
                                                           IActivityResult iActivityResult) {
        Random random = new Random();
        int resultId = random.nextInt(10000);
        activity.getIActivityResult().put(resultId, iActivityResult);
        jumpToSystemCameraPickImageActivity(activity, iActivityResult);
    }

    public static void jumpToSystemCameraPickImageActivity(Activity activity,
                                                           int requestCode) {
        Intent intent = null;
        intent = new Intent("android.media.action.IMAGE_CAPTURE");
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jumpToSystemCallActivity(Context context, String number) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + number));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void jumpToSystemMessageActivity(Context context,
                                                   String number) {
        Uri smsToUri = Uri.parse("smsto://" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, smsToUri);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void jumpToSystemInstallApkActivity(Context context,
                                                      String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static void jumpToSystemDownloadApk(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse(Html.fromHtml(url).toString());
        intent.setData(data);
        intent.setPackage("com.google.android.browser");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setComponent(new ComponentName("com.android.browser",
                "com.android.browser.BrowserActivity"));
        context.startActivity(intent);
    }

    public static void jumpToSystemShareText(Context context, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
        // startActivity(Intent.createChooser(sendIntent,
        // getResources().getText(R.string.send_to)));
    }

    public static void jumpToSystemShareImage(Context context, String imageUri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(shareIntent);
        // startActivity(Intent.createChooser(shareIntent,
        // getResources().getText(R.string.send_to)));
    }

    public static void jumpToSystemShareImages(Context context,
                                               ArrayList<Uri> imageUris) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        context.startActivity(shareIntent);
        // startActivity(Intent.createChooser(shareIntent,
        // "Share images to.."));
    }

    // http://blog.csdn.net/waylife/article/details/44315103
    public static void createShortCut(String shortcutname, int iconId,
                                      String action, String shortData, String packageString,
                                      String tagerClass) {// 创建快捷方式的Intent
        if (hasInstallShortcut(TApplication.getInstance(), shortcutname))
            return;
        Intent shortcutintent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);// 不允许重复创建
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutname);// 需要现实的名称
        Parcelable iconParcelable = Intent.ShortcutIconResource.fromContext(
                TApplication.getInstance(), iconId);// 快捷图片
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                iconParcelable); // 点击快捷图片，运行的程序主入口

        Intent intent = new Intent(action);
        // intent.setAction(Intent.ACTION_MAIN);
        // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // intent.setComponent(component);
        intent.setClassName(packageString, tagerClass);
        intent.putExtra("shortData", shortData);

        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        shortcutintent.putExtra("shortData", shortData);
        TApplication.getInstance().sendBroadcast(shortcutintent); // 发送广播。OK
    }

    public static void delShortcut(String shortcutname, String action,
                                   String packageString, String tagerClass) {
        Intent shortcut = new Intent(
                "com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutname);
        ComponentName comp = new ComponentName(packageString, tagerClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                new Intent(action).setComponent(comp));
        TApplication.getInstance().sendBroadcast(shortcut);
    }

    public static boolean hasInstallShortcut(Context context, String name) {
        boolean hasInstall = false;

        String AUTHORITY = "com.android.launcher.settings";
        int systemversion = Build.VERSION.SDK_INT;
        if (systemversion < 8) {
            AUTHORITY = "com.android.launcher2.settings";
        } else if (systemversion < 19) {
            AUTHORITY = "com.android.launcher2.settings";
        } else {// 4.4以及以上
            AUTHORITY = "com.android.launcher3.settings";
        }

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/favorites?notify=true");

        Cursor cursor = context.getContentResolver().query(CONTENT_URI,
                new String[]{"title"}, "title=?", new String[]{name},
                null);

        if (cursor != null && cursor.getCount() > 0) {
            hasInstall = true;
        }

        if (cursor != null) {
            cursor.close();
        }

        return hasInstall;
    }


    /**
     * @param context
     * @param activityName
     * @return
     * @Description 判断是否是顶部activity
     */
    public static boolean isTopActivy(Context context, String activityName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cName = am.getRunningTasks(1).size() > 0 ? am
                .getRunningTasks(1).get(0).topActivity : null;

        if (null == cName)
            return false;
        return cName.getClassName().equals(activityName);
    }


    public interface IActivityResult {
        void onActivityResult(int resultCode, Intent intent);
    }
}
