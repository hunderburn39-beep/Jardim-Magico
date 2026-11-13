package com.adcolony.sdk;

import android.net.Uri;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class bf {
    ArrayList<AdColonyPubServicesCallbacks> a = new ArrayList<>();

    abstract String a();

    bf() {
    }

    void a(AdColonyPubServicesCallbacks adColonyPubServicesCallbacks) {
        this.a.add(adColonyPubServicesCallbacks);
        bv.aN().aG().a(adColonyPubServicesCallbacks);
    }

    void a(final AdColonyPubServices.ServiceAvailability serviceAvailability) {
        bw.b(a(), "yvolverNotificationServiceAvailabilityChanged() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.1
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onServiceAvailabilityChanged(serviceAvailability);
                    }
                }
            }
        });
    }

    void b() {
        bw.b(a(), "yvolverNotificationOverlayShownChanged() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.4
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onOverlayVisibilityChanged();
                    }
                }
            }
        });
    }

    void a(final AdColonyPubServicesDigitalItem adColonyPubServicesDigitalItem) {
        bw.b(a(), "yvolverGrantDigitalProductItem() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.5
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onGrantDigitalProductItem(adColonyPubServicesDigitalItem);
                    }
                }
            }
        });
    }

    void a(final String str, final int i, final String str2) {
        bw.b(a(), "yvolverInAppPurchaseRewardSuccess()", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.6
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onInAppPurchaseRewardSuccess(str, i);
                        bv.aN().a(i, str, str2, true);
                    }
                }
            }
        });
    }

    void a(final String str, final String str2) {
        bw.b(a(), "yvolverInAppPurchaseRewardDidFail() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.7
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onInAppPurchaseRewardDidFail(str, str2);
                    }
                }
            }
        });
    }

    void c() {
        bw.b(a(), "yvolverStatsRefreshed() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.8
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onStatsRefreshed();
                    }
                }
            }
        });
    }

    void a(@NonNull final String str) {
        bw.b(a(), "onPushRegistrationSuccess() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.9
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onPushRegistrationSuccess(str);
                    }
                }
            }
        });
    }

    void a(@NonNull final Exception exc) {
        bw.b(a(), "onPushRegistrationFailure() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.10
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onPushRegistrationFailure(exc);
                    }
                }
            }
        });
    }

    void a(final AdColonyPubServicesPushNotification adColonyPubServicesPushNotification) {
        bw.b(a(), "onPushNotificationReceived() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.11
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onPushNotificationReceived(adColonyPubServicesPushNotification);
                    }
                }
            }
        });
    }

    void a(final Uri uri, final Map<String, String> map, final boolean z) {
        bw.b(a(), "yvolverOnOpenUrl() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.2
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onOpenUrl(uri, map, z);
                    }
                }
            }
        });
    }

    void d() {
        bw.b(a(), "yvolverOnVIPInformationUpdated() called", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bf.3
            @Override // java.lang.Runnable
            public void run() {
                if (!bf.this.a.isEmpty()) {
                    Iterator<AdColonyPubServicesCallbacks> it = bf.this.a.iterator();
                    while (it.hasNext()) {
                        it.next().onVIPInformationUpdated();
                    }
                }
            }
        });
    }
}
