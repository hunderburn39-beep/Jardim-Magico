package com.google.android.gms.internal;

import android.view.View;
import android.webkit.WebChromeClient;

/* JADX INFO: loaded from: classes.dex */
public final class ed extends eb {
    public ed(dz dzVar) {
        super(dzVar);
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback customViewCallback) {
        a(view, requestedOrientation, customViewCallback);
    }
}
