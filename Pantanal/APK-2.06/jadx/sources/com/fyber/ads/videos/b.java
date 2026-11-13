package com.fyber.ads.videos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.fyber.Fyber;
import com.fyber.ads.videos.mediation.TPNVideoEvent;
import com.fyber.ads.videos.mediation.TPNVideoValidationResult;
import com.fyber.cache.CacheManager;
import com.fyber.cache.internal.e;
import com.fyber.requesters.VirtualCurrencyRequester;
import com.fyber.user.User;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.i;
import com.fyber.utils.n;
import com.fyber.utils.r;
import com.fyber.utils.s;
import com.fyber.utils.x;
import com.fyber.utils.y;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: compiled from: RewardedVideoClient.java */
/* JADX INFO: loaded from: classes.dex */
public final class b implements com.fyber.c.d.d.InterfaceC0022d {
    public static final b a = new b();
    private Handler b;
    private Handler c;
    private RewardedVideoActivity d;
    private Context e;
    private WebView f;
    private String h;
    private Map<String, String> i;
    private VirtualCurrencyRequester l;
    private c m;
    private WebViewClient n;
    private WebChromeClient o;
    private com.fyber.c.d.d p;
    private com.fyber.c.d.b q;
    private com.fyber.ads.videos.mediation.c r;
    private boolean t;
    private boolean g = false;
    private boolean j = true;
    private d k = d.MUST_QUERY_SERVER_FOR_OFFERS;
    private boolean s = false;
    private boolean u = false;

    static /* synthetic */ WebView b(b bVar) {
        bVar.f = null;
        return null;
    }

    static /* synthetic */ WebViewClient c(b bVar) {
        bVar.n = null;
        return null;
    }

    static /* synthetic */ RewardedVideoActivity d(b bVar) {
        bVar.d = null;
        return null;
    }

    static /* synthetic */ Context f(b bVar) {
        bVar.e = null;
        return null;
    }

    static /* synthetic */ Map h(b bVar) {
        bVar.i = null;
        return null;
    }

    static /* synthetic */ VirtualCurrencyRequester p(b bVar) {
        bVar.l = null;
        return null;
    }

    private b() {
        HandlerThread handlerThread = new HandlerThread("RVTimer", 1);
        handlerThread.start();
        this.b = new Handler(handlerThread.getLooper(), new Handler.Callback() { // from class: com.fyber.ads.videos.b.1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        b.this.d(r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_DEFAULT));
                        break;
                    case 2:
                        FyberLogger.d("RewardedVideoClient", "Timeout reached, canceling request...");
                        b.a(b.this, 0);
                        break;
                }
                return true;
            }
        });
        this.c = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.fyber.ads.videos.b.3
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                switch (message.what) {
                    case 123:
                        if (b.this.f != null) {
                            String string = message.obj.toString();
                            b.this.f.loadUrl(string, i.d());
                            if (string.equals("about:blank")) {
                                b.b(b.this);
                                b.c(b.this);
                                b.d(b.this);
                                if (b.this.l == null) {
                                    b.f(b.this);
                                }
                            }
                        }
                        break;
                    case 522:
                        b.g(b.this);
                        break;
                    default:
                        FyberLogger.e("RewardedVideoClient", "Unknown message what field");
                        break;
                }
                return true;
            }
        });
        this.r = new com.fyber.ads.videos.mediation.c();
    }

    public final boolean a(final com.fyber.a.a aVar, Context context) throws Exception {
        if (b()) {
            if (this.f == null) {
                this.e = context;
                this.t = false;
                Callable<WebView> callable = new Callable<WebView>() { // from class: com.fyber.ads.videos.b.6
                    /* JADX INFO: Access modifiers changed from: private */
                    @Override // java.util.concurrent.Callable
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public WebView call() throws Exception {
                        WebView webView = new WebView(b.this.e);
                        WebSettings settings = webView.getSettings();
                        y.a(settings);
                        y.a(webView);
                        settings.setJavaScriptEnabled(true);
                        settings.setUseWideViewPort(false);
                        webView.setBackgroundColor(-16777216);
                        if (n.b(14)) {
                            settings.setUserAgentString("Mozilla/5.0 (X11; CrOS i686 4319.74.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.57 Safari/537.36 (Sponsorpay SDK)");
                        }
                        webView.setScrollBarStyle(0);
                        webView.setWebChromeClient(b.k(b.this));
                        webView.setWebViewClient(b.l(b.this));
                        com.fyber.ads.videos.mediation.c cVar = b.this.r;
                        b.this.r.getClass();
                        webView.addJavascriptInterface(cVar, "SynchJS");
                        return webView;
                    }
                };
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    this.f = callable.call();
                } else {
                    this.f = (WebView) Fyber.getConfigs().a(callable).get();
                }
                this.f.setContentDescription("videoPlayerWebview");
            }
            this.s = false;
            Fyber.getConfigs().a((Runnable) new com.fyber.utils.d() { // from class: com.fyber.ads.videos.b.4
                @Override // com.fyber.utils.d
                public final void a() {
                    String strA = b.a(b.this, aVar);
                    b.h(b.this);
                    FyberLogger.d("RewardedVideoClient", "Loading URL: " + strA);
                    b.this.c(strA);
                    b.this.a(d.QUERYING_SERVER_FOR_OFFERS);
                    b.this.b.sendEmptyMessageDelayed(2, TapjoyConstants.TIMER_INCREMENT);
                }
            });
            return true;
        }
        FyberLogger.d("RewardedVideoClient", "RewardedVideoClient cannot request offers at this point. It might be requesting offers right now or an offer might be currently being presented to the user.");
        return false;
    }

    private boolean g() {
        if (this.e != null) {
            try {
                boolean z = this.e.getPackageManager().getApplicationInfo(this.e.getPackageName(), 128).metaData.getBoolean("FYBEnableSSLRewardedVideo", true);
                if (!z) {
                    FyberLogger.d("RewardedVideoClient", "Manifest metadata - disabling SSL");
                }
                return !z;
            } catch (PackageManager.NameNotFoundException | NullPointerException e) {
                FyberLogger.e("RewardedVideoClient", "Failed to load meta-data from Manifest: " + e.getMessage());
            }
        }
        return false;
    }

    public final boolean a(final RewardedVideoActivity rewardedVideoActivity, boolean z) {
        if (rewardedVideoActivity != null) {
            if (!this.k.a()) {
                FyberLogger.d("RewardedVideoClient", "RewardedVideoClient is not ready to show offers. Call requestOffers() and wait until your listener is called with the confirmation that offers have been received.");
            } else {
                com.fyber.cache.internal.a aVarB = CacheManager.a().b();
                String str = "";
                if (aVarB != null && !aVarB.equals(com.fyber.cache.internal.a.a)) {
                    str = String.format(Locale.ENGLISH, ", cache_config_id:'%s'", aVarB.a());
                }
                Locale locale = Locale.ENGLISH;
                CacheManager.a().d();
                String str2 = String.format(locale, "javascript:Sponsorpay.MBE.SDKInterface.do_start({cached_ad_ids:%s, downloaded_videos_count:%d%s})", e.d(), Integer.valueOf(CacheManager.a().d().a()), str);
                FyberLogger.i("RewardedVideoClient", str2);
                c(str2);
                CacheManager.a().d().c();
                this.d = rewardedVideoActivity;
                if (!z) {
                    Fyber.getConfigs();
                    Fyber.a.a(new com.fyber.utils.d() { // from class: com.fyber.ads.videos.b.5
                        @Override // com.fyber.utils.d
                        public final void a() {
                            rewardedVideoActivity.addContentView(b.this.f, new FrameLayout.LayoutParams(-1, -1));
                        }
                    });
                }
                this.b.sendEmptyMessageDelayed(1, TapjoyConstants.TIMER_INCREMENT);
                return true;
            }
        } else {
            FyberLogger.d("RewardedVideoClient", "The provided activity is null, RewardedVideoClient cannot start the engagement.");
        }
        return false;
    }

    public final void a() {
        if (this.k.equals(d.USER_ENGAGED) || this.k.equals(d.SHOWING_OFFERS) || this.k.equals(d.READY_TO_SHOW_OFFERS)) {
            if (this.k == d.USER_ENGAGED) {
                b(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_FINISHED_VALUE);
            } else {
                b(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ABORTED_VALUE);
            }
        }
    }

    public final boolean b() {
        return this.k.c() && !this.u;
    }

    public final boolean c() {
        return this.k.a();
    }

    public final void a(boolean z) {
        this.j = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (str.equals("STARTED")) {
            this.b.removeMessages(1);
            if (a(d.SHOWING_OFFERS)) {
                a(c.a.STARTED);
                return;
            }
            return;
        }
        if (!str.equals(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_FINISHED_VALUE)) {
            if (str.equals(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ABORTED_VALUE)) {
                this.b.removeMessages(1);
                c(true);
                a(c.a.CLOSE_ABORTED);
                return;
            } else if (str.equals(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ERROR)) {
                d(r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_DEFAULT));
                return;
            } else {
                if (str.equals("USER_ENGAGED")) {
                    a(d.USER_ENGAGED);
                    return;
                }
                return;
            }
        }
        if (this.l != null) {
            final VirtualCurrencyRequester virtualCurrencyRequesterWithPlacementId = VirtualCurrencyRequester.from(this.l).withPlacementId(this.h);
            this.t = true;
            this.b.postDelayed(new Runnable() { // from class: com.fyber.ads.videos.b.8
                @Override // java.lang.Runnable
                public final void run() {
                    if (b.this.e != null) {
                        virtualCurrencyRequesterWithPlacementId.request(b.this.e);
                        if (b.this.t) {
                            b.p(b.this);
                            b.f(b.this);
                            return;
                        }
                        return;
                    }
                    FyberLogger.d("RewardedVideoClient", "There's no context available to perform a VCS request");
                }
            }, 3000L);
        }
        c(false);
        a(c.a.CLOSE_FINISHED);
        if (!this.j) {
            return;
        }
        Toast.makeText(this.e, r.a(Fyber.Settings.UIStringIdentifier.RV_REWARD_NOTIFICATION), 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(boolean z) {
        if (z) {
            this.l = null;
        }
        if (this.f != null) {
            c("about:blank");
        }
        if (this.p != null) {
            this.p.a("unknown", TJAdUnitConstants.String.FORCE_CLOSE);
        }
        this.i = null;
        this.h = null;
        a(d.MUST_QUERY_SERVER_FOR_OFFERS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        if (StringUtils.notNullNorEmpty(str)) {
            Message messageObtain = Message.obtain(this.c);
            messageObtain.what = 123;
            messageObtain.obj = str;
            messageObtain.sendToTarget();
        }
    }

    public final boolean a(c cVar) {
        this.m = cVar;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(c.a aVar) {
        if (this.m != null) {
            FyberLogger.i("RewardedVideoClient", "RewardedVideoClientStatus -> " + aVar);
            this.m.didChangeStatus(aVar);
        }
    }

    public final void a(ValueCallback<Boolean> valueCallback) {
        this.r.a(this.f, valueCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(String str) {
        if (!this.g && this.f != null) {
            this.g = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this.d == null ? this.e : this.d);
            builder.setTitle(r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_TITLE)).setMessage(str).setNeutralButton(r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_BUTTON_TITLE_DISMISS), new DialogInterface.OnClickListener() { // from class: com.fyber.ads.videos.b.7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    b.this.a(c.a.ERROR);
                    b.this.c(true);
                    b.this.g = false;
                }
            });
            try {
                builder.show();
            } catch (WindowManager.BadTokenException e) {
                this.g = false;
                FyberLogger.e("RewardedVideoClient", "Unable to show the dialog window");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(d dVar) {
        if (this.k == dVar || dVar.ordinal() - this.k.ordinal() > 1) {
            return false;
        }
        this.k = dVar;
        FyberLogger.d("RewardedVideoClient", "RewardedVideoClient mStatus -> " + dVar.name());
        return true;
    }

    public final void d() {
        Message messageObtain = Message.obtain(this.c);
        messageObtain.what = 522;
        messageObtain.sendToTarget();
    }

    @Override // com.fyber.c.d.d.InterfaceC0022d
    public final void a(int i, String str) {
        this.p = null;
        this.s = true;
    }

    public final void e() {
        if (this.s && this.k == d.MUST_QUERY_SERVER_FOR_OFFERS) {
            a(c.a.CLOSE_ABORTED);
        }
    }

    public final void f() {
        if (this.k == d.SHOWING_OFFERS) {
            FyberLogger.e("RewardedVideoClient", "Connection has been lost");
            this.b.post(new Runnable() { // from class: com.fyber.ads.videos.b.2
                @Override // java.lang.Runnable
                public final void run() {
                    b.this.d(r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_OFFLINE));
                }
            });
        }
    }

    public final void b(boolean z) {
        this.u = z;
    }

    /* JADX INFO: compiled from: RewardedVideoClient.java */
    static class a implements com.fyber.c.d.b {
        private final Handler a;
        private double b;
        private String c;

        a(@NonNull Handler handler) {
            this.a = handler;
        }

        @Override // com.fyber.c.d.b
        public final void a() {
        }

        @Override // com.fyber.c.d.b
        public final void a(String str, String str2, String str3) {
            this.c = str;
        }

        @Override // com.fyber.c.d.b
        public final void a(int i) {
            this.b = TimeUnit.MILLISECONDS.toSeconds(i);
            b(String.format(Locale.ENGLISH, "%s('play', {tpn:'%s', result:'%s', duration:'%.2f', id:'%s'})", "javascript:Sponsorpay.MBE.SDKInterface.notify", "local", com.fyber.c.d.a.PlayingEvent, Double.valueOf(this.b), this.c));
        }

        @Override // com.fyber.c.d.b
        public final void b(int i) {
            b(String.format(Locale.ENGLISH, "%s('play', {tpn:'%s', result:'%s', currentTime:'%.3f', duration:'%.2f', id:'%s'})", "javascript:Sponsorpay.MBE.SDKInterface.notify", "local", com.fyber.c.d.a.TimeUpdateEvent, Double.valueOf(TimeUnit.MILLISECONDS.toSeconds(i)), Double.valueOf(this.b), this.c));
        }

        @Override // com.fyber.c.d.b
        public final void b() {
            a(com.fyber.c.d.a.EndedEvent);
        }

        @Override // com.fyber.c.d.b
        public final void c() {
            a(com.fyber.c.d.a.ClickThroughEvent);
        }

        @Override // com.fyber.c.d.b
        public final void d() {
            a(com.fyber.c.d.a.CancelEvent);
        }

        @Override // com.fyber.c.d.b
        public final void a(String str) {
        }

        private void a(com.fyber.c.d.a aVar) {
            b(String.format("%s('play', {tpn:'%s', result:'%s', id:'%s'})", "javascript:Sponsorpay.MBE.SDKInterface.notify", "local", aVar, this.c));
        }

        private void b(String str) {
            FyberLogger.d("RewardedVideoClient", "javascript client called with URL:" + str);
            if (StringUtils.notNullNorEmpty(str)) {
                Message messageObtain = Message.obtain(this.a);
                messageObtain.what = 123;
                messageObtain.obj = str;
                messageObtain.sendToTarget();
            }
        }
    }

    public final boolean a(String str) {
        if (this.k.b()) {
            this.h = str;
            a(d.MUST_QUERY_SERVER_FOR_OFFERS);
            return true;
        }
        FyberLogger.d("RewardedVideoClient", "Cannot change the placement ID while a request to the server is going on or an offer is being presented to the user.");
        return false;
    }

    public final boolean a(VirtualCurrencyRequester virtualCurrencyRequester) {
        if (this.k.b()) {
            this.l = virtualCurrencyRequester;
            a(d.MUST_QUERY_SERVER_FOR_OFFERS);
            return true;
        }
        FyberLogger.d("RewardedVideoClient", "Cannot change the currency ID while a request to the server is going on or an offer is being presented to the user.");
        return false;
    }

    public final boolean a(Map<String, String> map) {
        if (this.k.b()) {
            this.i = map;
            a(d.MUST_QUERY_SERVER_FOR_OFFERS);
            return true;
        }
        FyberLogger.d("RewardedVideoClient", "Cannot change custom parameters while a request to the server is going on or an offer is being presented to the user.");
        return false;
    }

    static /* synthetic */ void a(b bVar, int i) {
        bVar.b.removeMessages(2);
        boolean z = i > 0;
        if (z) {
            bVar.a(d.READY_TO_SHOW_OFFERS);
        } else {
            bVar.c(true);
        }
        if (bVar.m == null) {
            return;
        }
        bVar.m.didReceiveOffers(z);
    }

    static /* synthetic */ void g(b bVar) {
        if (bVar.f != null && bVar.p == null) {
            try {
                Class.forName("android.webkit.WebView").getMethod("onPause", null).invoke(bVar.f, null);
            } catch (Exception e) {
                FyberLogger.e("RewardedVideoClient", "onPause error", e);
            }
        }
    }

    static /* synthetic */ String a(b bVar, com.fyber.a.a aVar) {
        s sVarA = s.a(com.fyber.utils.e.a("videos"), aVar).e().b(bVar.h).a(bVar.i).a("rewarded", "1").a("ad_format", "video").a();
        if (bVar.g()) {
            sVarA.f();
        }
        return sVarA.g();
    }

    static /* synthetic */ WebChromeClient k(b bVar) {
        if (bVar.o == null) {
            bVar.o = new WebChromeClient() { // from class: com.fyber.ads.videos.b.10
                @Override // android.webkit.WebChromeClient
                public final boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
                    FyberLogger.d("RewardedVideoClient", "js alert - " + str2);
                    FyberLogger.d("RewardedVideoClient", "js alert - " + str2);
                    if (!b.this.g) {
                        b.this.g = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(b.this.d == null ? b.this.e : b.this.d);
                        builder.setTitle(r.a(Fyber.Settings.UIStringIdentifier.RV_FORFEIT_DIALOG_TITLE)).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.fyber.ads.videos.b.10.3
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                b.this.b(RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ABORTED_VALUE);
                                b.this.g = false;
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.fyber.ads.videos.b.10.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                b.this.g = false;
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.fyber.ads.videos.b.10.1
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                b.this.g = false;
                            }
                        });
                        builder.show();
                    }
                    jsResult.cancel();
                    return true;
                }
            };
        }
        return bVar.o;
    }

    static /* synthetic */ WebViewClient l(b bVar) {
        if (bVar.n == null) {
            bVar.n = new x(bVar.d) { // from class: com.fyber.ads.videos.b.9
                @Override // com.fyber.utils.x
                protected final void a(String str, Uri uri) {
                    if (str.equals("requestOffers")) {
                        b.a(b.this, Integer.parseInt(uri.getQueryParameter("n")));
                        return;
                    }
                    if (str.equals(TJAdUnitConstants.String.VIDEO_START)) {
                        b.this.b(uri.getQueryParameter("status"));
                        return;
                    }
                    if (str.equals("validate")) {
                        String queryParameter = uri.getQueryParameter("tpn");
                        FyberLogger.d("RewardedVideoClient", "RewardedVideo client asks to validate a third party network: " + queryParameter);
                        HashMap<String, String> map = new HashMap<>(1);
                        map.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, uri.getQueryParameter(TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        com.fyber.mediation.b.a.a(b.this.e, queryParameter, map, new com.fyber.ads.videos.mediation.a() { // from class: com.fyber.ads.videos.b.9.1
                            @Override // com.fyber.ads.videos.mediation.a
                            public final void a(String str2, String str3, TPNVideoValidationResult tPNVideoValidationResult, Map<String, String> map2) {
                                String str4 = String.format("%s('validate', {tpn:'%s', id:%s, result:'%s', adapter_version:'%s'})", "javascript:Sponsorpay.MBE.SDKInterface.notify", str2, map2.get(TMXConstants.TAG_TILE_ATTRIBUTE_ID), tPNVideoValidationResult, str3);
                                FyberLogger.d("RewardedVideoClient", "Notifying - " + str4);
                                b.this.c(str4);
                            }
                        });
                        return;
                    }
                    if (str.equals("play")) {
                        String queryParameter2 = uri.getQueryParameter("tpn");
                        if (queryParameter2.equals("local")) {
                            if (b.this.d != null) {
                                if (b.this.q == null) {
                                    b.this.q = new a(b.this.c);
                                }
                                b.this.p = new com.fyber.c.d.d.a().a(b.this.q).a(uri.getQueryParameter(TMXConstants.TAG_TILE_ATTRIBUTE_ID)).b(uri.getQueryParameter("clickThroughUrl")).d(uri.getQueryParameter("alertMessage")).e(uri.getQueryParameter("showAlert")).a(b.this).a(new com.fyber.ads.videos.a()).a(b.this.d);
                                b.this.p.a();
                                b.this.p.b();
                                b.this.d.addContentView(b.this.p, new FrameLayout.LayoutParams(-1, -1));
                                return;
                            }
                            FyberLogger.d("RewardedVideoClient", "There was an issue - we were unable to attach the video player. ");
                            return;
                        }
                        HashMap<String, String> map2 = new HashMap<>(1);
                        map2.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, uri.getQueryParameter(TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        FyberLogger.d("RewardedVideoClient", "RewardedVideo client asks to play an offer from a third party network:" + queryParameter2);
                        com.fyber.mediation.b.a.a(b.this.d, queryParameter2, map2, new com.fyber.ads.videos.mediation.b() { // from class: com.fyber.ads.videos.b.9.2
                            @Override // com.fyber.ads.videos.mediation.b
                            public final void a(String str2, String str3, TPNVideoEvent tPNVideoEvent, Map<String, String> map3) {
                                if (tPNVideoEvent == TPNVideoEvent.Started) {
                                    b.this.b("STARTED");
                                }
                                String str4 = String.format("%s('play', {tpn:'%s', id:%s, result:'%s', adapter_version:'%s'})", "javascript:Sponsorpay.MBE.SDKInterface.notify", str2, map3.get(TMXConstants.TAG_TILE_ATTRIBUTE_ID), tPNVideoEvent, str3);
                                FyberLogger.d("RewardedVideoClient", "Notifying - " + str4);
                                b.this.c(str4);
                            }
                        });
                        return;
                    }
                    if (str.equals("jud")) {
                        String strMapToString = User.mapToString();
                        if (StringUtils.nullOrEmpty(strMapToString)) {
                            strMapToString = "";
                        }
                        String str2 = String.format("javascript:Sponsorpay.MBE.SDKInterface.trigger('jud', '%s')", strMapToString);
                        FyberLogger.i("RewardedVideoClient", "JUD tracking event will be called:" + str2);
                        b.this.c(str2);
                    }
                }

                @Override // android.webkit.WebViewClient
                public final void onReceivedError(WebView webView, int i, String str, String str2) {
                    FyberLogger.d("RewardedVideoClient", "onReceivedError url - " + str2 + " - " + str);
                    if (!str2.startsWith("market://")) {
                        if (b.this.k == d.QUERYING_SERVER_FOR_OFFERS) {
                            b.this.a(c.a.ERROR);
                            b.this.c(true);
                        } else if (b.this.p != null) {
                            b.this.p.onError(null, -1, -1);
                        } else {
                            b.this.d(r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_DEFAULT));
                        }
                        super.onReceivedError(webView, i, str, str2);
                        return;
                    }
                    FyberLogger.d("RewardedVideoClient", "discarding error - market:// url");
                }

                @Override // com.fyber.utils.x
                protected final void b() {
                    b.this.b("USER_ENGAGED");
                    b.this.a(c.a.PENDING_CLOSE);
                }

                @Override // com.fyber.utils.x
                protected final void c() {
                    b.this.d(r.a(Fyber.Settings.UIStringIdentifier.ERROR_PLAY_STORE_UNAVAILABLE));
                }

                @Override // com.fyber.utils.x
                protected final Activity a() {
                    return b.this.d;
                }

                @Override // com.fyber.utils.x
                protected final void a(int i, String str) {
                    RewardedVideoActivity rewardedVideoActivity = b.this.d;
                    if (rewardedVideoActivity != null) {
                        rewardedVideoActivity.setResult(i);
                        a(str);
                    }
                }
            };
        }
        return bVar.n;
    }
}
