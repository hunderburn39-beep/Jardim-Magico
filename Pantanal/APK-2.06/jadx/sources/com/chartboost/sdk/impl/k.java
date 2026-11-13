package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class k extends com.chartboost.sdk.f {
    com.chartboost.sdk.Libraries.i k;
    com.chartboost.sdk.Libraries.i l;
    com.chartboost.sdk.Libraries.i m;
    com.chartboost.sdk.Libraries.i n;
    com.chartboost.sdk.Libraries.i o;
    com.chartboost.sdk.Libraries.i p;
    protected String q;
    protected float r;

    public class a extends com.chartboost.sdk.f.a {
        protected be c;
        protected bf d;
        protected bf e;
        protected ImageView f;
        private boolean h;

        protected a(Context context) {
            super(context);
            this.h = false;
            setBackgroundColor(0);
            setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            com.chartboost.sdk.g gVarA = com.chartboost.sdk.g.a();
            this.c = (be) gVarA.a(new be(context));
            addView(this.c, new RelativeLayout.LayoutParams(-1, -1));
            this.e = new bf(context) { // from class: com.chartboost.sdk.impl.k.a.1
                @Override // com.chartboost.sdk.impl.bf
                protected void a(MotionEvent motionEvent) {
                    a.this.a(motionEvent.getX(), motionEvent.getY(), a.this.e.getWidth(), a.this.e.getHeight());
                }
            };
            a(this.e);
            this.e.setContentDescription("CBAd");
            this.f = (ImageView) gVarA.a(new ImageView(context));
            this.f.setBackgroundColor(-16777216);
            addView(this.f);
            addView(this.e);
        }

        protected void d() {
            this.d = new bf(getContext()) { // from class: com.chartboost.sdk.impl.k.a.2
                @Override // com.chartboost.sdk.impl.bf
                protected void a(MotionEvent motionEvent) {
                    a.this.e();
                }
            };
            this.d.setContentDescription("CBClose");
            addView(this.d);
        }

        protected void a(float f, float f2, float f3, float f4) {
            k.this.a((String) null, com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a("click_coordinates", com.chartboost.sdk.Libraries.d.a(com.chartboost.sdk.Libraries.d.a(TMXConstants.TAG_OBJECT_ATTRIBUTE_X, Float.valueOf(f)), com.chartboost.sdk.Libraries.d.a(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, Float.valueOf(f2)), com.chartboost.sdk.Libraries.d.a("w", Float.valueOf(f3)), com.chartboost.sdk.Libraries.d.a("h", Float.valueOf(f4))))));
        }

        @Override // com.chartboost.sdk.f.a
        protected void a(int i, int i2) {
            int iRound;
            int iRound2;
            if (!this.h) {
                d();
                this.h = true;
            }
            boolean zA = k.this.a().a();
            com.chartboost.sdk.Libraries.i iVar = zA ? k.this.k : k.this.l;
            com.chartboost.sdk.Libraries.i iVar2 = zA ? k.this.m : k.this.n;
            if (!iVar.e()) {
                if (iVar == k.this.k) {
                    iVar = k.this.l;
                } else {
                    iVar = k.this.k;
                }
            }
            if (!iVar2.e()) {
                if (iVar2 == k.this.m) {
                    iVar2 = k.this.n;
                } else {
                    iVar2 = k.this.m;
                }
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            k.this.a(layoutParams, iVar, 1.0f);
            k.this.r = Math.min(Math.min(i / layoutParams.width, i2 / layoutParams.height), 1.0f);
            layoutParams.width = (int) (layoutParams.width * k.this.r);
            layoutParams.height = (int) (layoutParams.height * k.this.r);
            Point pointB = k.this.b(zA ? "frame-portrait" : "frame-landscape");
            layoutParams.leftMargin = Math.round(((i - layoutParams.width) / 2.0f) + ((pointB.x / iVar.g()) * k.this.r));
            layoutParams.topMargin = Math.round(((pointB.y / iVar.g()) * k.this.r) + ((i2 - layoutParams.height) / 2.0f));
            k.this.a(layoutParams2, iVar2, 1.0f);
            Point pointB2 = k.this.b(zA ? "close-portrait" : "close-landscape");
            if (pointB2.x == 0 && pointB2.y == 0) {
                iRound = Math.round((-layoutParams2.width) / 2.0f) + layoutParams.leftMargin + layoutParams.width;
                iRound2 = layoutParams.topMargin + Math.round((-layoutParams2.height) / 2.0f);
            } else {
                iRound = Math.round(((layoutParams.leftMargin + (layoutParams.width / 2.0f)) + pointB2.x) - (layoutParams2.width / 2.0f));
                iRound2 = Math.round((pointB2.y + (layoutParams.topMargin + (layoutParams.height / 2.0f))) - (layoutParams2.height / 2.0f));
            }
            layoutParams2.leftMargin = Math.min(Math.max(0, iRound), i - layoutParams2.width);
            layoutParams2.topMargin = Math.min(Math.max(0, iRound2), i2 - layoutParams2.height);
            this.c.setLayoutParams(layoutParams);
            this.d.setLayoutParams(layoutParams2);
            this.c.setScaleType(ImageView.ScaleType.FIT_CENTER);
            this.c.a(iVar);
            this.d.a(iVar2);
            com.chartboost.sdk.Libraries.i iVar3 = zA ? k.this.o : k.this.p;
            if (!iVar3.e()) {
                if (iVar3 == k.this.o) {
                    iVar3 = k.this.p;
                } else {
                    iVar3 = k.this.o;
                }
            }
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
            k.this.a(layoutParams3, iVar3, k.this.r);
            Point pointB3 = k.this.b(zA ? "ad-portrait" : "ad-landscape");
            layoutParams3.leftMargin = Math.round(((i - layoutParams3.width) / 2.0f) + ((pointB3.x / iVar3.g()) * k.this.r));
            layoutParams3.topMargin = Math.round(((pointB3.y / iVar3.g()) * k.this.r) + ((i2 - layoutParams3.height) / 2.0f));
            this.f.setLayoutParams(layoutParams3);
            this.e.setLayoutParams(layoutParams3);
            this.e.a(ImageView.ScaleType.FIT_CENTER);
            this.e.a(iVar3);
        }

        protected void e() {
            k.this.h();
        }

        @Override // com.chartboost.sdk.f.a
        public void b() {
            super.b();
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
        }
    }

    public k(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
        this.r = 1.0f;
        this.k = new com.chartboost.sdk.Libraries.i(this);
        this.l = new com.chartboost.sdk.Libraries.i(this);
        this.m = new com.chartboost.sdk.Libraries.i(this);
        this.n = new com.chartboost.sdk.Libraries.i(this);
        this.o = new com.chartboost.sdk.Libraries.i(this);
        this.p = new com.chartboost.sdk.Libraries.i(this);
    }

    @Override // com.chartboost.sdk.f
    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(context);
    }

    @Override // com.chartboost.sdk.f
    public boolean a(JSONObject jSONObject) {
        if (!super.a(jSONObject)) {
            return false;
        }
        this.q = jSONObject.optString("ad_id");
        if (this.e.isNull("frame-portrait") || this.e.isNull("close-portrait")) {
            this.i = false;
        }
        if (this.e.isNull("frame-landscape") || this.e.isNull("close-landscape")) {
            this.j = false;
        }
        this.l.a("frame-landscape");
        this.k.a("frame-portrait");
        this.n.a("close-landscape");
        this.m.a("close-portrait");
        if (this.e.isNull("ad-portrait")) {
            this.i = false;
        }
        if (this.e.isNull("ad-landscape")) {
            this.j = false;
        }
        this.p.a("ad-landscape");
        this.o.a("ad-portrait");
        return true;
    }

    protected Point b(String str) {
        JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(this.e, str, "offset");
        return jSONObjectA != null ? new Point(jSONObjectA.optInt(TMXConstants.TAG_OBJECT_ATTRIBUTE_X), jSONObjectA.optInt(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y)) : new Point(0, 0);
    }

    public void a(ViewGroup.LayoutParams layoutParams, com.chartboost.sdk.Libraries.i iVar, float f) {
        layoutParams.width = (int) ((iVar.b() / iVar.g()) * f);
        layoutParams.height = (int) ((iVar.c() / iVar.g()) * f);
    }

    @Override // com.chartboost.sdk.f
    public void d() {
        super.d();
        this.l.d();
        this.k.d();
        this.n.d();
        this.m.d();
        this.p.d();
        this.o.d();
        this.l = null;
        this.k = null;
        this.n = null;
        this.m = null;
        this.p = null;
        this.o = null;
    }
}
