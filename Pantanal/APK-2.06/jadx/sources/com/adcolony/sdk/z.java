package com.adcolony.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class z {
    String a;
    JSONObject b;

    z(JSONObject jSONObject) {
        try {
            this.b = jSONObject;
            this.a = jSONObject.getString("m_type");
        } catch (JSONException e) {
            u.h.a("JSON Error in ADCMessage constructor: ").b(e.toString());
        }
    }

    z(String str, int i) {
        try {
            this.a = str;
            this.b = new JSONObject();
            this.b.put("m_target", i);
        } catch (JSONException e) {
            u.h.a("JSON Error in ADCMessage constructor: ").b(e.toString());
        }
    }

    z(String str, int i, String str2) {
        try {
            this.a = str;
            this.b = s.a(str2);
            this.b.put("m_target", i);
        } catch (JSONException e) {
            u.h.a("JSON Error in ADCMessage constructor: ").b(e.toString());
        }
    }

    z(String str, int i, JSONObject jSONObject) {
        try {
            this.a = str;
            this.b = jSONObject;
            this.b.put("m_target", i);
        } catch (JSONException e) {
            u.h.a("JSON Error in ADCMessage constructor: ").b(e.toString());
        }
    }

    z a() {
        return a((JSONObject) null);
    }

    z a(String str) {
        return a(s.a(str));
    }

    z a(JSONObject jSONObject) {
        try {
            z zVar = new z("reply", this.b.getInt("m_origin"), jSONObject);
            zVar.b.put("m_id", this.b.getInt("m_id"));
            return zVar;
        } catch (JSONException e) {
            u.h.a("JSON error in ADCMessage's create_reply(): ").b(e.toString());
            return new z("JSONException", 0);
        }
    }

    void b() {
        a.a(this.a, this.b);
    }
}
