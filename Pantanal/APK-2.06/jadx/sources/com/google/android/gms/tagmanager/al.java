package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class al extends bx {
    private static final String ID = com.google.android.gms.internal.a.GREATER_EQUALS.toString();

    public al() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.bx
    protected boolean a(dg dgVar, dg dgVar2, Map<String, com.google.android.gms.internal.d.a> map) {
        return dgVar.compareTo(dgVar2) >= 0;
    }
}
