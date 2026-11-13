package com.google.android.gms.analytics;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
class x {
    private String vh;
    private final long vi;
    private final long vj;
    private String vk = "https:";

    x(String str, long j, long j2) {
        this.vh = str;
        this.vi = j;
        this.vj = j2;
    }

    void J(String str) {
        this.vh = str;
    }

    void K(String str) {
        if (str == null || TextUtils.isEmpty(str.trim()) || !str.toLowerCase().startsWith("http:")) {
            return;
        }
        this.vk = "http:";
    }

    String cO() {
        return this.vh;
    }

    long cP() {
        return this.vi;
    }

    long cQ() {
        return this.vj;
    }

    String cR() {
        return this.vk;
    }
}
