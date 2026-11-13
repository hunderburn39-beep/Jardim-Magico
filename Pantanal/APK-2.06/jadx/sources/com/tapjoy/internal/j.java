package com.tapjoy.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class j extends o {
    private final boolean c;

    public j(SharedPreferences sharedPreferences, String str) {
        super(sharedPreferences, str);
        this.c = false;
    }

    public final Boolean a() {
        return Boolean.valueOf(b());
    }

    public final boolean b() {
        return this.a.getBoolean(this.b, this.c);
    }

    public final void a(boolean z) {
        this.a.edit().putBoolean(this.b, z).commit();
    }
}
