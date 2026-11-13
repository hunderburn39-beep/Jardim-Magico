package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;

/* JADX INFO: loaded from: classes.dex */
public final class au {
    private AdListener lF;
    private AppEventListener lV;
    private String lX;
    private final Context mContext;
    private final bp ml;
    private final aj mm;
    private ap mn;
    private InAppPurchaseListener mp;

    public au(Context context) {
        this(context, aj.az());
    }

    public au(Context context, aj ajVar) {
        this.ml = new bp();
        this.mContext = context;
        this.mm = ajVar;
    }

    private void k(String str) throws RemoteException {
        if (this.lX == null) {
            l(str);
        }
        this.mn = ag.a(this.mContext, new ak(), this.lX, this.ml);
        if (this.lF != null) {
            this.mn.a(new af(this.lF));
        }
        if (this.lV != null) {
            this.mn.a(new am(this.lV));
        }
        if (this.mp != null) {
            this.mn.a(new cp(this.mp));
        }
    }

    private void l(String str) {
        if (this.mn == null) {
            throw new IllegalStateException("The ad unit ID must be set on InterstitialAd before " + str + " is called.");
        }
    }

    public void a(as asVar) {
        try {
            if (this.mn == null) {
                k("loadAd");
            }
            if (this.mn.a(this.mm.a(this.mContext, asVar))) {
                this.ml.c(asVar.aC());
                this.ml.d(asVar.aD());
            }
        } catch (RemoteException e) {
            dw.c("Failed to load ad.", e);
        }
    }

    public AdListener getAdListener() {
        return this.lF;
    }

    public String getAdUnitId() {
        return this.lX;
    }

    public AppEventListener getAppEventListener() {
        return this.lV;
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.mp;
    }

    public boolean isLoaded() {
        try {
            if (this.mn == null) {
                return false;
            }
            return this.mn.isReady();
        } catch (RemoteException e) {
            dw.c("Failed to check if ad is ready.", e);
            return false;
        }
    }

    public void setAdListener(AdListener adListener) {
        try {
            this.lF = adListener;
            if (this.mn != null) {
                this.mn.a(adListener != null ? new af(adListener) : null);
            }
        } catch (RemoteException e) {
            dw.c("Failed to set the AdListener.", e);
        }
    }

    public void setAdUnitId(String adUnitId) {
        if (this.lX != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on InterstitialAd.");
        }
        this.lX = adUnitId;
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        try {
            this.lV = appEventListener;
            if (this.mn != null) {
                this.mn.a(appEventListener != null ? new am(appEventListener) : null);
            }
        } catch (RemoteException e) {
            dw.c("Failed to set the AppEventListener.", e);
        }
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        try {
            this.mp = inAppPurchaseListener;
            if (this.mn != null) {
                this.mn.a(inAppPurchaseListener != null ? new cp(inAppPurchaseListener) : null);
            }
        } catch (RemoteException e) {
            dw.c("Failed to set the InAppPurchaseListener.", e);
        }
    }

    public void show() {
        try {
            l("show");
            this.mn.showInterstitial();
        } catch (RemoteException e) {
            dw.c("Failed to show interstitial.", e);
        }
    }
}
