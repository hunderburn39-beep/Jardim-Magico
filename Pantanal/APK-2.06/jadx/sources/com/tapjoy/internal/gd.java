package com.tapjoy.internal;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class gd {
    final q a;
    public final q b;
    private final SharedPreferences c;

    public gd(Context context) {
        this.c = context.getApplicationContext().getSharedPreferences("fiverocks", 0);
        this.a = new q(this.c, "noMoreToday.date");
        this.b = new q(this.c, "noMoreToday.actionIds");
        b();
    }

    static String a() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public final void b() {
        String strA = this.a.a();
        if (strA != null && !a().equals(strA)) {
            this.a.a(null);
            this.b.a(null);
        }
    }
}
