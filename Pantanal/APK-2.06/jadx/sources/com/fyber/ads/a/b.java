package com.fyber.ads.a;

/* JADX INFO: compiled from: ClientState.java */
/* JADX INFO: loaded from: classes.dex */
public enum b {
    READY_TO_CHECK_OFFERS(false, true),
    REQUESTING_OFFERS(false, false),
    READY_TO_SHOW_OFFERS(true, true),
    SHOWING_OFFERS(false, false);

    private final boolean e;
    private final boolean f;

    b(boolean z, boolean z2) {
        this.e = z;
        this.f = z2;
    }

    public final boolean a() {
        return this.e;
    }

    public final boolean b() {
        return this.f;
    }
}
