package com.google.android.gms.tagmanager;

import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class bc extends aj {
    private static final String ID = com.google.android.gms.internal.a.LANGUAGE.toString();

    public bc() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        String language;
        Locale locale = Locale.getDefault();
        if (locale != null && (language = locale.getLanguage()) != null) {
            return dh.r(language.toLowerCase());
        }
        return dh.lT();
    }
}
