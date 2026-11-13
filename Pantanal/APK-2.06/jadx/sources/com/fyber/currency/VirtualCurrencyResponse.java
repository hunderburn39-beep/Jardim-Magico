package com.fyber.currency;

import com.fyber.b.k;

/* JADX INFO: loaded from: classes.dex */
public class VirtualCurrencyResponse implements k.a {
    private double a;
    private String b;
    private String c;
    private String d;
    private boolean e;

    public VirtualCurrencyResponse(double d, String str, String str2, String str3, boolean z) {
        this.a = d;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = z;
    }

    public double getDeltaOfCoins() {
        return this.a;
    }

    public String getLatestTransactionId() {
        return this.b;
    }

    public String getCurrencyId() {
        return this.c;
    }

    public String getCurrencyName() {
        return this.d;
    }

    public boolean isDefault() {
        return this.e;
    }
}
