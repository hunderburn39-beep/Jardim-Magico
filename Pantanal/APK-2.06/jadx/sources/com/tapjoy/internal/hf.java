package com.tapjoy.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class hf extends RelativeLayout {
    private gn a;
    private a b;
    private af c;
    private int d;
    private int e;
    private ArrayList f;
    private gw g;

    public interface a {
        void a();

        void a(gv gvVar);
    }

    public hf(Context context, gn gnVar, a aVar) {
        super(context);
        this.c = af.UNSPECIFIED;
        this.d = 0;
        this.e = 0;
        this.f = new ArrayList();
        this.g = null;
        this.a = gnVar;
        this.b = aVar;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.b.a();
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0129  */
    @Override // android.widget.RelativeLayout, android.view.View
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float f;
        float f2;
        float f3;
        float f4;
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (size >= size2) {
            if (this.c != af.LANDSCAPE) {
                this.c = af.LANDSCAPE;
                a();
            }
        } else if (this.c != af.PORTRAIT) {
            this.c = af.PORTRAIT;
            a();
        }
        if (this.d != size || this.e != size2) {
            this.d = size;
            this.e = size2;
            float f5 = size;
            float f6 = size2;
            if (this.g == null || this.g.b == null) {
                f = 0.0f;
                f2 = 0.0f;
                f3 = f5;
                f4 = f6;
            } else {
                float f7 = ((this.g.b.y * f5) / this.g.b.x) / f6;
                if (f7 < 1.0f) {
                    float f8 = (this.g.b.y * f5) / this.g.b.x;
                    f3 = f5;
                    f = 0.0f;
                    f2 = (f6 - f8) / 2.0f;
                    f4 = f8;
                } else if (f7 <= 1.0f) {
                    f = 0.0f;
                    f2 = 0.0f;
                    f3 = f5;
                    f4 = f6;
                } else {
                    float f9 = (this.g.b.x * f6) / this.g.b.y;
                    f2 = 0.0f;
                    f = (f5 - f9) / 2.0f;
                    f4 = f6;
                    f3 = f9;
                }
            }
            for (View view : ah.a(this)) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                gv gvVar = (gv) view.getTag();
                float fA = gvVar.a.a(f3, f4);
                float fA2 = gvVar.b.a(f3, f4);
                float fA3 = gvVar.c.a(f3, f4);
                float fA4 = gvVar.d.a(f3, f4);
                int i = gvVar.e;
                int i2 = gvVar.f;
                if (i == 14) {
                    i = 9;
                    fA += (f3 - fA3) / 2.0f;
                }
                if (i2 == 15) {
                    i2 = 10;
                    fA2 += (f4 - fA4) / 2.0f;
                }
                layoutParams.addRule(i, -1);
                layoutParams.addRule(i2, -1);
                layoutParams.width = Math.round(fA3);
                layoutParams.height = Math.round(fA4);
                if (i == 9) {
                    layoutParams.leftMargin = Math.round(f + fA);
                } else if (i == 11) {
                    layoutParams.rightMargin = Math.round(f + fA);
                }
                if (i2 == 10) {
                    layoutParams.topMargin = Math.round(f2 + fA2);
                } else if (i2 == 12) {
                    layoutParams.bottomMargin = Math.round(f2 + fA2);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void a() {
        gw gwVar;
        Iterator it = this.a.a.iterator();
        gw gwVar2 = null;
        while (true) {
            if (!it.hasNext()) {
                gwVar = gwVar2;
                break;
            }
            gwVar = (gw) it.next();
            if (gwVar.a == this.c) {
                break;
            }
            if (gwVar.a != af.UNSPECIFIED) {
                gwVar = gwVar2;
            }
            gwVar2 = gwVar;
        }
        removeAllViews();
        if (gwVar == null) {
            return;
        }
        this.g = gwVar;
        Context context = getContext();
        for (final gv gvVar : gwVar.c) {
            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
            Bitmap bitmap = gvVar.l.b;
            Bitmap bitmap2 = gvVar.m != null ? gvVar.m.b : null;
            final BitmapDrawable bitmapDrawable = new BitmapDrawable((Resources) null, bitmap);
            final BitmapDrawable bitmapDrawable2 = bitmap2 != null ? new BitmapDrawable((Resources) null, bitmap2) : null;
            ag.a(view, bitmapDrawable);
            view.setOnTouchListener(new View.OnTouchListener() { // from class: com.tapjoy.internal.hf.1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 0) {
                        if (bitmapDrawable2 != null) {
                            ag.a(v, bitmapDrawable2);
                            return false;
                        }
                        return false;
                    }
                    if (event.getAction() == 1) {
                        ag.a(v, bitmapDrawable);
                        return false;
                    }
                    return false;
                }
            });
            view.setOnClickListener(new View.OnClickListener() { // from class: com.tapjoy.internal.hf.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View v) {
                    hf.this.b.a(gvVar);
                }
            });
            view.setTag(gvVar);
            addView(view, layoutParams);
        }
    }
}
