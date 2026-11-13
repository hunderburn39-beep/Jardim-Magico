package com.adcolony.sdk;

import android.content.Intent;
import android.support.annotation.NonNull;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyInterstitial {
    public static final int ADCOLONY_IAP_ENGAGEMENT_END_CARD = 0;
    public static final int ADCOLONY_IAP_ENGAGEMENT_OVERLAY = 1;
    AdColonyInterstitialListener a;
    c b;
    AdColonyAdOptions c;
    int d;
    String e;
    int f;
    String g;
    boolean h;
    private boolean i;

    AdColonyInterstitial(String ad_session_id, AdColonyInterstitialListener listener, String zone_id) {
        this.a = listener;
        this.g = zone_id;
        this.e = ad_session_id;
    }

    public boolean show() {
        if (this.i) {
            u.e.b("This ad object has already been shown. Please request a new ad via AdColony.requestInterstitial.");
            return false;
        }
        if (this.h) {
            u.e.b("This ad object has expired. Please request a new ad via AdColony.requestInterstitial.");
            return false;
        }
        if (a.m.D) {
            u.e.b("Can not show ad while an interstitial is already active.");
            return false;
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "zone_id", this.g);
        s.b(jSONObjectA, "type", 0);
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.e);
        if (this.c != null) {
            s.a(jSONObjectA, "pre_popup", this.c.a);
            s.a(jSONObjectA, "post_popup", this.c.b);
        }
        AdColonyZone adColonyZone = a.m.z.get(this.g);
        if (adColonyZone != null && adColonyZone.r && a.m.t == null) {
            u.e.b("Rewarded ad: show() called with no reward listener set.");
        }
        new z("AdSession.launch_ad_unit", 1, jSONObjectA).b();
        return true;
    }

    public boolean cancel() {
        if (this.b == null || !(a.l instanceof AdColonyInterstitialActivity)) {
            return false;
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.b.m);
        new z("AdSession.on_request_close", this.b.l, jSONObjectA).b();
        return true;
    }

    public AdColonyInterstitialListener getListener() {
        return this.a;
    }

    public void setListener(@NonNull AdColonyInterstitialListener listener) {
        this.a = listener;
    }

    public String getZoneID() {
        return this.g;
    }

    public boolean isExpired() {
        return this.h || this.i;
    }

    public void destroy() {
        a.m.g.b.remove(this.e);
    }

    boolean a() {
        if (a.l == null || a.m == null) {
            return false;
        }
        a.m.D = true;
        a.m.q = this.b;
        a.m.s = this;
        u.b.b("Launching fullscreen Activity via AdColonyInterstitial's launch method.");
        a.l.startActivity(new Intent(a.l, (Class<?>) AdColonyInterstitialActivity.class));
        this.i = true;
        return true;
    }

    void a(c cVar) {
        this.b = cVar;
    }
}
