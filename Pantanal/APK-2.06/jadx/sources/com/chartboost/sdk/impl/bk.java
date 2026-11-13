package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class bk extends RelativeLayout {
    private com.chartboost.sdk.f.a a;
    private bd b;
    private bd c;
    private bj d;
    private final com.chartboost.sdk.Model.a e;

    public bk(Context context, com.chartboost.sdk.Model.a aVar) {
        super(context);
        this.e = aVar;
        if (aVar.b == com.chartboost.sdk.Model.a.b.NATIVE) {
            this.b = new bd(context);
            addView(this.b, new RelativeLayout.LayoutParams(-1, -1));
            this.c = new bd(context);
            addView(this.c, new RelativeLayout.LayoutParams(-1, -1));
            this.c.setVisibility(8);
        }
    }

    public void a() {
        if (this.a == null) {
            this.a = this.e.l();
            if (this.a != null) {
                addView(this.a, new RelativeLayout.LayoutParams(-1, -1));
                this.a.a();
            }
        }
        c();
    }

    public void b() {
        boolean z = !this.e.r;
        this.e.r = true;
        if (this.d == null) {
            this.d = new bj(getContext());
            this.d.setVisibility(8);
            addView(this.d, new RelativeLayout.LayoutParams(-1, -1));
        } else {
            if (this.c != null && this.b != null) {
                this.c.bringToFront();
                this.c.setVisibility(0);
                bc bcVar = this.e.a.j.a;
                this.c.a(bcVar);
                bcVar.a(false, this.b);
            }
            this.d.bringToFront();
            this.d.a();
        }
        if (!g()) {
            this.d.setVisibility(0);
            if (z) {
                bc bcVar2 = this.e.a.j.a;
                if (this.c != null && this.b != null) {
                    e().a(bcVar2);
                }
                bcVar2.a(true, this.d);
            }
        }
    }

    public void c() {
        if (this.d != null) {
            this.d.clearAnimation();
            this.d.setVisibility(8);
        }
    }

    public void d() {
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    public bd e() {
        return this.b;
    }

    public View f() {
        return this.a;
    }

    public boolean g() {
        return this.d != null && this.d.getVisibility() == 0;
    }

    public com.chartboost.sdk.Model.a h() {
        return this.e;
    }

    public boolean i() {
        return this.a != null && this.a.getVisibility() == 0;
    }
}
