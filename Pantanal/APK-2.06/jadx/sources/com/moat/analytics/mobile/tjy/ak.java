package com.moat.analytics.mobile.tjy;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
public class ak extends MoatFactory {
    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public Object createCustomTracker(ac acVar) {
        return null;
    }

    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public NativeDisplayTracker createNativeDisplayTracker(View view, String str) {
        return new al();
    }

    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public NativeVideoTracker createNativeVideoTracker(String str) {
        return new am();
    }

    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public WebAdTracker createWebAdTracker(ViewGroup viewGroup) {
        return new ao();
    }

    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public WebAdTracker createWebAdTracker(WebView webView) {
        return new ao();
    }

    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public WebAdTracker createWebDisplayTracker(ViewGroup viewGroup) {
        return new ao();
    }

    @Override // com.moat.analytics.mobile.tjy.MoatFactory
    public WebAdTracker createWebDisplayTracker(WebView webView) {
        return new ao();
    }
}
