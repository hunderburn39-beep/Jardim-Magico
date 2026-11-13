package com.chartboost.sdk.InPlay;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.c;
import com.chartboost.sdk.i;
import com.chartboost.sdk.impl.ab;
import com.chartboost.sdk.impl.ac;
import com.chartboost.sdk.impl.ad;
import com.chartboost.sdk.impl.af;
import com.chartboost.sdk.impl.ag;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.chartboost.sdk.impl.al;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    final c a;
    final af b;
    final AtomicReference<com.chartboost.sdk.Model.c> c;
    private final ak f;
    volatile boolean e = false;
    private final ArrayList<CBInPlay> g = new ArrayList<>();
    final LinkedHashMap<String, Bitmap> d = new LinkedHashMap<>(4);

    public a(c cVar, af afVar, ak akVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference) {
        this.a = cVar;
        this.b = afVar;
        this.f = akVar;
        this.c = atomicReference;
    }

    public synchronized void a(String str) {
        if (!a() && !this.e) {
            a(str, true);
        }
    }

    private synchronized boolean a() {
        return this.g.size() == 4;
    }

    public synchronized boolean b(String str) {
        return this.g.size() > 0;
    }

    public synchronized CBInPlay c(String str) {
        CBInPlay cBInPlay;
        cBInPlay = null;
        if (this.g.size() > 0) {
            cBInPlay = this.g.get(0);
            this.g.remove(0);
        }
        if (!a() && !this.e) {
            a(str, true);
        }
        if (cBInPlay == null && i.c != null) {
            i.c.didFailToLoadInPlay(str, CBError.CBImpressionError.NO_AD_FOUND);
        }
        return cBInPlay;
    }

    private void a(final String str, final boolean z) {
        this.e = true;
        aj ajVar = new aj("/inplay/get", this.f);
        ajVar.a("raw", (Object) true);
        ajVar.a("cache", (Object) true);
        ajVar.a(ac.b.HIGH);
        ajVar.a("location", str);
        ajVar.a(com.chartboost.sdk.Model.b.e);
        ajVar.a(new aj.a() { // from class: com.chartboost.sdk.InPlay.a.1
            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, aj ajVar2) {
                a.this.e = false;
                if (jSONObject != null) {
                    CBInPlay cBInPlay = new CBInPlay(a.this);
                    cBInPlay.a(jSONObject);
                    cBInPlay.b(jSONObject.optString("name"));
                    if (!TextUtils.isEmpty(str)) {
                        cBInPlay.a(str);
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("icons");
                    if (jSONObjectOptJSONObject != null) {
                        String strOptString = jSONObjectOptJSONObject.optString("lg");
                        if (!strOptString.isEmpty()) {
                            if (a.this.d.get(strOptString) == null) {
                                b bVar = new b();
                                C0009a c0009a = new C0009a();
                                bVar.c = cBInPlay;
                                bVar.b = strOptString;
                                bVar.a = z;
                                c0009a.a = str;
                                a.this.b.a(strOptString, bVar, c0009a);
                                return;
                            }
                            a.this.a(cBInPlay, strOptString, true);
                        }
                    }
                }
            }

            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, aj ajVar2, CBError cBError) {
                CBLogging.b("CBInPlayManager", "InPlay cache call failed" + cBError);
                a.this.e = false;
                if (i.c != null) {
                    i.c.didFailToLoadInPlay(str, cBError != null ? cBError.c() : null);
                }
            }
        });
    }

    private class b implements ag<Bitmap> {
        protected boolean a;
        protected String b;
        protected CBInPlay c;

        private b() {
        }

        @Override // com.chartboost.sdk.impl.ag
        public void a(Bitmap bitmap) {
            synchronized (a.this) {
                a.this.d.put(this.b, bitmap);
                a.this.a(this.c, this.b, this.a);
            }
        }
    }

    /* JADX INFO: renamed from: com.chartboost.sdk.InPlay.a$a, reason: collision with other inner class name */
    private class C0009a implements ab {
        protected String a;

        private C0009a() {
        }

        @Override // com.chartboost.sdk.impl.ab
        public void a(ad adVar) {
            CBLogging.b("CBInPlayManager", "Bitmap download failed " + adVar.a());
            if (i.c != null) {
                i.c.didFailToLoadInPlay(this.a, CBError.CBImpressionError.NETWORK_FAILURE);
            }
        }
    }

    synchronized void a(CBInPlay cBInPlay, String str, boolean z) {
        cBInPlay.a(this.d.get(str));
        this.g.add(cBInPlay);
        com.chartboost.sdk.a aVar = i.c;
        if (aVar != null && z) {
            aVar.didCacheInPlay(cBInPlay.getLocation());
        }
        if (!a() && !this.e) {
            a(cBInPlay.getLocation(), false);
        }
    }

    protected void a(CBInPlay cBInPlay) {
        JSONObject jSONObjectA = cBInPlay.a();
        aj ajVar = new aj("/inplay/show", this.f);
        ajVar.a("inplay-dictionary", (Object) jSONObjectA);
        ajVar.a("location", cBInPlay.getLocation());
        ajVar.j();
    }

    protected void b(final CBInPlay cBInPlay) {
        String str;
        final JSONObject jSONObjectA = cBInPlay.a();
        if (jSONObjectA != null) {
            String strOptString = jSONObjectA.optString("link");
            String strOptString2 = jSONObjectA.optString("deep-link");
            if (strOptString2.isEmpty()) {
                str = strOptString;
            } else {
                try {
                    if (!al.a(strOptString2)) {
                        strOptString2 = strOptString;
                    }
                    str = strOptString2;
                } catch (Exception e) {
                    CBLogging.b("CBInPlayManager", "Cannot open a url");
                    str = strOptString;
                }
            }
        } else {
            str = null;
        }
        final c cVar = this.a;
        c.a aVar = new c.a() { // from class: com.chartboost.sdk.InPlay.a.2
            @Override // com.chartboost.sdk.c.a
            public void a() {
                aj ajVarD = cVar.d();
                ajVarD.a("location", cBInPlay.getLocation());
                ajVarD.a("to", jSONObjectA);
                ajVarD.a("cgn", jSONObjectA);
                ajVarD.a("creative", jSONObjectA);
                ajVarD.a("ad_id", jSONObjectA);
                ajVarD.a("type", jSONObjectA);
                ajVarD.a("more_type", jSONObjectA);
                ajVarD.j();
            }
        };
        if (!TextUtils.isEmpty(str)) {
            cVar.a((com.chartboost.sdk.Model.a) null, str, aVar);
        } else {
            cVar.a().a(null, false, str, CBError.CBClickError.URI_INVALID, aVar);
        }
    }
}
