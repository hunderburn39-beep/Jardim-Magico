package com.fyber.reporters.a;

import android.util.Log;
import com.fyber.utils.FyberLogger;

/* JADX INFO: compiled from: ReporterResult.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class c {
    public abstract void a();

    protected abstract String b();

    public final void a(int i) {
        String str = "Report was unsuccessful. Response code: " + i;
        if (FyberLogger.isLogging()) {
            FyberLogger.i(b(), str);
        } else {
            Log.i(b(), str);
        }
    }
}
