package com.google.android.gms.tagmanager;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class x implements bi {
    private int sz = 5;

    x() {
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void b(String str, Throwable th) {
        if (this.sz <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void c(String str, Throwable th) {
        if (this.sz <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void setLogLevel(int logLevel) {
        this.sz = logLevel;
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void v(String str) {
        if (this.sz <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void w(String str) {
        if (this.sz <= 6) {
            Log.e("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void x(String str) {
        if (this.sz <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void y(String str) {
        if (this.sz <= 2) {
            Log.v("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.bi
    public void z(String str) {
        if (this.sz <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }
}
