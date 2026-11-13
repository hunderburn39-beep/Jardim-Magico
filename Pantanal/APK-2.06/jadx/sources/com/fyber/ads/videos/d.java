package com.fyber.ads.videos;

/* JADX INFO: compiled from: RewardedVideoOffersStatus.java */
/* JADX INFO: loaded from: classes.dex */
public enum d {
    MUST_QUERY_SERVER_FOR_OFFERS(false, true, true),
    QUERYING_SERVER_FOR_OFFERS(false, false, false),
    READY_TO_SHOW_OFFERS(true, true, true),
    SHOWING_OFFERS(false, false, false),
    USER_ENGAGED(false, false, false);

    private final boolean f;
    private final boolean g;
    private final boolean h;

    d(boolean z, boolean z2, boolean z3) {
        this.f = z;
        this.h = z2;
        this.g = z3;
    }

    final boolean a() {
        return this.f;
    }

    final boolean b() {
        return this.g;
    }

    final boolean c() {
        return this.h;
    }
}
