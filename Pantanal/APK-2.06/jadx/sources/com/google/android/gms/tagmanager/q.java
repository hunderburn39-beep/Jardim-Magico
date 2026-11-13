package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class q extends dc {
    private static final String ID = com.google.android.gms.internal.a.CONTAINS.toString();

    public q() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.dc
    protected boolean a(String str, String str2, Map<String, com.google.android.gms.internal.d.a> map) {
        return str.contains(str2);
    }
}
