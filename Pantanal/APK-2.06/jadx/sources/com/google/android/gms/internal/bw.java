package com.google.android.gms.internal;

import android.app.Activity;
import android.os.RemoteException;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class bw<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> extends br.a {
    private final MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> nH;
    private final NETWORK_EXTRAS nI;

    public bw(MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> mediationAdapter, NETWORK_EXTRAS network_extras) {
        this.nH = mediationAdapter;
        this.nI = network_extras;
    }

    private SERVER_PARAMETERS b(String str, int i, String str2) throws RemoteException {
        HashMap map;
        try {
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                map = new HashMap(jSONObject.length());
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.getString(next));
                }
            } else {
                map = new HashMap(0);
            }
            Class<SERVER_PARAMETERS> serverParametersType = this.nH.getServerParametersType();
            if (serverParametersType == null) {
                return null;
            }
            SERVER_PARAMETERS server_parametersNewInstance = serverParametersType.newInstance();
            server_parametersNewInstance.load(map);
            return server_parametersNewInstance;
        } catch (Throwable th) {
            dw.c("Could not get MediationServerParameters.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ah ahVar, String str, bs bsVar) throws RemoteException {
        a(dVar, ahVar, str, (String) null, bsVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (!(this.nH instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting interstitial ad from adapter.");
        try {
            ((MediationInterstitialAdapter) this.nH).requestInterstitialAd(new bx(bsVar), (Activity) com.google.android.gms.dynamic.e.d(dVar), b(str, ahVar.lL, str2), by.e(ahVar), this.nI);
        } catch (Throwable th) {
            dw.c("Could not request interstitial ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ak akVar, ah ahVar, String str, bs bsVar) throws RemoteException {
        a(dVar, akVar, ahVar, str, null, bsVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.br
    public void a(com.google.android.gms.dynamic.d dVar, ak akVar, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (!(this.nH instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting banner ad from adapter.");
        try {
            ((MediationBannerAdapter) this.nH).requestBannerAd(new bx(bsVar), (Activity) com.google.android.gms.dynamic.e.d(dVar), b(str, ahVar.lL, str2), by.b(akVar), by.e(ahVar), this.nI);
        } catch (Throwable th) {
            dw.c("Could not request banner ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void destroy() throws RemoteException {
        try {
            this.nH.destroy();
        } catch (Throwable th) {
            dw.c("Could not destroy adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public com.google.android.gms.dynamic.d getView() throws RemoteException {
        if (!(this.nH instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            return com.google.android.gms.dynamic.e.h(((MediationBannerAdapter) this.nH).getBannerView());
        } catch (Throwable th) {
            dw.c("Could not get banner view from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.br
    public void pause() throws RemoteException {
        throw new RemoteException();
    }

    @Override // com.google.android.gms.internal.br
    public void resume() throws RemoteException {
        throw new RemoteException();
    }

    @Override // com.google.android.gms.internal.br
    public void showInterstitial() throws RemoteException {
        if (!(this.nH instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Showing interstitial from adapter.");
        try {
            ((MediationInterstitialAdapter) this.nH).showInterstitial();
        } catch (Throwable th) {
            dw.c("Could not show interstitial from adapter.", th);
            throw new RemoteException();
        }
    }
}
