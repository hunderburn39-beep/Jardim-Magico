package com.google.android.gms.internal;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class w implements y {
    private dz kU;

    public w(dz dzVar) {
        this.kU = dzVar;
    }

    @Override // com.google.android.gms.internal.y
    public void a(ab abVar, boolean z) {
        HashMap map = new HashMap();
        map.put("isVisible", z ? "1" : "0");
        this.kU.a("onAdVisibilityChanged", map);
    }
}
