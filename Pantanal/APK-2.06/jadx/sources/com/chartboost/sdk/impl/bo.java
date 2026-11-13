package com.chartboost.sdk.impl;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class bo extends com.chartboost.sdk.f {
    final com.chartboost.sdk.Tracking.a k;
    final com.chartboost.sdk.e l;
    public String m;
    String n;
    protected int o;
    long p;
    long q;
    boolean r;
    private final com.chartboost.sdk.impl.b s;
    private final af t;
    private String u;
    private JSONObject v;
    private float w;
    private float x;
    private boolean y;
    private b z;

    public enum b {
        NONE,
        IDLE,
        PLAYING,
        PAUSED
    }

    public class c extends com.chartboost.sdk.f.a {
        public bn c;
        public bm d;
        public RelativeLayout e;
        public RelativeLayout f;

        public c(Context context, String str) {
            super(context);
            setFocusable(false);
            com.chartboost.sdk.g gVarA = com.chartboost.sdk.g.a();
            this.e = (RelativeLayout) gVarA.a(new RelativeLayout(context));
            this.f = (RelativeLayout) gVarA.a(new RelativeLayout(context));
            this.c = (bn) gVarA.a(new bn(context));
            this.c.setWebViewClient(new a());
            this.d = (bm) gVarA.a(new bm(this.e, this.f, null, this.c, bo.this));
            this.c.setWebChromeClient(this.d);
            if (g.a().a(19)) {
                bn bnVar = this.c;
                bn.setWebContentsDebuggingEnabled(true);
            }
            this.c.loadDataWithBaseURL(bo.this.n, str, "text/html", "utf-8", null);
            this.e.addView(this.c);
            this.c.getSettings().setSupportZoom(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            this.e.setLayoutParams(layoutParams);
            this.c.setLayoutParams(layoutParams);
            this.c.setBackgroundColor(0);
            this.f.setVisibility(8);
            this.f.setLayoutParams(layoutParams);
            addView(this.e);
            addView(this.f);
            bo.this.p = System.currentTimeMillis();
            CBUtility.c().postDelayed(new Runnable() { // from class: com.chartboost.sdk.impl.bo.c.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!bo.this.r) {
                        CBLogging.a("CBWebViewProtocol", "Webview seems to be taking more time loading the html content, so closing the view.");
                        bo.this.k.a(bo.this.f.q().e(), bo.this.f.f, bo.this.f.p(), "Webview seems to be taking more time loading the html content, so closing the view.", true);
                        bo.this.a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
                    }
                }
            }, 3000L);
        }

        @Override // com.chartboost.sdk.f.a
        protected void a(int i, int i2) {
        }
    }

    public bo(com.chartboost.sdk.Model.a aVar, com.chartboost.sdk.impl.b bVar, af afVar, com.chartboost.sdk.Tracking.a aVar2, com.chartboost.sdk.e eVar) {
        super(aVar);
        this.m = "UNKNOWN";
        this.u = null;
        this.n = null;
        this.o = 1;
        this.w = BitmapDescriptorFactory.HUE_RED;
        this.x = BitmapDescriptorFactory.HUE_RED;
        this.y = false;
        this.p = 0L;
        this.q = 0L;
        this.r = false;
        this.z = b.NONE;
        this.s = bVar;
        this.t = afVar;
        this.k = aVar2;
        this.l = eVar;
    }

    @Override // com.chartboost.sdk.f
    protected com.chartboost.sdk.f.a b(Context context) {
        return new c(context, this.u);
    }

    @Override // com.chartboost.sdk.f
    public boolean a(JSONObject jSONObject) {
        File file = com.chartboost.sdk.Libraries.g.e().a;
        this.v = com.chartboost.sdk.Libraries.d.a(jSONObject, "events");
        if (file == null) {
            CBLogging.b("CBWebViewProtocol", "External Storage path is unavailable or media not mounted");
            a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
            return false;
        }
        this.n = "file://" + file.getAbsolutePath() + "/";
        if (g.a().a((CharSequence) this.f.j)) {
            CBLogging.b("CBWebViewProtocol", "Invalid adId being passed in th response");
            a(CBError.CBImpressionError.ERROR_DISPLAYING_VIEW);
            return false;
        }
        String str = this.f.k;
        if (str == null) {
            CBLogging.b("CBWebViewProtocol", "No html data found in memory");
            a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
            return false;
        }
        JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(jSONObject, "webview");
        if (jSONObjectA == null) {
            CBLogging.b("CBWebViewProtocol", "No webview element in response");
            a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
            return false;
        }
        if (!this.s.a(jSONObjectA, file)) {
            CBLogging.b("CBWebViewProtocol", "Missing assets for template");
            a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
            return false;
        }
        this.u = str;
        b();
        return true;
    }

    @Override // com.chartboost.sdk.f
    public void h() {
        super.h();
    }

    public void b(String str) {
        JSONArray jSONArrayOptJSONArray;
        if (this.v != null && !TextUtils.isEmpty(str) && (jSONArrayOptJSONArray = this.v.optJSONArray(str)) != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                String strOptString = jSONArrayOptJSONArray.optString(i);
                if (!strOptString.isEmpty()) {
                    this.t.a(strOptString);
                    CBLogging.a("CBWebViewProtocol", "###### Sending VAST Tracking Event: " + strOptString);
                }
            }
        }
    }

    public void c(String str) {
        this.k.a(this.f.q().e(), this.f.f, this.f.p(), str);
    }

    public void d(String str) {
        String str2 = g.a().a((CharSequence) str) ? "Unknown Webview error" : str;
        this.k.a(this.f.q().e(), this.f.f, this.f.p(), str2, true);
        CBLogging.b("CBWebViewProtocol", "Webview error occurred closing the webview" + str2);
        a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
        h();
    }

    public void e(String str) {
        if (g.a().a((CharSequence) str)) {
            str = "Unknown Webview warning message";
        }
        this.k.b(this.f.q().e(), this.f.f, this.f.p(), str);
        CBLogging.d("CBWebViewProtocol", "Webview warning occurred closing the webview" + str);
    }

    private class a extends WebViewClient {
        private a() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bo.this.r = true;
            bo.this.q = System.currentTimeMillis();
            CBLogging.a("CBWebViewProtocol", "Total web view load response time " + ((bo.this.q - bo.this.p) / 1000));
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            bo.this.a(CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW);
            bo.this.r = true;
            bo.this.l.d(bo.this.f);
            CBLogging.a("CBWebViewProtocol", "Webview seems to have some issues loading html, onRecievedError callback triggered");
            bo.this.k.a(bo.this.f.q().e(), bo.this.f.f, bo.this.f.p(), "Webview seems to have some issues loading html, onRecievedError callback triggered", true);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return false;
        }
    }

    @Override // com.chartboost.sdk.f
    public boolean l() {
        if (this.z != b.PLAYING) {
            c cVarE = e();
            if (cVarE != null && cVarE.d != null) {
                cVarE.d.onHideCustomView();
            }
            h();
        }
        return true;
    }

    @Override // com.chartboost.sdk.f
    public void m() {
        super.m();
        final c cVarE = e();
        if (cVarE != null && cVarE.c != null) {
            CBUtility.c().post(new Runnable() { // from class: com.chartboost.sdk.impl.bo.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (cVarE != null && cVarE.c != null) {
                            CBLogging.a("CBWebViewProtocol", "Calling native to javascript: javascript:Chartboost.EventHandler.handleNativeEvent(\"onForeground\", \"\")");
                            cVarE.c.loadUrl("javascript:Chartboost.EventHandler.handleNativeEvent(\"onForeground\", \"\")");
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(bo.class, "onResume Runnable.run", e);
                    }
                }
            });
            this.k.d(this.m, this.f.p());
        }
    }

    @Override // com.chartboost.sdk.f
    public void n() {
        super.n();
        final c cVarE = e();
        if (cVarE != null && cVarE.c != null) {
            CBUtility.c().post(new Runnable() { // from class: com.chartboost.sdk.impl.bo.2
                @Override // java.lang.Runnable
                public void run() {
                    if (cVarE != null && cVarE.c != null) {
                        CBLogging.a("CBWebViewProtocol", "Calling native to javascript: javascript:Chartboost.EventHandler.handleNativeEvent(\"onBackground\", \"\")");
                        cVarE.c.loadUrl("javascript:Chartboost.EventHandler.handleNativeEvent(\"onBackground\", \"\")");
                    }
                }
            });
            this.k.e(this.m, this.f.p());
        }
    }

    public void o() {
        if (this.o <= 1) {
            this.f.f();
            this.o++;
        }
    }

    @Override // com.chartboost.sdk.f
    public void d() {
        c cVarE = e();
        if (cVarE != null) {
            if (cVarE.c != null) {
                CBLogging.a("CBWebViewProtocol", "Destroying the webview object and cleaning up the references");
                cVarE.c.destroy();
                cVarE.c = null;
            }
            if (cVarE.d != null) {
                cVarE.d = null;
            }
            if (cVarE.e != null) {
                cVarE.e = null;
            }
            if (cVarE.f != null) {
                cVarE.f = null;
            }
        }
        super.d();
    }

    public void p() {
        this.k.c(this.m, this.f.p());
    }

    public void a(b bVar) {
        this.z = bVar;
    }

    @Override // com.chartboost.sdk.f
    /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
    public c e() {
        return (c) super.e();
    }

    public void a(float f) {
        this.x = f;
    }

    public void b(float f) {
        this.w = f;
    }

    @Override // com.chartboost.sdk.f
    public float j() {
        return this.w;
    }

    @Override // com.chartboost.sdk.f
    public float k() {
        return this.x;
    }

    public void r() {
        if (!this.y) {
            this.k.d("", this.f.p());
            this.f.r();
            this.y = true;
        }
    }
}
