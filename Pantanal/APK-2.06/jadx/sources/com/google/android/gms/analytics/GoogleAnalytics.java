package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.tapjoy.TJAdUnitConstants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class GoogleAnalytics extends TrackerHandler {
    private static boolean uY;
    private static GoogleAnalytics vf;
    private Context mContext;
    private f sH;
    private String so;
    private String sp;
    private boolean uZ;
    private af va;
    private volatile Boolean vb;
    private Logger vc;
    private Set<a> vd;
    private boolean ve;

    interface a {
        void f(Activity activity);

        void g(Activity activity);
    }

    class b implements Application.ActivityLifecycleCallbacks {
        b() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            GoogleAnalytics.this.d(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            GoogleAnalytics.this.e(activity);
        }
    }

    protected GoogleAnalytics(Context context) {
        this(context, t.q(context), r.ci());
    }

    private GoogleAnalytics(Context context, f thread, af serviceManager) {
        this.vb = false;
        this.ve = false;
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.sH = thread;
        this.va = serviceManager;
        g.n(this.mContext);
        ae.n(this.mContext);
        h.n(this.mContext);
        this.vc = new l();
        this.vd = new HashSet();
        cN();
    }

    private int I(String str) {
        String lowerCase = str.toLowerCase();
        if ("verbose".equals(lowerCase)) {
            return 0;
        }
        if (TJAdUnitConstants.String.VIDEO_INFO.equals(lowerCase)) {
            return 1;
        }
        if ("warning".equals(lowerCase)) {
            return 2;
        }
        return TJAdUnitConstants.String.VIDEO_ERROR.equals(lowerCase) ? 3 : -1;
    }

    private Tracker a(Tracker tracker) {
        if (this.so != null) {
            tracker.set("&an", this.so);
        }
        if (this.sp != null) {
            tracker.set("&av", this.sp);
        }
        return tracker;
    }

    static GoogleAnalytics cM() {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            googleAnalytics = vf;
        }
        return googleAnalytics;
    }

    private void cN() {
        ApplicationInfo applicationInfo;
        int i;
        w wVarP;
        if (uY) {
            return;
        }
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 129);
        } catch (PackageManager.NameNotFoundException e) {
            aa.y("PackageManager doesn't know about package: " + e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            aa.z("Couldn't get ApplicationInfo to load gloabl config.");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle == null || (i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource")) <= 0 || (wVarP = new v(this.mContext).p(i)) == null) {
            return;
        }
        a(wVarP);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Activity activity) {
        Iterator<a> it = this.vd.iterator();
        while (it.hasNext()) {
            it.next().f(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(Activity activity) {
        Iterator<a> it = this.vd.iterator();
        while (it.hasNext()) {
            it.next().g(activity);
        }
    }

    public static GoogleAnalytics getInstance(Context context) {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            if (vf == null) {
                vf = new GoogleAnalytics(context);
            }
            googleAnalytics = vf;
        }
        return googleAnalytics;
    }

    void a(a aVar) {
        this.vd.add(aVar);
    }

    void a(w wVar) {
        int I;
        aa.y("Loading global config values.");
        if (wVar.cC()) {
            this.so = wVar.cD();
            aa.y("app name loaded: " + this.so);
        }
        if (wVar.cE()) {
            this.sp = wVar.cF();
            aa.y("app version loaded: " + this.sp);
        }
        if (wVar.cG() && (I = I(wVar.cH())) >= 0) {
            aa.y("log level loaded: " + I);
            getLogger().setLogLevel(I);
        }
        if (wVar.cI()) {
            this.va.setLocalDispatchPeriod(wVar.cJ());
        }
        if (wVar.cK()) {
            setDryRun(wVar.cL());
        }
    }

    void b(a aVar) {
        this.vd.remove(aVar);
    }

    @Deprecated
    public void dispatchLocalHits() {
        this.va.dispatchLocalHits();
    }

    public void enableAutoActivityReports(Application application) {
        if (Build.VERSION.SDK_INT < 14 || this.ve) {
            return;
        }
        application.registerActivityLifecycleCallbacks(new b());
        this.ve = true;
    }

    public boolean getAppOptOut() {
        u.cy().a(u.a.GET_APP_OPT_OUT);
        return this.vb.booleanValue();
    }

    public Logger getLogger() {
        return this.vc;
    }

    public boolean isDryRunEnabled() {
        u.cy().a(u.a.GET_DRY_RUN);
        return this.uZ;
    }

    public Tracker newTracker(int configResId) {
        Tracker trackerA;
        aj ajVarP;
        synchronized (this) {
            u.cy().a(u.a.GET_TRACKER);
            Tracker tracker = new Tracker(null, this);
            if (configResId > 0 && (ajVarP = new ai(this.mContext).p(configResId)) != null) {
                tracker.a(this.mContext, ajVarP);
            }
            trackerA = a(tracker);
        }
        return trackerA;
    }

    public Tracker newTracker(String trackingId) {
        Tracker trackerA;
        synchronized (this) {
            u.cy().a(u.a.GET_TRACKER);
            trackerA = a(new Tracker(trackingId, this));
        }
        return trackerA;
    }

    @Override // com.google.android.gms.analytics.TrackerHandler
    void q(Map<String, String> map) {
        synchronized (this) {
            try {
                if (map == null) {
                    throw new IllegalArgumentException("hit cannot be null");
                }
                ak.a(map, "&ul", ak.a(Locale.getDefault()));
                ak.a(map, "&sr", ae.cZ().getValue("&sr"));
                map.put("&_u", u.cy().cA());
                u.cy().cz();
                this.sH.q(map);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void reportActivityStart(Activity activity) {
        if (this.ve) {
            return;
        }
        d(activity);
    }

    public void reportActivityStop(Activity activity) {
        if (this.ve) {
            return;
        }
        e(activity);
    }

    public void setAppOptOut(boolean optOut) {
        u.cy().a(u.a.SET_APP_OPT_OUT);
        this.vb = Boolean.valueOf(optOut);
        if (this.vb.booleanValue()) {
            this.sH.bR();
        }
    }

    public void setDryRun(boolean dryRun) {
        u.cy().a(u.a.SET_DRY_RUN);
        this.uZ = dryRun;
    }

    @Deprecated
    public void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        this.va.setLocalDispatchPeriod(dispatchPeriodInSeconds);
    }

    public void setLogger(Logger logger) {
        u.cy().a(u.a.SET_LOGGER);
        this.vc = logger;
    }
}
