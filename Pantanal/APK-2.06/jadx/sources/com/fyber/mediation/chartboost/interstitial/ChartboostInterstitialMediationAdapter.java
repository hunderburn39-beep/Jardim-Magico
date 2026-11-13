package com.fyber.mediation.chartboost.interstitial;

import android.app.Activity;
import android.content.Context;
import com.chartboost.sdk.Chartboost;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.mediation.chartboost.ChartboostMediationAdapter;
import com.fyber.utils.FyberLogger;

/* JADX INFO: loaded from: classes.dex */
public class ChartboostInterstitialMediationAdapter extends InterstitialMediationAdapter<ChartboostMediationAdapter> implements IFyberChartboostInterstitial {
    private static final String TAG = ChartboostInterstitialMediationAdapter.class.getSimpleName();

    public ChartboostInterstitialMediationAdapter(ChartboostMediationAdapter adapter) {
        super(adapter);
    }

    @Override // com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter
    protected void checkForAds(Context context) {
        checkForInterstitial();
    }

    @Override // com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter
    protected void show(Activity activity) {
        if (Chartboost.hasInterstitial(ChartboostMediationAdapter.LOCATION_INTERSTITIAL_DEFAULT)) {
            Chartboost.showInterstitial(ChartboostMediationAdapter.LOCATION_INTERSTITIAL_DEFAULT);
        } else {
            FyberLogger.w(TAG, "Chartboost.hasInterstitial(fyber_interstitial) returned `false`;");
            interstitialError("Ad has not been cached yet.");
        }
    }

    public void checkForInterstitial() {
        Chartboost.cacheInterstitial(ChartboostMediationAdapter.LOCATION_INTERSTITIAL_DEFAULT);
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberSetAdAvailable() {
        setAdAvailable();
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberSetAdUnavailable() {
        setAdNotAvailable();
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberSetAdError(String msg) {
        setAdError(msg);
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberInterstitialShown() {
        interstitialShown();
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberInterstitialClicked() {
        interstitialClicked();
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberInterstitialClosed() {
        interstitialClosed();
    }

    @Override // com.fyber.mediation.chartboost.interstitial.IFyberChartboostInterstitial
    public void fyberInterstitialError(String msg) {
        interstitialError(msg);
    }
}
