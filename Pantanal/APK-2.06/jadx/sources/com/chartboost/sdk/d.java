package com.chartboost.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.af;
import com.chartboost.sdk.impl.ai;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.chartboost.sdk.impl.am;
import com.tapjoy.TapjoyConstants;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class d {
    public final com.chartboost.sdk.impl.b a;
    public final com.chartboost.sdk.impl.d b;
    final c c;
    public final af d;
    public final ak e;
    public final h f;
    public final AtomicReference<com.chartboost.sdk.Model.c> g;
    public final SharedPreferences h;
    public final com.chartboost.sdk.Tracking.a i;
    public final e j;
    public final am k;
    private final ai o;
    protected final Handler l = CBUtility.c();
    public com.chartboost.sdk.Model.a.b n = com.chartboost.sdk.Model.a.b.NATIVE;
    private a s = null;
    private final ConcurrentHashMap<String, com.chartboost.sdk.Model.a> q = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, com.chartboost.sdk.Model.a> m = new ConcurrentHashMap<>();
    private final Map<String, com.chartboost.sdk.Model.a> p = new HashMap();
    private final ConcurrentHashMap<String, com.chartboost.sdk.Model.a> r = new ConcurrentHashMap<>();

    /* JADX INFO: Access modifiers changed from: protected */
    public interface a {
        void a(com.chartboost.sdk.Model.a aVar);

        void a(com.chartboost.sdk.Model.a aVar, CBError.CBImpressionError cBImpressionError);

        void b(com.chartboost.sdk.Model.a aVar);

        void c(com.chartboost.sdk.Model.a aVar);

        void d(com.chartboost.sdk.Model.a aVar);

        void e(com.chartboost.sdk.Model.a aVar);

        boolean f(com.chartboost.sdk.Model.a aVar);

        boolean g(com.chartboost.sdk.Model.a aVar);

        boolean h(com.chartboost.sdk.Model.a aVar);
    }

    protected abstract com.chartboost.sdk.Model.a a(String str, boolean z);

    protected abstract a b();

    protected abstract aj e(com.chartboost.sdk.Model.a aVar);

    public abstract String e();

    protected abstract aj l(com.chartboost.sdk.Model.a aVar);

    public d(com.chartboost.sdk.impl.b bVar, com.chartboost.sdk.impl.d dVar, c cVar, af afVar, ai aiVar, ak akVar, h hVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, SharedPreferences sharedPreferences, com.chartboost.sdk.Tracking.a aVar, e eVar, am amVar) {
        this.a = bVar;
        this.b = dVar;
        this.c = cVar;
        this.d = afVar;
        this.o = aiVar;
        this.e = akVar;
        this.f = hVar;
        this.g = atomicReference;
        this.h = sharedPreferences;
        this.i = aVar;
        this.j = eVar;
        this.k = amVar;
    }

    public void a(String str) {
        final com.chartboost.sdk.Model.a aVarA;
        final com.chartboost.sdk.Model.a aVar = this.q.get(str);
        final com.chartboost.sdk.Model.a aVar2 = this.m.get(str);
        if (aVar2 != null) {
            aVarA = aVar2;
        } else if (aVar != null) {
            aVarA = aVar;
        } else {
            com.chartboost.sdk.Model.a aVarC = c(str);
            if (aVarC != null) {
                aVarC.m = true;
                CBLogging.b(getClass().getSimpleName(), String.format("%s %s", "Request already in process for impression with location", str));
                return;
            }
            aVarA = a(str, false);
        }
        e eVarM = this.f.m();
        if (eVarM != null && eVarM.c()) {
            if (a() != null) {
                a().a(aVarA, CBError.CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
            }
        } else if (!b(aVarA)) {
            final com.chartboost.sdk.Model.a.d dVar = this.c.d;
            this.l.post(new Runnable() { // from class: com.chartboost.sdk.d.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (aVar != null) {
                            if (aVar.d == com.chartboost.sdk.Model.a.e.NONE) {
                                aVar.d = com.chartboost.sdk.Model.a.e.CACHED;
                            }
                            d.this.g(aVar);
                            return;
                        }
                        if (aVar2 != null && aVar2.w().length() != 0) {
                            aVar2.a(aVar2.w(), dVar);
                        } else {
                            d.this.c(aVarA);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(d.class, "show Runnable.run", e);
                    }
                }
            });
        }
    }

    public void b(String str) {
        com.chartboost.sdk.Model.a aVar = this.q.get(str);
        com.chartboost.sdk.Model.a aVar2 = this.m.get(str);
        if (aVar2 != null) {
            a().d(aVar2);
            return;
        }
        if (aVar != null) {
            a().d(aVar);
            return;
        }
        if (c(str) != null) {
            CBLogging.b(getClass().getSimpleName(), String.format("%s %s", "Request already in process for impression with location", str));
            return;
        }
        com.chartboost.sdk.Model.a aVarA = a(str, true);
        if (!b(aVarA)) {
            c(aVarA);
        }
    }

    protected void a(com.chartboost.sdk.Model.a aVar) {
        p(aVar);
        a().d(aVar);
        aVar.d = com.chartboost.sdk.Model.a.e.CACHED;
    }

    protected final boolean b(com.chartboost.sdk.Model.a aVar) {
        if (a().h(aVar) || this.h.getInt("cbPrefSessionCount", 0) != 1) {
            return false;
        }
        a(aVar, CBError.CBImpressionError.FIRST_SESSION_INTERSTITIALS_DISABLED);
        return true;
    }

    protected void c(com.chartboost.sdk.Model.a aVar) {
        aj ajVarE;
        if (f(aVar) && a().g(aVar)) {
            if (!aVar.n && aVar.e == com.chartboost.sdk.Model.a.EnumC0011a.MORE_APPS && i.u) {
                aVar.q = true;
                this.f.a(aVar);
            }
            if (d(aVar) && (ajVarE = e(aVar)) != null) {
                a(ajVarE, aVar);
                o(aVar);
                this.i.a(e(), aVar.f, aVar.p(), aVar.n);
            }
        }
    }

    protected boolean d(com.chartboost.sdk.Model.a aVar) {
        return true;
    }

    public void a(final com.chartboost.sdk.Model.a aVar, final CBError.CBImpressionError cBImpressionError) {
        h.a(new Runnable() { // from class: com.chartboost.sdk.d.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    d.this.n(aVar);
                    e eVarM = d.this.f.m();
                    if (eVarM != null && eVarM.b()) {
                        eVarM.a(aVar, true);
                    } else if (aVar.d == com.chartboost.sdk.Model.a.e.DISPLAYED && eVarM != null) {
                        eVarM.b(aVar);
                    }
                    d.this.a().a(aVar, cBImpressionError);
                    String strP = aVar.p();
                    com.chartboost.sdk.Tracking.a aVar2 = d.this.i;
                    String strE = aVar.q().e();
                    String str = aVar.f;
                    if (TextUtils.isEmpty(strP)) {
                        strP = "";
                    }
                    aVar2.a(strE, str, strP, cBImpressionError);
                } catch (Exception e) {
                    com.chartboost.sdk.Tracking.a.a(d.class, "handleError Runnable.run", e);
                }
            }
        });
    }

    protected final boolean f(com.chartboost.sdk.Model.a aVar) {
        if (this.f == null) {
            a(aVar, CBError.CBImpressionError.SESSION_NOT_STARTED);
            return false;
        }
        e eVarM = this.f.m();
        if (!aVar.n && eVarM != null && eVarM.c()) {
            if (a() == null) {
                return false;
            }
            a().a(aVar, CBError.CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
            return false;
        }
        if (!this.o.b()) {
            a(aVar, CBError.CBImpressionError.INTERNET_UNAVAILABLE);
            return false;
        }
        return true;
    }

    protected void g(com.chartboost.sdk.Model.a aVar) {
        boolean z = aVar.d != com.chartboost.sdk.Model.a.e.DISPLAYED;
        if (z) {
            if (i.d != null && i.d.doesWrapperUseCustomShouldDisplayBehavior()) {
                this.r.put(aVar.f == null ? "" : aVar.f, aVar);
            }
            if (!a().f(aVar)) {
                return;
            }
        }
        a(aVar, z);
    }

    protected void b(String str, boolean z) {
        if (str == null) {
            str = "";
        }
        com.chartboost.sdk.Model.a aVar = this.r.get(str);
        if (aVar != null) {
            this.r.remove(str);
            if (z) {
                a(aVar, true);
            }
        }
    }

    private void a(com.chartboost.sdk.Model.a aVar, boolean z) {
        boolean z2 = aVar.d == com.chartboost.sdk.Model.a.e.CACHED;
        i(aVar);
        e eVarM = this.f.m();
        if (eVarM != null) {
            if (eVarM.b()) {
                eVarM.a(aVar, false);
            } else if (aVar.q && !z2 && aVar.d != com.chartboost.sdk.Model.a.e.DISPLAYED) {
                return;
            }
        }
        if (z) {
            h(aVar);
        } else {
            this.f.a(aVar);
        }
    }

    public void h(com.chartboost.sdk.Model.a aVar) {
        this.f.a(aVar);
    }

    protected void i(com.chartboost.sdk.Model.a aVar) {
        j(aVar);
    }

    public void j(com.chartboost.sdk.Model.a aVar) {
        if (!aVar.o) {
            if (aVar.q().a() != null) {
                aVar.q().a().e(aVar);
            }
            aVar.o = true;
            aVar.n = false;
            k(aVar);
            this.m.remove(aVar.f);
            if (e(aVar.f) == aVar) {
                f(aVar.f);
            }
        }
    }

    protected void k(final com.chartboost.sdk.Model.a aVar) {
        aj ajVarL = l(aVar);
        if (aVar.n) {
            ajVarL.a("cached", "1");
        } else {
            ajVarL.a("cached", "0");
        }
        String strOptString = aVar.w().optString("ad_id");
        if (!strOptString.isEmpty()) {
            ajVarL.a("ad_id", strOptString);
        }
        ajVarL.a("location", aVar.f);
        ajVarL.a(new aj.a() { // from class: com.chartboost.sdk.d.3
            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, aj ajVar) {
                if (i.s && !d.this.d(aVar.f)) {
                    d.this.b(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, aj ajVar, CBError cBError) {
            }
        });
        this.i.a(e(), aVar.f, aVar.p());
    }

    protected final boolean m(com.chartboost.sdk.Model.a aVar) {
        return TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - aVar.c.getTime()) >= 86400;
    }

    protected void a(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject) {
        if (jSONObject == null) {
            CBLogging.b(aVar.e, "Response is null");
            a(aVar, CBError.CBImpressionError.INVALID_RESPONSE);
        } else if (jSONObject.optInt("status") == 404) {
            CBLogging.b(aVar.e, "Invalid status code" + jSONObject.optInt("status"));
            a(aVar, CBError.CBImpressionError.NO_AD_FOUND);
        } else if (jSONObject.optInt("status") != 200) {
            CBLogging.b(aVar.e, "Invalid status code" + jSONObject.optInt("status"));
            a(aVar, CBError.CBImpressionError.INVALID_RESPONSE);
        } else {
            aVar.a(jSONObject, this.c.d);
        }
    }

    protected final void a(aj ajVar, final com.chartboost.sdk.Model.a aVar) {
        aVar.z = true;
        ajVar.a(new aj.a() { // from class: com.chartboost.sdk.d.4
            @Override // com.chartboost.sdk.impl.aj.a
            public void a(final JSONObject jSONObject, aj ajVar2) {
                h.a(new Runnable() { // from class: com.chartboost.sdk.d.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (jSONObject != null && jSONObject.optInt("status") != 200) {
                                CBLogging.b(aVar.e, "Invalid status code" + jSONObject.optInt("status"));
                                d.this.a(aVar, CBError.CBImpressionError.NO_AD_FOUND);
                                d.this.i.a(d.this.e(), aVar.f, "Status Code: " + jSONObject.optInt("status"), CBError.CBImpressionError.NO_AD_FOUND);
                            } else {
                                if (jSONObject != null) {
                                    aVar.z = false;
                                    if (jSONObject.optString("type").equals(TapjoyConstants.TJC_PLUGIN_NATIVE)) {
                                        d.this.a(aVar, com.chartboost.sdk.Model.a.b.NATIVE);
                                    } else {
                                        d.this.a(aVar, com.chartboost.sdk.Model.a.b.WEB);
                                    }
                                    d.this.a(aVar, jSONObject);
                                    return;
                                }
                                d.this.i.a(d.this.e(), aVar.f, "No response body", CBError.CBImpressionError.INVALID_RESPONSE);
                                d.this.a(aVar, CBError.CBImpressionError.INVALID_RESPONSE);
                            }
                        } catch (Exception e) {
                            com.chartboost.sdk.Tracking.a.a(d.class, "sendRequest onSuccess", e);
                            d.this.a(aVar, CBError.CBImpressionError.INVALID_RESPONSE);
                        }
                    }
                });
            }

            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, final aj ajVar2, final CBError cBError) {
                h.a(new Runnable() { // from class: com.chartboost.sdk.d.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            aVar.z = false;
                            Object[] objArr = new Object[3];
                            objArr[0] = ajVar2.d();
                            objArr[1] = cBError.a().name();
                            objArr[2] = cBError.b() != null ? cBError.b() : "";
                            CBLogging.d("network failure", String.format("request %s failed with error %s: %s", objArr));
                            d.this.a(aVar, cBError.c());
                        } catch (Exception e) {
                            com.chartboost.sdk.Tracking.a.a(d.class, "sendRequest callback onFailure Runnable.run", e);
                        }
                    }
                });
            }
        });
    }

    protected com.chartboost.sdk.Model.a c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.p.get(str);
    }

    protected void n(com.chartboost.sdk.Model.a aVar) {
        if (aVar != null) {
            this.p.remove(aVar.f);
        }
    }

    protected void o(com.chartboost.sdk.Model.a aVar) {
        if (aVar != null) {
            aVar.l = System.currentTimeMillis();
            this.p.put(aVar.f, aVar);
        }
    }

    public boolean d(String str) {
        return e(str) != null;
    }

    protected com.chartboost.sdk.Model.a e(String str) {
        com.chartboost.sdk.Model.a aVar;
        com.chartboost.sdk.Model.a aVar2 = this.q.get(str);
        if (aVar2 == null || m(aVar2)) {
            if (this.m.isEmpty() || !this.m.containsKey(str) || (aVar = this.m.get(str)) == null || m(aVar)) {
                return null;
            }
            return aVar;
        }
        return aVar2;
    }

    protected void f(String str) {
        CBLogging.a(getClass().getSimpleName(), "##### Removing impression-> " + e() + " at location" + str);
        this.q.remove(str);
    }

    protected void p(com.chartboost.sdk.Model.a aVar) {
        CBLogging.a(getClass().getSimpleName(), "##### Adding aimpression-> " + e() + " at location" + aVar.f);
        CBLogging.a(getClass().getSimpleName(), "##### Impression should cache:" + aVar.n);
        this.q.put(aVar.f, aVar);
    }

    protected final a a() {
        if (this.s == null) {
            this.s = b();
        }
        return this.s;
    }

    public void c() {
        if (this.m != null && !this.m.isEmpty()) {
            CBLogging.a(getClass().getSimpleName(), "###### Invalidate Cached Impression for webview");
            for (com.chartboost.sdk.Model.a aVar : this.m.values()) {
                o(aVar);
                this.m.remove(aVar.f);
                a(aVar, aVar.w());
            }
        }
        if (this.q != null && !this.q.isEmpty()) {
            for (com.chartboost.sdk.Model.a aVar2 : this.q.values()) {
                o(aVar2);
                a(aVar2, aVar2.w());
                this.q.remove(aVar2.f);
            }
        }
        if (this.r != null && !this.r.isEmpty()) {
            for (com.chartboost.sdk.Model.a aVar3 : this.r.values()) {
                o(aVar3);
                a(aVar3, aVar3.w());
                this.r.remove(aVar3.f);
            }
        }
    }

    public Context d() {
        return this.f.k();
    }

    public void a(com.chartboost.sdk.Model.a aVar, String str) {
    }

    public com.chartboost.sdk.Model.a.b f() {
        return this.n;
    }

    public void a(com.chartboost.sdk.Model.a aVar, com.chartboost.sdk.Model.a.b bVar) {
        if (aVar != null) {
            aVar.b = bVar;
        }
        this.n = bVar;
    }

    public void g() {
        CBLogging.a(getClass(), "Invalidating any pending impressions for " + getClass());
        long millis = TimeUnit.MINUTES.toMillis(this.g.get().x);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.p != null && !this.p.isEmpty()) {
            Iterator<Map.Entry<String, com.chartboost.sdk.Model.a>> it = this.p.entrySet().iterator();
            while (it.hasNext()) {
                com.chartboost.sdk.Model.a value = it.next().getValue();
                if (jCurrentTimeMillis - value.l > millis) {
                    value.a(CBError.CBImpressionError.PENDING_IMPRESSION_ERROR);
                    CBLogging.a(this, "Expired pending impression in cache, removing the impression for " + getClass());
                    this.i.a(e(), value.f, value.p(), "Pending impression still in cache, so removing them", false);
                    it.remove();
                }
            }
        }
    }
}
