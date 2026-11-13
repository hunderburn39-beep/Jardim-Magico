package com.adcolony.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class bo {
    private Context c;
    private Map<String, Object> b = new HashMap();
    private String d = "fuzz.tmp";
    boolean a = false;

    private bo() {
    }

    bo(Context context) {
        this.c = context;
    }

    private String s() {
        return "AdColonyPubServices";
    }

    String a() {
        return (this.b == null || !this.b.containsKey("appVersion") || this.b.get("appVersion") == null) ? "" : (String) this.b.get("appVersion");
    }

    String b() {
        return (this.b == null || !this.b.containsKey("androidVersion") || this.b.get("androidVersion") == null) ? "" : (String) this.b.get("androidVersion");
    }

    int c() {
        if (this.b == null || !this.b.containsKey("androidSdkVersion") || this.b.get("androidSdkVersion") == null) {
            return 0;
        }
        return ((Integer) this.b.get("androidSdkVersion")).intValue();
    }

    String d() {
        return (this.b == null || !this.b.containsKey("manufacturer") || this.b.get("manufacturer") == null) ? "" : (String) this.b.get("manufacturer");
    }

    String e() {
        return (this.b == null || !this.b.containsKey("model") || this.b.get("model") == null) ? "" : (String) this.b.get("model");
    }

    String f() {
        return (this.b == null || !this.b.containsKey("country") || this.b.get("country") == null) ? "" : (String) this.b.get("country");
    }

    String g() {
        return (this.b == null || !this.b.containsKey("countryCode") || this.b.get("countryCode") == null) ? "" : (String) this.b.get("countryCode");
    }

    String h() {
        return (this.b == null || !this.b.containsKey("currencyCode") || this.b.get("currencyCode") == null) ? "" : (String) this.b.get("currencyCode");
    }

    String i() {
        return (this.b == null || !this.b.containsKey(TapjoyConstants.TJC_PLATFORM) || this.b.get(TapjoyConstants.TJC_PLATFORM) == null) ? "" : (String) this.b.get(TapjoyConstants.TJC_PLATFORM);
    }

    String j() {
        return u();
    }

    String k() {
        return (this.b == null || !this.b.containsKey("realDeviceId") || this.b.get("realDeviceId") == null) ? "" : (String) this.b.get("realDeviceId");
    }

    String l() {
        return (this.b == null || !this.b.containsKey("sdkVersion") || this.b.get("sdkVersion") == null) ? "" : (String) this.b.get("sdkVersion");
    }

    float m() {
        if (this.b == null || !this.b.containsKey("contentScale") || this.b.get("contentScale") == null) {
            return 2.0f;
        }
        return ((Float) this.b.get("contentScale")).floatValue();
    }

    Map<String, Object> n() {
        if (this.b == null || !this.b.containsKey("applicationCapabilities") || this.b.get("applicationCapabilities") == null) {
            return null;
        }
        return (Map) this.b.get("applicationCapabilities");
    }

    String o() {
        return new JSONObject(new TreeMap(this.b)).toString();
    }

    void p() {
        this.b.clear();
        this.c = bv.aN().l();
        if (this.c != null) {
            String packageName = bv.aN().l().getPackageName();
            try {
                PackageInfo packageInfo = bv.aN().l().getPackageManager().getPackageInfo(packageName, 0);
                int i = packageInfo.versionCode;
                String str = packageInfo.versionName;
                long j = packageInfo.firstInstallTime;
                long j2 = packageInfo.lastUpdateTime;
                String strB = ck.b(j);
                String strB2 = ck.b(j2);
                this.b.put("appInstallTime", strB);
                this.b.put("appUpdateTime", strB2);
                this.b.put("appVersionCode", Integer.valueOf(i));
                this.b.put("appVersion", str);
            } catch (Exception e) {
                bw.a(s(), "Caught exception in device data update: " + e.getMessage(), e);
            }
            String strAk = bv.aN().ak();
            if (strAk == null) {
                strAk = "";
            }
            String strE = AdColony.e();
            String strD = AdColony.d();
            String str2 = Build.VERSION.RELEASE;
            String userID = AdColony.getAppOptions().getUserID();
            bw.b(s(), "advertisingID set in deviceData=" + strD, true);
            bw.b(s(), "pushToken set in deviceData=" + strAk, true);
            this.b.put("userId", userID);
            this.b.put("deviceIdAlt", strE);
            this.b.put("pushDeviceToken", strAk);
            this.b.put("advertisingId", strD);
            this.b.put("osVersion", str2);
            this.b.put("bundleId", packageName);
            this.b.put("androidVersion", Build.VERSION.RELEASE);
            this.b.put("androidSdkVersion", Integer.valueOf(Build.VERSION.SDK_INT));
            this.b.put("manufacturer", Build.MANUFACTURER);
            this.b.put("model", Build.MODEL);
            this.b.put("locale", Locale.getDefault().toString());
            this.b.put("countryCode", Locale.getDefault().getCountry().toString());
            this.b.put("currencyCode", Currency.getInstance(Locale.getDefault()).getCurrencyCode());
            this.b.put("languageCode", Locale.getDefault().getDisplayLanguage());
            this.b.put(TapjoyConstants.TJC_PLATFORM, TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
            this.b.put("deviceId", u());
            this.b.put("contentScale", Float.valueOf(ck.a()));
            this.b.put("applicationCapabilities", bv.aN().X());
            this.b.put("sdkVersion", bm.G());
            this.b.put("initParams", bv.aN().aI());
            this.b.put("screenHeight", Integer.valueOf(ck.b()));
            this.b.put("screenWidth", Integer.valueOf(ck.c()));
            TimeZone timeZone = TimeZone.getDefault();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset());
            boolean zInDaylightTime = timeZone.inDaylightTime(new Date(System.currentTimeMillis()));
            this.b.put("timeZone", timeZone.getDisplayName());
            this.b.put("timeZoneGmtOffsetMinutes", Long.valueOf(minutes));
            this.b.put("timeZoneIsDst", Boolean.valueOf(zInDaylightTime));
        }
        if (!t()) {
            bv.aN().a(bn.a.YVOLVER_ERROR_MISC, "Invalid device data", true, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
        }
    }

    void q() {
        bw.b(s(), "reset deviceID", true);
        if (!b(v())) {
            bv.aN().a(bn.a.YVOLVER_ERROR_MISC, "Failed to reset deviceID", false, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
        }
    }

    void r() {
        File fileStreamPath = this.c.getFileStreamPath(this.d);
        if (fileStreamPath.exists()) {
            fileStreamPath.delete();
        }
    }

    private boolean t() {
        return (this.b == null || this.b.isEmpty() || a() == null || j() == null) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00c4  */
    private String u() {
        String string;
        String str;
        String str2 = null;
        try {
            if (!this.c.getFileStreamPath(this.d).exists()) {
                str = null;
            } else {
                ObjectInputStream objectInputStream = new ObjectInputStream(this.c.openFileInput(this.d));
                str = (String) objectInputStream.readObject();
                try {
                    objectInputStream.close();
                } catch (Exception e) {
                    str2 = str;
                }
            }
            str2 = str;
        } catch (Exception e2) {
        }
        if (str2 == null) {
            bw.b(s(), "Problem getting deviceID. generating a new one.", true);
            string = v();
            if (!b(string)) {
                string = str2;
            }
        } else {
            string = str2;
        }
        if (string != null) {
            StringBuilder sb = new StringBuilder();
            char cCharAt = string.charAt(0);
            char cCharAt2 = string.charAt(19);
            char cCharAt3 = string.charAt(39);
            String strSubstring = string.substring(1, 19);
            String strSubstring2 = string.substring(21, 39);
            sb.append(cCharAt3);
            sb.append(cCharAt2);
            sb.append(cCharAt);
            sb.append("-");
            sb.append(strSubstring);
            sb.append(strSubstring2);
            string = sb.toString();
            if (string.charAt(0) != 'A' || string.charAt(1) != 'N' || string.charAt(2) != 'D') {
                bw.b(s(), "invalid deviceID", true);
            }
            bw.b(s(), "deviceID: " + string, true);
        } else {
            bw.b(s(), "Could not get deviceID", true);
        }
        return string;
    }

    private String v() {
        bw.b(s(), "generateDeviceID()", true);
        String string = UUID.randomUUID().toString();
        bw.b(s(), "generated deviceID=" + string, true);
        StringBuilder sb = new StringBuilder();
        String strSubstring = string.substring(0, 18);
        String strSubstring2 = string.substring(18, 36);
        sb.append(strSubstring);
        sb.append(strSubstring2);
        sb.insert(0, 'D');
        sb.insert(19, 'N');
        sb.insert(20, '$');
        sb.append('A');
        String string2 = sb.toString();
        bw.b(s(), "fuzzified: " + string2, true);
        return string2;
    }

    private boolean b(String str) {
        bw.b(s(), "saveDeviceID()", true);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.c.openFileOutput(this.d, 0));
            objectOutputStream.writeObject(str);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            bw.b(s(), "couldn't save " + this.d, true);
            return false;
        }
    }

    boolean a(String str) {
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(0, 18);
        String strSubstring2 = str.substring(18, 36);
        sb.append(strSubstring);
        sb.append(strSubstring2);
        sb.insert(0, 'D');
        sb.insert(19, 'N');
        sb.insert(20, '$');
        sb.append('A');
        String string = sb.toString();
        bw.b(s(), "fuzzified: " + string, true);
        return b(string);
    }
}
