package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class p extends aj {
    private static final String ID = com.google.android.gms.internal.a.CONTAINER_VERSION.toString();
    private final String Xl;

    public p(String str) {
        super(ID, new String[0]);
        this.Xl = str;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        return this.Xl == null ? dh.lT() : dh.r(this.Xl);
    }
}
