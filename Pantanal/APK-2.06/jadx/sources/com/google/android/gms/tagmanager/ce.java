package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ce extends aj {
    private static final String ID = com.google.android.gms.internal.a.RANDOM.toString();
    private static final String YX = com.google.android.gms.internal.b.MIN.toString();
    private static final String YY = com.google.android.gms.internal.b.MAX.toString();

    public ce() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x005c  */
    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        double dDoubleValue;
        double d;
        com.google.android.gms.internal.d.a aVar = map.get(YX);
        com.google.android.gms.internal.d.a aVar2 = map.get(YY);
        if (aVar == null || aVar == dh.lT() || aVar2 == null || aVar2 == dh.lT()) {
            dDoubleValue = 2.147483647E9d;
            d = 0.0d;
        } else {
            dg dgVarK = dh.k(aVar);
            dg dgVarK2 = dh.k(aVar2);
            if (dgVarK == dh.lR() || dgVarK2 == dh.lR()) {
                dDoubleValue = 2.147483647E9d;
                d = 0.0d;
            } else {
                double dDoubleValue2 = dgVarK.doubleValue();
                dDoubleValue = dgVarK2.doubleValue();
                if (dDoubleValue2 <= dDoubleValue) {
                    d = dDoubleValue2;
                } else {
                    dDoubleValue = 2.147483647E9d;
                    d = 0.0d;
                }
            }
        }
        return dh.r(Long.valueOf(Math.round(((dDoubleValue - d) * Math.random()) + d)));
    }
}
