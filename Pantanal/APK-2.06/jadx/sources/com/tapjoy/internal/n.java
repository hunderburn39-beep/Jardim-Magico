package com.tapjoy.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class n extends o {
    private final long c;

    public n(SharedPreferences sharedPreferences, String str) {
        super(sharedPreferences, str);
        this.c = 0L;
    }

    public final long a() {
        return this.a.getLong(this.b, this.c);
    }

    public final void a(long j) {
        this.a.edit().putLong(this.b, j).commit();
    }

    public final SharedPreferences.Editor a(SharedPreferences.Editor editor) {
        return editor.remove(this.b);
    }

    public final SharedPreferences.Editor a(SharedPreferences.Editor editor, long j) {
        return editor.putLong(this.b, j);
    }
}
