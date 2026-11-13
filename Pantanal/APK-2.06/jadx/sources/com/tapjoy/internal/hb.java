package com.tapjoy.internal;

import com.tapjoy.TJAdUnitConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class hb extends gy {
    private final gl.l c;
    private final gl.a d;
    private final gl.z e;
    private final String f;

    private hb(gl.l lVar, gl.a aVar, gl.z zVar, String str) {
        this.c = lVar;
        this.d = aVar;
        this.e = zVar;
        this.f = str;
    }

    @Override // com.tapjoy.internal.ce
    public final String c() {
        return "api/v1/tokens";
    }

    @Override // com.tapjoy.internal.gy, com.tapjoy.internal.ce
    public final Map e() {
        Map mapE = super.e();
        mapE.put(TJAdUnitConstants.String.VIDEO_INFO, new bq(gk.a(this.c)));
        mapE.put("app", new bq(gk.a(this.d)));
        mapE.put("user", new bq(gk.a(this.e)));
        String str = this.f;
        if (!(str == null || str.length() == 0)) {
            mapE.put("push_token", this.f);
        }
        return mapE;
    }

    public hb(gl.n nVar, String str) {
        this(nVar.c, nVar.d, nVar.e, str);
    }
}
