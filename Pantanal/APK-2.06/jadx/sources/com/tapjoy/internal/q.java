package com.tapjoy.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class q extends o {
    private final String c;

    public q(SharedPreferences sharedPreferences, String str) {
        super(sharedPreferences, str);
        this.c = null;
    }

    public final String a() {
        return this.a.getString(this.b, this.c);
    }

    public final void a(String str) {
        this.a.edit().putString(this.b, str).commit();
    }

    public final SharedPreferences.Editor a(SharedPreferences.Editor editor, String str) {
        return str != null ? editor.putString(this.b, str) : editor.remove(this.b);
    }
}
