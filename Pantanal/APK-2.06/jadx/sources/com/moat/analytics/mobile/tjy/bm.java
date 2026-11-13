package com.moat.analytics.mobile.tjy;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.util.HashSet;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
class bm implements bl {
    bm() {
    }

    @Override // com.moat.analytics.mobile.tjy.bl
    public com.moat.analytics.mobile.tjy.base.functional.a a(ViewGroup viewGroup) {
        if (viewGroup instanceof WebView) {
            return com.moat.analytics.mobile.tjy.base.functional.a.a((WebView) viewGroup);
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(viewGroup);
        HashSet hashSet = new HashSet();
        int i = 0;
        WebView webView = null;
        while (!linkedList.isEmpty() && i < 100) {
            i++;
            for (View view : new bo((ViewGroup) linkedList.poll())) {
                if (view instanceof WebView) {
                    if (webView != null) {
                        Log.e("MoatWebViewHound", "Ambiguous ad container: multiple WebViews reside within it.");
                        webView = null;
                        break;
                    }
                    webView = (WebView) view;
                }
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) view;
                    if (!hashSet.contains(viewGroup2)) {
                        hashSet.add(viewGroup2);
                        linkedList.add(viewGroup2);
                    }
                }
            }
        }
        return com.moat.analytics.mobile.tjy.base.functional.a.b(webView);
    }
}
