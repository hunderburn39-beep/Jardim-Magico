package com.fyber.mediation.adcolony.interstitial;

import android.app.Activity;
import android.content.Context;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.mediation.adcolony.AdColonyMediationAdapter;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyInterstitialMediationAdapter extends InterstitialMediationAdapter<AdColonyMediationAdapter> {
    private static final String ERROR_NO_VIDEO_AVAILABLE = "No Video Available";
    private static String TAG = AdColonyInterstitialMediationAdapter.class.getSimpleName();
    private String currentZoneId;
    private List<String> interstitialZoneIds;
    private AdColonyInterstitial mAdColonyInterstitial;
    AdColonyInterstitialListener mListener;
    private List<String> zoneBlacklist;

    public AdColonyInterstitialMediationAdapter(AdColonyMediationAdapter adapter, List<String> interstitialZoneIds) {
        super(adapter);
        this.mListener = new AdColonyInterstitialListener() { // from class: com.fyber.mediation.adcolony.interstitial.AdColonyInterstitialMediationAdapter.1
            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
                AdColonyInterstitialMediationAdapter.this.setAdAvailable();
                AdColonyInterstitialMediationAdapter.this.mAdColonyInterstitial = adColonyInterstitial;
            }

            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onRequestNotFilled(AdColonyZone zone) {
                AdColonyInterstitialMediationAdapter.this.setAdNotAvailable();
                AdColonyInterstitialMediationAdapter.this.mAdColonyInterstitial = null;
                AdColonyInterstitialMediationAdapter.this.zoneBlacklist.add(zone.getZoneID());
            }

            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onOpened(AdColonyInterstitial ad) {
                AdColonyInterstitialMediationAdapter.this.interstitialShown();
            }

            @Override // com.adcolony.sdk.AdColonyInterstitialListener
            public void onClosed(AdColonyInterstitial ad) {
                AdColonyInterstitialMediationAdapter.this.interstitialClosed();
                AdColonyInterstitialMediationAdapter.this.mAdColonyInterstitial = null;
            }
        };
        this.interstitialZoneIds = interstitialZoneIds;
        this.zoneBlacklist = new ArrayList();
    }

    @Override // com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter
    protected void show(Activity activity) {
        if (this.mAdColonyInterstitial != null && !this.mAdColonyInterstitial.isExpired()) {
            this.mAdColonyInterstitial.show();
            return;
        }
        if (this.mAdColonyInterstitial == null) {
            FyberLogger.e(TAG, "interstitialVideo is null");
        }
        interstitialError(ERROR_NO_VIDEO_AVAILABLE);
    }

    @Override // com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter
    protected void checkForAds(Context context) {
        initInterstitial();
    }

    private void initInterstitial() {
        if (this.zoneBlacklist.size() == this.interstitialZoneIds.size()) {
            this.zoneBlacklist.clear();
        }
        String tZoneId = getZoneId();
        if (tZoneId != null) {
            FyberLogger.i(getClass().getSimpleName(), "Got zone id for interstitials: " + tZoneId);
            this.currentZoneId = tZoneId;
            AdColony.requestInterstitial(this.currentZoneId, this.mListener);
        }
    }

    private String getZoneId() {
        String placementId = getContextData().get("tpn_placement_id");
        if (!StringUtils.nullOrEmpty(placementId)) {
            return placementId;
        }
        String zoneId = getFirstNotBlacklistedZoneId();
        if (StringUtils.notNullNorEmpty(zoneId)) {
            FyberLogger.d(TAG, "No placement id found in context data, falling back to configs.");
            return zoneId;
        }
        setAdError(InterstitialMediationAdapter.ERROR_NO_PLACEMENT_ID);
        FyberLogger.w(TAG, "Ad request failed because there is no placement id to use in the ad request.");
        return null;
    }

    private String getFirstNotBlacklistedZoneId() {
        for (String zoneId : this.interstitialZoneIds) {
            if (!this.zoneBlacklist.contains(zoneId)) {
                return zoneId;
            }
        }
        return null;
    }
}
