package com.adcolony.sdk;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.GoogleApiAvailability;
import com.tapjoy.mraid.view.MraidView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
class cd {
    static final String a = "adColonyPubServices";
    static final String b = "receivedInForeground";
    static final String c = "gcm_defaultSenderId";
    private static final String d = "savedPushMsgs.ymf";
    private static final String e = "YvolverPushNotMgr";
    private static final int f = 9000;
    private static final int g = 10;
    private static final String l = "adcOpenUrl";
    private static final String m = "adcOpenCatalog";
    private String h;
    private int i = -1;
    private LinkedBlockingQueue<AdColonyPubServicesPushNotification> j = new LinkedBlockingQueue<>(10);
    private String k;

    cd() {
    }

    void a(String str) throws Exception {
        int identifier;
        String packageName;
        Exception e2 = null;
        if (bv.aN().l() == null || (packageName = bv.aN().l().getPackageName()) == null) {
            identifier = -1;
        } else {
            if (str == null) {
                try {
                    identifier = bv.aN().j().getResources().getIdentifier(c, "string", packageName);
                } catch (Exception e3) {
                    e2 = e3;
                    identifier = -1;
                }
            } else {
                identifier = -1;
            }
            try {
                this.i = bv.aN().l().getPackageManager().getApplicationInfo(packageName, 128).icon;
            } catch (Exception e4) {
                e2 = e4;
            }
        }
        if (this.i <= 0) {
            e2 = new RuntimeException("An error occurred when retrieving app-icon for the default icon in push notifications. Have you called AdColony.configure()?");
        } else if (str != null || identifier != -1) {
            if (str != null) {
                this.h = str;
            } else {
                this.h = bv.aN().j().getString(identifier);
            }
            if (!i()) {
                e2 = new RuntimeException("Google Play services is not available. It is required for AdColony Push Notifications.");
            }
        } else {
            e2 = new bu();
        }
        if (e2 == null) {
            bv.aN().j().startService(new Intent(bv.aN().k(), (Class<?>) AdColonyPubServicesPushRegIdIntentService.class));
        } else {
            bv.aN().aE().a(e2);
            throw e2;
        }
    }

    void a() {
        bw.b(e, "unregisterForPushNotifications", true);
        this.h = null;
        this.i = -1;
        this.j.clear();
        this.k = null;
    }

    boolean a(Intent intent) {
        boolean z = true;
        if (intent.getExtras() == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        AdColonyPubServicesPushNotification adColonyPubServicesPushNotification = new AdColonyPubServicesPushNotification();
        adColonyPubServicesPushNotification.a(System.currentTimeMillis());
        Object obj = extras.get(a);
        if (obj != null) {
            adColonyPubServicesPushNotification.b(true);
            adColonyPubServicesPushNotification.a(obj.toString());
            boolean z2 = extras.getBoolean(b, false);
            adColonyPubServicesPushNotification.a(z2);
            if (!z2) {
                b(adColonyPubServicesPushNotification);
            }
            bw.b(e, "add pending notification", true);
            this.j.add(adColonyPubServicesPushNotification);
            if (bv.aN().w() && bv.aN().j() != null) {
                while (b()) {
                }
            } else {
                w.a(1, "Pubservices is unavailable. Adding push message to pending queue.", false);
            }
        } else {
            adColonyPubServicesPushNotification.b(false);
            HashMap map = new HashMap();
            map.put("notification", extras.toString());
            bv.aN().a("Unable to create push notification", bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) map);
            z = false;
        }
        bv.aN().aE().a(adColonyPubServicesPushNotification);
        return z;
    }

    boolean b() {
        if (this.j.size() == 0) {
            return false;
        }
        try {
            c(this.j.take());
        } catch (InterruptedException e2) {
            bw.a(e, "Error occurred when executing pending notification. ->" + e2.getMessage(), e2);
        }
        return true;
    }

    String c() {
        return this.h;
    }

    int d() {
        return this.i;
    }

    boolean b(String str) {
        e();
        if (this.k != null && str.equals(this.k)) {
            return false;
        }
        bw.b(e, "updating token: " + this.k, true);
        this.k = str;
        return true;
    }

    String e() {
        if (this.k == null) {
            this.k = bv.aN().al();
        }
        return this.k;
    }

    void f() {
        bw.b(e, "onDestroy()", true);
    }

    private boolean i() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int iIsGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(bv.aN().j());
        if (iIsGooglePlayServicesAvailable != 0) {
            if (googleApiAvailability.isUserResolvableError(iIsGooglePlayServicesAvailable)) {
                googleApiAvailability.getErrorDialog(bv.aN().j(), iIsGooglePlayServicesAvailable, f).show();
                return false;
            }
            bw.a("PushManager", "This device is not supported with Google Play Services.", false);
            return false;
        }
        return true;
    }

    private void c(AdColonyPubServicesPushNotification adColonyPubServicesPushNotification) {
        cc.b bVar;
        HashMap<String, Serializable> map;
        a(adColonyPubServicesPushNotification);
        cc ccVarA = adColonyPubServicesPushNotification.a();
        if (ccVarA != null && (bVar = ccVarA.e) != null && (map = bVar.e) != null) {
            if (bVar.d.equalsIgnoreCase(l)) {
                HashMap map2 = new HashMap();
                for (String str : map.keySet()) {
                    map2.put(str, map.get(str));
                }
                bv.aN().i(map2);
                return;
            }
            if (bVar.d.equalsIgnoreCase(m)) {
                bv.aN().j(map);
            }
        }
    }

    void a(AdColonyPubServicesPushNotification adColonyPubServicesPushNotification) {
        if (adColonyPubServicesPushNotification.a().e != null) {
            HashMap map = new HashMap();
            map.put("state", "action_completed");
            map.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, adColonyPubServicesPushNotification.getId());
            map.put("category", "");
            map.put(MraidView.ACTION_KEY, "");
            map.put("payload", adColonyPubServicesPushNotification.getPayload());
            map.put("app_in_foreground", Boolean.valueOf(adColonyPubServicesPushNotification.b()));
            map.put("meta", adColonyPubServicesPushNotification.a().b());
            bv.aN().a("push_notification", map);
        }
    }

    void b(AdColonyPubServicesPushNotification adColonyPubServicesPushNotification) {
        HashMap map = new HashMap();
        map.put("state", "received");
        map.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, adColonyPubServicesPushNotification.getId());
        map.put("category", "");
        map.put(MraidView.ACTION_KEY, "");
        map.put("payload", adColonyPubServicesPushNotification.getPayload());
        map.put("app_in_foreground", Boolean.valueOf(adColonyPubServicesPushNotification.b()));
        map.put("meta", adColonyPubServicesPushNotification.a().b());
        bv.aN().a("push_notification", map);
    }

    void g() {
        bw.b(e, "loadPushesFromDisk", true);
        List<Map<String, Object>> listM = ck.m(d);
        bw.b(e, "pushes saved: " + listM.toString(), true);
        for (Map<String, Object> map : listM) {
            String str = (String) map.get("payload");
            cc ccVar = (cc) map.get("meta");
            String str2 = (String) map.get(TMXConstants.TAG_TILE_ATTRIBUTE_ID);
            bw.b(e, "load pending notification into list", true);
            this.j.add(new AdColonyPubServicesPushNotification(str, ccVar, str2));
        }
    }

    void h() {
        while (this.j.size() > 0) {
            try {
                ArrayList arrayList = new ArrayList();
                while (this.j.size() > 0) {
                    AdColonyPubServicesPushNotification adColonyPubServicesPushNotificationTake = this.j.take();
                    bw.b(e, "saving push to disk ->" + adColonyPubServicesPushNotificationTake, true);
                    HashMap map = new HashMap();
                    map.put("payload", adColonyPubServicesPushNotificationTake.getPayload());
                    map.put("meta", adColonyPubServicesPushNotificationTake.a());
                    map.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, adColonyPubServicesPushNotificationTake.getId());
                    arrayList.add(map);
                }
                ck.a(arrayList, d);
            } catch (InterruptedException e2) {
                bw.a(e, "Interrupted exception occurred while saving notifications from disk", e2);
            }
        }
    }
}
