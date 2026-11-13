package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class dc extends cc {
    public dc(String str) {
        super(str);
    }

    @Override // com.google.android.gms.tagmanager.cc
    protected boolean a(com.google.android.gms.internal.d.a aVar, com.google.android.gms.internal.d.a aVar2, Map<String, com.google.android.gms.internal.d.a> map) {
        String strJ = dh.j(aVar);
        String strJ2 = dh.j(aVar2);
        if (strJ == dh.lS() || strJ2 == dh.lS()) {
            return false;
        }
        return a(strJ, strJ2, map);
    }

    protected abstract boolean a(String str, String str2, Map<String, com.google.android.gms.internal.d.a> map);
}
