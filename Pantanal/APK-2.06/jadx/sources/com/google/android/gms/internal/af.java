package com.google.android.gms.internal;

import com.google.android.gms.ads.AdListener;

/* JADX INFO: loaded from: classes.dex */
public final class af extends ao.a {
    private final AdListener lF;

    public af(AdListener adListener) {
        this.lF = adListener;
    }

    @Override // com.google.android.gms.internal.ao
    public void onAdClosed() {
        this.lF.onAdClosed();
    }

    @Override // com.google.android.gms.internal.ao
    public void onAdFailedToLoad(int errorCode) {
        this.lF.onAdFailedToLoad(errorCode);
    }

    @Override // com.google.android.gms.internal.ao
    public void onAdLeftApplication() {
        this.lF.onAdLeftApplication();
    }

    @Override // com.google.android.gms.internal.ao
    public void onAdLoaded() {
        this.lF.onAdLoaded();
    }

    @Override // com.google.android.gms.internal.ao
    public void onAdOpened() {
        this.lF.onAdOpened();
    }
}
