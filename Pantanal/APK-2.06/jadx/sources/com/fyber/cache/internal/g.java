package com.fyber.cache.internal;

import com.fyber.utils.FyberLogger;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ConfigurationResponseParser.java */
/* JADX INFO: loaded from: classes.dex */
public final class g {
    private b a;
    private JSONObject b;
    private ArrayList<h> c = new ArrayList<>();

    public static g a(String str) {
        return new g(str);
    }

    private g(String str) {
        this.a = null;
        this.b = null;
        FyberLogger.i("ConfigurationResponseParser", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.b = jSONObject.getJSONObject("config");
            this.a = a(this.b);
            JSONArray jSONArray = jSONObject.getJSONArray("videos");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                this.c.add(new h(jSONObject2.getInt("ad_id"), jSONObject2.getString("url")));
            }
        } catch (JSONException e) {
            FyberLogger.d("ConfigurationResponseParser", e.getLocalizedMessage());
        }
    }

    private static b a(JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString(TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        Integer numValueOf = null;
        if (jSONObject.has("refresh_interval")) {
            try {
                numValueOf = Integer.valueOf(jSONObject.getInt("refresh_interval"));
            } catch (JSONException e) {
            }
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("network_policy");
        JSONObject jSONObject3 = jSONObject2.getJSONObject(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI);
        JSONObject jSONObject4 = jSONObject2.getJSONObject("cellular");
        d dVar = new d(jSONObject3.getInt("max_download_count"));
        return new b().a(a.EnumC0023a.WIFI, dVar).a(a.EnumC0023a.CELLULAR, new d(jSONObject4.getInt("max_download_count"))).a(numValueOf).a(string);
    }

    public final b a() {
        return this.a;
    }

    public final ArrayList<h> b() {
        return this.c;
    }
}
