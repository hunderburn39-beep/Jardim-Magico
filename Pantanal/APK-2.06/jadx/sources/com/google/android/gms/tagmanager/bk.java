package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings;
import com.tapjoy.TapjoyConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class bk extends aj {
    private static final String ID = com.google.android.gms.internal.a.MOBILE_ADWORDS_UNIQUE_ID.toString();
    private final Context mContext;

    public bk(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    protected String G(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        String strG = G(this.mContext);
        return strG == null ? dh.lT() : dh.r(strG);
    }
}
