package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;

/* JADX INFO: loaded from: classes.dex */
public final class ag extends com.google.android.gms.dynamic.g<aq> {
    private static final ag lG = new ag();

    private ag() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    public static ap a(Context context, ak akVar, String str, bp bpVar) {
        ap apVarB;
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0 && (apVarB = lG.b(context, akVar, str, bpVar)) != null) {
            return apVarB;
        }
        dw.v("Using AdManager from the client jar.");
        return new v(context, akVar, str, bpVar, new dx(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, true));
    }

    private ap b(Context context, ak akVar, String str, bp bpVar) {
        try {
            return ap.a.f(z(context).a(com.google.android.gms.dynamic.e.h(context), akVar, str, bpVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE));
        } catch (RemoteException e) {
            dw.c("Could not create remote AdManager.", e);
            return null;
        } catch (com.google.android.gms.dynamic.g.a e2) {
            dw.c("Could not create remote AdManager.", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.g
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public aq d(IBinder iBinder) {
        return aq.a.g(iBinder);
    }
}
