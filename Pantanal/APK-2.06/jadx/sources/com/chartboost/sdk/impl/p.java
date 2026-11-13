package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public abstract class p extends RelativeLayout {
    protected l a;
    private q b;
    private a c;

    public enum a {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    protected abstract View a();

    protected abstract int b();

    public p(Context context, l lVar) {
        super(context);
        this.a = lVar;
        this.c = a.BOTTOM;
        a(context);
    }

    public void a(a aVar) {
        if (aVar == null) {
            CBLogging.b("ToasterView", "Side object cannot be null");
            return;
        }
        this.c = aVar;
        RelativeLayout.LayoutParams layoutParams = null;
        setClickable(false);
        int iB = b();
        switch (this.c) {
            case TOP:
                layoutParams = new RelativeLayout.LayoutParams(-1, CBUtility.a(iB, getContext()));
                layoutParams.addRule(10);
                this.b.b(1);
                break;
            case BOTTOM:
                layoutParams = new RelativeLayout.LayoutParams(-1, CBUtility.a(iB, getContext()));
                layoutParams.addRule(12);
                this.b.b(4);
                break;
            case LEFT:
                layoutParams = new RelativeLayout.LayoutParams(CBUtility.a(iB, getContext()), -1);
                layoutParams.addRule(9);
                this.b.b(8);
                break;
            case RIGHT:
                layoutParams = new RelativeLayout.LayoutParams(CBUtility.a(iB, getContext()), -1);
                layoutParams.addRule(11);
                this.b.b(2);
                break;
        }
        setLayoutParams(layoutParams);
    }

    private void a(Context context) {
        Context context2 = getContext();
        setGravity(17);
        this.b = new q(context2);
        this.b.a(-1);
        this.b.setBackgroundColor(-855638017);
        addView(this.b, new RelativeLayout.LayoutParams(-1, -1));
        addView(a(), new RelativeLayout.LayoutParams(-1, -1));
    }

    public void a(boolean z) {
        a(z, 500L);
    }

    private void a(final boolean z, long j) {
        TranslateAnimation translateAnimation;
        this.a.D = z;
        if (z && getVisibility() == 0) {
            return;
        }
        if (z || getVisibility() != 8) {
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.impl.p.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!z) {
                        p.this.setVisibility(8);
                        p.this.clearAnimation();
                    }
                    synchronized (p.this.a.h) {
                        p.this.a.h.remove(p.this);
                    }
                }
            };
            if (z) {
                setVisibility(0);
            }
            float fA = CBUtility.a(b(), getContext());
            switch (this.c) {
                case TOP:
                    translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, z ? -fA : 0.0f, z ? 0.0f : -fA);
                    break;
                case BOTTOM:
                    float f = z ? fA : 0.0f;
                    if (z) {
                        fA = 0.0f;
                    }
                    translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, f, fA);
                    break;
                case LEFT:
                    translateAnimation = new TranslateAnimation(z ? -fA : 0.0f, z ? 0.0f : -fA, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    break;
                case RIGHT:
                    float f2 = z ? fA : 0.0f;
                    if (z) {
                        fA = 0.0f;
                    }
                    translateAnimation = new TranslateAnimation(f2, fA, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    break;
                default:
                    translateAnimation = null;
                    break;
            }
            translateAnimation.setDuration(j);
            translateAnimation.setFillAfter(!z);
            startAnimation(translateAnimation);
            synchronized (this.a.h) {
                this.a.h.put(this, runnable);
            }
            g.a().a.postDelayed(runnable, j);
        }
    }
}
