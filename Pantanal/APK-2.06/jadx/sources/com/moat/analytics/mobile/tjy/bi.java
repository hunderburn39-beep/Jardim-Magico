package com.moat.analytics.mobile.tjy;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class bi implements bh, m {
    private View a;
    private final WebView b;
    private boolean c;
    private final l d;
    private final a e;
    private final ap f;
    private com.moat.analytics.mobile.tjy.base.functional.a g;

    bi(View view, WebView webView, boolean z, a aVar, ap apVar) {
        this(view, webView, z, new n(webView.getContext(), apVar), aVar, apVar);
    }

    bi(View view, WebView webView, boolean z, l lVar, a aVar, ap apVar) {
        com.moat.analytics.mobile.tjy.base.asserts.a.a(view);
        com.moat.analytics.mobile.tjy.base.asserts.a.a(webView);
        com.moat.analytics.mobile.tjy.base.asserts.a.a(aVar);
        com.moat.analytics.mobile.tjy.base.asserts.a.a(lVar);
        if (apVar.b()) {
            Log.d("MoatViewTracker", "In initialization method.");
        }
        this.e = aVar;
        this.a = view;
        this.b = webView;
        this.c = z;
        this.d = lVar;
        this.f = apVar;
        this.g = com.moat.analytics.mobile.tjy.base.functional.a.a();
    }

    private static String a(Rect rect) {
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right - rect.left;
        return String.valueOf(new StringBuilder("{\"x\":").append(i).append(',').append('\"').append("y\":").append(i2).append(',').append('\"').append("w\":").append(i3).append(',').append('\"').append("h\":").append(rect.bottom - rect.top).append('}'));
    }

    private static String a(Map map, boolean z) {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (sb.length() > 1) {
                sb.append(',');
            }
            sb.append('\"').append(str).append('\"').append(':');
            if (z) {
                sb.append('\"').append(str2).append('\"');
            } else {
                sb.append(str2);
            }
        }
        sb.append("}");
        return String.valueOf(sb);
    }

    private void a(Map map, String str, Rect rect) {
        map.put(str, a(b(rect)));
    }

    private Rect b(Rect rect) {
        float f = j().density;
        if (f == BitmapDescriptorFactory.HUE_RED) {
            return rect;
        }
        return new Rect(Math.round(rect.left / f), Math.round(rect.top / f), Math.round(rect.right / f), Math.round(rect.bottom / f));
    }

    private Rect c(Rect rect) {
        Rect rectK = k();
        if (!this.a.getGlobalVisibleRect(rectK)) {
            rectK = k();
        }
        rectK.left = Math.min(Math.max(0, rectK.left), rect.right);
        rectK.right = Math.min(Math.max(0, rectK.right), rect.right);
        rectK.top = Math.min(Math.max(0, rectK.top), rect.bottom);
        rectK.bottom = Math.min(Math.max(0, rectK.bottom), rect.bottom);
        return rectK;
    }

    private String g() {
        String string;
        Exception e;
        if (this.g.c()) {
            return (String) this.g.b();
        }
        try {
            Context context = this.b.getContext();
            string = context.getPackageManager().getApplicationLabel(context.getApplicationContext().getApplicationInfo()).toString();
            try {
                this.g = com.moat.analytics.mobile.tjy.base.functional.a.a(string);
                return string;
            } catch (Exception e2) {
                e = e2;
                com.moat.analytics.mobile.tjy.base.exception.a.a(e);
                return string;
            }
        } catch (Exception e3) {
            string = "_unknown_";
            e = e3;
        }
    }

    private boolean h() {
        return this.a.isShown() && !this.e.a();
    }

    private Rect i() {
        DisplayMetrics displayMetricsJ = j();
        return new Rect(0, 0, displayMetricsJ.widthPixels, displayMetricsJ.heightPixels);
    }

    private DisplayMetrics j() {
        return this.a.getContext().getResources().getDisplayMetrics();
    }

    private Rect k() {
        return new Rect(ExploreByTouchHelper.INVALID_ID, ExploreByTouchHelper.INVALID_ID, ExploreByTouchHelper.INVALID_ID, ExploreByTouchHelper.INVALID_ID);
    }

    @Override // com.moat.analytics.mobile.tjy.m
    public String a() {
        HashMap map = new HashMap();
        try {
            Rect rectI = i();
            Rect rectC = c(rectI);
            Rect rectE = e();
            a(map, "screen", rectI);
            a(map, "visible", rectC);
            a(map, "maybe", rectC);
            a(map, "view", rectE);
            map.put("inFocus", String.valueOf(h() ? 1 : 0));
            map.put("dr", new StringBuilder().append(j().density).toString());
            return a(map, false);
        } catch (Exception e) {
            return "{}";
        }
    }

    @Override // com.moat.analytics.mobile.tjy.bh
    public void a(View view) {
        if (this.f.b()) {
            Log.d("MoatViewTracker", "changing view to " + (view != null ? view.getClass().getSimpleName() + "@" + view.hashCode() : "null"));
        }
        this.a = view;
    }

    @Override // com.moat.analytics.mobile.tjy.m
    public String b() {
        try {
            return a(f(), true);
        } catch (Exception e) {
            return "{}";
        }
    }

    @Override // com.moat.analytics.mobile.tjy.bh
    public boolean c() {
        if (this.f.b()) {
            Log.d("MoatViewTracker", "Attempting bridge installation.");
        }
        boolean zA = this.d.a(this.b, this);
        if (this.f.b()) {
            Log.d("MoatViewTracker", "Bridge " + (zA ? "" : "not ") + "installed.");
        }
        return zA;
    }

    @Override // com.moat.analytics.mobile.tjy.bh
    public void d() {
        this.d.a();
    }

    @Override // com.moat.analytics.mobile.tjy.bh
    public Rect e() {
        int[] iArr = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.a.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(i, i2, this.a.getWidth() + i, this.a.getHeight() + i2);
    }

    public Map f() {
        HashMap map = new HashMap();
        String strG = g();
        String string = Integer.toString(Build.VERSION.SDK_INT);
        String str = this.c ? "1" : "0";
        map.put("versionHash", "8ace5ca5da6b9adb3c0f055aad4a98c2aedf4bd7");
        map.put("appName", strG);
        map.put("namespace", "TJY");
        map.put("version", "1.7.10");
        map.put("deviceOS", string);
        map.put("isNative", str);
        return map;
    }
}
