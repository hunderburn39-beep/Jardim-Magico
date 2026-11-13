package com.tapjoy.internal;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public final class x {
    private static Handler a;

    public static synchronized Handler a() {
        if (a == null) {
            a = new Handler(Looper.getMainLooper());
        }
        return a;
    }

    public static be a(final Handler handler) {
        return new be() { // from class: com.tapjoy.internal.x.1
            @Override // com.tapjoy.internal.be
            public final boolean a(Runnable runnable) {
                return handler.post(runnable);
            }
        };
    }
}
