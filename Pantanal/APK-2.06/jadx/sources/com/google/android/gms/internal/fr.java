package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class fr extends com.google.android.gms.dynamic.g<fn> {
    private static final fr DK = new fr();

    private fr() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View b(Context context, int i, int i2) throws com.google.android.gms.dynamic.g.a {
        return DK.c(context, i, i2);
    }

    private View c(Context context, int i, int i2) throws com.google.android.gms.dynamic.g.a {
        try {
            return (View) com.google.android.gms.dynamic.e.d(z(context).a(com.google.android.gms.dynamic.e.h(context), i, i2));
        } catch (Exception e) {
            throw new com.google.android.gms.dynamic.g.a("Could not get button with size " + i + " and color " + i2, e);
        }
    }

    @Override // com.google.android.gms.dynamic.g
    /* JADX INFO: renamed from: E, reason: merged with bridge method [inline-methods] */
    public fn d(IBinder iBinder) {
        return fn.a.D(iBinder);
    }
}
