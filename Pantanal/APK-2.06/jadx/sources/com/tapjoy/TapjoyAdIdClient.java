package com.tapjoy;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyAdIdClient {
    private Context a;
    private String b;
    private boolean c;

    public TapjoyAdIdClient(Context context) {
        this.a = context;
    }

    public boolean setupAdIdInfo() {
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.a);
            this.b = advertisingIdInfo.getId();
            this.c = !advertisingIdInfo.isLimitAdTrackingEnabled();
            return true;
        } catch (Error e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public boolean setupAdIdInfoReflection() {
        try {
            Class<?> cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            Method method = cls.getMethod("getAdvertisingIdInfo", Context.class);
            TapjoyLog.d("TapjoyAdIdClient", "Found method: " + method);
            Object objInvoke = method.invoke(cls, this.a);
            Method method2 = objInvoke.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]);
            Method method3 = objInvoke.getClass().getMethod("getId", new Class[0]);
            this.c = !((Boolean) method2.invoke(objInvoke, new Object[0])).booleanValue();
            this.b = (String) method3.invoke(objInvoke, new Object[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAdvertisingId() {
        return this.b;
    }

    public boolean isAdTrackingEnabled() {
        return this.c;
    }
}
