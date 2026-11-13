package com.google.android.gms.internal;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class gk {
    public static boolean y(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }
}
