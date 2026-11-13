package com.adcolony.sdk;

import android.util.Pair;
import com.google.android.gms.plus.PlusShare;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ce {
    static final String A = "redeemed";
    static final String B = "cancelled";
    static final String C = "captured";
    static final String D = "captured_error";
    static final String E = "ser_rew.ymf";
    static int a = -1;
    static int b = 0;
    static int c = 1;
    static int d = 2;
    static int e = 3;
    static int f = 4;
    static int g = 5;
    static String h = "digitalPackages";
    static String i = "digitalRedemptions";
    static String j = "customRewards";
    static String k = "googleIap";
    static String l = "amazonIap";
    static String m = "serverReward";
    static String n = "productId";
    static String o = "consumable";
    static String p = "invisible";
    static String q = "transactionId";
    static int r = -1;
    static int s = 0;
    static int t = 0;
    static int u = 0;
    static int v = 0;
    static int w = 0;
    static int x = 0;
    static final String y = "pending";
    static final String z = "redeeming";
    Queue<Map<String, String>> F = new LinkedList();
    Queue<Map<String, String>> G = new LinkedList();
    volatile ArrayDeque<Map<String, Object>> H = new ArrayDeque<>();

    ce() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String o() {
        return "AdColonyPubServices";
    }

    void a(final String str, final String str2, final boolean z2) {
        bw.b(o(), "CUSTOM: grantRewardImpl: " + bv.aN().aw().a(bn.aG), true);
        if (bv.aN().x()) {
            new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.1
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    synchronized (this) {
                        HashMap map = new HashMap();
                        map.put("reward_name", str);
                        map.put(ce.p, Integer.toString(z2 ? 1 : 0));
                        HashMap map2 = new HashMap();
                        map2.put("rewardKey", str2);
                        ca.c().a(bv.aN().aw().a(bn.aG), (Map<String, String>) map, "customReward", false, (Map<String, Object>) map2, 1, new bk() { // from class: com.adcolony.sdk.ce.1.1
                            @Override // com.adcolony.sdk.bk
                            public void a(bn.a aVar) {
                                a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                            }
                        });
                    }
                }
            };
        }
    }

    void a(final String str) {
        if (cl.b().e()) {
            bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.ce.12
                @Override // java.lang.Runnable
                public void run() {
                    bw.b(ce.o(), "digitalRedemptionComplete callback", true);
                    if (cl.b().c() != null) {
                        cl.b().c().b(true, str);
                    }
                }
            });
        }
    }

    void a() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.17
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                Map<String, Object> mapA;
                String strA = ck.a(bv.aN().l());
                if (strA != null && (mapA = ck.a(strA, bv.aN().l())) != null && mapA.containsKey(ce.i)) {
                    mapA.remove(ce.i);
                    ck.a(mapA, strA, bv.aN().l());
                }
                b();
            }
        };
    }

    void b() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.18
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                String strA = ck.a(bv.aN().l());
                if (strA != null) {
                    Map<String, Object> mapA = ck.a(strA, bv.aN().l());
                    if (mapA != null && mapA.containsKey(ce.k)) {
                        mapA.remove(ce.k);
                    }
                    if (mapA != null && mapA.containsKey(ce.l)) {
                        mapA.remove(ce.l);
                    }
                    ck.a(mapA, strA, bv.aN().l());
                }
                b();
            }
        };
    }

    synchronized boolean a(String str, int i2, String str2, Map<String, Object> map) {
        String string = UUID.randomUUID().toString();
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (map == null) {
            map = new HashMap<>();
        }
        HashMap map2 = new HashMap();
        map2.put("product_id", str);
        map2.put("quantity", Integer.valueOf(i2));
        map2.put("encrypted_payload", str2);
        map2.put("user_params", map);
        map2.put("transaction_id", string);
        this.H.add(map2);
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.19
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bw.b(ce.o(), "Saving server rewards.", true);
                Map<String, Object> mapL = ck.l(ce.E);
                for (Map<String, Object> map3 : ce.this.H) {
                    mapL.put((String) map3.get("transaction_id"), map3);
                }
                ck.a(mapL, ce.E);
                bw.b(ce.o(), "server reward saved successfully", true);
                b();
            }
        };
        return true;
    }

    synchronized void a(final int i2) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.20
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                ce.this.H.clear();
                final JSONArray jSONArray = new JSONArray();
                Map<String, Object> mapL = ck.l(ce.E);
                if (mapL != null) {
                    Iterator<Map.Entry<String, Object>> it = mapL.entrySet().iterator();
                    while (it.hasNext()) {
                        Map<String, Object> map = (Map) it.next().getValue();
                        ce.this.H.add(map);
                        jSONArray.put(ck.e(map));
                    }
                    bw.b(ce.o(), "getServerRewardQueue callback called, queue: " + jSONArray, true);
                    bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.ce.20.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (cl.b().c() != null) {
                                cl.b().c().a(i2, jSONArray);
                            }
                        }
                    });
                }
                b();
            }
        };
    }

    void b(String str) {
        final HashMap map = new HashMap();
        map.put("error_id", str);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.ce.21
            @Override // java.lang.Runnable
            public void run() {
                if (cl.b().c() != null) {
                    cl.b().c().a(ck.a((Map<String, Object>) map));
                }
            }
        });
    }

    synchronized void c(String str) {
        final String str2;
        HashMap map;
        if (!bv.aN().x()) {
            final HashMap map2 = new HashMap();
            map2.put("error_id", "Service unavailable");
            bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.ce.22
                @Override // java.lang.Runnable
                public void run() {
                    if (cl.b().c() != null) {
                        cl.b().c().a(ck.a((Map<String, Object>) map2));
                    }
                }
            });
        } else {
            bw.b(o(), "processing serverRewards", true);
            Map<String, Object> mapL = ck.l(E);
            if (mapL != null) {
                mapL.entrySet().iterator();
                Iterator<Map.Entry<String, Object>> it = mapL.entrySet().iterator();
                str2 = null;
                while (true) {
                    if (!it.hasNext()) {
                        map = null;
                        break;
                    }
                    Map map3 = (Map) it.next().getValue();
                    str2 = (String) map3.get("transaction_id");
                    if (str2.equals(str)) {
                        HashMap map4 = new HashMap();
                        map4.put("transaction_id", str2);
                        map4.put("device_id", bv.aN().au().j());
                        map4.put("encrypted_payload", map3.get("encrypted_payload"));
                        map4.put("user_params", ck.e((Map<String, Object>) map3.get("user_params")).toString());
                        map = map4;
                        break;
                    }
                }
            } else {
                final HashMap map5 = new HashMap();
                map5.put("error_id", "Reward Map is empty");
                bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.ce.23
                    @Override // java.lang.Runnable
                    public void run() {
                        if (cl.b().c() != null) {
                            cl.b().c().a(ck.a((Map<String, Object>) map5));
                        }
                    }
                });
                str2 = null;
                map = null;
            }
            if (map != null) {
                final HashMap map6 = new HashMap();
                map6.put("server_reward", ck.e(map).toString());
                new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.2
                    @Override // com.adcolony.sdk.bp
                    public synchronized void a() {
                        HashMap map7 = new HashMap();
                        map7.put("transactionId", str2);
                        bw.b(ce.o(), "] posting server call: " + bn.aM, true);
                        ca.c().a(bv.aN().aw().a(bn.aM), map6, "serverReward", false, (Map<String, Object>) map7, 1, new bk() { // from class: com.adcolony.sdk.ce.2.1
                            @Override // com.adcolony.sdk.bk
                            public void a(bn.a aVar) {
                                boolean z2 = aVar == bn.a.YVOLVER_ERROR_NONE;
                                if (!z2) {
                                    bv.aN().aB().b("Redemption failure");
                                }
                                a(z2, aVar);
                            }
                        });
                    }
                };
            } else {
                final HashMap map7 = new HashMap();
                map7.put("error_id", "unknown");
                bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.ce.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (cl.b().c() != null) {
                            cl.b().c().a(ck.a((Map<String, Object>) map7));
                        }
                    }
                });
            }
        }
    }

    synchronized void c() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.4
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bw.b(ce.o(), "clearing server reward temp queue", true);
                ce.this.H.clear();
                Map<String, Object> mapL = ck.l(ce.E);
                if (mapL != null) {
                    mapL.entrySet().iterator();
                    Iterator<Map.Entry<String, Object>> it = mapL.entrySet().iterator();
                    while (it.hasNext()) {
                        ce.this.H.add((Map) it.next().getValue());
                    }
                }
                b();
            }
        };
    }

    synchronized void d(final String str) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.5
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                Map<String, Object> mapL = ck.l(ce.E);
                if (mapL != null) {
                    Iterator<Map.Entry<String, Object>> it = mapL.entrySet().iterator();
                    while (it.hasNext()) {
                        if (it.next().getKey().equals(str)) {
                            it.remove();
                        }
                    }
                    ck.a(mapL, ce.E);
                }
                b();
            }
        };
    }

    void a(String str, String str2, String str3, long j2, int i2) {
        boolean z2 = true;
        if (bv.aN().y()) {
            if (str == null || str2 == null || str3 == null) {
                z2 = false;
            } else if (str.isEmpty()) {
                bw.b(o(), "Invalid googleJsonString: empty");
                z2 = false;
            } else if (str2.isEmpty()) {
                bw.b(o(), "Invalid purchaseSignature: empty");
                z2 = false;
            } else if (str3.isEmpty()) {
                bw.b(o(), "Invalid locale: empty");
                z2 = false;
            } else {
                try {
                    HashMap map = new HashMap();
                    JSONObject jSONObject = new JSONObject(str);
                    String string = null;
                    if (jSONObject.has("orderId")) {
                        string = jSONObject.getString("orderId");
                    }
                    String string2 = jSONObject.getString("productId");
                    String string3 = Float.toString(j2 / 1000000.0f);
                    boolean zI = bv.aN().i();
                    map.put("iap_receipt_data", str);
                    map.put("iap_product_id", string2);
                    if (string != null) {
                        map.put("iap_transaction_id", string);
                    }
                    map.put("iap_price_locale", str3);
                    map.put("iap_signature", str2);
                    map.put("iap_price", string3);
                    map.put("iap_service", "Google");
                    map.put("in_game_currency_quantity", Integer.toString(i2));
                    if (zI) {
                        map.put("invisible", Integer.toString(1));
                    } else {
                        map.put("invisible", Integer.toString(0));
                    }
                    bw.b(o(), "payloadSignature: " + ck.a(c(map), bv.aN().g()), true);
                    this.G.add(map);
                    d();
                } catch (JSONException e2) {
                    bw.a(o(), "JSON Error=" + e2.getMessage(), e2);
                    z2 = false;
                }
            }
            if (!z2) {
                String string4 = "";
                try {
                    string4 = new JSONObject(str).getString("productId");
                } catch (JSONException e3) {
                    bw.a(o(), "JSON Error=" + e3, e3);
                }
                bv.aN().aE().a(string4, "null value");
            }
        }
    }

    void a(String str, String str2, String str3, String str4, int i2) {
        boolean zI = bv.aN().i();
        if (bv.aN().y()) {
            a(str, str2, str3, zI, str4, i2);
        }
    }

    void a(String str, String str2, String str3, boolean z2, String str4, int i2) {
        HashMap map = new HashMap();
        map.put("iap_product_id", str);
        map.put("iap_transaction_id", str2);
        map.put("iap_user_id", str3);
        map.put("invisible", Boolean.toString(z2));
        map.put("iap_price", str4);
        map.put("iap_service", "Amazon");
        map.put("in_game_currency_quantity", Integer.toString(i2));
        String strA = ck.a(c(map), bv.aN().g());
        bw.b(o(), "payloadSignature: " + strA, true);
        map.put("payload_signature", strA);
        this.F.add(map);
        e();
        g();
    }

    private static String c(Map<String, String> map) {
        String str = "";
        Iterator it = new TreeSet(map.keySet()).iterator();
        while (true) {
            String str2 = str;
            if (it.hasNext()) {
                String str3 = (String) it.next();
                str = (str2 + str3) + map.get(str3);
            } else {
                bw.b(o(), "sigString: " + str2, true);
                return str2;
            }
        }
    }

    synchronized void d() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.6
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bw.b(ce.o(), "Saving google IAP", true);
                Map<String, Object> mapA = ck.a(ce.k, true);
                for (Map<String, String> map : ce.this.G) {
                    String str = null;
                    try {
                        JSONObject jSONObject = new JSONObject(map.get("iap_receipt_data"));
                        String string = jSONObject.getString("purchaseToken");
                        if (jSONObject.has("orderId")) {
                            string = jSONObject.getString("orderId");
                            try {
                                map.put("iap_transaction_id", string);
                            } catch (JSONException e2) {
                                str = string;
                                e = e2;
                                bw.a(ce.o(), "Caught Exception->" + e.getMessage(), e);
                            }
                        }
                        str = string;
                    } catch (JSONException e3) {
                        e = e3;
                    }
                    map.put("yvolverSignature", bv.aN().g(map.get("iap_receipt_data") + map.get("iap_signature") + str + map.get("iap_product_id") + map.get("invisible") + map.get("iap_price_locale") + map.get("iap_price") + map.get("payload_signature") + map.get("in_game_currency_quantity")));
                    mapA.put(str, map);
                }
                ck.a(ce.k, mapA);
                bw.b(ce.o(), "google IAP saved successfully", true);
                ce.this.f();
                b();
            }
        };
    }

    synchronized void e() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.7
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                Map<String, Object> mapA = ck.a(ce.l, false);
                for (Map<String, String> map : ce.this.F) {
                    map.put("yvolverSignature", bv.aN().g(map.get("iap_product_id") + map.get("iap_transaction_id") + map.get("iap_user_id") + map.get("invisible") + map.get("iap_price") + map.get("payload_signature") + map.get("in_game_currency_quantity")));
                    mapA.put(map.get("iap_transaction_id"), map);
                }
                ck.a(ce.l, mapA);
                b();
            }
        };
    }

    synchronized void f() {
        if (this.G.size() > 0) {
            Map<String, String> mapPoll = this.G.poll();
            if (mapPoll.containsKey("yvolverSignature")) {
                mapPoll.remove("yvolverSignature");
            }
            a(mapPoll);
        }
    }

    synchronized void g() {
        if (this.F.size() > 0) {
            Map<String, String> mapPoll = this.F.poll();
            if (mapPoll.containsKey("yvolverSignature")) {
                mapPoll.remove("yvolverSignature");
            }
            b(mapPoll);
        }
    }

    void a(final Map<String, String> map) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.8
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bw.b(ce.o(), "sending Google IAP=" + map, true);
                if (!bv.aN().x()) {
                    bw.b(ce.o(), "IAP send aborted", true);
                } else {
                    ca.c().a(bv.aN().aw().a(bn.aE), map, "googleIap", false, (Map<String, Object>) null, 1, new bk() { // from class: com.adcolony.sdk.ce.8.1
                        @Override // com.adcolony.sdk.bk
                        public void a(bn.a aVar) {
                            a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                        }
                    });
                }
            }
        };
    }

    void b(final Map<String, String> map) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.9
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                if (!bv.aN().x()) {
                    bw.b(ce.o(), "IAP send aborted", true);
                } else {
                    ca.c().a(bv.aN().aw().a(bn.aE), map, "amazonIap", false, (Map<String, Object>) null, 1, new bk() { // from class: com.adcolony.sdk.ce.9.1
                        @Override // com.adcolony.sdk.bk
                        public void a(bn.a aVar) {
                            a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                        }
                    });
                }
            }
        };
    }

    void e(String str) {
        bw.b(o(), "custom addCustomRewardTransactionToQueue", true);
        boolean zI = bv.aN().i();
        Map<String, Object> mapA = ck.a(j, true);
        String str2 = "rewardCustom" + UUID.randomUUID();
        HashMap map = new HashMap();
        map.put("reward_name", str);
        map.put(p, Boolean.valueOf(zI));
        mapA.put(str2, map);
        ck.a(j, mapA);
        a(str, str2, zI);
    }

    void h() {
        Map<String, Object> mapA = ck.a(j, false);
        if (mapA != null) {
            for (Map.Entry<String, Object> entry : mapA.entrySet()) {
                String key = entry.getKey();
                bw.b(o(), "oldRewards key=" + key, true);
                Map map = (Map) entry.getValue();
                a((String) map.get("reward_name"), key, ((Boolean) map.get(p)).booleanValue());
            }
        }
    }

    void f(String str) {
        if (ck.a(k, false) != null) {
            Map<String, Object> mapA = ck.a(k, false);
            Iterator<Map.Entry<String, Object>> it = mapA.entrySet().iterator();
            while (it.hasNext()) {
                String key = it.next().getKey();
                bw.b(o(), "removeGoogleIAPFromStorage key=" + key, true);
                if (key.equals(str)) {
                    bw.b(o(), "googleIAP removing transaction=" + str, true);
                    it.remove();
                }
            }
            ck.a(k, mapA);
        }
    }

    void g(String str) {
        if (ck.a(l, false) != null) {
            Map<String, Object> mapA = ck.a(l, false);
            Iterator<Map.Entry<String, Object>> it = mapA.entrySet().iterator();
            while (it.hasNext()) {
                String key = it.next().getKey();
                bw.b(o(), "removeAmazon purchaseKey=" + key, true);
                if (key.equals(str)) {
                    bw.b(o(), "removing amazon transaction=" + str, true);
                    it.remove();
                }
            }
            ck.a(l, mapA);
        }
    }

    synchronized void i() {
        bw.b(o(), "checking purchases", true);
        if (bv.aN().x()) {
            bw.b(o(), "can attempt redemption", true);
            new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.10
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    if (ck.a(ce.k, false) == null) {
                        bw.b(ce.o(), "no google purchases were found", true);
                    } else {
                        for (Map.Entry<String, Object> entry : ck.a(ce.k, false).entrySet()) {
                            bw.b(ce.o(), "redemption key=" + entry.getKey(), true);
                            Map<String, String> map = (Map) entry.getValue();
                            String str = map.get("iap_receipt_data");
                            bw.b(ce.o(), "redemption receipt=" + str, true);
                            String str2 = map.get("iap_signature");
                            String str3 = map.get("iap_transaction_id");
                            String str4 = map.get("iap_product_id");
                            if (map.get("yvolverSignature").equals(bv.aN().g(str + str2 + str3 + str4 + map.get("invisible") + map.get("iap_price_locale") + map.get("iap_price") + map.get("payload_signature") + map.get("in_game_currency_quantity")))) {
                                map.remove("yvolverSignature");
                                ce.this.a(map);
                            } else {
                                HashMap map2 = new HashMap();
                                map2.put("reason", "validation after storage retrival failed.");
                                map2.put("transaction", str3);
                                bv.aN().a("invalidIAPSignature", bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) map2);
                            }
                        }
                    }
                    b();
                }
            };
            bw.b(o(), "checkForOldGooglePurchases event posted", true);
        } else {
            bw.b(o(), "could nto attempt endpoint (checkForOlPurchases)", true);
        }
    }

    synchronized void j() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.11
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                if (ck.a(ce.l, false) != null) {
                    for (Map.Entry<String, Object> entry : ck.a(ce.l, false).entrySet()) {
                        bw.b(ce.o(), "checkForOldAmazon key=" + entry.getKey(), true);
                        Map<String, String> map = (Map) entry.getValue();
                        String str = map.get("iap_product_id");
                        String str2 = map.get("iap_transaction_id");
                        String str3 = map.get("iap_user_id");
                        String str4 = map.get("invisible");
                        String str5 = map.get("yvolverSignature");
                        String strG = bv.aN().g(str + str2 + str3 + str4 + map.get("iap_price") + map.get("payload_signature"));
                        bw.b(ce.o(), "checkForOldAmazon savedSignature=" + str5 + ", newSignature=" + strG, true);
                        if (str5.equals(strG)) {
                            map.remove("yvolverSignature");
                            ce.this.b(map);
                        }
                    }
                }
                b();
            }
        };
    }

    void a(final String str, final int i2) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.13
            /* JADX WARN: Code duplicated, block: B:39:0x014b  */
            /* JADX WARN: Code duplicated, block: B:40:0x014d A[PHI: r0
              0x014d: PHI (r0v13 java.util.Map) = (r0v0 java.util.Map), (r0v0 java.util.Map), (r0v16 java.util.Map), (r0v16 java.util.Map) binds: [B:15:0x004d, B:17:0x0055, B:19:0x0086, B:21:0x008e] A[DONT_GENERATE, DONT_INLINE]] */
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                boolean z2;
                int iIntValue;
                Map<String, Object> map;
                Map map2 = null;
                synchronized (this) {
                    bw.b(ce.o(), "startPurchaseDigitalItem called: transactionId = " + (str == null ? "null" : str), true);
                    if (str == null || str.isEmpty()) {
                        z2 = false;
                    } else {
                        int i3 = ce.a;
                        String strA = ck.a(bv.aN().l());
                        if (strA == null) {
                            bw.b(ce.o(), "startPurchaseDigitalItem: fileStore !found", true);
                            iIntValue = i3;
                            map = null;
                        } else {
                            Map<String, Object> mapA = ck.a(strA, bv.aN().l());
                            if (mapA == null || !mapA.containsKey(ce.h)) {
                                map = mapA;
                                iIntValue = i3;
                            } else {
                                map2 = (Map) mapA.get(ce.h);
                                bw.b(ce.o(), "startPurchaseDigitalItem: transactionId=" + str + ", redemptionMap=" + map2, true);
                                if (map2 == null || !map2.containsKey(str)) {
                                    map = mapA;
                                    iIntValue = i3;
                                } else {
                                    map = mapA;
                                    iIntValue = ((Integer) map2.get(str)).intValue();
                                }
                            }
                        }
                        if (map == null) {
                            map = new HashMap<>();
                        }
                        if (map2 == null) {
                            map2 = new HashMap();
                        }
                        if (iIntValue == ce.a) {
                            map2.put(str, Integer.valueOf(ce.b));
                            bw.b(ce.o(), "startPurchaseDigitalItem: transactionId=" + str + ", state = " + map2.get(str), true);
                            map.put(ce.h, map2);
                            ck.a(map, strA, bv.aN().l());
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                    }
                    Boolean.valueOf(z2);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Boolean.valueOf(z2));
                    bw.b(ce.o(), "startPurchaseDigitalItem: params sent back to callback=" + ck.a((List<Object>) arrayList), true);
                    cl.b().a(i2, arrayList);
                    b();
                }
            }
        };
    }

    void a(String str, String str2, int i2) {
        String str3 = null;
        HashMap map = new HashMap();
        map.put("json", str);
        map.put("signature", str2);
        map.put(TJAdUnitConstants.String.CALLBACK_ID, Integer.valueOf(i2));
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("transaction_id");
            JSONArray jSONArray = jSONObject.getJSONArray("items");
            List<Map<String, Object>> listA = a(jSONArray);
            HashMap map2 = new HashMap();
            map2.put("items", listA);
            map2.put("transaction_id", string);
            if (bv.aN().g(ck.a(map2, (String[]) null)).equals(str2)) {
                bv.aN().aB().a(ck.a(jSONArray), string, i2);
            } else {
                str3 = "Error in digital redemption, invalid signature";
            }
        } catch (JSONException e2) {
            str3 = "Error in digital redemption from exception->" + e2.getMessage();
        }
        if (str3 != null) {
            bw.b(o(), str3);
            bv.aN().a(str3, bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) map);
        }
    }

    private List<Map<String, Object>> a(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i2);
            int i3 = jSONObject.getInt("digital_item_id");
            String string = jSONObject.getString("name");
            String string2 = jSONObject.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
            String string3 = jSONObject.getString("product_id");
            String string4 = jSONObject.getString("item_url_thumb");
            String string5 = jSONObject.getString("item_url_large");
            int i4 = jSONObject.getInt("item_type");
            int i5 = jSONObject.getInt("quantity");
            boolean z2 = jSONObject.getBoolean("consumable");
            HashMap map = new HashMap();
            map.put("digital_item_id", Integer.valueOf(i3));
            map.put("name", string);
            map.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, string2);
            map.put("product_id", string3);
            map.put("item_url_thumb", string4);
            map.put("item_url_large", string5);
            map.put("item_type", Integer.valueOf(i4));
            map.put("quantity", Integer.valueOf(i5));
            map.put("consumable", Boolean.valueOf(z2));
            arrayList.add(map);
        }
        return arrayList;
    }

    Boolean b(String str, int i2) {
        Map<String, Object> mapA = ck.a(h, true);
        if (mapA == null) {
            return false;
        }
        mapA.put(str, Integer.valueOf(i2));
        ck.a(h, mapA);
        return true;
    }

    public void a(final List<Object> list, final String str, final int i2) {
        final String[] strArr = {"", "Unknown error granting digital items", "Exception caught during digital redemption process", "Not all items in the digital package could be granted", "Digital redemption delegates not implemented", "Unable to redeem due to service availability"};
        bw.b(o(), "purchaseDigitalItems: " + list, true);
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.14
            /* JADX WARN: Code duplicated, block: B:61:0x01f0  */
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                int i3;
                String str2;
                int iIntValue;
                Map<String, Object> mapA;
                try {
                    String strA = ck.a(bv.aN().l());
                    if (strA == null || (mapA = ck.a(strA, bv.aN().l())) == null || !mapA.containsKey(ce.h)) {
                        iIntValue = -1;
                    } else {
                        Map map = (Map) mapA.get(ce.h);
                        bw.b(ce.o(), "purchaseDigitalItems: transactionId=" + str + ", redemptionMap = " + map, true);
                        if (map == null || !map.containsKey(str)) {
                            iIntValue = -1;
                        } else {
                            iIntValue = ((Integer) map.get(str)).intValue();
                        }
                    }
                    if (iIntValue == ce.b) {
                        bw.b(ce.o(), "Transaction is pending; purchase digital items", true);
                        int i4 = !ce.this.b(str, ce.c).booleanValue() ? 1 : 0;
                        if (bv.aN().m()) {
                            ArrayList<AdColonyPubServicesDigitalItem> arrayList = new ArrayList();
                            for (Object obj : list) {
                                AdColonyPubServicesDigitalItem adColonyPubServicesDigitalItem = new AdColonyPubServicesDigitalItem((Map) obj);
                                if (adColonyPubServicesDigitalItem == null) {
                                    bw.b(ce.o(), "Could not create local digital item from " + obj + ". Digital redemption did not complete.");
                                    bv.aN().a("Could not create local digital item from " + obj.toString(), bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                                    break;
                                }
                                arrayList.add(adColonyPubServicesDigitalItem);
                            }
                            if (list.size() == arrayList.size()) {
                                for (AdColonyPubServicesDigitalItem adColonyPubServicesDigitalItem2 : arrayList) {
                                    bw.b(ce.o(), "Attempting to grant " + adColonyPubServicesDigitalItem2, true);
                                    bv.aN().aE().a(adColonyPubServicesDigitalItem2);
                                }
                                if (ce.this.b(str, ce.d).booleanValue()) {
                                    i3 = i4;
                                    str2 = null;
                                } else {
                                    i3 = 1;
                                    str2 = null;
                                }
                            } else {
                                str2 = "Error in digital redemption, not all items in the digital package could be granted.";
                                i3 = 3;
                            }
                        } else {
                            str2 = "Error in digital redemption, digital redemption delegates not implemented.";
                            i3 = 5;
                        }
                    } else {
                        str2 = "Error in digital redemption, unable to redeem digital items, bad transaction state";
                        i3 = 0;
                    }
                } catch (Exception e2) {
                    String str3 = String.format("Error in digital redemption for transactionId=" + str + ", exception caught during digital redemption process: %s", e2.getMessage());
                    bv.aN().a(e2, str3);
                    i3 = 2;
                    str2 = str3;
                }
                if (str2 != null) {
                    bv.aN().a(str2, bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                    ce.this.b(str, ce.e);
                }
                if (bv.aN().x()) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(Integer.valueOf(i3));
                    cl.b().a(i2, arrayList2);
                    ce.this.a(strArr[i3]);
                    ce.this.k();
                }
            }
        };
    }

    void k() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.15
            /* JADX WARN: Code duplicated, block: B:22:0x00bf A[Catch: all -> 0x00cd, TRY_LEAVE, TryCatch #1 {all -> 0x00cd, blocks: (B:3:0x0001, B:5:0x0019, B:6:0x0024, B:8:0x002a, B:10:0x0047, B:12:0x004b, B:13:0x0050, B:16:0x005f, B:20:0x00b9, B:27:0x00d0, B:22:0x00bf), top: B:31:0x0001, inners: #0 }] */
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                try {
                    bw.b(ce.o(), "finishing redemption transactions", true);
                    final JSONArray jSONArray = new JSONArray();
                    Map<String, Object> mapA = ck.a(ce.h, false);
                    if (mapA != null) {
                        ce.p();
                        for (Map.Entry<String, Object> entry : mapA.entrySet()) {
                            String key = entry.getKey();
                            int iIntValue = ((Integer) entry.getValue()).intValue();
                            ce.c(iIntValue);
                            if (iIntValue == ce.d || iIntValue == ce.e) {
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    jSONObject.put("transaction_id", key);
                                    jSONObject.put("result_code", iIntValue);
                                    jSONArray.put(jSONObject);
                                } catch (JSONException e2) {
                                    bs bsVar = new bs(bn.a.YVOLVER_ERROR_JSON, "JSON error occured while parsing redemption object tranaction id" + key + "exception msg=" + e2.getMessage());
                                    bv.aN().a((Exception) e2, bn.a.YVOLVER_ERROR_JSON, "JSON error occured while parsing redemption object tranaction id" + key + "exception msg=" + e2.getMessage(), true);
                                    a(bsVar);
                                }
                            }
                        }
                        if (jSONArray != null || jSONArray.length() == 0) {
                            bw.b(ce.o(), "finishDigRedemption. No transactions to finish", true);
                            b();
                        } else {
                            new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.15.1
                                @Override // com.adcolony.sdk.bp
                                public synchronized void a() {
                                    HashMap map = new HashMap();
                                    map.put("transactions", jSONArray.toString());
                                    bk bkVar = new bk() { // from class: com.adcolony.sdk.ce.15.1.1
                                        @Override // com.adcolony.sdk.bk
                                        public void a(bn.a aVar) {
                                            a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                                        }
                                    };
                                    bw.b(ce.o(), "finishRedemptionTransactions successful. calling endpoint", true);
                                    ca.c().a(bv.aN().aw().a(bn.aL), (Map<String, String>) map, "digiFinish", true, (Map<String, Object>) null, 1, bkVar);
                                }
                            };
                        }
                    } else if (jSONArray != null) {
                        bw.b(ce.o(), "finishDigRedemption. No transactions to finish", true);
                        b();
                    } else {
                        bw.b(ce.o(), "finishDigRedemption. No transactions to finish", true);
                        b();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void p() {
        r = 0;
        s = 0;
        t = 0;
        u = 0;
        v = 0;
        w = 0;
        x = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(int i2) {
        if (i2 == b) {
            s++;
        } else if (i2 == c) {
            t++;
        } else if (i2 == d) {
            u++;
        } else if (i2 == e) {
            v++;
        } else if (i2 == f) {
            w++;
        } else if (i2 == g) {
            x++;
        }
        r++;
    }

    static HashMap<String, Object> l() {
        Map<String, Object> mapA;
        HashMap map = new HashMap();
        HashMap<String, Object> map2 = new HashMap<>();
        if (s == -1 && (mapA = ck.a(h, false)) != null) {
            p();
            for (Map.Entry<String, Object> entry : mapA.entrySet()) {
                entry.getKey();
                c(((Integer) entry.getValue()).intValue());
            }
        }
        map.put(y, Integer.valueOf(s));
        map.put(z, Integer.valueOf(t));
        map.put(A, Integer.valueOf(u));
        map.put(B, Integer.valueOf(v));
        map.put(C, Integer.valueOf(w));
        map.put(D, Integer.valueOf(x));
        map2.put("digital_redemption_counts", map);
        return map2;
    }

    void a(final List<Pair<String, Boolean>> list) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.ce.16
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                int i2;
                Map<String, Object> mapA = ck.a(ce.h, false);
                if (mapA != null) {
                    Set<Map.Entry<String, Object>> setEntrySet = mapA.entrySet();
                    ce.p();
                    for (Map.Entry<String, Object> entry : setEntrySet) {
                        String key = entry.getKey();
                        int iIntValue = ((Integer) entry.getValue()).intValue();
                        bw.b(ce.o(), "transactionId=" + key, true);
                        int i3 = 0;
                        while (true) {
                            if (i3 >= list.size()) {
                                i2 = iIntValue;
                                break;
                            }
                            Pair pair = (Pair) list.get(i3);
                            String str = (String) pair.first;
                            boolean zBooleanValue = ((Boolean) pair.second).booleanValue();
                            if (!key.equals(str)) {
                                i3++;
                            } else if (zBooleanValue) {
                                mapA.put(key, Integer.valueOf(ce.f));
                                i2 = ce.f;
                                break;
                            } else {
                                mapA.put(key, Integer.valueOf(ce.g));
                                i2 = iIntValue;
                                break;
                            }
                        }
                        ce.c(i2);
                    }
                    ck.a(ce.h, mapA);
                }
                b();
            }
        };
    }
}
