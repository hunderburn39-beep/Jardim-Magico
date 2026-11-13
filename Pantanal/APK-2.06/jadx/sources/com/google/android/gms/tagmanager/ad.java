package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ad extends dc {
    private static final String ID = com.google.android.gms.internal.a.ENDS_WITH.toString();

    public ad() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.dc
    protected boolean a(String str, String str2, Map<String, com.google.android.gms.internal.d.a> map) {
        return str.endsWith(str2);
    }
}
