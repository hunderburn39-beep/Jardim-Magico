package com.chartboost.sdk;

import android.content.Context;
import com.chartboost.sdk.impl.bb;
import com.chartboost.sdk.impl.j;
import com.chartboost.sdk.impl.l;
import com.chartboost.sdk.impl.m;
import com.chartboost.sdk.impl.n;
import com.chartboost.sdk.impl.r;

/* JADX INFO: loaded from: classes.dex */
public class g {
    private static g a = new g();

    public static g a() {
        return a;
    }

    public <T> T a(T t) {
        return t;
    }

    public bb a(Context context) {
        return new bb(context);
    }

    public r a(Context context, l lVar) {
        return new r(context, lVar);
    }

    public j b(Context context, l lVar) {
        return new j(context, lVar);
    }

    public n b(Context context) {
        return new n(context);
    }

    public m c(Context context, l lVar) {
        return new m(context, lVar);
    }
}
