package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.mraid.controller.Abstract;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class l extends k {
    protected boolean A;
    protected boolean B;
    protected boolean C;
    protected boolean D;
    protected int E;
    protected com.chartboost.sdk.Libraries.i F;
    protected com.chartboost.sdk.Libraries.i G;
    protected com.chartboost.sdk.Libraries.i H;
    protected com.chartboost.sdk.Libraries.i I;
    protected com.chartboost.sdk.Libraries.i J;
    protected com.chartboost.sdk.Libraries.i K;
    protected com.chartboost.sdk.Libraries.i L;
    protected com.chartboost.sdk.Libraries.i M;
    protected boolean N;
    protected boolean O;
    protected boolean P;
    private final boolean Q;
    private boolean R;
    private boolean S;
    private boolean T;
    protected b s;
    protected int t;
    protected String u;
    protected String v;
    protected int w;
    protected int x;
    JSONObject y;
    protected boolean z;

    protected enum b {
        REWARD_OFFER,
        VIDEO_PLAYING,
        POST_VIDEO
    }

    public class a extends k.a {
        final r h;
        final m i;
        private final bf k;
        private o l;
        private View m;
        private final j n;
        private final bf o;

        private a(Context context) {
            JSONObject jSONObjectOptJSONObject;
            JSONObject jSONObjectOptJSONObject2;
            super(context);
            com.chartboost.sdk.g gVarA = com.chartboost.sdk.g.a();
            if (l.this.O) {
                this.m = new View(context);
                this.m.setBackgroundColor(-16777216);
                this.m.setVisibility(8);
                addView(this.m);
            }
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                this.l = (o) gVarA.a(new o(context, l.this));
                this.l.setVisibility(8);
                addView(this.l);
            }
            this.h = gVarA.a(context, l.this);
            this.h.setVisibility(8);
            addView(this.h);
            this.n = gVarA.b(context, l.this);
            this.n.setVisibility(8);
            addView(this.n);
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                this.i = gVarA.c(context, l.this);
                this.i.setVisibility(8);
                addView(this.i);
            } else {
                this.i = null;
            }
            this.k = new bf(getContext()) { // from class: com.chartboost.sdk.impl.l.a.1
                @Override // com.chartboost.sdk.impl.bf
                protected void a(MotionEvent motionEvent) {
                    if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                        a.this.i.a(false);
                    }
                    if (l.this.s == b.VIDEO_PLAYING) {
                        a.this.c(false);
                    }
                    a.this.b(true);
                }
            };
            this.k.setVisibility(8);
            addView(this.k);
            this.o = new bf(getContext()) { // from class: com.chartboost.sdk.impl.l.a.2
                @Override // com.chartboost.sdk.impl.bf
                protected void a(MotionEvent motionEvent) {
                    a.this.e();
                }
            };
            this.o.setVisibility(8);
            this.o.setContentDescription("CBClose");
            addView(this.o);
            JSONObject jSONObjectOptJSONObject3 = l.this.y.optJSONObject("progress");
            JSONObject jSONObjectOptJSONObject4 = l.this.y.optJSONObject("video-controls-background");
            if (jSONObjectOptJSONObject3 != null && !jSONObjectOptJSONObject3.isNull("background-color") && !jSONObjectOptJSONObject3.isNull("border-color") && !jSONObjectOptJSONObject3.isNull("progress-color") && !jSONObjectOptJSONObject3.isNull("radius")) {
                l.this.N = true;
                n nVarC = this.h.c();
                nVarC.a(com.chartboost.sdk.f.a(jSONObjectOptJSONObject3.optString("background-color")));
                nVarC.b(com.chartboost.sdk.f.a(jSONObjectOptJSONObject3.optString("border-color")));
                nVarC.c(com.chartboost.sdk.f.a(jSONObjectOptJSONObject3.optString("progress-color")));
                nVarC.b((float) jSONObjectOptJSONObject3.optDouble("radius", 0.0d));
            }
            if (jSONObjectOptJSONObject4 != null && !jSONObjectOptJSONObject4.isNull("color")) {
                this.h.a(com.chartboost.sdk.f.a(jSONObjectOptJSONObject4.optString("color")));
            }
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO && l.this.B && (jSONObjectOptJSONObject2 = l.this.y.optJSONObject("post-video-toaster")) != null) {
                this.n.a(jSONObjectOptJSONObject2.optString("title"), jSONObjectOptJSONObject2.optString("tagline"));
            }
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO && l.this.A && (jSONObjectOptJSONObject = l.this.y.optJSONObject("confirmation")) != null) {
                this.l.a(jSONObjectOptJSONObject.optString("text"), com.chartboost.sdk.f.a(jSONObjectOptJSONObject.optString("color")));
            }
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO && l.this.C) {
                JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(l.this.y, "post-video-reward-toaster");
                this.i.a((jSONObjectA == null || !jSONObjectA.optString("position").equals("inside-top")) ? p.a.BOTTOM : p.a.TOP);
                this.i.a(jSONObjectA != null ? jSONObjectA.optString("text") : "");
                if (l.this.K.e()) {
                    this.i.a(l.this.M);
                }
            }
            JSONObject jSONObjectG = l.this.g();
            if (jSONObjectG == null || jSONObjectG.isNull("video-click-button")) {
                this.h.d();
            }
            this.h.d(l.this.y.optBoolean("video-progress-timer-enabled"));
            if (l.this.P || l.this.O) {
                this.f.setVisibility(4);
            }
            String[] strArr = new String[1];
            strArr[0] = l.this.a().a() ? "video-portrait" : "video-landscape";
            JSONObject jSONObjectA2 = com.chartboost.sdk.Libraries.d.a(jSONObjectG, strArr);
            l.this.v = jSONObjectA2 != null ? jSONObjectA2.optString(TMXConstants.TAG_TILE_ATTRIBUTE_ID) : "";
            if (l.this.v.isEmpty()) {
                l.this.a(CBError.CBImpressionError.VIDEO_ID_MISSING);
                return;
            }
            if (l.this.u == null) {
                l.this.u = com.chartboost.sdk.Libraries.g.a(l.this.v);
            }
            if (l.this.u == null) {
                l.this.a(CBError.CBImpressionError.VIDEO_UNAVAILABLE);
            } else {
                this.h.a(l.this.u);
            }
        }

        @Override // com.chartboost.sdk.impl.k.a
        protected void d() {
            super.d();
            if (l.this.s == b.REWARD_OFFER && (!l.this.A || l.this.o())) {
                b(false);
            } else {
                a(l.this.s, false);
            }
        }

        public void f() {
            c(true);
            this.h.h();
            l.this.t++;
            if (l.this.t <= 1 && !l.this.v() && l.this.w >= 1) {
                l.this.f.f();
            }
        }

        @Override // com.chartboost.sdk.impl.k.a, com.chartboost.sdk.f.a
        protected void a(int i, int i2) {
            super.a(i, i2);
            a(l.this.s, false);
            boolean zA = l.this.a().a();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) this.c.getLayoutParams();
            l.this.a(layoutParams2, zA ? l.this.G : l.this.F, 1.0f);
            Point pointB = l.this.b(zA ? "replay-portrait" : "replay-landscape");
            int iRound = Math.round(((layoutParams6.leftMargin + (layoutParams6.width / 2.0f)) + pointB.x) - (layoutParams2.width / 2.0f));
            int iRound2 = Math.round((((layoutParams6.height / 2.0f) + layoutParams6.topMargin) + pointB.y) - (layoutParams2.height / 2.0f));
            layoutParams2.leftMargin = Math.min(Math.max(0, iRound), i - layoutParams2.width);
            layoutParams2.topMargin = Math.min(Math.max(0, iRound2), i2 - layoutParams2.height);
            this.k.bringToFront();
            if (zA) {
                this.k.a(l.this.G);
            } else {
                this.k.a(l.this.F);
            }
            RelativeLayout.LayoutParams layoutParams7 = (RelativeLayout.LayoutParams) this.e.getLayoutParams();
            if (!l.this.t()) {
                layoutParams3.width = layoutParams7.width;
                layoutParams3.height = layoutParams7.height;
                layoutParams3.leftMargin = layoutParams7.leftMargin;
                layoutParams3.topMargin = layoutParams7.topMargin;
                layoutParams4.width = layoutParams7.width;
                layoutParams4.height = layoutParams7.height;
                layoutParams4.leftMargin = layoutParams7.leftMargin;
                layoutParams4.topMargin = layoutParams7.topMargin;
            } else {
                RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(-2, -2);
                com.chartboost.sdk.Libraries.i iVar = zA ? l.this.m : l.this.n;
                l.this.a(layoutParams8, iVar, 1.0f);
                layoutParams8.leftMargin = 0;
                layoutParams8.topMargin = 0;
                layoutParams8.addRule(11);
                this.o.setLayoutParams(layoutParams8);
                this.o.a(iVar);
            }
            layoutParams5.width = layoutParams7.width;
            layoutParams5.height = 72;
            layoutParams5.leftMargin = layoutParams7.leftMargin;
            layoutParams5.topMargin = (layoutParams7.height + layoutParams7.topMargin) - 72;
            if (l.this.O) {
                this.m.setLayoutParams(layoutParams);
            }
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                this.l.setLayoutParams(layoutParams3);
            }
            this.h.setLayoutParams(layoutParams4);
            this.n.setLayoutParams(layoutParams5);
            this.k.setLayoutParams(layoutParams2);
            if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                this.l.a();
            }
            this.h.a();
        }

        void b(boolean z) {
            if (l.this.s != b.VIDEO_PLAYING) {
                if (l.this.A) {
                    a(b.REWARD_OFFER, z);
                    return;
                }
                a(b.VIDEO_PLAYING, z);
                JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(l.this.y, "timer");
                if (l.this.t < 1 && jSONObjectA != null && !jSONObjectA.isNull("delay")) {
                    Object[] objArr = new Object[1];
                    objArr[0] = l.this.z ? "visible" : "hidden";
                    CBLogging.c("InterstitialVideoViewProtocol", String.format("controls starting %s, setting timer", objArr));
                    this.h.b(l.this.z);
                    l.this.a(this.h, new Runnable() { // from class: com.chartboost.sdk.impl.l.a.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Object[] objArr2 = new Object[1];
                            objArr2[0] = l.this.z ? "hidden" : "shown";
                            CBLogging.c("InterstitialVideoViewProtocol", String.format("controls %s automatically from timer", objArr2));
                            a.this.h.a(!l.this.z, true);
                            synchronized (l.this.h) {
                                l.this.h.remove(a.this.h);
                            }
                        }
                    }, Math.round(jSONObjectA.optDouble("delay", 0.0d) * 1000.0d));
                } else {
                    this.h.b(!l.this.z);
                }
                this.h.e();
                if (l.this.t <= 1) {
                    l.this.f.g();
                }
            }
        }

        void c(boolean z) {
            this.h.f();
            if (l.this.s == b.VIDEO_PLAYING && z) {
                if (l.this.t < 1 && l.this.y != null && !l.this.y.isNull("post-video-reward-toaster") && l.this.C && l.this.K.e() && l.this.L.e()) {
                    e(true);
                }
                a(b.POST_VIDEO, true);
                if (CBUtility.a().a()) {
                    requestLayout();
                }
            }
        }

        private void e(boolean z) {
            if (z) {
                this.i.a(true);
            } else {
                this.i.setVisibility(0);
            }
            l.this.a.postDelayed(new Runnable() { // from class: com.chartboost.sdk.impl.l.a.4
                @Override // java.lang.Runnable
                public void run() {
                    a.this.i.a(false);
                }
            }, 2500L);
        }

        private void a(b bVar, boolean z) {
            l.this.s = bVar;
            switch (bVar) {
                case REWARD_OFFER:
                    l.this.a(!l.this.t(), this.e, z);
                    if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                        l.this.a(true, (View) this.l, z);
                    }
                    if (l.this.O) {
                        l.this.a(false, this.m, z);
                    }
                    l.this.a(false, (View) this.h, z);
                    l.this.a(false, (View) this.k, z);
                    l.this.a(false, (View) this.n, z);
                    this.e.setEnabled(false);
                    this.k.setEnabled(false);
                    this.h.setEnabled(false);
                    break;
                case VIDEO_PLAYING:
                    l.this.a(false, (View) this.e, z);
                    if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                        l.this.a(false, (View) this.l, z);
                    }
                    if (l.this.O) {
                        l.this.a(true, this.m, z);
                    }
                    l.this.a(true, (View) this.h, z);
                    l.this.a(false, (View) this.k, z);
                    l.this.a(false, (View) this.n, z);
                    this.e.setEnabled(true);
                    this.k.setEnabled(false);
                    this.h.setEnabled(true);
                    break;
                case POST_VIDEO:
                    l.this.a(true, (View) this.e, z);
                    if (l.this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                        l.this.a(false, (View) this.l, z);
                    }
                    if (l.this.O) {
                        l.this.a(false, this.m, z);
                    }
                    l.this.a(false, (View) this.h, z);
                    l.this.a(true, (View) this.k, z);
                    l.this.a(l.this.L.e() && l.this.K.e() && l.this.B, this.n, z);
                    this.k.setEnabled(true);
                    this.e.setEnabled(true);
                    this.h.setEnabled(false);
                    if (l.this.D) {
                        e(false);
                    }
                    break;
            }
            boolean zG = g();
            bf bfVarD = d(true);
            bfVarD.setEnabled(zG);
            l.this.a(zG, bfVarD, z);
            bf bfVarD2 = d(false);
            bfVarD2.setEnabled(false);
            l.this.a(false, (View) bfVarD2, z);
            if (l.this.P || l.this.O) {
                l.this.a(!l.this.t(), this.f, z);
            }
            l.this.a(!l.this.t(), this.c, z);
            a(bVar != b.REWARD_OFFER);
        }

        protected boolean g() {
            if (l.this.s == b.VIDEO_PLAYING && l.this.t < 1) {
                JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(l.this.g(), "close-" + (l.this.a().a() ? "portrait" : TJAdUnitConstants.String.LANDSCAPE));
                float fOptDouble = jSONObjectA != null ? (float) jSONObjectA.optDouble("delay", -1.0d) : -1.0f;
                int iRound = fOptDouble >= BitmapDescriptorFactory.HUE_RED ? Math.round(fOptDouble * 1000.0f) : -1;
                l.this.E = iRound;
                if (iRound < 0) {
                    return false;
                }
                if (iRound > this.h.b().d()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.chartboost.sdk.impl.k.a, com.chartboost.sdk.f.a
        public void b() {
            l.this.n();
            super.b();
        }

        @Override // com.chartboost.sdk.impl.k.a
        protected void e() {
            JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(l.this.y, "cancel-popup");
            if (l.this.s == b.VIDEO_PLAYING && jSONObjectA != null && !jSONObjectA.isNull("title") && !jSONObjectA.isNull("text") && !jSONObjectA.isNull("cancel") && !jSONObjectA.isNull("confirm")) {
                this.h.g();
                if (l.this.t < 1) {
                    l.this.p();
                    return;
                }
            }
            if (l.this.s == b.VIDEO_PLAYING) {
                c(false);
                this.h.h();
                if (l.this.t < 1) {
                    l.this.t++;
                    l.this.f.f();
                }
            }
            l.this.a.post(new Runnable() { // from class: com.chartboost.sdk.impl.l.a.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        l.this.h();
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(a.class, "onCloseButton Runnable.run", e);
                    }
                }
            });
        }

        @Override // com.chartboost.sdk.impl.k.a
        protected void a(float f, float f2, float f3, float f4) {
            if ((!l.this.z || l.this.s != b.VIDEO_PLAYING) && l.this.s != b.REWARD_OFFER) {
                b(f, f2, f3, f4);
            }
        }

        protected void b(float f, float f2, float f3, float f4) {
            if (l.this.s == b.VIDEO_PLAYING) {
                c(false);
            }
            l.this.a((String) null, com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a("click_coordinates", com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a(TMXConstants.TAG_OBJECT_ATTRIBUTE_X, Float.valueOf(f)), com.chartboost.sdk.Libraries.d.a(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, Float.valueOf(f2)), com.chartboost.sdk.Libraries.d.a("w", Float.valueOf(f3)), com.chartboost.sdk.Libraries.d.a("h", Float.valueOf(f4))))));
        }

        protected void h() {
            l.this.A = false;
            b(true);
        }

        public bf d(boolean z) {
            return (!(l.this.t() && z) && (l.this.t() || z)) ? this.d : this.o;
        }
    }

    public l(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
        this.s = b.REWARD_OFFER;
        this.Q = true;
        this.R = false;
        this.w = 0;
        this.x = 0;
        this.S = false;
        this.T = false;
        this.D = false;
        this.E = 0;
        this.N = false;
        this.O = false;
        this.P = false;
        this.s = b.REWARD_OFFER;
        this.F = new com.chartboost.sdk.Libraries.i(this);
        this.G = new com.chartboost.sdk.Libraries.i(this);
        this.H = new com.chartboost.sdk.Libraries.i(this);
        this.I = new com.chartboost.sdk.Libraries.i(this);
        this.J = new com.chartboost.sdk.Libraries.i(this);
        this.K = new com.chartboost.sdk.Libraries.i(this);
        this.L = new com.chartboost.sdk.Libraries.i(this);
        this.M = new com.chartboost.sdk.Libraries.i(this);
        this.t = 0;
    }

    public boolean o() {
        return this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO;
    }

    public void p() {
        JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(this.y, "cancel-popup");
        bg.a aVar = (bg.a) com.chartboost.sdk.g.a().a(new bg.a());
        if (jSONObjectA != null) {
            aVar.a(jSONObjectA.optString("title")).b(jSONObjectA.optString("text")).d(jSONObjectA.optString("confirm")).c(jSONObjectA.optString("cancel"));
        }
        aVar.a(e().getContext(), new bg.b() { // from class: com.chartboost.sdk.impl.l.1
            @Override // com.chartboost.sdk.impl.bg.b
            public void a(bg bgVar) {
                a aVarE = l.this.e();
                if (aVarE != null) {
                    aVarE.h.e();
                }
            }

            @Override // com.chartboost.sdk.impl.bg.b
            public void a(bg bgVar, int i) {
                a aVarE = l.this.e();
                if (i == 1) {
                    if (aVarE != null) {
                        aVarE.h.e();
                    }
                } else {
                    if (aVarE != null) {
                        aVarE.c(false);
                        aVarE.h.h();
                    }
                    l.this.h();
                }
            }
        });
    }

    @Override // com.chartboost.sdk.impl.k, com.chartboost.sdk.f
    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(context);
    }

    @Override // com.chartboost.sdk.f
    public boolean l() {
        int visibility = e().d(true).getVisibility();
        if (visibility != 4 && visibility != 8) {
            e().e();
        }
        return true;
    }

    @Override // com.chartboost.sdk.f
    public void m() {
        super.m();
        if (this.s == b.VIDEO_PLAYING && this.R) {
            e().h.b().a(this.w);
            e().h.e();
        }
        this.R = false;
    }

    @Override // com.chartboost.sdk.f
    public void n() {
        super.n();
        if (this.s == b.VIDEO_PLAYING && !this.R) {
            this.R = true;
            e().h.g();
        }
    }

    @Override // com.chartboost.sdk.impl.k, com.chartboost.sdk.f
    public boolean a(JSONObject jSONObject) {
        if (!super.a(jSONObject)) {
            return false;
        }
        this.y = jSONObject.optJSONObject("ux");
        if (this.y == null) {
            return false;
        }
        if (this.e.isNull("video-landscape") || this.e.isNull("replay-landscape")) {
            this.j = false;
        }
        this.F.a("replay-landscape");
        this.G.a("replay-portrait");
        this.J.a("video-click-button");
        this.K.a("post-video-reward-icon");
        this.L.a("post-video-button");
        this.H.a("video-confirmation-button");
        this.I.a("video-confirmation-icon");
        this.M.a("post-video-reward-icon");
        this.z = this.y.optBoolean("video-controls-togglable");
        this.O = jSONObject.optBoolean(Abstract.FULL_SCREEN);
        this.P = jSONObject.optBoolean("preroll_popup_fullscreen");
        if (this.f.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
            JSONObject jSONObjectOptJSONObject = this.y.optJSONObject("confirmation");
            JSONObject jSONObjectOptJSONObject2 = this.y.optJSONObject("post-video-toaster");
            if (jSONObjectOptJSONObject2 != null && !jSONObjectOptJSONObject2.isNull("title") && !jSONObjectOptJSONObject2.isNull("tagline")) {
                this.B = true;
            }
            if (jSONObjectOptJSONObject != null && !jSONObjectOptJSONObject.isNull("text") && !jSONObjectOptJSONObject.isNull("color")) {
                this.A = true;
            }
            if (!this.y.isNull("post-video-reward-toaster")) {
                this.C = true;
            }
        }
        return true;
    }

    @Override // com.chartboost.sdk.f
    protected void i() {
        if (this.A && (!this.H.e() || !this.I.e())) {
            this.A = false;
        }
        getClass();
        super.i();
    }

    @Override // com.chartboost.sdk.impl.k, com.chartboost.sdk.f
    public void d() {
        super.d();
        this.F.d();
        this.G.d();
        this.J.d();
        this.K.d();
        this.L.d();
        this.H.d();
        this.I.d();
        this.M.d();
        this.F = null;
        this.G = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.H = null;
        this.I = null;
        this.M = null;
    }

    public boolean q() {
        return this.s == b.VIDEO_PLAYING;
    }

    @Override // com.chartboost.sdk.f
    /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
    public a e() {
        return (a) super.e();
    }

    protected void s() {
        this.f.r();
    }

    protected boolean t() {
        if (this.s == b.POST_VIDEO) {
            return false;
        }
        boolean zA = CBUtility.a().a();
        if (this.s == b.REWARD_OFFER) {
            return this.P || zA;
        }
        if (this.s == b.VIDEO_PLAYING) {
            return this.O || zA;
        }
        return zA;
    }

    public boolean u() {
        return this.S;
    }

    public void a(boolean z) {
        this.S = z;
    }

    public boolean v() {
        return this.T;
    }

    public void w() {
        this.T = true;
        a(CBError.CBImpressionError.ERROR_PLAYING_VIDEO);
    }

    @Override // com.chartboost.sdk.f
    public float j() {
        return this.x;
    }

    @Override // com.chartboost.sdk.f
    public float k() {
        return this.w;
    }
}
