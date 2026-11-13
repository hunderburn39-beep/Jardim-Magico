package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class bn extends WebView {
    private bm a;
    private boolean b;

    public bn(Context context) {
        super(context);
        this.b = false;
    }

    @Override // android.webkit.WebView
    @SuppressLint({"SetJavaScriptEnabled"})
    public void setWebChromeClient(WebChromeClient client) {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setAllowContentAccess(true);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setBuiltInZoomControls(false);
            settings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            setWebContentsDebuggingEnabled(true);
        }
        if (client instanceof bm) {
            this.a = (bm) client;
        }
        super.setWebChromeClient(client);
    }

    @Override // android.webkit.WebView
    public void loadData(String data, String mimeType, String encoding) {
        a();
        super.loadData(data, mimeType, encoding);
    }

    @Override // android.webkit.WebView
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        a();
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String url) {
        a();
        super.loadUrl(url);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        a();
        super.loadUrl(url, additionalHttpHeaders);
    }

    private void a() {
        if (!this.b) {
            this.b = true;
        }
    }
}
