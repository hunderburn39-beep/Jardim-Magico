package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class b extends aj {
    private static final String ID = com.google.android.gms.internal.a.ADVERTISER_ID.toString();
    private final a Wz;

    public b(Context context) {
        this(a.E(context));
    }

    b(a aVar) {
        super(ID, new String[0]);
        this.Wz = aVar;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        String strJT = this.Wz.jT();
        return strJT == null ? dh.lT() : dh.r(strJT);
    }
}
