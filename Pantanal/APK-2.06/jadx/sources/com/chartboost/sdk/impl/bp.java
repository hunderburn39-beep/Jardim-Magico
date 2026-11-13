package com.chartboost.sdk.impl;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.chartboost.sdk.Libraries.CBLogging;
import com.tapjoy.TJAdUnitConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class bp implements Runnable {
    private final bm a;
    private final bo b;
    private final int c;
    private final JSONObject d;
    private final String e;

    bp(bm bmVar, bo boVar, int i, String str, JSONObject jSONObject) {
        this.a = bmVar;
        this.b = boVar;
        this.c = i;
        this.e = str;
        this.d = jSONObject;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            switch (this.c) {
                case 0:
                    this.b.a((String) null, (JSONObject) null);
                    return;
                case 1:
                    this.b.h();
                    return;
                case 2:
                    try {
                        float f = (float) this.d.getDouble("duration");
                        CBLogging.a("NativeBridgeCommand", "######### JS->Native Video current player duration" + (f * 1000.0f));
                        this.b.a(f * 1000.0f);
                        return;
                    } catch (Exception e) {
                        this.b.e("Parsing exception unknown field for current player duration");
                        CBLogging.b("NativeBridgeCommand", "Cannot find duration parameter for the video");
                        return;
                    }
                case 3:
                    try {
                        String string = this.d.getString(TJAdUnitConstants.String.MESSAGE);
                        Log.d(bn.class.getName(), "JS->Native Debug message: " + string);
                        this.b.c(string);
                        return;
                    } catch (Exception e2) {
                        CBLogging.b("NativeBridgeCommand", "Exception occured while parsing the message for webview debug track event");
                        this.b.c("Exception occured while parsing the message for webview debug track event");
                        return;
                    }
                case 4:
                    try {
                        String string2 = this.d.getString(TJAdUnitConstants.String.MESSAGE);
                        Log.d(bn.class.getName(), "JS->Native Error message: " + string2);
                        this.b.d(string2);
                        return;
                    } catch (Exception e3) {
                        CBLogging.b("NativeBridgeCommand", "Error message is empty");
                        this.b.d("");
                        return;
                    }
                case 5:
                    try {
                        String string3 = this.d.getString("url");
                        if (!string3.startsWith("http://") && !string3.startsWith("https://")) {
                            string3 = "http://" + string3;
                        }
                        this.b.e().getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string3)));
                        CBLogging.a(bn.class.getName(), "JS->Native Track MRAID openUrl: " + string3);
                        return;
                    } catch (ActivityNotFoundException e4) {
                        com.chartboost.sdk.Tracking.a.a(getClass(), "ActivityNotFoundException occured when opening a url in a browser", e4);
                        CBLogging.b("NativeBridgeCommand", "ActivityNotFoundException occured when opening a url in a browser");
                        return;
                    } catch (Exception e5) {
                        com.chartboost.sdk.Tracking.a.a(getClass(), "Exception while opening a browser view with MRAID url", e5);
                        CBLogging.b("NativeBridgeCommand", "Exception while opening a browser view with MRAID url");
                        return;
                    }
                case 6:
                    this.b.r();
                    return;
                case 7:
                    try {
                        float f2 = (float) this.d.getDouble("duration");
                        CBLogging.a("NativeBridgeCommand", "######### JS->Native Video total player duration" + (f2 * 1000.0f));
                        this.b.b(f2 * 1000.0f);
                        return;
                    } catch (Exception e6) {
                        this.b.e("Parsing exception unknown field for total player duration");
                        CBLogging.b("NativeBridgeCommand", "Cannot find duration parameter for the video");
                        return;
                    }
                case 8:
                    try {
                        String string4 = this.d.getString("event");
                        this.b.b(string4);
                        Log.d(bn.class.getName(), "JS->Native Track VAST event message: " + string4);
                        return;
                    } catch (Exception e7) {
                        CBLogging.b("NativeBridgeCommand", "Exception occured while parsing the message for webview tracking VAST events");
                        return;
                    }
                case 9:
                    this.a.onHideCustomView();
                    this.b.a(bo.b.IDLE);
                    this.b.o();
                    return;
                case 10:
                    try {
                        String string5 = this.d.getString("name");
                        if (!g.a().a((CharSequence) string5)) {
                            this.b.m = string5;
                        }
                        break;
                    } catch (Exception e8) {
                        CBLogging.b("NativeBridgeCommand", "Cannot find video file name");
                        this.b.e("Parsing exception unknown field for video pause");
                    }
                    this.b.a(bo.b.PAUSED);
                    return;
                case 11:
                    try {
                        String string6 = this.d.getString("name");
                        if (!g.a().a((CharSequence) string6)) {
                            this.b.m = string6;
                        }
                        break;
                    } catch (Exception e9) {
                        CBLogging.b("NativeBridgeCommand", "Cannot find video file name");
                        this.b.e("Parsing exception unknown field for video play");
                    }
                    this.b.a(bo.b.PLAYING);
                    return;
                case 12:
                    try {
                        String string7 = this.d.getString("name");
                        if (!g.a().a((CharSequence) string7)) {
                            this.b.m = string7;
                        }
                        this.b.p();
                        return;
                    } catch (Exception e10) {
                        CBLogging.b("NativeBridgeCommand", "Cannot find video file name");
                        this.b.e("Parsing exception unknown field for video replay");
                        return;
                    }
                case 13:
                    try {
                        String string8 = this.d.getString(TJAdUnitConstants.String.MESSAGE);
                        Log.d(bn.class.getName(), "JS->Native Warning message: " + string8);
                        this.b.e(string8);
                        return;
                    } catch (Exception e11) {
                        CBLogging.b("NativeBridgeCommand", "Warning message is empty");
                        this.b.e("");
                        return;
                    }
                default:
                    return;
            }
        } catch (Exception e12) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "run(" + this.e + ")", e12);
        }
        com.chartboost.sdk.Tracking.a.a(getClass(), "run(" + this.e + ")", e12);
    }
}
