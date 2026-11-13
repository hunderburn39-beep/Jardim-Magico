package com.adcolony.sdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.google.android.gms.plus.PlusShare;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class bg {
    private static final String ap = "sentTokenToServer";
    private static final String aq = "push_device_token";
    private static final String ar = "registered_push_app_version";
    String N;
    String P;
    String Q;
    String R;
    ArrayList<String> U;
    ArrayList<String> V;
    ArrayList<String> W;
    ArrayList<String> X;
    Map<String, Object> Y;
    Map<String, Object> Z;
    Handler a;
    Map<String, Object> ac;
    SharedPreferences af;
    SharedPreferences.Editor ag;
    AdColonyPubServices.ServiceAvailability ai;
    AdColonyPubServicesVIPInformation aj;
    AdColonyPubServicesConReceiver ak;
    Thread.UncaughtExceptionHandler am;
    Handler an;
    HandlerThread ao;
    Handler b;
    Handler c;
    Handler d;
    Handler e;
    HandlerThread f;
    HandlerThread g;
    HandlerThread h;
    HandlerThread i;
    cd k;
    be l;
    bm m;
    cg n;
    cb o;
    ce p;
    cj q;
    br r;
    cf s;
    bl t;
    YvolverDeepLinkManager v;
    boolean w;
    boolean x;
    by j = new by();
    bq u = new bq(this.j);
    boolean y = false;
    boolean z = false;
    boolean A = false;
    boolean B = false;
    boolean C = false;
    boolean D = false;
    boolean E = false;
    int F = 1;
    int G = 0;
    int H = 0;
    int I = 1;
    int J = 2;
    int K = 3;
    int L = 4;
    long M = 0;
    String O = "";
    String S = bn.aB;
    final String T = "miscPrefs";
    Map<String, Object> aa = null;
    Map<String, Object> ab = null;
    Activity ad = null;
    Context ae = null;
    bo ah = null;
    bd al = null;
    private Thread.UncaughtExceptionHandler as = new a();

    abstract String a();

    abstract void a(String str);

    abstract void a(Map<String, Object> map);

    abstract ArrayList<String> b();

    abstract void b(String str);

    abstract void c();

    abstract int d();

    abstract boolean e();

    bg() {
    }

    void f() {
        bw.a((String) null, "Using v3.0.4", false);
        this.Y = new HashMap();
        this.Z = new HashMap();
        this.U = b();
        this.j.a(this.u);
        this.l = new be();
        this.d = new Handler(Looper.getMainLooper());
        this.am = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this.as);
        this.f = new HandlerThread("_dbThread");
        this.f.start();
        this.a = new Handler(this.f.getLooper());
        this.g = new HandlerThread("_netThread");
        this.g.start();
        this.b = new Handler(this.g.getLooper());
        this.h = new HandlerThread("_statsThread");
        this.h.start();
        this.c = new Handler(this.h.getLooper());
        this.i = new HandlerThread("_deviceHandlerThread");
        this.i.start();
        this.e = new Handler(this.i.getLooper());
        this.ao = new HandlerThread("adIdThread");
        this.ao.start();
        this.an = new Handler(this.ao.getLooper());
        this.m = new bm();
        this.n = new cg();
        this.o = new cb();
        this.p = new ce();
        this.q = new cj();
        this.r = new br();
        this.s = new cf();
        this.t = new bl();
        this.k = new cd();
        this.v = new YvolverDeepLinkManager();
        this.al = new bd();
        this.s.b();
    }

    private class a implements Thread.UncaughtExceptionHandler {
        public a() {
            bw.b(bg.this.a(), "Installed exception handler", true);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable ex) {
            bg.this.a(new Exception(ex), bn.a.YVOLVER_ERROR_UNCAUGHT_EXCEPTION, "Uncaught exception", true);
            bg.this.am.uncaughtException(thread, ex);
        }
    }

    String g() {
        if (this.Q != null) {
            return c(this.Q);
        }
        return null;
    }

    ArrayList<String> h() {
        return this.U;
    }

    String c(String str) {
        return u(str);
    }

    private String u(String str) {
        if (str != null && str != "") {
            try {
                char[] charArray = str.toCharArray();
                int length = str.length();
                char c = charArray[length >> 1];
                for (int i = length >> 1; i < length - 1; i++) {
                    charArray[i] = charArray[i + 1];
                }
                String str2 = new String(Arrays.copyOfRange(charArray, 0, length - 1));
                int iA = ck.a(c);
                ArrayList<String> arrayListA = ch.a(str2);
                if (iA >= 0 && iA < arrayListA.size()) {
                    return ck.e(arrayListA.get(iA));
                }
            } catch (Exception e) {
                bw.a(a(), "Exception->" + e.getMessage(), e);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(boolean z) {
        if (z) {
            this.Q = null;
        }
    }

    boolean i() {
        return this.ai == AdColonyPubServices.ServiceAvailability.SERVICE_INVISIBLE;
    }

    Activity j() {
        return this.ad;
    }

    void a(Activity activity) {
        this.ad = activity;
    }

    Context k() {
        return this.ad;
    }

    Context l() {
        return this.ae;
    }

    synchronized void b(Activity activity) {
        bw.b(a(), "SetActivity called: " + activity, true);
        if (activity != null) {
            this.ad = activity;
            if (this.ae == null) {
                this.ae = this.ad.getApplicationContext();
            }
            if (this.al == null) {
                this.al = new bd();
            }
            if (!this.E) {
                activity.getApplication().registerActivityLifecycleCallbacks(this.al);
                this.E = true;
            }
            if (this.ah == null) {
                this.ah = new bo(l());
            }
            new bp(this.u) { // from class: com.adcolony.sdk.bg.1
                @Override // com.adcolony.sdk.bp
                public void a() {
                    b();
                }
            };
            this.j.a();
        }
    }

    boolean m() {
        bw.b(a(), "should Send " + (!i()), true);
        return !i();
    }

    private String b(int i) {
        String str = bn.ax;
        if (i == this.H) {
            return bn.ax;
        }
        if (i == this.I) {
            return bn.ay;
        }
        if (i == this.J) {
            return bn.az;
        }
        if (i == this.K) {
            return bn.aA;
        }
        if (i == this.L) {
            return bn.aB;
        }
        return str;
    }

    void n() {
        this.z = true;
        if (this.q.n().booleanValue() || this.q.o().booleanValue()) {
            this.q.g();
        }
    }

    void o() {
        this.z = false;
        this.q.j();
    }

    boolean p() {
        return this.z;
    }

    void a(ArrayList<Object> arrayList) {
        if (arrayList == null) {
            bw.b("IN-APP-MSG-QUEUE", "handleNotifications params==null", true);
            return;
        }
        bw.b("IN-APP-MSG-QUEUE", "handleNotifications: notifications.size()==" + arrayList.size(), true);
        Iterator<Object> it = arrayList.iterator();
        while (it.hasNext()) {
            this.q.a((Map<String, Object>) it.next());
        }
    }

    boolean b(ArrayList<String> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(arrayList.get(i));
        }
        if (arrayList2.isEmpty()) {
            return false;
        }
        Collections.sort(arrayList2, new Comparator<String>() { // from class: com.adcolony.sdk.bg.12
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(String str, String str2) {
                return str.compareToIgnoreCase(str2);
            }
        });
        if (this.V == null || !this.V.equals(arrayList2)) {
            this.V = arrayList2;
            bw.b(a(), " nonConsumables=" + this.V, true);
        }
        return true;
    }

    private String aN() {
        if (!this.S.equals(bn.aB)) {
            this.S += "/api/public/app/config";
        }
        bw.b(a(), "devUrl:" + this.S, true);
        return this.S;
    }

    private String c(int i) {
        return i == this.L ? aN() : b(i);
    }

    private bp a(bp bpVar, Map<String, Object> map) {
        int iE = e(map);
        final String strD = d(map);
        final String str = c(iE) + "/" + strD;
        return new bp(this.u) { // from class: com.adcolony.sdk.bg.15
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bg.this.B = false;
                bg.this.C = false;
                ca.c().a(strD, str, null, "config", false, null, 0, new bk() { // from class: com.adcolony.sdk.bg.15.1
                    @Override // com.adcolony.sdk.bk
                    public void a(bn.a aVar) {
                        bg.this.B = aVar == bn.a.YVOLVER_ERROR_NONE;
                        a(bg.this.B, aVar);
                    }
                });
            }
        };
    }

    synchronized boolean q() {
        return this.C;
    }

    private bp a(bp bpVar, final Map<String, Object> map, final String str) {
        return new bp(this.u, bpVar) { // from class: com.adcolony.sdk.bg.16
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bg.this.C = false;
                if (!i()) {
                    if (j().h()) {
                        d();
                    } else {
                        a(j().c());
                    }
                }
                if (bg.this.B) {
                    if (bg.this.Q == null) {
                        bw.b(bg.this.a(), "Invalid API key", false);
                    } else {
                        HashMap map2 = new HashMap();
                        map2.put("data", bg.this.ah.o());
                        map2.put("reason", str);
                        if (bg.this.P != null) {
                            map2.put("s2s_session_token", bg.this.P);
                        }
                        bg.this.l.b();
                        map2.put("asset_signatures", bg.this.l.e().toString());
                        ca.c().a(bg.this.d(map), bg.this.m.a(bn.aD), map2, "init", false, null, 1, new bk() { // from class: com.adcolony.sdk.bg.16.1
                            @Override // com.adcolony.sdk.bk
                            public void a(bn.a aVar) {
                                bg.this.C = aVar == bn.a.YVOLVER_ERROR_NONE;
                                a(bg.this.C, aVar);
                            }
                        });
                    }
                }
            }
        };
    }

    synchronized void b(final Map<String, Object> map) {
        bw.b(a(), "Yvolver configure called.", true);
        new bp(this.u) { // from class: com.adcolony.sdk.bg.17
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bg.this.D = true;
                bg.this.c(map);
                bg.this.f("SetApiKey");
                b();
            }
        };
    }

    synchronized void c(final Map<String, Object> map) {
        bw.b(a(), "Yvolver setInitParams called", true);
        new bp(this.u) { // from class: com.adcolony.sdk.bg.18
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                if (map != null) {
                    if (map.containsKey("yv_h") && map.get("yv_h") != null && !map.get("yv_h").equals(null)) {
                        bg.this.S = (String) map.get("yv_h");
                    }
                    bg.this.G = bg.this.H;
                    if (map.containsKey("yv_e") && map.get("yv_e") != null && !map.get("yv_e").equals(null)) {
                        String str = (String) map.get("yv_e");
                        if (str.equals("dev")) {
                            bg.this.G = bg.this.L;
                        } else if (str.equals("staging")) {
                            bg.this.G = bg.this.K;
                        } else if (str.equals("alpha")) {
                            bg.this.G = bg.this.J;
                        } else if (str.equals("swap")) {
                            bg.this.G = bg.this.I;
                        }
                    }
                    if (map.containsKey("yv_apikey") && map.get("yv_apikey") != null && !map.get("yv_apikey").equals(null)) {
                        bg.this.N = (String) map.get("yv_apikey");
                    }
                }
                bg.this.aa = map;
                b();
            }
        };
    }

    void d(final String str) {
        if (x()) {
            new bp(this.u) { // from class: com.adcolony.sdk.bg.19
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    bg.this.c(ck.b(str));
                    bg.this.f("SuperUserCommand");
                }
            };
        }
    }

    synchronized void a(final bt btVar) {
        if (!com.adcolony.sdk.a.m.m.b) {
            this.d.postDelayed(new Runnable() { // from class: com.adcolony.sdk.bg.20
                @Override // java.lang.Runnable
                public void run() {
                    if (com.adcolony.sdk.a.m.m.b) {
                        btVar.a();
                    } else {
                        bg.this.d.postDelayed(this, 1000L);
                    }
                }
            }, 1000L);
        }
    }

    synchronized void a(AdColonyPubServicesCallbacks adColonyPubServicesCallbacks) {
        if (ck.f() && !ap()) {
            try {
                if (!this.t.a.contains(adColonyPubServicesCallbacks)) {
                    this.t.a(adColonyPubServicesCallbacks);
                }
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.addYvolverListener");
            }
        }
    }

    synchronized void b(AdColonyPubServicesCallbacks adColonyPubServicesCallbacks) {
        this.t.a.remove(adColonyPubServicesCallbacks);
    }

    synchronized String r() {
        return this.N;
    }

    String s() {
        return "3.0.4";
    }

    String e(String str) {
        String[] strArr = {"Android", "Model/" + this.ah.e(), "OS/" + this.ah.b(), "App/" + this.ah.a()};
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (String str2 : strArr) {
            sb.append(str2);
            sb2.append(str2);
            sb2.append("; ");
        }
        return "Yvolver/3.0.4 (" + sb2.toString() + g(this.ah.j() + str + "3.0.4" + sb.toString()) + ")";
    }

    void a(final String str, final Map<String, Object> map) {
        new bp(this.u) { // from class: com.adcolony.sdk.bg.21
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bg.this.r.a(str, map);
                b();
            }
        };
    }

    void b(String str, Map<String, Object> map) {
        if (this.x) {
            HashMap map2 = new HashMap();
            map2.put("name", str);
            map2.put("data", map);
            a("developer_event", map2);
        }
    }

    void a(final String str, final bn.b bVar, final boolean z, final Map<String, Object> map) {
        new bp(this.u) { // from class: com.adcolony.sdk.bg.2
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bg.this.r.a(str, bVar, z, map);
                b();
            }
        };
    }

    void a(int i, String str, String str2, boolean z) {
        HashMap map = new HashMap();
        map.put("in_game_currency_quantity", Integer.valueOf(i));
        map.put("iap_transaction_id", str2);
        map.put("iap_product_id", str);
        a(z ? "iapRewardSuccessCalled" : "iapRewardSuccessAttempt", bn.b.YVOLVER_SEVERITY_DEBUG, false, (Map<String, Object>) map);
    }

    void a(int i, String str, String str2, int i2, String str3) {
        HashMap map = new HashMap();
        map.put("in_game_currency_quantity", Integer.valueOf(i));
        map.put("iap_transaction_id", str2);
        map.put("iap_product_id", str);
        map.put("error_code", Integer.valueOf(i2));
        map.put(TJAdUnitConstants.String.VIDEO_ERROR, str3);
        a("iapRewardError", bn.b.YVOLVER_SEVERITY_DEBUG, false, (Map<String, Object>) map);
    }

    AdColonyPubServices.ServiceAvailability t() {
        return this.ai == null ? AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE : this.ai;
    }

    synchronized void a(AdColonyPubServices.ServiceAvailability serviceAvailability) {
        bw.b(a(), "setServiceAvailability (availability: " + this.ai + ", status: " + serviceAvailability + ")", true);
        if (this.ai != serviceAvailability) {
            this.ai = serviceAvailability;
            if (x()) {
                this.q.b();
            }
            if (this.ai == AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE) {
                bv.aN().aq();
                bw.b(a(), "init success trying to execute pending notifications", true);
                if (bv.aN().j() != null) {
                    while (this.k.b()) {
                    }
                }
            }
            this.t.a(serviceAvailability);
        }
    }

    void u() {
        if (z()) {
            c();
        }
        a(AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
    }

    Map<String, Object> v() {
        HashMap map = new HashMap();
        map.put("apiKey", r());
        map.put("productionMode", Integer.valueOf(this.G));
        map.put("configEndpoint", b(this.G));
        return map;
    }

    String d(Map<String, Object> map) {
        return (String) map.get("apiKey");
    }

    int e(Map<String, Object> map) {
        return ((Integer) map.get("productionMode")).intValue();
    }

    String f(Map<String, Object> map) {
        return (String) map.get("configEndpoint");
    }

    synchronized void f(final String str) {
        bw.b(a(), "startYvolver called", true);
        new bp(this.u) { // from class: com.adcolony.sdk.bg.3
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                if (!com.adcolony.sdk.a.m.m.b) {
                    bv.aN().a(new bt() { // from class: com.adcolony.sdk.bg.3.1
                        @Override // com.adcolony.sdk.bt
                        public void a() {
                            bg.this.f(str);
                        }
                    });
                } else {
                    bg.this.ah.p();
                    bg.this.a(bg.this.N, bg.this.G, str);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(String str, int i, String str2) {
        bw.b(a(), "startYvolver was called (apiKey1: " + str + ", productionMode: " + i + ", configEndpoint: " + b(i) + ", reason: " + str2 + ")", true);
        if (str != null && l() != null) {
            this.n.a(true);
            this.x = true;
            bp bpVar = new bp(this.u) { // from class: com.adcolony.sdk.bg.4
                @Override // com.adcolony.sdk.bp
                public void a() {
                    if (bg.this.ai != AdColonyPubServices.ServiceAvailability.SERVICE_CONNECTING) {
                        bg.this.a(AdColonyPubServices.ServiceAvailability.SERVICE_CONNECTING);
                        bg.this.d(false);
                        b();
                        return;
                    }
                    d();
                }
            };
            HashMap map = new HashMap();
            map.put("apiKey", str);
            map.put("productionMode", Integer.valueOf(i));
            map.put("configEndpoint", b(i));
            bp bpVarA = a(a(bpVar, map), map, str2);
            final HashMap map2 = new HashMap(map);
            new bp(this.u, bpVarA) { // from class: com.adcolony.sdk.bg.5
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    bg.this.b(this, (Map<String, Object>) map2);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(bp bpVar, Map<String, Object> map) {
        bp bpVarJ = bpVar.j();
        bw.b(a(), "startYvolverCleanup was called", true);
        this.N = d(map);
        this.G = e(map);
        if (!bpVarJ.g()) {
            d(false);
            if (bpVarJ.f()) {
                bs bsVarC = bpVarJ.c();
                bw.b(a(), "Initialization failed: " + (bsVarC == null ? null : bsVarC.toString()));
                this.s.f();
            }
            if (t() == AdColonyPubServices.ServiceAvailability.SERVICE_CONNECTING) {
                a(AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
            }
            bpVar.d();
        } else {
            bw.b(a(), "Initialization succeeded", true);
            if (t() == AdColonyPubServices.ServiceAvailability.SERVICE_CONNECTING) {
                a(AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE);
            }
            bpVar.b();
        }
        this.r.b(false);
        this.p.c();
    }

    boolean w() {
        return ck.f() && !ap() && this.ai == AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE;
    }

    boolean x() {
        if (ck.f() && !ap() && ck.l()) {
            return this.ai == AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE || this.ai == AdColonyPubServices.ServiceAvailability.SERVICE_INVISIBLE;
        }
        return false;
    }

    boolean y() {
        return z();
    }

    boolean z() {
        boolean z = false;
        AdColonyPubServices.ServiceAvailability serviceAvailabilityT = t();
        boolean z2 = (serviceAvailabilityT == AdColonyPubServices.ServiceAvailability.SERVICE_BANNED || this.Q == null) ? false : true;
        int iD = this.m.D();
        int iA = this.n.a();
        if (iA >= iD) {
            HashMap map = new HashMap();
            map.put("limit", Integer.valueOf(iD));
            map.put("current_amount", Integer.valueOf(iA));
            this.o.b("overflow_stat", map);
            this.n.a(0L);
        } else {
            z = z2;
        }
        bw.b(a(), "shouldQueueStatCommand(): availability: " + serviceAvailabilityT + ", apiSecret: " + this.Q + ", returning: " + z + ", num stats: " + iA, true);
        return z;
    }

    String A() {
        return g();
    }

    String g(String str) {
        return ck.a(str, A());
    }

    int B() {
        return this.G;
    }

    void a(boolean z) {
        if (ck.f() && !ap()) {
            this.y = z;
        }
    }

    synchronized void h(String str) {
        if (this.P != str && ck.f() && !ap()) {
            try {
                this.P = str;
                bw.b(a(), "Setting session token: ", true);
                a(this.P);
                if (this.x) {
                    f("SetSessionToken");
                }
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.setSessionToken");
            }
        }
    }

    void i(String str) {
        if (ck.f() && !ap()) {
            try {
                if (!str.equals(this.R)) {
                    this.R = str;
                    if (this.x) {
                        f("SetDeviceToken");
                    }
                }
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.setDeviceToken");
            }
        }
    }

    void j(String str) {
        this.O = str;
    }

    void C() {
        if (ck.f() && !ap()) {
            try {
                this.n.a(0L);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.refreshStats");
            }
        }
    }

    boolean a(Map<String, Object> map, String str, String str2) {
        if (!map.containsKey("payload_signature") || map.get("payload_signature") == null) {
            return false;
        }
        String str3 = (String) map.get("payload_signature");
        String strA = ck.a(g(), new HashMap(map), new String[]{"payload_signature"});
        boolean zEquals = strA.equals(str3);
        if (!zEquals) {
            bw.b(a(), ", Invalid signature for payload " + str2 + ", got: " + str3 + ", expected:" + strA, true);
            a("InvalidPayloadSignature->" + str2 + ", got: " + str3 + ", expected: " + strA, bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
        }
        return zEquals;
    }

    void b(Map<String, Object> map, String str, String str2) {
        if (map == null || str == null || str2 == null) {
            String str3 = null;
            if (map == null) {
                str3 = "Response param == null in YvolverImpl.handleCommonResponse";
            } else if (str == null) {
                str3 = "apiKey param == null in YvolverImpl.handleCommonResponse";
            } else if (str2 == null) {
                str3 = "Callback param == null in YvolverImpl.handleCommonResponse";
            }
            if (str3 != null) {
                bv.aN().a(new RuntimeException(str3), "YvolverImpl.handleCommonResponse");
                return;
            }
            return;
        }
        if (map.containsKey(bn.am)) {
            String str4 = (String) map.get(bn.am);
            if (str2 != null && !str2.equals("config")) {
                l(str4);
            }
        }
        if (map.containsKey("timestamp") && map.get("timestamp") != null) {
            ck.a(((Long) map.get("timestamp")).longValue() - ck.g());
        }
        if (map.containsKey("notifications") && m()) {
            bw.b("IN-APP-MSG-QUEUE", "Response contains notifications. About to get and call handleNotifications", true);
            a((ArrayList<Object>) map.get("notifications"));
        }
        if (map.containsKey("config_outdated")) {
            f("ConfigOutdated");
        }
        if (map.containsKey("reset_device_id")) {
            G();
        }
        if (map.containsKey(bn.aj)) {
            bw.b(a(), "" + map.get(bn.aj), true);
            b((ArrayList<String>) map.get(bn.aj));
        }
        if (map.containsKey("experiments")) {
            h((Map<String, Object>) map.get("experiments"));
        }
        if (map.containsKey("device_token")) {
            a(map);
        }
        if (map.containsKey("user_info")) {
            bw.b(a(), "response contains user_info", true);
            a((Map<String, Object>) map.get("user_info"), str);
        }
        if (map.containsKey("digital_items")) {
            d((ArrayList<String>) map.get("digital_items"));
        }
        if (map.containsKey("stats")) {
            ca.c().a((ArrayList<Object>) map.get("stats"));
        }
    }

    boolean D() {
        return w();
    }

    void g(Map<String, ?> map) {
        HashMap map2 = new HashMap();
        if (map != null) {
            map2.put("source_id", map.get("sourceId"));
        }
        a("show_dev_iap", map2);
        cl.b().d();
    }

    ArrayList<String> E() {
        if (!ck.f() || ap()) {
            return null;
        }
        return this.V;
    }

    void c(ArrayList<String> arrayList) {
        this.V = arrayList;
    }

    void F() {
        this.ah.r();
        f("ResetDeviceId");
    }

    void G() {
        cl.b().d();
        this.ah.q();
        f("ResetDeviceId");
    }

    String k(String str) {
        return this.m.a("catalog") + str + "?device_id=" + this.ah.j() + "&consumer_key=" + r() + "&sdk_version=3.0.4";
    }

    void H() {
        if (ck.f() && ap()) {
            new bp(this.u) { // from class: com.adcolony.sdk.bg.6
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    ca.c().a(bg.this.m.a(bn.aK), (Map<String, String>) null, (String) null, false, (Map<String, Object>) null, 1, new bk() { // from class: com.adcolony.sdk.bg.6.1
                        @Override // com.adcolony.sdk.bk
                        public void a(bn.a aVar) {
                            a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                        }
                    });
                }
            };
        }
    }

    void l(String str) {
        bw.b(a(), "handle service availability was called", true);
        if (str != null) {
            if (bn.a().containsKey(str)) {
                a(bn.a().get(str));
                return;
            } else {
                a(AdColonyPubServices.ServiceAvailability.SERVICE_UNKNOWN);
                return;
            }
        }
        a(AdColonyPubServices.ServiceAvailability.SERVICE_UNKNOWN);
    }

    boolean I() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) k().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    void J() {
        if (Build.MANUFACTURER.equalsIgnoreCase("amazon")) {
            this.p.j();
        } else {
            this.p.i();
        }
        this.p.k();
        this.r.a(true);
        this.n.e();
        this.p.h();
        ArrayList arrayList = (ArrayList) this.aa.get("yv_injected_logs");
        if (arrayList != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < arrayList.size()) {
                    this.o.a("loadTest", (Map) arrayList.get(i2));
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    void a(String str, String str2, String str3, long j, int i) {
        if (ck.f() && !ap()) {
            try {
                this.p.a(str, str2, str3, j, i);
                return;
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.grantRewardFromInAppPurchaseGoogle");
                return;
            }
        }
        bw.b(a(), "Unable to grant reward from IAP due to uninitialization or previous error");
    }

    void a(String str, String str2, String str3, String str4, int i) {
        if (ck.f() && !ap()) {
            try {
                this.p.a(str, str2, str3, str4, i);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.grantRewardFromInAppPurchaseAmazon");
            }
        }
    }

    boolean a(String str, int i, String str2, Map<String, Object> map) {
        return this.p.a(str, i, str2, map);
    }

    void a(EnumSet<AdColonyPubServices.NotificationType> enumSet) {
        b(enumSet.contains(AdColonyPubServices.NotificationType.TOAST));
        c(enumSet.contains(AdColonyPubServices.NotificationType.MODAL));
    }

    void b(boolean z) {
        if (ck.f() && !ap()) {
            try {
                boolean andSet = this.q.a.getAndSet(z);
                if (z && !andSet) {
                    this.q.b();
                }
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.allowToasts");
            }
        }
    }

    void c(boolean z) {
        if (ck.f() && !ap()) {
            try {
                boolean andSet = this.q.b.getAndSet(z);
                if (z && !andSet) {
                    this.q.b();
                }
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.allowModals");
            }
        }
    }

    void m(String str) {
        this.Q = str;
    }

    void K() {
        this.n.d();
    }

    HashMap<String, Object> n(String str) {
        if (ck.f() && !ap()) {
            try {
                return this.n.b(str);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.getStat");
            }
        }
        return null;
    }

    long o(String str) throws Exception {
        if (ck.f() && !ap()) {
            try {
                return this.n.a(str);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.getStatValueLong");
            }
        }
        return 0L;
    }

    ArrayList<HashMap<String, Object>> L() {
        if (ck.f() && !ap()) {
            try {
                return this.n.b();
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.getStats");
            }
        }
        return null;
    }

    boolean a(String str, long j) {
        if (ck.f() && !ap()) {
            try {
                return this.n.c(str, j);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.setStat");
            }
        }
        return false;
    }

    boolean b(String str, long j) {
        if (ck.f() && !ap()) {
            try {
                return this.n.b(str, j);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.incrementStat");
            }
        }
        return false;
    }

    boolean c(String str, long j) {
        if (ck.f() && !ap()) {
            try {
                return this.n.a(str, j);
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.decrementStat");
            }
        }
        return false;
    }

    boolean p(String str) {
        return this.n.c(str);
    }

    void M() {
        if (ck.f() && !ap()) {
            try {
                this.n.g();
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.markStartRound");
            }
        }
    }

    void N() {
        if (ck.f() && !ap()) {
            try {
                this.n.h();
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.markEndRound");
            }
        }
    }

    void O() {
        this.af = l().getSharedPreferences("miscPrefs", 0);
        this.ag = this.af.edit();
        this.ag.clear();
        this.ag.commit();
        new File(l().getFilesDir().getPath() + "/shared_prefs/miscPrefs.xml").delete();
    }

    boolean P() {
        this.af = l().getSharedPreferences("miscPrefs", 0);
        return this.af.getBoolean(TJAdUnitConstants.String.ENABLED, true);
    }

    int Q() {
        this.af = l().getSharedPreferences("miscPrefs", 0);
        return this.af.getInt("xPos", 15);
    }

    int R() {
        this.af = l().getSharedPreferences("miscPrefs", 0);
        return this.af.getInt("yPos", 15);
    }

    void a(int i) {
        this.af = l().getSharedPreferences("miscPrefs", 0);
        this.ag = this.af.edit();
        this.ag.putInt("lastMessageId", i);
        this.ag.apply();
    }

    int S() {
        this.af = l().getSharedPreferences("miscPrefs", 0);
        return this.af.getInt("lastMessageId", 0);
    }

    void T() {
        new bp(this.u) { // from class: com.adcolony.sdk.bg.7
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                String strA = ck.a(bv.aN().l());
                if (strA != null) {
                    bg.this.l().getFileStreamPath(strA).delete();
                }
                b();
            }
        };
    }

    void q(String str) {
        CookieSyncManager.createInstance(l());
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(str);
        if (cookie != null) {
            String[] strArrSplit = cookie.split(";");
            for (String str2 : strArrSplit) {
                cookieManager.setCookie(str, str2.split("=")[0].trim() + "=; Expires=Wed, 31 Dec 1979 23:59:59 GMT");
            }
        }
        cookieManager.removeExpiredCookie();
        CookieSyncManager.getInstance().sync();
    }

    void U() {
        q(".facebook.com");
        q("https://.facebook.com");
    }

    void V() {
        this.n.c();
        this.ah.r();
        T();
        this.l.d();
        O();
        this.o.b();
    }

    void W() {
        this.p.b();
        this.p.a();
        this.n.c();
        this.o.c();
    }

    Map<String, Object> X() {
        if (this.ab == null) {
            this.ab = new HashMap();
            this.ab.put("can_show_in_app_purchase_page", Boolean.valueOf(e()));
        }
        this.Z.putAll(this.ab);
        if (this.W != null && !this.W.isEmpty()) {
            this.Z.put("unsupported_digital_item_product_ids", this.W);
        }
        this.Z.put("contentScale", Float.valueOf(ck.a()));
        this.Z.put("densityDPI", Integer.valueOf(ck.d()));
        this.Z.put("screenHeight", Integer.valueOf(ck.b()));
        this.Z.put("screenWidth", Integer.valueOf(ck.c()));
        return this.Z;
    }

    ArrayList<String> Y() {
        if (!ck.f() || ap()) {
            return null;
        }
        try {
            if (this.X == null || this.X.isEmpty()) {
                return null;
            }
            return this.X;
        } catch (Exception e) {
            bv.aN().a(e, "YvolverBaseImpl.getSupportedDigitalItemProductIds");
            return null;
        }
    }

    synchronized AdColonyPubServicesVIPInformation Z() {
        AdColonyPubServicesVIPInformation adColonyPubServicesVIPInformation;
        if (ck.f() && !ap()) {
            try {
                adColonyPubServicesVIPInformation = this.aj;
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.getUserVipStatus");
                adColonyPubServicesVIPInformation = null;
            }
        } else {
            adColonyPubServicesVIPInformation = null;
        }
        return adColonyPubServicesVIPInformation;
    }

    synchronized Map<String, Object> aa() {
        Map<String, Object> map;
        if (ck.f() && !ap()) {
            try {
                map = this.ac;
            } catch (Exception e) {
                bv.aN().a(e, "YvolverBaseImpl.getExperiments");
                map = null;
            }
        } else {
            map = null;
        }
        return map;
    }

    Object r(String str) {
        if (!ck.f() || ap()) {
            return null;
        }
        try {
            if (this.ac == null || !this.ac.containsKey(str)) {
                return null;
            }
            return this.ac.get(str);
        } catch (Exception e) {
            bv.aN().a(e, "YvolverBaseImpl.getExperimentValue");
            return null;
        }
    }

    void h(Map<String, Object> map) {
        this.ac = map;
        ck.a(this.ac, "ex.bin", bv.aN().l());
    }

    synchronized boolean a(Map<String, Object> map, String str) {
        boolean z;
        boolean z2 = false;
        synchronized (this) {
            if (map != null) {
                HashMap map2 = new HashMap();
                map2.putAll(map);
                String str2 = (String) map2.get("signature");
                String strA = ck.a(g(), map2, new String[]{"signature"});
                new HashMap();
                if (strA.equals(str2)) {
                    this.aj = new AdColonyPubServicesVIPInformation(map2);
                    bv.aN().aE().d();
                    z = true;
                } else {
                    String str3 = "Invalid signature for VIP user info, got: " + str2 + ", expected: " + strA;
                    bw.b(a(), str3, true);
                    a(str3, bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                    z = false;
                }
                z2 = z;
            }
        }
        return z2;
    }

    boolean d(ArrayList<String> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next());
                this.X = arrayList2;
            }
        }
        return true;
    }

    void ab() {
        this.d.post(new Runnable() { // from class: com.adcolony.sdk.bg.8
            @Override // java.lang.Runnable
            public void run() {
                bg.this.w = false;
                bw.b(bg.this.a(), "onStop()", true);
                if (Build.VERSION.SDK_INT < 14) {
                    bw.b(bg.this.a(), "Aborting handle restart. OS is too old.");
                    return;
                }
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) bv.aN().l().getSystemService("activity")).getRunningAppProcesses()) {
                    if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equalsIgnoreCase(bv.aN().l().getPackageName())) {
                        bw.b(bg.this.a(), runningAppProcessInfo.processName + " app is still in foreground", true);
                        bg.this.w = true;
                    }
                }
                if (!bg.this.w) {
                    bw.b(bg.this.a(), "app in in background and stopped", true);
                    HashMap map = new HashMap();
                    map.put("state", "STOPPED");
                    bg.this.a("visibility_changed", map);
                    if (bg.this.m.r()) {
                        bg.this.r.a(true);
                    }
                    bg.this.s.c();
                }
            }
        });
    }

    void ac() {
        this.d.post(new Runnable() { // from class: com.adcolony.sdk.bg.9
            @Override // java.lang.Runnable
            public void run() {
                bw.b(bg.this.a(), "onPause()", true);
                if (Build.VERSION.SDK_INT < 14) {
                    bw.b(bg.this.a(), "Aborting handle pause. OS is too old.");
                    return;
                }
                HashMap map = new HashMap();
                map.put("state", "BACKGROUND");
                bg.this.a("visibility_changed", map);
                bg.this.w = false;
                bg.this.q.i();
                try {
                    bg.this.l().unregisterReceiver(bg.this.ak);
                } catch (Exception e) {
                    bw.b(bg.this.a(), "conReceive receiver not registered", true);
                }
            }
        });
    }

    void c(final Activity activity) {
        this.d.post(new Runnable() { // from class: com.adcolony.sdk.bg.10
            @Override // java.lang.Runnable
            public void run() {
                bw.b(bg.this.a(), "onResume()", true);
                if (Build.VERSION.SDK_INT < 14) {
                    bw.b(bg.this.a(), "Aborting handle resume. OS is too old.");
                    return;
                }
                bg.this.b(activity);
                bg.this.w = true;
                bg.this.q.j();
                bg.this.ak = new AdColonyPubServicesConReceiver();
                bg.this.l().registerReceiver(bg.this.ak, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                if (!bg.this.w) {
                    HashMap map = new HashMap();
                    map.put("state", "FOREGROUND");
                    bg.this.a("visibility_changed", map);
                }
                if (bg.this.t() == AdColonyPubServices.ServiceAvailability.SERVICE_AVAILABLE) {
                    bw.b(bg.this.a(), "onResume trying to execute pending Notifications", true);
                    while (bg.this.k.b()) {
                    }
                }
            }
        });
    }

    void ad() {
        this.d.post(new Runnable() { // from class: com.adcolony.sdk.bg.11
            @Override // java.lang.Runnable
            public void run() {
                bw.b(bg.this.a(), "onRestart()", true);
                if (Build.VERSION.SDK_INT < 14) {
                    bw.b(bg.this.a(), "Aborting handle restart. OS is too old.");
                    return;
                }
                HashMap map = new HashMap();
                map.put("state", "LAUNCHED");
                bg.this.a("visibility_changed", map);
                bg.this.s.e();
            }
        });
    }

    void d(Activity activity) {
    }

    void ae() {
        this.q.h();
        aj();
    }

    boolean af() {
        if (bv.aN().ag() && this.q.o().booleanValue()) {
            bw.b(a(), "handleOnBackPressed was called", true);
            this.q.a(ci.a.HARDWARE_BACK_BUTTON);
            return false;
        }
        return false;
    }

    boolean ag() {
        return this.q.n().booleanValue();
    }

    void s(String str) throws Exception {
        this.k.a(str);
    }

    void ah() {
        ai();
        this.k.a();
        this.ah.p();
        f("NewPushToken");
    }

    void t(String str) {
        bw.b(a(), "PUSH TOKEN:" + str, true);
        if (this.k.b(str)) {
            this.af = l().getSharedPreferences("miscPrefs", 0);
            this.ag = this.af.edit();
            this.ag.putString(aq, str);
            this.ag.putBoolean(ap, true);
            try {
                this.ag.putInt(ar, k().getPackageManager().getPackageInfo(k().getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                bw.a(a(), "Did not store appVersion. NameNotFound Exception was thrown->" + e, e);
            }
            this.ag.apply();
            this.ah.p();
            f("NewPushToken");
        }
    }

    void ai() {
        bw.b(a(), "unregistering push-token", true);
        this.ag = this.af.edit();
        this.ag.remove(aq);
        this.ag.putBoolean(ap, false);
        this.ag.apply();
    }

    void aj() {
        this.k.f();
    }

    String ak() {
        return this.k.e();
    }

    String al() {
        this.af = k().getSharedPreferences("miscPrefs", 0);
        return this.af.getString(aq, null);
    }

    int am() {
        this.af = k().getSharedPreferences("miscPrefs", 0);
        return this.af.getInt(ar, -1);
    }

    String an() {
        return this.k.c();
    }

    int ao() throws Exception {
        int iD = this.k.d();
        if (iD == -1) {
            iD = l().getPackageManager().getApplicationInfo(l().getPackageName(), 128).icon;
            if (iD == -1) {
                throw new RuntimeException("AdColony could not retrieve default notification icon.");
            }
        }
        return iD;
    }

    boolean a(Intent intent) {
        try {
            if (b(intent)) {
                return false;
            }
            return c(intent);
        } catch (Exception e) {
            bv.aN().a(e, "YvolverBaseImpl.handleRemoteIntent");
            return false;
        }
    }

    boolean b(Intent intent) {
        boolean z;
        bw.b(a(), "handleDeepLink()", true);
        if (intent.getData() != null) {
            bw.b(a(), "handleDeepLink() isDeepLink", true);
            Uri data = intent.getData();
            if (data.getScheme().startsWith("adc")) {
                String query = data.getQuery() == null ? "" : data.getQuery();
                String host = data.getHost() == null ? "" : data.getHost();
                String path = data.getPath() == null ? "" : data.getPath();
                HashMap map = new HashMap();
                map.put("url", data.toString());
                map.put("decoded_parms", query);
                map.put("host", host);
                map.put("path", path);
                map.put("source_application", "");
                map.put("callback", false);
                a("application_open_url", map);
                z = true;
            } else {
                z = false;
            }
            HashMap map2 = new HashMap();
            for (String str : data.getQueryParameterNames()) {
                String queryParameter = data.getQueryParameter(str);
                if (queryParameter != null && !queryParameter.isEmpty()) {
                    map2.put(str, queryParameter);
                }
            }
            bw.b(a(), "handleDeepLink() callingOnReceiveDeepLink", true);
            this.v.a(data, map2, z);
            return true;
        }
        bw.b(a(), "handleDeepLink !deeplink", true);
        return false;
    }

    boolean c(Intent intent) {
        return this.k.a(intent);
    }

    static int a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    void i(final Map<String, Object> map) {
        as().post(new Runnable() { // from class: com.adcolony.sdk.bg.13
            @Override // java.lang.Runnable
            public void run() {
                Object obj = map.get("url");
                if (obj != null && (obj instanceof String)) {
                    Uri uri = Uri.parse((String) obj);
                    if (uri != null) {
                        HashMap map2 = new HashMap();
                        String string = uri.toString();
                        String query = uri.getQuery() == null ? "" : uri.getQuery();
                        String path = uri.getPath() == null ? "" : uri.getPath();
                        String host = uri.getHost() == null ? "" : uri.getHost();
                        map2.put("url", string);
                        map2.put("decoded_parms", query);
                        map2.put("host", host);
                        map2.put("path", path);
                        map2.put("source_application", "");
                        map2.put("callback", "");
                        bg.this.a("application_open_url", map2);
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW", uri);
                            intent.addFlags(268435457);
                            bg.this.k().startActivity(intent);
                            return;
                        } catch (ActivityNotFoundException e) {
                            bg.this.b(e, "Activity not found when calling startActivity in adcOpenUrl. Exception msg=" + e.getMessage());
                            return;
                        }
                    }
                    bw.c(bg.this.a(), "Error occurred when parsing url string to URI sent from push notification");
                }
            }
        });
    }

    void j(final Map<String, Serializable> map) {
        as().post(new Runnable() { // from class: com.adcolony.sdk.bg.14
            @Override // java.lang.Runnable
            public void run() {
                Serializable serializable = (Serializable) map.get("path");
                if (serializable != null && (serializable instanceof String)) {
                    cl.b().a((String) serializable, false, false);
                }
            }
        });
    }

    boolean ap() {
        if (this.A) {
            bw.b(a(), "AdColonyPubServices previously encountered an exception. Ignoring AdColonyPubServices API calls");
        }
        return this.A;
    }

    void a(bn.a aVar, String str, boolean z, AdColonyPubServices.ServiceAvailability serviceAvailability) {
        bw.a(a(), "Disabling AdColonyPubServices due to error or exception: SDK error; code: " + aVar.ordinal() + ", reason:" + str, new bs(aVar, str), z, true);
        b(serviceAvailability);
    }

    void a(Exception exc, String str, boolean z) {
        a(new bs(exc, str), str, z);
    }

    void a(Exception exc, bn.a aVar, String str, boolean z) {
        a(new bs(exc, aVar, str), str, z);
    }

    void a(bs bsVar, String str, boolean z) {
        bw.b(a(), "SDK exception; message:" + bsVar.getMessage() + ", reason:" + str, bsVar, z, true);
        b(AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
    }

    private void b(AdColonyPubServices.ServiceAvailability serviceAvailability) {
        bv.aN().a(serviceAvailability);
        this.A = true;
    }

    void aq() {
        this.A = false;
    }

    void a(Exception exc, String str) {
        String str2 = "An error occurred in " + str + ". Error=" + exc.getMessage();
        bw.a(a(), str2, new bs(exc, str2), true);
    }

    void b(Exception exc, String str) {
        bw.a(a(), str, new bs(exc, str), true);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0130  */
    void a(Exception exc, boolean z) {
        boolean z2;
        boolean z3;
        if (exc != null) {
            StringWriter stringWriter = new StringWriter();
            exc.printStackTrace(new PrintWriter(stringWriter));
            Map<String, ?> mapA = this.o.a();
            if (mapA == null || mapA.isEmpty()) {
                z2 = false;
            } else {
                z2 = false;
                for (Map.Entry<String, ?> entry : mapA.entrySet()) {
                    Map<String, Object> map = (Map) entry.getValue();
                    if (map.get("type").equals(TJAdUnitConstants.String.VIDEO_ERROR)) {
                        Map map2 = (Map) map.get("data");
                        if (map2.containsKey(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION) && map2.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION).equals(exc.toString())) {
                            if (map2.containsKey("num")) {
                                map2.put("num", Integer.valueOf(((Integer) map2.get("num")).intValue() + 1));
                                map.put("data", map2);
                                this.o.d(entry.getKey(), map);
                                bw.b(a(), "sending exception to server: " + map2, true);
                            }
                            z3 = true;
                        } else {
                            z3 = z2;
                        }
                    } else {
                        z3 = z2;
                    }
                    z2 = z3;
                }
            }
            if (!z2) {
                HashMap map3 = new HashMap();
                map3.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, exc);
                map3.put("stackTrace", stringWriter.toString());
                map3.put("num", 1);
                if (z) {
                    a("exception", map3);
                    bw.b(a(), "logged new fatal exception: " + exc, false);
                } else {
                    a(TJAdUnitConstants.String.VIDEO_ERROR, map3);
                    bw.b(a(), "Logged new non-fatal exception:" + exc);
                }
                bw.b(a(), map3.toString(), true);
            }
        }
        this.r.a(true);
        if (z) {
            u();
            this.s.d();
        }
    }

    Handler ar() {
        return this.b;
    }

    Handler as() {
        return this.d;
    }

    Map<String, Object> at() {
        return this.Y;
    }

    bo au() {
        return this.ah;
    }

    be av() {
        return this.l;
    }

    bm aw() {
        return this.m;
    }

    boolean ax() {
        return this.w;
    }

    cg ay() {
        return this.n;
    }

    Handler az() {
        return this.c;
    }

    br aA() {
        return this.r;
    }

    ce aB() {
        return this.p;
    }

    cj aC() {
        return this.q;
    }

    cf aD() {
        return this.s;
    }

    bl aE() {
        return this.t;
    }

    cb aF() {
        return this.o;
    }

    YvolverDeepLinkManager aG() {
        return this.v;
    }

    void e(ArrayList<String> arrayList) {
        this.U = arrayList;
    }

    long aH() {
        return this.M;
    }

    void a(long j) {
        this.M = j;
    }

    Map<String, Object> aI() {
        return this.aa;
    }

    int aJ() {
        return this.F;
    }

    void aK() {
        this.F++;
    }

    cd aL() {
        return this.k;
    }

    int aM() {
        return this.al.a();
    }
}
