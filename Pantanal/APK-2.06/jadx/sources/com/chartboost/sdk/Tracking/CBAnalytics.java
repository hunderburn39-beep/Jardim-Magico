package com.chartboost.sdk.Tracking;

import android.text.TextUtils;
import android.util.Base64;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.d;
import com.chartboost.sdk.Libraries.f;
import com.chartboost.sdk.h;
import com.chartboost.sdk.i;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.google.android.gms.plus.PlusShare;
import java.util.EnumMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CBAnalytics {

    public enum CBIAPPurchaseInfo {
        PRODUCT_ID,
        PRODUCT_TITLE,
        PRODUCT_DESCRIPTION,
        PRODUCT_PRICE,
        PRODUCT_CURRENCY_CODE,
        GOOGLE_PURCHASE_DATA,
        GOOGLE_PURCHASE_SIGNATURE,
        AMAZON_PURCHASE_TOKEN,
        AMAZON_USER_ID
    }

    public enum CBIAPType {
        GOOGLE_PLAY,
        AMAZON
    }

    private CBAnalytics() {
    }

    public enum CBLevelType {
        HIGHEST_LEVEL_REACHED(1),
        CURRENT_AREA(2),
        CHARACTER_LEVEL(3),
        OTHER_SEQUENTIAL(4),
        OTHER_NONSEQUENTIAL(5);

        private final int a;

        CBLevelType(int value) {
            this.a = value;
        }

        public int getLevelType() {
            return this.a;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x004b A[Catch: all -> 0x00a1, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x0013, B:9:0x0021, B:11:0x002f, B:13:0x003d, B:18:0x0054, B:15:0x004b), top: B:23:0x0005 }] */
    public synchronized void trackInAppPurchaseEvent(EnumMap<CBIAPPurchaseInfo, String> map, CBIAPType iapType) {
        if (map == null || iapType == null) {
            CBLogging.b("CBPostInstallTracker", "Null object is passed. Please pass a valid value object");
        } else if (TextUtils.isEmpty(map.get(CBIAPPurchaseInfo.PRODUCT_ID)) || TextUtils.isEmpty(map.get(CBIAPPurchaseInfo.PRODUCT_TITLE)) || TextUtils.isEmpty(map.get(CBIAPPurchaseInfo.PRODUCT_DESCRIPTION)) || TextUtils.isEmpty(map.get(CBIAPPurchaseInfo.PRODUCT_PRICE)) || TextUtils.isEmpty(map.get(CBIAPPurchaseInfo.PRODUCT_CURRENCY_CODE))) {
            CBLogging.b("CBPostInstallTracker", "Null object is passed. Please pass a valid value object");
        } else {
            a(map.get(CBIAPPurchaseInfo.PRODUCT_ID), map.get(CBIAPPurchaseInfo.PRODUCT_TITLE), map.get(CBIAPPurchaseInfo.PRODUCT_DESCRIPTION), map.get(CBIAPPurchaseInfo.PRODUCT_PRICE), map.get(CBIAPPurchaseInfo.PRODUCT_CURRENCY_CODE), map.get(CBIAPPurchaseInfo.GOOGLE_PURCHASE_DATA), map.get(CBIAPPurchaseInfo.GOOGLE_PURCHASE_SIGNATURE), map.get(CBIAPPurchaseInfo.AMAZON_USER_ID), map.get(CBIAPPurchaseInfo.AMAZON_PURCHASE_TOKEN), iapType);
        }
        throw th;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00ba A[Catch: all -> 0x001e, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:9:0x0010, B:11:0x0016, B:16:0x0021, B:18:0x0027, B:20:0x002d, B:22:0x0033, B:24:0x0039, B:28:0x0047, B:30:0x005e, B:35:0x006f, B:37:0x0074, B:39:0x0078, B:41:0x007e, B:44:0x008c, B:46:0x00ba, B:55:0x0109, B:43:0x0084, B:47:0x00c3, B:49:0x00c7, B:51:0x00cd, B:54:0x00dc, B:53:0x00d3, B:33:0x0067, B:26:0x003f), top: B:60:0x0003, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0109 A[Catch: all -> 0x001e, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:9:0x0010, B:11:0x0016, B:16:0x0021, B:18:0x0027, B:20:0x002d, B:22:0x0033, B:24:0x0039, B:28:0x0047, B:30:0x005e, B:35:0x006f, B:37:0x0074, B:39:0x0078, B:41:0x007e, B:44:0x008c, B:46:0x00ba, B:55:0x0109, B:43:0x0084, B:47:0x00c3, B:49:0x00c7, B:51:0x00cd, B:54:0x00dc, B:53:0x00d3, B:33:0x0067, B:26:0x003f), top: B:60:0x0003, inners: #0 }] */
    private static synchronized void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, CBIAPType cBIAPType) {
        if (i.m == null) {
            CBLogging.b("CBPostInstallTracker", "You need call Chartboost.init() before calling any public API's");
        } else if (h.a() == null) {
            CBLogging.b("CBPostInstallTracker", "You need call Chartboost.startWithAppId() before tracking in-app purchases");
        } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5)) {
            CBLogging.b("CBPostInstallTracker", "Null object is passed. Please pass a valid value object");
        } else {
            try {
                Matcher matcher = Pattern.compile("(\\d+\\.\\d+)|(\\d+)").matcher(str4);
                matcher.find();
                String strGroup = matcher.group();
                if (TextUtils.isEmpty(strGroup)) {
                    CBLogging.b("CBPostInstallTracker", "Invalid price object");
                } else {
                    float f = Float.parseFloat(strGroup);
                    JSONObject jSONObjectA = null;
                    if (cBIAPType == CBIAPType.GOOGLE_PLAY) {
                        if (TextUtils.isEmpty(str6) || TextUtils.isEmpty(str7)) {
                            CBLogging.b("CBPostInstallTracker", "Null object is passed for for purchase data or purchase signature");
                        } else {
                            jSONObjectA = d.a(d.a("purchaseData", str6), d.a("purchaseSignature", str7), d.a("type", Integer.valueOf(CBIAPType.GOOGLE_PLAY.ordinal())));
                            if (jSONObjectA == null) {
                                CBLogging.b("CBPostInstallTracker", "Error while parsing the receipt to a JSON Object, ");
                            } else {
                                a(h.a().f, d.a(d.a("localized-title", str2), d.a("localized-description", str3), d.a("price", Float.valueOf(f)), d.a("currency", str5), d.a("productID", str), d.a("receipt", Base64.encodeToString(jSONObjectA.toString().getBytes(), 2))), "iap", cBIAPType);
                            }
                        }
                    } else if (cBIAPType == CBIAPType.AMAZON) {
                        if (TextUtils.isEmpty(str8) || TextUtils.isEmpty(str9)) {
                            CBLogging.b("CBPostInstallTracker", "Null object is passed for for amazon user id or amazon purchase token");
                        } else {
                            jSONObjectA = d.a(d.a("userID", str8), d.a("purchaseToken", str9), d.a("type", Integer.valueOf(CBIAPType.AMAZON.ordinal())));
                            if (jSONObjectA == null) {
                                CBLogging.b("CBPostInstallTracker", "Error while parsing the receipt to a JSON Object, ");
                            } else {
                                a(h.a().f, d.a(d.a("localized-title", str2), d.a("localized-description", str3), d.a("price", Float.valueOf(f)), d.a("currency", str5), d.a("productID", str), d.a("receipt", Base64.encodeToString(jSONObjectA.toString().getBytes(), 2))), "iap", cBIAPType);
                            }
                        }
                    } else if (jSONObjectA == null) {
                        CBLogging.b("CBPostInstallTracker", "Error while parsing the receipt to a JSON Object, ");
                    } else {
                        a(h.a().f, d.a(d.a("localized-title", str2), d.a("localized-description", str3), d.a("price", Float.valueOf(f)), d.a("currency", str5), d.a("productID", str), d.a("receipt", Base64.encodeToString(jSONObjectA.toString().getBytes(), 2))), "iap", cBIAPType);
                    }
                }
            } catch (IllegalStateException e) {
                CBLogging.b("CBPostInstallTracker", "Invalid price object");
            }
        }
    }

    public static synchronized void trackInAppGooglePlayPurchaseEvent(String title, String description, String price, String currency, String productID, String purchaseData, String purchaseSignature) {
        a(productID, title, description, price, currency, purchaseData, purchaseSignature, null, null, CBIAPType.GOOGLE_PLAY);
    }

    public static synchronized void trackInAppAmazonStorePurchaseEvent(String title, String description, String price, String currency, String productID, String userID, String purchaseToken) {
        a(productID, title, description, price, currency, null, null, userID, purchaseToken, CBIAPType.AMAZON);
    }

    public static synchronized void trackLevelInfo(String eventLabel, CBLevelType type, int mainLevel, String description) {
        trackLevelInfo(eventLabel, type, mainLevel, 0, description);
    }

    public static synchronized void trackLevelInfo(String eventLabel, CBLevelType type, int mainLevel, int subLevel, String description) {
        if (TextUtils.isEmpty(eventLabel)) {
            CBLogging.b("CBPostInstallTracker", "Invalid value: event label cannot be empty or null");
        } else if (type == null) {
            CBLogging.b("CBPostInstallTracker", "Invalid value: level type cannot be empty or null, please choose from one of the CBLevelType enum values");
        } else if (mainLevel < 0 || subLevel < 0) {
            CBLogging.b("CBPostInstallTracker", "Invalid value: Level number should be > 0");
        } else if (description.isEmpty()) {
            CBLogging.b("CBPostInstallTracker", "Invalid value: description cannot be empty or null");
        } else {
            JSONObject jSONObjectA = d.a(d.a("event_label", eventLabel), d.a("event_field", Integer.valueOf(type.getLevelType())), d.a("main_level", Integer.valueOf(mainLevel)), d.a("sub_level", Integer.valueOf(subLevel)), d.a(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, description), d.a("timestamp", Long.valueOf(System.currentTimeMillis())), d.a("data_type", "level_info"));
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObjectA);
            a(h.a().f, jSONArray, "tracking");
        }
    }

    private static synchronized void a(ak akVar, JSONObject jSONObject, String str, CBIAPType cBIAPType) {
        aj ajVar = new aj(String.format(Locale.US, "%s%s", "/post-install-event/", str), akVar);
        ajVar.a(str, (Object) jSONObject);
        ajVar.a(f.a(f.a("status", com.chartboost.sdk.Libraries.a.b)));
        ajVar.b(str);
        ajVar.j();
    }

    private static synchronized void a(ak akVar, JSONArray jSONArray, String str) {
        aj ajVar = new aj("/post-install-event/".concat("tracking"), akVar);
        ajVar.a("track_info", jSONArray);
        ajVar.a(f.a(f.a("status", com.chartboost.sdk.Libraries.a.b)));
        ajVar.b(str);
        ajVar.j();
    }
}
