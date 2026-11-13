package com.google.android.gms.tagmanager;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
class bm {
    bm() {
    }

    int jZ() {
        return Build.VERSION.SDK_INT;
    }

    public bl kH() {
        return jZ() < 8 ? new av() : new aw();
    }
}
