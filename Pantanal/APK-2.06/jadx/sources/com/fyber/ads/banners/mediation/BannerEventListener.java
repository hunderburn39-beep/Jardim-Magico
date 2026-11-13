package com.fyber.ads.banners.mediation;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface BannerEventListener {
    void onBannerClick(View view);

    void onBannerError(View view, String str);

    void onBannerLeftApplication(View view);

    void onBannerLoaded(View view);
}
