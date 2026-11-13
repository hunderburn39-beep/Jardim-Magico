package com.tapjoy.mediation;

import android.os.Bundle;
import com.google.android.gms.plus.PlusShare;
import com.tapjoy.TapjoyException;
import com.tapjoy.TapjoyUtil;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TJMediatedPlacementData {
    private String a;
    private String b;
    private String c;
    private Bundle d;
    private JSONObject e;
    private String f;
    private String g;
    private int h;

    public TJMediatedPlacementData(String mediatedData) throws TapjoyException {
        try {
            JSONObject jSONObject = new JSONObject(mediatedData);
            this.a = jSONObject.getString("name");
            this.b = jSONObject.optString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, "No description provided");
            this.c = jSONObject.getString("class_name");
            this.d = a(TapjoyUtil.jsonToStringMap(jSONObject.getJSONObject("params")));
            this.e = jSONObject.getJSONObject("next_call");
            this.f = jSONObject.getString("fill_url");
            this.g = jSONObject.getString("no_fill_url");
            this.h = jSONObject.optInt("current_card_index", 0);
        } catch (JSONException e) {
            throw new TapjoyException("Could not create MediatedPlacementData. Malformed or missing data.");
        }
    }

    private static Bundle a(Map map) {
        Bundle bundle = new Bundle();
        for (Map.Entry entry : map.entrySet()) {
            bundle.putString((String) entry.getKey(), (String) entry.getValue());
        }
        return bundle;
    }

    public String getName() {
        return this.a;
    }

    public String getClassname() {
        return this.c;
    }

    public Bundle getExtras() {
        return this.d;
    }

    public JSONObject getNextCall() {
        return this.e;
    }

    public String getFillURL() {
        return this.f;
    }

    public String getNoFillURL() {
        return this.g;
    }
}
