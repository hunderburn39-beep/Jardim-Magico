package com.chartboost.sdk.Libraries;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import com.chartboost.sdk.impl.ak;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.tapjoy.TapjoyConstants;
import java.io.IOException;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {
    final ak a;
    final SharedPreferences b;
    private a c = null;
    private int d = -1;
    private String e = null;
    private String f = null;

    public c(ak akVar, SharedPreferences sharedPreferences) {
        this.a = akVar;
        this.b = sharedPreferences;
    }

    public void a() {
        Class<?> cls = null;
        try {
            cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
        } catch (ClassNotFoundException e) {
        }
        if (cls == null) {
            try {
                throw new ClassNotFoundException("Google play services library is missing. Unable to find class com.google.android.gms.ads.identifier.AdvertisingIdClient");
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
                CBLogging.b("CBIdentity", "WARNING: It looks like you've forgotten to include the Google Play Services library in your project. Please review the SDK documentation for more details.");
            }
        }
    }

    public static class a {
        public final int a;
        public final String b;
        public final String c;
        public final String d;

        public a(int i, String str, String str2, String str3) {
            this.a = i;
            this.b = str;
            this.c = str2;
            this.d = str3;
        }
    }

    public synchronized a b() {
        String str;
        String str2;
        boolean z;
        a aVar;
        boolean z2 = false;
        synchronized (this) {
            if (this.c != null && this.c.c != null) {
                str = this.c.c;
            } else {
                String strC = c();
                if (strC == null || "9774d56d682e549c".equals(strC)) {
                    strC = e();
                }
                str = strC;
                z2 = true;
            }
            d();
            if (this.c != null && this.c.d != null && this.c.d.equals(this.e)) {
                z = z2;
                str2 = this.c.d;
            } else {
                str2 = this.e;
                z = true;
            }
            if (this.c != null && this.c.a != this.d) {
                z = true;
            }
            if (z || this.c == null) {
                JSONObject jSONObject = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
                if (str != null) {
                    d.a(jSONObject, "uuid", str);
                }
                if (str2 != null) {
                    d.a(jSONObject, "gaid", str2);
                }
                this.c = new a(this.d, Base64.encodeToString(jSONObject.toString().getBytes(), 0), str, str2);
            }
            aVar = this.c;
        }
        return aVar;
    }

    public String c() {
        return Settings.Secure.getString(com.chartboost.sdk.i.m.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
    }

    private String e() {
        if (this.f == null && this.b != null) {
            this.f = this.b.getString("cbUUID", null);
            if (this.f == null) {
                this.f = UUID.randomUUID().toString();
                SharedPreferences.Editor editorEdit = this.b.edit();
                editorEdit.putString("cbUUID", this.f);
                editorEdit.apply();
            }
        }
        return this.f;
    }

    protected void d() throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
        Context context = com.chartboost.sdk.i.m;
        if ("Amazon".equals(Build.MANUFACTURER)) {
            b(context);
        } else {
            a(context);
        }
    }

    private void a(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
        AdvertisingIdClient.Info infoA;
        try {
            infoA = com.chartboost.sdk.impl.g.a().a(context);
        } catch (GooglePlayServicesNotAvailableException e) {
            infoA = null;
        } catch (GooglePlayServicesRepairableException e2) {
            infoA = null;
        } catch (IOException e3) {
            infoA = null;
        } catch (SecurityException e4) {
            CBLogging.a("CBIdentity", "Security Exception when retrieving AD id", e4);
            infoA = null;
        } catch (Exception e5) {
            CBLogging.a("CBIdentity", "General Exception when retrieving AD id", e5);
            com.chartboost.sdk.Tracking.a.a(getClass(), "getAdvertisingIdClientInfo", e5);
            infoA = null;
        }
        if (infoA == null) {
            this.d = -1;
            this.e = null;
        } else {
            this.d = infoA.isLimitAdTrackingEnabled() ? 1 : 0;
            this.e = infoA.getId();
        }
    }

    private void b(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        int i = Settings.Secure.getInt(contentResolver, "limit_ad_tracking", 2);
        if (i == 0) {
            this.d = 0;
            this.e = Settings.Secure.getString(contentResolver, TapjoyConstants.TJC_ADVERTISING_ID);
        } else if (i == 2) {
            this.d = -1;
            this.e = null;
        } else {
            this.d = 1;
            this.e = null;
        }
    }
}
