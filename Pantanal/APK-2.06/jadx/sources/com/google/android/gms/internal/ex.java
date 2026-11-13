package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;

/* JADX INFO: loaded from: classes.dex */
public final class ex extends Drawable implements Drawable.Callback {
    private int CA;
    private int CB;
    private boolean CC;
    private b CD;
    private Drawable CE;
    private Drawable CF;
    private boolean CG;
    private boolean CH;
    private boolean CI;
    private int CJ;
    private boolean Cp;
    private int Cv;
    private long Cw;
    private int Cx;
    private int Cy;
    private int Cz;

    private static final class a extends Drawable {
        private static final a CK = new a();
        private static final C0062a CL = new C0062a();

        /* JADX INFO: renamed from: com.google.android.gms.internal.ex$a$a, reason: collision with other inner class name */
        private static final class C0062a extends Drawable.ConstantState {
            private C0062a() {
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return a.CK;
            }
        }

        private a() {
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return CL;
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter cf) {
        }
    }

    static final class b extends Drawable.ConstantState {
        int CM;
        int CN;

        b(b bVar) {
            if (bVar != null) {
                this.CM = bVar.CM;
                this.CN = bVar.CN;
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.CM;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ex(this);
        }
    }

    public ex(Drawable drawable, Drawable drawable2) {
        this(null);
        drawable = drawable == null ? a.CK : drawable;
        this.CE = drawable;
        drawable.setCallback(this);
        this.CD.CN |= drawable.getChangingConfigurations();
        drawable2 = drawable2 == null ? a.CK : drawable2;
        this.CF = drawable2;
        drawable2.setCallback(this);
        this.CD.CN |= drawable2.getChangingConfigurations();
    }

    ex(b bVar) {
        this.Cv = 0;
        this.Cz = MotionEventCompat.ACTION_MASK;
        this.CB = 0;
        this.Cp = true;
        this.CD = new b(bVar);
    }

    public boolean canConstantState() {
        if (!this.CG) {
            this.CH = (this.CE.getConstantState() == null || this.CF.getConstantState() == null) ? false : true;
            this.CG = true;
        }
        return this.CH;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z = true;
        boolean z2 = false;
        switch (this.Cv) {
            case 1:
                this.Cw = SystemClock.uptimeMillis();
                this.Cv = 2;
                break;
            case 2:
                if (this.Cw >= 0) {
                    float fUptimeMillis = (SystemClock.uptimeMillis() - this.Cw) / this.CA;
                    z = fUptimeMillis >= 1.0f;
                    if (z) {
                        this.Cv = 0;
                    }
                    this.CB = (int) ((Math.min(fUptimeMillis, 1.0f) * (this.Cy - this.Cx)) + this.Cx);
                    break;
                }
            default:
                z2 = z;
                break;
        }
        int i = this.CB;
        boolean z3 = this.Cp;
        Drawable drawable = this.CE;
        Drawable drawable2 = this.CF;
        if (z2) {
            if (!z3 || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.Cz) {
                drawable2.setAlpha(this.Cz);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z3) {
            drawable.setAlpha(this.Cz - i);
        }
        drawable.draw(canvas);
        if (z3) {
            drawable.setAlpha(this.Cz);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.Cz);
        }
        invalidateSelf();
    }

    public Drawable ez() {
        return this.CF;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.CD.CM | this.CD.CN;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.CD.CM = getChangingConfigurations();
        return this.CD;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return Math.max(this.CE.getIntrinsicHeight(), this.CF.getIntrinsicHeight());
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return Math.max(this.CE.getIntrinsicWidth(), this.CF.getIntrinsicWidth());
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (!this.CI) {
            this.CJ = Drawable.resolveOpacity(this.CE.getOpacity(), this.CF.getOpacity());
            this.CI = true;
        }
        return this.CJ;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable who) {
        Drawable.Callback callback;
        if (!gr.fu() || (callback = getCallback()) == null) {
            return;
        }
        callback.invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.CC && super.mutate() == this) {
            if (!canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.CE.mutate();
            this.CF.mutate();
            this.CC = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        this.CE.setBounds(bounds);
        this.CF.setBounds(bounds);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        Drawable.Callback callback;
        if (!gr.fu() || (callback = getCallback()) == null) {
            return;
        }
        callback.scheduleDrawable(this, what, when);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        if (this.CB == this.Cz) {
            this.CB = alpha;
        }
        this.Cz = alpha;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter cf) {
        this.CE.setColorFilter(cf);
        this.CF.setColorFilter(cf);
    }

    public void startTransition(int durationMillis) {
        this.Cx = 0;
        this.Cy = this.Cz;
        this.CB = 0;
        this.CA = durationMillis;
        this.Cv = 1;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        Drawable.Callback callback;
        if (!gr.fu() || (callback = getCallback()) == null) {
            return;
        }
        callback.unscheduleDrawable(this, what);
    }
}
