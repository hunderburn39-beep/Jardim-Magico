package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.tapjoy.internal.bl;
import com.tapjoy.internal.br;
import com.tapjoy.internal.cr;
import com.tapjoy.internal.da;
import com.tapjoy.internal.el;
import com.tapjoy.internal.eo;
import com.tapjoy.internal.eu;
import com.tapjoy.internal.fu;
import com.tapjoy.internal.gl;
import com.tapjoy.internal.y;
import com.tapjoy.mediation.TJMediationNetwork;
import com.tapjoy.mediation.TJMediationSettings;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import org.w3c.dom.Document;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyConnectCore {
    public static final float DEFAULT_CURRENCY_MULTIPLIER = 1.0f;
    private static Set aA;
    private static int aB;
    private static int aC;
    private static int aD;
    private static long aE;
    private static long aF;
    private static long aG;
    private static String aH;
    private static int aI;
    private static double aJ;
    private static double aK;
    private static long aL;
    private static int aM;
    private static int aN;
    private static int aO;
    private static String aP;
    private static String aQ;
    private static String aR;
    private static boolean ae;
    private static PackageManager af;
    private static String ak;
    private static String al;
    private static String am;
    private static String an;
    private static int ao;
    private static String ap;
    private static String aq;
    private static long ar;
    private static String as;
    private static int at;
    private static int au;
    private static String av;
    private static String aw;
    private static String ax;
    private static String ay;
    private static String az;
    protected static boolean d;
    private long Z = 0;
    private Timer aa = null;
    private boolean ab = false;
    private boolean ad;
    private TapjoyGpsHelper ag;
    private static Context g = null;
    private static String h = null;
    private static TapjoyConnectCore i = null;
    private static TapjoyURLConnection j = null;
    private static TJConnectListener k = null;
    private static TJSetUserIDListener l = null;
    private static Vector m = new Vector(Arrays.asList(TapjoyConstants.dependencyClassNames));
    private static String n = "";
    private static String o = "";
    private static String p = "";
    private static String q = "";
    private static String r = "";
    private static String s = "";
    private static String t = "";
    private static String u = "";
    private static String v = "";
    private static String w = "";
    private static String x = "";
    private static String y = "";
    private static String z = "";
    private static int A = 1;
    private static float B = 1.0f;
    private static int C = 1;
    private static String D = "";
    private static boolean E = false;
    private static String F = "";
    private static String G = "";
    private static String H = "";
    private static String I = "";
    private static String J = "";
    private static String K = "";
    private static String L = "";
    private static String M = "";
    private static String N = "";
    private static String O = "";
    private static String P = TapjoyConstants.TJC_PLUGIN_NATIVE;
    private static String Q = "";
    private static String R = "";
    private static float S = 1.0f;
    private static boolean T = false;
    private static String U = "";
    private static String V = "";
    private static String W = "";
    private static String X = "";
    private static String Y = null;
    private static long ac = 0;
    protected static int a = 0;
    protected static int b = 0;
    protected static String c = "";
    protected static String e = "";
    protected static String f = "";
    private static Hashtable ah = TapjoyConnectFlag.CONNECT_FLAG_DEFAULTS;
    private static String ai = "";
    private static Map aj = new ConcurrentHashMap();

    public static TapjoyConnectCore getInstance() {
        return i;
    }

    public static void requestTapjoyConnect(Context applicationContext, String apiKey) throws TapjoyIntegrationException {
        requestTapjoyConnect(applicationContext, apiKey, null);
    }

    public static void requestTapjoyConnect(Context applicationContext, String apiKey, Hashtable hashtable) throws TapjoyIntegrationException {
        requestTapjoyConnect(applicationContext, apiKey, hashtable, null);
    }

    public static void requestTapjoyConnect(Context applicationContext, String apiKey, Hashtable hashtable, TJConnectListener tapjoyConnectListener) throws TapjoyIntegrationException {
        try {
            el elVar = new el(apiKey);
            if (elVar.a != el.a.SDK_ANDROID) {
                throw new IllegalArgumentException("The given API key was not for Android.");
            }
            h = apiKey;
            w = elVar.b;
            N = elVar.c;
            O = elVar.d;
            fu.a(applicationContext).j = apiKey;
            if (hashtable != null) {
                ah.putAll(hashtable);
                eu.b().a(hashtable);
            }
            k = tapjoyConnectListener;
            i = new TapjoyConnectCore(applicationContext);
        } catch (IllegalArgumentException e2) {
            throw new TapjoyIntegrationException(e2.getMessage());
        }
    }

    /* JADX WARN: Code duplicated, block: B:161:0x051a  */
    public TapjoyConnectCore(Context applicationContext) {
        WifiInfo connectionInfo;
        boolean zD;
        boolean z2;
        this.ad = false;
        g = applicationContext;
        eu.a().a(applicationContext);
        j = new TapjoyURLConnection();
        af = g.getPackageManager();
        this.ag = new TapjoyGpsHelper(g);
        try {
            if (ah == null) {
                ah = new Hashtable();
            }
            if (getConnectFlagValue(TapjoyConnectFlag.ENABLE_LOGGING) != null && getConnectFlagValue(TapjoyConnectFlag.ENABLE_LOGGING).equals("true")) {
                TapjoyLog.setDebugEnabled(true);
            }
            k();
            int identifier = g.getResources().getIdentifier("raw/tapjoy_config", null, g.getPackageName());
            Properties properties = new Properties();
            try {
                properties.load(g.getResources().openRawResource(identifier));
                a(properties);
            } catch (Exception e2) {
            }
            if (getConnectFlagValue("unit_test_mode") == "") {
                l();
            }
            String string = Settings.Secure.getString(g.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
            n = string;
            if (string != null) {
                n = n.toLowerCase();
            }
            try {
                x = af.getPackageInfo(g.getPackageName(), 0).versionName;
                u = TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
                F = TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
                s = Build.MODEL;
                t = Build.MANUFACTURER;
                v = Build.VERSION.RELEASE;
                y = "11.9.0";
                z = TapjoyConstants.TJC_BRIDGE_VERSION_NUMBER;
                try {
                    if (Build.VERSION.SDK_INT > 3) {
                        TapjoyDisplayMetricsUtil tapjoyDisplayMetricsUtil = new TapjoyDisplayMetricsUtil(g);
                        A = tapjoyDisplayMetricsUtil.getScreenDensityDPI();
                        B = tapjoyDisplayMetricsUtil.getScreenDensityScale();
                        C = tapjoyDisplayMetricsUtil.getScreenLayoutSize();
                    }
                } catch (Exception e3) {
                    TapjoyLog.e("TapjoyConnect", "Error getting screen density/dimensions/layout: " + e3.toString());
                }
                if (e("android.permission.ACCESS_WIFI_STATE")) {
                    try {
                        WifiManager wifiManager = (WifiManager) g.getSystemService(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI);
                        if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                            String macAddress = connectionInfo.getMacAddress();
                            q = macAddress;
                            if (macAddress != null) {
                                q = q.replace(":", "").toLowerCase();
                            }
                        }
                    } catch (Exception e4) {
                        TapjoyLog.e("TapjoyConnect", "Error getting device mac address: " + e4.toString());
                    }
                } else {
                    TapjoyLog.d("TapjoyConnect", "*** ignore macAddress");
                }
                TelephonyManager telephonyManager = (TelephonyManager) g.getSystemService("phone");
                if (telephonyManager != null) {
                    G = telephonyManager.getNetworkOperatorName();
                    H = telephonyManager.getNetworkCountryIso();
                    String networkOperator = telephonyManager.getNetworkOperator();
                    if (networkOperator != null && (networkOperator.length() == 5 || networkOperator.length() == 6)) {
                        I = networkOperator.substring(0, 3);
                        J = networkOperator.substring(3);
                    }
                    if (e("android.permission.READ_PHONE_STATE")) {
                        try {
                            if (getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID) != null && getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID).length() > 0) {
                                p = getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID);
                            } else {
                                p = telephonyManager.getDeviceId();
                            }
                            TapjoyLog.d("TapjoyConnect", "deviceID: " + p);
                            if (p == null) {
                                TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Device ID is NULL"));
                                z2 = false;
                            } else if (p.length() == 0 || p.equals("000000000000000") || p.equals("0")) {
                                TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Device ID is empty or an emulator"));
                                z2 = false;
                            } else {
                                p = p.toLowerCase(Locale.getDefault());
                                z2 = true;
                            }
                            TapjoyLog.i("TapjoyConnect", "ANDROID SDK VERSION: " + Build.VERSION.SDK_INT);
                            if (Build.VERSION.SDK_INT >= 9) {
                                TapjoyLog.d("TapjoyConnect", "TRYING TO GET SERIAL OF 2.3+ DEVICE...");
                                String serial = getSerial();
                                if (!z2) {
                                    p = serial;
                                }
                                if (p == null) {
                                    TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Device serial number is NULL"));
                                } else if (p.length() == 0 || p.equals("000000000000000") || p.equals("0") || p.equals("unknown")) {
                                    TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Device serial number is empty or an emulator"));
                                } else {
                                    p = p.toLowerCase(Locale.getDefault());
                                }
                            }
                        } catch (Exception e5) {
                            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Cannot get deviceID -- " + e5.toString()));
                            p = null;
                        }
                    } else {
                        TapjoyLog.d("TapjoyConnect", "*** ignore deviceID");
                    }
                }
                SharedPreferences sharedPreferences = g.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0);
                String string2 = sharedPreferences.getString(TapjoyConstants.PREF_INSTALL_ID, "");
                r = string2;
                if (string2 == null || r.length() == 0) {
                    try {
                        r = TapjoyUtil.SHA256(UUID.randomUUID().toString() + System.currentTimeMillis());
                        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                        editorEdit.putString(TapjoyConstants.PREF_INSTALL_ID, r);
                        editorEdit.commit();
                    } catch (Exception e6) {
                        TapjoyLog.e("TapjoyConnect", "Error generating install id: " + e6.toString());
                    }
                }
                try {
                    E = a("android.hardware.location", "android.permission.ACCESS_FINE_LOCATION");
                } catch (Exception e7) {
                    TapjoyLog.e("TapjoyConnect", "Error trying to detect capabilities on devicee: " + e7.toString());
                }
                if (getConnectFlagValue(TapjoyConnectFlag.STORE_NAME) != null && getConnectFlagValue(TapjoyConnectFlag.STORE_NAME).length() > 0) {
                    M = getConnectFlagValue(TapjoyConnectFlag.STORE_NAME);
                    if (!new ArrayList(Arrays.asList(TapjoyConnectFlag.STORE_ARRAY)).contains(M)) {
                        TapjoyLog.w("TapjoyConnect", "Warning -- undefined STORE_NAME: " + M);
                    }
                }
                try {
                    String str = M;
                    Intent intent = new Intent("android.intent.action.VIEW");
                    if (str.length() <= 0) {
                        intent.setData(Uri.parse("market://details"));
                        if (af.queryIntentActivities(intent, 0).size() > 0) {
                            zD = true;
                        } else {
                            zD = false;
                        }
                    } else if (str.equals(TapjoyConnectFlag.STORE_GFAN)) {
                        zD = d("com.mappn.gfan");
                    } else if (str.equals(TapjoyConnectFlag.STORE_SKT)) {
                        zD = d("com.skt.skaf.TSCINSTALL");
                    } else {
                        zD = false;
                    }
                    T = zD;
                } catch (Exception e8) {
                    TapjoyLog.e("TapjoyConnect", "Error trying to detect store intent on devicee: " + e8.toString());
                }
                i();
                if (getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS).length() > 0) {
                    f = getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS);
                }
                if (getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK).length() > 0) {
                    e = getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK);
                }
                if (getConnectFlagValue(TapjoyConnectFlag.USER_ID) != null && getConnectFlagValue(TapjoyConnectFlag.USER_ID).length() > 0) {
                    TapjoyLog.i("TapjoyConnect", "Setting userID to: " + getConnectFlagValue(TapjoyConnectFlag.USER_ID));
                    setUserID(getConnectFlagValue(TapjoyConnectFlag.USER_ID), null);
                }
                R = TapjoyUtil.getRedirectDomain(getConnectFlagValue(TapjoyConnectFlag.SERVICE_URL));
                TapjoyLog.d("TapjoyConnect", "deviceID: " + p + ((getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID) == null || getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID).length() <= 0) ? "" : " *debug_device_id*"));
                if (ah != null) {
                    j();
                }
                callConnect();
                this.ad = true;
            } catch (PackageManager.NameNotFoundException e9) {
                throw new TapjoyException(e9.getMessage());
            }
        } catch (TapjoyIntegrationException e10) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, e10.getMessage()));
            f();
            eo.b.notifyObservers(Boolean.FALSE);
        } catch (TapjoyException e11) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, e11.getMessage()));
            f();
            eo.b.notifyObservers(Boolean.FALSE);
        }
    }

    private static void f() {
        if (!cr.c(O)) {
            fu.a().a(g, h, "11.9.0", TapjoyConfig.TJC_ANALYTICS_SERVICE_URL, O, N);
        }
        if (k != null) {
            k.onConnectFailure();
        }
    }

    public void callConnect() {
        fetchAdvertisingID();
    }

    public void fetchAdvertisingID() {
        new Thread(new Runnable() { // from class: com.tapjoy.TapjoyConnectCore.1
            @Override // java.lang.Runnable
            public final void run() {
                TapjoyConnectCore.this.ag.loadAdvertisingId();
                if (TapjoyConnectCore.this.ag.isGooglePlayServicesAvailable() && TapjoyConnectCore.this.ag.isGooglePlayManifestConfigured()) {
                    TapjoyConnectCore.b = TapjoyConnectCore.this.ag.getDeviceGooglePlayServicesVersion();
                    TapjoyConnectCore.a = TapjoyConnectCore.this.ag.getPackagedGooglePlayServicesVersion();
                }
                if (TapjoyConnectCore.this.ag.isAdIdAvailable()) {
                    TapjoyConnectCore.d = TapjoyConnectCore.this.ag.isAdTrackingEnabled();
                    TapjoyConnectCore.c = TapjoyConnectCore.this.ag.getAdvertisingId();
                }
                if (TapjoyConnectCore.o()) {
                    TapjoyLog.i("TapjoyConnect", "Disabling persistent IDs. Do this only if you are not using Tapjoy to manage currency.");
                }
                TapjoyConnectCore.this.completeConnectCall();
            }
        }).start();
    }

    public void appPause() {
        this.ab = true;
    }

    public void appResume() {
        if (this.ab) {
            p();
            this.ab = false;
        }
    }

    public static Map getURLParams() {
        Map genericURLParams = getGenericURLParams();
        genericURLParams.putAll(getTimeStampAndVerifierParams());
        return genericURLParams;
    }

    public static Map getGenericURLParams() {
        Map mapG = g();
        TapjoyUtil.safePut(mapG, TapjoyConstants.TJC_APP_ID, w, true);
        return mapG;
    }

    private static Map g() {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_PLUGIN, P, true);
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_SDK_TYPE, Q, true);
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_APP_ID, w, true);
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_LIBRARY_VERSION, y, true);
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_LIBRARY_REVISION, TapjoyRevision.GIT_REVISION, true);
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_BRIDGE_VERSION, z, true);
        TapjoyUtil.safePut(map3, TapjoyConstants.TJC_APP_VERSION_NAME, x, true);
        map2.putAll(map3);
        HashMap map4 = new HashMap();
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_NAME, s, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_PLATFORM, F, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_OS_VERSION_NAME, v, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_MANUFACTURER, t, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_TYPE_NAME, u, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_SCREEN_LAYOUT_SIZE, new StringBuilder().append(C).toString(), true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_LOCATION, String.valueOf(E), true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_STORE_NAME, M, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_STORE_VIEW, String.valueOf(T), true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_CARRIER_NAME, G, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_CARRIER_COUNTRY_CODE, H, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_MOBILE_NETWORK_CODE, J, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_MOBILE_COUNTRY_CODE, I, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_COUNTRY_CODE, Locale.getDefault().getCountry(), true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_LANGUAGE, Locale.getDefault().getLanguage(), true);
        K = getConnectionType();
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_CONNECTION_TYPE, K, true);
        L = getConnectionSubType();
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_CONNECTION_SUBTYPE, L, true);
        TapjoyUtil.safePut(map4, TapjoyConstants.TJC_DEVICE_SCREEN_DENSITY, new StringBuilder().append(A).toString(), true);
        map2.putAll(map4);
        HashMap map5 = new HashMap();
        if (n()) {
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_ADVERTISING_ID, c, true);
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_AD_TRACKING_ENABLED, String.valueOf(d), true);
        }
        if (!o()) {
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_ANDROID_ID, n, true);
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_DEVICE_ID_NAME, p, true);
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_DEVICE_MAC_ADDRESS, q, true);
        }
        TapjoyUtil.safePut(map5, TapjoyConstants.TJC_INSTALL_ID, r, true);
        TapjoyUtil.safePut(map5, TapjoyConstants.TJC_USER_ID, D, true);
        TapjoyUtil.safePut(map5, TapjoyConstants.TJC_ADVERTISING_ID_CHECK_DISABLED, e, true);
        TapjoyUtil.safePut(map5, TapjoyConstants.TJC_PERSISTENT_ID_DISABLED, f, true);
        if (a != 0) {
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_PACKAGED_GOOGLE_PLAY_SERVICES_VERSION, Integer.toString(a), true);
        }
        if (b != 0) {
            TapjoyUtil.safePut(map5, TapjoyConstants.TJC_DEVICE_GOOGLE_PLAY_SERVICES_VERSION, Integer.toString(b), true);
        }
        if (o == null || o.length() == 0 || System.currentTimeMillis() - ac > TapjoyConstants.SESSION_ID_INACTIVITY_TIME) {
            o = p();
        } else {
            ac = System.currentTimeMillis();
        }
        TapjoyUtil.safePut(map5, TapjoyConstants.TJC_SESSION_ID, o, true);
        map2.putAll(map5);
        HashMap map6 = new HashMap();
        TapjoyUtil.safePut(map6, TapjoyConstants.TJC_APP_GROUP_ID, U, true);
        TapjoyUtil.safePut(map6, TapjoyConstants.TJC_STORE, V, true);
        TapjoyUtil.safePut(map6, TapjoyConstants.TJC_ANALYTICS_API_KEY, W, true);
        TapjoyUtil.safePut(map6, TapjoyConstants.TJC_MANAGED_DEVICE_ID, X, true);
        map2.putAll(map6);
        if (TapjoyCache.getInstance() != null && TapjoyCache.getInstance().getCachedOfferIDs() != null && TapjoyCache.getInstance().getCachedOfferIDs().length() > 0) {
            TapjoyUtil.safePut(map2, TapjoyConstants.TJC_CACHED_OFFERS, TapjoyCache.getInstance().getCachedOfferIDs(), true);
        }
        TapjoyUtil.safePut(map2, TapjoyConstants.TJC_CURRENCY_MULTIPLIER, Float.toString(S), true);
        map.putAll(map2);
        HashMap map7 = new HashMap();
        i();
        HashMap map8 = new HashMap();
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_ANALYTICS_ID, ak, true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_PACKAGE_ID, al, true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_PACKAGE_SIGN, am, true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_DEVICE_DISPLAY_DENSITY, new StringBuilder().append(aM).toString(), true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_DEVICE_DISPLAY_WIDTH, new StringBuilder().append(aN).toString(), true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_DEVICE_DISPLAY_HEIGHT, new StringBuilder().append(aO).toString(), true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_DEVICE_COUNTRY_SIM, aP, true);
        TapjoyUtil.safePut(map8, TapjoyConstants.TJC_DEVICE_TIMEZONE, aQ, true);
        map7.putAll(map8);
        HashMap map9 = new HashMap();
        TapjoyUtil.safePut(map9, TapjoyConstants.TJC_PACKAGE_VERSION, an, true);
        TapjoyUtil.safePut(map9, TapjoyConstants.TJC_PACKAGE_REVISION, new StringBuilder().append(ao).toString(), true);
        TapjoyUtil.safePut(map9, TapjoyConstants.TJC_PACKAGE_DATA_VERSION, ap, true);
        TapjoyUtil.safePut(map9, TapjoyConstants.TJC_INSTALLER, aq, true);
        if (cr.c(M)) {
            TapjoyUtil.safePut(map9, TapjoyConstants.TJC_STORE_NAME, aR, true);
        }
        map7.putAll(map9);
        map7.putAll(h());
        map.putAll(map7);
        return map;
    }

    public static Map getTimeStampAndVerifierParams() {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        String strA = a(jCurrentTimeMillis);
        HashMap map = new HashMap();
        TapjoyUtil.safePut(map, "timestamp", String.valueOf(jCurrentTimeMillis), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_VERIFIER, strA, true);
        return map;
    }

    private static Map h() {
        HashMap map = new HashMap();
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_INSTALLED, new StringBuilder().append(ar).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_REFERRER, as, true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_LEVEL, new StringBuilder().append(at).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_FRIEND_COUNT, new StringBuilder().append(au).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_VARIABLE_1, av, true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_VARIABLE_2, aw, true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_VARIABLE_3, ax, true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_VARIABLE_4, ay, true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_VARIABLE_5, az, true);
        Iterator it = aA.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            TapjoyUtil.safePut(map, "user_tags[" + i2 + "]", (String) it.next(), true);
            i2++;
        }
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_WEEKLY_FREQUENCY, new StringBuilder().append(aB).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_USER_MONTHLY_FREQUENCY, new StringBuilder().append(aC).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_SESSION_TOTAL_COUNT, new StringBuilder().append(aD).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_SESSION_TOTAL_LENGTH, new StringBuilder().append(aE).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_SESSION_LAST_AT, new StringBuilder().append(aF).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_SESSION_LAST_LENGTH, new StringBuilder().append(aG).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_PURCHASE_CURRENCY, aH, true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_PURCHASE_TOTAL_COUNT, new StringBuilder().append(aI).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_PURCHASE_TOTAL_PRICE, new StringBuilder().append(aJ).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_PURCHASE_LAST_PRICE, new StringBuilder().append(aK).toString(), true);
        TapjoyUtil.safePut(map, TapjoyConstants.TJC_PURCHASE_LAST_AT, new StringBuilder().append(aL).toString(), true);
        return map;
    }

    private static void i() {
        gl.n nVarA = fu.a(g).a(true);
        ak = nVarA.c.h();
        al = nVarA.c.y();
        am = nVarA.c.A();
        aM = nVarA.c.c;
        aN = nVarA.c.d;
        aO = nVarA.c.e;
        aP = nVarA.c.E();
        aQ = nVarA.c.w();
        an = nVarA.d.f();
        ao = nVarA.d.c;
        ap = nVarA.d.i();
        aq = nVarA.d.k();
        aR = nVarA.d.m();
        ar = nVarA.e.c;
        as = nVarA.e.g();
        at = nVarA.e.p;
        au = nVarA.e.q;
        av = nVarA.e.C();
        aw = nVarA.e.E();
        ax = nVarA.e.G();
        ay = nVarA.e.I();
        az = nVarA.e.K();
        aA = new HashSet(nVarA.e.r);
        aB = nVarA.e.d;
        aC = nVarA.e.e;
        aD = nVarA.e.g;
        aE = nVarA.e.h;
        aF = nVarA.e.i;
        aG = nVarA.e.j;
        aH = nVarA.e.p();
        aI = nVarA.e.k;
        aJ = nVarA.e.l;
        aK = nVarA.e.n;
        aL = nVarA.e.m;
    }

    private static void j() {
        TapjoyLog.i("TapjoyConnect", "Connect Flags:");
        TapjoyLog.i("TapjoyConnect", "--------------------");
        for (Map.Entry entry : ah.entrySet()) {
            TapjoyLog.i("TapjoyConnect", "key: " + ((String) entry.getKey()) + ", value: " + Uri.encode(entry.getValue().toString()));
        }
        TapjoyLog.i("TapjoyConnect", "hostURL: [" + getConnectFlagValue(TapjoyConnectFlag.SERVICE_URL) + "]");
        TapjoyLog.i("TapjoyConnect", "redirectDomain: [" + R + "]");
        TapjoyLog.i("TapjoyConnect", "--------------------");
    }

    private static void k() {
        try {
            if (af != null) {
                ApplicationInfo applicationInfo = af.getApplicationInfo(g.getPackageName(), 128);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    for (String str : TapjoyConnectFlag.FLAG_ARRAY) {
                        String string = applicationInfo.metaData.getString("tapjoy." + str);
                        if (string != null) {
                            TapjoyLog.d("TapjoyConnect", "Found manifest flag: " + str + ", " + string);
                            b(str, string);
                        }
                    }
                    TapjoyLog.d("TapjoyConnect", "Metadata successfully loaded");
                    return;
                }
                TapjoyLog.d("TapjoyConnect", "No metadata present.");
            }
        } catch (Exception e2) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Error reading manifest meta-data -- " + e2.toString()));
        }
    }

    private static void a(Properties properties) {
        Enumeration enumerationKeys = properties.keys();
        while (enumerationKeys.hasMoreElements()) {
            try {
                String str = (String) enumerationKeys.nextElement();
                b(str, (String) properties.get(str));
            } catch (ClassCastException e2) {
                TapjoyLog.e("TapjoyConnect", "Error parsing configuration properties in tapjoy_config.txt");
            }
        }
    }

    private void l() throws TapjoyIntegrationException {
        try {
            List<ActivityInfo> listAsList = Arrays.asList(af.getPackageInfo(g.getPackageName(), 1).activities);
            if (listAsList != null) {
                for (ActivityInfo activityInfo : listAsList) {
                    if (m.contains(activityInfo.name)) {
                        int iIndexOf = m.indexOf(activityInfo.name);
                        try {
                            Class.forName((String) m.get(iIndexOf));
                            Vector vector = new Vector();
                            if ((activityInfo.configChanges & 128) != 128) {
                                vector.add("orientation");
                            }
                            if ((activityInfo.configChanges & 32) != 32) {
                                vector.add("keyboardHidden");
                            }
                            if (vector.size() != 0) {
                                if (vector.size() == 1) {
                                    throw new TapjoyIntegrationException(vector.toString() + " property is not specified in manifest configChanges for " + ((String) m.get(iIndexOf)));
                                }
                                throw new TapjoyIntegrationException(vector.toString() + " properties are not specified in manifest configChanges for " + ((String) m.get(iIndexOf)));
                            }
                            if (Build.VERSION.SDK_INT >= 13 && (activityInfo.configChanges & 1024) != 1024) {
                                TapjoyLog.w("TapjoyConnect", "WARNING -- screenSize property is not specified in manifest configChanges for " + ((String) m.get(iIndexOf)));
                            }
                            if (Build.VERSION.SDK_INT >= 11 && activityInfo.name.equals("com.tapjoy.TJAdUnitActivity") && (activityInfo.flags & 512) != 512) {
                                throw new TapjoyIntegrationException("'hardwareAccelerated' property not specified in manifest for " + ((String) m.get(iIndexOf)));
                            }
                            m.remove(iIndexOf);
                        } catch (ClassNotFoundException e2) {
                            throw new TapjoyIntegrationException("[ClassNotFoundException] Could not find dependency class " + ((String) m.get(iIndexOf)));
                        }
                    }
                }
            }
            if (m.size() != 0) {
                if (m.size() == 1) {
                    throw new TapjoyIntegrationException("Missing " + m.size() + " dependency class in manifest: " + m.toString());
                }
                throw new TapjoyIntegrationException("Missing " + m.size() + " dependency classes in manifest: " + m.toString());
            }
            m();
            try {
                try {
                    Class.forName("com.tapjoy.TJAdUnitJSBridge").getMethod(TJAdUnitConstants.String.CLOSE_REQUESTED, Boolean.class);
                    String strCopyTextFromJarIntoString = (String) TapjoyUtil.getResource("mraid.js");
                    if (strCopyTextFromJarIntoString == null) {
                        strCopyTextFromJarIntoString = TapjoyUtil.copyTextFromJarIntoString("js/mraid.js", g);
                    }
                    if (strCopyTextFromJarIntoString != null) {
                        if (getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK).equals("true")) {
                            TapjoyLog.i("TapjoyConnect", "Skipping integration check for Google Play Services and Advertising ID. Do this only if you do not have access to Google Play Services.");
                            return;
                        } else {
                            this.ag.checkGooglePlayIntegration();
                            return;
                        }
                    }
                    throw new TapjoyIntegrationException("ClassNotFoundException: mraid.js was not found.");
                } catch (NoSuchMethodException e3) {
                    throw new TapjoyIntegrationException("Try configuring Proguard or other code obfuscators to ignore com.tapjoy classes. Visit http://dev.tapjoy.comfor more information.");
                }
            } catch (ClassNotFoundException e4) {
                throw new TapjoyIntegrationException("ClassNotFoundException: com.tapjoy.TJAdUnitJSBridge was not found.");
            }
        } catch (PackageManager.NameNotFoundException e5) {
            throw new TapjoyIntegrationException("NameNotFoundException: Could not find package.");
        }
    }

    private static void m() throws TapjoyIntegrationException {
        Vector vector = new Vector();
        for (String str : TapjoyConstants.dependencyPermissions) {
            if (!e(str)) {
                vector.add(str);
            }
        }
        if (vector.size() != 0) {
            if (vector.size() == 1) {
                throw new TapjoyIntegrationException("Missing 1 permission in manifest: " + vector.toString());
            }
            throw new TapjoyIntegrationException("Missing " + vector.size() + " permissions in manifest: " + vector.toString());
        }
        Vector vector2 = new Vector();
        for (String str2 : TapjoyConstants.optionalPermissions) {
            if (!e(str2)) {
                vector2.add(str2);
            }
        }
        if (vector2.size() != 0) {
            if (vector2.size() == 1) {
                TapjoyLog.w("TapjoyConnect", "WARNING -- " + vector2.toString() + " permission was not found in manifest. The exclusion of this permission could cause problems.");
            } else {
                TapjoyLog.w("TapjoyConnect", "WARNING -- " + vector2.toString() + " permissions were not found in manifest. The exclusion of these permissions could cause problems.");
            }
        }
    }

    private static boolean n() {
        return c != null && c.length() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean o() {
        return n() && getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS).equals("true");
    }

    class a extends TimerTask {
        private a() {
        }

        /* synthetic */ a(TapjoyConnectCore tapjoyConnectCore, byte b) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            TapjoyConnectCore.this.Z += TapjoyConstants.TIMER_INCREMENT;
            TapjoyLog.d("TapjoyConnect", "elapsed_time: " + TapjoyConnectCore.this.Z + " (" + ((TapjoyConnectCore.this.Z / 1000) / 60) + "m " + ((TapjoyConnectCore.this.Z / 1000) % 60) + "s)");
            SharedPreferences.Editor editorEdit = TapjoyConnectCore.g.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).edit();
            editorEdit.putLong(TapjoyConstants.PREF_ELAPSED_TIME, TapjoyConnectCore.this.Z);
            editorEdit.commit();
            if (TapjoyConnectCore.this.Z >= TapjoyConstants.PAID_APP_TIME) {
                TapjoyLog.d("TapjoyConnect", "timer done...");
                if (TapjoyConnectCore.Y != null && TapjoyConnectCore.Y.length() > 0) {
                    TapjoyLog.d("TapjoyConnect", "Calling PPA actionComplete...");
                    TapjoyConnectCore.this.actionComplete(TapjoyConnectCore.Y);
                }
                cancel();
            }
        }
    }

    private static boolean a(String str, boolean z2) throws Throwable {
        br brVar;
        br brVarB;
        long jLongValue;
        br brVar2 = null;
        try {
            try {
                brVarB = br.b(str);
                try {
                    Map mapD = brVarB.d();
                    String strA = cr.a((String) mapD.get(TapjoyConstants.TJC_APP_GROUP_ID));
                    String strA2 = cr.a((String) mapD.get(TapjoyConstants.TJC_STORE));
                    String strA3 = cr.a((String) mapD.get(TapjoyConstants.TJC_ANALYTICS_API_KEY));
                    String strA4 = cr.a((String) mapD.get(TapjoyConstants.TJC_MANAGED_DEVICE_ID));
                    String strA5 = cr.a((String) mapD.get(TapjoyConstants.TJC_PACKAGE_NAMES));
                    Object obj = mapD.get("cache_max_age");
                    el elVar = new el(strA3);
                    if (elVar.a != el.a.RPC_ANALYTICS) {
                        throw new IOException("Invalid analytics_api_key");
                    }
                    String str2 = elVar.b;
                    if (!str2.regionMatches(13, "-8000-8000-", 0, 11)) {
                        throw new IllegalArgumentException("The given UUID did not come from 5Rocks.");
                    }
                    String string = new StringBuffer().append(str2.substring(0, 8)).append(str2.substring(24, 30)).append(str2.substring(9, 13)).append(str2.substring(30)).toString();
                    String str3 = elVar.c;
                    String str4 = strA == null ? string : strA;
                    fu.a().a(g, strA3, "11.9.0", TapjoyConfig.TJC_ANALYTICS_SERVICE_URL, string, str3);
                    U = str4;
                    V = strA2;
                    W = strA3;
                    X = strA4;
                    ArrayList arrayList = new ArrayList();
                    if (strA5 != null) {
                        for (String str5 : strA5.split(",")) {
                            String strTrim = str5.trim();
                            if (strTrim.length() > 0) {
                                arrayList.add(strTrim);
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        a(arrayList);
                    }
                    brVarB.close();
                    if (!z2) {
                        try {
                            if (obj instanceof String) {
                                try {
                                    jLongValue = Long.parseLong(((String) obj).trim());
                                } catch (NumberFormatException e2) {
                                    jLongValue = 0;
                                }
                            } else if (obj instanceof Number) {
                                try {
                                    jLongValue = ((Number) obj).longValue();
                                } catch (NumberFormatException e3) {
                                    jLongValue = 0;
                                }
                            } else {
                                jLongValue = 0;
                            }
                            if (jLongValue <= 0) {
                                TapjoyAppSettings.getInstance().removeConnectResult();
                            } else {
                                TapjoyAppSettings.getInstance().saveConnectResultAndParams(str, s(), (jLongValue * 1000) + y.b());
                            }
                            eu euVarA = eu.a();
                            Object obj2 = mapD.get(TapjoyConstants.PREF_SERVER_PROVIDED_CONFIGURATIONS);
                            if (obj2 instanceof Map) {
                                try {
                                    euVarA.a.a((Map) obj2);
                                    euVarA.c().edit().putString(TapjoyConstants.PREF_SERVER_PROVIDED_CONFIGURATIONS, bl.a(obj2)).apply();
                                } catch (Exception e4) {
                                }
                            } else if (obj2 == null) {
                                euVarA.a.a((Map) null);
                                euVarA.c().edit().remove(TapjoyConstants.PREF_SERVER_PROVIDED_CONFIGURATIONS).apply();
                            }
                        } catch (IOException e5) {
                            e = e5;
                            brVar2 = null;
                            try {
                                TapjoyLog.v("TapjoyConnect", e.getMessage());
                                da.a(brVar2);
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                brVar = brVar2;
                                da.a(brVar);
                                throw th;
                            }
                        } catch (RuntimeException e6) {
                            e = e6;
                            brVarB = null;
                            TapjoyLog.v("TapjoyConnect", e.getMessage());
                            da.a(brVarB);
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            brVar = null;
                            da.a(brVar);
                            throw th;
                        }
                    }
                    da.a(null);
                    return true;
                } catch (IOException e7) {
                    e = e7;
                    brVar2 = brVarB;
                } catch (RuntimeException e8) {
                    e = e8;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException e9) {
            e = e9;
        } catch (RuntimeException e10) {
            e = e10;
            brVarB = null;
        } catch (Throwable th4) {
            th = th4;
            brVar = null;
        }
    }

    private static synchronized void a(List list) {
        ai = "";
        for (ApplicationInfo applicationInfo : af.getInstalledApplications(0)) {
            if ((applicationInfo.flags & 1) != 1 && list.contains(applicationInfo.packageName)) {
                TapjoyLog.d("TapjoyConnect", "MATCH: installed packageName: " + applicationInfo.packageName);
                if (ai.length() > 0) {
                    ai += ",";
                }
                ai += applicationInfo.packageName;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(String str) throws Throwable {
        br brVarB;
        try {
            try {
                brVarB = br.b(str);
                try {
                    if (!brVarB.a()) {
                        brVarB.close();
                        da.a(null);
                        TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Completed Pay-Per-Action call failed."));
                        return false;
                    }
                    brVarB.s();
                    TapjoyLog.d("TapjoyConnect", "Successfully sent completed Pay-Per-Action to Tapjoy server.");
                    brVarB.close();
                    da.a(null);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    TapjoyLog.v("TapjoyConnect", e.getMessage());
                    da.a(brVarB);
                } catch (RuntimeException e3) {
                    e = e3;
                    TapjoyLog.v("TapjoyConnect", e.getMessage());
                    da.a(brVarB);
                }
            } catch (Throwable th) {
                th = th;
                da.a(null);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            brVarB = null;
        } catch (RuntimeException e5) {
            e = e5;
            brVarB = null;
        } catch (Throwable th2) {
            th = th2;
            da.a(null);
            throw th;
        }
    }

    public void release() {
        i = null;
        j = null;
        TapjoyLog.d("TapjoyConnect", "Releasing core static instance.");
    }

    public static String getAppID() {
        return w;
    }

    public static String getDeviceID() {
        return p;
    }

    public static String getUserID() {
        return D;
    }

    public static String getHostURL() {
        return getConnectFlagValue(TapjoyConnectFlag.SERVICE_URL);
    }

    public static String getPlacementURL() {
        return getConnectFlagValue(TapjoyConnectFlag.PLACEMENT_URL);
    }

    public static String getConnectURL() {
        return TapjoyConfig.TJC_CONNECT_SERVICE_URL;
    }

    public static String getRedirectDomain() {
        return R;
    }

    public static String getCarrierName() {
        return G;
    }

    public String getSerial() {
        String string;
        Exception e2;
        try {
            Field declaredField = Class.forName("android.os.Build").getDeclaredField("SERIAL");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            string = declaredField.get(Build.class).toString();
            try {
                TapjoyLog.d("TapjoyConnect", "serial: " + string);
            } catch (Exception e3) {
                e2 = e3;
                TapjoyLog.e("TapjoyConnect", e2.toString());
            }
        } catch (Exception e4) {
            string = null;
            e2 = e4;
        }
        return string;
    }

    public static String getConnectionType() {
        String str = "";
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) g.getSystemService("connectivity");
            if (connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null) {
                switch (connectivityManager.getActiveNetworkInfo().getType()) {
                    case 1:
                    case 6:
                        str = TapjoyConstants.TJC_CONNECTION_TYPE_WIFI;
                        break;
                    default:
                        str = TapjoyConstants.TJC_CONNECTION_TYPE_MOBILE;
                        break;
                }
                TapjoyLog.d("TapjoyConnect", "connectivity: " + connectivityManager.getActiveNetworkInfo().getType());
                TapjoyLog.d("TapjoyConnect", "connection_type: " + str);
            }
            return str;
        } catch (Exception e2) {
            String str2 = str;
            TapjoyLog.e("TapjoyConnect", "getConnectionType error: " + e2.toString());
            return str2;
        }
    }

    public static String getConnectionSubType() {
        String subtypeName;
        Exception e2;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) g.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            subtypeName = connectivityManager.getActiveNetworkInfo().getSubtypeName();
            try {
                TapjoyLog.d("TapjoyConnect", "connection_sub_type: " + subtypeName);
                return subtypeName;
            } catch (Exception e3) {
                e2 = e3;
            }
        } catch (Exception e4) {
            subtypeName = "";
            e2 = e4;
        }
        TapjoyLog.e("TapjoyConnect", "getConnectionSubType error: " + e2.toString());
        return subtypeName;
    }

    private static boolean d(String str) {
        Iterator<ApplicationInfo> it = af.getInstalledApplications(0).iterator();
        while (it.hasNext()) {
            if (it.next().packageName.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String str, String str2) {
        for (FeatureInfo featureInfo : af.getSystemAvailableFeatures()) {
            if (featureInfo.name.matches(str)) {
                return af.checkPermission(str2, g.getPackageName()) == 0;
            }
        }
        return false;
    }

    private static String p() {
        String strSHA256;
        Exception e2;
        TapjoyLog.i("TapjoyConnect", "generating sessionID...");
        try {
            strSHA256 = TapjoyUtil.SHA256((System.currentTimeMillis() / 1000) + w + p);
            try {
                ac = System.currentTimeMillis();
            } catch (Exception e3) {
                e2 = e3;
                TapjoyLog.e("TapjoyConnect", "unable to generate session id: " + e2.toString());
            }
        } catch (Exception e4) {
            strSHA256 = null;
            e2 = e4;
        }
        return strSHA256;
    }

    public static Context getContext() {
        return g;
    }

    private static String q() {
        if (o()) {
            return c;
        }
        if (p != null && p.length() > 0) {
            return p;
        }
        if (q != null && q.length() > 0) {
            return q;
        }
        if (n()) {
            return c;
        }
        if (n != null && n.length() > 0) {
            return n;
        }
        TapjoyLog.e("TapjoyConnect", "Error -- no valid device identifier");
        return null;
    }

    private static String a(long j2) {
        try {
            return TapjoyUtil.SHA256(w + ":" + q() + ":" + j2 + ":" + N);
        } catch (Exception e2) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Error in computing verifier value -- " + e2.toString()));
            return "";
        }
    }

    public static String getAwardCurrencyVerifier(long time, int amount, String guid) {
        try {
            return TapjoyUtil.SHA256(w + ":" + q() + ":" + time + ":" + N + ":" + amount + ":" + guid);
        } catch (Exception e2) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Error in computing awardCurrencyVerifier -- " + e2.toString()));
            return "";
        }
    }

    private static String a(long j2, String str) {
        try {
            return TapjoyUtil.SHA256(w + ":" + q() + ":" + j2 + ":" + N + ":" + str);
        } catch (Exception e2) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Error in computing packageNamesVerifier -- " + e2.toString()));
            return "";
        }
    }

    public boolean isInitialized() {
        return this.ad;
    }

    public static void setPlugin(String name) {
        P = name;
    }

    public static void setSDKType(String name) {
        Q = name;
    }

    public static void setUserID(String id, TJSetUserIDListener listener) {
        D = id;
        l = listener;
        TapjoyLog.d("TapjoyConnect", "URL parameters: " + getURLParams());
        new Thread(new Runnable() { // from class: com.tapjoy.TapjoyConnectCore.2
            @Override // java.lang.Runnable
            public final void run() {
                TapjoyLog.i("TapjoyConnect", "Setting userID to " + TapjoyConnectCore.D);
                TapjoyHttpURLResponse responseFromURL = TapjoyConnectCore.j.getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_USER_ID_URL_PATH, TapjoyConnectCore.getURLParams());
                boolean zA = false;
                if (responseFromURL.response != null) {
                    zA = TapjoyConnectCore.a(responseFromURL.response);
                }
                TapjoyConnectCore.a(zA);
            }
        }).start();
    }

    public static void viewDidClose(String contentViewId) {
        TapjoyLog.d("TapjoyConnect", "viewDidClose: " + contentViewId);
        aj.remove(contentViewId);
        eo.e.notifyObservers();
    }

    public static void viewWillOpen(String contentViewId, int viewType) {
        TapjoyLog.d("TapjoyConnect", "viewWillOpen: " + contentViewId);
        aj.put(contentViewId, Integer.valueOf(viewType));
    }

    public static boolean isViewOpen() {
        TapjoyLog.d("TapjoyConnect", "isViewOpen: " + aj.size());
        return !aj.isEmpty();
    }

    public static boolean isFullScreenViewOpen() {
        Iterator it = aj.values().iterator();
        while (it.hasNext()) {
            switch (((Integer) it.next()).intValue()) {
                case 1:
                case 2:
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    public static void setViewShowing(boolean showing) {
        if (showing) {
            aj.put("", 1);
        } else {
            aj.clear();
        }
    }

    private static void b(String str, String str2) {
        if ((str.equals(TapjoyConnectFlag.SERVICE_URL) || str.equals(TapjoyConnectFlag.PLACEMENT_URL)) && !str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        ah.put(str, str2);
    }

    private static boolean e(String str) {
        return af.checkPermission(str, g.getPackageName()) == 0;
    }

    public void actionComplete(String actionID) {
        TapjoyLog.i("TapjoyConnect", "actionComplete: " + actionID);
        Map mapG = g();
        TapjoyUtil.safePut(mapG, TapjoyConstants.TJC_APP_ID, actionID, true);
        mapG.putAll(getTimeStampAndVerifierParams());
        TapjoyLog.d("TapjoyConnect", "PPA URL parameters: " + mapG);
        new Thread(new PPAThread(mapG)).start();
    }

    public void enablePaidAppWithActionID(String paidAppPayPerActionID) {
        byte b2 = 0;
        TapjoyLog.i("TapjoyConnect", "enablePaidAppWithActionID: " + paidAppPayPerActionID);
        Y = paidAppPayPerActionID;
        this.Z = g.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).getLong(TapjoyConstants.PREF_ELAPSED_TIME, 0L);
        TapjoyLog.d("TapjoyConnect", "paidApp elapsed: " + this.Z);
        if (this.Z >= TapjoyConstants.PAID_APP_TIME) {
            if (Y != null && Y.length() > 0) {
                TapjoyLog.d("TapjoyConnect", "Calling PPA actionComplete...");
                actionComplete(Y);
                return;
            }
            return;
        }
        if (this.aa == null) {
            this.aa = new Timer();
            this.aa.schedule(new a(this, b2), TapjoyConstants.TIMER_INCREMENT, TapjoyConstants.TIMER_INCREMENT);
        }
    }

    public void completeConnectCall() {
        boolean z2;
        String connectResult;
        TapjoyLog.d("TapjoyConnect", "starting connect call...");
        String hostURL = TapjoyConfig.TJC_CONNECT_SERVICE_URL;
        if (getHostURL() != TapjoyConfig.TJC_SERVICE_URL) {
            hostURL = getHostURL();
        }
        if (isConnected() || (connectResult = TapjoyAppSettings.getInstance().getConnectResult(s(), y.b())) == null || !a(connectResult, true)) {
            z2 = false;
        } else {
            TapjoyLog.i("TapjoyConnect", "Connect using stored connect result");
            ae = true;
            r();
            if (k != null) {
                k.onConnectSuccess();
            }
            eo.a.notifyObservers();
            z2 = true;
        }
        TapjoyHttpURLResponse responseFromURL = j.getResponseFromURL(hostURL + TapjoyConstants.TJC_CONNECT_URL_PATH, (Map) null, (Map) null, getURLParams());
        if (responseFromURL != null && responseFromURL.statusCode == 200) {
            if (a(responseFromURL.response, false)) {
                TapjoyLog.i("TapjoyConnect", "Successfully connected to Tapjoy");
                ae = true;
                r();
                for (Map.Entry entry : getGenericURLParams().entrySet()) {
                    TapjoyLog.d("TapjoyConnect", ((String) entry.getKey()) + ": " + ((String) entry.getValue()));
                }
                if (!z2) {
                    if (k != null) {
                        k.onConnectSuccess();
                    }
                    eo.a.notifyObservers();
                }
                eo.b.notifyObservers(Boolean.TRUE);
            } else {
                if (!z2) {
                    f();
                }
                eo.b.notifyObservers(Boolean.FALSE);
            }
            if (ai.length() > 0) {
                Map genericURLParams = getGenericURLParams();
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_PACKAGE_NAMES, ai, true);
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                String strA = a(jCurrentTimeMillis, ai);
                TapjoyUtil.safePut(genericURLParams, "timestamp", String.valueOf(jCurrentTimeMillis), true);
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_VERIFIER, strA, true);
                TapjoyHttpURLResponse responseFromURL2 = new TapjoyURLConnection().getResponseFromURL(getHostURL() + TapjoyConstants.TJC_SDK_LESS_CONNECT, genericURLParams);
                if (responseFromURL2 != null && responseFromURL2.statusCode == 200) {
                    TapjoyLog.d("TapjoyConnect", "Successfully pinged sdkless api.");
                    return;
                }
                return;
            }
            return;
        }
        if (!z2) {
            f();
        }
        eo.b.notifyObservers(Boolean.FALSE);
    }

    private void r() {
        TapjoyLog.d("TapjoyConnect", "Initializing mediation params");
        try {
            ArrayList<TJMediationNetwork> arrayList = (ArrayList) ah.get(TapjoyConnectFlag.MEDIATION_CONFIGS);
            if (arrayList != null) {
                TapjoyLog.i("TapjoyConnect", "Initializing mediation partners with configs");
                for (final TJMediationNetwork tJMediationNetwork : arrayList) {
                    new Thread() { // from class: com.tapjoy.TapjoyConnectCore.3
                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            if (tJMediationNetwork != null) {
                                tJMediationNetwork.init();
                            } else {
                                TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, "Failed to cast mediation connect flag into TJMediationNetwork type! Make sure to pass in an ArrayList<TJMediationNetwork> type as your mediation configs."));
                            }
                        }
                    }.run();
                }
            }
        } catch (ClassCastException e2) {
            TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, "Invalid type! Make sure to pass in an ArrayList<TJMediationNetwork> type as your mediation configs."));
        }
        String connectFlagValue = getConnectFlagValue(TapjoyConnectFlag.MEDIATION_TIMEOUT);
        if (connectFlagValue != null && !connectFlagValue.isEmpty()) {
            TJMediationSettings.getInstance().setTimeout(connectFlagValue);
            TapjoyLog.i("TapjoyConnect", "Setting mediation timeout to " + connectFlagValue + "s");
        }
    }

    public class PPAThread implements Runnable {
        private Map b;

        public PPAThread(Map map) {
            this.b = map;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            TapjoyHttpURLResponse responseFromURL = TapjoyConnectCore.j.getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_CONNECT_URL_PATH, (Map) null, (Map) null, this.b);
            if (responseFromURL.response != null) {
                TapjoyConnectCore.c(responseFromURL.response);
            }
        }
    }

    public void setCurrencyMultiplier(float multiplier) {
        TapjoyLog.i("TapjoyConnect", "setVirtualCurrencyMultiplier: " + multiplier);
        S = multiplier;
    }

    public float getCurrencyMultiplier() {
        return S;
    }

    public static String getConnectFlagValue(String key) {
        if (ah == null || ah.get(key) == null) {
            return "";
        }
        return ah.get(key).toString();
    }

    public static String getSecretKey() {
        return N;
    }

    public static String getAndroidID() {
        return n;
    }

    public static String getSha1MacAddress() {
        try {
            return TapjoyUtil.SHA1(q);
        } catch (Exception e2) {
            TapjoyLog.e("TapjoyConnect", "Error generating sha1 of macAddress: " + e2.toString());
            return null;
        }
    }

    public static String getMacAddress() {
        return q;
    }

    public static float getDeviceScreenDensityScale() {
        return B;
    }

    public static boolean isConnected() {
        return ae;
    }

    public static boolean isUnitTestMode() {
        return getConnectFlagValue("unit_test_mode") == "true";
    }

    private static String s() {
        String str = w + x + y + c + p + r;
        try {
            return TapjoyUtil.SHA1(str);
        } catch (Exception e2) {
            return str;
        }
    }

    static /* synthetic */ boolean a(String str) {
        Document documentBuildDocument = TapjoyUtil.buildDocument(str);
        if (documentBuildDocument != null) {
            String nodeTrimValue = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("PackageNames"));
            if (nodeTrimValue != null && nodeTrimValue.length() > 0) {
                Vector vector = new Vector();
                int i2 = 0;
                while (true) {
                    int iIndexOf = nodeTrimValue.indexOf(44, i2);
                    if (iIndexOf == -1) {
                        break;
                    }
                    TapjoyLog.d("TapjoyConnect", "parse: " + nodeTrimValue.substring(i2, iIndexOf).trim());
                    vector.add(nodeTrimValue.substring(i2, iIndexOf).trim());
                    i2 = iIndexOf + 1;
                }
                TapjoyLog.d("TapjoyConnect", "parse: " + nodeTrimValue.substring(i2).trim());
                vector.add(nodeTrimValue.substring(i2).trim());
                a(vector);
            }
            String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(documentBuildDocument.getElementsByTagName("Success"));
            if (nodeTrimValue2 == null || !nodeTrimValue2.equals("true")) {
                return false;
            }
        }
        return true;
    }

    static /* synthetic */ void a(boolean z2) {
        if (z2) {
            TapjoyLog.i("TapjoyConnect", "Set userID is successful");
            if (l != null) {
                l.onSetUserIDSuccess();
                return;
            }
            return;
        }
        TapjoyLog.e("TapjoyConnect", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Failed to set userID"));
        if (l != null) {
            l.onSetUserIDFailure("Failed to set userID");
        }
    }
}
