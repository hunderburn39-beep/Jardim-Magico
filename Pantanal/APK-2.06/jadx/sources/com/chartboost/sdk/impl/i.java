package com.chartboost.sdk.impl;

import android.content.SharedPreferences;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class i extends h {
    public i(b bVar, d dVar, com.chartboost.sdk.c cVar, af afVar, e eVar, ai aiVar, ak akVar, com.chartboost.sdk.h hVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, SharedPreferences sharedPreferences, com.chartboost.sdk.Tracking.a aVar, com.chartboost.sdk.e eVar2, am amVar) {
        super(bVar, dVar, cVar, afVar, eVar, aiVar, akVar, hVar, atomicReference, sharedPreferences, aVar, eVar2, amVar);
    }

    @Override // com.chartboost.sdk.impl.h
    protected boolean b(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject) {
        return true;
    }

    @Override // com.chartboost.sdk.impl.h, com.chartboost.sdk.d
    protected com.chartboost.sdk.Model.a a(String str, boolean z) {
        return new com.chartboost.sdk.Model.a(this, com.chartboost.sdk.Model.a.EnumC0011a.REWARDED_VIDEO, z, str, false, f());
    }

    @Override // com.chartboost.sdk.impl.h, com.chartboost.sdk.d
    protected aj e(com.chartboost.sdk.Model.a aVar) {
        com.chartboost.sdk.Model.c cVar = this.g.get();
        if (!cVar.u) {
            aVar.b = com.chartboost.sdk.Model.a.b.NATIVE;
            JSONArray jSONArrayC = this.a.c();
            aj ajVar = new aj("/reward/get", this.e);
            ajVar.a("local-videos", jSONArrayC);
            ajVar.a(ac.b.HIGH);
            ajVar.a("location", aVar.f);
            if (aVar.n) {
                ajVar.a("cache", "1");
            }
            ajVar.a(com.chartboost.sdk.Model.b.b);
            return ajVar;
        }
        aVar.b = com.chartboost.sdk.Model.a.b.WEB;
        JSONObject jSONObjectB = this.a.b();
        an anVar = new an(cVar.F, this.e);
        anVar.a("cache_assets", jSONObjectB, an.a.AD);
        anVar.a(ac.b.HIGH);
        anVar.a(com.chartboost.sdk.Model.b.h);
        anVar.a("location", aVar.f, an.a.AD);
        if (aVar.n) {
            anVar.a("cache", true, an.a.AD);
        } else {
            anVar.a("cache", false, an.a.AD);
        }
        return anVar;
    }

    @Override // com.chartboost.sdk.impl.h, com.chartboost.sdk.d
    public aj l(com.chartboost.sdk.Model.a aVar) {
        aj ajVarL = super.l(aVar);
        ajVarL.a("/reward/show");
        return ajVarL;
    }

    @Override // com.chartboost.sdk.impl.h, com.chartboost.sdk.d
    protected void i(com.chartboost.sdk.Model.a aVar) {
    }

    @Override // com.chartboost.sdk.d
    public void h(final com.chartboost.sdk.Model.a aVar) {
        final JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(aVar.w(), "ux", "pre-popup");
        if (jSONObjectA != null && !jSONObjectA.optString("title").isEmpty() && !jSONObjectA.optString("text").isEmpty() && !jSONObjectA.optString("confirm").isEmpty() && !jSONObjectA.optString("cancel").isEmpty() && d() != null) {
            this.l.post(new Runnable() { // from class: com.chartboost.sdk.impl.i.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bg.a aVar2 = (bg.a) com.chartboost.sdk.g.a().a(new bg.a());
                        aVar2.a(jSONObjectA.optString("title")).b(jSONObjectA.optString("text")).d(jSONObjectA.optString("confirm")).c(jSONObjectA.optString("cancel"));
                        aVar2.a(i.this.d(), new bg.b() { // from class: com.chartboost.sdk.impl.i.1.1
                            @Override // com.chartboost.sdk.impl.bg.b
                            public void a(bg bgVar) {
                                i.this.a(aVar, CBError.CBImpressionError.USER_CANCELLATION);
                            }

                            @Override // com.chartboost.sdk.impl.bg.b
                            public void a(bg bgVar, int i) {
                                if (i == 1) {
                                    i.this.q(aVar);
                                } else {
                                    i.this.a(aVar, CBError.CBImpressionError.USER_CANCELLATION);
                                }
                            }
                        });
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(i.class, "preShow Runnable.run", e);
                    }
                }
            });
        } else {
            q(aVar);
        }
    }

    void q(com.chartboost.sdk.Model.a aVar) {
        super.h(aVar);
    }

    protected void r(com.chartboost.sdk.Model.a aVar) {
        final JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(aVar.w(), "ux", "post-popup");
        if (jSONObjectA != null && !jSONObjectA.optString("title").isEmpty() && !jSONObjectA.optString("text").isEmpty() && !jSONObjectA.optString("confirm").isEmpty() && d() != null && aVar.t) {
            this.l.post(new Runnable() { // from class: com.chartboost.sdk.impl.i.2
                @Override // java.lang.Runnable
                public void run() {
                    bg.a aVar2 = (bg.a) com.chartboost.sdk.g.a().a(new bg.a());
                    aVar2.a(jSONObjectA.optString("title")).b(jSONObjectA.optString("text")).c(jSONObjectA.optString("confirm"));
                    aVar2.a(i.this.d(), new bg.b() { // from class: com.chartboost.sdk.impl.i.2.1
                        @Override // com.chartboost.sdk.impl.bg.b
                        public void a(bg bgVar, int i) {
                            CBLogging.c("CBRewardedVideo", "post-popup dismissed");
                        }
                    });
                }
            });
        }
    }

    @Override // com.chartboost.sdk.impl.h, com.chartboost.sdk.d
    public com.chartboost.sdk.d.a b() {
        return new com.chartboost.sdk.d.a() { // from class: com.chartboost.sdk.impl.i.3
            @Override // com.chartboost.sdk.d.a
            public void a(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didClickRewardedVideo(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void b(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didCloseRewardedVideo(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void c(com.chartboost.sdk.Model.a aVar) {
                i.this.r(aVar);
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didDismissRewardedVideo(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void d(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didCacheRewardedVideo(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void a(com.chartboost.sdk.Model.a aVar, CBError.CBImpressionError cBImpressionError) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didFailToLoadRewardedVideo(aVar.f, cBImpressionError);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public void e(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    com.chartboost.sdk.i.c.didDisplayRewardedVideo(aVar.f);
                }
            }

            @Override // com.chartboost.sdk.d.a
            public boolean f(com.chartboost.sdk.Model.a aVar) {
                if (com.chartboost.sdk.i.c != null) {
                    return com.chartboost.sdk.i.c.shouldDisplayRewardedVideo(aVar.f);
                }
                return true;
            }

            @Override // com.chartboost.sdk.d.a
            public boolean g(com.chartboost.sdk.Model.a aVar) {
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

    @Override // com.chartboost.sdk.impl.h, com.chartboost.sdk.d
    public String e() {
        return String.format("%s-%s", "rewarded-video", com.chartboost.sdk.b.a(this.g.get()));
    }
}
