package com.tapjoy.internal;

import android.content.Context;
import com.tapjoy.TJPlacementListener;

/* JADX INFO: loaded from: classes.dex */
abstract class ew {
    private static final ew a;
    private static ew b;

    public abstract Object a(Context context, String str, TJPlacementListener tJPlacementListener);

    ew() {
    }

    static {
        ex exVar = new ex();
        a = exVar;
        b = exVar;
    }

    static ew a() {
        return b;
    }
}
