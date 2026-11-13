package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public abstract class au implements ar {
    protected abstract as a(Object obj, boolean z);

    @Override // com.tapjoy.internal.ar
    public final Object a(Object obj) {
        as asVarA = a(obj, false);
        if (asVarA != null) {
            return asVarA.a();
        }
        return null;
    }

    @Override // com.tapjoy.internal.ar
    public void a(Object obj, Object obj2) {
        a(obj, true).a(obj2);
    }
}
