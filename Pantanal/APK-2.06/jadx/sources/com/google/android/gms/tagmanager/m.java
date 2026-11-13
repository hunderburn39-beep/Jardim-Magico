package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class m extends aj {
    private static final String ID = com.google.android.gms.internal.a.CONSTANT.toString();
    private static final String VALUE = com.google.android.gms.internal.b.VALUE.toString();

    public m() {
        super(ID, VALUE);
    }

    public static String ka() {
        return ID;
    }

    public static String kb() {
        return VALUE;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        return map.get(VALUE);
    }
}
