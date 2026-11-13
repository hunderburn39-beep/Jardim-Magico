package com.fyber.ads.banners;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.fyber.Fyber;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.ads.banners.a.b;
import com.fyber.ads.banners.mediation.BannerEventListener;
import com.fyber.ads.banners.mediation.BannerWrapper;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.d;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class BannerAd extends Ad implements BannerEventListener {
    public static final int POSITION_BOTTOM = 80;
    public static final int POSITION_TOP = 48;
    protected ViewGroup a;
    protected BannerAdListener b;
    private final b c;
    private BannerWrapper d;
    private AtomicInteger e;
    private FrameLayout f;
    private int g;

    /* synthetic */ BannerAd(a aVar, byte b) {
        this(aVar);
    }

    static /* synthetic */ BannerWrapper e(BannerAd bannerAd) {
        bannerAd.d = null;
        return null;
    }

    private BannerAd(a aVar) {
        this.e = new AtomicInteger(0);
        this.g = 80;
        this.c = aVar.a;
        this.d = aVar.b;
        this.d.setBannerEventListener(this);
    }

    public BannerAd withListener(BannerAdListener bannerAdListener) {
        this.b = bannerAdListener;
        return this;
    }

    public BannerAd displayInView(ViewGroup viewGroup) {
        if (this.d == null) {
            a("This BannerAd appears to have been already destroyed");
        } else {
            this.a = viewGroup;
        }
        return this;
    }

    public BannerAd withPosition(int i) {
        if (this.d == null) {
            a("This BannerAd appears to have been already destroyed");
        } else if (i == 80 || i == 48) {
            this.g = i;
        }
        return this;
    }

    public BannerAd displayAtTop() {
        return withPosition(48);
    }

    public BannerAd displayAtBottom() {
        return withPosition(80);
    }

    public void hide() {
        if (this.d != null) {
            Fyber.getConfigs();
            Fyber.a.a(new d() { // from class: com.fyber.ads.banners.BannerAd.1
                @Override // com.fyber.utils.d
                public final void a() {
                    if (BannerAd.this.a == null) {
                        if (BannerAd.this.f != null) {
                            BannerAd.this.f.setVisibility(8);
                            return;
                        }
                        return;
                    }
                    BannerAd.this.a.setVisibility(8);
                }
            });
        } else {
            a("This BannerAd appears to have been already destroyed");
        }
    }

    public void show() {
        if (this.d != null) {
            Fyber.getConfigs();
            Fyber.a.a(new d() { // from class: com.fyber.ads.banners.BannerAd.2
                @Override // com.fyber.utils.d
                public final void a() {
                    if (BannerAd.this.a == null) {
                        if (BannerAd.this.f != null) {
                            BannerAd.this.f.setVisibility(0);
                            return;
                        }
                        return;
                    }
                    BannerAd.this.a.setVisibility(0);
                }
            });
        } else {
            a("This BannerAd appears to have been already destroyed");
        }
    }

    @Override // com.fyber.ads.Ad
    public void start(final Activity activity) {
        if (this.d == null) {
            a("There's no BannerWrapper for this BannerAd - this banner will not be shown");
        } else {
            if (com.fyber.ads.banners.a.a.a().a()) {
                com.fyber.ads.banners.a.a.a(com.fyber.ads.a.b.SHOWING_OFFERS);
                Fyber.getConfigs();
                Fyber.a.a(new d() { // from class: com.fyber.ads.banners.BannerAd.3
                    @Override // com.fyber.utils.d
                    public final void a() {
                        if (BannerAd.this.a != null) {
                            BannerAd.this.a.addView(BannerAd.this.d.getView());
                        } else {
                            BannerAd.this.f = new FrameLayout(activity.getApplicationContext());
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2, BannerAd.this.g | 1);
                            BannerAd.this.f.addView(BannerAd.this.d.getView());
                            activity.addContentView(BannerAd.this.f, layoutParams);
                        }
                        new com.fyber.b.a.b.a(com.fyber.ads.a.a.ShowImpression).a(BannerAd.this.c).b();
                        BannerAd.this.onBannerLoaded(BannerAd.this.d.getView());
                    }
                });
                return;
            }
            onBannerError(null, "A banner is already being displayed");
        }
    }

    public void destroy() {
        if (this.d != null) {
            Fyber.getConfigs();
            Fyber.a.a(new d() { // from class: com.fyber.ads.banners.BannerAd.4
                @Override // com.fyber.utils.d
                public final void a() {
                    View view = BannerAd.this.d.getView();
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(view);
                    }
                    if (BannerAd.this.f != null) {
                        ViewGroup viewGroup2 = (ViewGroup) BannerAd.this.f.getParent();
                        if (viewGroup2 != null) {
                            viewGroup2.removeView(view);
                        }
                        BannerAd.this.f = null;
                    }
                    BannerAd.this.d.destroy();
                    BannerAd.e(BannerAd.this);
                    com.fyber.ads.banners.a.a.a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
                }
            });
            FyberLogger.d("BannerAd", "\"destroy()\" has been called on this BannerAd instance");
            return;
        }
        a("\"destroy()\" was already called on this BannerAd instance");
    }

    @Override // com.fyber.ads.Ad
    public boolean canStart() {
        return com.fyber.ads.banners.a.a.a().a() && this.d != null;
    }

    @Override // com.fyber.ads.Ad
    public AdFormat getAdFormat() {
        return AdFormat.BANNER;
    }

    @Override // com.fyber.ads.banners.mediation.BannerEventListener
    public void onBannerLoaded(View view) {
        if (!this.e.compareAndSet(0, 1)) {
            new com.fyber.b.a.b.a(com.fyber.ads.a.a.ShowRotation).a(Collections.singletonMap("position", String.valueOf(this.e.getAndIncrement()))).a(this.c).b();
        }
        if (this.b != null) {
            this.b.onAdLoaded(this);
        }
    }

    @Override // com.fyber.ads.banners.mediation.BannerEventListener
    public void onBannerClick(View view) {
        new com.fyber.b.a.b.a(com.fyber.ads.a.a.ShowClick).a(this.c).b();
        if (this.b != null) {
            this.b.onAdClicked(this);
        }
    }

    @Override // com.fyber.ads.banners.mediation.BannerEventListener
    public void onBannerError(View view, String str) {
        new com.fyber.b.a.b.a(com.fyber.ads.a.a.ShowError).a(this.c).b();
        if (this.b != null) {
            this.b.onAdError(this, str);
        }
    }

    @Override // com.fyber.ads.banners.mediation.BannerEventListener
    public void onBannerLeftApplication(View view) {
        if (this.b != null) {
            this.b.onAdLeftApplication(this);
        }
    }

    private void a(String str) {
        FyberLogger.d("BannerAd", str);
        onBannerError(null, "The \"destroy()\" method appears to have been already called");
    }

    public static class a {
        public b a;
        public BannerWrapper b;

        public a(b bVar, BannerWrapper bannerWrapper) {
            this.a = bVar;
            this.b = bannerWrapper;
        }

        public final BannerAd a() {
            return new BannerAd(this, (byte) 0);
        }
    }
}
