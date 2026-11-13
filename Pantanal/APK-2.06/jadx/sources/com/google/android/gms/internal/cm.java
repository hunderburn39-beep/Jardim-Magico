package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class cm extends cn.a {
    private String lh;
    private Context mContext;
    private String oD;
    private ArrayList<String> oE;

    public cm(String str, ArrayList<String> arrayList, Context context, String str2) {
        this.oD = str;
        this.oE = arrayList;
        this.lh = str2;
        this.mContext = context;
    }

    private void aX() {
        try {
            this.mContext.getClassLoader().loadClass("com.google.ads.conversiontracking.IAPConversionReporter").getDeclaredMethod("reportWithProductId", Context.class, String.class, String.class, Boolean.TYPE).invoke(null, this.mContext, this.oD, "", true);
        } catch (ClassNotFoundException e) {
            dw.z("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (NoSuchMethodException e2) {
            dw.z("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (Exception e3) {
            dw.c("Fail to report a conversion.", e3);
        }
    }

    protected String a(String str, HashMap<String, String> map) {
        String str2;
        String packageName = this.mContext.getPackageName();
        try {
            str2 = this.mContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            dw.c("Error to retrieve app version", e);
            str2 = "";
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - dj.bu().bw();
        for (String str3 : map.keySet()) {
            str = str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", str3), String.format("$1%s$2", map.get(str3)));
        }
        return str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "sessionid"), String.format("$1%s$2", dj.qK)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "appid"), String.format("$1%s$2", packageName)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "osversion"), String.format("$1%s$2", String.valueOf(Build.VERSION.SDK_INT))).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "sdkversion"), String.format("$1%s$2", this.lh)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "appversion"), String.format("$1%s$2", str2)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "timestamp"), String.format("$1%s$2", String.valueOf(jElapsedRealtime))).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "[^@]+"), String.format("$1%s$2", "")).replaceAll("@@", "@");
    }

    @Override // com.google.android.gms.internal.cn
    public String getProductId() {
        return this.oD;
    }

    protected int j(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        return i == 4 ? 3 : 0;
    }

    @Override // com.google.android.gms.internal.cn
    public void recordPlayBillingResolution(int billingResponseCode) {
        if (billingResponseCode == 0) {
            aX();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("google_play_status", String.valueOf(billingResponseCode));
        map.put("sku", this.oD);
        map.put("status", String.valueOf(j(billingResponseCode)));
        Iterator<String> it = this.oE.iterator();
        while (it.hasNext()) {
            new du(this.mContext, this.lh, a(it.next(), map)).start();
        }
    }

    @Override // com.google.android.gms.internal.cn
    public void recordResolution(int resolution) {
        if (resolution == 1) {
            aX();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(resolution));
        map.put("sku", this.oD);
        Iterator<String> it = this.oE.iterator();
        while (it.hasNext()) {
            new du(this.mContext, this.lh, a(it.next(), map)).start();
        }
    }
}
