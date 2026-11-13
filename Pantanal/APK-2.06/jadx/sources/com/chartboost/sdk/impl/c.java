package com.chartboost.sdk.impl;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
class c extends ac<Void> {
    final a a;
    private final d i;

    c(d dVar, a aVar, File file) {
        super(ac.a.GET, aVar.c, null, file);
        this.h = 1;
        this.i = dVar;
        this.a = aVar;
    }

    @Override // com.chartboost.sdk.impl.ac
    public ae<Void> a(ah ahVar) {
        return ae.b();
    }

    @Override // com.chartboost.sdk.impl.ac
    public aq a() {
        return new aq(this.i.a.a(), null, null);
    }

    @Override // com.chartboost.sdk.impl.ac
    public void a(Void r3) {
        this.i.a(this, null);
    }

    @Override // com.chartboost.sdk.impl.ac
    public void a(ad adVar) {
        this.i.a(this, adVar);
    }
}
