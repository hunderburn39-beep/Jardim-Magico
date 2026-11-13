package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public abstract class g<T> {
    private final String Hx;
    private T Hy;

    public static class a extends Exception {
        public a(String str) {
            super(str);
        }

        public a(String str, Throwable th) {
            super(str, th);
        }
    }

    protected g(String str) {
        this.Hx = str;
    }

    protected abstract T d(IBinder iBinder);

    protected final T z(Context context) throws a {
        if (this.Hy == null) {
            fq.f(context);
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                throw new a("Could not get remote context.");
            }
            try {
                this.Hy = d((IBinder) remoteContext.getClassLoader().loadClass(this.Hx).newInstance());
            } catch (ClassNotFoundException e) {
                throw new a("Could not load creator class.");
            } catch (IllegalAccessException e2) {
                throw new a("Could not access creator.");
            } catch (InstantiationException e3) {
                throw new a("Could not instantiate creator.");
            }
        }
        return this.Hy;
    }
}
