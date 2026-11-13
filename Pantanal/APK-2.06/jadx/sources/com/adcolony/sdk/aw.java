package com.adcolony.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TJAdUnitConstants;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class aw extends WebView implements ad {
    int a;
    public boolean b;
    boolean c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private JSONArray t;
    private JSONObject u;
    private c v;
    private z w;

    aw(Context context, z zVar, int i, int i2, c cVar) {
        super(context);
        this.f = "";
        this.g = "";
        this.i = "";
        this.j = "";
        this.t = s.b();
        this.u = s.a();
        this.w = zVar;
        a(zVar, i, i2, cVar);
        e();
    }

    aw(Context context, int i, boolean z) {
        super(context);
        this.f = "";
        this.g = "";
        this.i = "";
        this.j = "";
        this.t = s.b();
        this.u = s.a();
        this.a = i;
        this.b = z;
    }

    void a(String str) {
        if (this.c) {
            u.b.b("Ignoring call to execute_js as WebView has been destroyed.");
        } else if (Build.VERSION.SDK_INT >= 19) {
            evaluateJavascript(str, null);
        } else {
            loadUrl("javascript:" + str);
        }
    }

    @Override // com.adcolony.sdk.ad
    public int a() {
        return this.a;
    }

    boolean a(z zVar) {
        return s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID) == this.k && s.c(zVar.b, "container_id") == this.v.n && s.b(zVar.b, "ad_session_id").equals(this.v.m);
    }

    void e() {
        a(false, (z) null);
    }

    void a(boolean z, z zVar) {
        a aVar;
        if (this.w == null) {
            this.w = zVar;
        }
        this.q = z;
        this.b = s.d(this.w.b, "is_display_module");
        if (z) {
            String strB = s.b(zVar.b, "filepath");
            this.e = strB;
            this.d = "file://" + strB;
            this.u = s.f(zVar.b, TJAdUnitConstants.String.VIDEO_INFO);
            this.p = true;
        }
        setFocusable(true);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= 19) {
            setWebContentsDebuggingEnabled(true);
        }
        setWebChromeClient(new WebChromeClient() { // from class: com.adcolony.sdk.aw.1
            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                u.d.a("JS Alert: ").b(message);
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage cm) {
                if (cm.messageLevel() == ConsoleMessage.MessageLevel.WARNING) {
                    u.f.b("onConsoleMessage: " + cm.message());
                } else if (cm.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                    if ((cm.message().contains("ADC3_update is not defined") || cm.message().contains("NativeLayer.dispatch_messages is not a function")) && com.adcolony.sdk.a.l != null && (com.adcolony.sdk.a.l instanceof b)) {
                        z zVar2 = new z("AdSession.finish_fullscreen_ad", 0);
                        s.b(zVar2.b, "status", 1);
                        ((b) com.adcolony.sdk.a.l).a(zVar2);
                    }
                    u.h.b("onConsoleMessage: " + cm.message());
                }
                return true;
            }
        });
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setGeolocationEnabled(true);
        settings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 17) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            aVar = new a() { // from class: com.adcolony.sdk.aw.2
                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    JSONObject jSONObjectA = s.a();
                    s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, aw.this.k);
                    s.a(jSONObjectA, "ad_session_id", aw.this.h);
                    s.b(jSONObjectA, "container_id", aw.this.v.n);
                    s.b(jSONObjectA, "code", error.getErrorCode());
                    s.a(jSONObjectA, TJAdUnitConstants.String.VIDEO_ERROR, error.getDescription().toString());
                    s.a(jSONObjectA, "url", aw.this.d);
                    new z("WebView.on_error", aw.this.v.l, jSONObjectA).b();
                }

                @Override // android.webkit.WebViewClient
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    if (!request.getUrl().toString().endsWith("mraid.js")) {
                        return null;
                    }
                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aw.this.i.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
                        aw.this.s = true;
                        return new WebResourceResponse("text/javascript", AsyncHttpResponseHandler.DEFAULT_CHARSET, byteArrayInputStream);
                    } catch (UnsupportedEncodingException e) {
                        u.h.b("UTF-8 not supported.");
                        return null;
                    }
                }
            };
        } else if (Build.VERSION.SDK_INT >= 21) {
            aVar = new a() { // from class: com.adcolony.sdk.aw.3
                @Override // android.webkit.WebViewClient
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    if (!request.getUrl().toString().endsWith("mraid.js")) {
                        return null;
                    }
                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aw.this.i.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
                        aw.this.s = true;
                        return new WebResourceResponse("text/javascript", AsyncHttpResponseHandler.DEFAULT_CHARSET, byteArrayInputStream);
                    } catch (UnsupportedEncodingException e) {
                        u.h.b("UTF-8 not supported.");
                        return null;
                    }
                }
            };
        } else {
            aVar = new a();
        }
        addJavascriptInterface(new Object() { // from class: com.adcolony.sdk.aw.4
            @JavascriptInterface
            public void dispatch_messages(String json_messages) {
                JSONArray jSONArrayB = s.b(json_messages);
                if (jSONArrayB == null) {
                    u.g.b("[INTERNAL] ADCJSON parse error in dispatch_messages javascript interface function");
                    return;
                }
                for (int i = 0; i < jSONArrayB.length(); i++) {
                    com.adcolony.sdk.a.m.d.a(s.c(jSONArrayB, i));
                }
            }
        }, "NativeLayer");
        setWebViewClient(aVar);
        if (this.b) {
            String strReplaceFirst = "";
            try {
                FileInputStream fileInputStream = new FileInputStream(this.e);
                StringBuilder sb = new StringBuilder(fileInputStream.available());
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = fileInputStream.read(bArr, 0, 1024);
                    if (i < 0) {
                        break;
                    } else {
                        sb.append(new String(bArr, 0, i, "ISO-8859-1"));
                    }
                }
                strReplaceFirst = sb.toString().replaceFirst("var\\s*ADC_DEVICE_INFO\\s*=\\s*null\\s*;", "var ADC_DEVICE_INFO = " + s.b(s.f(this.w.b, TJAdUnitConstants.String.VIDEO_INFO), "metadata") + ";\n");
            } catch (IOException e) {
                u.g.a("Failed to find or open display module at URL: ").a(this.d).a(" with error: ").b(e.toString());
            }
            loadDataWithBaseURL(this.d, strReplaceFirst, "text/html", null, null);
        } else if (this.d.startsWith("http") || this.d.startsWith("file")) {
            loadUrl(this.d);
        } else {
            loadDataWithBaseURL(this.g.equals("") ? "data" : this.g, z ? s.b(zVar.b, "data") : this.d, "text/html", null, null);
        }
        if (!z) {
            f();
            g();
        }
        if (z || this.p) {
            com.adcolony.sdk.a.m.d.a(this);
        }
        if (!this.f.equals("")) {
            a(this.f);
        }
    }

    void f() {
        this.v.o.add(com.adcolony.sdk.a.a("WebView.set_visible", new ac() { // from class: com.adcolony.sdk.aw.5
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (aw.this.a(zVar)) {
                    aw.this.c(zVar);
                }
            }
        }, true));
        this.v.o.add(com.adcolony.sdk.a.a("WebView.set_bounds", new ac() { // from class: com.adcolony.sdk.aw.6
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (aw.this.a(zVar)) {
                    aw.this.b(zVar);
                }
            }
        }, true));
        this.v.o.add(com.adcolony.sdk.a.a("WebView.execute_js", new ac() { // from class: com.adcolony.sdk.aw.7
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (aw.this.a(zVar)) {
                    aw.this.a(s.b(zVar.b, "custom_js"));
                }
            }
        }, true));
        this.v.p.add("WebView.set_visible");
        this.v.p.add("WebView.set_bounds");
        this.v.p.add("WebView.execute_js");
    }

    void g() {
        setVisibility(4);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.n, this.o);
        layoutParams.setMargins(this.l, this.m, 0, 0);
        layoutParams.gravity = 0;
        this.v.addView(this, layoutParams);
    }

    void a(z zVar, int i, c cVar) {
        a(zVar, i, -1, cVar);
        g();
    }

    void a(z zVar, int i, int i2, c cVar) {
        this.d = s.b(zVar.b, "url");
        if (this.d.equals("")) {
            this.d = s.b(zVar.b, "data");
        }
        this.g = s.b(zVar.b, "base_url");
        this.f = s.b(zVar.b, "custom_js");
        this.h = s.b(zVar.b, "ad_session_id");
        this.u = s.f(zVar.b, TJAdUnitConstants.String.VIDEO_INFO);
        this.j = s.b(zVar.b, "mraid_filepath");
        if (!this.b) {
            try {
                this.i = com.adcolony.sdk.a.m.i.a(this.j, false).toString();
                this.i = this.i.replaceFirst("bridge.os_name\\s*=\\s*\"\"\\s*;", "bridge.os_name = \"\";\nvar ADC_DEVICE_INFO = " + this.u.toString() + ";\n");
            } catch (IOException e) {
                u.g.a("Could not load MRAID from filepath: ").b(this.j);
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.h);
                new z("AdSession.on_error", cVar.l, jSONObjectA).b();
            }
        }
        this.k = i;
        this.v = cVar;
        if (i2 >= 0) {
            this.a = i2;
        } else {
            f();
        }
        this.n = s.c(zVar.b, "width");
        this.o = s.c(zVar.b, "height");
        this.l = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        this.m = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
        this.p = s.d(zVar.b, "enable_messages") || this.q;
    }

    void b(z zVar) {
        this.l = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        this.m = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
        this.n = s.c(zVar.b, "width");
        this.o = s.c(zVar.b, "height");
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(this.l, this.m, 0, 0);
        layoutParams.width = this.n;
        layoutParams.height = this.o;
        setLayoutParams(layoutParams);
        if (this.q) {
            JSONObject jSONObjectA = s.a();
            s.a(jSONObjectA, "success", true);
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.a);
            zVar.a(jSONObjectA).b();
        }
    }

    void c(z zVar) {
        if (s.d(zVar.b, "visible")) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
        if (this.q) {
            JSONObject jSONObjectA = s.a();
            s.a(jSONObjectA, "success", true);
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.a);
            zVar.a(jSONObjectA).b();
        }
    }

    @Override // com.adcolony.sdk.ad
    public void a(JSONObject jSONObject) {
        synchronized (this.t) {
            this.t.put(jSONObject);
        }
    }

    @Override // com.adcolony.sdk.ad
    public void b() {
    }

    @Override // com.adcolony.sdk.ad
    public void c() {
        if (com.adcolony.sdk.a.l != null && this.r) {
            com.adcolony.sdk.a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.aw.8
                @Override // java.lang.Runnable
                public void run() {
                    String string = "";
                    synchronized (aw.this.t) {
                        if (aw.this.t.length() > 0) {
                            if (aw.this.p) {
                                string = aw.this.t.toString();
                            }
                            aw.this.t = s.b();
                        }
                    }
                    if (aw.this.p) {
                        aw.this.a("NativeLayer.dispatch_messages(ADC3_update(" + string + "));");
                    }
                }
            });
        }
    }

    @Override // com.adcolony.sdk.ad
    public boolean d() {
        return false;
    }

    private class a extends WebViewClient {
        private a() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView view, String url) {
            if (url.equals(aw.this.d)) {
                aw.this.a("if (typeof(CN) != 'undefined' && CN.div) {\n  if (typeof(cn_dispatch_on_touch_begin) != 'undefined') CN.div.removeEventListener('mousedown',  cn_dispatch_on_touch_begin, true);\n  if (typeof(cn_dispatch_on_touch_end) != 'undefined')   CN.div.removeEventListener('mouseup',  cn_dispatch_on_touch_end, true);\n  if (typeof(cn_dispatch_on_touch_move) != 'undefined')  CN.div.removeEventListener('mousemove',  cn_dispatch_on_touch_move, true);\n}\n");
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            aw.this.r = false;
            aw.this.s = false;
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            JSONObject jSONObjectA = s.a();
            s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, aw.this.k);
            s.a(jSONObjectA, "url", url);
            u.b.a("onPageFinished called with URL = ").b(url);
            if (aw.this.v == null) {
                new z("WebView.on_load", aw.this.a, jSONObjectA).b();
            } else {
                s.a(jSONObjectA, "ad_session_id", aw.this.h);
                s.b(jSONObjectA, "container_id", aw.this.v.n);
                new z("WebView.on_load", aw.this.v.l, jSONObjectA).b();
            }
            if ((aw.this.p || aw.this.q) && (url.startsWith("data") || url.startsWith("file") || url.equals(aw.this.g) || aw.this.s)) {
                u.b.b("WebView data loaded - executing ADC3_init");
                u.b.b("===============================================================================");
                u.b.b("ADC3_init(" + aw.this.a + "," + aw.this.u.toString() + ");");
                u.b.b("===============================================================================");
                aw.this.a("ADC3_init(" + aw.this.a + "," + aw.this.u.toString() + ");");
                aw.this.r = true;
            }
            if (aw.this.q) {
                JSONObject jSONObjectA2 = s.a();
                s.a(jSONObjectA2, "success", true);
                s.b(jSONObjectA2, TMXConstants.TAG_TILE_ATTRIBUTE_ID, aw.this.a);
                aw.this.w.a(jSONObjectA2).b();
            }
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (Build.VERSION.SDK_INT >= 21 || !url.endsWith("mraid.js")) {
                return null;
            }
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aw.this.i.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
                aw.this.s = true;
                return new WebResourceResponse("text/javascript", AsyncHttpResponseHandler.DEFAULT_CHARSET, byteArrayInputStream);
            } catch (UnsupportedEncodingException e) {
                u.h.b("UTF-8 not supported.");
                return null;
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int error, String description, String url) {
            if (Build.VERSION.SDK_INT < 23) {
                JSONObject jSONObjectA = s.a();
                s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, aw.this.k);
                s.a(jSONObjectA, "ad_session_id", aw.this.h);
                s.b(jSONObjectA, "container_id", aw.this.v.n);
                s.b(jSONObjectA, "code", error);
                s.a(jSONObjectA, TJAdUnitConstants.String.VIDEO_ERROR, description);
                s.a(jSONObjectA, "url", url);
                new z("WebView.on_error", aw.this.v.l, jSONObjectA).b();
            }
        }
    }
}
