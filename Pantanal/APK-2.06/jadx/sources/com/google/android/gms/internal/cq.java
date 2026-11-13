package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.purchase.InAppPurchase;

/* JADX INFO: loaded from: classes.dex */
public class cq implements InAppPurchase {
    private final cn oF;

    public cq(cn cnVar) {
        this.oF = cnVar;
    }

    @Override // com.google.android.gms.ads.purchase.InAppPurchase
    public String getProductId() {
        try {
            return this.oF.getProductId();
        } catch (RemoteException e) {
            dw.c("Could not forward getProductId to InAppPurchase", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.purchase.InAppPurchase
    public void recordPlayBillingResolution(int billingResponseCode) {
        try {
            this.oF.recordPlayBillingResolution(billingResponseCode);
        } catch (RemoteException e) {
            dw.c("Could not forward recordPlayBillingResolution to InAppPurchase", e);
        }
    }

    @Override // com.google.android.gms.ads.purchase.InAppPurchase
    public void recordResolution(int resolution) {
        try {
            this.oF.recordResolution(resolution);
        } catch (RemoteException e) {
            dw.c("Could not forward recordResolution to InAppPurchase", e);
        }
    }
}
