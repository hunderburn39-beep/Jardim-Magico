package com.fyber.mediation.adcolony;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.fyber.ads.banners.mediation.BannerMediationAdapter;
import com.fyber.mediation.MediationAdapter;
import com.fyber.mediation.adcolony.interstitial.AdColonyInterstitialMediationAdapter;
import com.fyber.mediation.adcolony.rv.AdColonyVideoMediationAdapter;
import com.fyber.mediation.annotations.AdapterDefinition;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@AdapterDefinition(apiVersion = 5, name = "AdColony", sdkFeatures = {"banners", "blended"}, version = "3.0.4-r1")
public class AdColonyMediationAdapter extends MediationAdapter {
    public static final String APP_ID = "app.id";
    public static final String CLIENT_OPTIONS = "client.options";
    public static final String CONFIRMATION_DIALOG = "with.confirmation.dialog";
    private static final String CUSTOM_ID = "custom.id";
    private static final String DEVICE_ID = "device.id";
    public static final String TAG = AdColonyMediationAdapter.class.getSimpleName();
    public static final String ZONE_IDS_INTERSTITIAL = "zone.ids.interstitial";
    public static final String ZONE_IDS_REWARDED_VIDEO = "zone.ids.rewarded.video";
    private Map<String, Object> configs;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private AdColonyInterstitialMediationAdapter mInterstitialMediationAdapter;
    private AdColonyVideoMediationAdapter mVideoMediationAdapter;
    private List<String> mZoneIdInterstitialList;
    private List<String> mZoneIdRewardedVideoList;

    @Override // com.fyber.mediation.MediationAdapter
    public boolean startAdapter(final Activity activity, Map<String, Object> configs) {
        if (Build.VERSION.SDK_INT < 14) {
            FyberLogger.d(TAG, "AdColony requires Android Version 4.0.0\nThe mediation adapter will not be started");
            return false;
        }
        this.configs = configs;
        FyberLogger.i(TAG, "Starting AdColony adapter");
        final String appId = (String) getConfiguration(this.configs, APP_ID, String.class);
        this.mZoneIdInterstitialList = getZoneIds(ZONE_IDS_INTERSTITIAL);
        this.mZoneIdRewardedVideoList = getZoneIds(ZONE_IDS_REWARDED_VIDEO);
        final String clientOptions = (String) getConfiguration(this.configs, CLIENT_OPTIONS, String.class);
        final List<String> tUsedZones = new ArrayList<>();
        tUsedZones.addAll(this.mZoneIdRewardedVideoList);
        tUsedZones.addAll(this.mZoneIdInterstitialList);
        if (tUsedZones.isEmpty()) {
            FyberLogger.w(TAG, "At least one Zone ID must be provided! Adapter won't start.");
            return false;
        }
        if (StringUtils.nullOrEmpty(appId)) {
            FyberLogger.w(TAG, "App Id is not provided! Adapter won't start");
            return false;
        }
        this.mHandler.post(new Runnable() { // from class: com.fyber.mediation.adcolony.AdColonyMediationAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                AdColony.configure(activity, AdColonyMediationAdapter.this.getAdColonyOptionsFromString(clientOptions), appId, (String[]) tUsedZones.toArray(new String[tUsedZones.size()]));
                if (!AdColonyMediationAdapter.this.mZoneIdInterstitialList.isEmpty()) {
                    AdColonyMediationAdapter.this.mInterstitialMediationAdapter = new AdColonyInterstitialMediationAdapter(AdColonyMediationAdapter.this, AdColonyMediationAdapter.this.mZoneIdInterstitialList);
                }
                if (!AdColonyMediationAdapter.this.mZoneIdRewardedVideoList.isEmpty()) {
                    AdColonyMediationAdapter.this.mVideoMediationAdapter = new AdColonyVideoMediationAdapter(AdColonyMediationAdapter.this, AdColonyMediationAdapter.this.mZoneIdRewardedVideoList, AdColonyMediationAdapter.this.shouldShowConfirmationDialog());
                }
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AdColonyAppOptions getAdColonyOptionsFromString(String clientOptions) {
        AdColonyAppOptions options = new AdColonyAppOptions();
        if (clientOptions != null) {
            clientOptions.substring(clientOptions.indexOf(58) + 1, clientOptions.indexOf(44));
            clientOptions.substring(clientOptions.lastIndexOf(58));
        }
        return options;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public String getName() {
        return "AdColony";
    }

    @Override // com.fyber.mediation.MediationAdapter
    public String getVersion() {
        return "3.0.4-r1";
    }

    @Override // com.fyber.mediation.MediationAdapter
    public AdColonyVideoMediationAdapter getVideoMediationAdapter() {
        return this.mVideoMediationAdapter;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public AdColonyInterstitialMediationAdapter getInterstitialMediationAdapter() {
        return this.mInterstitialMediationAdapter;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public BannerMediationAdapter<AdColonyMediationAdapter> getBannerMediationAdapter() {
        return null;
    }

    @Override // com.fyber.mediation.MediationAdapter
    protected Set<?> getListeners() {
        return null;
    }

    private List<String> getZoneIds(String selectedZoneId) {
        List<String> zoneIds = new LinkedList<>();
        String[] zoneIdsFromAnnotation = (String[]) getConfiguration(this.configs, selectedZoneId, String[].class);
        if (zoneIdsFromAnnotation != null) {
            for (String id : zoneIdsFromAnnotation) {
                if (StringUtils.notNullNorEmpty(id)) {
                    zoneIds.add(id);
                }
            }
        } else {
            String zoneIdString = (String) getConfiguration(this.configs, selectedZoneId, String.class);
            if (StringUtils.notNullNorEmpty(zoneIdString)) {
                zoneIds = Arrays.asList(zoneIdString.split(","));
            }
        }
        FyberLogger.i(getName(), "selected zone id: " + selectedZoneId + "; " + Arrays.toString(zoneIds.toArray()));
        return zoneIds;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldShowConfirmationDialog() {
        return ((Boolean) getConfiguration(this.configs, CONFIRMATION_DIALOG, Boolean.FALSE, Boolean.class)).booleanValue();
    }
}
