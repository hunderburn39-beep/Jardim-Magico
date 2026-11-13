package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;

/* JADX INFO: loaded from: classes.dex */
public final class bv implements MediationBannerListener, MediationInterstitialListener {
    private final bs nG;

    public bv(bs bsVar) {
        this.nG = bsVar;
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerListener
    public void onAdClicked(MediationBannerAdapter adapter) {
        fq.aj("onClick must be called on the main UI thread.");
        dw.v("Adapter called onClick.");
        try {
            this.nG.P();
        } catch (RemoteException e) {
            dw.c("Could not call onAdClicked.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerListener
    public void onAdClosed(MediationBannerAdapter adapter) {
        fq.aj("onAdClosed must be called on the main UI thread.");
        dw.v("Adapter called onAdClosed.");
        try {
            this.nG.onAdClosed();
        } catch (RemoteException e) {
            dw.c("Could not call onAdClosed.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialListener
    public void onAdClosed(MediationInterstitialAdapter adapter) {
        fq.aj("onAdClosed must be called on the main UI thread.");
        dw.v("Adapter called onAdClosed.");
        try {
            this.nG.onAdClosed();
        } catch (RemoteException e) {
            dw.c("Could not call onAdClosed.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerListener
    public void onAdFailedToLoad(MediationBannerAdapter adapter, int errorCode) {
        fq.aj("onAdFailedToLoad must be called on the main UI thread.");
        dw.v("Adapter called onAdFailedToLoad with error. " + errorCode);
        try {
            this.nG.onAdFailedToLoad(errorCode);
        } catch (RemoteException e) {
            dw.c("Could not call onAdFailedToLoad.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialListener
    public void onAdFailedToLoad(MediationInterstitialAdapter adapter, int errorCode) {
        fq.aj("onAdFailedToLoad must be called on the main UI thread.");
        dw.v("Adapter called onAdFailedToLoad with error " + errorCode + ".");
        try {
            this.nG.onAdFailedToLoad(errorCode);
        } catch (RemoteException e) {
            dw.c("Could not call onAdFailedToLoad.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerListener
    public void onAdLeftApplication(MediationBannerAdapter adapter) {
        fq.aj("onAdLeftApplication must be called on the main UI thread.");
        dw.v("Adapter called onAdLeftApplication.");
        try {
            this.nG.onAdLeftApplication();
        } catch (RemoteException e) {
            dw.c("Could not call onAdLeftApplication.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialListener
    public void onAdLeftApplication(MediationInterstitialAdapter adapter) {
        fq.aj("onAdLeftApplication must be called on the main UI thread.");
        dw.v("Adapter called onAdLeftApplication.");
        try {
            this.nG.onAdLeftApplication();
        } catch (RemoteException e) {
            dw.c("Could not call onAdLeftApplication.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerListener
    public void onAdLoaded(MediationBannerAdapter adapter) {
        fq.aj("onAdLoaded must be called on the main UI thread.");
        dw.v("Adapter called onAdLoaded.");
        try {
            this.nG.onAdLoaded();
        } catch (RemoteException e) {
            dw.c("Could not call onAdLoaded.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialListener
    public void onAdLoaded(MediationInterstitialAdapter adapter) {
        fq.aj("onAdLoaded must be called on the main UI thread.");
        dw.v("Adapter called onAdLoaded.");
        try {
            this.nG.onAdLoaded();
        } catch (RemoteException e) {
            dw.c("Could not call onAdLoaded.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerListener
    public void onAdOpened(MediationBannerAdapter adapter) {
        fq.aj("onAdOpened must be called on the main UI thread.");
        dw.v("Adapter called onAdOpened.");
        try {
            this.nG.onAdOpened();
        } catch (RemoteException e) {
            dw.c("Could not call onAdOpened.", e);
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialListener
    public void onAdOpened(MediationInterstitialAdapter adapter) {
        fq.aj("onAdOpened must be called on the main UI thread.");
        dw.v("Adapter called onAdOpened.");
        try {
            this.nG.onAdOpened();
        } catch (RemoteException e) {
            dw.c("Could not call onAdOpened.", e);
        }
    }
}
