package com.tapjoy.internal;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ha extends gy {
    public final gl.d.a c = gl.d.e();
    private gl.i d = null;

    @Override // com.tapjoy.internal.ce
    public final String c() {
        return this.d == gl.i.USAGES ? "api/v1/usages" : "api/v1/cevs";
    }

    public final boolean a(gl.c cVar) {
        if (this.d == null) {
            this.d = cVar.c;
        } else if (cVar.c != this.d) {
            return false;
        }
        gl.d.a aVar = this.c;
        if (cVar == null) {
            throw new NullPointerException();
        }
        aVar.e();
        aVar.b.add(cVar);
        return true;
    }

    @Override // com.tapjoy.internal.gy, com.tapjoy.internal.ce
    public final Map e() {
        Map mapE = super.e();
        mapE.put("events", new bq(gk.a(this.c.d())));
        return mapE;
    }
}
