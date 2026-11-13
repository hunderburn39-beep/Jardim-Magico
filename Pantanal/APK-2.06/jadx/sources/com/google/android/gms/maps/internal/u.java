package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.RuntimeRemoteException;

/* JADX INFO: loaded from: classes.dex */
public class u {
    private static c SA;
    private static Context Sz;

    public static c A(Context context) throws GooglePlayServicesNotAvailableException {
        fq.f(context);
        if (SA != null) {
            return SA;
        }
        B(context);
        SA = C(context);
        try {
            SA.a(com.google.android.gms.dynamic.e.h(getRemoteContext(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return SA;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    private static void B(Context context) throws GooglePlayServicesNotAvailableException {
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (iIsGooglePlayServicesAvailable) {
            case 0:
                return;
            default:
                throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
        }
    }

    private static c C(Context context) {
        if (iz()) {
            Log.i(u.class.getSimpleName(), "Making Creator statically");
            return (c) c(iA());
        }
        Log.i(u.class.getSimpleName(), "Making Creator dynamically");
        return c.a.ab((IBinder) a(getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
    }

    private static <T> T a(ClassLoader classLoader, String str) {
        try {
            return (T) c(((ClassLoader) fq.f(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find dynamic class " + str);
        }
    }

    private static <T> T c(Class<?> cls) {
        try {
            return (T) cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to call the default constructor of " + cls.getName());
        } catch (InstantiationException e2) {
            throw new IllegalStateException("Unable to instantiate the dynamic class " + cls.getName());
        }
    }

    private static Context getRemoteContext(Context context) {
        if (Sz == null) {
            if (iz()) {
                Sz = context.getApplicationContext();
            } else {
                Sz = GooglePlayServicesUtil.getRemoteContext(context);
            }
        }
        return Sz;
    }

    private static Class<?> iA() {
        try {
            return Build.VERSION.SDK_INT < 15 ? Class.forName("com.google.android.gms.maps.internal.CreatorImplGmm6") : Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean iz() {
        return false;
    }
}
