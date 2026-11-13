package com.fyber.cache.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.fyber.cache.CacheManager;
import java.util.Iterator;
import org.json.JSONArray;

/* JADX INFO: compiled from: CacheStatistics.java */
/* JADX INFO: loaded from: classes.dex */
public final class e {
    public static final e a = new e();
    private final SharedPreferences b;
    private volatile int c;

    protected e() {
        this.b = null;
    }

    public e(Context context) {
        this.b = context.getSharedPreferences("FyberCacheStatistics", 0);
        this.c = this.b.getInt("download.count", 0);
    }

    public final int a() {
        return this.c;
    }

    public final void b() {
        this.c++;
        e();
    }

    public final void c() {
        this.c = 0;
        e();
    }

    public static String d() {
        JSONArray jSONArray = new JSONArray();
        f fVarC = CacheManager.a().c();
        if (fVarC != null) {
            for (c cVar : fVarC.a().values()) {
                if (cVar.c() == 2 && cVar.a().exists()) {
                    Iterator<h> it = cVar.f().iterator();
                    while (it.hasNext()) {
                        jSONArray.put(it.next().a());
                    }
                }
            }
        }
        return jSONArray.toString();
    }

    private void e() {
        if (this.b != null) {
            this.b.edit().putInt("download.count", this.c).apply();
        }
    }
}
