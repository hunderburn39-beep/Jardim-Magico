package com.google.android.gms.tagmanager;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
class ap {
    private final long XX;
    private String XY;
    private final long vi;
    private final long vj;

    ap(long j, long j2, long j3) {
        this.vi = j;
        this.vj = j2;
        this.XX = j3;
    }

    void K(String str) {
        if (str == null || TextUtils.isEmpty(str.trim())) {
            return;
        }
        this.XY = str;
    }

    long cP() {
        return this.vi;
    }

    long kD() {
        return this.XX;
    }

    String kE() {
        return this.XY;
    }
}
