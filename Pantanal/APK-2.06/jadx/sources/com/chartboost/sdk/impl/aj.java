package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class aj {
    protected final ak b;
    private String c;
    private String d;
    private a f = null;
    private com.chartboost.sdk.Libraries.f.a g = null;
    private ac.b h = ac.b.NORMAL;
    protected final JSONObject a = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
    private String e = "POST";

    public interface a {
        void a(JSONObject jSONObject, aj ajVar);

        void a(JSONObject jSONObject, aj ajVar, CBError cBError);
    }

    public aj(String str, ak akVar) {
        this.c = str;
        this.b = akVar;
    }

    public void a(String str, Object obj) {
        com.chartboost.sdk.Libraries.d.a(this.a, str, obj);
    }

    public void a(String str, JSONObject jSONObject) {
        if (jSONObject != null && !jSONObject.isNull(str)) {
            a(str, jSONObject.optString(str));
        }
    }

    protected void a() {
        ay ayVar = this.b.c;
        a("app", ayVar.n);
        a("model", ayVar.a);
        a(TapjoyConstants.TJC_DEVICE_TYPE_NAME, ayVar.o);
        a("os", ayVar.b);
        a("country", ayVar.c);
        a("language", ayVar.d);
        a("sdk", ayVar.g);
        a("timestamp", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())));
        a("session", Integer.valueOf(this.b.e.getInt("cbPrefSessionCount", 0)));
        a("reachability", Integer.valueOf(this.b.b.a()));
        a("scale", ayVar.m);
        a("is_portrait", Boolean.valueOf(CBUtility.a().a()));
        a(TJAdUnitConstants.String.BUNDLE, ayVar.e);
        a("bundle_id", ayVar.f);
        a("carrier", ayVar.p);
        a("custom_id", com.chartboost.sdk.i.a);
        a("mediation", com.chartboost.sdk.i.h);
        if (com.chartboost.sdk.i.d != null) {
            a("framework_version", com.chartboost.sdk.i.f);
            a("wrapper_version", com.chartboost.sdk.i.b);
        }
        a("rooted_device", Boolean.valueOf(ayVar.q));
        a(TapjoyConstants.TJC_DEVICE_TIMEZONE, ayVar.r);
        a("mobile_network", ayVar.s);
        a("dw", ayVar.j);
        a("dh", ayVar.k);
        a("dpi", ayVar.l);
        a("w", ayVar.h);
        a("h", ayVar.i);
        a("commit_hash", "afcc232a034726649153ea72aa2c9ac55c7dfab9");
        com.chartboost.sdk.Libraries.c.a aVarB = this.b.a.b();
        a("identity", aVarB.b);
        if (aVarB.a != -1) {
            a("limit_ad_tracking", Boolean.valueOf(aVarB.a == 1));
        }
        String str = this.b.d.get().a;
        if (!g.a().a((CharSequence) str)) {
            a("config_variant", str);
        }
    }

    public String b() {
        return d();
    }

    public String c() {
        return this.a.toString();
    }

    public String d() {
        if (this.c == null) {
            return "/";
        }
        return (this.c.startsWith("/") ? "" : "/") + this.c;
    }

    public void a(String str) {
        this.c = str;
    }

    public boolean e() {
        return d().equals("/api/track");
    }

    public JSONObject f() {
        return this.a;
    }

    public com.chartboost.sdk.Libraries.f.a g() {
        return this.g;
    }

    public void a(com.chartboost.sdk.Libraries.f.a aVar) {
        if (!com.chartboost.sdk.Libraries.f.c(aVar)) {
            CBLogging.b("CBRequest", "Validation predicate must be a dictionary style -- either VDictionary, VDictionaryExact, VDictionaryWithValues, or just a list of KV pairs.");
        }
        this.g = aVar;
    }

    public void a(com.chartboost.sdk.Libraries.f.k... kVarArr) {
        this.g = com.chartboost.sdk.Libraries.f.a(kVarArr);
    }

    public void b(String str) {
        this.d = str;
    }

    public void a(ac.b bVar) {
        this.h = bVar;
    }

    public ac.b h() {
        return this.h;
    }

    public a i() {
        return this.f;
    }

    public void j() {
        this.f = null;
        this.b.a(this, null);
    }

    public void a(a aVar) {
        this.f = aVar;
        this.b.a(this, aVar);
    }
}
