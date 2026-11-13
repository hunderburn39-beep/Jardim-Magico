package com.fyber.ads.banners;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.AdRequestCallback;
import com.fyber.requesters.BannerRequester;
import com.fyber.requesters.RequestError;
import com.fyber.utils.FyberLogger;

/* JADX INFO: loaded from: classes.dex */
public class BannerAdView extends FrameLayout implements BannerAdListener, AdRequestCallback {
    private BannerRequester a;
    private boolean b;
    private Activity c;
    private BannerAdListener d;
    private BannerAd e;

    public BannerAdView(Activity activity) {
        super(activity.getApplicationContext());
        this.b = false;
        this.c = activity;
        this.a = BannerRequester.create(this);
    }

    public BannerAdView(Activity activity, AttributeSet attributeSet) {
        this(activity, attributeSet, 0);
    }

    public BannerAdView(Activity activity, AttributeSet attributeSet, int i) {
        super(activity, attributeSet, i);
        this.b = false;
        this.c = activity;
        this.a = BannerRequester.create(this);
    }

    public BannerAdView withNetworkSize(@NonNull NetworkBannerSize networkBannerSize) {
        if (this.a != null) {
            this.a.withNetworkSize(networkBannerSize);
        } else if (this.c != null) {
            FyberLogger.d("BannerAdView", "This BannerAdView appears to be already displayed - this will have no effect on this instance");
        } else {
            FyberLogger.d("BannerAdView", "This BannerAdView appears to have been already destroyed");
            onAdError(null, "The \"destroy()\" method appears to have been already called");
        }
        return this;
    }

    public BannerAdView withListener(@NonNull BannerAdListener bannerAdListener) {
        this.d = bannerAdListener;
        return this;
    }

    public BannerAdView withPlacementId(String str) {
        if (this.a != null) {
            this.a.withPlacementId(str);
        } else if (this.c != null) {
            FyberLogger.d("BannerAdView", "This BannerAdView appears to be already displayed - this will have no effect on this instance");
        } else {
            FyberLogger.d("BannerAdView", "This BannerAdView appears to have been already destroyed");
            onAdError(null, "The \"destroy()\" method appears to have been already called");
        }
        return this;
    }

    public BannerAdView loadOnAttach() {
        if (this.a != null) {
            this.b = true;
        } else if (this.c != null) {
            FyberLogger.d("BannerAdView", "This BannerAdView appears to be already displayed - this will have no effect on this instance");
        } else {
            FyberLogger.d("BannerAdView", "This BannerAdView appears to have been already destroyed");
            onAdError(null, "The \"destroy()\" method appears to have been already called");
        }
        return this;
    }

    public void load() {
        if (!this.b && this.a != null) {
            this.a.request(getContext());
            this.a = null;
        } else {
            FyberLogger.d("BannerAdView", "There's no BannerWrapper for this BannerAd - this banner will not be shown");
            onAdError(null, "The \"destroy()\" method appears to have been already called");
        }
    }

    public void destroy() {
        if (this.c != null) {
            this.c = null;
            if (this.e != null) {
                this.e.destroy();
                return;
            }
            return;
        }
        FyberLogger.d("BannerAdView", "This BannerAdView appears to have been already destroyed");
        onAdError(null, "The \"destroy()\" method appears to have been already called");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.b && this.a != null) {
            this.a.request(getContext());
            this.a = null;
        }
    }

    @Override // com.fyber.requesters.AdRequestCallback
    public void onAdAvailable(Ad ad) {
        if (ad.getAdFormat() == AdFormat.BANNER) {
            this.e = (BannerAd) ad;
            this.e.displayInView(this).withListener(this).start(this.c);
        } else {
            FyberLogger.d("BannerAdView", "The ad received is not a Banner. ¯\\_(ツ)_/¯ ");
            onAdError(null, "Error occurred while requesting a banner");
        }
    }

    @Override // com.fyber.requesters.AdRequestCallback
    public void onAdNotAvailable(AdFormat adFormat) {
        if (adFormat == AdFormat.BANNER) {
            onAdError(null, "No banner available");
        } else {
            FyberLogger.d("BannerAdView", "The ad received is not a Banner. ¯\\_(ツ)_/¯ ");
            onAdError(null, "Error occurred while requesting a banner");
        }
    }

    @Override // com.fyber.requesters.Callback
    public void onRequestError(RequestError requestError) {
        FyberLogger.d("BannerAdView", "Error while requesting - " + requestError.getDescription());
        onAdError(null, "Error occurred while requesting a banner - " + requestError.getDescription());
    }

    @Override // com.fyber.ads.banners.BannerAdListener
    public void onAdLoaded(BannerAd bannerAd) {
        if (this.d != null) {
            this.d.onAdLoaded(bannerAd);
        } else {
            FyberLogger.d("BannerAdView", "onAdLoaded was called");
        }
    }

    @Override // com.fyber.ads.banners.BannerAdListener
    public void onAdClicked(BannerAd bannerAd) {
        if (this.d != null) {
            this.d.onAdClicked(bannerAd);
        } else {
            FyberLogger.d("BannerAdView", "onAdClicked was called");
        }
    }

    @Override // com.fyber.ads.banners.BannerAdListener
    public void onAdError(BannerAd bannerAd, String str) {
        if (this.d != null) {
            this.d.onAdError(bannerAd, str);
        } else {
            FyberLogger.d("BannerAdView", "onAdError was called");
        }
    }

    @Override // com.fyber.ads.banners.BannerAdListener
    public void onAdLeftApplication(BannerAd bannerAd) {
        if (this.d != null) {
            this.d.onAdLeftApplication(bannerAd);
        } else {
            FyberLogger.d("BannerAdView", "onAdLeftApplication was called");
        }
    }
}
