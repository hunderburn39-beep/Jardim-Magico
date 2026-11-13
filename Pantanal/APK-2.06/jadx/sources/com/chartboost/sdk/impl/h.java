package com.chartboost.sdk.impl;

import android.content.SharedPreferences;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class h extends com.chartboost.sdk.d {
    final e o;

    public h(b bVar, d dVar, com.chartboost.sdk.c cVar, af afVar, e eVar, ai aiVar, ak akVar, com.chartboost.sdk.h hVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, SharedPreferences sharedPreferences, com.chartboost.sdk.Tracking.a aVar, com.chartboost.sdk.e eVar2, am amVar) {
        super(bVar, dVar, cVar, afVar, aiVar, akVar, hVar, atomicReference, sharedPreferences, aVar, eVar2, amVar);
        this.o = eVar;
    }

    protected boolean b(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject) {
        return jSONObject != null && jSONObject.optString("media-type").equals("video");
    }

    @Override // com.chartboost.sdk.d
    protected void g(com.chartboost.sdk.Model.a aVar) {
        super.g(aVar);
    }

    @Override // com.chartboost.sdk.d
    protected void a(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject) {
        if (aVar.b == com.chartboost.sdk.Model.a.b.NATIVE) {
            if (b(aVar, jSONObject) && !this.a.b(jSONObject)) {
                CBLogging.b("CBInterstitial", "Video Media unavailable for the cached impression");
                a(aVar, CBError.CBImpressionError.VIDEO_UNAVAILABLE);
                return;
            } else {
                super.a(aVar, jSONObject);
                return;
            }
        }
        aVar.a(jSONObject);
        JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(jSONObject, "webview");
        if (jSONObjectA != null) {
            String strOptString = jSONObjectA.optString("template");
            if (!strOptString.isEmpty()) {
                aVar.j = strOptString;
            }
            this.b.a(aVar, jSONObjectA, aVar.n ? 1 : 2);
            return;
        }
        this.i.a(e(), aVar.f, "Empty", "Response is empty", true);
        CBLogging.b("CBInterstitial", "Response got from the server is empty");
        a(aVar, CBError.CBImpressionError.INVALID_RESPONSE);
    }

    @Override // com.chartboost.sdk.d
    public void a(com.chartboost.sdk.Model.a aVar, String str) {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectW = aVar.w();
        if (jSONObjectW != null && (jSONObjectOptJSONObject = jSONObjectW.optJSONObject("webview")) != null) {
            String strOptString = jSONObjectOptJSONObject.optString("template");
            if (jSONObjectW.optBoolean("prefetch_required")) {
                this.o.a();
            }
            aVar.j = strOptString;
            aVar.k = str;
            this.m.put(aVar.f, aVar);
            if (aVar.n) {
                a().d(aVar);
                aVar.d = com.chartboost.sdk.Model.a.e.CACHED;
                n(aVar);
                if (aVar.m) {
                    a(aVar.f);
                    return;
                }
                return;
            }
            super.a(aVar, aVar.w());
        }
    }

    @Override // com.chartboost.sdk.d
    protected com.chartboost.sdk.Model.a a(String str, boolean z) {
        return new com.chartboost.sdk.Model.a(this, com.chartboost.sdk.Model.a.EnumC0011a.INTERSTITIAL, z, str, false, f());
    }

    @Override // com.chartboost.sdk.d
    protected aj e(com.chartboost.sdk.Model.a aVar) {
        com.chartboost.sdk.Model.c cVar = this.g.get();
        if (!cVar.u) {
            aVar.b = com.chartboost.sdk.Model.a.b.NATIVE;
            aj ajVar = new aj("/interstitial/get", this.e);
            ajVar.a("local-videos", this.a.c());
            ajVar.a(ac.b.HIGH);
            ajVar.a(com.chartboost.sdk.Model.b.h);
            ajVar.a("location", aVar.f);
            if (aVar.n) {
                ajVar.a("cache", "1");
                return ajVar;
            }
            return ajVar;
        }
        aVar.b = com.chartboost.sdk.Model.a.b.WEB;
        JSONObject jSONObjectB = this.a.b();
        an anVar = new an(cVar.D, this.e);
        anVar.a("cache_assets", jSONObjectB, an.a.AD);
        anVar.a(ac.b.HIGH);
        anVar.a("location", aVar.f, an.a.AD);
        if (aVar.n) {
            anVar.a("cache", true, an.a.AD);
        } else {
            anVar.a("cache", false, an.a.AD);
        }
        anVar.a(com.chartboost.sdk.Model.b.h);
        return anVar;
    }

    @Override // com.chartboost.sdk.d
    protected void i(com.chartboost.sdk.Model.a aVar) {
        if (aVar.g != com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO && aVar.b != com.chartboost.sdk.Model.a.b.WEB) {
            super.i(aVar);
        }
    }

    @Override // com.chartboost.sdk.d
    protected com.chartboost.sdk.d.a b() {
        return new com.chartboost.sdk.d.a() { // from class: com.chartboost.sdk.impl.h.1
            @Override // com.chartboost.sdk.d.a
            public void a(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didClickInterstitial(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void b(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didCloseInterstitial(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void c(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didDismissInterstitial(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void d(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didCacheInterstitial(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void a(com.chartboost.sdk.Model.a aVar, CBError.CBImpressionError cBImpressionError) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didFailToLoadInterstitial(aVar.f, cBImpressionError);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void e(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didDisplayInterstitial(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public boolean f(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    return com.chartboost.sdk.i.c.shouldDisplayInterstitial(aVar.f);
                }
                return true;
            }

            @Override // com.chartboost.sdk.d.a
            public boolean g(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    return com.chartboost.sdk.i.c.shouldRequestInterstitial(aVar.f);
                }
                return true;
            }

            @Override // com.chartboost.sdk.d.a
            public boolean h(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    return com.chartboost.sdk.i.t;
                }
                return true;
            }
        };
    }

    @Override // com.chartboost.sdk.d
    protected aj l(com.chartboost.sdk.Model.a aVar) {
        return new aj("/interstitial/show", this.e);
    }

    @Override // com.chartboost.sdk.d
    public String e() {
        return String.format("%s-%s", "interstitial", com.chartboost.sdk.b.a(this.g.get()));
    }
}
