package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
public class ReactiveVideoTrackerPlugin implements ac {
    private final String a;

    public ReactiveVideoTrackerPlugin(String str) {
        this.a = str;
    }

    @Override // com.moat.analytics.mobile.tjy.ac
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public ReactiveVideoTracker a() {
        return new bg();
    }

    @Override // com.moat.analytics.mobile.tjy.ac
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public ReactiveVideoTracker a(a aVar, ap apVar) {
        return (ReactiveVideoTracker) ay.a(apVar, new bf(this, aVar, apVar), new be());
    }
}
