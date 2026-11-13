package com.chartboost.sdk.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.chartboost.sdk.Libraries.CBLogging;
import java.util.Observable;

/* JADX INFO: loaded from: classes.dex */
public class ai extends Observable {
    private boolean a = true;
    private boolean b = false;
    private b c = b.CONNECTION_UNKNOWN;
    private final a d = new a();

    public enum b {
        CONNECTION_UNKNOWN(-1),
        CONNECTION_ERROR(0),
        CONNECTION_WIFI(1),
        CONNECTION_MOBILE(2);

        private final int e;

        b(int i) {
            this.e = i;
        }

        public int a() {
            return this.e;
        }
    }

    public int a() {
        return this.c.a();
    }

    private class a extends BroadcastReceiver {
        public a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ai.this.a(context);
            ai.this.notifyObservers();
        }
    }

    public void a(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                a(true);
                if (activeNetworkInfo.getType() == 1) {
                    this.c = b.CONNECTION_WIFI;
                    CBLogging.a("CBReachability", "NETWORK TYPE: TYPE_WIFI");
                } else if (activeNetworkInfo.getType() == 0) {
                    this.c = b.CONNECTION_MOBILE;
                    CBLogging.a("CBReachability", "NETWORK TYPE: TYPE_MOBILE");
                }
            } else {
                a(false);
                this.c = b.CONNECTION_ERROR;
                CBLogging.a("CBReachability", "NETWORK TYPE: NO Network");
            }
        } catch (SecurityException e) {
            this.c = b.CONNECTION_UNKNOWN;
            CBLogging.b("CBReachability", "Chartboost SDK requires 'android.permission.ACCESS_NETWORK_STATE' permission set in your AndroidManifest.xml");
        }
    }

    @Override // java.util.Observable
    public void notifyObservers() {
        if (this.a) {
            setChanged();
            super.notifyObservers(this);
        }
    }

    public void a(boolean z) {
        this.a = z;
    }

    public boolean b() {
        return this.a;
    }

    public Intent b(Context context) {
        if (context == null || this.b) {
            return null;
        }
        b(true);
        CBLogging.a("CBReachability", "Network broadcast successfully registered");
        return context.registerReceiver(this.d, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void c(Context context) {
        if (context != null && this.b) {
            context.unregisterReceiver(this.d);
            b(false);
            CBLogging.a("CBReachability", "Network broadcast successfully unregistered");
        }
    }

    public void b(boolean z) {
        this.b = z;
    }

    public static Integer d(Context context) {
        TelephonyManager telephonyManager;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if ((activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (SecurityException e) {
            CBLogging.b("CBReachability", "Chartboost SDK requires 'android.permission.ACCESS_NETWORK_STATE' permission set in your AndroidManifest.xml");
        }
        return null;
    }
}
