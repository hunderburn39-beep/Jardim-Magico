package com.adcolony.sdk;

import android.net.Uri;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesCallbacks {
    public void onServiceAvailabilityChanged(AdColonyPubServices.ServiceAvailability status) {
    }

    public void onOverlayVisibilityChanged() {
    }

    public void onInAppPurchaseRewardSuccess(String productId, int inGameCurrencyBonus) {
    }

    public void onInAppPurchaseRewardDidFail(String productId, String error) {
    }

    public void onStatsRefreshed() {
    }

    public void onGrantDigitalProductItem(AdColonyPubServicesDigitalItem product) {
    }

    public void onPushRegistrationSuccess(String token) {
    }

    public void onPushRegistrationFailure(Exception exception) {
    }

    public void onPushNotificationReceived(AdColonyPubServicesPushNotification pushNotification) {
    }

    public void onOpenUrl(Uri uri, Map<String, String> queryParams, boolean isAdColonyUri) {
    }

    public void onVIPInformationUpdated() {
    }
}
