package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.ads.AdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;

/* JADX INFO: loaded from: classes.dex */
public final class bx<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> implements MediationBannerListener, MediationInterstitialListener {
    private final bs nG;

    public bx(bs bsVar) {
        this.nG = bsVar;
    }

    @Override // com.google.ads.mediation.MediationBannerListener
    public void onClick(MediationBannerAdapter<?, ?> adapter) {
        dw.v("Adapter called onClick.");
        if (!dv.bD()) {
            dw.z("onClick must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.P();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdClicked.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.P();
            } catch (RemoteException e) {
                dw.c("Could not call onAdClicked.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationBannerListener
    public void onDismissScreen(MediationBannerAdapter<?, ?> adapter) {
        dw.v("Adapter called onDismissScreen.");
        if (!dv.bD()) {
            dw.z("onDismissScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdClosed();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdClosed.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdClosed();
            } catch (RemoteException e) {
                dw.c("Could not call onAdClosed.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationInterstitialListener
    public void onDismissScreen(MediationInterstitialAdapter<?, ?> adapter) {
        dw.v("Adapter called onDismissScreen.");
        if (!dv.bD()) {
            dw.z("onDismissScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdClosed();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdClosed.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdClosed();
            } catch (RemoteException e) {
                dw.c("Could not call onAdClosed.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationBannerListener
    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> adapter, final AdRequest.ErrorCode errorCode) {
        dw.v("Adapter called onFailedToReceiveAd with error. " + errorCode);
        if (!dv.bD()) {
            dw.z("onFailedToReceiveAd must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdFailedToLoad(by.a(errorCode));
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdFailedToLoad.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdFailedToLoad(by.a(errorCode));
            } catch (RemoteException e) {
                dw.c("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationInterstitialListener
    public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> adapter, final AdRequest.ErrorCode errorCode) {
        dw.v("Adapter called onFailedToReceiveAd with error " + errorCode + ".");
        if (!dv.bD()) {
            dw.z("onFailedToReceiveAd must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdFailedToLoad(by.a(errorCode));
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdFailedToLoad.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdFailedToLoad(by.a(errorCode));
            } catch (RemoteException e) {
                dw.c("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationBannerListener
    public void onLeaveApplication(MediationBannerAdapter<?, ?> adapter) {
        dw.v("Adapter called onLeaveApplication.");
        if (!dv.bD()) {
            dw.z("onLeaveApplication must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdLeftApplication();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdLeftApplication.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdLeftApplication();
            } catch (RemoteException e) {
                dw.c("Could not call onAdLeftApplication.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationInterstitialListener
    public void onLeaveApplication(MediationInterstitialAdapter<?, ?> adapter) {
        dw.v("Adapter called onLeaveApplication.");
        if (!dv.bD()) {
            dw.z("onLeaveApplication must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdLeftApplication();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdLeftApplication.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdLeftApplication();
            } catch (RemoteException e) {
                dw.c("Could not call onAdLeftApplication.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationBannerListener
    public void onPresentScreen(MediationBannerAdapter<?, ?> adapter) {
        dw.v("Adapter called onPresentScreen.");
        if (!dv.bD()) {
            dw.z("onPresentScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdOpened();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdOpened.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdOpened();
            } catch (RemoteException e) {
                dw.c("Could not call onAdOpened.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationInterstitialListener
    public void onPresentScreen(MediationInterstitialAdapter<?, ?> adapter) {
        dw.v("Adapter called onPresentScreen.");
        if (!dv.bD()) {
            dw.z("onPresentScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdOpened();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdOpened.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdOpened();
            } catch (RemoteException e) {
                dw.c("Could not call onAdOpened.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationBannerListener
    public void onReceivedAd(MediationBannerAdapter<?, ?> adapter) {
        dw.v("Adapter called onReceivedAd.");
        if (!dv.bD()) {
            dw.z("onReceivedAd must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdLoaded();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdLoaded.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdLoaded();
            } catch (RemoteException e) {
                dw.c("Could not call onAdLoaded.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.MediationInterstitialListener
    public void onReceivedAd(MediationInterstitialAdapter<?, ?> adapter) {
        dw.v("Adapter called onReceivedAd.");
        if (!dv.bD()) {
            dw.z("onReceivedAd must be called on the main UI thread.");
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.bx.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        bx.this.nG.onAdLoaded();
                    } catch (RemoteException e) {
                        dw.c("Could not call onAdLoaded.", e);
                    }
                }
            });
        } else {
            try {
                this.nG.onAdLoaded();
            } catch (RemoteException e) {
                dw.c("Could not call onAdLoaded.", e);
            }
        }
    }
}
