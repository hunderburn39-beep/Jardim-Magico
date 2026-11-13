package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class cs {
    private static void a(Throwable th, Class cls) throws Throwable {
        if (th != null && cls.isInstance(th)) {
            throw ((Throwable) cls.cast(th));
        }
    }

    public static RuntimeException a(Throwable th) throws Throwable {
        Throwable th2 = (Throwable) cq.a(th);
        a(th2, Error.class);
        a(th2, RuntimeException.class);
        throw new RuntimeException(th);
    }
}
