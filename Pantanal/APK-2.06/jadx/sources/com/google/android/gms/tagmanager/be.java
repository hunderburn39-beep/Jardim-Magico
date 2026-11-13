package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class be extends bx {
    private static final String ID = com.google.android.gms.internal.a.LESS_THAN.toString();

    public be() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.bx
    protected boolean a(dg dgVar, dg dgVar2, Map<String, com.google.android.gms.internal.d.a> map) {
        return dgVar.compareTo(dgVar2) < 0;
    }
}
