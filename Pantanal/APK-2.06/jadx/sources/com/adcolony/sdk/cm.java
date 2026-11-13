package com.adcolony.sdk;

import android.webkit.WebView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class cm {
    static final String a = "yvolver.js";

    cm() {
    }

    static void a(final cn cnVar, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.1
            @Override // java.lang.Runnable
            public void run() {
                cnVar.c();
                cl.b().a(i, null);
            }
        });
    }

    static void a(final cn cnVar, final String str, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.12
            @Override // java.lang.Runnable
            public void run() {
                bw.b(cm.b(), "Catalog Page:" + str, true);
                cnVar.a("", str);
                cl.b().a(i, null);
            }
        });
    }

    static void a(final String str, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.13
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> mapB = ck.b(str);
                if (mapB != null) {
                    bv.aN().i(mapB);
                }
                cl.b().a(i, null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b() {
        return "AdColonyPubServices";
    }

    static void b(cn cnVar, int i) {
    }

    static void c(cn cnVar, int i) {
        bv.aN().F();
        cl.b().a(i, (List<Object>) null, true);
    }

    static void d(cn cnVar, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.14
            @Override // java.lang.Runnable
            public void run() {
                bv.aN().U();
                cl.b().a(i, null);
            }
        });
    }

    static void b(final cn cnVar, final String str, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.15
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> mapB = ck.b(str);
                if (mapB != null && mapB.containsKey("event") && mapB.containsKey("data")) {
                    bv.aN().a((String) mapB.get("event"), (Map<String, Object>) mapB.get("data"));
                    cm.a(cnVar.a(), i, (String) null);
                }
            }
        });
    }

    static void c(cn cnVar, final String str, int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.16
            @Override // java.lang.Runnable
            public void run() {
                Map<String, Object> mapB = ck.b(str);
                if (mapB != null && mapB.containsKey(bn.aj)) {
                    bv.aN().b((ArrayList<String>) mapB.get(bn.aj));
                }
            }
        });
    }

    static void e(final cn cnVar, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.17
            @Override // java.lang.Runnable
            public void run() {
                bw.b(cm.b(), "Close was called via javascript", true);
                cnVar.b();
                cl.b().a(i, null);
            }
        });
    }

    static void d(cn cnVar, String str, int i) {
        bw.b(b(), "jsLog: " + str, true);
        cl.b().a(i, null);
    }

    static void e(final cn cnVar, final String str, final int i) {
        bw.b(b(), "setCloseButtonParams was called via js", true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.18
            @Override // java.lang.Runnable
            public void run() {
                bw.b(cm.b(), "coordinates: " + str, true);
                Map<String, Object> mapB = ck.b(str);
                if (mapB != null) {
                    cnVar.a(mapB);
                }
                cl.b().a(i, null);
            }
        });
    }

    static void f(cn cnVar, int i) {
        bv.aN().W();
        cl.b().a(i, null);
    }

    static void g(cn cnVar, int i) {
        bv.aN().av().d();
        cl.b().a(i, null);
    }

    static void h(cn cnVar, int i) {
        bv.aN().V();
        cl.b().a(i, null);
    }

    static void i(cn cnVar, final int i) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.19
            @Override // java.lang.Runnable
            public void run() {
                bv.aN().f("SuperUserCommand");
                cl.b().a(i, null);
            }
        });
    }

    static void j(final cn cnVar, final int i) {
        bv.aN().aA().a(true);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.2
            @Override // java.lang.Runnable
            public void run() {
                cm.a(cnVar.a(), i, (String) null);
            }
        });
    }

    static void k(final cn cnVar, final int i) {
        bv.aN().H();
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.3
            @Override // java.lang.Runnable
            public void run() {
                cm.a(cnVar.a(), i, (String) null);
            }
        });
    }

    static void f(final cn cnVar, String str, final int i) {
        bv.aN().b(str);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.4
            @Override // java.lang.Runnable
            public void run() {
                cm.a(cnVar.a(), i, (String) null);
            }
        });
    }

    static void g(final cn cnVar, String str, final int i) {
        bv.aN().d(str);
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.5
            @Override // java.lang.Runnable
            public void run() {
                cm.a(cnVar.a(), i, (String) null);
            }
        });
    }

    static void l(cn cnVar, int i) {
        bv.aN().aB().a(i);
    }

    static void h(cn cnVar, String str, int i) {
        bv.aN().aB().c(str);
        cl.b().a(i, null);
    }

    static void i(cn cnVar, String str, int i) {
        String string = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("transaction_id")) {
                string = jSONObject.getString("transaction_id");
            } else {
                bw.b(b(), "Invalid or empty transaction id (" + (0 == 0 ? "null" : null) + ")", true);
            }
            bw.b(b(), "Calling startPurchaseDigitalItem()", true);
            bv.aN().aB().a(string, i);
        } catch (JSONException e) {
            bv.aN().a(e, "YvolverWebAppInterfaceImpl.startPurchaseDigitalItem (JSON)");
        }
    }

    static void m(final cn cnVar, final int i) {
        final Map<String, Object> mapX = bv.aN().X();
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.6
            @Override // java.lang.Runnable
            public void run() {
                cm.a(cnVar.a(), i, ck.a((Map<String, Object>) mapX));
            }
        });
    }

    static void a(cn cnVar, String str, String str2, int i) {
        bv.aN().aB().a(str, str2, i);
    }

    static void j(final cn cnVar, final String str, final int i) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cm.7
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                String str2 = str;
                Map<String, Object> mapL = ck.l(cm.a);
                if (mapL != null && mapL.get(str2) != null) {
                    JSONObject jSONObjectE = ck.e((Map<String, Object>) mapL.get(str2));
                    JSONArray jSONArray = new JSONArray();
                    jSONArray.put(jSONObjectE);
                    final String string = jSONArray.toString();
                    bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            cm.a(cnVar.a(), i, string);
                        }
                    });
                } else {
                    bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.7.2
                        @Override // java.lang.Runnable
                        public void run() {
                            cm.a(cnVar.a(), i, (String) null);
                        }
                    });
                }
                b();
            }
        };
    }

    static void k(final cn cnVar, final String str, final int i) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cm.8
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                List<Object> listA = ck.a(str);
                String str2 = (String) listA.get(0);
                Map map = (Map) listA.get(1);
                Map<String, Object> mapL = ck.l(cm.a);
                mapL.put(str2, map);
                ck.a(mapL, cm.a);
                bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        cm.a(cnVar.a(), i, (String) null);
                    }
                });
                b();
            }
        };
    }

    static void l(final cn cnVar, final String str, final int i) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cm.9
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                String str2 = str;
                Map<String, Object> mapL = ck.l(cm.a);
                mapL.remove(str2);
                ck.a(mapL, cm.a);
                bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        cm.a(cnVar.a(), i, (String) null);
                    }
                });
                b();
            }
        };
    }

    static void n(final cn cnVar, final int i) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cm.10
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                ck.a(new HashMap(), cm.a);
                b();
            }
        };
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cm.11
            @Override // java.lang.Runnable
            public void run() {
                cm.a(cnVar.a(), i, (String) null);
            }
        });
    }

    public static void a(WebView webView, int i, String str) {
        if (str == null) {
            str = ck.a((Map<String, Object>) new HashMap());
        }
        bw.b(b(), "resultForCallback " + i + " " + str, true);
        ck.a(webView, "javascript:NativeBridge.resultForCallback(" + i + ", " + str + ");");
    }
}
