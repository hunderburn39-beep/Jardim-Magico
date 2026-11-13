package com.moat.analytics.mobile.tjy;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class af implements NativeDisplayTracker {
    private WebView a;
    private bh b;
    private final String c;
    private final ap d;
    private boolean e;

    public af(View view, String str, a aVar, ap apVar) {
        if (apVar.b()) {
            Log.d("MoatNativeDispTracker", "Initializing.");
        }
        this.c = str;
        this.a = new WebView(view.getContext());
        WebSettings settings = this.a.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccess(false);
        settings.setDatabaseEnabled(false);
        settings.setDomStorageEnabled(false);
        settings.setGeolocationEnabled(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSaveFormData(false);
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(1);
        }
        this.d = apVar;
        this.b = new bi(view, this.a, true, aVar, apVar);
        this.e = false;
    }

    private static String a(int i, int i2, String str, String str2) {
        return "<!DOCTYPE html>\n<html>\n<head lang=\"en\">\n    <meta charset=\"UTF-8\">\n    <title></title>\n</head>\n<body style=\"margin:0;padding:0;\">\n<div id=\"mianahwvc\" style=\"width:" + i + "px;height:" + i2 + "px;\">\n    <script src=\"https://z.moatads.com/" + str + "/moatad.js#" + str2 + "\" type=\"text/javascript\"></script>\n</div>\n</body>\n</html>";
    }

    private static String a(Map map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < 8; i++) {
            String str = "moatClientLevel" + i;
            if (map.containsKey(str)) {
                linkedHashMap.put(str, map.get(str));
            }
        }
        for (int i2 = 0; i2 < 8; i2++) {
            String str2 = "moatClientSlicer" + i2;
            if (map.containsKey(str2)) {
                linkedHashMap.put(str2, map.get(str2));
            }
        }
        for (String str3 : map.keySet()) {
            if (!linkedHashMap.containsKey(str3)) {
                linkedHashMap.put(str3, (String) map.get(str3));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str4 : linkedHashMap.keySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(str4).append('=').append((String) linkedHashMap.get(str4));
        }
        return String.valueOf(sb);
    }

    private void a() {
        if (this.e) {
            return;
        }
        a("Shutting down.");
        this.b.d();
        this.a.loadUrl("about:blank");
        this.a.destroy();
        this.a = null;
        this.b = null;
        this.e = true;
    }

    private void a(String str) {
        if (this.d.b()) {
            Log.d("MoatNativeDispTracker", String.format("id = %s, message = %s", Integer.valueOf(hashCode()), str));
        }
    }

    @Override // com.moat.analytics.mobile.tjy.NativeDisplayTracker
    public void stopTracking() {
        a("Called stopTracking.");
        a();
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0009 A[Catch: Exception -> 0x006a, TryCatch #0 {Exception -> 0x006a, blocks: (B:4:0x0003, B:8:0x0012, B:10:0x001a, B:6:0x0009), top: B:18:0x0003 }] */
    @Override // com.moat.analytics.mobile.tjy.NativeDisplayTracker
    public boolean track(Map map) {
        boolean zC = false;
        if (map != null) {
            try {
                if (map.isEmpty()) {
                    a("adIdMap is null or empty. Shutting down.");
                    a();
                } else {
                    zC = this.b.c();
                    if (zC) {
                        Rect rectE = this.b.e();
                        int iWidth = rectE.width();
                        int iHeight = rectE.height();
                        String strA = a(map);
                        a("Parsed ad ids = " + strA);
                        this.a.loadData(a(iWidth, iHeight, this.c, strA), "text/html", "utf-8");
                    }
                    a("Attempt to start tracking ad was " + (zC ? "" : "un") + "successful.");
                }
            } catch (Exception e) {
                com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            }
        } else {
            a("adIdMap is null or empty. Shutting down.");
            a();
        }
        return zC;
    }
}
