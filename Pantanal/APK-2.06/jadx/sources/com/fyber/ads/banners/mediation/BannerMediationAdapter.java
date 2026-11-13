package com.fyber.ads.banners.mediation;

import android.content.Context;
import com.fyber.Fyber;
import com.fyber.ads.banners.BannerSize;
import com.fyber.mediation.MediationAdapter;
import com.fyber.utils.c;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public abstract class BannerMediationAdapter<V extends MediationAdapter> {
    protected c<BannerWrapper, a> asyncCallable;
    protected V mAdapter;

    protected abstract boolean checkForAds(Context context, List<BannerSize> list);

    public BannerMediationAdapter(V v) {
        this.mAdapter = v;
    }

    protected void setAdAvailable(BannerWrapper bannerWrapper) {
        if (this.asyncCallable != null) {
            this.asyncCallable.a(bannerWrapper);
        }
    }

    protected void setAdNotAvailable() {
        if (this.asyncCallable != null) {
            this.asyncCallable.a((BannerWrapper) null);
        }
    }

    protected void setAdError(String str) {
        if (this.asyncCallable != null) {
            this.asyncCallable.a(new a(str));
        }
    }

    public final Future<BannerWrapper> a(Context context, List<BannerSize> list) {
        this.asyncCallable = new c<>();
        Future<BannerWrapper> futureA = Fyber.getConfigs().a(this.asyncCallable);
        if (!checkForAds(context, list)) {
            this.asyncCallable.a(new a("Unable to perform the request"));
        }
        return futureA;
    }
}
