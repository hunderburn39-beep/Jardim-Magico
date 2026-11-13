package com.tapjoy.internal;

import android.content.Context;
import com.tapjoy.TJAdUnitConstants;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class gz extends gy {
    public final String c;
    public boolean d = false;
    private final fu e;
    private final gl.l f;
    private final gl.a g;
    private final gl.z h;
    private Context i;

    public gz(fu fuVar, gl.l lVar, gl.a aVar, gl.z zVar, String str, Context context) {
        this.e = fuVar;
        this.f = lVar;
        this.g = aVar;
        this.h = zVar;
        this.c = str;
        this.i = context;
    }

    @Override // com.tapjoy.internal.ce
    public final String c() {
        return "placement";
    }

    @Override // com.tapjoy.internal.gy, com.tapjoy.internal.ce
    public final Map e() {
        Map mapE = super.e();
        mapE.put(TJAdUnitConstants.String.VIDEO_INFO, new bq(gk.a(this.f)));
        mapE.put("app", new bq(gk.a(this.g)));
        mapE.put("user", new bq(gk.a(this.h)));
        mapE.put("placement", this.c);
        return mapE;
    }

    public static class a {
        public gb a;
        public final List b;

        public a(gb gbVar, List list) {
            this.a = gbVar;
            this.b = list;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.gy, com.tapjoy.internal.cd
    public final /* synthetic */ Object a(br brVar) {
        brVar.h();
        List listC = null;
        gn gnVar = null;
        gq gqVar = null;
        while (brVar.j()) {
            String strL = brVar.l();
            if ("interstitial".equals(strL)) {
                gqVar = (gq) brVar.a(gq.n);
            } else if ("contextual_button".equals(strL)) {
                gnVar = (gn) brVar.a(gn.d);
            } else if ("enabled_placements".equals(strL)) {
                listC = brVar.c();
            } else {
                brVar.s();
            }
        }
        brVar.i();
        if (gqVar != null && (gqVar.a() || gqVar.b())) {
            return new a(new fz(this.e, this.c, gqVar, this.i), listC);
        }
        if (gnVar != null) {
            return new a(new fq(this.e, this.c, gnVar, this.i), listC);
        }
        return new a(new ga(), listC);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.gy, com.tapjoy.internal.ce
    public final /* synthetic */ Object f() {
        a aVar = (a) super.f();
        if (!(aVar.a instanceof ga)) {
            aVar.a.a();
            if (!aVar.a.b()) {
                new Object[1][0] = this.c;
                aVar.a = new ga();
            }
        }
        return aVar;
    }
}
