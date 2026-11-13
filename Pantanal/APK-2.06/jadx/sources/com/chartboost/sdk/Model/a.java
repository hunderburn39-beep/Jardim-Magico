package com.chartboost.sdk.Model;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.f;
import com.chartboost.sdk.g;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.al;
import com.chartboost.sdk.impl.bk;
import com.chartboost.sdk.impl.bo;
import com.chartboost.sdk.impl.k;
import com.chartboost.sdk.impl.l;
import com.chartboost.sdk.impl.z;
import com.tapjoy.TapjoyConstants;
import java.util.Date;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private f D;
    private d E;
    private Runnable F;
    public final com.chartboost.sdk.d a;
    public b b;
    public final EnumC0011a e;
    public String f;
    public String j;
    public String k;
    public boolean n;
    public bk p;
    public boolean q;
    public aj v;
    public boolean y;
    private Boolean C = null;
    public int h = 0;
    public String i = "";
    public long l = 0;
    public boolean m = false;
    public boolean r = false;
    public boolean s = false;
    public boolean t = false;
    public boolean u = false;
    public boolean x = false;
    public boolean z = false;
    public e d = e.LOADING;
    public final Date c = new Date();
    public boolean o = false;
    public boolean w = false;
    private JSONObject A = (JSONObject) g.a().a(new JSONObject());
    public c g = c.NONE;
    private boolean B = true;

    /* JADX INFO: renamed from: com.chartboost.sdk.Model.a$a, reason: collision with other inner class name */
    public enum EnumC0011a {
        INTERSTITIAL,
        MORE_APPS,
        REWARDED_VIDEO,
        NONE
    }

    public enum b {
        NATIVE,
        WEB
    }

    public enum c {
        INTERSTITIAL,
        INTERSTITIAL_VIDEO,
        INTERSTITIAL_REWARD_VIDEO,
        NONE
    }

    public interface d {
        void a(a aVar);

        void a(a aVar, CBError.CBImpressionError cBImpressionError);

        void a(a aVar, String str, JSONObject jSONObject);

        void b(a aVar);

        void c(a aVar);

        void d(a aVar);
    }

    public enum e {
        LOADING,
        LOADED,
        DISPLAYED,
        CACHED,
        DISMISSING,
        NONE
    }

    public a(com.chartboost.sdk.d dVar, EnumC0011a enumC0011a, boolean z, String str, boolean z2, b bVar) {
        this.b = b.NATIVE;
        this.y = false;
        this.a = dVar;
        this.n = z;
        this.y = true;
        this.e = enumC0011a;
        this.q = z2;
        this.f = str;
        this.b = bVar;
        if (this.f == null) {
            this.f = CBLocation.LOCATION_DEFAULT;
        }
    }

    public void a(JSONObject jSONObject, d dVar) {
        JSONArray jSONArrayOptJSONArray;
        this.A = jSONObject;
        this.d = e.LOADING;
        this.E = dVar;
        if (jSONObject.optString("type").equals(TapjoyConstants.TJC_PLUGIN_NATIVE)) {
            this.b = b.NATIVE;
        } else {
            this.b = b.WEB;
        }
        if (this.b == b.NATIVE) {
            switch (this.e) {
                case INTERSTITIAL:
                    if (jSONObject.optString("media-type").equals("video")) {
                        this.g = c.INTERSTITIAL_VIDEO;
                        this.D = new l(this);
                        this.B = false;
                    } else {
                        this.g = c.INTERSTITIAL;
                        this.D = new k(this);
                    }
                    break;
                case REWARDED_VIDEO:
                    this.g = c.INTERSTITIAL_REWARD_VIDEO;
                    this.D = new l(this);
                    this.B = false;
                    this.h = jSONObject.optInt("reward");
                    this.i = jSONObject.optString("currency-name");
                    break;
                case MORE_APPS:
                    this.D = new z(this);
                    this.B = false;
                    break;
            }
        } else {
            switch (this.e) {
                case INTERSTITIAL:
                    String strOptString = jSONObject.optString("media-type");
                    if (strOptString.equals("video")) {
                        this.g = c.INTERSTITIAL_VIDEO;
                        this.B = false;
                    } else if (strOptString.equals(TMXConstants.TAG_IMAGE)) {
                        this.g = c.INTERSTITIAL;
                    } else {
                        CBLogging.b("CBImpression", "Unknown media type in the response, so have issues determining which ad type to create the view for.");
                        a(CBError.CBImpressionError.ERROR_CREATING_VIEW);
                    }
                    break;
                case REWARDED_VIDEO:
                    this.g = c.INTERSTITIAL_REWARD_VIDEO;
                    this.B = false;
                    this.h = jSONObject.optInt("reward");
                    if (this.h <= 0) {
                        try {
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("webview");
                            if (jSONObjectOptJSONObject != null && (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("elements")) != null) {
                                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                                    JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray.optJSONObject(i);
                                    if (jSONObjectOptJSONObject2 != null) {
                                        String strOptString2 = jSONObjectOptJSONObject2.optString("param");
                                        if (strOptString2.contains("reward_amount")) {
                                            this.h = jSONObjectOptJSONObject2.optInt(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE);
                                        }
                                        if (strOptString2.contains("reward_currency")) {
                                            this.i = jSONObjectOptJSONObject2.optString(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            com.chartboost.sdk.Tracking.a.a(getClass(), "prepare webview rewarded video", e2);
                        }
                    }
                    break;
                case MORE_APPS:
                    this.B = false;
                    break;
            }
            this.D = new bo(this, this.a.a, this.a.d, this.a.i, this.a.j);
        }
        this.D.a(jSONObject);
    }

    public boolean a() {
        return this.B;
    }

    public void b() {
        if (this.E != null) {
            this.y = true;
            this.E.b(this);
        }
    }

    public void c() {
        if (this.E != null) {
            this.E.a(this);
        }
    }

    public boolean a(String str, JSONObject jSONObject) {
        if (this.d != e.DISPLAYED || this.s) {
            return false;
        }
        if (str == null) {
            str = this.A.optString("link");
        }
        String strOptString = this.A.optString("deep-link");
        if (!strOptString.isEmpty()) {
            try {
                if (al.a(strOptString)) {
                    try {
                        this.C = Boolean.TRUE;
                        str = strOptString;
                    } catch (Exception e2) {
                        str = strOptString;
                        e = e2;
                        com.chartboost.sdk.Tracking.a.a(getClass(), "onClick", e);
                    }
                } else {
                    this.C = Boolean.FALSE;
                }
            } catch (Exception e3) {
                e = e3;
            }
        }
        if (this.w) {
            return false;
        }
        this.w = true;
        this.y = false;
        this.E.a(this, str, jSONObject);
        return true;
    }

    public boolean d() {
        return this.C != null;
    }

    public boolean e() {
        return this.C.booleanValue();
    }

    public void a(CBError.CBImpressionError cBImpressionError) {
        if (this.E != null) {
            this.E.a(this, cBImpressionError);
        }
    }

    public void f() {
        if (this.E != null) {
            this.E.c(this);
        }
    }

    public void g() {
        if (this.E != null) {
            this.E.d(this);
        }
    }

    public boolean h() {
        if (this.D != null) {
            this.D.b();
            if (this.D.e() != null) {
                return true;
            }
        } else {
            CBLogging.b("CBImpression", "reinitializing -- no view protocol exists!!");
        }
        CBLogging.e("CBImpression", "reinitializing -- view not yet created");
        return false;
    }

    public void i() {
        j();
        if (this.o) {
            if (this.D != null) {
                this.D.d();
            }
            this.D = null;
            CBLogging.e("CBImpression", "Destroying the view and view data");
        }
    }

    public void j() {
        if (this.p != null) {
            this.p.d();
            try {
                if (this.D != null && this.D.e() != null && this.D.e().getParent() != null) {
                    this.p.removeView(this.D.e());
                }
            } catch (Exception e2) {
                CBLogging.b("CBImpression", "Exception raised while cleaning up views", e2);
                com.chartboost.sdk.Tracking.a.a(getClass(), "cleanUpViews", e2);
            }
            this.p = null;
        }
        if (this.D != null) {
            this.D.f();
        }
        CBLogging.e("CBImpression", "Destroying the view");
    }

    public CBError.CBImpressionError k() {
        return this.D != null ? this.D.c() : CBError.CBImpressionError.ERROR_CREATING_VIEW;
    }

    public f.a l() {
        if (this.D != null) {
            return this.D.e();
        }
        return null;
    }

    public void m() {
        if (this.D != null && this.D.e() != null) {
            this.D.e().setVisibility(8);
        }
    }

    public void a(Runnable runnable) {
        this.F = runnable;
    }

    public void n() {
        this.s = true;
    }

    public void o() {
        if (this.F != null) {
            this.F.run();
            this.F = null;
        }
        this.s = false;
        this.r = false;
    }

    public String p() {
        return this.A.optString("ad_id");
    }

    public com.chartboost.sdk.d q() {
        return this.a;
    }

    public void r() {
        q().j(this);
    }

    public boolean s() {
        if (this.D != null) {
            return this.D.l();
        }
        return false;
    }

    public void t() {
        this.w = false;
        if (this.D != null && this.x) {
            this.x = false;
            this.D.m();
        }
    }

    public void u() {
        this.w = false;
    }

    public void v() {
        if (this.D != null && !this.x) {
            this.x = true;
            this.D.n();
        }
    }

    public JSONObject w() {
        return this.A;
    }

    public void a(JSONObject jSONObject) {
        this.A = jSONObject == null ? (JSONObject) g.a().a(new JSONObject()) : jSONObject;
    }

    public f x() {
        return this.D;
    }

    public boolean y() {
        return this.y;
    }
}
