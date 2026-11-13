package com.google.android.gms.internal;

import com.google.android.gms.ads.doubleclick.AppEventListener;

/* JADX INFO: loaded from: classes.dex */
public final class am extends ar.a {
    private final AppEventListener lV;

    public am(AppEventListener appEventListener) {
        this.lV = appEventListener;
    }

    @Override // com.google.android.gms.internal.ar
    public void onAppEvent(String name, String info) {
        this.lV.onAppEvent(name, info);
    }
}
