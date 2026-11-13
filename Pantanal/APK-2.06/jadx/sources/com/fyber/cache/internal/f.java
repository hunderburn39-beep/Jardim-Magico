package com.fyber.cache.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.fyber.utils.FyberLogger;
import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CacheStore.java */
/* JADX INFO: loaded from: classes.dex */
public final class f {
    public static final f a = new f();
    private final File b;
    private final boolean c;
    private final SharedPreferences d;
    private Map<String, c> e;

    protected f() {
        this.c = false;
        this.b = null;
        this.d = null;
        this.e = new HashMap();
    }

    public f(Context context) {
        File file = new File(context.getCacheDir(), "FyberVideoCache");
        if (!file.exists()) {
            FyberLogger.d("CacheStore", "The cache directory does not exist, creating...");
            file.mkdirs();
        }
        this.b = file;
        this.d = context.getSharedPreferences("FyberCacheStorage", 0);
        this.c = e();
    }

    private void f() {
        boolean z;
        boolean z2 = false;
        for (c cVar : this.e.values()) {
            if (cVar.a().exists()) {
                z = z2;
            } else {
                FyberLogger.d("CacheStore", "Local file for cache entry " + cVar.b() + " was removed.");
                cVar.a(0);
                z = true;
            }
            z2 = z;
        }
        if (z2) {
            FyberLogger.d("CacheStore", "Saving Cache file.");
            g();
        }
    }

    public final c a(String str) {
        return this.e.get(Uri.parse(str).getEncodedSchemeSpecificPart());
    }

    public final Map<String, c> a() {
        return this.e;
    }

    public final c a(h hVar) {
        c cVar;
        String strB = hVar.b();
        String encodedSchemeSpecificPart = Uri.parse(strB).getEncodedSchemeSpecificPart();
        if (this.c) {
            if (this.e.containsKey(encodedSchemeSpecificPart)) {
                cVar = this.e.get(encodedSchemeSpecificPart);
            } else {
                cVar = new c(i(), strB, 0);
                this.e.put(encodedSchemeSpecificPart, cVar);
            }
        } else {
            cVar = new c(i(), strB, 4);
        }
        cVar.a(hVar);
        g();
        return cVar;
    }

    public final void b() {
        g();
    }

    public static String a(Collection<c> collection) {
        return String.format(Locale.ENGLISH, "{\"cache\":[%s]}", TextUtils.join(",", collection));
    }

    private void h() {
        FyberLogger.d("CacheStore", "Cache storage file recovering issue, purging the local files...");
        File[] fileArrListFiles = this.b.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            file.delete();
        }
    }

    public final int c() {
        int size = this.e.size();
        Iterator<c> it = this.e.values().iterator();
        while (true) {
            int i = size;
            if (it.hasNext()) {
                c next = it.next();
                size = (next.a().exists() && next.c() == 2) ? i : i - 1;
            } else {
                return i;
            }
        }
    }

    private File i() {
        File file = new File(this.b, UUID.randomUUID().toString());
        if (file.exists()) {
            FyberLogger.i("CacheStore", "Video already exists in cache: " + file.getAbsolutePath());
            file = i();
        }
        FyberLogger.i("CacheStore", "Save in file: " + file.getAbsolutePath());
        return file;
    }

    public final void a(int i) {
        if (i >= 0 && i < this.e.size()) {
            FyberLogger.i("CacheStore", "Trimming cache to " + i + " slots");
            b(this.e.size() - i);
        }
    }

    public final void b(int i) {
        if (i > 0 && !this.e.isEmpty()) {
            FyberLogger.i("CacheStore", "Freeing up " + i + " cache slots");
            TreeSet treeSet = new TreeSet(new a((byte) 0));
            treeSet.addAll(this.e.values());
            while (true) {
                c cVar = (c) treeSet.pollFirst();
                if (cVar == null || i <= 0) {
                    break;
                }
                c cVarRemove = this.e.remove(Uri.parse(cVar.b()).getEncodedSchemeSpecificPart());
                if (cVarRemove != null) {
                    cVarRemove.a().delete();
                    g();
                }
                i--;
            }
            FyberLogger.i("CacheStore", "Current cache size: " + this.e.size() + " slots");
        }
    }

    public final boolean d() {
        return this.c;
    }

    /* JADX INFO: compiled from: CacheStore.java */
    private static class a implements Comparator<c> {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        @Override // java.util.Comparator
        public final /* synthetic */ int compare(c cVar, c cVar2) {
            long jG = cVar.g() - cVar2.g();
            if (jG < 0) {
                return -1;
            }
            if (jG > 0) {
                return 1;
            }
            return 0;
        }
    }

    private boolean e() {
        try {
            this.e = new HashMap();
            if (this.d.getAll().isEmpty()) {
                h();
            } else {
                try {
                    JSONArray jSONArray = new JSONObject(this.d.getString("FyberCacheStorage", "{\"cache\":[]}")).getJSONArray("cache");
                    int length = jSONArray.length();
                    for (int i = 0; i < length; i++) {
                        c cVar = new c(jSONArray.getJSONObject(i));
                        this.e.put(Uri.parse(cVar.b()).getEncodedSchemeSpecificPart(), cVar);
                    }
                } catch (JSONException e) {
                    h();
                }
            }
            f();
            return this.b.exists() && this.b.isDirectory();
        } catch (Exception e2) {
            this.e = new HashMap();
            return false;
        }
    }

    private void g() {
        if (this.c) {
            this.d.edit().putString("FyberCacheStorage", a(this.e.values())).apply();
        }
    }
}
