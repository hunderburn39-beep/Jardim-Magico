package com.adcolony.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.heyzap.sdk.ads.HeyzapAds;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class h implements ADCDownload.Listener {
    static final String a = "026ae9c9824b3e483fa6c71fa88f57ae27816141";
    static final String b = "7bf3a1e7bbd31e612eda3310c2cdb8075c43c6b5";
    String B;
    boolean D;
    boolean E;
    boolean F;
    boolean G;
    private JSONObject H;
    private String J;
    private String K;
    private String L;
    private boolean M;
    private boolean N;
    private boolean O;
    private boolean P;
    private boolean R;
    private int S;
    private Application.ActivityLifecycleCallbacks V;
    i c;
    ab d;
    l e;
    aj f;
    d g;
    k h;
    n i;
    w j;
    am k;
    ao l;
    j m;
    aq n;
    ap o;
    ae p;
    c q;
    ax r;
    AdColonyInterstitial s;
    AdColonyRewardListener t;
    AdColonyAppOptions v;
    z w;
    boolean x;
    z y;
    HashMap<String, AdColonyCustomMessageListener> u = new HashMap<>();
    HashMap<String, AdColonyZone> z = new HashMap<>();
    HashMap<Integer, aw> A = new HashMap<>();
    private String I = "https://adc3-launch.adcolony.com/v4/launch";
    int C = 1;
    private boolean Q = true;
    private int T = 1;
    private final int U = 120;

    h() {
    }

    Activity a() {
        return a.l;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x026e  */
    void a(AdColonyAppOptions adColonyAppOptions, boolean z) {
        boolean z2;
        boolean z3 = true;
        this.F = z;
        this.v = adColonyAppOptions;
        this.d = new ab();
        this.c = new i();
        this.e = new l();
        this.e.a();
        this.f = new aj();
        this.f.a();
        this.g = new d();
        this.g.a();
        this.h = new k();
        this.i = new n();
        this.i.a();
        this.j = new w();
        w wVar = this.j;
        w.a();
        this.k = new am();
        this.k.a();
        this.l = new ao();
        this.l.a();
        this.m = new j();
        this.m.d();
        this.p = new ae();
        this.B = this.p.c();
        if (!z) {
            this.N = new File(this.k.a + a).exists();
            this.O = new File(this.k.a + b).exists();
            if (this.N && this.O) {
                String strB = s.b(s.c(this.k.a + a), "sdk_version");
                j jVar = this.m;
                if (strB.equals("3.0.4")) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            this.M = z2;
            a(this.M);
            if (a.l != null && this.V == null) {
                this.V = new Application.ActivityLifecycleCallbacks() { // from class: com.adcolony.sdk.h.1
                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityResumed(Activity activity) {
                        a.o = true;
                        if (a.l != null && h.this.f.j && (a.l instanceof b) && !((b) a.l).g) {
                            u.d.b("Ignoring onActivityResumed");
                            return;
                        }
                        u.d.b("onActivityResumed() Activity Lifecycle Callback");
                        a.a(activity);
                        if (h.this.w != null) {
                            h.this.w.a(h.this.w.b).b();
                            h.this.w = null;
                        }
                        h.this.E = false;
                        h.this.f.j = true;
                        h.this.f.l = true;
                        h.this.f.r = false;
                        if (!h.this.Q && !h.this.f.m) {
                            h.this.f.a(true);
                        }
                        h.this.h.a();
                        h.this.Q = false;
                        if (w.j == null || w.j.c == null || w.j.c.isShutdown()) {
                            AdColony.a(activity, a.m.v);
                        } else {
                            w.j.a(5L, TimeUnit.SECONDS);
                        }
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityPaused(Activity activity) {
                        a.o = false;
                        h.this.f.j = false;
                        h.this.f.l = true;
                        if (a.m != null) {
                            a.m.m.x();
                        }
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityDestroyed(Activity activity) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityCreated(Activity activity, Bundle bundle) {
                        if (!h.this.f.m) {
                            h.this.f.a(true);
                        }
                        a.a(activity);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    }
                };
                a.l.getApplication().registerActivityLifecycleCallbacks(this.V);
            }
            if (new File(this.k.a + a).exists()) {
                this.H = s.c(this.k.a + a);
                b(this.H);
            }
        }
        this.C++;
        a.a("Module.load", new ac() { // from class: com.adcolony.sdk.h.12
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.c(zVar);
            }
        });
        a.a("Module.unload", new ac() { // from class: com.adcolony.sdk.h.13
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.a(zVar);
            }
        });
        a.a("AdColony.on_configured", new ac() { // from class: com.adcolony.sdk.h.14
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.G = true;
                if (h.this.R) {
                    JSONObject jSONObjectA = s.a();
                    JSONObject jSONObjectA2 = s.a();
                    s.a(jSONObjectA2, TapjoyConstants.TJC_APP_VERSION_NAME, au.a());
                    s.a(jSONObjectA, "app_bundle_info", jSONObjectA2);
                    new z("AdColony.on_update", 1, jSONObjectA).b();
                    h.this.R = false;
                }
                if (w.j != null) {
                    w.j.b(s.b(zVar.b, "app_session_id"));
                }
            }
        });
        a.a("AdColony.get_app_info", new ac() { // from class: com.adcolony.sdk.h.15
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.b(zVar);
            }
        });
        a.a("AdColony.v4vc_reward", new ac() { // from class: com.adcolony.sdk.h.16
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.f(zVar);
            }
        });
        a.a("AdColony.zone_info", new ac() { // from class: com.adcolony.sdk.h.17
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.g(zVar);
            }
        });
        a.a("AdColony.probe_launch_server", new ac() { // from class: com.adcolony.sdk.h.18
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.a(true, true);
            }
        });
        a.a("Crypto.sha1", new ac() { // from class: com.adcolony.sdk.h.19
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "sha1", au.c(s.b(zVar.b, "data")));
                zVar.a(jSONObjectA).b();
            }
        });
        a.a("Crypto.crc32", new ac() { // from class: com.adcolony.sdk.h.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                JSONObject jSONObjectA = s.a();
                s.b(jSONObjectA, "crc32", au.b(s.b(zVar.b, "data")));
                zVar.a(jSONObjectA).b();
            }
        });
        a.a("Crypto.uuid", new ac() { // from class: com.adcolony.sdk.h.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                int iC = s.c(zVar.b, "number");
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "uuids", au.a(iC));
                zVar.a(jSONObjectA).b();
            }
        });
        a.a("Device.query_advertiser_info", new ac() { // from class: com.adcolony.sdk.h.4
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                if (a.l != null) {
                    au.b.execute(new Runnable() { // from class: com.adcolony.sdk.h.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            h.this.a(a.l, zVar);
                        }
                    });
                }
            }
        });
        a.a("AdColony.controller_version", new ac() { // from class: com.adcolony.sdk.h.5
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                h.this.m.g = s.b(zVar.b, "version");
                if (w.j != null) {
                    w.j.a(h.this.m.g);
                }
                u.d.b("Controller version: " + h.this.m.g);
            }
        });
        try {
            int i = (int) (a.l.getPackageManager().getPackageInfo(a.l.getPackageName(), 0).lastUpdateTime / 1000);
            if (new File(this.k.a + "AppVersion").exists()) {
                JSONObject jSONObjectC = s.c(this.k.a + "AppVersion");
                if ((jSONObjectC != null ? s.c(jSONObjectC, "last_update") : i) != i) {
                    this.R = true;
                } else {
                    z3 = false;
                }
            }
            if (z3) {
                JSONObject jSONObjectA = s.a();
                s.b(jSONObjectA, "last_update", i);
                s.h(jSONObjectA, this.k.a + "AppVersion");
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    boolean b() {
        if (this.F || !this.m.c().contains("arm") || ADCNative.nativeNeonSupported()) {
            return true;
        }
        u.g.b("ARM architechture without NEON support. Disabling AdColony.");
        b(true);
        return false;
    }

    boolean a(boolean z) {
        return a(z, false);
    }

    boolean a(boolean z, boolean z2) {
        au.e(a.l.getFilesDir().getAbsolutePath() + "/../");
        u.b.a(">").b(a.l.getFilesDir().getAbsolutePath() + "/../");
        this.m.d = new File(this.k.c() + "/../lib/libImmEndpointWarpJ.so").exists();
        this.P = z2;
        this.M = z;
        if (z && !z2) {
            u.d.b("Loading .so from libs");
            try {
                System.loadLibrary("js");
                System.loadLibrary(HeyzapAds.Network.ADCOLONY);
                this.d.a();
                b();
            } catch (UnsatisfiedLinkError e) {
                u.g.b("Expecting libadcolony.so in libs directory but it was not found.");
                b(true);
                return false;
            }
        }
        d();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        new Thread(new Runnable() { // from class: com.adcolony.sdk.h.6
            @Override // java.lang.Runnable
            public void run() {
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "url", h.this.I);
                s.a(jSONObjectA, "content_type", "application/json");
                s.a(jSONObjectA, "content", h.this.m.a(h.this.m).toString());
                u.b.b("Launch: " + h.this.m.a(h.this.m).toString());
                u.d.a("Saving Launch to ").a(h.this.k.a).b(h.a);
                new ADCDownload(new z("WebServices.post", 0, jSONObjectA), h.this);
            }
        }).start();
    }

    boolean a(JSONObject jSONObject) {
        if (!this.M) {
            u.f.b("Non-standard launch. Downloading new controller.");
            return true;
        }
        if (this.H != null && s.b(s.f(this.H, "controller"), "sha1").equals(s.b(s.f(jSONObject, "controller"), "sha1"))) {
            return false;
        }
        u.f.b("Controller sha1 does not match, downloading new controller.");
        return true;
    }

    void a(z zVar) {
        a(s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
    }

    void b(boolean z) {
        this.F = z;
    }

    void b(z zVar) {
        JSONObject jSONObject = this.v.d;
        s.a(jSONObject, TapjoyConstants.TJC_APP_ID, this.v.a);
        s.a(jSONObject, "zone_ids", this.v.c);
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "options", jSONObject);
        zVar.a(jSONObjectA).b();
    }

    boolean c(final z zVar) {
        if (a.l == null) {
            return false;
        }
        try {
            final int iC = zVar.b.has(TMXConstants.TAG_TILE_ATTRIBUTE_ID) ? s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID) : 0;
            if (iC <= 0) {
                ab abVar = this.d;
                iC = abVar.c;
                abVar.c = iC + 1;
            }
            a(iC);
            boolean zD = s.d(zVar.b, "is_webview");
            final boolean zD2 = s.d(zVar.b, "is_display_module");
            if (zD) {
                a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.h.7
                    @Override // java.lang.Runnable
                    public void run() {
                        Context applicationContext = a.l.getApplicationContext();
                        ab abVar2 = h.this.d;
                        int i = abVar2.c;
                        abVar2.c = i + 1;
                        aw awVar = new aw(applicationContext, i, zD2);
                        awVar.a(true, zVar);
                        h.this.A.put(Integer.valueOf(awVar.a()), awVar);
                    }
                });
            } else {
                final ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
                executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: com.adcolony.sdk.h.8
                    @Override // java.lang.Runnable
                    public void run() {
                        JSONObject jSONObjectF = s.f(zVar.b, TJAdUnitConstants.String.VIDEO_INFO);
                        if (iC == 1 && a.m.v != null) {
                            s.a(jSONObjectF, "options", a.m.v.d);
                        }
                        h.this.d.a(new ADCVMModule(a.l, iC, s.b(zVar.b, "filepath"), jSONObjectF, executorServiceNewSingleThreadExecutor));
                    }
                });
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "success", true);
                s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, iC);
                zVar.a(jSONObjectA).b();
            }
            return true;
        } catch (RuntimeException e) {
            u.h.b("Failed to create AdUnit file://" + s.b(zVar.b, "filepath"));
            u.h.b(e.toString());
            e.printStackTrace();
            return false;
        }
    }

    void d(z zVar) {
        this.w = zVar;
    }

    void e(z zVar) {
        this.y = zVar;
    }

    void c() {
        if (a.m != null && a.m.f != null && a.m.f.m) {
            this.S++;
            this.T = this.T * this.S <= 120 ? this.T * this.S : 120;
            au.a(new Runnable() { // from class: com.adcolony.sdk.h.9
                @Override // java.lang.Runnable
                public void run() {
                    new Handler().postDelayed(new Runnable() { // from class: com.adcolony.sdk.h.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (a.m != null && a.m.f != null && a.m.f.m) {
                                h.this.d();
                            }
                        }
                    }, h.this.T * 1000);
                }
            });
            return;
        }
        u.f.b("Max launch server download attempts hit, or AdColony is no longer active.");
    }

    @Override // com.adcolony.sdk.ADCDownload.Listener
    public void on_download_finished(ADCDownload download, z message, Map<String, List<String>> headers) {
        if (download.a.equals(this.I)) {
            if (download.e) {
                u.b.b("Launch: " + download.b);
                JSONObject jSONObjectA = s.a(download.b);
                j jVar = this.m;
                s.a(jSONObjectA, "sdk_version", "3.0.4");
                if (jSONObjectA != null) {
                    s.h(jSONObjectA, this.k.a + a);
                    if (!c(jSONObjectA)) {
                        if (!this.M) {
                            u.g.b("Incomplete or disabled launch server response. Disabling AdColony until next launch.");
                            b(true);
                            return;
                        }
                        return;
                    }
                    if (a(jSONObjectA)) {
                        u.d.b("Controller missing or out of date. Downloading new controller.");
                        JSONObject jSONObjectA2 = s.a();
                        s.a(jSONObjectA2, "url", this.J);
                        s.a(jSONObjectA2, "filepath", this.k.a + b);
                        new ADCDownload(new z("WebServices.download", 0, jSONObjectA2), this);
                    }
                    this.H = jSONObjectA;
                    return;
                }
                return;
            }
            c();
            return;
        }
        if (download.a.equals(this.J)) {
            if (!a(this.K)) {
                u.e.b("Downloaded controller sha1 does not match expected value, retrying.");
                c();
            } else if (!this.M && !this.P && a.l != null) {
                a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.h.10
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            System.loadLibrary("js");
                            System.loadLibrary(HeyzapAds.Network.ADCOLONY);
                        } catch (UnsatisfiedLinkError e) {
                            h.this.b(true);
                            u.g.b("Expecting libadcolony.so in libs folder but it was not found. Disabling AdColony until next launch.");
                        }
                        h.this.d.a();
                        h.this.b();
                    }
                });
            }
        }
    }

    private boolean a(String str) {
        File file = new File(a.l.getFilesDir().getAbsolutePath() + "/adc3/" + b);
        if (file.exists()) {
            return au.a(str, file);
        }
        return false;
    }

    boolean a(Context context, z zVar) {
        if (context == null) {
            return false;
        }
        AdvertisingIdClient.Info advertisingIdInfo = null;
        try {
            advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        } catch (Exception e) {
            e.printStackTrace();
            if (Build.MANUFACTURER.equals("Amazon")) {
                return false;
            }
            u.e.b("Advertising ID is not available. Collecting Android ID instead of Advertising ID.");
            return false;
        } catch (NoClassDefFoundError e2) {
            u.e.b("Google Play Services ads dependencies are missing. Collecting Android ID instead of Advertising ID.");
            return false;
        } catch (NoSuchMethodError e3) {
            u.e.b("Google Play Services is out of date, please update to GPS 4.0+. Collecting Android ID instead of Advertising ID.");
        }
        if (advertisingIdInfo == null) {
            return false;
        }
        this.m.a = advertisingIdInfo.getId();
        this.m.c = advertisingIdInfo.isLimitAdTrackingEnabled();
        this.m.b = true;
        if (zVar != null) {
            JSONObject jSONObjectA = s.a();
            s.a(jSONObjectA, "advertiser_id", this.m.b());
            s.a(jSONObjectA, "limit_ad_tracking", this.m.f());
            zVar.a(jSONObjectA).b();
        }
        return true;
    }

    void a(AdColonyAppOptions adColonyAppOptions) {
        synchronized (this.g.b) {
            Iterator<Map.Entry<String, AdColonyInterstitial>> it = this.g.b.entrySet().iterator();
            while (it.hasNext()) {
                AdColonyInterstitial value = it.next().getValue();
                value.h = true;
                value.a.onExpiring(value);
            }
            this.g.b.clear();
        }
        this.G = false;
        a(1);
        this.z.clear();
        this.v = adColonyAppOptions;
        this.d.a();
        a(true, true);
    }

    boolean a(int i) {
        if (this.d.a(i) == null) {
            return false;
        }
        if (this.A.containsKey(Integer.valueOf(i))) {
            aw awVar = this.A.get(Integer.valueOf(i));
            if (awVar.b) {
                awVar.loadUrl("about:blank");
                awVar.clearCache(true);
                awVar.removeAllViews();
                awVar.c = true;
            }
            this.A.remove(Integer.valueOf(i));
        }
        if (this.y != null) {
            this.y.b();
            this.y = null;
            this.x = false;
        }
        u.d.a("Destroying module with id = ").b(i);
        return true;
    }

    void b(JSONObject jSONObject) {
        w.i = s.c(s.f(jSONObject, "logging"), "send_level");
    }

    boolean c(JSONObject jSONObject) {
        if (jSONObject == null) {
            u.d.b("Launch response verification failed - response is null or unknown");
            return false;
        }
        try {
            JSONObject jSONObjectF = s.f(jSONObject, "controller");
            this.J = s.b(jSONObjectF, "url");
            this.K = s.b(jSONObjectF, "sha1");
            this.L = s.b(jSONObject, "status");
            b(jSONObject);
        } catch (Exception e) {
            try {
                new File(this.k.a + a).delete();
            } catch (Exception e2) {
            }
        }
        if (this.L.equals("disable")) {
            try {
                new File(this.k.a + b).delete();
            } catch (Exception e3) {
            }
            u.g.b("Launch server response with disabled status. Disabling AdColony until next launch.");
            b(true);
            return false;
        }
        if (!this.J.equals("") && !this.L.equals("")) {
            return true;
        }
        u.g.b("Missing controller status or URL. Disabling AdColony until next launch.");
        return false;
    }

    boolean f(final z zVar) {
        if (this.t == null || a.l == null) {
            return false;
        }
        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.h.11
            @Override // java.lang.Runnable
            public void run() {
                h.this.t.onReward(new AdColonyReward(zVar));
            }
        });
        return true;
    }

    void g(z zVar) {
        AdColonyZone adColonyZone;
        if (this.F) {
            u.f.b("AdColony is disabled. Ignoring zone_info message.");
            return;
        }
        JSONObject jSONObjectF = s.f(zVar.b, "reward");
        String strB = s.b(jSONObjectF, "reward_name");
        int iC = s.c(jSONObjectF, "reward_amount");
        int iC2 = s.c(jSONObjectF, "views_per_reward");
        int iC3 = s.c(jSONObjectF, "views_until_reward");
        String strB2 = s.b(zVar.b, "zone_id");
        int iC4 = s.c(zVar.b, "status");
        int iC5 = s.c(zVar.b, "type");
        String strB3 = s.b(zVar.b, "reward_name_plural");
        String strB4 = s.b(zVar.b, "reward_prompt");
        boolean z = iC4 != 1;
        if (this.z.containsKey(strB2)) {
            adColonyZone = this.z.get(strB2);
        } else {
            adColonyZone = new AdColonyZone(strB2);
            this.z.put(strB2, adColonyZone);
        }
        adColonyZone.r = s.d(zVar.b, "rewarded");
        adColonyZone.h = strB2;
        adColonyZone.i = strB;
        adColonyZone.j = strB3;
        adColonyZone.k = strB4;
        adColonyZone.o = iC2;
        adColonyZone.n = iC3;
        adColonyZone.p = iC;
        adColonyZone.l = iC4;
        adColonyZone.m = iC5;
        adColonyZone.q = z;
    }
}
