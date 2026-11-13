package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebSettings;

/* JADX INFO: loaded from: classes.dex */
public final class dt {
    public static void a(Context context, WebSettings webSettings) {
        ds.a(context, webSettings);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
    }

    public static String getDefaultUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }
}
