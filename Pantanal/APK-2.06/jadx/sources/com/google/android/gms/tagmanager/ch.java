package com.google.android.gms.tagmanager;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: loaded from: classes.dex */
class ch extends dc {
    private static final String ID = com.google.android.gms.internal.a.REGEX.toString();
    private static final String Zb = com.google.android.gms.internal.b.IGNORE_CASE.toString();

    public ch() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.dc
    protected boolean a(String str, String str2, Map<String, com.google.android.gms.internal.d.a> map) {
        try {
            return Pattern.compile(str2, dh.n(map.get(Zb)).booleanValue() ? 66 : 64).matcher(str).find();
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
