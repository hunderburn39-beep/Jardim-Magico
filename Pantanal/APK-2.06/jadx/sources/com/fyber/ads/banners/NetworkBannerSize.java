package com.fyber.ads.banners;

/* JADX INFO: loaded from: classes.dex */
public class NetworkBannerSize {
    private final String a;
    private final BannerSize b;

    public NetworkBannerSize(String str, BannerSize bannerSize) {
        this.a = str;
        this.b = bannerSize;
    }

    public String getNetwork() {
        return this.a;
    }

    public BannerSize getSize() {
        return this.b;
    }

    public String toString() {
        return this.a + " " + this.b.toString();
    }
}
