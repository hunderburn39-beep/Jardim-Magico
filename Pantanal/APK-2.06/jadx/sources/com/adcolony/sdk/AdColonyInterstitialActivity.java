package com.adcolony.sdk;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyInterstitialActivity extends b {
    AdColonyInterstitial l;

    public AdColonyInterstitialActivity() {
        this.l = a.m == null ? null : a.m.s;
    }

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
        this.d = this.l.d;
        super.onCreate(bundle);
        if (a.m != null && this.l.a != null) {
            this.l.a.onOpened(this.l);
            AdColonyPubServices.onAdOpened();
        }
    }

    @Override // com.adcolony.sdk.b
    void a(z zVar) {
        super.a(zVar);
        f fVarRemove = a.m.g.f.remove(this.e);
        if (fVarRemove != null) {
            for (MediaPlayer mediaPlayer : fVarRemove.c.c.values()) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            }
            fVarRemove.b.i.autoPause();
            fVarRemove.b.i.release();
        }
        JSONObject jSONObjectF = s.f(zVar.b, "v4iap");
        JSONArray jSONArrayG = s.g(jSONObjectF, "product_ids");
        if (jSONObjectF != null && this.l != null && this.l.a != null && jSONArrayG.length() > 0) {
            this.l.a.onIAPEvent(this.l, s.b(jSONArrayG, 0), s.c(jSONObjectF, "engagement_type"));
        }
        a.m.g.a(this.c);
        if (this.l != null) {
            a.m.g.b.remove(this.l.e);
        }
        if (this.l != null && this.l.a != null) {
            this.l.a.onClosed(this.l);
            AdColonyPubServices.onAdClosed();
            this.l.b = null;
            this.l.a = null;
            this.l = null;
        }
        u.d.b("finish_ad call finished");
        System.gc();
    }
}
