package com.google.android.gms.tagmanager;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class cc extends aj {
    private static final String XQ = com.google.android.gms.internal.b.ARG0.toString();
    private static final String YN = com.google.android.gms.internal.b.ARG1.toString();

    public cc(String str) {
        super(str, XQ, YN);
    }

    protected abstract boolean a(com.google.android.gms.internal.d.a aVar, com.google.android.gms.internal.d.a aVar2, Map<String, com.google.android.gms.internal.d.a> map);

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        Iterator<com.google.android.gms.internal.d.a> it = map.values().iterator();
        while (it.hasNext()) {
            if (it.next() == dh.lT()) {
                return dh.r(false);
            }
        }
        com.google.android.gms.internal.d.a aVar = map.get(XQ);
        com.google.android.gms.internal.d.a aVar2 = map.get(YN);
        return dh.r(Boolean.valueOf((aVar == null || aVar2 == null) ? false : a(aVar, aVar2, map)));
    }
}
