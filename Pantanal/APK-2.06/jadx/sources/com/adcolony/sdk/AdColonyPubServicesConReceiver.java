package com.adcolony.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesConReceiver extends BroadcastReceiver {
    private Runnable a = new Runnable() { // from class: com.adcolony.sdk.AdColonyPubServicesConReceiver.1
        @Override // java.lang.Runnable
        public void run() {
            AdColonyPubServicesConReceiver.this.a();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public String b() {
        return "AdColonyPubServices";
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context arg0, Intent arg1) {
        bw.b(b(), "Action: " + arg1.getAction(), true);
        if (arg1.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (arg1.getBooleanExtra("noConnectivity", false)) {
                bw.b(b(), "Network connection lost.", true);
                if (!bv.aN().I()) {
                    bv.aN().u();
                }
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) arg0.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    if (activeNetworkInfo.isConnected()) {
                        bw.b(b(), "Network Connected", true);
                        if (bv.aN().t() != AdColonyPubServices.ServiceAvailability.SERVICE_CONNECTING && bv.aN().t() != AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE) {
                            bv.aN().ar().removeCallbacks(this.a);
                            bv.aN().ar().postDelayed(this.a, 1000L);
                            return;
                        }
                        return;
                    }
                    return;
                }
                bw.b(b(), "No network detected", true);
                return;
            }
            bw.b(b(), "No network detected", true);
        }
    }

    synchronized void a() {
        bw.b(b(), "New connection was called", true);
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.AdColonyPubServicesConReceiver.2
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                if (bv.aN().t() != AdColonyPubServices.ServiceAvailability.SERVICE_CONNECTING && bv.aN().t() != AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE) {
                    bw.b(AdColonyPubServicesConReceiver.this.b(), "New Connection was executed", true);
                    bv.aN().f("InternetAvailable");
                }
            }
        };
    }
}
