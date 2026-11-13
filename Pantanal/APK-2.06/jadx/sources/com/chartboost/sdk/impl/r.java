package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBUtility;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapjoy.TJAdUnitConstants;
import java.util.Locale;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class r extends RelativeLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private static final CharSequence j = "00:00";
    final RelativeLayout a;
    final q b;
    final q c;
    final bf d;
    final TextView e;
    final n f;
    final bb g;
    final l h;
    final Runnable i;
    private boolean k;
    private boolean l;
    private final Handler m;
    private final Runnable n;
    private final Runnable o;

    public r(Context context, l lVar) {
        super(context);
        this.k = false;
        this.l = false;
        this.m = CBUtility.c();
        this.n = new Runnable() { // from class: com.chartboost.sdk.impl.r.2
            @Override // java.lang.Runnable
            public void run() {
                r.this.a(false);
            }
        };
        this.o = new Runnable() { // from class: com.chartboost.sdk.impl.r.3
            @Override // java.lang.Runnable
            public void run() {
                if (r.this.b != null) {
                    r.this.b.setVisibility(8);
                }
                if (r.this.h.N) {
                    r.this.f.setVisibility(8);
                }
                r.this.c.setVisibility(8);
                if (r.this.d != null) {
                    r.this.d.setEnabled(false);
                }
            }
        };
        this.i = new Runnable() { // from class: com.chartboost.sdk.impl.r.4
            private int b = 0;

            @Override // java.lang.Runnable
            public void run() {
                if (r.this.g.a().e()) {
                    int iD = r.this.g.a().d();
                    if (iD > 0) {
                        r.this.h.w = iD;
                        if (r.this.h.w / 1000.0f > BitmapDescriptorFactory.HUE_RED && !r.this.h.u()) {
                            r.this.h.s();
                            r.this.h.a(true);
                        }
                    }
                    float fC = iD / r.this.g.a().c();
                    if (r.this.h.N) {
                        r.this.f.a(fC);
                    }
                    int i = iD / 1000;
                    if (this.b != i) {
                        this.b = i;
                        r.this.e.setText(String.format(Locale.US, "%02d:%02d", Integer.valueOf(i / 60), Integer.valueOf(i % 60)));
                    }
                }
                l.a aVarE = r.this.h.e();
                if (aVarE.g()) {
                    bf bfVarD = aVarE.d(true);
                    if (bfVarD.getVisibility() == 8) {
                        r.this.h.a(true, (View) bfVarD);
                        bfVarD.setEnabled(true);
                    }
                }
                Handler handler = g.a().a;
                handler.removeCallbacks(r.this.i);
                handler.postDelayed(r.this.i, 16L);
            }
        };
        this.h = lVar;
        JSONObject jSONObjectG = lVar.g();
        float f = context.getResources().getDisplayMetrics().density;
        int iRound = Math.round(f * 10.0f);
        com.chartboost.sdk.g gVarA = com.chartboost.sdk.g.a();
        this.g = gVarA.a(context);
        lVar.e().a(this.g);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(13);
        addView(this.g, layoutParams);
        this.a = (RelativeLayout) gVarA.a(new RelativeLayout(context));
        if (jSONObjectG != null && !jSONObjectG.isNull("video-click-button")) {
            this.b = (q) gVarA.a(new q(context));
            this.b.setVisibility(8);
            this.d = new bf(context) { // from class: com.chartboost.sdk.impl.r.1
                @Override // com.chartboost.sdk.impl.bf
                protected void a(MotionEvent motionEvent) {
                    JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a(TJAdUnitConstants.String.VIDEO_PAUSED, (Object) 1));
                    com.chartboost.sdk.Libraries.d.a(jSONObjectA, "click_coordinates", com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a(TMXConstants.TAG_OBJECT_ATTRIBUTE_X, Float.valueOf(motionEvent.getX())), com.chartboost.sdk.Libraries.d.a(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, Float.valueOf(motionEvent.getY())), com.chartboost.sdk.Libraries.d.a("w", Integer.valueOf(r.this.d.getWidth())), com.chartboost.sdk.Libraries.d.a("h", Integer.valueOf(r.this.d.getHeight()))));
                    r.this.h.a((String) null, jSONObjectA);
                }
            };
            this.d.a(ImageView.ScaleType.FIT_CENTER);
            com.chartboost.sdk.Libraries.i iVar = lVar.J;
            Point pointB = lVar.b("video-click-button");
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.leftMargin = Math.round(pointB.x / iVar.g());
            layoutParams2.topMargin = Math.round(pointB.y / iVar.g());
            lVar.a(layoutParams2, iVar, 1.0f);
            this.d.a(iVar);
            this.b.addView(this.d, layoutParams2);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, Math.round(layoutParams2.height + (10.0f * f)));
            layoutParams3.addRule(10);
            this.a.addView(this.b, layoutParams3);
        } else {
            this.b = null;
            this.d = null;
        }
        this.c = (q) gVarA.a(new q(context));
        this.c.setVisibility(8);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, Math.round(32.5f * f));
        layoutParams4.addRule(12);
        this.a.addView(this.c, layoutParams4);
        this.c.setGravity(16);
        this.c.setPadding(iRound, iRound, iRound, iRound);
        this.e = (TextView) gVarA.a(new TextView(context));
        this.e.setTextColor(-1);
        this.e.setTextSize(2, 11.0f);
        this.e.setText(j);
        this.e.setPadding(0, 0, iRound, 0);
        this.e.setSingleLine();
        this.e.measure(0, 0);
        int measuredWidth = this.e.getMeasuredWidth();
        this.e.setGravity(17);
        this.c.addView(this.e, new LinearLayout.LayoutParams(measuredWidth, -1));
        this.f = gVarA.b(context);
        this.f.setVisibility(8);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, Math.round(10.0f * f));
        layoutParams5.setMargins(0, CBUtility.a(1, context), 0, 0);
        this.c.addView(this.f, layoutParams5);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams6.addRule(6, this.g.getId());
        layoutParams6.addRule(8, this.g.getId());
        layoutParams6.addRule(5, this.g.getId());
        layoutParams6.addRule(7, this.g.getId());
        addView(this.a, layoutParams6);
        a();
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.d != null) {
            this.d.setEnabled(enabled);
        }
        if (enabled) {
            b(false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.m.removeCallbacks(this.i);
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent e) {
        if (!this.g.a().e() || e.getActionMasked() != 0) {
            return false;
        }
        if (this.h == null) {
            return true;
        }
        a(true);
        return true;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer arg0) {
        this.h.w = this.g.a().c();
        if (this.h.e() != null) {
            this.h.e().f();
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        this.h.x = this.g.a().c();
        this.h.e().a(true);
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        this.h.w();
        return false;
    }

    void a(boolean z) {
        a(!this.k, z);
    }

    protected void a(boolean z, boolean z2) {
        this.m.removeCallbacks(this.n);
        this.m.removeCallbacks(this.o);
        if (this.h.z && this.h.q() && z != this.k) {
            this.k = z;
            AlphaAnimation alphaAnimation = this.k ? new AlphaAnimation(BitmapDescriptorFactory.HUE_RED, 1.0f) : new AlphaAnimation(1.0f, BitmapDescriptorFactory.HUE_RED);
            alphaAnimation.setDuration(z2 ? 100L : 200L);
            alphaAnimation.setFillAfter(true);
            if (!this.l && this.b != null) {
                this.b.setVisibility(0);
                this.b.startAnimation(alphaAnimation);
                if (this.d != null) {
                    this.d.setEnabled(true);
                }
            }
            if (this.h.N) {
                this.f.setVisibility(0);
            }
            this.c.setVisibility(0);
            this.c.startAnimation(alphaAnimation);
            if (this.k) {
                this.m.postDelayed(this.n, 3000L);
            } else {
                this.m.postDelayed(this.o, alphaAnimation.getDuration());
            }
        }
    }

    public void b(boolean z) {
        this.m.removeCallbacks(this.n);
        this.m.removeCallbacks(this.o);
        if (z) {
            if (!this.l && this.b != null) {
                this.b.setVisibility(0);
            }
            if (this.h.N) {
                this.f.setVisibility(0);
            }
            this.c.setVisibility(0);
            if (this.d != null) {
                this.d.setEnabled(true);
            }
        } else {
            if (this.b != null) {
                this.b.clearAnimation();
                this.b.setVisibility(8);
            }
            this.c.clearAnimation();
            if (this.h.N) {
                this.f.setVisibility(8);
            }
            this.c.setVisibility(8);
            if (this.d != null) {
                this.d.setEnabled(false);
            }
        }
        this.k = z;
    }

    public void c(boolean z) {
        setBackgroundColor(z ? -16777216 : 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (!z) {
            layoutParams.addRule(6, this.g.getId());
            layoutParams.addRule(8, this.g.getId());
            layoutParams.addRule(5, this.g.getId());
            layoutParams.addRule(7, this.g.getId());
        }
        this.a.setLayoutParams(layoutParams);
        if (this.b != null) {
            this.b.setGravity(8388627);
            this.b.requestLayout();
        }
    }

    public void a() {
        c(CBUtility.a().a());
    }

    public bb.a b() {
        return this.g.a();
    }

    public n c() {
        return this.f;
    }

    public void a(int i) {
        if (this.b != null) {
            this.b.setBackgroundColor(i);
        }
        this.c.setBackgroundColor(i);
    }

    public void d() {
        if (this.b != null) {
            this.b.setVisibility(8);
        }
        this.l = true;
        if (this.d != null) {
            this.d.setEnabled(false);
        }
    }

    public void d(boolean z) {
        this.e.setVisibility(z ? 0 : 8);
    }

    public void a(String str) {
        this.g.a().a((MediaPlayer.OnCompletionListener) this);
        this.g.a().a((MediaPlayer.OnErrorListener) this);
        this.g.a().a((MediaPlayer.OnPreparedListener) this);
        this.g.a().a(Uri.parse(str));
    }

    public void e() {
        this.m.postDelayed(new Runnable() { // from class: com.chartboost.sdk.impl.r.5
            @Override // java.lang.Runnable
            public void run() {
                r.this.g.setVisibility(0);
            }
        }, 500L);
        this.g.a().a();
        this.m.removeCallbacks(this.i);
        this.m.postDelayed(this.i, 16L);
    }

    public void f() {
        if (this.g.a().e()) {
            this.h.w = this.g.a().d();
            this.g.a().b();
        }
        if (this.h.e().e.getVisibility() == 0) {
            this.h.e().e.postInvalidate();
        }
        this.m.removeCallbacks(this.i);
    }

    public void g() {
        if (this.g.a().e()) {
            this.h.w = this.g.a().d();
        }
        this.g.a().b();
        this.m.removeCallbacks(this.i);
    }

    public void h() {
        this.g.setVisibility(8);
        invalidate();
    }
}
