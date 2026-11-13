package com.fyber.utils;

import android.os.Build;

/* JADX INFO: compiled from: OSVersion.java */
/* JADX INFO: loaded from: classes.dex */
public final class n {
    public static boolean a() {
        return Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22;
    }

    public static boolean a(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    public static boolean b(int i) {
        return Build.VERSION.SDK_INT < i;
    }
}
