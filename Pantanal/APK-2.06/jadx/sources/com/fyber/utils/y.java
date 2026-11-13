package com.fyber.utils;

import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/* JADX INFO: compiled from: WebViewConfigs.java */
/* JADX INFO: loaded from: classes.dex */
public final class y {
    public static void a(WebSettings webSettings) {
        if (n.b(20)) {
            try {
                WebSettings.class.getMethod("setPluginState", WebSettings.PluginState.class).invoke(webSettings, WebSettings.PluginState.ON);
            } catch (Exception e) {
                FyberLogger.d("WebViewConfigs", "Unable to enable plugin support for the webview");
            }
        }
    }

    public static void a(WebView webView) {
        if (n.a(21)) {
            try {
                CookieManager.class.getMethod("setAcceptThirdPartyCookies", WebView.class, Boolean.TYPE).invoke(CookieManager.getInstance(), webView, true);
            } catch (Exception e) {
                FyberLogger.d("WebViewConfigs", "Unable to set acceptThirdPartyCookies for the webview");
            }
        }
    }
}
