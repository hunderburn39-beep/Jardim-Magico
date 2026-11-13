package com.chartboost.sdk.impl;

/* JADX INFO: loaded from: classes.dex */
public class ad {
    public final ah a;
    public final Exception b;

    public String a() {
        if (this.b != null) {
            return this.b.getMessage();
        }
        return null;
    }

    public ad(ah ahVar, Exception exc) {
        this.a = ahVar;
        this.b = exc;
    }
}
