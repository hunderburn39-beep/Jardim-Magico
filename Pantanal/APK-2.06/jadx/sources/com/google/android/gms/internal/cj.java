package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class cj extends com.google.android.gms.dynamic.g<cl> {
    private static final cj oC = new cj();

    private static final class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    private cj() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static ck a(Activity activity) {
        ck ckVarC;
        try {
            if (b(activity)) {
                dw.v("Using AdOverlay from the client jar.");
                ckVarC = new cc(activity);
            } else {
                ckVarC = oC.c(activity);
            }
            return ckVarC;
        } catch (a e) {
            dw.z(e.getMessage());
            return null;
        }
    }

    private static boolean b(Activity activity) throws a {
        Intent intent = activity.getIntent();
        if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
        }
        throw new a("Ad overlay requires the useClientJar flag in intent extras.");
    }

    private ck c(Activity activity) {
        try {
            return ck.a.m(z(activity).a(com.google.android.gms.dynamic.e.h(activity)));
        } catch (RemoteException e) {
            dw.c("Could not create remote AdOverlay.", e);
            return null;
        } catch (com.google.android.gms.dynamic.g.a e2) {
            dw.c("Could not create remote AdOverlay.", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.g
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public cl d(IBinder iBinder) {
        return cl.a.n(iBinder);
    }
}
