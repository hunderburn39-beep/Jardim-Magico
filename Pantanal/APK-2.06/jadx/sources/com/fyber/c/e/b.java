package com.fyber.c.e;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: compiled from: SpinnerLayout.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends com.fyber.c.a {
    private Animation b;
    private View c;
    private ImageView d;

    public b(Context context) {
        super(context);
        this.c = new View(context);
        this.b = new RotateAnimation(BitmapDescriptorFactory.HUE_RED, 360.0f, 1, 0.5f, 1, 0.5f);
        this.b.setRepeatCount(-1);
        this.b.setDuration(1000L);
        this.b.setInterpolator(new LinearInterpolator());
        this.b.setFillAfter(true);
        int iA = a(100);
        int iA2 = a(90);
        this.c.setLayoutParams(new FrameLayout.LayoutParams(iA, iA));
        View view = this.c;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1304543682);
        gradientDrawable.setShape(0);
        gradientDrawable.setUseLevel(false);
        gradientDrawable.setCornerRadius(8.0f);
        view.setBackgroundDrawable(gradientDrawable);
        addView(this.c);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iA2, iA2);
        layoutParams.addRule(13, -1);
        this.d = a(new a());
        this.d.setLayoutParams(layoutParams);
        setContentDescription("loadingSpinner");
    }

    public final void a() {
        this.d.startAnimation(this.b);
    }

    public final void b() {
        this.d.clearAnimation();
    }
}
