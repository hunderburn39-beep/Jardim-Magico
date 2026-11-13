package com.tapjoy.mraid.controller;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tapjoy.TapjoyConstants;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.util.NetworkBroadcastReceiver;
import com.tapjoy.mraid.view.MraidView;

/* JADX INFO: loaded from: classes.dex */
public class Network extends Abstract {
    private ConnectivityManager c;
    private int d;
    private NetworkBroadcastReceiver e;
    private IntentFilter f;

    public Network(MraidView adView, Context context) {
        super(adView, context);
        this.c = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public String getNetwork() {
        String str;
        NetworkInfo activeNetworkInfo = null;
        try {
            activeNetworkInfo = this.c.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activeNetworkInfo == null) {
            str = "offline";
        } else {
            switch (AnonymousClass1.a[activeNetworkInfo.getState().ordinal()]) {
                case 1:
                    str = "unknown";
                    break;
                case 2:
                    str = "offline";
                    break;
                default:
                    int type = activeNetworkInfo.getType();
                    if (type == 0) {
                        str = "cell";
                    } else if (type != 1) {
                        str = "unknown";
                    } else {
                        str = TapjoyConstants.TJC_CONNECTION_TYPE_WIFI;
                    }
                    break;
            }
        }
        TapjoyLog.d("MRAID Network", "getNetwork: " + str);
        return str;
    }

    /* JADX INFO: renamed from: com.tapjoy.mraid.controller.Network$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[NetworkInfo.State.values().length];

        static {
            try {
                a[NetworkInfo.State.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[NetworkInfo.State.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void startNetworkListener() {
        if (this.d == 0) {
            this.e = new NetworkBroadcastReceiver(this);
            this.f = new IntentFilter();
            this.f.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }
        this.d++;
        this.b.registerReceiver(this.e, this.f);
    }

    public void stopNetworkListener() {
        this.d--;
        if (this.d == 0) {
            this.b.unregisterReceiver(this.e);
            this.e = null;
            this.f = null;
        }
    }

    public void onConnectionChanged() {
        String str = "window.mraidview.fireChangeEvent({ network: '" + getNetwork() + "'});";
        TapjoyLog.d("MRAID Network", str);
        this.a.injectMraidJavaScript(str);
    }

    @Override // com.tapjoy.mraid.controller.Abstract
    public void stopAllListeners() {
        this.d = 0;
        try {
            this.b.unregisterReceiver(this.e);
        } catch (Exception e) {
        }
    }
}
