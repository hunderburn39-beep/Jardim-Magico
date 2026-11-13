package com.fyber.ads.interstitials.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.fyber.Fyber;
import com.fyber.ads.AdFormat;
import com.fyber.ads.interstitials.InterstitialActivity;
import com.fyber.ads.interstitials.InterstitialAdCloseReason;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.mediation.MediationUserActivityListener;
import com.fyber.requesters.OfferWallRequester;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.r;
import com.fyber.utils.x;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: ExchangeInterstitial.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends InterstitialMediationAdapter<com.fyber.mediation.a.a> implements View.OnClickListener, com.fyber.c.d.d.InterfaceC0022d, MediationUserActivityListener, RequestCallback {
    private Activity a;
    private FrameLayout b;
    private Handler c;
    private WebView d;
    private WebViewClient e;
    private String f;
    private AtomicBoolean g;
    private com.fyber.c.d.d.a h;
    private com.fyber.c.d.d i;
    private d j;
    private String k;

    public b(com.fyber.mediation.a.a aVar) {
        super(aVar);
        this.g = new AtomicBoolean(false);
        this.c = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.fyber.ads.interstitials.a.b.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        FyberLogger.d("ExchangeInterstitial", "Creating webview...");
                        Context context = (Context) message.obj;
                        b.this.d = new WebView(context);
                        b.a(b.this, context, b.this.d);
                        b.this.d.getSettings().setJavaScriptEnabled(true);
                        b.this.d.setWebViewClient(b.b(b.this));
                        b.this.d.setScrollBarStyle(0);
                        b.this.d.setBackgroundColor(-16777216);
                        return false;
                    case 1:
                        if (StringUtils.notNullNorEmpty(b.this.f) && b.this.g.compareAndSet(false, true)) {
                            FyberLogger.d("ExchangeInterstitial", "Loading html...");
                            b.this.d.loadDataWithBaseURL(null, b.this.f, "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
                        }
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    @Override // com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter
    protected final void checkForAds(Context context) {
        f();
        d();
        a creativeType = getCreativeType();
        switch (creativeType) {
            case VIDEO:
                String str = (String) this.internalInterstitialAd.a(TapjoyConstants.TJC_VIDEO_URL, String.class);
                if (StringUtils.notNullNorEmpty(str)) {
                    String strA = (String) this.internalInterstitialAd.a("alert_message", String.class);
                    if (StringUtils.nullOrEmpty(strA)) {
                        strA = r.a(Fyber.Settings.UIStringIdentifier.INT_VIDEO_DIALOG_CLOSE);
                    }
                    this.h = new com.fyber.c.d.d.a().a(str).b((String) this.internalInterstitialAd.a("click_through_url", String.class)).c((String) this.internalInterstitialAd.a("click_through_text", String.class)).d(strA).a(Float.valueOf(((Number) this.internalInterstitialAd.a("close_button_delay", Number.class, Float.valueOf(BitmapDescriptorFactory.HUE_RED))).floatValue())).a(new c()).a(((Boolean) this.internalInterstitialAd.a("show_alert", Boolean.class, false)).booleanValue());
                    a(context);
                    setAdAvailable();
                } else {
                    setAdError("invalid_offer");
                }
                break;
            case STATIC:
                if (a(context)) {
                    setAdAvailable();
                } else {
                    setAdError("invalid_offer");
                }
                break;
            default:
                setAdError("invalid_offer", "trying to check ads for an unknown creative type: " + creativeType);
                break;
        }
    }

    private boolean a(Context context) {
        this.f = (String) this.internalInterstitialAd.a(TJAdUnitConstants.String.HTML, String.class);
        boolean zNotNullNorEmpty = StringUtils.notNullNorEmpty(this.f);
        if (zNotNullNorEmpty) {
            if (this.d == null) {
                Message messageObtain = Message.obtain(this.c);
                messageObtain.what = 0;
                messageObtain.obj = context;
                messageObtain.sendToTarget();
            }
            if (((Boolean) this.internalInterstitialAd.a("preload", Boolean.class, false)).booleanValue()) {
                FyberLogger.d("ExchangeInterstitial", "Preload payload - true");
                c();
            }
        }
        return zNotNullNorEmpty;
    }

    @Override // com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter
    protected final void show(Activity activity) {
        if (!b()) {
            this.a = activity;
            if (this.a instanceof InterstitialActivity) {
                ((InterstitialActivity) this.a).setMarketPlaceInterstitialListener(this);
            }
            Window window = activity.getWindow();
            window.requestFeature(1);
            window.setFlags(1024, 1024);
            a creativeType = getCreativeType();
            switch (creativeType) {
                case VIDEO:
                    if (this.h != null) {
                        this.j = new d(this.internalInterstitialAd, StringUtils.notNullNorEmpty(this.f));
                        this.i = this.h.a((com.fyber.c.d.b) this.j).a(this).a(activity);
                        activity.setRequestedOrientation(6);
                        this.i.a();
                        this.i.b();
                        activity.addContentView(this.i, new FrameLayout.LayoutParams(-1, -1));
                        interstitialShown();
                    } else {
                        interstitialError("An error occurred while trying to show the ad");
                    }
                    break;
                case STATIC:
                    a();
                    interstitialShown();
                    break;
                default:
                    interstitialError("trying to show interstitial for an unknown creative type: " + creativeType);
                    break;
            }
        }
    }

    private void a() {
        if (!b()) {
            this.a.setContentView(this.b, new FrameLayout.LayoutParams(-1, -1));
            this.b.setTag(true);
            if (!this.g.get()) {
                c();
            }
            if (getCreativeType() == a.STATIC) {
                String str = getContextData().get("orientation");
                int i = Integer.parseInt(getContextData().get("rotation"));
                boolean zD = Fyber.getConfigs().a().d();
                if (str.equalsIgnoreCase("portrait")) {
                    if (zD) {
                        if (i == 1) {
                            a(9);
                            return;
                        } else {
                            a(1);
                            return;
                        }
                    }
                    if (i == 2) {
                        a(9);
                        return;
                    } else {
                        a(1);
                        return;
                    }
                }
                if (str.equalsIgnoreCase(TJAdUnitConstants.String.LANDSCAPE)) {
                    if (zD) {
                        if (i == 2) {
                            a(8);
                            return;
                        } else {
                            a(0);
                            return;
                        }
                    }
                    if (i == 3) {
                        a(8);
                    } else {
                        a(0);
                    }
                }
            }
        }
    }

    private boolean b() {
        if (!StringUtils.notNullNorEmpty(this.k)) {
            return false;
        }
        interstitialError(this.k, "preload");
        return true;
    }

    private void c() {
        Message messageObtain = Message.obtain(this.c);
        messageObtain.what = 1;
        messageObtain.sendToTarget();
    }

    private void d() {
        e();
        if (this.b == null) {
            return;
        }
        this.b.removeAllViews();
        ViewGroup viewGroup = (ViewGroup) this.b.getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        this.b = null;
    }

    private void e() {
        if (this.i != null) {
            this.i.removeAllViews();
            this.i = null;
        }
    }

    private void f() {
        this.g.set(false);
        this.f = null;
        this.h = null;
        this.a = null;
        this.d = null;
        this.j = null;
        this.k = null;
    }

    @Override // com.fyber.mediation.MediationUserActivityListener
    public final boolean notifyOnBackPressed() {
        if (this.i != null && this.i.notifyOnBackPressed()) {
            return true;
        }
        a("back_btn", (InterstitialAdCloseReason) null);
        return false;
    }

    @Override // com.fyber.mediation.MediationUserActivityListener
    public final void notifyOnUserLeft() {
        a("app_background", (InterstitialAdCloseReason) null);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        a("abort_btn", (InterstitialAdCloseReason) null);
    }

    private void a(String str, InterstitialAdCloseReason interstitialAdCloseReason) {
        interstitialClosed(str, interstitialAdCloseReason);
        d();
        f();
    }

    private void a(int i) {
        this.a.setRequestedOrientation(i);
    }

    @Override // com.fyber.c.d.d.InterfaceC0022d
    public final void a(int i, String str) {
        this.j.a(i, str);
        if (StringUtils.notNullNorEmpty(this.f)) {
            a();
            e();
        } else if (i == 1 && !this.j.e()) {
            a(str, InterstitialAdCloseReason.ReasonVideoEnded);
        } else {
            a(str, (InterstitialAdCloseReason) null);
        }
    }

    @Override // com.fyber.requesters.RequestCallback
    public final void onAdAvailable(Intent intent) {
        this.a.startActivity(intent);
    }

    @Override // com.fyber.requesters.RequestCallback
    public final void onAdNotAvailable(AdFormat adFormat) {
    }

    @Override // com.fyber.requesters.Callback
    public final void onRequestError(RequestError requestError) {
    }

    static /* synthetic */ void a(b bVar, Context context, WebView webView) {
        bVar.b = new FrameLayout(context);
        bVar.b.setContentDescription("interstitialStaticLayout");
        bVar.b.setTag(false);
        com.fyber.c.b.a aVar = new com.fyber.c.b.a(context);
        int iA = aVar.a();
        aVar.setLayoutParams(new FrameLayout.LayoutParams(iA, iA, 53));
        webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        webView.setContentDescription("interstitialWebView");
        bVar.b.addView(webView);
        bVar.b.addView(aVar);
        aVar.setOnClickListener(bVar);
        aVar.setContentDescription("interstitialCloseButton");
    }

    static /* synthetic */ WebViewClient b(b bVar) {
        if (bVar.e == null) {
            bVar.e = new x() { // from class: com.fyber.ads.interstitials.a.b.2
                @Override // com.fyber.utils.x, android.webkit.WebViewClient
                public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    if (!super.shouldOverrideUrlLoading(webView, str)) {
                        c(str);
                        return true;
                    }
                    return true;
                }

                @Override // com.fyber.utils.x
                protected final void a(int i, String str) {
                    c(str);
                }

                @Override // com.fyber.utils.x
                protected final Activity a() {
                    return b.this.a;
                }

                @Override // com.fyber.utils.x
                protected final void a(String str, Uri uri) {
                    if (str.contains("offerwall") && uri != null) {
                        b.a(b.this, uri);
                    }
                }

                @Override // com.fyber.utils.x
                protected final void b() {
                }

                @Override // android.webkit.WebViewClient
                public final void onReceivedError(WebView webView, int i, String str, String str2) {
                    String str3 = String.format(Locale.ENGLISH, "Interstitials WebView triggered an error. Error code: %d, error description: %s. Failing URL: %s", Integer.valueOf(i), str, str2);
                    FyberLogger.e("ExchangeInterstitial", str3);
                    if (b.this.b != null && ((Boolean) b.this.b.getTag()).booleanValue()) {
                        b.this.interstitialError(str3);
                    } else {
                        FyberLogger.d("ExchangeInterstitial", "Page loading error, storing the value for the show phase - error message: " + str3);
                        b.this.k = str3;
                    }
                }

                private void c(String str) {
                    if (b.this.a != null) {
                        b.this.interstitialClicked();
                        a(str);
                    }
                }
            };
        }
        return bVar.e;
    }

    static /* synthetic */ void a(b bVar, Uri uri) {
        String queryParameter = uri.getQueryParameter("placementId");
        FyberLogger.d("ExchangeInterstitial", "Placement ID - " + queryParameter);
        OfferWallRequester.create(bVar).closeOnRedirect(true).withPlacementId(queryParameter).request(bVar.a);
    }
}
