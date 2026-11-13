package com.chartboost.sdk.impl;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class bm extends WebChromeClient {
    final bo a;
    private View b;
    private ViewGroup c;
    private View d;
    private bn e;
    private boolean f;
    private FrameLayout g;
    private WebChromeClient.CustomViewCallback h;
    private a i;
    private final Handler j;

    public interface a {
        void a(boolean z);
    }

    public bm() {
        this.j = CBUtility.c();
        this.a = null;
    }

    public bm(View view, ViewGroup viewGroup, View view2, bn bnVar, bo boVar) {
        this.j = CBUtility.c();
        this.b = view;
        this.c = viewGroup;
        this.d = view2;
        this.e = bnVar;
        this.f = false;
        this.a = boVar;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage cm) {
        Log.d(bm.class.getSimpleName(), "Chartboost Webview:" + cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId());
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        try {
            JSONObject jSONObjectA = g.a().a(message);
            result.confirm(a(jSONObjectA.getJSONObject("eventArgs"), jSONObjectA.getString("eventType")));
        } catch (JSONException e) {
            CBLogging.b("CBWebChromeClient", "Exception caught parsing the function name from js to native");
        }
        return true;
    }

    public String a(JSONObject jSONObject, String str) {
        int i = 0;
        switch (str) {
            case "click":
                break;
            case "close":
                i = 1;
                break;
            case "videoCompleted":
                i = 9;
                break;
            case "videoPlaying":
                i = 11;
                break;
            case "videoPaused":
                i = 10;
                break;
            case "videoReplay":
                i = 12;
                break;
            case "currentVideoDuration":
                i = 2;
                break;
            case "totalVideoDuration":
                i = 7;
                break;
            case "show":
                i = 6;
                break;
            case "error":
                Log.d(bn.class.getName(), "Javascript Error occured");
                i = 4;
                break;
            case "warning":
                Log.d(bn.class.getName(), "Javascript warning occurred");
                i = 13;
                break;
            case "debug":
                i = 3;
                break;
            case "tracking":
                i = 8;
                break;
            case "openUrl":
                i = 5;
                break;
            default:
                Log.e("CBWebChromeClient", "JavaScript to native " + str + " callback not recognized.");
                return "Function name not recognized.";
        }
        Log.d("CBWebChromeClient", "JavaScript to native " + str + " callback triggered.");
        this.j.post(new bp(this, this.a, i, str, jSONObject));
        return "Native function successfully called.";
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (view instanceof FrameLayout) {
            this.f = true;
            this.g = (FrameLayout) view;
            this.h = callback;
            this.b.setVisibility(4);
            this.c.addView(this.g, new ViewGroup.LayoutParams(-1, -1));
            this.c.setVisibility(0);
            if (this.i != null) {
                this.i.a(true);
            }
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        onShowCustomView(view, callback);
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        if (this.f) {
            this.c.setVisibility(4);
            this.c.removeView(this.g);
            this.b.setVisibility(0);
            if (this.h != null && !this.h.getClass().getName().contains(".chromium.")) {
                this.h.onCustomViewHidden();
            }
            this.f = false;
            this.g = null;
            this.h = null;
            if (this.i != null) {
                this.i.a(false);
            }
        }
    }
}
