package com.tapjoy.internal;

import android.app.Activity;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class fi {
    public static void a(boolean z) {
        boolean z2;
        fu fuVarA = fu.a();
        if (fr.a != z) {
            fr.a = z;
            if (z) {
                fr.a("The debug mode has been enabled");
            } else {
                fr.a("The debug mode has been disabled");
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 || !z || !fuVarA.k) {
            return;
        }
        fuVarA.i.a();
    }

    public static void a(Activity activity) {
        fu fuVarA = fu.a();
        if (!fr.a(activity, "onActivityStart: The given activity was null")) {
            return;
        }
        fr.c("onActivityStart");
        d.a(activity.getApplication());
        d.b(activity);
        if (!fuVarA.b("onActivityStart") || !fuVarA.e()) {
            return;
        }
        fo.b(activity);
    }

    public static void b(Activity activity) {
        fu fuVarA = fu.a();
        if (!fr.a(activity, "onActivityStop: The given activity was null")) {
            return;
        }
        fr.c("onActivityStop");
        d.c(activity);
        if (!fuVarA.b("onActivityStop") || d.b()) {
            return;
        }
        fuVarA.h.a();
    }

    public static void a() {
        fu fuVarA = fu.a();
        if (!fuVarA.b("startSession") || !fuVarA.e()) {
            return;
        }
        fo.b(null);
    }

    public static void b() {
        fu fuVarA = fu.a();
        if (!fuVarA.b("endSession")) {
            return;
        }
        fuVarA.h.a();
    }

    public static void a(String str, String str2, String str3, String str4, long j) {
        fu fuVarA = fu.a();
        if (!fuVarA.c("trackEvent") || !fr.a(str2, "trackEvent: name was null")) {
            return;
        }
        LinkedHashMap linkedHashMapB = null;
        if (j != 0) {
            linkedHashMapB = cv.b();
            linkedHashMapB.put(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, Long.valueOf(j));
        }
        fuVarA.g.a(str, str2, str3, str4, linkedHashMapB);
        fr.a("trackEvent category:{}, name:{}, p1:{}, p2:{}, values:{} called", str, str2, str3, str4, linkedHashMapB);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, long j, String str6, long j2, String str7, long j3) {
        fu fuVarA = fu.a();
        if (!fuVarA.c("trackEvent") || !fr.a(str2, "trackEvent: name was null")) {
            return;
        }
        LinkedHashMap linkedHashMapB = cv.b();
        if (str5 != null && j != 0) {
            linkedHashMapB.put(str5, Long.valueOf(j));
        }
        if (str6 != null && j2 != 0) {
            linkedHashMapB.put(str6, Long.valueOf(j2));
        }
        if (str7 != null && j3 != 0) {
            linkedHashMapB.put(str7, Long.valueOf(j3));
        }
        if (linkedHashMapB.isEmpty()) {
            linkedHashMapB = null;
        }
        fuVarA.g.a(str, str2, str3, str4, linkedHashMapB);
        fr.a("trackEvent category:{}, name:{}, p1:{}, p2:{}, values:{} called", str, str2, str3, str4, linkedHashMapB);
    }

    public static void a(String str, String str2, String str3, String str4) {
        fu fuVarA = fu.a();
        if (fuVarA.b("trackPurchase")) {
            try {
                g gVar = new g(str);
                String strB = fp.b(gVar.a);
                String strB2 = fp.b(gVar.f);
                if (strB == null || strB2 == null) {
                    fr.a("trackPurchase", "skuDetails", "insufficient fields");
                    return;
                }
                if (strB2.length() != 3) {
                    fr.a("trackPurchase", "skuDetails", "invalid currency code");
                    return;
                }
                String strB3 = fp.b(str2);
                String strB4 = fp.b(str3);
                if (strB3 != null) {
                    if (strB4 != null) {
                        try {
                            h hVar = new h(strB3);
                            if (cr.c(hVar.a) || cr.c(hVar.b) || cr.c(hVar.c) || hVar.d == 0) {
                                fr.a("trackPurchase", "purchaseData", "insufficient fields");
                            }
                        } catch (IOException e) {
                            fr.a("trackPurchase", "purchaseData", "invalid PurchaseData JSON");
                        }
                    } else {
                        fr.a("trackPurchase", "dataSignature", "is null, skipping purchase validation");
                    }
                } else if (strB4 != null) {
                    fr.a("trackPurchase", "purchaseData", "is null. skipping purchase validation");
                }
                fuVarA.g.a(strB, strB2.toUpperCase(Locale.US), gVar.g / 1000000.0d, strB3, strB4, fp.b(str4));
                if (strB3 != null && strB4 != null) {
                    fr.a("trackPurchase with purchaseData called");
                } else {
                    fr.a("trackPurchase without purchaseData called");
                }
            } catch (IOException e2) {
                fr.a("trackPurchase", "skuDetails", "invalid SkuDetails JSON");
            }
        }
    }
}
