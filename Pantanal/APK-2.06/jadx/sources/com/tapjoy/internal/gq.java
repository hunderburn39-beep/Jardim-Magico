package com.tapjoy.internal;

import android.graphics.Point;
import android.os.SystemClock;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class gq extends gm {
    public static final bm n = new bm() { // from class: com.tapjoy.internal.gq.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            return new gq(brVar);
        }
    };
    public gt a;
    public gt b;
    public gt c;
    public Point d;
    public gt e;
    public gt f;
    public String g;
    public fg h;
    public ArrayList i = new ArrayList();
    public ArrayList j = new ArrayList();
    public Map k;
    public long l;
    public gr m;

    public gq() {
    }

    gq(br brVar) {
        brVar.h();
        String strB = null;
        String strB2 = null;
        while (brVar.j()) {
            String strL = brVar.l();
            if ("frame".equals(strL)) {
                brVar.h();
                while (brVar.j()) {
                    String strL2 = brVar.l();
                    if ("portrait".equals(strL2)) {
                        this.a = (gt) gt.c.a(brVar);
                    } else if (TJAdUnitConstants.String.LANDSCAPE.equals(strL2)) {
                        this.b = (gt) gt.c.a(brVar);
                    } else if ("close_button".equals(strL2)) {
                        this.c = (gt) gt.c.a(brVar);
                    } else if ("close_button_offset".equals(strL2)) {
                        this.d = (Point) bn.a.a(brVar);
                    } else {
                        brVar.s();
                    }
                }
                brVar.i();
            } else if ("creative".equals(strL)) {
                brVar.h();
                while (brVar.j()) {
                    String strL3 = brVar.l();
                    if ("portrait".equals(strL3)) {
                        this.e = (gt) gt.c.a(brVar);
                    } else if (TJAdUnitConstants.String.LANDSCAPE.equals(strL3)) {
                        this.f = (gt) gt.c.a(brVar);
                    } else {
                        brVar.s();
                    }
                }
                brVar.i();
            } else if ("url".equals(strL)) {
                this.g = brVar.b();
            } else if (gj.a(strL)) {
                this.h = gj.a(strL, brVar);
            } else if ("mappings".equals(strL)) {
                brVar.h();
                while (brVar.j()) {
                    String strL4 = brVar.l();
                    if ("portrait".equals(strL4)) {
                        brVar.a(this.i, go.h);
                    } else if (TJAdUnitConstants.String.LANDSCAPE.equals(strL4)) {
                        brVar.a(this.j, go.h);
                    } else {
                        brVar.s();
                    }
                }
                brVar.i();
            } else if ("meta".equals(strL)) {
                this.k = brVar.d();
            } else if ("ttl".equals(strL)) {
                this.l = ((long) (brVar.p() * 1000.0d)) + SystemClock.elapsedRealtime();
            } else if ("no_more_today".equals(strL)) {
                this.m = (gr) gr.d.a(brVar);
            } else if ("ad_content".equals(strL)) {
                strB = brVar.b();
            } else if (TapjoyConstants.TJC_REDIRECT_URL.equals(strL)) {
                strB2 = brVar.b();
            } else {
                brVar.s();
            }
        }
        brVar.i();
        if (this.g == null) {
            this.g = "";
        }
        if (this.i != null) {
            for (go goVar : this.i) {
                if (goVar.f == null) {
                    goVar.f = strB;
                }
                if (goVar.e == null) {
                    goVar.e = strB2;
                }
            }
        }
        if (this.j != null) {
            for (go goVar2 : this.j) {
                if (goVar2.f == null) {
                    goVar2.f = strB;
                }
                if (goVar2.e == null) {
                    goVar2.e = strB2;
                }
            }
        }
    }

    public final boolean a() {
        return (this.c == null || this.a == null || this.e == null) ? false : true;
    }

    public final boolean b() {
        return (this.c == null || this.b == null || this.f == null) ? false : true;
    }
}
