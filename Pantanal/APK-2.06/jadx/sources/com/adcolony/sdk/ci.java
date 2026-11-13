package com.adcolony.sdk;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ci {
    private static final String A = "close_method";
    private static final AlphaAnimation B = new AlphaAnimation(BitmapDescriptorFactory.HUE_RED, 1.0f);
    private static final AlphaAnimation C = new AlphaAnimation(1.0f, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation D = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, -1.0f, 1, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation E = new TranslateAnimation(1, -1.0f, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation F = new TranslateAnimation(1, 1.0f, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation G = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, 1.0f, 1, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation H = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, 1.0f, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation I = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, -1.0f, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED);
    private static final TranslateAnimation J = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, -1.0f);
    private static final TranslateAnimation K = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, 1.0f);
    private static final TranslateAnimation L = new TranslateAnimation(1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED, 1, BitmapDescriptorFactory.HUE_RED);
    private static final String j = "time_to_click_ms";
    private static final String k = "source_id";
    private static final String l = "duration_ms";
    private static final String m = "duration_animation_in_ms";
    private static final String n = "duration_animation_out_ms";
    private static final String o = "width";
    private static final String p = "height";
    private static final String q = "type";
    private static final String r = "display_type_s";
    private static final String s = "position";
    private static final String t = "animation_in";
    private static final String u = "animation_out";
    private static final String v = "scaling";
    private static final String w = "presentation_delay_ms";
    private static final String x = "modal_background_color_rgba";
    private static final String y = "slug";
    private static final String z = "metadata";
    Map<String, Object> f;
    private Map<String, Object> h;
    a a = a.NONE;
    c b = c.NONE;
    long c = 0;
    long d = 0;
    long e = 0;
    private bm i = bv.aN().aw();
    d g = new b();

    enum a {
        NONE,
        UNKNOWN,
        USER_SWIPE,
        USER_CLOSE_BUTTON,
        USER_TAP_BACKGROUND,
        REDIRECT_TO_CATALOG,
        DEV_REDEMPTION,
        NATURAL_TIMEOUT,
        FROM_JS,
        HARDWARE_BACK_BUTTON,
        ACTIVITY_HIDDEN
    }

    enum c {
        NONE,
        QUEUED,
        SHOWING,
        HIDDEN,
        DISMISSING
    }

    interface d {
        WebView a();

        void a(bk bkVar);

        Context b();

        void c();
    }

    private static AlphaAnimation a(boolean z2) {
        if (z2) {
            return new AlphaAnimation(0.1f, 1.0f);
        }
        return new AlphaAnimation(1.0f, BitmapDescriptorFactory.HUE_RED);
    }

    class b implements d {
        b() {
        }

        @Override // com.adcolony.sdk.ci.d
        public WebView a() {
            return null;
        }

        @Override // com.adcolony.sdk.ci.d
        public Context b() {
            return null;
        }

        @Override // com.adcolony.sdk.ci.d
        public void c() {
        }

        @Override // com.adcolony.sdk.ci.d
        public void a(bk bkVar) {
            bkVar.a(bn.a.YVOLVER_ERROR_NONE);
        }
    }

    ci(Map<String, Object> map) {
        this.h = map;
    }

    void a() {
        this.e = System.currentTimeMillis();
    }

    private String y() {
        return "AdColonyPubServices";
    }

    int b() {
        int iA = this.i.a();
        if (this.h.containsKey(l)) {
            return ((Integer) this.h.get(l)).intValue();
        }
        return iA;
    }

    String c() {
        if (!this.h.containsKey("notification")) {
            return null;
        }
        return (String) this.h.get("notification");
    }

    int d() {
        int iG = this.i.g();
        if (this.h.containsKey("position_type")) {
            return ((Integer) this.h.get("position_type")).intValue();
        }
        return iG;
    }

    int e() {
        int iH = this.i.h();
        if (this.h.containsKey("animation_in_type")) {
            return ((Integer) this.h.get("animation_in_type")).intValue();
        }
        return iH;
    }

    int f() {
        int iB = this.i.b();
        if (this.h.containsKey(m)) {
            return ((Integer) this.h.get(m)).intValue();
        }
        return iB;
    }

    int g() {
        int i = this.i.i();
        if (this.h.containsKey("animation_out_type")) {
            return ((Integer) this.h.get("animation_out_type")).intValue();
        }
        return i;
    }

    int h() {
        int iC = this.i.c();
        if (this.h.containsKey(n)) {
            return ((Integer) this.h.get(n)).intValue();
        }
        return iC;
    }

    int i() {
        int iF = this.i.f();
        if (this.h.containsKey("toast_default_width")) {
            return ((Integer) this.h.get("toast_default_width")).intValue();
        }
        return iF;
    }

    int j() {
        int iE = this.i.e();
        if (this.h.containsKey("toast_default_height")) {
            return ((Integer) this.h.get("toast_default_height")).intValue();
        }
        return iE;
    }

    int k() {
        int iT = this.i.t();
        if (this.h.containsKey(w)) {
            return ((Integer) this.h.get(w)).intValue();
        }
        return iT;
    }

    String l() {
        String strU = this.i.u();
        if (this.h.containsKey("modal_background_rgba")) {
            return (String) this.h.get("modal_background_rgba");
        }
        return strU;
    }

    Map<String, Object> m() {
        HashMap map = new HashMap();
        if (this.h.containsKey(z)) {
            return (Map) this.h.get(z);
        }
        return map;
    }

    Map<String, Object> n() {
        if (this.f == null) {
            this.f = new HashMap();
        }
        return this.f;
    }

    Animation a(int i) {
        switch (i) {
            case 1:
                return D;
            case 2:
                return E;
            case 4:
                return F;
            case 8:
                return G;
            case 16:
                return B;
            default:
                return L;
        }
    }

    String a(a aVar) {
        switch (aVar) {
            case NONE:
                return "NONE";
            case UNKNOWN:
                return "UNKNOWN";
            case USER_SWIPE:
                return "USER_SWIPE";
            case USER_CLOSE_BUTTON:
                return "USER_CLOSE_BUTTON";
            case USER_TAP_BACKGROUND:
                return "USER_TAP_BACKGROUND";
            case REDIRECT_TO_CATALOG:
                return "REDIRECT_TO_CATALOG";
            case DEV_REDEMPTION:
                return "DEV_REDEMPTION";
            case NATURAL_TIMEOUT:
                return "NATURAL_TIMEOUT";
            case FROM_JS:
                return "FROM_JS";
            case HARDWARE_BACK_BUTTON:
                return "HARDWARE_BACK_BUTTON";
            case ACTIVITY_HIDDEN:
                return "ACTIVITY_HIDDEN";
            default:
                return "INVALID";
        }
    }

    int o() {
        if (!this.h.containsKey("scaling_type")) {
            return 0;
        }
        return ((Integer) this.h.get("scaling_type")).intValue();
    }

    String b(int i) {
        String str;
        if (i != 0) {
            str = (i & 2) != 0 ? "HEIGHT " : "";
            if ((i & 1) != 0) {
                str = str + "WIDTH ";
            }
        } else {
            str = "NONE";
        }
        return str.trim();
    }

    int p() {
        if (!this.h.containsKey("reward_type")) {
            return -1;
        }
        return ((Integer) this.h.get("reward_type")).intValue();
    }

    String c(int i) {
        switch (i) {
            case 0:
                return "IAP_REWARD";
            case 1:
                return "CUSTOM_REWARD";
            case 2:
                return "ANNOUNCEMENT";
            case 3:
                return "SHOW_BADGE";
            case 4:
                return "ACTIVITY_COMPLETE";
            case 5:
                return "VIP";
            default:
                return "INVALID";
        }
    }

    String q() {
        if (!this.h.containsKey(k)) {
            return "";
        }
        return (String) this.h.get(k);
    }

    int r() {
        if (!this.h.containsKey("display_type")) {
            return -1;
        }
        return ((Integer) this.h.get("display_type")).intValue();
    }

    String d(int i) {
        switch (i) {
            case 0:
                return "MODAL";
            case 1:
                return "TOAST";
            case 2:
                return "NOT_SHOWN";
            default:
                return "INVALID";
        }
    }

    String e(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "TOP_LEFT";
            case 2:
                return "TOP_CENTER";
            case 3:
                return "TOP_RIGHT";
            case 4:
                return "BOTTOM_LEFT";
            case 5:
                return "BOTTOM_CENTER";
            case 6:
                return "BOTTOM_RIGHT";
            case 7:
                return "CENTER_LEFT";
            case 8:
                return "CENTER";
            case 9:
                return "CENTER_RIGHT";
            default:
                return "INVALID";
        }
    }

    String f(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "SLIDE_DOWN";
            case 2:
                return "SLIDE_RIGHT";
            case 4:
                return "SLIDE_LEFT";
            case 8:
                return "SLIDE_UP";
            case 16:
                return "FADE";
            default:
                return "";
        }
    }

    Map<String, Object> s() {
        HashMap map = new HashMap();
        map.put(j, Long.valueOf(this.c));
        map.put(k, q());
        map.put(l, Integer.valueOf(b()));
        map.put(m, Integer.valueOf(f()));
        map.put(n, Integer.valueOf(h()));
        map.put("width", Integer.valueOf(i()));
        map.put("height", Integer.valueOf(j()));
        map.put("type", c(p()));
        map.put(r, d(r()));
        map.put(s, e(d()));
        map.put(t, f(e()));
        map.put(u, f(g()));
        map.put(v, b(o()));
        map.put(w, Integer.valueOf(k()));
        map.put(x, l());
        map.put(y, n());
        map.put(z, m());
        map.put(A, a(this.a));
        return map;
    }

    Animation g(int i) {
        switch (i) {
            case 1:
                return K;
            case 2:
                return H;
            case 4:
                return I;
            case 8:
                return J;
            case 16:
                return C;
            default:
                return L;
        }
    }

    int h(int i) {
        switch (i) {
            case 0:
            case 2:
            default:
                return 48;
            case 1:
                return 8388659;
            case 3:
                return 8388661;
            case 4:
                return 8388691;
            case 5:
                return 80;
            case 6:
                return 8388693;
            case 7:
                return 8388627;
            case 8:
                return 17;
            case 9:
                return 8388629;
        }
    }

    void a(d dVar) {
        this.g = dVar;
    }

    d t() {
        return this.g;
    }

    long u() {
        return this.d;
    }

    void v() {
        this.d = System.currentTimeMillis() - this.e;
    }

    void w() {
        this.c = System.currentTimeMillis() - this.e;
    }

    long x() {
        return this.c;
    }
}
