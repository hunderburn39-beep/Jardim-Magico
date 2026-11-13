package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.MediationServerParameters;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class bp extends bq.a {
    private Map<Class<? extends NetworkExtras>, NetworkExtras> nB;
    private Map<Class<? extends MediationAdapter>, Bundle> nC;

    private <NETWORK_EXTRAS extends com.google.ads.mediation.NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> br n(String str) throws RemoteException {
        try {
            Class<?> cls = Class.forName(str, false, bp.class.getClassLoader());
            if (com.google.ads.mediation.MediationAdapter.class.isAssignableFrom(cls)) {
                com.google.ads.mediation.MediationAdapter mediationAdapter = (com.google.ads.mediation.MediationAdapter) cls.newInstance();
                return new bw(mediationAdapter, (com.google.ads.mediation.NetworkExtras) this.nB.get(mediationAdapter.getAdditionalParametersType()));
            }
            if (MediationAdapter.class.isAssignableFrom(cls)) {
                return new bu((MediationAdapter) cls.newInstance(), this.nC.get(cls));
            }
            dw.z("Could not instantiate mediation adapter: " + str + " (not a valid adapter).");
            throw new RemoteException();
        } catch (Throwable th) {
            dw.z("Could not instantiate mediation adapter: " + str + ". " + th.getMessage());
            throw new RemoteException();
        }
    }

    public void c(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
        this.nB = map;
    }

    public void d(Map<Class<? extends MediationAdapter>, Bundle> map) {
        this.nC = map;
    }

    @Override // com.google.android.gms.internal.bq
    public br m(String str) throws RemoteException {
        return n(str);
    }
}
