package com.google.android.gms.tagmanager;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class w extends df {
    private static final String ID = com.google.android.gms.internal.a.DATA_LAYER_WRITE.toString();
    private static final String VALUE = com.google.android.gms.internal.b.VALUE.toString();
    private static final String XL = com.google.android.gms.internal.b.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer WK;

    public w(DataLayer dataLayer) {
        super(ID, VALUE);
        this.WK = dataLayer;
    }

    private void a(com.google.android.gms.internal.d.a aVar) {
        String strJ;
        if (aVar == null || aVar == dh.lN() || (strJ = dh.j(aVar)) == dh.lS()) {
            return;
        }
        this.WK.bv(strJ);
    }

    private void b(com.google.android.gms.internal.d.a aVar) {
        if (aVar == null || aVar == dh.lN()) {
            return;
        }
        Object objO = dh.o(aVar);
        if (objO instanceof List) {
            for (Object obj : (List) objO) {
                if (obj instanceof Map) {
                    this.WK.push((Map) obj);
                }
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.df
    public void z(Map<String, com.google.android.gms.internal.d.a> map) {
        b(map.get(VALUE));
        a(map.get(XL));
    }
}
