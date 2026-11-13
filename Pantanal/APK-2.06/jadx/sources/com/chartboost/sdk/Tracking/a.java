package com.chartboost.sdk.Tracking;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.d;
import com.chartboost.sdk.Libraries.f;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Model.c;
import com.chartboost.sdk.b;
import com.chartboost.sdk.impl.ac;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a implements com.chartboost.sdk.Libraries.a {
    private static a c;
    private static final Long n = Long.valueOf(TimeUnit.MINUTES.toMillis(5));
    private final ak d;
    private final AtomicReference<c> e;
    private final SharedPreferences f;
    private String g;
    private JSONArray h;
    private long i;
    private long j;
    private final long k;
    private final g l;
    private boolean m = false;
    private long o = System.currentTimeMillis() - n.longValue();

    public a(ak akVar, AtomicReference<c> atomicReference, SharedPreferences sharedPreferences) {
        c = this;
        this.d = akVar;
        this.e = atomicReference;
        this.f = sharedPreferences;
        this.k = System.currentTimeMillis();
        this.h = (JSONArray) com.chartboost.sdk.g.a().a(new JSONArray());
        this.l = new g(false);
    }

    public void a() {
        a(TJAdUnitConstants.String.VIDEO_START);
        a("did-become-active");
    }

    private void a(String str) {
        if (this.e.get().n) {
            a("session", str, (String) null, (String) null, (String) null, (String) null, false);
        }
    }

    public void b() {
        a(false);
    }

    private void a(boolean z) {
        if (this.e.get().n) {
            JSONObject jSONObject = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
            d.a(jSONObject, TJAdUnitConstants.String.VIDEO_COMPLETE, Boolean.valueOf(z));
            a("session", "end", null, null, null, null, jSONObject, false);
            a("did-become-active");
        }
    }

    public void a(String str, String str2, String str3, String str4) {
        if (this.e.get().o) {
            a("webview-track", str, str2, str3, str4, null, null, false);
        }
    }

    public void a(JSONObject jSONObject) {
        c cVar = this.e.get();
        if (cVar.o) {
            a("folder", b.a(cVar), null, null, null, null, jSONObject, false);
        }
    }

    public void a(String str, String str2, String str3, boolean z) {
        if (this.e.get().o) {
            a("ad-get", str, str2, TextUtils.isEmpty(str3) ? "empty-adid" : str3, b(z), AdColonyUserMetadata.USER_SINGLE, null, false);
        }
    }

    public void a(String str, String str2, String str3) {
        if (this.e.get().o) {
            a("ad-show", str, str2, str3, (String) null, (String) null, false);
        }
    }

    public void b(String str, String str2, String str3) {
        if (this.e.get().o) {
            a("ad-click", str, str2, str3, (String) null, (String) null, false);
        }
    }

    public void c(String str, String str2, String str3) {
        if (this.e.get().o) {
            a("ad-close", str, str2, str3, (String) null, (String) null, false);
        }
    }

    public void d(String str, String str2, String str3) {
        if (this.e.get().o) {
            a("ad-dismiss", str, str2, str3, (String) null, (String) null, false);
        }
    }

    public void a(String str, String str2, String str3, CBError.CBImpressionError cBImpressionError) {
        if (this.e.get().o) {
            a("ad-error", str, str2, TextUtils.isEmpty(str3) ? "empty-adid" : str3, cBImpressionError != null ? cBImpressionError.toString() : "", (String) null, false);
        }
    }

    public void a(String str, String str2, String str3, String str4, boolean z) {
        if (this.e.get().o) {
            a("ad-error", str, str2, TextUtils.isEmpty(str3) ? "empty-adid" : str3, str4, (String) null, z);
        }
    }

    public void b(String str, String str2, String str3, String str4) {
        if (this.e.get().o) {
            a("ad-warning", str, str2, TextUtils.isEmpty(str3) ? "empty-adid" : str3, str4, (String) null, false);
        }
    }

    public void a(String str, String str2) {
        c cVar = this.e.get();
        if (cVar.o) {
            a("asset-prefetcher", TJAdUnitConstants.String.VIDEO_START, b.a(cVar), str, str2, null, null, false);
        }
    }

    public void c(String str, String str2, String str3, String str4) {
        c cVar = this.e.get();
        if (cVar.o) {
            a("asset-prefetcher", "failure", b.a(cVar), str, str2, str4, null, false);
        }
    }

    public void e(String str, String str2, String str3) {
        c cVar = this.e.get();
        if (cVar.o) {
            a("asset-prefetcher", "success", b.a(cVar), str, str2, str3, null, false);
        }
    }

    public void b(String str, String str2) {
        if (this.e.get().o) {
            a("playback-complete", str, str2, (String) null, (String) null, (String) null, false);
        }
    }

    public void c(String str, String str2) {
        if (this.e.get().o) {
            a("replay", str, str2, (String) null, (String) null, (String) null, false);
        }
    }

    public void d(String str, String str2) {
        if (this.e.get().o) {
            a("playback-start", str, str2, (String) null, (String) null, (String) null, false);
        }
    }

    public void e(String str, String str2) {
        if (this.e.get().o) {
            a("playback-stop", str, str2, (String) null, (String) null, (String) null, false);
        }
    }

    public static void a(Class cls, String str, Exception exc) {
        exc.printStackTrace();
        a aVar = c;
        if (aVar != null) {
            aVar.b(cls, str, exc);
        }
    }

    private synchronized void b(Class cls, String str, Exception exc) {
        c cVar = this.e.get();
        if (cVar != null && cVar.l && !this.m) {
            this.m = true;
            try {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - this.o >= n.longValue()) {
                        a("exception", cls.getName(), str, exc.getClass().getName(), exc.getMessage(), Log.getStackTraceString(exc), null, true);
                        this.o = jCurrentTimeMillis;
                    }
                    this.m = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    this.m = false;
                }
            } catch (Throwable th) {
                this.m = false;
                throw th;
            }
        }
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, JSONObject jSONObject) {
        if (this.e.get().o) {
            a(str, str2, str3, str4, str5, str6, jSONObject, false);
        }
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        a(str, str2, str3, str4, str5, str6, (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject()), z);
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, JSONObject jSONObject, boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.i;
        long j2 = jCurrentTimeMillis - this.k;
        JSONObject jSONObject2 = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
        d.a(jSONObject2, "event", a((Object) str));
        d.a(jSONObject2, "kingdom", a((Object) str2));
        d.a(jSONObject2, "phylum", a((Object) str3));
        d.a(jSONObject2, "class", a((Object) str4));
        d.a(jSONObject2, "family", a((Object) str5));
        d.a(jSONObject2, "genus", a((Object) str6));
        d.a(jSONObject2, "meta", jSONObject == null ? (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject()) : jSONObject);
        d.a(jSONObject2, "clientTimestamp", Long.valueOf(System.currentTimeMillis() / 1000));
        d.a(jSONObject2, TapjoyConstants.TJC_SESSION_ID, d());
        d.a(jSONObject2, "totalSessionTime", Long.valueOf(j / 1000));
        d.a(jSONObject2, "currentSessionTime", Long.valueOf(j2 / 1000));
        synchronized (this) {
            boolean z2 = this.m;
            this.m = true;
            try {
                this.h.put(jSONObject2);
                JSONObject jSONObject3 = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
                d.a(jSONObject3, "events", this.h);
                CBLogging.a("CBTrack", "###Writing" + a((Object) str) + "to tracking cache dir");
                h();
                if (z || e()) {
                    b(jSONObject3).j();
                    k();
                }
                this.m = z2;
            } catch (Throwable th) {
                this.m = z2;
                throw th;
            }
        }
    }

    private boolean e() {
        return this.h != null && this.h.length() >= 50;
    }

    private String f() {
        JSONObject jSONObject = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
        d.a(jSONObject, "startTime", Long.valueOf(System.currentTimeMillis()));
        d.a(jSONObject, "deviceID", this.d.a.c());
        this.g = com.chartboost.sdk.Libraries.b.b(jSONObject.toString().getBytes());
        return this.g;
    }

    public void c() {
        File file = new File(this.l.b(), "cb_previous_session_info");
        JSONObject jSONObjectA = file.exists() ? this.l.a(file) : null;
        if (jSONObjectA != null) {
            this.j = jSONObjectA.optLong("timestamp");
            this.i = jSONObjectA.optLong("start_timestamp");
            this.g = jSONObjectA.optString(TapjoyConstants.TJC_SESSION_ID);
            if (System.currentTimeMillis() - this.j <= 180000) {
                if (!TextUtils.isEmpty(this.g)) {
                    h();
                    return;
                }
            } else {
                a(true);
            }
        }
        g();
    }

    private void g() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.i = jCurrentTimeMillis;
        this.j = jCurrentTimeMillis;
        this.g = f();
        a(jCurrentTimeMillis, jCurrentTimeMillis);
        int i = this.f.getInt("cbPrefSessionCount", 0) + 1;
        SharedPreferences.Editor editorEdit = this.f.edit();
        editorEdit.putInt("cbPrefSessionCount", i);
        editorEdit.apply();
    }

    private void h() {
        a(this.i, System.currentTimeMillis());
    }

    private void a(long j, long j2) {
        JSONObject jSONObject = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
        d.a(jSONObject, "start_timestamp", Long.valueOf(j));
        d.a(jSONObject, "timestamp", Long.valueOf(j2));
        d.a(jSONObject, TapjoyConstants.TJC_SESSION_ID, this.g);
        this.l.a(this.l.b(), "cb_previous_session_info", jSONObject.toString().getBytes());
    }

    private aj b(JSONObject jSONObject) {
        aj ajVar = new aj("/api/track", this.d);
        ajVar.a("track", (Object) jSONObject);
        ajVar.a(f.a(f.a("status", com.chartboost.sdk.Libraries.a.b)));
        ajVar.a(ac.b.LOW);
        return ajVar;
    }

    public String toString() {
        return "Session [ startTime: " + j() + " sessionEvents: " + i() + " ]";
    }

    String d() {
        return this.g;
    }

    private JSONArray i() {
        return this.h;
    }

    private long j() {
        return this.i;
    }

    private static String b(boolean z) {
        return z ? "1" : "0";
    }

    private static Object a(Object obj) {
        return obj != null ? obj : "";
    }

    private void k() {
        this.h = (JSONArray) com.chartboost.sdk.g.a().a(new JSONArray());
    }
}
