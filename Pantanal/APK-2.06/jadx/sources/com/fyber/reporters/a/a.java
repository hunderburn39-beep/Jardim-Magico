package com.fyber.reporters.a;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: compiled from: AdvertiserState.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private SharedPreferences a;

    public a(Context context) {
        this.a = context.getSharedPreferences("FyberPreferences", 0);
    }

    public final void a(String str) {
        this.a.edit().putString("AdvertiserAnswerReceived" + str, "1").apply();
    }

    public final String b(String str) {
        return this.a.getString("AdvertiserAnswerReceived" + str, "0");
    }

    public final void c(String str) {
        this.a.edit().putString("InstallSubId", str).apply();
    }

    public final String a() {
        return this.a.getString("InstallSubId", "");
    }

    public final void d(String str) {
        this.a.edit().putString("InstallReferrer", str).apply();
    }

    public final String b() {
        return this.a.getString("InstallReferrer", "");
    }
}
