package com.adcolony.sdk;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.io.File;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class q extends ImageView {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private boolean g;
    private boolean h;
    private String i;
    private String j;
    private z k;
    private c l;

    q(Context context, z zVar, int i, c cVar) {
        super(context);
        this.a = i;
        this.k = zVar;
        this.l = cVar;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEventCompat.ACTION_MASK;
        if (action != 0 && action != 1 && action != 3 && action != 2 && action != 5 && action != 6) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        JSONObject jSONObjectA = s.a();
        s.b(jSONObjectA, "view_id", this.a);
        s.a(jSONObjectA, "ad_session_id", this.j);
        s.b(jSONObjectA, "container_x", this.b + x);
        s.b(jSONObjectA, "container_y", this.c + y);
        s.b(jSONObjectA, "view_x", x);
        s.b(jSONObjectA, "view_y", y);
        s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.l.n);
        switch (action) {
            case 0:
                new z("AdContainer.on_touch_began", this.l.l, jSONObjectA).b();
                break;
            case 1:
                if (!this.l.q) {
                    a.m.r = a.m.g.e.get(this.j);
                }
                if (x > 0 && x < this.d && y > 0 && y < this.e) {
                    new z("AdContainer.on_touch_ended", this.l.l, jSONObjectA).b();
                } else {
                    new z("AdContainer.on_touch_cancelled", this.l.l, jSONObjectA).b();
                }
                break;
            case 2:
                new z("AdContainer.on_touch_moved", this.l.l, jSONObjectA).b();
                break;
            case 3:
                new z("AdContainer.on_touch_cancelled", this.l.l, jSONObjectA).b();
                break;
            case 5:
                int action2 = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                s.b(jSONObjectA, "container_x", ((int) event.getX(action2)) + this.b);
                s.b(jSONObjectA, "container_y", ((int) event.getY(action2)) + this.c);
                s.b(jSONObjectA, "view_x", (int) event.getX(action2));
                s.b(jSONObjectA, "view_y", (int) event.getY(action2));
                new z("AdContainer.on_touch_began", this.l.l, jSONObjectA).b();
                break;
            case 6:
                int action3 = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                int x2 = (int) event.getX(action3);
                int y2 = (int) event.getY(action3);
                s.b(jSONObjectA, "container_x", ((int) event.getX(action3)) + this.b);
                s.b(jSONObjectA, "container_y", ((int) event.getY(action3)) + this.c);
                s.b(jSONObjectA, "view_x", (int) event.getX(action3));
                s.b(jSONObjectA, "view_y", (int) event.getY(action3));
                if (!this.l.q) {
                    a.m.r = a.m.g.e.get(this.j);
                }
                if (x2 > 0 && x2 < this.d && y2 > 0 && y2 < this.e) {
                    new z("AdContainer.on_touch_ended", this.l.l, jSONObjectA).b();
                } else {
                    new z("AdContainer.on_touch_cancelled", this.l.l, jSONObjectA).b();
                }
                break;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(z zVar) {
        return s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID) == this.a && s.c(zVar.b, "container_id") == this.l.n && s.b(zVar.b, "ad_session_id").equals(this.l.m);
    }

    void a() {
        this.j = s.b(this.k.b, "ad_session_id");
        this.b = s.c(this.k.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        this.c = s.c(this.k.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
        this.d = s.c(this.k.b, "width");
        this.e = s.c(this.k.b, "height");
        this.i = s.b(this.k.b, "filepath");
        this.f = s.d(this.k.b, "dpi");
        this.g = s.d(this.k.b, "invert_y");
        this.h = s.d(this.k.b, "wrap_content");
        setImageURI(Uri.fromFile(new File(this.i)));
        if (this.f) {
            float fJ = (a.m.m.j() * this.e) / getDrawable().getIntrinsicHeight();
            this.e = (int) (getDrawable().getIntrinsicHeight() * fJ);
            this.d = (int) (fJ * getDrawable().getIntrinsicWidth());
            this.b -= this.d;
            this.c = this.g ? this.c + this.e : this.c - this.e;
        }
        setVisibility(4);
        FrameLayout.LayoutParams layoutParams = this.h ? new FrameLayout.LayoutParams(-2, -2) : new FrameLayout.LayoutParams(this.d, this.e);
        layoutParams.setMargins(this.b, this.c, 0, 0);
        layoutParams.gravity = 0;
        this.l.addView(this, layoutParams);
        this.l.o.add(a.a("ImageView.set_visible", new ac() { // from class: com.adcolony.sdk.q.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (q.this.a(zVar)) {
                    q.this.d(zVar);
                }
            }
        }, true));
        this.l.o.add(a.a("ImageView.set_bounds", new ac() { // from class: com.adcolony.sdk.q.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (q.this.a(zVar)) {
                    q.this.b(zVar);
                }
            }
        }, true));
        this.l.o.add(a.a("ImageView.set_image", new ac() { // from class: com.adcolony.sdk.q.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (q.this.a(zVar)) {
                    q.this.c(zVar);
                }
            }
        }, true));
        this.l.p.add("ImageView.set_visible");
        this.l.p.add("ImageView.set_bounds");
        this.l.p.add("ImageView.set_image");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(z zVar) {
        this.b = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        this.c = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
        this.d = s.c(zVar.b, "width");
        this.e = s.c(zVar.b, "height");
        if (this.f) {
            float fJ = (a.m.m.j() * this.e) / getDrawable().getIntrinsicHeight();
            this.e = (int) (getDrawable().getIntrinsicHeight() * fJ);
            this.d = (int) (fJ * getDrawable().getIntrinsicWidth());
            this.b -= this.d;
            this.c -= this.e;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(this.b, this.c, 0, 0);
        layoutParams.width = this.d;
        layoutParams.height = this.e;
        setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(z zVar) {
        this.i = s.b(zVar.b, "filepath");
        setImageURI(Uri.fromFile(new File(this.i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(z zVar) {
        if (s.d(zVar.b, "visible")) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
    }

    int[] b() {
        return new int[]{this.b, this.c, this.d, this.e};
    }
}
