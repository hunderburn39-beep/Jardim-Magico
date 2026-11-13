package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesUtil;

/* JADX INFO: loaded from: classes.dex */
public final class cu {

    public interface a {
        void a(cz czVar);
    }

    public static Cdo a(Context context, cx cxVar, a aVar) {
        return cxVar.kK.rt ? b(context, cxVar, aVar) : c(context, cxVar, aVar);
    }

    private static Cdo b(Context context, cx cxVar, a aVar) {
        dw.v("Fetching ad response from local ad request service.");
        cv.a aVar2 = new cv.a(context, cxVar, aVar);
        aVar2.start();
        return aVar2;
    }

    private static Cdo c(Context context, cx cxVar, a aVar) {
        dw.v("Fetching ad response from remote ad request service.");
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            return new cv.b(context, cxVar, aVar);
        }
        dw.z("Failed to connect to remote ad request service.");
        return null;
    }
}
