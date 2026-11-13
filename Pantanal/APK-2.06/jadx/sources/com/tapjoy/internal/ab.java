package com.tapjoy.internal;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class ab {
    public static String a(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI);
        if (wifiManager != null) {
            try {
                return cr.b(wifiManager.getConnectionInfo().getMacAddress());
            } catch (SecurityException e) {
            } catch (RuntimeException e2) {
            }
        }
        return null;
    }
}
