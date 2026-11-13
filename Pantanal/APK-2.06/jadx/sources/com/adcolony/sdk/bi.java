package com.adcolony.sdk;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MotionEventCompat;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
abstract class bi extends Activity {
    static final String a = "command";
    static final String b = "catalogPage";
    static final String c = "openedFromToast";
    static final String d = "source_id";
    static final String e = "close";
    static final String f = "redemptionResult";
    static final String g = "redemptionError";
    static final String h = "packageComplete";
    static final String i = "displayType";
    static final String j = "coordinates";
    static final String k = "x";
    static final String l = "y";
    static final String m = "enabled";
    static final String n = "callbackId";
    boolean A;
    ImageView B;
    FrameLayout.LayoutParams C;
    WebSettings D;
    int p;
    WebView q;
    long s;
    long t;
    long u;
    FrameLayout w;
    LinearLayout x;
    int y;
    String z;
    boolean o = false;
    String r = "";
    int v = 200;
    d E = d.NONE;
    e F = new e();
    Handler G = new Handler(Looper.getMainLooper());
    Boolean H = false;
    String I = "<!DOCTYPE html><html><head><title>Page Title</title><style>html, body {height: 100%;margin: 0;padding: 0;width: 100%;background-color:lightgrey;}body {display: table;}.err-msg-group {text-align: center;display: table-cell;vertical-align: middle;}</style></head><body><div class=\"err-msg-group\"><h3>Unable to Connect to Server</h3><p>Please try again later.</p></div></body></html>";

    enum d {
        NONE,
        SPLASH,
        BASE,
        FINISHED,
        ERROR
    }

    abstract void a();

    abstract void a(boolean z, String str);

    abstract void b();

    abstract void c();

    abstract String d();

    bi() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        if (i2 != 0) {
            bw.b(d(), "overlay error", true);
            this.E = d.ERROR;
            this.q.loadDataWithBaseURL("http://www.yvolver.com", this.I, "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
            return;
        }
        bw.b(d(), "the splash was requested", true);
        be.a aVarB = bv.aN().av().b("catalog_splash");
        if (aVarB != null) {
            this.q.loadDataWithBaseURL("http://www.yvolver.com", aVarB.b(), "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
        } else {
            bw.b(d(), "Unable to load splash asset", true);
            a(this.q, h());
            this.t = System.currentTimeMillis();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String h() {
        if (bv.aN().aw().a("catalog") == null || bv.aN().aw().a("catalog").isEmpty()) {
            return null;
        }
        String[] strArrSplit = (bv.aN().aw().a("catalog") + this.r).split(Pattern.quote("?"), 2);
        HashMap map = new HashMap();
        if (strArrSplit.length == 2) {
            map.putAll(ck.k(strArrSplit[1]));
        }
        map.put("device_id", bv.aN().au().j());
        map.put("consumer_key", bv.aN().r());
        map.put("sdk_version", "3.0.4");
        return strArrSplit[0] + ck.d(map);
    }

    void a(final WebView webView, final String str) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.bi.1
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                bw.b(bi.this.d(), " -- DISPATCH loadWebViewInEventHack --", true);
                bi.this.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.bi.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ck.a(webView, str);
                        b();
                    }
                });
            }
        };
    }

    @Override // android.app.Activity
    @SuppressLint({"NewApi", "RtlHardcoded"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.w = new FrameLayout(this);
        this.x = new LinearLayout(this);
        this.x.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        this.w.setBackgroundColor(0);
        this.w.addView(this.x);
        setContentView(this.w, layoutParams);
        this.s = System.currentTimeMillis();
        bw.b(d(), "openCatalogMs: " + this.s, true);
        Window window = getWindow();
        this.p = getResources().getConfiguration().orientation;
        if (bv.aN().aw().F()) {
            setRequestedOrientation(14);
            if (this.p == 2) {
                bw.b(d(), " Overlay Orientation == LANDSCAPE", true);
                this.x.setOrientation(0);
            } else if (this.p == 1) {
                bw.b(d(), " Overlay Orientation == PORTRAIT", true);
                this.x.setOrientation(1);
            }
        }
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.format = -2;
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        layoutParams2.flags = 1024;
        window.setAttributes(layoutParams2);
        window.setBackgroundDrawableResource(R.color.transparent);
        this.q = new WebView(this);
        Intent intent = getIntent();
        i();
        a(intent.getExtras());
    }

    private void i() {
        int iA = (int) ((25.0f * ck.a()) + 0.5f);
        int iA2 = (int) ((ck.a() * 25.0f) + 0.5f);
        this.C = new FrameLayout.LayoutParams(iA, iA2, 8388661);
        this.q.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 17.0f));
        this.x.addView(this.q);
        this.w.setVerticalScrollBarEnabled(false);
        this.w.setHorizontalScrollBarEnabled(false);
        this.q.setVerticalScrollBarEnabled(false);
        this.q.setHorizontalScrollBarEnabled(false);
        this.q.setOverScrollMode(2);
        a();
        this.B = new ImageView(bv.aN().l());
        be.a aVarB = bv.aN().av().b("catalog_close_button");
        if (aVarB != null) {
            byte[] bArrDecode = Base64.decode(aVarB.b(), 0);
            this.B.setImageBitmap(BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length));
        }
        int iQ = bv.aN().Q();
        int iR = bv.aN().R();
        int iIntValue = bv.aN().at().containsKey("xPos") ? ((Integer) bv.aN().at().get("xPos")).intValue() : iQ;
        int iIntValue2 = bv.aN().at().containsKey("yPos") ? ((Integer) bv.aN().at().get("yPos")).intValue() : iR;
        bw.b(d(), "buttonOffset = (" + iIntValue + ", " + iIntValue2 + ")", true);
        int iA3 = (int) (iIntValue * ck.a());
        int iA4 = (int) (iIntValue2 * ck.a());
        this.C.width = iA;
        this.C.height = iA2;
        this.C.gravity = 53;
        this.C.rightMargin = iA3;
        this.C.topMargin = iA4;
        this.w.addView(this.B);
        this.B.setLayoutParams(this.C);
        this.B.setVisibility(0);
        this.B.setOnClickListener(new View.OnClickListener() { // from class: com.adcolony.sdk.bi.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                cl.b().d();
            }
        });
        this.D = this.q.getSettings();
        this.q.setBackgroundColor(Color.argb(1, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK));
        this.D.setSupportZoom(false);
        this.D.setDomStorageEnabled(true);
        this.D.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        this.D.setAllowFileAccess(true);
        this.D.setAppCacheEnabled(true);
        this.D.setJavaScriptEnabled(true);
        this.D.setCacheMode(-1);
        bw.b(d(), "User Agent: " + this.D.getUserAgentString(), true);
        this.q.setWebChromeClient(new WebChromeClient() { // from class: com.adcolony.sdk.bi.3
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage cm) {
                bw.b(bi.this.d(), " " + cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId(), true);
                return true;
            }
        });
        this.q.addJavascriptInterface(new ba(new c(this.q)), "Android");
        if (Build.VERSION.SDK_INT >= 23) {
            this.q.setWebViewClient(new b());
        } else {
            this.q.setWebViewClient(new a());
        }
        b();
        a(0);
    }

    class c implements cn {
        private WebView b;

        public c(WebView webView) {
            this.b = null;
            this.b = webView;
        }

        @Override // com.adcolony.sdk.cn
        public WebView a() {
            return this.b;
        }

        @Override // com.adcolony.sdk.cn
        @SuppressLint({"RtlHardcoded"})
        public void a(final Map<String, Object> map) {
            bi.this.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.bi.c.1
                @Override // java.lang.Runnable
                public void run() {
                    if (map.containsKey("x") && map.containsKey("y")) {
                        Number number = (Number) map.get("x");
                        Number number2 = (Number) map.get("y");
                        int iIntValue = number.intValue();
                        int iIntValue2 = number2.intValue();
                        bi.this.C.rightMargin = (int) (iIntValue * ck.a());
                        bi.this.C.topMargin = (int) (iIntValue2 * ck.a());
                        bi.this.C.gravity = 53;
                    }
                    if (map.containsKey("enabled")) {
                        if (((Boolean) map.get("enabled")).booleanValue()) {
                            bi.this.B.setVisibility(0);
                        } else {
                            bi.this.B.setVisibility(4);
                        }
                    }
                    bi.this.B.setLayoutParams(bi.this.C);
                }
            });
        }

        @Override // com.adcolony.sdk.cn
        public void b() {
            bw.b(bi.this.d(), " OverlayActivityImpl::handleClose", true);
            cl.b().d();
        }

        @Override // com.adcolony.sdk.cn
        public void c() {
        }

        @Override // com.adcolony.sdk.cn
        public void a(String str, String str2) {
        }
    }

    class a extends WebViewClient {
        a() {
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!bi.this.o && bi.this.E != d.ERROR) {
                bi.this.o = true;
                bi.this.G.postDelayed(bi.this.F, bv.aN().aw().A());
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (view.getUrl() == null || bi.this.H.booleanValue()) {
                bw.b(bi.this.d(), "Unknown err with webview's URL", true);
                return;
            }
            bw.b(bi.this.d(), "onPageFinished " + view.getUrl() + " progress: " + Integer.toString(view.getProgress()), true);
            bw.b(bi.this.d(), " catalogUrl: " + bi.this.h(), true);
            if (bi.this.E == d.SPLASH) {
                bi.this.E = d.BASE;
                bi.this.u = System.currentTimeMillis() - bi.this.t;
                bw.b(bi.this.d(), "LoadTime: " + bi.this.u, true);
            }
            if (!bi.this.H.booleanValue() && bi.this.E == d.NONE && bi.this.h() != null) {
                if (!view.isShown()) {
                    view.setVisibility(0);
                }
                if (view.isShown()) {
                    bi.this.a(view, bi.this.h());
                    bi.this.t = System.currentTimeMillis();
                } else {
                    cl.b().d();
                }
            }
            if (bi.this.E == d.NONE) {
                bi.this.E = d.SPLASH;
            }
            if (bi.this.E == d.BASE) {
                bi.this.E = d.FINISHED;
                bi.this.o = false;
                bi.this.G.removeCallbacks(bi.this.F);
            }
            bw.b(bi.this.d(), "pageStatus: " + bi.this.E, true);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            bw.b(bi.this.d(), " There was an error loading url " + failingUrl + ": " + errorCode, true);
            bi.this.v = errorCode;
            bi.this.H = true;
            bi.this.u = System.currentTimeMillis() - bi.this.t;
            bw.b(bi.this.d(), " loadTime:" + bi.this.u, true);
            bi.this.a(errorCode);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler sslHandler, SslError sslError) {
            super.onReceivedSslError(view, sslHandler, sslError);
            if (Build.VERSION.SDK_INT > 14) {
                bw.b(bi.this.d(), "There was an SSL error loading url " + sslError.getUrl() + ": " + sslError.toString(), true);
            }
            bi.this.v = sslError.getPrimaryError();
            bi.this.H = true;
            bi.this.u = System.currentTimeMillis() - bi.this.t;
            bw.b(bi.this.d(), " loadTime: " + bi.this.u, true);
            bi.this.a(bi.this.v);
        }
    }

    @TargetApi(23)
    class b extends a {
        b() {
            super();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError webResourceError) {
            super.onReceivedError(view, request, webResourceError);
            onReceivedError(view, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), request.getUrl().toString());
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        bw.b(d(), "back button pressed", true);
        if (this.q.getUrl().contains(bv.aN().aw().a("catalog"))) {
            ck.a(this.q, "javascript:handleBackButton()");
            return;
        }
        bw.b(d(), "non-yvolver page", true);
        if (this.q.canGoBack()) {
            bw.b(d(), "go back was called", true);
            this.q.goBack();
        }
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        a(intent.getExtras());
    }

    public void injectCallbackResult(int callbackId, String jsonParams) {
        cm.a(this.q, callbackId, jsonParams);
    }

    private void a(Bundle bundle) {
        int i2 = 0;
        bw.b(d(), "processBundle was executed", true);
        this.r = bundle.getString(b);
        boolean z = bundle.getBoolean("close", false);
        boolean z2 = bundle.getBoolean(f, false);
        String string = bundle.getString(g);
        if (z) {
            cl.b().d();
        }
        if (bundle.containsKey(f)) {
            b(z2, string);
        }
        if (bundle.containsKey(h) && this.q != null) {
            ck.a(this.q, "javascript:finishDigitalRedemptionTransaction()");
        }
        if (bundle.containsKey(c)) {
            this.A = bundle.getBoolean(c, false);
        }
        if (bundle.containsKey(i)) {
            this.y = bundle.getInt(i);
        }
        if (bundle.containsKey(d)) {
            this.z = bundle.getString(d);
        }
        if (bundle.containsKey(j)) {
            boolean zContainsKey = bundle.containsKey("x");
            boolean zContainsKey2 = bundle.containsKey("y");
            if (zContainsKey) {
                this.C.rightMargin = (int) (bundle.getInt("x", 0) * ck.a());
            }
            if (zContainsKey2) {
                this.C.topMargin = (int) (bundle.getInt("y", 0) * ck.a());
            }
            if (zContainsKey && zContainsKey2) {
                this.C.gravity = 53;
                this.B.setLayoutParams(this.C);
            }
            if (bundle.containsKey("enabled")) {
                if (bundle.getBoolean("enabled", true)) {
                    this.B.setVisibility(0);
                } else {
                    this.B.setVisibility(4);
                }
            }
        }
        if (bundle.containsKey("callbackId")) {
            ArrayList arrayList = new ArrayList();
            int i3 = bundle.getInt("callbackId");
            while (true) {
                int i4 = i2 + 1;
                String str = "callbackIdParam" + i2;
                if (!bundle.containsKey(str)) {
                    break;
                }
                arrayList.add(bundle.getString(str));
                i2 = i4;
            }
            injectCallbackResult(i3, ck.a((List<Object>) arrayList));
        }
        c();
    }

    void b(boolean z, String str) {
        a(z, str);
        if (z && this.q != null) {
            ck.a(this.q, "javascript:updateDigitalRedemptionStatus(\"" + str + "\");");
        }
    }

    void a(String str) {
        if (this.q != null) {
            ck.a(this.q, "javascript:finishServerRewardTransaction(" + str.toString() + ");");
        }
    }

    void a(final int i2, final JSONArray jSONArray) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bi.4
            @Override // java.lang.Runnable
            public void run() {
                JSONArray jSONArray2 = new JSONArray();
                jSONArray2.put(jSONArray);
                bw.b(bi.this.d(), "finalArray: " + jSONArray2, true);
                ck.a(bi.this.q, "javascript:NativeBridge.resultForCallback(" + i2 + ", " + jSONArray2.toString() + ");");
            }
        });
    }

    void e() {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bi.5
            @Override // java.lang.Runnable
            public void run() {
                if (bi.this.q != null) {
                    ck.a(bi.this.q, "javascript:finishDigitalRedemptionTransaction()");
                }
            }
        });
    }

    void f() {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.s;
        HashMap map = new HashMap();
        map.put("duration_ms", Long.valueOf(jCurrentTimeMillis));
        map.put("download_ms", 0);
        map.put("document_ready_ms", 0);
        map.put("http_status_code", Integer.valueOf(this.v));
        if (this.u != 0) {
            map.put("loadtime_ms", Long.valueOf(this.u));
        }
        map.put("request_url", h());
        if (this.A) {
            map.put(d, this.z);
            map.put("reward_type", Integer.valueOf(this.y));
        }
        bw.b(d(), map.toString(), true);
        bv.aN().a("close_catalog", map);
    }

    void g() {
        bw.b(d(), "closeCatalog", true);
        f();
        cl.b().a(null);
        this.D.setJavaScriptEnabled(false);
        this.q.stopLoading();
        this.o = false;
        this.G.removeCallbacks(this.F);
        this.q.invalidate();
        this.q.removeAllViews();
        this.w.removeAllViews();
        this.w.invalidate();
        this.q.destroy();
        this.q = null;
        this.w = null;
        finish();
        System.gc();
        cl.b().a(null);
        bv.aN().aE().b();
        bv.aN().aC().b();
    }

    class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            bi.this.o = false;
            bi.this.G.removeCallbacks(bi.this.F);
            bw.b(bi.this.d(), "overlay timeout", true);
            bi.this.H = true;
            if (bi.this.q != null) {
                bi.this.q.stopLoading();
            }
            bi.this.a(503);
        }
    }
}
