package com.tapjoy.internal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public abstract class gb {
    long h;
    boolean i;
    public fn j;
    public String k;

    public abstract void a();

    public abstract void a(fv fvVar, eq eqVar);

    public boolean b() {
        return true;
    }

    static void a(Context context, String str) {
        if (!cr.c(str)) {
            try {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            } catch (Exception e) {
            }
        }
    }
}
