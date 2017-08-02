package com.thinkcore;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class TDevice {

    public String getDeviceMac() {
        WifiManager wm = (WifiManager) TApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }


    /**
     * Returns the DeviceId according to the TelephonyManager.
     *
     * @param context Context for the application being reported.
     * @return Returns the DeviceId according to the TelephonyManager or null if
     * there is no TelephonyManager.
     */
    public static String getDeviceId(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
