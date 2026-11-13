package com.google.android.gms.analytics;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ab {
    private final Map<String, Integer> vt = new HashMap();
    private final Map<String, String> vu = new HashMap();
    private final boolean vv;
    private final String vw;

    ab(String str, boolean z) {
        this.vv = z;
        this.vw = str;
    }

    void c(String str, int i) {
        if (this.vv) {
            Integer num = this.vt.get(str);
            if (num == null) {
                num = 0;
            }
            this.vt.put(str, Integer.valueOf(num.intValue() + i));
        }
    }

    String cU() {
        if (!this.vv) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.vw);
        for (String str : this.vt.keySet()) {
            sb.append("&").append(str).append("=").append(this.vt.get(str));
        }
        for (String str2 : this.vu.keySet()) {
            sb.append("&").append(str2).append("=").append(this.vu.get(str2));
        }
        return sb.toString();
    }
}
