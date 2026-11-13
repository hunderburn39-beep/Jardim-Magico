package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class e extends aj {
    private static final String ID = com.google.android.gms.internal.a.ADWORDS_CLICK_REFERRER.toString();
    private static final String WA = com.google.android.gms.internal.b.COMPONENT.toString();
    private static final String WB = com.google.android.gms.internal.b.CONVERSION_ID.toString();
    private final Context kI;

    public e(Context context) {
        super(ID, WB);
        this.kI = context;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        com.google.android.gms.internal.d.a aVar = map.get(WB);
        if (aVar == null) {
            return dh.lT();
        }
        String strJ = dh.j(aVar);
        com.google.android.gms.internal.d.a aVar2 = map.get(WA);
        String strE = ay.e(this.kI, strJ, aVar2 != null ? dh.j(aVar2) : null);
        return strE != null ? dh.r(strE) : dh.lT();
    }
}
