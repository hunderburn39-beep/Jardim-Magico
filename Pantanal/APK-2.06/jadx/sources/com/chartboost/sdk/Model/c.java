package com.chartboost.sdk.Model;

import com.chartboost.sdk.g;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public final int A;
    public final boolean B;
    public final String C;
    public final String D;
    public final String E;
    public final String F;
    public final String a;
    public final boolean b;
    public final boolean c;
    public final List<String> d;
    public final boolean e;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final boolean i;
    public final int j;
    public final boolean k;
    public final boolean l;
    public final boolean m;
    public final boolean n;
    public final boolean o;
    public final boolean p;
    public final int q;
    public final int r;
    public final int s;
    public final List<String> t;
    public final boolean u;
    public final boolean v;
    public final boolean w;
    public final int x;
    public final boolean y;
    public final boolean z;

    public c(JSONObject jSONObject) {
        g gVarA = g.a();
        this.a = jSONObject.optString("configVariant");
        this.b = jSONObject.optBoolean("prefetchDisable");
        this.c = jSONObject.optBoolean("publisherDisable");
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("invalidateFolderList");
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                String strOptString = jSONArrayOptJSONArray.optString(i);
                if (!strOptString.isEmpty()) {
                    arrayList.add(strOptString);
                }
            }
        }
        this.d = Collections.unmodifiableList(arrayList);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(TapjoyConstants.TJC_PLUGIN_NATIVE);
        jSONObjectOptJSONObject = jSONObjectOptJSONObject == null ? (JSONObject) gVarA.a(new JSONObject()) : jSONObjectOptJSONObject;
        this.e = jSONObjectOptJSONObject.optBoolean(TJAdUnitConstants.String.ENABLED, true);
        this.f = jSONObjectOptJSONObject.optBoolean("inplayEnabled", true);
        this.g = jSONObjectOptJSONObject.optBoolean("interstitialEnabled", true);
        this.h = jSONObjectOptJSONObject.optBoolean("lockOrientation");
        this.i = jSONObjectOptJSONObject.optBoolean("moreAppsEnabled", true);
        this.j = jSONObjectOptJSONObject.optInt("prefetchSession", 3);
        this.k = jSONObjectOptJSONObject.optBoolean("rewardVideoEnabled", true);
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("trackingLevels");
        jSONObjectOptJSONObject2 = jSONObjectOptJSONObject2 == null ? (JSONObject) gVarA.a(new JSONObject()) : jSONObjectOptJSONObject2;
        this.l = jSONObjectOptJSONObject2.optBoolean("critical", true);
        this.m = jSONObjectOptJSONObject2.optBoolean(TapjoyConstants.TJC_DEBUG);
        this.n = jSONObjectOptJSONObject2.optBoolean("session");
        this.o = jSONObjectOptJSONObject2.optBoolean("system");
        this.p = jSONObjectOptJSONObject2.optBoolean("user");
        JSONObject jSONObjectOptJSONObject3 = jSONObject.optJSONObject("webview");
        jSONObjectOptJSONObject3 = jSONObjectOptJSONObject3 == null ? (JSONObject) gVarA.a(new JSONObject()) : jSONObjectOptJSONObject3;
        boolean zA = com.chartboost.sdk.impl.g.a().a(14);
        this.q = jSONObjectOptJSONObject3.optInt("cacheMaxBytes", 104857600);
        int iOptInt = jSONObjectOptJSONObject3.optInt("cacheMaxUnits", 10);
        this.r = iOptInt <= 0 ? 10 : iOptInt;
        this.s = (int) TimeUnit.SECONDS.toDays(jSONObjectOptJSONObject3.optInt("cacheTTLs", com.chartboost.sdk.Libraries.a.a));
        ArrayList arrayList2 = new ArrayList();
        JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject3.optJSONArray("directories");
        if (jSONArrayOptJSONArray2 != null) {
            int length2 = jSONArrayOptJSONArray2.length();
            for (int i2 = 0; i2 < length2; i2++) {
                String strOptString2 = jSONArrayOptJSONArray2.optString(i2);
                if (!strOptString2.isEmpty()) {
                    arrayList2.add(strOptString2);
                }
            }
        }
        this.t = Collections.unmodifiableList(arrayList2);
        this.u = zA && jSONObjectOptJSONObject3.optBoolean(TJAdUnitConstants.String.ENABLED, true);
        this.v = jSONObjectOptJSONObject3.optBoolean("inplayEnabled", true);
        this.w = jSONObjectOptJSONObject3.optBoolean("interstitialEnabled", true);
        int iOptInt2 = jSONObjectOptJSONObject3.optInt("invalidatePendingImpression", 3);
        this.x = iOptInt2 <= 0 ? 3 : iOptInt2;
        this.y = jSONObjectOptJSONObject3.optBoolean("lockOrientation", true);
        this.z = jSONObjectOptJSONObject3.optBoolean("moreAppsEnabled", true);
        this.A = jSONObjectOptJSONObject3.optInt("prefetchSession", 3);
        this.B = jSONObjectOptJSONObject3.optBoolean("rewardVideoEnabled", true);
        this.C = jSONObjectOptJSONObject3.optString("version", "v2");
        this.D = String.format("%s/%s%s", "webview", this.C, "/interstitial/get");
        this.E = String.format("%s/%s/%s", "webview", this.C, "prefetch");
        this.F = String.format("%s/%s%s", "webview", this.C, "/reward/get");
    }
}
