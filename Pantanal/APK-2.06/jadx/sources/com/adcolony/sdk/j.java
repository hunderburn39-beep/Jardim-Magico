package com.adcolony.sdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tapjoy.TapjoyConstants;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class j {
    static final String f = "3.0.4";
    static final String h = "Development";
    String a;
    boolean b;
    boolean c;
    boolean d;
    int e = 2;
    private String i = TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
    private String j = "android_native";
    String g = "";
    private JSONArray k = s.b();

    j() {
    }

    String a() {
        return com.adcolony.sdk.a.l == null ? "" : Settings.Secure.getString(com.adcolony.sdk.a.l.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
    }

    String b() {
        return this.a;
    }

    String c() {
        return System.getProperty("os.arch").toLowerCase();
    }

    void d() {
        this.b = false;
        com.adcolony.sdk.a.a("Device.get_info", new ac() { // from class: com.adcolony.sdk.j.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (j.this.n() < 14) {
                    j.this.new a(zVar, j.this, false).execute(new Void[0]);
                } else {
                    j.this.new a(zVar, j.this, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                }
            }
        });
        com.adcolony.sdk.a.a("Device.application_exists", new ac() { // from class: com.adcolony.sdk.j.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "result", au.a(s.b(zVar.b, "name")));
                s.a(jSONObjectA, "success", true);
                zVar.a(jSONObjectA).b();
            }
        });
    }

    String e() {
        return o() ? "tablet" : "phone";
    }

    boolean f() {
        return this.c;
    }

    String g() {
        if (com.adcolony.sdk.a.l == null) {
            return "";
        }
        String networkOperatorName = ((TelephonyManager) com.adcolony.sdk.a.l.getSystemService("phone")).getNetworkOperatorName();
        if (networkOperatorName.length() == 0) {
            return "unknown";
        }
        return networkOperatorName;
    }

    int h() {
        if (com.adcolony.sdk.a.l == null) {
            return 0;
        }
        Context applicationContext = com.adcolony.sdk.a.l.getApplicationContext();
        Activity activity = com.adcolony.sdk.a.l;
        return ((ActivityManager) applicationContext.getSystemService("activity")).getMemoryClass();
    }

    long i() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / ((long) 1048576);
    }

    float j() {
        return com.adcolony.sdk.a.l.getResources().getDisplayMetrics().density;
    }

    String k() {
        return com.adcolony.sdk.a.l == null ? "" : bc.a(com.adcolony.sdk.a.l);
    }

    int l() {
        if (com.adcolony.sdk.a.l == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        com.adcolony.sdk.a.l.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    int m() {
        if (com.adcolony.sdk.a.l == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        com.adcolony.sdk.a.l.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    int n() {
        return Build.VERSION.SDK_INT;
    }

    boolean o() {
        if (com.adcolony.sdk.a.l == null) {
            return false;
        }
        DisplayMetrics displayMetrics = com.adcolony.sdk.a.l.getResources().getDisplayMetrics();
        float f2 = displayMetrics.widthPixels / displayMetrics.xdpi;
        float f3 = displayMetrics.heightPixels / displayMetrics.ydpi;
        return Math.sqrt((double) ((f3 * f3) + (f2 * f2))) >= 6.0d;
    }

    String p() {
        try {
            return Locale.getDefault().getISO3Language();
        } catch (Exception e) {
            return "";
        }
    }

    String q() {
        try {
            return Locale.getDefault().getISO3Country();
        } catch (Exception e) {
            return "";
        }
    }

    String r() {
        return "";
    }

    String s() {
        return Build.MANUFACTURER;
    }

    String t() {
        return Build.MODEL;
    }

    String u() {
        return Build.VERSION.RELEASE;
    }

    JSONArray v() {
        return this.k;
    }

    int w() {
        if (com.adcolony.sdk.a.l == null) {
            return 2;
        }
        switch (com.adcolony.sdk.a.l.getResources().getConfiguration().orientation) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                return 2;
        }
    }

    boolean x() {
        if (com.adcolony.sdk.a.l == null) {
            return false;
        }
        int iW = w();
        switch (iW) {
            case 0:
                if (this.e != 1) {
                    return false;
                }
                u.d.b("Sending device info update");
                this.e = iW;
                if (n() < 14) {
                    new a(null, this, true).execute(new Void[0]);
                } else {
                    new a(null, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                }
                return true;
            case 1:
                if (this.e != 0) {
                    return false;
                }
                u.d.b("Sending device info update");
                this.e = iW;
                if (n() < 14) {
                    new a(null, this, true).execute(new Void[0]);
                } else {
                    new a(null, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                }
                return true;
            default:
                return false;
        }
    }

    class a extends AsyncTask<Void, Void, JSONObject> {
        z a;
        j b;
        boolean c;

        a(z zVar, j jVar, boolean z) {
            this.a = zVar;
            this.b = jVar;
            this.c = z;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public JSONObject doInBackground(Void... voidArr) {
            if (Build.VERSION.SDK_INT < 14) {
                return null;
            }
            return j.this.a(this.b);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(JSONObject jSONObject) {
            if (this.c) {
                new z("Device.update_info", 1, jSONObject).b();
            } else {
                this.a.a(jSONObject).b();
            }
        }
    }

    JSONObject a(j jVar) {
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TapjoyConstants.TJC_CARRIER_NAME, jVar.g());
        s.a(jSONObjectA, "data_path", com.adcolony.sdk.a.m.k.c);
        s.b(jSONObjectA, "device_api", jVar.n());
        s.a(jSONObjectA, "device_id", jVar.k());
        s.b(jSONObjectA, "display_width", jVar.l());
        s.b(jSONObjectA, "display_height", jVar.m());
        s.b(jSONObjectA, "screen_width", jVar.l());
        s.b(jSONObjectA, "screen_height", jVar.m());
        s.a(jSONObjectA, TapjoyConstants.TJC_DEVICE_TYPE_NAME, jVar.e());
        s.a(jSONObjectA, "locale_language_code", jVar.p());
        s.a(jSONObjectA, "ln", jVar.p());
        s.a(jSONObjectA, "locale_country_code", jVar.q());
        s.a(jSONObjectA, "locale", jVar.q());
        s.a(jSONObjectA, TapjoyConstants.TJC_DEVICE_MAC_ADDRESS, jVar.r());
        s.a(jSONObjectA, "manufacturer", jVar.s());
        s.a(jSONObjectA, "device_brand", jVar.s());
        s.a(jSONObjectA, "media_path", com.adcolony.sdk.a.m.k.b);
        s.a(jSONObjectA, "temp_storage_path", com.adcolony.sdk.a.m.k.d);
        s.b(jSONObjectA, "memory_class", jVar.h());
        s.b(jSONObjectA, "network_speed", 20);
        s.a(jSONObjectA, "memory_used_mb", jVar.i());
        s.a(jSONObjectA, "model", jVar.t());
        s.a(jSONObjectA, "device_model", jVar.t());
        s.a(jSONObjectA, TapjoyConstants.TJC_SDK_TYPE, "android_native");
        s.a(jSONObjectA, "sdk_version", f);
        s.a(jSONObjectA, "network_type", com.adcolony.sdk.a.m.p.c());
        s.a(jSONObjectA, TapjoyConstants.TJC_DEVICE_OS_VERSION_NAME, jVar.u());
        s.a(jSONObjectA, "os_name", TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
        s.a(jSONObjectA, TapjoyConstants.TJC_PLATFORM, TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
        s.a(jSONObjectA, "arch", jVar.c());
        s.a(jSONObjectA, "user_id", s.b(com.adcolony.sdk.a.m.v.d, "user_id"));
        s.a(jSONObjectA, TapjoyConstants.TJC_APP_ID, com.adcolony.sdk.a.m.v.a);
        s.a(jSONObjectA, "immersion", this.d);
        this.e = jVar.w();
        s.b(jSONObjectA, "current_orientation", this.e);
        JSONArray jSONArrayB = s.b();
        if (au.a(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE)) {
            jSONArrayB.put("google");
        }
        if (au.a("com.amazon.venezia")) {
            jSONArrayB.put("amazon");
        }
        s.a(jSONObjectA, "available_stores", jSONArrayB);
        if (com.adcolony.sdk.a.l != null) {
            try {
                PackageInfo packageInfo = com.adcolony.sdk.a.l.getPackageManager().getPackageInfo(com.adcolony.sdk.a.l.getPackageName(), 4096);
                if (packageInfo.requestedPermissions != null) {
                    this.k = s.b();
                    for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                        this.k.put(packageInfo.requestedPermissions[i]);
                    }
                }
            } catch (Exception e) {
            }
        }
        s.a(jSONObjectA, "permissions", this.k);
        int i2 = 40;
        while (!jVar.b && i2 > 0) {
            try {
                Thread.sleep(50L);
                i2--;
            } catch (Exception e2) {
            }
        }
        s.a(jSONObjectA, "advertiser_id", jVar.b());
        s.a(jSONObjectA, "limit_tracking", jVar.f());
        if (jVar.b() == null || jVar.b().equals("")) {
            s.a(jSONObjectA, "android_id_sha1", au.c(jVar.a()));
        }
        return jSONObjectA;
    }
}
