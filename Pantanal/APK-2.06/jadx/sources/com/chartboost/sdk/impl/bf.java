package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class bf extends RelativeLayout {
    private final Rect a;
    final a c;
    protected Button d;
    boolean e;

    protected abstract void a(MotionEvent motionEvent);

    public bf(Context context) {
        this(context, null);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public bf(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new Rect();
        this.d = null;
        this.e = true;
        this.c = new a(getContext());
        this.c.setOnTouchListener(new View.OnTouchListener() { // from class: com.chartboost.sdk.impl.bf.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                boolean zA = bf.this.a(v, event);
                switch (event.getActionMasked()) {
                    case 0:
                        bf.this.c.a(zA);
                        return zA;
                    case 1:
                        if (bf.this.getVisibility() == 0 && bf.this.isEnabled() && zA) {
                            bf.this.a(event);
                        }
                        bf.this.c.a(false);
                        return true;
                    case 2:
                        bf.this.c.a(zA);
                        return true;
                    case 3:
                    case 4:
                        bf.this.c.a(false);
                        return true;
                    default:
                        return true;
                }
            }
        });
        addView(this.c, new RelativeLayout.LayoutParams(-1, -1));
    }

    boolean a(View view, MotionEvent motionEvent) {
        view.getLocalVisibleRect(this.a);
        this.a.left += view.getPaddingLeft();
        this.a.top += view.getPaddingTop();
        this.a.right -= view.getPaddingRight();
        this.a.bottom -= view.getPaddingBottom();
        return this.a.contains(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
    }

    private class a extends be {
        private boolean c;

        public a(Context context) {
            super(context);
            this.c = false;
            this.c = false;
        }

        protected void a(boolean z) {
            if (bf.this.e && z) {
                if (!this.c) {
                    if (getDrawable() != null) {
                        getDrawable().setColorFilter(1996488704, PorterDuff.Mode.SRC_ATOP);
                    } else if (getBackground() != null) {
                        getBackground().setColorFilter(1996488704, PorterDuff.Mode.SRC_ATOP);
                    }
                    invalidate();
                    this.c = true;
                    return;
                }
                return;
            }
            if (this.c) {
                if (getDrawable() != null) {
                    getDrawable().clearColorFilter();
                } else if (getBackground() != null) {
                    getBackground().clearColorFilter();
                }
                invalidate();
                this.c = false;
            }
        }

        public void a(com.chartboost.sdk.Libraries.i iVar, ViewGroup.LayoutParams layoutParams) {
            a(iVar);
            layoutParams.width = iVar.h();
            layoutParams.height = iVar.i();
        }
    }

    public void a(String str) {
        if (str != null) {
            a().setText(str);
            addView(a(), new RelativeLayout.LayoutParams(-1, -1));
            this.c.setVisibility(8);
            a(false);
            this.d.setOnClickListener(new View.OnClickListener() { // from class: com.chartboost.sdk.impl.bf.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    bf.this.a((MotionEvent) null);
                }
            });
            return;
        }
        if (this.d != null) {
            removeView(a());
            this.d = null;
            this.c.setVisibility(0);
            a(true);
        }
    }

    public TextView a() {
        if (this.d == null) {
            this.d = new Button(getContext());
            this.d.setGravity(17);
        }
        this.d.postInvalidate();
        return this.d;
    }

    public void a(com.chartboost.sdk.Libraries.i iVar) {
        this.c.a(iVar);
        a((String) null);
    }

    public void a(com.chartboost.sdk.Libraries.i iVar, RelativeLayout.LayoutParams layoutParams) {
        this.c.a(iVar, layoutParams);
        a((String) null);
    }

    public void a(ImageView.ScaleType scaleType) {
        this.c.setScaleType(scaleType);
    }

    public void a(boolean z) {
        this.e = z;
    }
}
