package com.tapjoy.internal;

import com.google.android.gms.plus.PlusShare;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public long g;

    public g(String str) {
        br brVarB = br.b(str);
        brVarB.h();
        while (brVarB.j()) {
            String strL = brVarB.l();
            if ("productId".equals(strL)) {
                this.a = brVarB.m();
            } else if ("type".equals(strL)) {
                this.b = brVarB.m();
            } else if ("price".equals(strL)) {
                this.c = brVarB.m();
            } else if ("title".equals(strL)) {
                this.d = brVarB.m();
            } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION.equals(strL)) {
                this.e = brVarB.m();
            } else if ("price_currency_code".equals(strL)) {
                this.f = brVarB.m();
            } else if ("price_amount_micros".equals(strL)) {
                this.g = brVarB.q();
            } else {
                brVarB.s();
            }
        }
        brVarB.i();
    }
}
