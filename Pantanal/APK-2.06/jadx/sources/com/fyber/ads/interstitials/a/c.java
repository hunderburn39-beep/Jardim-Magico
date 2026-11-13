package com.fyber.ads.interstitials.a;

import android.content.Intent;
import android.net.Uri;

/* JADX INFO: compiled from: InterstitialClickthroughBrowserListener.java */
/* JADX INFO: loaded from: classes.dex */
public final class c extends com.fyber.utils.a {
    @Override // com.fyber.utils.a
    protected final void a(com.fyber.c.d.d dVar, com.fyber.c.a.a aVar, Uri uri) {
        dVar.getContext().startActivity(new Intent("android.intent.action.VIEW", uri));
    }

    @Override // com.fyber.utils.a
    protected final String a() {
        return "InterstitialClickthroughBrowserListener";
    }
}
