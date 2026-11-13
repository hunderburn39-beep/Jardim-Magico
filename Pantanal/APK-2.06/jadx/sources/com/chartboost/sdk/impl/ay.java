package com.chartboost.sdk.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ay {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public String e;
    public String f;
    public final String g;
    public final Integer h;
    public final Integer i;
    public final Integer j;
    public final Integer k;
    public final String l;
    public final Float m;
    public final String n;
    public final String o;
    public final JSONObject p;
    public final boolean q;
    public final String r;
    public final Integer s;

    public ay(Context context, String str) {
        JSONObject jSONObjectA;
        Exception exc;
        int i;
        int i2;
        int iHeight;
        String strSubstring;
        String strSubstring2 = null;
        int i3 = 0;
        this.n = str;
        if ("sdk".equals(Build.PRODUCT)) {
            this.a = "Android Simulator";
        } else {
            this.a = Build.MODEL;
        }
        this.o = Build.MANUFACTURER + " " + Build.MODEL;
        this.b = "Android " + Build.VERSION.RELEASE;
        this.c = Locale.getDefault().getCountry();
        this.d = Locale.getDefault().getLanguage();
        this.g = "6.6.0";
        this.m = Float.valueOf(context.getResources().getDisplayMetrics().density);
        try {
            String packageName = context.getPackageName();
            this.e = context.getPackageManager().getPackageInfo(packageName, 128).versionName;
            this.f = packageName;
        } catch (Exception e) {
            CBLogging.b("RequestBody", "Exception raised getting package mager object", e);
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null && telephonyManager.getPhoneType() != 0) {
            String simOperator = telephonyManager.getSimOperator();
            if (TextUtils.isEmpty(simOperator)) {
                strSubstring = null;
            } else {
                strSubstring = simOperator.substring(0, 3);
                strSubstring2 = simOperator.substring(3);
            }
            jSONObjectA = com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a("carrier-name", telephonyManager.getNetworkOperatorName()), com.chartboost.sdk.Libraries.d.a("mobile-country-code", strSubstring), com.chartboost.sdk.Libraries.d.a("mobile-network-code", strSubstring2), com.chartboost.sdk.Libraries.d.a("iso-country-code", telephonyManager.getNetworkCountryIso()), com.chartboost.sdk.Libraries.d.a("phone-type", Integer.valueOf(telephonyManager.getPhoneType())));
        } else {
            jSONObjectA = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
        }
        this.p = jSONObjectA;
        this.q = CBUtility.d();
        this.r = CBUtility.e();
        this.s = ai.d(context);
        try {
            if (context instanceof Activity) {
                Rect rect = new Rect();
                ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int iWidth = rect.width();
                try {
                    iHeight = rect.height();
                    i3 = iWidth;
                } catch (Exception e2) {
                    i = iWidth;
                    exc = e2;
                    CBLogging.c("RequestBody", "Exception getting activity size", exc);
                    i2 = i;
                }
            } else {
                iHeight = 0;
            }
            i2 = i3;
            i3 = iHeight;
        } catch (Exception e3) {
            exc = e3;
            i = 0;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        }
        int i4 = displayMetrics.widthPixels;
        int i5 = displayMetrics.heightPixels;
        this.j = Integer.valueOf(i4);
        this.k = Integer.valueOf(i5);
        this.l = "" + displayMetrics.densityDpi;
        i2 = (i2 <= 0 || i2 > i4) ? i4 : i2;
        i3 = (i3 <= 0 || i3 > i5) ? i5 : i3;
        this.h = Integer.valueOf(i2);
        this.i = Integer.valueOf(i3);
    }
}
