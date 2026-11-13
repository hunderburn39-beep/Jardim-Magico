package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class er {
    private static boolean zC = false;
    private final String mTag;
    private boolean zD;
    private boolean zE;
    private String zF;

    public er(String str) {
        this(str, dR());
    }

    public er(String str, boolean z) {
        this.mTag = str;
        this.zD = z;
        this.zE = false;
    }

    public static boolean dR() {
        return zC;
    }

    private String e(String str, Object... objArr) {
        String str2 = String.format(str, objArr);
        return !TextUtils.isEmpty(this.zF) ? this.zF + str2 : str2;
    }

    public void a(String str, Object... objArr) {
        if (dQ()) {
            Log.v(this.mTag, e(str, objArr));
        }
    }

    public void a(Throwable th, String str, Object... objArr) {
        if (dP() || zC) {
            Log.d(this.mTag, e(str, objArr), th);
        }
    }

    public void ab(String str) {
        this.zF = TextUtils.isEmpty(str) ? null : String.format("[%s] ", str);
    }

    public void b(String str, Object... objArr) {
        if (dP() || zC) {
            Log.d(this.mTag, e(str, objArr));
        }
    }

    public void c(String str, Object... objArr) {
        Log.i(this.mTag, e(str, objArr));
    }

    public void d(String str, Object... objArr) {
        Log.w(this.mTag, e(str, objArr));
    }

    public boolean dP() {
        return this.zD;
    }

    public boolean dQ() {
        return this.zE;
    }
}
