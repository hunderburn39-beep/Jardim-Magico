package com.google.android.gms.internal;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class z {
    private final String le;
    private final JSONObject lf;
    private final String lg;
    private final String lh;

    public z(String str, dx dxVar, String str2, JSONObject jSONObject) {
        this.lh = dxVar.rq;
        this.lf = jSONObject;
        this.lg = str;
        this.le = str2;
    }

    public String al() {
        return this.le;
    }

    public String am() {
        return this.lh;
    }

    public JSONObject an() {
        return this.lf;
    }

    public String ao() {
        return this.lg;
    }
}
