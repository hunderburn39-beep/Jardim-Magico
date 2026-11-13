package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.a;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.t;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class AdvertisingIdClient {

    public static final class Info {
        private final String kw;
        private final boolean kx;

        public Info(String advertisingId, boolean limitAdTrackingEnabled) {
            this.kw = advertisingId;
            this.kx = limitAdTrackingEnabled;
        }

        public String getId() {
            return this.kw;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.kx;
        }
    }

    static Info a(Context context, a aVar) throws IOException {
        try {
            try {
                t tVarB = t.a.b(aVar.dV());
                Info info = new Info(tVarB.getId(), tVarB.a(true));
                try {
                    context.unbindService(aVar);
                } catch (IllegalArgumentException e) {
                    Log.i("AdvertisingIdClient", "getAdvertisingIdInfo unbindService failed.", e);
                }
                return info;
            } catch (Throwable th) {
                try {
                    context.unbindService(aVar);
                } catch (IllegalArgumentException e2) {
                    Log.i("AdvertisingIdClient", "getAdvertisingIdInfo unbindService failed.", e2);
                }
                throw th;
            }
        } catch (RemoteException e3) {
            Log.i("AdvertisingIdClient", "GMS remote exception ", e3);
            throw new IOException("Remote exception");
        } catch (InterruptedException e4) {
            throw new IOException("Interrupted exception");
        }
    }

    static a g(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            try {
                GooglePlayServicesUtil.s(context);
                a aVar = new a();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                if (context.bindService(intent, aVar, 1)) {
                    return aVar;
                }
                throw new IOException("Connection failure");
            } catch (GooglePlayServicesNotAvailableException e) {
                throw new IOException(e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    public static Info getAdvertisingIdInfo(Context context) throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
        fq.ak("Calling this from your main thread can lead to deadlock");
        return a(context, g(context));
    }
}
