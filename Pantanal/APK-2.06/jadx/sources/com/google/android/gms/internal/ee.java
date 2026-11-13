package com.google.android.gms.internal;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.URI;
import java.net.URISyntaxException;

/* JADX INFO: loaded from: classes.dex */
public class ee extends WebViewClient {
    private final dz lC;
    private final String rM;
    private boolean rN = false;
    private final ct rO;

    public ee(ct ctVar, dz dzVar, String str) {
        this.rM = B(str);
        this.lC = dzVar;
        this.rO = ctVar;
    }

    private String B(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return str.endsWith("/") ? str.substring(0, str.length() - 1) : str;
        } catch (IndexOutOfBoundsException e) {
            dw.w(e.getMessage());
            return str;
        }
    }

    protected boolean A(String str) {
        boolean z = false;
        String strB = B(str);
        if (!TextUtils.isEmpty(strB)) {
            try {
                URI uri = new URI(strB);
                if ("passback".equals(uri.getScheme())) {
                    dw.v("Passback received");
                    this.rO.bb();
                    z = true;
                } else if (!TextUtils.isEmpty(this.rM)) {
                    URI uri2 = new URI(this.rM);
                    String host = uri2.getHost();
                    String host2 = uri.getHost();
                    String path = uri2.getPath();
                    String path2 = uri.getPath();
                    if (fo.equal(host, host2) && fo.equal(path, path2)) {
                        dw.v("Passback received");
                        this.rO.bb();
                        z = true;
                    }
                }
            } catch (URISyntaxException e) {
                dw.w(e.getMessage());
            }
        }
        return z;
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView view, String url) {
        dw.v("JavascriptAdWebViewClient::onLoadResource: " + url);
        if (A(url)) {
            return;
        }
        this.lC.bI().onLoadResource(this.lC, url);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView view, String url) {
        dw.v("JavascriptAdWebViewClient::onPageFinished: " + url);
        if (this.rN) {
            return;
        }
        this.rO.ba();
        this.rN = true;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        dw.v("JavascriptAdWebViewClient::shouldOverrideUrlLoading: " + url);
        if (!A(url)) {
            return this.lC.bI().shouldOverrideUrlLoading(this.lC, url);
        }
        dw.v("shouldOverrideUrlLoading: received passback url");
        return true;
    }
}
