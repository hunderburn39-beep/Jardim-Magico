package com.chartboost.sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.ac;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.tapjoy.TapjoyConstants;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {

    public interface a {
        void a();
    }

    public static void a(String str) {
        if (i.d == null) {
            CBLogging.b("CBConfig", "Set a valid CBFramework first");
        } else if (TextUtils.isEmpty(str)) {
            CBLogging.b("CBConfig", "Invalid Version String");
        } else {
            i.b = str;
        }
    }

    public static boolean a(AtomicReference<com.chartboost.sdk.Model.c> atomicReference, JSONObject jSONObject, SharedPreferences sharedPreferences) {
        try {
            atomicReference.set(new com.chartboost.sdk.Model.c(jSONObject));
            return true;
        } catch (Exception e) {
            com.chartboost.sdk.Tracking.a.a(b.class, "updateConfig", e);
            return false;
        }
    }

    public static void a(final h hVar, final a aVar, ak akVar, final AtomicReference<com.chartboost.sdk.Model.c> atomicReference, final SharedPreferences sharedPreferences) {
        hVar.j = true;
        aj ajVar = new aj("/api/config", akVar);
        ajVar.a(ac.b.HIGH);
        ajVar.a(com.chartboost.sdk.Libraries.f.a(com.chartboost.sdk.Libraries.f.a("status", com.chartboost.sdk.Libraries.a.b)));
        ajVar.a(new aj.a() { // from class: com.chartboost.sdk.b.1
            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, aj ajVar2) {
                hVar.j = false;
                JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(jSONObject, "response");
                if (jSONObjectA != null && b.a(atomicReference, jSONObjectA, sharedPreferences)) {
                    sharedPreferences.edit().putString("config", jSONObjectA.toString()).apply();
                }
                if (aVar != null) {
                    aVar.a();
                }
                if (!hVar.k) {
                    com.chartboost.sdk.a aVar2 = i.c;
                    if (aVar2 != null) {
                        aVar2.didInitialize();
                    }
                    hVar.k = true;
                }
            }

            @Override // com.chartboost.sdk.impl.aj.a
            public void a(JSONObject jSONObject, aj ajVar2, CBError cBError) {
                hVar.j = false;
                if (aVar != null) {
                    aVar.a();
                }
                if (!hVar.k) {
                    com.chartboost.sdk.a aVar2 = i.c;
                    if (aVar2 != null) {
                        aVar2.didInitialize();
                    }
                    hVar.k = true;
                }
            }
        });
    }

    public static boolean a() {
        return c() && b();
    }

    public static boolean b() {
        h hVarA = h.a();
        if (hVarA == null) {
            return false;
        }
        if (hVarA.p == null) {
            try {
                throw new Exception("Chartboost Weak Activity reference is null");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean a(Activity activity) {
        if (activity != null) {
            return true;
        }
        try {
            throw new Exception("Invalid activity context: Host Activity object is null, Please send a valid activity object");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean c() {
        try {
            if (h.a() == null) {
                throw new Exception("SDK Initialization error. SDK seems to be not initialized properly, check for any integration issues");
            }
            if (i.m == null) {
                throw new Exception("SDK Initialization error. Activity context seems to be not initialized properly, host activity or application context is being sent as null");
            }
            if (TextUtils.isEmpty(i.k)) {
                throw new Exception("SDK Initialization error. AppId is missing");
            }
            if (TextUtils.isEmpty(i.l)) {
                throw new Exception("SDK Initialization error. AppSignature is missing");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean b(Activity activity) {
        int i;
        int i2;
        int iCheckCallingOrSelfPermission;
        int iCheckCallingOrSelfPermission2;
        int iCheckCallingOrSelfPermission3;
        try {
            if (activity == null) {
                throw new RuntimeException("Invalid activity context passed during intitalization");
            }
            if (com.chartboost.sdk.impl.g.a().a(23)) {
                int iCheckSelfPermission = activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
                int iCheckSelfPermission2 = activity.checkSelfPermission("android.permission.ACCESS_NETWORK_STATE");
                i = iCheckSelfPermission;
                i2 = iCheckSelfPermission2;
                iCheckCallingOrSelfPermission = activity.checkSelfPermission("android.permission.INTERNET");
                iCheckCallingOrSelfPermission2 = activity.checkSelfPermission("android.permission.READ_PHONE_STATE");
                iCheckCallingOrSelfPermission3 = activity.checkSelfPermission("android.permission.ACCESS_WIFI_STATE");
            } else {
                int iCheckCallingOrSelfPermission4 = activity.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
                int iCheckCallingOrSelfPermission5 = activity.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE");
                i = iCheckCallingOrSelfPermission4;
                i2 = iCheckCallingOrSelfPermission5;
                iCheckCallingOrSelfPermission = activity.checkCallingOrSelfPermission("android.permission.INTERNET");
                iCheckCallingOrSelfPermission2 = activity.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE");
                iCheckCallingOrSelfPermission3 = activity.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE");
            }
            i.n = i != 0;
            i.o = iCheckCallingOrSelfPermission != 0;
            i.p = i2 != 0;
            i.q = iCheckCallingOrSelfPermission2 != 0;
            i.r = iCheckCallingOrSelfPermission3 != 0;
            if (i.o) {
                throw new RuntimeException("Please add the permission : android.permission.INTERNET in your android manifest.xml");
            }
            if (i.p) {
                throw new RuntimeException("Please add the permission : android.permission.ACCESS_NETWORK_STATE in your android manifest.xml");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean c(Activity activity) {
        try {
            if (activity.getPackageManager().queryIntentActivities(new Intent(activity, (Class<?>) CBImpressionActivity.class), 65536).isEmpty()) {
                throw new RuntimeException("Please add             <activity android:name=\"com.chartboost.sdk.CBImpressionActivity\"\n                  android:excludeFromRecents=\"true\"\n                  android:theme=\"@android:style/Theme.Translucent.NoTitleBar.Fullscreen\"\n                  android:configChanges=\"keyboardHidden|orientation|screenSize\"/> in your android manifest.xml");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String a(com.chartboost.sdk.Model.c cVar) {
        return !cVar.u ? TapjoyConstants.TJC_PLUGIN_NATIVE : "web";
    }
}
