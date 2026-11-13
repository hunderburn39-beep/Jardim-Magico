package com.google.android.gms.internal;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class cr {

    public interface a {
        void a(dh dhVar);
    }

    public static Cdo a(Context context, cx.a aVar, l lVar, dz dzVar, bq bqVar, a aVar2) {
        cs csVar = new cs(context, aVar, lVar, dzVar, bqVar, aVar2);
        csVar.start();
        return csVar;
    }
}
