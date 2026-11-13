package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.CBUtility;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class an extends aj {
    private final JSONObject c;
    private final JSONObject d;
    private final JSONObject e;
    private final JSONObject f;

    public enum a {
        AD
    }

    public an(String str, ak akVar) {
        super(str, akVar);
        com.chartboost.sdk.g gVarA = com.chartboost.sdk.g.a();
        this.c = (JSONObject) gVarA.a(new JSONObject());
        this.d = (JSONObject) gVarA.a(new JSONObject());
        this.e = (JSONObject) gVarA.a(new JSONObject());
        this.f = (JSONObject) gVarA.a(new JSONObject());
    }

    @Override // com.chartboost.sdk.impl.aj
    protected void a() {
        ay ayVar = this.b.c;
        com.chartboost.sdk.Libraries.d.a(this.d, "app", ayVar.n);
        com.chartboost.sdk.Libraries.d.a(this.d, TJAdUnitConstants.String.BUNDLE, ayVar.e);
        com.chartboost.sdk.Libraries.d.a(this.d, "bundle_id", ayVar.f);
        com.chartboost.sdk.Libraries.d.a(this.d, "custom_id", com.chartboost.sdk.i.a);
        com.chartboost.sdk.Libraries.d.a(this.d, TapjoyConstants.TJC_SESSION_ID, "");
        com.chartboost.sdk.Libraries.d.a(this.d, "ui", -1);
        com.chartboost.sdk.Libraries.d.a(this.d, "test_mode", false);
        a("app", (Object) this.d);
        com.chartboost.sdk.Libraries.d.a(this.e, "carrier", com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a(TapjoyConstants.TJC_CARRIER_NAME, ayVar.p.optString("carrier-name")), com.chartboost.sdk.Libraries.d.a(TapjoyConstants.TJC_MOBILE_COUNTRY_CODE, ayVar.p.optString("mobile-country-code")), com.chartboost.sdk.Libraries.d.a(TapjoyConstants.TJC_MOBILE_NETWORK_CODE, ayVar.p.optString("mobile-network-code")), com.chartboost.sdk.Libraries.d.a("iso_country_code", ayVar.p.optString("iso-country-code")), com.chartboost.sdk.Libraries.d.a("phone_type", Integer.valueOf(ayVar.p.optInt("phone-type")))));
        com.chartboost.sdk.Libraries.d.a(this.e, "model", ayVar.a);
        com.chartboost.sdk.Libraries.d.a(this.e, TapjoyConstants.TJC_DEVICE_TYPE_NAME, ayVar.o);
        com.chartboost.sdk.Libraries.d.a(this.e, "os", ayVar.b);
        com.chartboost.sdk.Libraries.d.a(this.e, "country", ayVar.c);
        com.chartboost.sdk.Libraries.d.a(this.e, "language", ayVar.d);
        com.chartboost.sdk.Libraries.d.a(this.e, "timestamp", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())));
        com.chartboost.sdk.Libraries.d.a(this.e, "reachability", Integer.valueOf(this.b.b.a()));
        com.chartboost.sdk.Libraries.d.a(this.e, "scale", ayVar.m);
        com.chartboost.sdk.Libraries.d.a(this.e, "is_portrait", Boolean.valueOf(CBUtility.a().a()));
        com.chartboost.sdk.Libraries.d.a(this.e, "rooted_device", Boolean.valueOf(ayVar.q));
        com.chartboost.sdk.Libraries.d.a(this.e, TapjoyConstants.TJC_DEVICE_TIMEZONE, ayVar.r);
        com.chartboost.sdk.Libraries.d.a(this.e, "mobile_network", ayVar.s);
        com.chartboost.sdk.Libraries.d.a(this.e, "dw", ayVar.j);
        com.chartboost.sdk.Libraries.d.a(this.e, "dh", ayVar.k);
        com.chartboost.sdk.Libraries.d.a(this.e, "dpi", ayVar.l);
        com.chartboost.sdk.Libraries.d.a(this.e, "w", ayVar.h);
        com.chartboost.sdk.Libraries.d.a(this.e, "h", ayVar.i);
        com.chartboost.sdk.Libraries.d.a(this.e, "device_family", "");
        com.chartboost.sdk.Libraries.d.a(this.e, "retina", false);
        com.chartboost.sdk.Libraries.c.a aVarB = this.b.a.b();
        com.chartboost.sdk.Libraries.d.a(this.e, "identity", aVarB.b);
        if (aVarB.a != -1) {
            com.chartboost.sdk.Libraries.d.a(this.e, "limit_ad_tracking", Boolean.valueOf(aVarB.a == 1));
        }
        a(TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX, (Object) this.e);
        com.chartboost.sdk.Libraries.d.a(this.c, "framework", "");
        com.chartboost.sdk.Libraries.d.a(this.c, "sdk", ayVar.g);
        if (com.chartboost.sdk.i.d != null) {
            com.chartboost.sdk.Libraries.d.a(this.c, "framework_version", com.chartboost.sdk.i.f);
            com.chartboost.sdk.Libraries.d.a(this.c, "wrapper_version", com.chartboost.sdk.i.b);
        }
        com.chartboost.sdk.Libraries.d.a(this.c, "mediation", com.chartboost.sdk.i.h);
        com.chartboost.sdk.Libraries.d.a(this.c, "commit_hash", "afcc232a034726649153ea72aa2c9ac55c7dfab9");
        String str = this.b.d.get().a;
        if (!g.a().a((CharSequence) str)) {
            com.chartboost.sdk.Libraries.d.a(this.c, "config_variant", str);
        }
        a("sdk", (Object) this.c);
        com.chartboost.sdk.Libraries.d.a(this.f, "session", Integer.valueOf(this.b.e.getInt("cbPrefSessionCount", 0)));
        if (this.f.isNull("cache")) {
            com.chartboost.sdk.Libraries.d.a(this.f, "cache", false);
        }
        if (this.f.isNull(TapjoyConstants.TJC_AMOUNT)) {
            com.chartboost.sdk.Libraries.d.a(this.f, TapjoyConstants.TJC_AMOUNT, 0);
        }
        if (this.f.isNull("retry_count")) {
            com.chartboost.sdk.Libraries.d.a(this.f, "retry_count", 0);
        }
        if (this.f.isNull("location")) {
            com.chartboost.sdk.Libraries.d.a(this.f, "location", "");
        }
        a("ad", (Object) this.f);
    }

    public void a(String str, Object obj, a aVar) {
        switch (aVar) {
            case AD:
                com.chartboost.sdk.Libraries.d.a(this.f, str, obj);
                a("ad", (Object) this.f);
                break;
        }
    }
}
