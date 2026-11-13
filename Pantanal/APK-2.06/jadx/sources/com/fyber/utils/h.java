package com.fyber.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: HostInfo.java */
/* JADX INFO: loaded from: classes.dex */
public class h {
    private static h a;
    private WindowManager b;
    private ConnectivityManager c;
    private int d;
    private int e;
    private float f;
    private float g;
    private boolean h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String o;
    private LocationManager p;
    private List<String> q;
    private boolean n = true;
    private CountDownLatch r = new CountDownLatch(1);

    public static h a(Context context) {
        if (a == null) {
            synchronized (h.class) {
                if (a == null) {
                    a = new h(context);
                }
            }
        }
        return a;
    }

    /* JADX WARN: Type inference failed for: r0v46, types: [com.fyber.utils.h$1] */
    private h(final Context context) {
        this.h = false;
        if (context == null) {
            throw new RuntimeException("A context is required to initialize HostInfo");
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread("AdvertisingIdRetriever") { // from class: com.fyber.utils.h.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    h.this.b(context);
                }
            }.start();
        } else {
            b(context);
        }
        this.j = "";
        this.i = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            this.j = telephonyManager.getNetworkOperatorName();
            this.i = telephonyManager.getNetworkCountryIso();
        } catch (SecurityException e2) {
        }
        try {
            this.c = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (RuntimeException e3) {
        }
        if (this.e == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.b = (WindowManager) context.getSystemService("window");
            this.b.getDefaultDisplay().getMetrics(displayMetrics);
            this.d = displayMetrics.widthPixels;
            this.e = displayMetrics.heightPixels;
            this.f = displayMetrics.xdpi;
            this.g = displayMetrics.ydpi;
        }
        try {
            this.k = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e4) {
            this.k = "";
        }
        Configuration configuration = context.getResources().getConfiguration();
        int iC = c();
        this.h = ((iC == 0 || iC == 2) && configuration.orientation == 2) || ((iC == 1 || iC == 3) && configuration.orientation == 1);
        LinkedList linkedList = new LinkedList();
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
            linkedList.add("gps");
            linkedList.add("passive");
        }
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
            linkedList.add("network");
        }
        if (!linkedList.isEmpty()) {
            this.p = (LocationManager) context.getSystemService("location");
            this.q = linkedList;
        }
        this.l = context.getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context) {
        try {
            Object objInvoke = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", Context.class).invoke(null, context);
            Method method = objInvoke.getClass().getMethod("getId", new Class[0]);
            Method method2 = objInvoke.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]);
            this.m = method.invoke(objInvoke, new Object[0]).toString();
            this.n = ((Boolean) method2.invoke(objInvoke, new Object[0])).booleanValue();
        } catch (Exception e2) {
            FyberLogger.e("HostInfo", e2.getLocalizedMessage(), e2);
            this.o = Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
            if (this.o == null) {
                this.o = "";
            }
        }
        this.r.countDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String j() {
        try {
            this.r.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e2) {
        }
        return this.m;
    }

    public final String a() {
        return this.o;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean k() {
        try {
            this.r.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e2) {
        }
        return Boolean.valueOf(this.n);
    }

    public final String b() {
        String[] strArr = {"portrait", TJAdUnitConstants.String.LANDSCAPE, "portrait", TJAdUnitConstants.String.LANDSCAPE, "portrait"};
        int iC = c();
        if (this.h) {
            iC++;
        }
        return strArr[iC];
    }

    public final int c() {
        return this.b.getDefaultDisplay().getRotation();
    }

    public final boolean d() {
        return this.h;
    }

    public static boolean e() {
        return n.a(14);
    }

    public final LocationManager f() {
        return this.p;
    }

    public final List<String> g() {
        return this.q;
    }

    /* JADX INFO: compiled from: HostInfo.java */
    static class e implements o {
        private Map<String, String> a;

        e() {
        }

        @Override // com.fyber.utils.o
        public final Map<String, String> a() {
            if (h.a == null) {
                return Collections.emptyMap();
            }
            if (this.a == null) {
                this.a = new HashMap();
                this.a.put("screen_width", Integer.toString(h.a.d));
                this.a.put("screen_height", Integer.toString(h.a.e));
                this.a.put("screen_density_x", Float.toString(h.a.f));
                this.a.put("screen_density_y", Float.toString(h.a.g));
            }
            return this.a;
        }
    }

    /* JADX INFO: compiled from: HostInfo.java */
    static class d implements o {
        d() {
        }

        @Override // com.fyber.utils.o
        public final Map<String, String> a() {
            if (h.a != null) {
                return Collections.singletonMap("orientation", h.a.b());
            }
            return Collections.emptyMap();
        }
    }

    /* JADX INFO: compiled from: HostInfo.java */
    static class f implements o {
        f() {
        }

        @Override // com.fyber.utils.o
        public final Map<String, String> a() {
            return h.a == null ? Collections.emptyMap() : Collections.singletonMap("user_time", h.i());
        }
    }

    /* JADX INFO: compiled from: HostInfo.java */
    static class c implements o {
        private Map<String, String> a;

        c() {
        }

        @Override // com.fyber.utils.o
        public final synchronized Map<String, String> a() {
            Map<String, String> mapEmptyMap;
            if (h.a == null) {
                mapEmptyMap = Collections.emptyMap();
            } else {
                if (this.a == null) {
                    this.a = new HashMap();
                    String strJ = h.a.j();
                    if (strJ == null) {
                        this.a.put(TapjoyConstants.TJC_ANDROID_ID, h.a.a());
                    }
                    this.a.put("google_ad_id", strJ);
                    this.a.put("google_ad_id_limited_tracking_enabled", Boolean.toString(h.a.k().booleanValue()));
                }
                mapEmptyMap = this.a;
            }
            return mapEmptyMap;
        }
    }

    /* JADX INFO: compiled from: HostInfo.java */
    static class b implements o {
        private final HashMap<String, String> a = new HashMap<>();

        b() {
            this.a.put(TapjoyConstants.TJC_DEVICE_OS_VERSION_NAME, Build.VERSION.RELEASE);
            this.a.put("phone_version", Build.MANUFACTURER + "_" + Build.MODEL);
            this.a.put("manufacturer", Build.MANUFACTURER);
            this.a.put("language", Locale.getDefault().toString());
        }

        @Override // com.fyber.utils.o
        public final synchronized Map<String, String> a() {
            if (h.a != null) {
                this.a.put(TapjoyConstants.TJC_CARRIER_NAME, h.a.j);
                this.a.put("carrier_country", h.a.i);
                this.a.put("network_connection_type", h.i(h.a));
            }
            return this.a;
        }
    }

    /* JADX INFO: compiled from: HostInfo.java */
    static class a implements o {
        private Map<String, String> a;

        a() {
        }

        @Override // com.fyber.utils.o
        public final Map<String, String> a() {
            if (h.a == null) {
                return Collections.emptyMap();
            }
            if (this.a == null) {
                this.a = new HashMap();
                this.a.put("app_bundle_name", h.a.l);
                this.a.put(TapjoyConstants.TJC_APP_VERSION_NAME, h.a.k);
            }
            return this.a;
        }
    }

    static /* synthetic */ String i() {
        String str = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US).format(new Date());
        return new StringBuilder(str).insert(str.length() - 2, ':').toString();
    }

    static /* synthetic */ String i(h hVar) {
        NetworkInfo activeNetworkInfo;
        if (hVar.c == null || (activeNetworkInfo = hVar.c.getActiveNetworkInfo()) == null) {
            return "";
        }
        return activeNetworkInfo.getType() == 1 ? TapjoyConstants.TJC_CONNECTION_TYPE_WIFI : "cellular";
    }
}
