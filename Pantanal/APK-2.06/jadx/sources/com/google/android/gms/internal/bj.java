package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class bj {
    public final List<bi> nc;
    public final long nd;
    public final List<String> ne;
    public final List<String> nf;
    public final List<String> ng;
    public final String nh;
    public final long ni;
    public int nj;
    public int nk;

    public bj(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (dw.n(2)) {
            dw.y("Mediation Response JSON: " + jSONObject.toString(2));
        }
        JSONArray jSONArray = jSONObject.getJSONArray("ad_networks");
        ArrayList arrayList = new ArrayList(jSONArray.length());
        int i = -1;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            bi biVar = new bi(jSONArray.getJSONObject(i2));
            arrayList.add(biVar);
            if (i < 0 && a(biVar)) {
                i = i2;
            }
        }
        this.nj = i;
        this.nk = jSONArray.length();
        this.nc = Collections.unmodifiableList(arrayList);
        this.nh = jSONObject.getString("qdata");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("settings");
        if (jSONObjectOptJSONObject == null) {
            this.nd = -1L;
            this.ne = null;
            this.nf = null;
            this.ng = null;
            this.ni = -1L;
            return;
        }
        this.nd = jSONObjectOptJSONObject.optLong("ad_network_timeout_millis", -1L);
        this.ne = bo.a(jSONObjectOptJSONObject, "click_urls");
        this.nf = bo.a(jSONObjectOptJSONObject, "imp_urls");
        this.ng = bo.a(jSONObjectOptJSONObject, "nofill_urls");
        long jOptLong = jSONObjectOptJSONObject.optLong("refresh", -1L);
        this.ni = jOptLong > 0 ? jOptLong * 1000 : -1L;
    }

    private boolean a(bi biVar) {
        Iterator<String> it = biVar.mY.iterator();
        while (it.hasNext()) {
            if (it.next().equals("com.google.ads.mediation.admob.AdMobAdapter")) {
                return true;
            }
        }
        return false;
    }
}
