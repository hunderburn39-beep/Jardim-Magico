package com.tapjoy.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class ge {
    final q A;
    final j B;
    final j C;
    final q D;
    final Context a;
    final SharedPreferences b;
    final q c;
    final q d;
    final m e;
    final m f;
    final q g;
    final m h;
    final n i;
    final n j;
    final n k;
    final q l;
    final m m;
    final k n;
    final n o;
    final k p;
    final q q;
    final q r;
    final m s;
    final m t;
    final q u;
    final q v;
    final q w;
    final q x;
    final q y;
    final q z;

    public static ge a(Context context) {
        return new ge(context);
    }

    private ge(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.a = applicationContext;
        this.b = applicationContext.getSharedPreferences("fiverocks", 0);
        this.c = new q(this.b, "sdk");
        this.d = new q(this.b, "ir");
        this.e = new m(this.b, "fql", 0);
        this.f = new m(this.b, "fq", 0);
        this.g = new q(this.b, "push");
        this.h = new m(this.b, "ss", 0);
        this.i = new n(this.b, "std");
        this.j = new n(this.b, "slt");
        this.k = new n(this.b, "sld");
        this.l = new q(this.b, "ptc");
        this.m = new m(this.b, "pc", 0);
        this.n = new k(this.b, "ptp");
        this.o = new n(this.b, "lpt");
        this.p = new k(this.b, "plp");
        this.q = new q(this.b, "adv");
        this.r = new q(this.b, "ui");
        this.s = new m(this.b, "ul", -1);
        this.t = new m(this.b, "uf", -1);
        this.u = new q(this.b, TapjoyConstants.TJC_USER_VARIABLE_1);
        this.v = new q(this.b, TapjoyConstants.TJC_USER_VARIABLE_2);
        this.w = new q(this.b, TapjoyConstants.TJC_USER_VARIABLE_3);
        this.x = new q(this.b, TapjoyConstants.TJC_USER_VARIABLE_4);
        this.y = new q(this.b, TapjoyConstants.TJC_USER_VARIABLE_5);
        this.z = new q(this.b, "utags");
        this.A = new q(this.b, "idfa");
        this.B = new j(this.b, "idfa.optout");
        this.C = new j(this.b, "push.optout");
        this.D = new q(this.b, "appId");
    }

    final SharedPreferences.Editor a() {
        return this.b.edit();
    }

    public final String b() {
        String string = this.b.getString("ir", null);
        if (string != null) {
            if (string.length() > 0) {
                return string;
            }
            return null;
        }
        File file = new File(fu.c(this.a), TapjoyConstants.TJC_REFERRER);
        if (file.exists()) {
            try {
                string = bk.a(file, ap.c);
            } catch (IOException e) {
            }
        }
        this.b.edit().putString("ir", string != null ? string : "").commit();
        if (string == null || string.length() <= 0) {
            return null;
        }
        return string;
    }

    public final void a(boolean z) {
        p.a(this.b, "gcm.onServer", z);
    }
}
