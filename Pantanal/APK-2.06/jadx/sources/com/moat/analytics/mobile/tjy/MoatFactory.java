package com.moat.analytics.mobile.tjy;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
public abstract class MoatFactory {
    public static MoatFactory create(Activity activity) {
        try {
            return new v(activity);
        } catch (com.moat.analytics.mobile.tjy.base.exception.b e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            return new ak();
        }
    }

    public abstract Object createCustomTracker(ac acVar);

    public abstract NativeDisplayTracker createNativeDisplayTracker(View view, String str);

    public abstract NativeVideoTracker createNativeVideoTracker(String str);

    public abstract WebAdTracker createWebAdTracker(ViewGroup viewGroup);

    public abstract WebAdTracker createWebAdTracker(WebView webView);

    @Deprecated
    public abstract WebAdTracker createWebDisplayTracker(ViewGroup viewGroup);

    @Deprecated
    public abstract WebAdTracker createWebDisplayTracker(WebView webView);
}
