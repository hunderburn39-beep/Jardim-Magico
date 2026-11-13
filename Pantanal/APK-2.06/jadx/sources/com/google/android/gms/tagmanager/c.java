package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class c extends aj {
    private static final String ID = com.google.android.gms.internal.a.ADVERTISING_TRACKING_ENABLED.toString();
    private final a Wz;

    public c(Context context) {
        this(a.E(context));
    }

    c(a aVar) {
        super(ID, new String[0]);
        this.Wz = aVar;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        return dh.r(Boolean.valueOf(!this.Wz.isLimitAdTrackingEnabled()));
    }
}
