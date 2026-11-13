package com.google.android.gms.internal;

import android.app.Activity;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

/* JADX INFO: loaded from: classes.dex */
public class jh extends com.google.android.gms.dynamic.g<jc> {
    private static jh adc;

    protected jh() {
        super("com.google.android.gms.wallet.dynamite.WalletDynamiteCreatorImpl");
    }

    public static iz a(Activity activity, com.google.android.gms.dynamic.c cVar, WalletFragmentOptions walletFragmentOptions, ja jaVar) throws GooglePlayServicesNotAvailableException {
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (iIsGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
        }
        try {
            return lY().z(activity).a(com.google.android.gms.dynamic.e.h(activity), cVar, walletFragmentOptions, jaVar);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (com.google.android.gms.dynamic.g.a e2) {
            throw new RuntimeException(e2);
        }
    }

    private static jh lY() {
        if (adc == null) {
            adc = new jh();
        }
        return adc;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.g
    /* JADX INFO: renamed from: aZ, reason: merged with bridge method [inline-methods] */
    public jc d(IBinder iBinder) {
        return jc.a.aV(iBinder);
    }
}
