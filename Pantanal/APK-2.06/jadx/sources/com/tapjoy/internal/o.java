package com.tapjoy.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public abstract class o {
    protected SharedPreferences a;
    protected String b;

    public o(SharedPreferences sharedPreferences, String str) {
        this.a = sharedPreferences;
        this.b = str;
    }

    public final void c() {
        this.a.edit().remove(this.b).commit();
    }
}
