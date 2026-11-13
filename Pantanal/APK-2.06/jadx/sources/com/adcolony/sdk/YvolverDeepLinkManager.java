package com.adcolony.sdk;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class YvolverDeepLinkManager {
    public static final String a = "YvolverDeepLinkManager";
    private List<DeepLink> b = new ArrayList();

    YvolverDeepLinkManager() {
    }

    void a(Uri uri, Map<String, String> map, boolean z) {
        bw.b(a, "onReceiveDeepLink() calling yvolverOnOpenUrl callback", true);
        a(new DeepLink(uri, map, z));
        bv.aN().aE().a(uri, map, z);
    }

    private void a(DeepLink deepLink) {
        synchronized (this.b) {
            this.b.add(deepLink);
        }
    }

    void a(AdColonyPubServicesCallbacks adColonyPubServicesCallbacks) {
        synchronized (this.b) {
            bw.b(a, "flushing queue for calback:" + adColonyPubServicesCallbacks, true);
            for (DeepLink deepLink : this.b) {
                if (deepLink != null && deepLink.a != null) {
                    adColonyPubServicesCallbacks.onOpenUrl(deepLink.a, deepLink.b, deepLink.c);
                }
            }
        }
    }

    public class DeepLink {
        Uri a;
        Map<String, String> b;
        boolean c;

        DeepLink(Uri uri, Map<String, String> params, boolean isAdColony) {
            this.a = uri;
            this.b = params;
            this.c = isAdColony;
        }
    }
}
