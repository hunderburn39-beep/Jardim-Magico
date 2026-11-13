package com.adcolony.sdk;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class bm {
    static final String A = "catalog_cross_fade_duration_ms";
    static final int B = 150;
    static final String C = "catalog_splash_fade_in_ms";
    static final int D = 50;
    static final String E = "catalog_over_status_bar";
    static final String F = "update_stat_frequency_ms";
    static final String H = "stat_save_frequency_ms";
    static final String J = "update_promo_stat_frequency_ms";
    static final String L = "session_resume_grace_period_ms";
    static final String N = "reset_session_timer_ms";
    static final String P = "reset_session_timer_after_exception_ms";
    static final String R = "require_payload_signature";
    static final boolean S = true;
    static final String T = "use_ssl_pinning";
    static final boolean U = false;
    static final String V = "update_event_frequency_ms";
    static final String X = "update_stat_call_delay_ms";
    static final String Z = "persistent_storage_save_frequency_ms";
    static final String a = "AdColonyPubServices";
    static final String ab = "flush_on_background";
    static final boolean ac = false;
    static final String ad = "toast_presentation_delay_ms";
    static final int ae = 0;
    static final String af = "toast_modal_background_rgba";
    static final String ag = "#00000000";
    static final String ah = "modal_background_rgba";
    static final String ai = "#00000000";
    static final String aj = "log_batch_size";
    static final int ak = 25;
    static final String al = "stat_batch_size";
    static final int am = 25;
    static final String an = "server_request_timeout_ms";
    static final String ap = "webview_timeout_ms";
    static final String ar = "close_button_initial_fade_in_ms";
    static final int as = 1500;
    static final String at = "persistence_limit_stat";
    static final int au = 1000;
    static final String av = "persistence_limit_log";
    static final int aw = 1000;
    static final String ax = "lock_fullscreen_view_orientation";
    static final boolean ay = true;
    static final String b = "endpoints";
    static final String c = "signature";
    static final String d = "version";
    static final String e = "catalog_splash";
    static final String f = "control_vars";
    static final String g = "toast_default_width";
    static final int h = 320;
    static final String i = "toast_default_height";
    static final int j = 52;
    static final String k = "toast_default_duration_ms";
    static final String m = "toast_animation_in_ms";
    static final int n = 200;
    static final String o = "toast_animation_out_ms";
    static final int p = 100;
    static final String q = "toast_gap_delay_ms";
    static final int r = 500;
    static final String s = "animation_in_type";
    static final int t = 1;
    static final String u = "animation_out_type";
    static final int v = 8;
    static final String w = "position_type";
    static final int x = 2;
    static final String y = "catalog_border_padding";
    static final int z = 0;
    Map<String, Object> aA = null;
    boolean aB;
    HashMap<String, Object> az;
    static final int l = a(3);
    static final int G = b(5);
    static final int I = a(10);
    static final int K = a(3);
    static final int M = b(5);
    static final int O = b(5);
    static final int Q = b(10);
    static final int W = b(5);
    static final int Y = b(1);
    static final int aa = a(1);
    static final int ao = a(30);
    static final int aq = a(30);

    static final int a(int i2) {
        return i2 * 1000;
    }

    static final int b(int i2) {
        return i2 * 60 * 1000;
    }

    bm() {
    }

    private String H() {
        return a;
    }

    void a(Map<String, Object> map) {
        this.aA = map;
        if (this.aA != null && this.aA.containsKey(f)) {
            this.az = (HashMap) this.aA.get(f);
        }
    }

    String a(String str) {
        if (this.aA == null || !this.aA.containsKey(b) || this.aA.get(b) == null) {
            bw.b(H(), "No endpoints found", true);
            return null;
        }
        HashMap map = (HashMap) this.aA.get(b);
        if (map.containsKey(str)) {
            return (String) map.get(str);
        }
        bw.b(H(), "Missing endpoint for:" + str, true);
        return null;
    }

    int a() {
        if (this.az != null && this.az.containsKey(k) && this.az.get(k) != null) {
            return ((Integer) this.az.get(k)).intValue();
        }
        bw.b(H(), "Default toast duration was returned", true);
        return l;
    }

    int b() {
        if (this.az != null && this.az.containsKey(m) && this.az.get(m) != null) {
            return ((Integer) this.az.get(m)).intValue();
        }
        bw.b(H(), "Default animation in was returned", true);
        return n;
    }

    int c() {
        if (this.az != null && this.az.containsKey(o) && this.az.get(o) != null) {
            return ((Integer) this.az.get(o)).intValue();
        }
        bw.b(H(), "Default Animation out was returned", true);
        return 100;
    }

    int d() {
        if (this.az != null && this.az.containsKey(q) && this.az.get(q) != null) {
            return ((Integer) this.az.get(q)).intValue();
        }
        bw.b(H(), "Default gap delay was returned", true);
        return 500;
    }

    int e() {
        if (this.az != null && this.az.containsKey(i) && this.az.get(i) != null) {
            return ((Integer) this.az.get(i)).intValue();
        }
        bw.b(H(), "Default toast height was returned", true);
        return 52;
    }

    int f() {
        if (this.az != null && this.az.containsKey(g) && this.az.get(g) != null) {
            return ((Integer) this.az.get(g)).intValue();
        }
        bw.b(H(), "Default toast width was returned", true);
        return h;
    }

    int g() {
        if (this.az != null && this.az.containsKey(w) && this.az.get(w) != null) {
            return ((Integer) this.az.get(w)).intValue();
        }
        bw.b(H(), "Default toast position was returned", true);
        return 2;
    }

    int h() {
        if (this.az == null || !this.az.containsKey(s) || this.az.get(s) == null) {
            return 1;
        }
        return ((Integer) this.az.get(s)).intValue();
    }

    int i() {
        if (this.az == null || !this.az.containsKey(u) || this.az.get(u) == null) {
            return 8;
        }
        return ((Integer) this.az.get(o)).intValue();
    }

    String j() {
        if (this.aA == null || !this.aA.containsKey(c)) {
            return null;
        }
        return (String) this.aA.get(c);
    }

    int k() {
        if (this.az != null && this.az.containsKey(F) && this.az.get(F) != null) {
            return ((Integer) this.az.get(F)).intValue();
        }
        bw.b(H(), "default stat frequency was returned", true);
        return G;
    }

    int l() {
        if (this.az != null && this.az.containsKey(J) && this.az.get(J) != null) {
            return ((Integer) this.az.get(J)).intValue();
        }
        bw.b(H(), "default promo stat freq was returned", true);
        return K;
    }

    int m() {
        if (this.aA == null || !this.aA.containsKey(d) || this.aA.get(d) == null) {
            return 0;
        }
        return ((Integer) this.aA.get(d)).intValue();
    }

    int n() {
        if (bv.aN().aD().h() > 0) {
            return (int) bv.aN().aD().h();
        }
        if (this.az != null && this.az.containsKey(L) && this.az.get(L) != null) {
            return ((Integer) this.az.get(L)).intValue();
        }
        return M;
    }

    int o() {
        int[] iArr = {O, Q};
        String[] strArr = {N, P};
        char c2 = this.aB ? (char) 1 : (char) 0;
        int i2 = iArr[c2];
        String str = strArr[c2];
        return (this.az == null || !this.az.containsKey(str)) ? i2 : ((Integer) this.az.get(str)).intValue();
    }

    void a(boolean z2) {
        this.aB = z2;
    }

    int p() {
        int i2 = I;
        if (this.az != null && this.az.containsKey(H) && this.az.get(H) != null) {
            return ((Integer) this.az.get(H)).intValue();
        }
        return i2;
    }

    int q() {
        int i2 = W;
        if (this.az != null && this.az.containsKey(V)) {
            return ((Integer) this.az.get(V)).intValue();
        }
        return i2;
    }

    boolean r() {
        if (this.az == null || !this.az.containsKey(ab)) {
            return false;
        }
        return ((Integer) this.az.get(ab)).intValue() > 0;
    }

    int s() {
        int i2 = Y;
        if (this.az != null && this.az.containsKey(X)) {
            return ((Integer) this.az.get(X)).intValue();
        }
        return i2;
    }

    int t() {
        if (this.az == null || !this.az.containsKey(ad) || this.az.get(ad) == null) {
            return 0;
        }
        return ((Integer) this.az.get(ad)).intValue();
    }

    String u() {
        if (this.az == null || !this.az.containsKey(af) || this.az.get(af) == null) {
            return "#00000000";
        }
        return (String) this.az.get(af);
    }

    String v() {
        if (this.az == null || !this.az.containsKey(ah) || this.az.get(ah) == null) {
            return "#00000000";
        }
        return (String) this.az.get(ah);
    }

    boolean w() {
        if (this.az == null || !this.az.containsKey(R) || this.az.get(R) == null) {
            return true;
        }
        return ((Integer) this.az.get(R)).intValue() == 1;
    }

    int x() {
        if (this.az == null || !this.az.containsKey(aj) || this.az.get(aj) == null) {
            return 25;
        }
        return ((Integer) this.az.get(aj)).intValue();
    }

    int y() {
        if (this.az == null || !this.az.containsKey(al) || this.az.get(al) == null) {
            return 25;
        }
        return ((Integer) this.az.get(al)).intValue();
    }

    int z() {
        int i2 = ao;
        if (this.az != null && this.az.containsKey(an) && this.az.get(an) != null) {
            return ((Integer) this.az.get(an)).intValue();
        }
        return i2;
    }

    int A() {
        int i2 = aq;
        if (this.az != null && this.az.containsKey(ap) && this.az.get(ap) != null) {
            return ((Integer) this.az.get(ap)).intValue();
        }
        return i2;
    }

    int B() {
        if (this.az == null || !this.az.containsKey(ar) || this.az.get(ar) == null) {
            return 1500;
        }
        return ((Integer) this.az.get(ar)).intValue();
    }

    int C() {
        int i2 = aa;
        if (this.az != null && this.az.containsKey(Z) && this.az.get(Z) != null) {
            return ((Integer) this.az.get(Z)).intValue();
        }
        return i2;
    }

    int D() {
        if (this.az == null || !this.az.containsKey(at) || this.az.get(at) == null) {
            return 1000;
        }
        return ((Integer) this.az.get(at)).intValue();
    }

    int E() {
        if (this.az == null || !this.az.containsKey(av) || this.az.get(av) == null) {
            return 1000;
        }
        return ((Integer) this.az.get(av)).intValue();
    }

    boolean F() {
        if (this.az == null || !this.az.containsKey(ax) || this.az.get(ax) == null) {
            return true;
        }
        return ((Boolean) this.az.get(ax)).booleanValue();
    }

    static String G() {
        return "3.0.4";
    }
}
