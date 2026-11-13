package com.google.android.gms.plus.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.fq;
import com.google.android.gms.plus.PlusOneDummyView;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    private static Context Sz;
    private static c Uj;

    public static class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    private static c D(Context context) throws a {
        fq.f(context);
        if (Uj == null) {
            if (Sz == null) {
                Sz = GooglePlayServicesUtil.getRemoteContext(context);
                if (Sz == null) {
                    throw new a("Could not get remote context.");
                }
            }
            try {
                Uj = c.a.aP((IBinder) Sz.getClassLoader().loadClass("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl").newInstance());
            } catch (ClassNotFoundException e) {
                throw new a("Could not load creator class.");
            } catch (IllegalAccessException e2) {
                throw new a("Could not access creator.");
            } catch (InstantiationException e3) {
                throw new a("Could not instantiate creator.");
            }
        }
        return Uj;
    }

    public static View a(Context context, int i, int i2, String str, int i3) {
        try {
            if (str == null) {
                throw new NullPointerException();
            }
            return (View) com.google.android.gms.dynamic.e.d(D(context).a(com.google.android.gms.dynamic.e.h(context), i, i2, str, i3));
        } catch (Exception e) {
            return new PlusOneDummyView(context, i);
        }
    }
}
