package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    public String a;
    public String b;
    public String c;
    public long d;
    public int e;
    public String f;
    public String g;

    public h(String str) {
        br brVarB = br.b(str);
        brVarB.h();
        while (brVarB.j()) {
            String strL = brVarB.l();
            if ("orderId".equals(strL)) {
                this.a = brVarB.m();
            } else if ("packageName".equals(strL)) {
                this.b = brVarB.m();
            } else if ("productId".equals(strL)) {
                this.c = brVarB.m();
            } else if ("purchaseTime".equals(strL)) {
                this.d = brVarB.q();
            } else if ("purchaseState".equals(strL)) {
                this.e = brVarB.r();
            } else if ("developerPayload".equals(strL)) {
                this.f = brVarB.m();
            } else if ("purchaseToken".equals(strL)) {
                this.g = brVarB.m();
            } else {
                brVarB.s();
            }
        }
        brVarB.i();
    }
}
