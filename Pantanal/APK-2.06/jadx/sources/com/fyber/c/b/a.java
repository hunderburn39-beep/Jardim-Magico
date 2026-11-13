package com.fyber.c.b;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: compiled from: CloseButtonLayout.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends com.fyber.c.a {
    public a(Context context) {
        super(context);
        int iA = a(30);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-16777216);
        gradientDrawable.setShape(1);
        gradientDrawable.setAlpha(128);
        gradientDrawable.setSize(iA, iA);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new ArcShape(BitmapDescriptorFactory.HUE_RED, 360.0f));
        shapeDrawable.getPaint().setColor(-1);
        shapeDrawable.getPaint().setStrokeWidth(Math.round(1.5f));
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.setIntrinsicHeight(iA);
        shapeDrawable.setIntrinsicWidth(iA);
        a(new LayerDrawable(new Drawable[]{shapeDrawable, gradientDrawable}));
        int iA2 = a(15);
        c cVar = new c(context, 1.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iA2, iA2);
        layoutParams.addRule(13);
        cVar.setLayoutParams(layoutParams);
        addView(cVar);
        setContentDescription("closeButton");
    }

    public final int a() {
        return a(60);
    }
}
