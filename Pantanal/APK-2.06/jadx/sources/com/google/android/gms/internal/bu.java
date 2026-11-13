package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class bu extends br.a {
    private final MediationAdapter nE;
    private final Bundle nF;

    public bu(MediationAdapter mediationAdapter, Bundle bundle) {
        this.nE = mediationAdapter;
        this.nF = bundle;
    }

    private Bundle a(String str, int i, String str2) throws RemoteException {
        dw.z("Server parameters: " + str);
        try {
            Bundle bundle = new Bundle();
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                Bundle bundle2 = new Bundle();
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    bundle2.putString(next, jSONObject.getString(next));
                }
                bundle = bundle2;
            }
            if (this.nE instanceof AdMobAdapter) {
                bundle.putString("adJson", str2);
                bundle.putInt("tagForChildDirectedTreatment", i);
            }
            return bundle;
        } catch (Throwable th) {
            dw.c("Could not get Server Parameters Bundle.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ah ahVar, String str, bs bsVar) throws RemoteException {
        a(dVar, ahVar, str, (String) null, bsVar);
    }

    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (!(this.nE instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting interstitial ad from adapter.");
        try {
            ((MediationInterstitialAdapter) this.nE).requestInterstitialAd((Context) com.google.android.gms.dynamic.e.d(dVar), new bv(bsVar), a(str, ahVar.lL, str2), new bt(new Date(ahVar.lH), ahVar.lI, ahVar.lJ != null ? new HashSet(ahVar.lJ) : null, ahVar.lK, ahVar.lL), this.nF);
        } catch (Throwable th) {
            dw.c("Could not request interstitial ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ak akVar, ah ahVar, String str, bs bsVar) throws RemoteException {
        a(dVar, akVar, ahVar, str, null, bsVar);
    }

    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ak akVar, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (!(this.nE instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting banner ad from adapter.");
        try {
            ((MediationBannerAdapter) this.nE).requestBannerAd((Context) com.google.android.gms.dynamic.e.d(dVar), new bv(bsVar), a(str, ahVar.lL, str2), com.google.android.gms.ads.a.a(akVar.width, akVar.height, akVar.lS), new bt(new Date(ahVar.lH), ahVar.lI, ahVar.lJ != null ? new HashSet(ahVar.lJ) : null, ahVar.lK, ahVar.lL), this.nF);
        } catch (Throwable th) {
            dw.c("Could not request banner ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void destroy() throws RemoteException {
        try {
            this.nE.onDestroy();
        } catch (Throwable th) {
            dw.c("Could not destroy adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public com.google.android.gms.dynamic.d getView() throws RemoteException {
        if (!(this.nE instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            return com.google.android.gms.dynamic.e.h(((MediationBannerAdapter) this.nE).getBannerView());
        } catch (Throwable th) {
            dw.c("Could not get banner view from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void pause() throws RemoteException {
        try {
            this.nE.onPause();
        } catch (Throwable th) {
            dw.c("Could not pause adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void resume() throws RemoteException {
        try {
            this.nE.onResume();
        } catch (Throwable th) {
            dw.c("Could not resume adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void showInterstitial() throws RemoteException {
        if (!(this.nE instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Showing interstitial from adapter.");
        try {
            ((MediationInterstitialAdapter) this.nE).showInterstitial();
        } catch (Throwable th) {
            dw.c("Could not show interstitial from adapter.", th);
            throw new RemoteException();
        }
    }
}
