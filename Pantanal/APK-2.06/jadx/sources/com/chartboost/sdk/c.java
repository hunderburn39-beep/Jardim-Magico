package com.chartboost.sdk;

import android.app.Activity;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.chartboost.sdk.impl.al;
import com.chartboost.sdk.impl.bk;
import com.chartboost.sdk.impl.bo;
import com.chartboost.sdk.impl.l;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {
    final ak a;
    final h b;
    final com.chartboost.sdk.Tracking.a c;
    public final com.chartboost.sdk.Model.a.d d = new com.chartboost.sdk.Model.a.d() { // from class: com.chartboost.sdk.c.1
        @Override // com.chartboost.sdk.Model.a.d
        public void a(com.chartboost.sdk.Model.a aVar) {
            boolean z;
            synchronized (c.this) {
                z = aVar.n;
            }
            if (aVar.d == com.chartboost.sdk.Model.a.e.LOADING) {
                aVar.d = com.chartboost.sdk.Model.a.e.LOADED;
                if (aVar.b == com.chartboost.sdk.Model.a.b.WEB) {
                    aVar.q().g(aVar);
                    aVar.q().n(aVar);
                    return;
                } else if (z) {
                    aVar.q().a(aVar);
                } else {
                    aVar.q().p(aVar);
                }
            }
            if (!z || aVar.d == com.chartboost.sdk.Model.a.e.DISPLAYED || aVar.m) {
                aVar.q().g(aVar);
            }
            aVar.q().n(aVar);
        }

        @Override // com.chartboost.sdk.Model.a.d
        public void b(com.chartboost.sdk.Model.a aVar) {
            e eVarM;
            if (aVar.d == com.chartboost.sdk.Model.a.e.DISPLAYED) {
                e eVarM2 = c.this.b.m();
                if (eVarM2 != null) {
                    eVarM2.b(aVar);
                }
            } else if (aVar.b == com.chartboost.sdk.Model.a.b.WEB && aVar.d == com.chartboost.sdk.Model.a.e.LOADED && (eVarM = c.this.b.m()) != null) {
                eVarM.d(aVar);
            }
            if (aVar.y()) {
                c.this.c.c(aVar.q().e(), aVar.f, aVar.p());
            } else {
                c.this.c.d(aVar.q().e(), aVar.f, aVar.p());
            }
        }

        @Override // com.chartboost.sdk.Model.a.d
        public void a(com.chartboost.sdk.Model.a aVar, String str, JSONObject jSONObject) {
            f fVar;
            e eVarM;
            aVar.q().a().a(aVar);
            if (aVar.a() && aVar.d == com.chartboost.sdk.Model.a.e.DISPLAYED && (eVarM = c.this.b.m()) != null) {
                eVarM.b(aVar);
            }
            if (!com.chartboost.sdk.impl.g.a().a((CharSequence) str)) {
                JSONObject jSONObjectW = aVar.w();
                aj ajVarD = c.this.d();
                ajVarD.a("ad_id", jSONObjectW);
                ajVarD.a("to", jSONObjectW);
                ajVarD.a("cgn", jSONObjectW);
                ajVarD.a("creative", jSONObjectW);
                if (aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO || aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                    if (aVar.b == com.chartboost.sdk.Model.a.b.NATIVE && aVar.l() != null) {
                        fVar = (l) aVar.x();
                    } else {
                        fVar = (aVar.b != com.chartboost.sdk.Model.a.b.WEB || aVar.l() == null) ? null : (bo) aVar.x();
                    }
                    if (fVar != null) {
                        float fK = fVar.k();
                        float fJ = fVar.j();
                        CBLogging.a(aVar.q().getClass().getSimpleName(), String.format(Locale.US, "TotalDuration: %f PlaybackTime: %f", Float.valueOf(fJ), Float.valueOf(fK)));
                        ajVarD.a("total_time", Float.valueOf(fJ / 1000.0f));
                        if (fK <= BitmapDescriptorFactory.HUE_RED) {
                            ajVarD.a("playback_time", Float.valueOf(fJ / 1000.0f));
                        } else {
                            ajVarD.a("playback_time", Float.valueOf(fK / 1000.0f));
                        }
                    }
                }
                if (jSONObject != null) {
                    ajVarD.a("cgn", jSONObject);
                    ajVarD.a("creative", jSONObject);
                    ajVarD.a("type", jSONObject);
                    ajVarD.a("more_type", jSONObject);
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("click_coordinates");
                    if (jSONObjectOptJSONObject != null) {
                        ajVarD.a("click_coordinates", (Object) jSONObjectOptJSONObject);
                    }
                }
                ajVarD.a("location", aVar.f);
                if (aVar.d()) {
                    ajVarD.a("retarget_reinstall", Boolean.valueOf(aVar.e()));
                }
                aVar.v = ajVarD;
                c.this.a(aVar, str, (a) null);
            } else {
                c.this.a().a(aVar, false, str, CBError.CBClickError.URI_INVALID, null);
            }
            c.this.c.b(aVar.q().e(), aVar.f, aVar.p());
        }

        @Override // com.chartboost.sdk.Model.a.d
        public void a(com.chartboost.sdk.Model.a aVar, CBError.CBImpressionError cBImpressionError) {
            d dVarQ = aVar.q();
            c.this.c.a(dVarQ.e(), aVar.f, aVar.p(), cBImpressionError);
            dVarQ.a(aVar, cBImpressionError);
        }

        @Override // com.chartboost.sdk.Model.a.d
        public void c(com.chartboost.sdk.Model.a aVar) {
            aVar.t = true;
            if (aVar.e == com.chartboost.sdk.Model.a.EnumC0011a.REWARDED_VIDEO && i.c != null) {
                i.c.didCompleteRewardedVideo(aVar.f, aVar.h);
            }
            c.a(aVar, c.this.a, c.this.c);
        }

        @Override // com.chartboost.sdk.Model.a.d
        public void d(com.chartboost.sdk.Model.a aVar) {
            aVar.u = true;
        }
    };
    private final al e;

    public interface a {
        void a();
    }

    public c(ak akVar, h hVar, com.chartboost.sdk.Tracking.a aVar, al alVar) {
        this.a = akVar;
        this.b = hVar;
        this.c = aVar;
        this.e = alVar;
    }

    public al.a a() {
        return this.e.a();
    }

    public final void a(com.chartboost.sdk.Model.a aVar, String str, a aVar2) {
        this.e.a(aVar, str, this.b.j(), aVar2);
    }

    public static class b implements al.a {
        @Override // com.chartboost.sdk.impl.al.a
        public void a(com.chartboost.sdk.Model.a aVar, boolean z, String str, CBError.CBClickError cBClickError, a aVar2) {
            if (aVar != null) {
                aVar.w = false;
                if (aVar.a()) {
                    aVar.d = com.chartboost.sdk.Model.a.e.DISMISSING;
                }
            }
            if (!z) {
                if (i.c != null) {
                    i.c.didFailToRecordClick(str, cBClickError);
                }
            } else if (aVar != null && aVar.v != null) {
                aVar.v.j();
            } else if (aVar2 != null) {
                aVar2.a();
            }
        }
    }

    protected boolean b() {
        com.chartboost.sdk.Model.a aVarC = c();
        if (aVarC == null) {
            return false;
        }
        aVarC.y = true;
        this.d.b(aVarC);
        return true;
    }

    static synchronized void a(com.chartboost.sdk.Model.a aVar, ak akVar, com.chartboost.sdk.Tracking.a aVar2) {
        aj ajVar = new aj("/api/video-complete", akVar);
        ajVar.a("location", aVar.f);
        ajVar.a("reward", Integer.valueOf(aVar.h));
        ajVar.a("currency-name", aVar.i);
        ajVar.a("ad_id", aVar.p());
        ajVar.a("force_close", (Object) false);
        f fVar = null;
        if (aVar.b == com.chartboost.sdk.Model.a.b.NATIVE && aVar.l() != null) {
            fVar = (l) aVar.x();
        } else if (aVar.b == com.chartboost.sdk.Model.a.b.WEB && aVar.l() != null) {
            fVar = (bo) aVar.x();
        }
        if (fVar != null) {
            float fK = fVar.k();
            float fJ = fVar.j();
            CBLogging.a(aVar.q().getClass().getSimpleName(), String.format(Locale.US, "TotalDuration: %f PlaybackTime: %f", Float.valueOf(fJ), Float.valueOf(fK)));
            ajVar.a("total_time", Float.valueOf(fJ / 1000.0f));
            if (fK <= BitmapDescriptorFactory.HUE_RED) {
                ajVar.a("playback_time", Float.valueOf(fJ / 1000.0f));
            } else {
                ajVar.a("playback_time", Float.valueOf(fK / 1000.0f));
            }
        }
        ajVar.j();
        aVar2.b(aVar.q().e(), aVar.p());
    }

    protected com.chartboost.sdk.Model.a c() {
        e eVarM = this.b.m();
        bk bkVarD = eVarM == null ? null : eVarM.d();
        if (bkVarD == null) {
            return null;
        }
        if (bkVarD.i() || bkVarD.g()) {
            return bkVarD.h();
        }
        return null;
    }

    public aj d() {
        return new aj("/api/click", this.a);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final boolean a(Activity activity, com.chartboost.sdk.Model.a aVar) {
        if (aVar != null) {
            switch (aVar.d) {
                case LOADING:
                    if (aVar.q) {
                        this.b.a(aVar);
                    }
                    break;
                case CACHED:
                case LOADED:
                    this.b.a(aVar);
                    break;
                case DISPLAYED:
                    if (!aVar.h()) {
                        if (i.d != null && i.d.doesWrapperUseCustomBackgroundingBehavior() && !(activity instanceof CBImpressionActivity)) {
                            return false;
                        }
                        e eVarM = this.b.m();
                        if (eVarM != null) {
                            CBLogging.b("CBImpressionDelegationManager", "Error onActivityStart " + aVar.d.name());
                            eVarM.d(aVar);
                        }
                    }
                    break;
            }
        }
        return true;
    }
}
