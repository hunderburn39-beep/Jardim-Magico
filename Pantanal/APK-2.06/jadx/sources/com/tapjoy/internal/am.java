package com.tapjoy.internal;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public final class am {
    protected int a = 0;
    protected float b = BitmapDescriptorFactory.HUE_RED;
    protected int c = 0;
    protected float d = BitmapDescriptorFactory.HUE_RED;
    protected int e = 0;
    protected float f = BitmapDescriptorFactory.HUE_RED;
    protected int g = 0;
    protected float h = BitmapDescriptorFactory.HUE_RED;

    public final Animation a() {
        return new TranslateAnimation(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
    }

    public final am a(float f) {
        this.a = 1;
        this.b = f;
        return this;
    }

    public final am b(float f) {
        this.e = 1;
        this.f = f;
        return this;
    }
}
