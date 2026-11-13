package com.adcolony.sdk;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesPushNotification implements Serializable {
    static final String a = "AdColonyPubServicesPushNotification";
    static final String b = "payload";
    static final String c = "id";
    String d;
    cc e;
    String f;
    boolean g;
    long h;
    boolean i;

    AdColonyPubServicesPushNotification() {
        this.g = false;
        this.h = -1L;
        this.i = false;
        this.d = null;
        this.e = null;
        this.f = null;
    }

    AdColonyPubServicesPushNotification(String payload, cc metaObject, String id) {
        this.g = false;
        this.h = -1L;
        this.i = false;
        this.d = payload;
        this.e = metaObject;
        this.f = id;
    }

    void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.e = new cc(jSONObject.getJSONObject("meta"));
            this.f = a(jSONObject, "id");
            this.d = a(jSONObject, b);
        } catch (JSONException e) {
            bw.a(a, "Error occurred while parsing PushNotificaiton Data ->" + e.getMessage(), e);
        }
    }

    private String a(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getString(str);
        } catch (JSONException e) {
            bw.b(a, "Error occurred while parsing Push field=" + str + " ->" + e.toString(), true);
            return null;
        }
    }

    cc a() {
        return this.e;
    }

    boolean b() {
        return this.g;
    }

    void a(boolean z) {
        this.g = z;
    }

    void a(long j) {
        this.h = j;
    }

    void b(boolean z) {
        this.i = z;
    }

    public String getPayload() {
        return this.d;
    }

    public String getId() {
        return this.f;
    }

    public long getTimeReceivedMsSince1970() {
        return this.h;
    }

    public boolean isAdColonyNotification() {
        return this.i;
    }

    public String toString() {
        return ((this.d != null ? "AdColonyPubServicesPushNotification={payload={" + this.d.toString() + "}," : "AdColonyPubServicesPushNotification={payload={},") + "timeReceivedEpoch=" + this.h) + "id=" + this.f + "};";
    }
}
