package com.tapjoy;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyDisplayMetricsUtil {
    private Context a;
    private Configuration b;
    private DisplayMetrics c = new DisplayMetrics();

    public TapjoyDisplayMetricsUtil(Context theContext) {
        this.a = theContext;
        ((WindowManager) this.a.getSystemService("window")).getDefaultDisplay().getMetrics(this.c);
        this.b = this.a.getResources().getConfiguration();
    }

    public int getScreenDensityDPI() {
        return this.c.densityDpi;
    }

    public float getScreenDensityScale() {
        return this.c.density;
    }

    public int getScreenLayoutSize() {
        return this.b.screenLayout & 15;
    }
}
