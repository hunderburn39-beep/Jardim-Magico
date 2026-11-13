package com.google.android.gms.analytics;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class aj implements j {
    String wh;
    double wi = -1.0d;
    int wj = -1;
    int wk = -1;
    int wl = -1;
    int wm = -1;
    Map<String, String> wn = new HashMap();

    aj() {
    }

    public String M(String str) {
        String str2 = this.wn.get(str);
        return str2 != null ? str2 : str;
    }

    public boolean dj() {
        return this.wh != null;
    }

    public String dk() {
        return this.wh;
    }

    public boolean dl() {
        return this.wi >= 0.0d;
    }

    public double dm() {
        return this.wi;
    }

    public boolean dn() {
        return this.wj >= 0;
    }

    /* JADX INFO: renamed from: do, reason: not valid java name */
    public boolean m1do() {
        return this.wk != -1;
    }

    public boolean dp() {
        return this.wk == 1;
    }

    public boolean dq() {
        return this.wl != -1;
    }

    public boolean dr() {
        return this.wl == 1;
    }

    public boolean ds() {
        return this.wm == 1;
    }

    public int getSessionTimeout() {
        return this.wj;
    }

    public String h(Activity activity) {
        return M(activity.getClass().getCanonicalName());
    }
}
