package com.tapjoy.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class m extends o {
    private final int c;

    public m(SharedPreferences sharedPreferences, String str, int i) {
        super(sharedPreferences, str);
        this.c = i;
    }

    public final Integer a() {
        return Integer.valueOf(b());
    }

    public final int b() {
        return this.a.getInt(this.b, this.c);
    }

    public final void a(int i) {
        this.a.edit().putInt(this.b, i).commit();
    }

    public final SharedPreferences.Editor a(SharedPreferences.Editor editor, int i) {
        return editor.putInt(this.b, i);
    }
}
