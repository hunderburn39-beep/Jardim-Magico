package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ah extends aj {
    private static final String ID = com.google.android.gms.internal.a.EVENT.toString();
    private final cs WL;

    public ah(cs csVar) {
        super(ID, new String[0]);
        this.WL = csVar;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        String strLx = this.WL.lx();
        return strLx == null ? dh.lT() : dh.r(strLx);
    }
}
