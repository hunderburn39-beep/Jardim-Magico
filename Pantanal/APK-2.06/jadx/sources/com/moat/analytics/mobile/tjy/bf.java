package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class bf implements ba {
    final /* synthetic */ a a;
    final /* synthetic */ ap b;
    final /* synthetic */ ReactiveVideoTrackerPlugin c;

    bf(ReactiveVideoTrackerPlugin reactiveVideoTrackerPlugin, a aVar, ap apVar) {
        this.c = reactiveVideoTrackerPlugin;
        this.a = aVar;
        this.b = apVar;
    }

    @Override // com.moat.analytics.mobile.tjy.ba
    public com.moat.analytics.mobile.tjy.base.functional.a a() {
        return com.moat.analytics.mobile.tjy.base.functional.a.a(new bd(this.c.a, this.a, this.b));
    }
}
