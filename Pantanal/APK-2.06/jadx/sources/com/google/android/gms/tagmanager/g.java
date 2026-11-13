package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class g extends aj {
    private static final String ID = com.google.android.gms.internal.a.APP_NAME.toString();
    private final Context mContext;

    public g(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            return dh.r(packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mContext.getPackageName(), 0)).toString());
        } catch (PackageManager.NameNotFoundException e) {
            bh.b("App name is not found.", e);
            return dh.lT();
        }
    }
}
