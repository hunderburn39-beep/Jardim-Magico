package com.tapjoy.internal;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;

/* JADX INFO: loaded from: classes.dex */
public final class aj extends ai {
    private final AnimationSet b;

    public aj() {
        super(new AnimationSet(true));
        this.b = (AnimationSet) this.a;
    }

    public final aj a(Animation animation) {
        this.b.addAnimation(animation);
        return this;
    }

    @Override // com.tapjoy.internal.ai
    public final /* bridge */ /* synthetic */ Animation a() {
        return this.b;
    }
}
