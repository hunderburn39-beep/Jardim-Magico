package com.chartboost.sdk.Libraries;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static JSONObject a(JSONObject jSONObject, String... strArr) {
        for (String str : strArr) {
            if (jSONObject == null) {
                break;
            }
            jSONObject = jSONObject.optJSONObject(str);
        }
        return jSONObject;
    }

    public static void a(JSONObject jSONObject, String str, Object obj) {
        try {
            jSONObject.put(str, obj);
        } catch (JSONException e) {
            com.chartboost.sdk.Tracking.a.a(d.class, "put (" + str + ")", e);
        }
    }

    public static JSONObject a(a... aVarArr) {
        JSONObject jSONObject = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
        for (a aVar : aVarArr) {
            a(jSONObject, aVar.a, aVar.b);
        }
        return jSONObject;
    }

    public static a a(String str, Object obj) {
        return new a(str, obj);
    }

    public static class a {
        final String a;
        final Object b;

        public a(String str, Object obj) {
            this.a = str;
            this.b = obj;
        }
    }
}
