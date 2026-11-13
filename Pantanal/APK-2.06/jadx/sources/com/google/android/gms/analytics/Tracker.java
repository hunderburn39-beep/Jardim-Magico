package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.internal.fq;
import com.tapjoy.TJAdUnitConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class Tracker {
    private final TrackerHandler vM;
    private final Map<String, String> vN;
    private ad vO;
    private final h vP;
    private final ae vQ;
    private final g vR;
    private boolean vS;
    private a vT;
    private aj vU;

    private class a implements GoogleAnalytics.a {
        private i tg;
        private Timer vV;
        private TimerTask vW;
        private boolean vX = false;
        private boolean vY = false;
        private int vZ = 0;
        private long wa = -1;
        private boolean wb = false;
        private long wc;

        /* JADX INFO: renamed from: com.google.android.gms.analytics.Tracker$a$a, reason: collision with other inner class name */
        private class C0029a extends TimerTask {
            private C0029a() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                a.this.vX = false;
            }
        }

        public a() {
            this.tg = new i() { // from class: com.google.android.gms.analytics.Tracker.a.1
                @Override // com.google.android.gms.analytics.i
                public long currentTimeMillis() {
                    return System.currentTimeMillis();
                }
            };
        }

        private void df() {
            GoogleAnalytics googleAnalyticsCM = GoogleAnalytics.cM();
            if (googleAnalyticsCM == null) {
                aa.w("GoogleAnalytics isn't initialized for the Tracker!");
            } else if (this.wa >= 0 || this.vY) {
                googleAnalyticsCM.a(Tracker.this.vT);
            } else {
                googleAnalyticsCM.b(Tracker.this.vT);
            }
        }

        private synchronized void dg() {
            if (this.vV != null) {
                this.vV.cancel();
                this.vV = null;
            }
        }

        public long dc() {
            return this.wa;
        }

        public boolean dd() {
            return this.vY;
        }

        public boolean de() {
            boolean z = this.wb;
            this.wb = false;
            return z;
        }

        boolean dh() {
            return this.wa == 0 || (this.wa > 0 && this.tg.currentTimeMillis() > this.wc + this.wa);
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.vY = enabled;
            df();
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.a
        public void f(Activity activity) {
            u.cy().a(u.a.EASY_TRACKER_ACTIVITY_START);
            dg();
            if (!this.vX && this.vZ == 0 && dh()) {
                this.wb = true;
            }
            this.vX = true;
            this.vZ++;
            if (this.vY) {
                HashMap map = new HashMap();
                map.put("&t", "appview");
                u.cy().t(true);
                Tracker.this.set("&cd", Tracker.this.vU != null ? Tracker.this.vU.h(activity) : activity.getClass().getCanonicalName());
                Tracker.this.send(map);
                u.cy().t(false);
            }
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.a
        public void g(Activity activity) {
            u.cy().a(u.a.EASY_TRACKER_ACTIVITY_STOP);
            this.vZ--;
            this.vZ = Math.max(0, this.vZ);
            this.wc = this.tg.currentTimeMillis();
            if (this.vZ == 0) {
                dg();
                this.vW = new C0029a();
                this.vV = new Timer("waitForActivityStart");
                this.vV.schedule(this.vW, 1000L);
            }
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.wa = sessionTimeout;
            df();
        }
    }

    Tracker(String trackingId, TrackerHandler handler) {
        this(trackingId, handler, h.cb(), ae.cZ(), g.ca(), new z("tracking"));
    }

    Tracker(String trackingId, TrackerHandler handler, h clientIdDefaultProvider, ae screenResolutionDefaultProvider, g appFieldsDefaultProvider, ad rateLimiter) {
        this.vN = new HashMap();
        this.vM = handler;
        if (trackingId != null) {
            this.vN.put("&tid", trackingId);
        }
        this.vN.put("useSecure", "1");
        this.vP = clientIdDefaultProvider;
        this.vQ = screenResolutionDefaultProvider;
        this.vR = appFieldsDefaultProvider;
        this.vO = rateLimiter;
        this.vT = new a();
    }

    void a(Context context, aj ajVar) {
        aa.y("Loading Tracker config values.");
        this.vU = ajVar;
        if (this.vU.dj()) {
            String strDk = this.vU.dk();
            set("&tid", strDk);
            aa.y("[Tracker] trackingId loaded: " + strDk);
        }
        if (this.vU.dl()) {
            String string = Double.toString(this.vU.dm());
            set("&sf", string);
            aa.y("[Tracker] sample frequency loaded: " + string);
        }
        if (this.vU.dn()) {
            setSessionTimeout(this.vU.getSessionTimeout());
            aa.y("[Tracker] session timeout loaded: " + dc());
        }
        if (this.vU.m1do()) {
            enableAutoActivityTracking(this.vU.dp());
            aa.y("[Tracker] auto activity tracking loaded: " + dd());
        }
        if (this.vU.dq()) {
            if (this.vU.dr()) {
                set("&aip", "1");
                aa.y("[Tracker] anonymize ip loaded: true");
            }
            aa.y("[Tracker] anonymize ip loaded: false");
        }
        this.vS = this.vU.ds();
        if (this.vU.ds()) {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), context));
            aa.y("[Tracker] report uncaught exceptions loaded: " + this.vS);
        }
    }

    long dc() {
        return this.vT.dc();
    }

    boolean dd() {
        return this.vT.dd();
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        if (!enabled) {
            this.vN.put("&ate", null);
            this.vN.put("&adid", null);
            return;
        }
        if (this.vN.containsKey("&ate")) {
            this.vN.remove("&ate");
        }
        if (this.vN.containsKey("&adid")) {
            this.vN.remove("&adid");
        }
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.vT.enableAutoActivityTracking(enabled);
    }

    public String get(String key) {
        u.cy().a(u.a.GET);
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (this.vN.containsKey(key)) {
            return this.vN.get(key);
        }
        if (key.equals("&ul")) {
            return ak.a(Locale.getDefault());
        }
        if (this.vP != null && this.vP.C(key)) {
            return this.vP.getValue(key);
        }
        if (this.vQ != null && this.vQ.C(key)) {
            return this.vQ.getValue(key);
        }
        if (this.vR == null || !this.vR.C(key)) {
            return null;
        }
        return this.vR.getValue(key);
    }

    public void send(Map<String, String> params) {
        u.cy().a(u.a.SEND);
        HashMap map = new HashMap();
        map.putAll(this.vN);
        if (params != null) {
            map.putAll(params);
        }
        if (TextUtils.isEmpty((CharSequence) map.get("&tid"))) {
            aa.z(String.format("Missing tracking id (%s) parameter.", "&tid"));
        }
        String str = (String) map.get("&t");
        if (TextUtils.isEmpty(str)) {
            aa.z(String.format("Missing hit type (%s) parameter.", "&t"));
            str = "";
        }
        if (this.vT.de()) {
            map.put("&sc", TJAdUnitConstants.String.VIDEO_START);
        }
        if (str.equals("transaction") || str.equals("item") || this.vO.cS()) {
            this.vM.q(map);
        } else {
            aa.z("Too many hits sent too quickly, rate limiting invoked.");
        }
    }

    public void set(String key, String value) {
        fq.b(key, (Object) "Key should be non-null");
        u.cy().a(u.a.SET);
        this.vN.put(key, value);
    }

    public void setAnonymizeIp(boolean anonymize) {
        set("&aip", ak.u(anonymize));
    }

    public void setAppId(String appId) {
        set("&aid", appId);
    }

    public void setAppInstallerId(String appInstallerId) {
        set("&aiid", appInstallerId);
    }

    public void setAppName(String appName) {
        set("&an", appName);
    }

    public void setAppVersion(String appVersion) {
        set("&av", appVersion);
    }

    public void setClientId(String clientId) {
        set("&cid", clientId);
    }

    public void setEncoding(String encoding) {
        set("&de", encoding);
    }

    public void setHostname(String hostname) {
        set("&dh", hostname);
    }

    public void setLanguage(String language) {
        set("&ul", language);
    }

    public void setLocation(String location) {
        set("&dl", location);
    }

    public void setPage(String page) {
        set("&dp", page);
    }

    public void setReferrer(String referrer) {
        set("&dr", referrer);
    }

    public void setSampleRate(double sampleRate) {
        set("&sf", Double.toHexString(sampleRate));
    }

    public void setScreenColors(String screenColors) {
        set("&sd", screenColors);
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setScreenResolution(int width, int height) {
        if (width >= 0 || height >= 0) {
            set("&sr", width + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + height);
        } else {
            aa.z("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.vT.setSessionTimeout(1000 * sessionTimeout);
    }

    public void setTitle(String title) {
        set("&dt", title);
    }

    public void setUseSecure(boolean useSecure) {
        set("useSecure", ak.u(useSecure));
    }

    public void setViewportSize(String viewportSize) {
        set("&vp", viewportSize);
    }
}
