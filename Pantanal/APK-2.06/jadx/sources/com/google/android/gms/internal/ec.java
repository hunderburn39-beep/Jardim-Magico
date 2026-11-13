package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class ec extends ea {
    public ec(dz dzVar, boolean z) {
        super(dzVar, z);
    }

    protected WebResourceResponse d(Context context, String str, String str2) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
        try {
            dq.a(context, str, true, httpURLConnection);
            httpURLConnection.connect();
            return new WebResourceResponse("application/javascript", AsyncHttpResponseHandler.DEFAULT_CHARSET, new ByteArrayInputStream(dq.a(new InputStreamReader(httpURLConnection.getInputStream())).getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET)));
        } finally {
            httpURLConnection.disconnect();
        }
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        WebResourceResponse webResourceResponseD;
        try {
            if (!"mraid.js".equalsIgnoreCase(new File(url).getName())) {
                webResourceResponseD = super.shouldInterceptRequest(webView, url);
            } else if (webView instanceof dz) {
                dz dzVar = (dz) webView;
                dzVar.bI().aM();
                if (dzVar.R().lT) {
                    dw.y("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_interstitial.js)");
                    webResourceResponseD = d(dzVar.getContext(), this.lC.bK().rq, "http://media.admob.com/mraid/v1/mraid_app_interstitial.js");
                } else if (dzVar.bL()) {
                    dw.y("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js)");
                    webResourceResponseD = d(dzVar.getContext(), this.lC.bK().rq, "http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js");
                } else {
                    dw.y("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_banner.js)");
                    webResourceResponseD = d(dzVar.getContext(), this.lC.bK().rq, "http://media.admob.com/mraid/v1/mraid_app_banner.js");
                }
            } else {
                dw.z("Tried to intercept request from a WebView that wasn't an AdWebView.");
                webResourceResponseD = super.shouldInterceptRequest(webView, url);
            }
            return webResourceResponseD;
        } catch (IOException e) {
            dw.z("Could not fetching MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, url);
        }
    }
}
