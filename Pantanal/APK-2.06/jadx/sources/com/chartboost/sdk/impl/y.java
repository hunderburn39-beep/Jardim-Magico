package com.chartboost.sdk.impl;

import android.content.SharedPreferences;
import com.chartboost.sdk.Model.CBError;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class y extends com.chartboost.sdk.d {
    protected int o;
    private com.chartboost.sdk.Model.a p;
    private boolean q;
    private boolean r;

    public y(b bVar, d dVar, com.chartboost.sdk.c cVar, af afVar, ai aiVar, ak akVar, com.chartboost.sdk.h hVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, SharedPreferences sharedPreferences, com.chartboost.sdk.Tracking.a aVar, com.chartboost.sdk.e eVar, am amVar) {
        super(bVar, dVar, cVar, afVar, aiVar, akVar, hVar, atomicReference, sharedPreferences, aVar, eVar, amVar);
        this.p = null;
    }

    @Override // com.chartboost.sdk.d
    public void a(String str) {
        this.o = 0;
        h();
        super.a(str);
    }

    @Override // com.chartboost.sdk.d
    protected void a(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray;
        if (!this.q && this.r) {
            this.r = false;
            if (jSONObject != null && (jSONArrayOptJSONArray = jSONObject.optJSONArray("cells")) != null) {
                this.o = jSONArrayOptJSONArray.length();
            }
        }
        super.a(aVar, jSONObject);
    }

    @Override // com.chartboost.sdk.d
    protected com.chartboost.sdk.Model.a a(String str, boolean z) {
        return new com.chartboost.sdk.Model.a(this, com.chartboost.sdk.Model.a.EnumC0011a.MORE_APPS, z, str, false, f());
    }

    @Override // com.chartboost.sdk.d
    protected aj e(com.chartboost.sdk.Model.a aVar) {
        aj ajVar = new aj("/more/get", this.e);
        ajVar.a(ac.b.HIGH);
        ajVar.a(com.chartboost.sdk.Model.b.d);
        return ajVar;
    }

    @Override // com.chartboost.sdk.d
    protected aj l(com.chartboost.sdk.Model.a aVar) {
        aj ajVar = new aj("/more/show", this.e);
        if (aVar.f != null) {
            ajVar.a("location", aVar.f);
        }
        JSONObject jSONObjectW = aVar.w();
        if (jSONObjectW != null && !jSONObjectW.isNull("cells")) {
            ajVar.a("cells", jSONObjectW.opt("cells"));
        }
        return ajVar;
    }

    @Override // com.chartboost.sdk.d
    protected com.chartboost.sdk.Model.a e(String str) {
        return this.p;
    }

    @Override // com.chartboost.sdk.d
    protected void f(String str) {
        this.p = null;
    }

    @Override // com.chartboost.sdk.d
    protected void p(com.chartboost.sdk.Model.a aVar) {
        this.p = aVar;
    }

    @Override // com.chartboost.sdk.d
    protected com.chartboost.sdk.d.a b() {
        return new com.chartboost.sdk.d.a() { // from class: com.chartboost.sdk.impl.y.1
            @Override // com.chartboost.sdk.d.a
            public void a(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didClickMoreApps(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void b(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didCloseMoreApps(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void c(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didDismissMoreApps(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void d(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didCacheMoreApps(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void a(com.chartboost.sdk.Model.a aVar, CBError.CBImpressionError cBImpressionError) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didFailToLoadMoreApps(aVar.f, cBImpressionError);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void e(com.chartboost.sdk.Model.a aVar) {
                y.this.o = 0;
                y.this.h();
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didDisplayMoreApps(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public boolean f(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    return com.chartboost.sdk.i.c.shouldDisplayMoreApps(aVar.f);
                }
                return true;
            }

            @Override // com.chartboost.sdk.d.a
            public boolean g(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    return com.chartboost.sdk.i.c.shouldRequestMoreApps(aVar.f);
                }
                return true;
            }

            @Override // com.chartboost.sdk.d.a
            public boolean h(com.chartboost.sdk.Model.a aVar) {
                return true;
            }
        };
    }

    protected void h() {
    }

    @Override // com.chartboost.sdk.d
    public String e() {
        return "more-apps";
    }
}
