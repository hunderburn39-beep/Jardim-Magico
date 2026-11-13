package com.fyber.ads.interstitials;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.fyber.ads.interstitials.b.a;
import com.fyber.ads.interstitials.b.b;
import com.fyber.mediation.MediationUserActivityListener;
import com.fyber.utils.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class InterstitialActivity extends Activity implements InterstitialAdListener {
    public static final String AD_STATUS = "AD_STATUS";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    private MediationUserActivityListener a;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (b.a().a()) {
            a aVarB = b.b();
            if (aVarB != null) {
                aVarB.a((InterstitialAdListener) this);
                aVarB.a((Activity) this);
                return;
            } else {
                b.a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
                onAdError(null, "Unknown internal issue. Please try again later.");
                return;
            }
        }
        onAdError(null, "There's no Ad available to be shown currently.");
    }

    @Override // android.app.Activity
    protected void onPause() {
        if (this.a != null) {
            this.a.notifyOnUserLeft();
        }
        super.onPause();
    }

    public void setMarketPlaceInterstitialListener(MediationUserActivityListener mediationUserActivityListener) {
        if (this.a == null) {
            this.a = mediationUserActivityListener;
        }
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        if (this.a != null) {
            this.a.notifyOnUserLeft();
        }
        super.onUserLeaveHint();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.a == null || !this.a.notifyOnBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override // com.fyber.ads.interstitials.InterstitialAdListener
    public void onAdError(InterstitialAd interstitialAd, String str) {
        finishInterstitialActivity(InterstitialAdCloseReason.ReasonError, str);
    }

    @Override // com.fyber.ads.interstitials.InterstitialAdListener
    public void onAdClosed(InterstitialAd interstitialAd, InterstitialAdCloseReason interstitialAdCloseReason) {
        finishInterstitialActivity(interstitialAdCloseReason, null);
    }

    @Override // com.fyber.ads.interstitials.InterstitialAdListener
    public void onAdShown(InterstitialAd interstitialAd) {
    }

    @Override // com.fyber.ads.interstitials.InterstitialAdListener
    public void onAdClicked(InterstitialAd interstitialAd) {
    }

    protected void finishInterstitialActivity(InterstitialAdCloseReason interstitialAdCloseReason, String str) {
        Intent intent = new Intent();
        intent.putExtra(AD_STATUS, interstitialAdCloseReason);
        if (StringUtils.notNullNorEmpty(str)) {
            intent.putExtra(ERROR_MESSAGE, str);
        }
        setResult(-1, intent);
        finish();
    }
}
