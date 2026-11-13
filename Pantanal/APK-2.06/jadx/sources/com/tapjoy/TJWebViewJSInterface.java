package com.tapjoy;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TJWebViewJSInterface {
    WebView a;
    TJWebViewJSInterfaceListener b;
    PriorityQueue c = new PriorityQueue();

    public TJWebViewJSInterface(WebView w, TJWebViewJSInterfaceListener l) {
        this.a = w;
        this.b = l;
    }

    @JavascriptInterface
    public void dispatchMethod(String params) {
        TapjoyLog.d("TJWebViewJSInterface", "dispatchMethod params: " + params);
        try {
            JSONObject jSONObject = new JSONObject(params);
            String string = jSONObject.getJSONObject("data").getString(TJAdUnitConstants.String.METHOD);
            TapjoyLog.d("TJWebViewJSInterface", "method: " + string);
            if (this.b != null) {
                this.b.onDispatchMethod(string, jSONObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callback(ArrayList arrayList, String methodName, String callbackID) {
        try {
            callbackToJavaScript(new JSONArray((Collection) arrayList), methodName, callbackID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callback(Map map, String methodName, String callbackID) {
        try {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(new JSONObject(map));
            callbackToJavaScript(jSONArray, methodName, callbackID);
        } catch (Exception e) {
            TapjoyLog.e("TJWebViewJSInterface", "Exception in callback to JS: " + e.toString());
            e.printStackTrace();
        }
    }

    public void callbackToJavaScript(Object arguments, String methodName, String callbackID) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(TJAdUnitConstants.String.ARGUMENTS, arguments);
            if (methodName != null && methodName.length() > 0) {
                jSONObject.put(TJAdUnitConstants.String.METHOD, methodName);
            }
            JSONObject jSONObject2 = new JSONObject();
            if (callbackID != null && callbackID.length() > 0) {
                jSONObject2.put(TJAdUnitConstants.String.CALLBACK_ID, callbackID);
            }
            jSONObject2.put("data", jSONObject);
            String str = "javascript:if(window.AndroidWebViewJavascriptBridge) AndroidWebViewJavascriptBridge._handleMessageFromAndroid('" + jSONObject2 + "');";
            if (this.c != null) {
                this.c.add(str);
            } else {
                new a(this.a).execute(str);
            }
        } catch (Exception e) {
            TapjoyLog.e("TJWebViewJSInterface", "Exception in callback to JS: " + e.toString());
            e.printStackTrace();
        }
    }

    public void flushMessageQueue() {
        if (this.c != null) {
            while (this.c.size() != 0) {
                new a(this.a).execute((String) this.c.poll());
            }
            this.c = null;
        }
    }

    @TargetApi(19)
    class a extends AsyncTask {
        WebView a;

        @Override // android.os.AsyncTask
        protected final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return ((String[]) objArr)[0];
        }

        @Override // android.os.AsyncTask
        protected final /* synthetic */ void onPostExecute(Object obj) {
            String str = (String) obj;
            if (this.a != null) {
                if (str.startsWith("javascript:") && Build.VERSION.SDK_INT >= 19) {
                    try {
                        this.a.evaluateJavascript(str.replaceFirst("javascript:", ""), null);
                        return;
                    } catch (Exception e) {
                        TapjoyLog.e("TJWebViewJSInterface", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTERNAL_ERROR, "Exception in evaluateJavascript. Device not supported. " + e.toString()));
                        return;
                    }
                }
                try {
                    this.a.loadUrl(str);
                } catch (Exception e2) {
                    TapjoyLog.e("TJWebViewJSInterface", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTERNAL_ERROR, "Exception in loadUrl. Device not supported. " + e2.toString()));
                }
            }
        }

        public a(WebView webView) {
            this.a = webView;
        }
    }
}
