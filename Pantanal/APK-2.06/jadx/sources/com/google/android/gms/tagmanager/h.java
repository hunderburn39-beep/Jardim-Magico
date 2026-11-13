package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class h extends aj {
    private static final String ID = com.google.android.gms.internal.a.APP_VERSION.toString();
    private final Context mContext;

    public h(Context context) {
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
            return dh.r(Integer.valueOf(this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            bh.w("Package name " + this.mContext.getPackageName() + " not found. " + e.getMessage());
            return dh.lT();
        }
    }
}
