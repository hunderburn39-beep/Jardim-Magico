package com.google.android.gms.analytics;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class l implements Logger {
    private int sz = 1;

    l() {
    }

    private String E(String str) {
        return Thread.currentThread().toString() + ": " + str;
    }

    @Override // com.google.android.gms.analytics.Logger
    public void error(Exception exception) {
        if (this.sz <= 3) {
            Log.e("GAV4", null, exception);
        }
    }

    @Override // com.google.android.gms.analytics.Logger
    public void error(String msg) {
        if (this.sz <= 3) {
            Log.e("GAV4", E(msg));
        }
    }

    @Override // com.google.android.gms.analytics.Logger
    public int getLogLevel() {
        return this.sz;
    }

    @Override // com.google.android.gms.analytics.Logger
    public void info(String msg) {
        if (this.sz <= 1) {
            Log.i("GAV4", E(msg));
        }
    }

    @Override // com.google.android.gms.analytics.Logger
    public void setLogLevel(int level) {
        this.sz = level;
    }

    @Override // com.google.android.gms.analytics.Logger
    public void verbose(String msg) {
        if (this.sz <= 0) {
            Log.v("GAV4", E(msg));
        }
    }

    @Override // com.google.android.gms.analytics.Logger
    public void warn(String msg) {
        if (this.sz <= 2) {
            Log.w("GAV4", E(msg));
        }
    }
}
