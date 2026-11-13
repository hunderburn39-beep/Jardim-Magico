package com.chartboost.sdk.impl;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
class ap extends ac<Bitmap> {
    private static final Object a = new Object();
    private final ag<Bitmap> i;

    ap(String str, ag<Bitmap> agVar, ab abVar) {
        super(ac.a.GET, str, abVar, null);
        this.i = agVar;
    }

    @Override // com.chartboost.sdk.impl.ac
    public ae<Bitmap> a(ah ahVar) {
        ae<Bitmap> aeVarA;
        synchronized (a) {
            try {
                Bitmap bitmapA = g.a().a(ahVar.a());
                if (bitmapA != null) {
                    aeVarA = ae.a(bitmapA);
                } else {
                    aeVarA = ae.a((Exception) new aw());
                }
            } catch (OutOfMemoryError e) {
                aeVarA = ae.a((Exception) new aw(e));
            }
        }
        return aeVarA;
    }

    @Override // com.chartboost.sdk.impl.ac
    public void a(Bitmap bitmap) {
        this.i.a(bitmap);
    }
}
