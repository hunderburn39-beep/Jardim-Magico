package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.heyzap.sdk.ads.HeyzapAds;
import com.tapjoy.TJAdUnitConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class w extends s {
    View.OnClickListener a;
    private final WebView b;

    public w(z zVar, Context context) {
        super(context);
        this.a = null;
        this.b = new WebView(context);
        addView(this.b, new LinearLayout.LayoutParams(-1, -1));
        this.b.setBackgroundColor(0);
        this.b.setWebViewClient(new WebViewClient() { // from class: com.chartboost.sdk.impl.w.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) {
                    return false;
                }
                if (url.contains(HeyzapAds.Network.CHARTBOOST) && url.contains("click") && w.this.a != null) {
                    w.this.a.onClick(w.this);
                }
                return true;
            }
        });
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener clickListener) {
        super.setOnClickListener(clickListener);
        this.a = clickListener;
    }

    @Override // com.chartboost.sdk.impl.s
    public void a(JSONObject jSONObject, int i) {
        String strOptString = jSONObject.optString(TJAdUnitConstants.String.HTML);
        if (!strOptString.isEmpty()) {
            try {
                this.b.loadDataWithBaseURL("file:///android_res/", strOptString, "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
            } catch (Exception e) {
                CBLogging.b("AppCellWebView", "Exception raised loading data into webview", e);
                com.chartboost.sdk.Tracking.a.a(getClass(), "prepareWithCellMeta", e);
            }
        }
    }

    @Override // com.chartboost.sdk.impl.s
    public int a() {
        return CBUtility.a(100, getContext());
    }
}
