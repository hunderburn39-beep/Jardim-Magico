package com.google.android.gms.internal;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class fj {
    private final String DH;

    public fj(String str) {
        this.DH = (String) fq.f(str);
    }

    public boolean P(int i) {
        return Log.isLoggable(this.DH, i);
    }

    public void a(String str, String str2, Throwable th) {
        if (P(6)) {
            Log.e(str, str2, th);
        }
    }

    public void f(String str, String str2) {
        if (P(2)) {
            Log.v(str, str2);
        }
    }

    public void g(String str, String str2) {
        if (P(5)) {
            Log.w(str, str2);
        }
    }

    public void h(String str, String str2) {
        if (P(6)) {
            Log.e(str, str2);
        }
    }
}
