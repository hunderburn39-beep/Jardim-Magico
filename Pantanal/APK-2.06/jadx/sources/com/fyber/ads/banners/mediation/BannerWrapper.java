package com.fyber.ads.banners.mediation;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class BannerWrapper {
    private BannerEventListener bannerEventListener;

    public abstract void destroy();

    public abstract View getView();

    public void onBannerLoaded() {
        if (this.bannerEventListener != null) {
            this.bannerEventListener.onBannerLoaded(getView());
        }
    }

    public void onBannerClick() {
        if (this.bannerEventListener != null) {
            this.bannerEventListener.onBannerClick(getView());
        }
    }

    public void onBannerError(String str) {
        if (this.bannerEventListener != null) {
            this.bannerEventListener.onBannerError(getView(), str);
        }
    }

    public void onBannerLeftApplication() {
        if (this.bannerEventListener != null) {
            this.bannerEventListener.onBannerLeftApplication(getView());
        }
    }

    public void setBannerEventListener(BannerEventListener bannerEventListener) {
        this.bannerEventListener = bannerEventListener;
    }
}
