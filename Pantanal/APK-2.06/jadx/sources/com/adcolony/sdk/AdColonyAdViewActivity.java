package com.adcolony.sdk;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyAdViewActivity extends b {
    ax l = a.m.r;
    boolean m = this.l instanceof AdColonyNativeAdView;

    @Override // com.adcolony.sdk.b, android.app.Activity
    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    @Override // com.adcolony.sdk.b, android.app.Activity, android.content.ComponentCallbacks
    public /* bridge */ /* synthetic */ void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.adcolony.sdk.b, android.app.Activity
    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    @Override // com.adcolony.sdk.b, android.app.Activity
    public /* bridge */ /* synthetic */ void onPause() {
        super.onPause();
    }

    @Override // com.adcolony.sdk.b, android.app.Activity
    public /* bridge */ /* synthetic */ void onResume() {
        super.onResume();
    }

    @Override // com.adcolony.sdk.b, android.app.Activity, android.view.Window.Callback
    public /* bridge */ /* synthetic */ void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    @Override // com.adcolony.sdk.b, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a.m.x = true;
        if (this.l.c != null && (this.l.c instanceof AdColonyNativeAdViewListener)) {
            ((AdColonyNativeAdViewListener) this.l.c).onOpened((AdColonyNativeAdView) this.l);
        }
    }

    @Override // com.adcolony.sdk.b
    void a(z zVar) {
        super.a(zVar);
        if (this.l.b != null) {
            JSONObject jSONObjectF = s.f(zVar.b, "v4iap");
            JSONArray jSONArrayG = s.g(jSONObjectF, "product_ids");
            if (this.l.c != null) {
                if (this.m) {
                    ((AdColonyNativeAdViewListener) this.l.c).onClosed((AdColonyNativeAdView) this.l);
                    AdColonyPubServices.onAdClosed();
                    if (jSONObjectF != null && jSONArrayG.length() > 0) {
                        ((AdColonyNativeAdViewListener) this.l.c).onIAPEvent((AdColonyNativeAdView) this.l, s.b(jSONArrayG, 0), s.c(jSONObjectF, "engagement_type"));
                    }
                } else {
                    ((ay) this.l.c).c(this.l);
                    AdColonyPubServices.onAdClosed();
                    if (jSONObjectF != null && jSONArrayG.length() > 0) {
                        ((ay) this.l.c).a(this.l, s.b(jSONArrayG, 0), s.c(jSONObjectF, "engagement_type"));
                    }
                }
            }
            ((ViewGroup) this.l.b.getParent()).removeView(this.l.b);
            a.m.g.a(this.l.b);
            this.l.b = null;
            System.gc();
        }
    }
}
