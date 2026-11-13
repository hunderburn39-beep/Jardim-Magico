package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class aq implements as {
    public final Object a;
    protected Object b = null;

    public aq(Object obj) {
        this.a = obj;
    }

    @Override // com.tapjoy.internal.as
    public final Object a() {
        return this.b;
    }

    @Override // com.tapjoy.internal.as
    public final void a(Object obj) {
        this.b = obj;
    }
}
