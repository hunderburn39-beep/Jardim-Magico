package com.fyber.ads.videos.mediation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.n;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: RewardedVideoMediationJSInterface.java */
/* JADX INFO: loaded from: classes.dex */
public final class c implements ValueCallback<String> {
    private ValueCallback<Boolean> a;
    private Handler b = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.fyber.ads.videos.mediation.c.1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            switch (message.what) {
                case 5689:
                    FyberLogger.d("RewardedVideoMediationJSInterface", "Timeout reached, returning \"false\" as default");
                    c.this.onReceiveValue("false");
                    break;
            }
            return false;
        }
    });
    private final String c = "SynchJS";

    public final void a(WebView webView, ValueCallback<Boolean> valueCallback) {
        if (valueCallback != null) {
            this.a = valueCallback;
            this.b.sendEmptyMessageDelayed(5689, 1000L);
            if (n.a(19)) {
                try {
                    webView.getClass().getMethod("evaluateJavascript", String.class, ValueCallback.class).invoke(webView, "javascript:try{ JSON.parse(Sponsorpay.MBE.SDKInterface.do_getOffer()).uses_tpn;}catch(error){false;};", this);
                    return;
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    FyberLogger.e("RewardedVideoMediationJSInterface", e.getLocalizedMessage());
                    return;
                }
            }
            webView.loadUrl("javascript:window.SynchJS.setValue((function(){try{return JSON.parse(Sponsorpay.MBE.SDKInterface.do_getOffer()).uses_tpn;}catch(js_eval_err){return false;}})());");
            return;
        }
        FyberLogger.e("RewardedVideoMediationJSInterface", "There is no ValueCallback to notify. Aborting...");
    }

    @JavascriptInterface
    public final void setValue(String str) {
        onReceiveValue(str);
    }

    @Override // android.webkit.ValueCallback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final void onReceiveValue(String str) {
        this.b.removeMessages(5689);
        this.a.onReceiveValue(Boolean.valueOf(Boolean.parseBoolean(str)));
        this.a = null;
    }
}
