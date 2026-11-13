package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/* JADX INFO: loaded from: classes.dex */
class g implements m {
    private static Object sf = new Object();
    private static g ss;
    protected String so;
    protected String sp;
    protected String sq;
    protected String sr;

    protected g() {
    }

    private g(Context context) {
        PackageManager packageManager = context.getPackageManager();
        this.sq = context.getPackageName();
        this.sr = packageManager.getInstallerPackageName(this.sq);
        String string = this.sq;
        String str = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                string = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                str = packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            aa.w("Error retrieving package info: appName set to " + string);
        }
        this.so = string;
        this.sp = str;
    }

    public static g ca() {
        return ss;
    }

    public static void n(Context context) {
        synchronized (sf) {
            if (ss == null) {
                ss = new g(context);
            }
        }
    }

    public boolean C(String str) {
        return "&an".equals(str) || "&av".equals(str) || "&aid".equals(str) || "&aiid".equals(str);
    }

    @Override // com.google.android.gms.analytics.m
    public String getValue(String field) {
        if (field == null) {
            return null;
        }
        if (field.equals("&an")) {
            return this.so;
        }
        if (field.equals("&av")) {
            return this.sp;
        }
        if (field.equals("&aid")) {
            return this.sq;
        }
        if (field.equals("&aiid")) {
            return this.sr;
        }
        return null;
    }
}
