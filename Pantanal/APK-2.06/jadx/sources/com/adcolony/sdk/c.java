package com.adcolony.sdk;

import android.R;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;
import java.util.ArrayList;
import java.util.HashMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class c extends FrameLayout {
    HashMap<Integer, av> a;
    HashMap<Integer, as> b;
    HashMap<Integer, o> c;
    HashMap<Integer, aw> d;
    HashMap<Integer, g> e;
    HashMap<Integer, m> f;
    HashMap<Integer, q> g;
    HashMap<Integer, Boolean> h;
    HashMap<Integer, View> i;
    int j;
    int k;
    int l;
    String m;
    int n;
    ArrayList<ac> o;
    ArrayList<String> p;
    boolean q;
    boolean r;
    boolean s;
    Context t;
    VideoView u;

    c(Context context, String str) {
        super(context);
        this.t = context;
        this.m = str;
        setBackgroundColor(-16777216);
    }

    boolean a(z zVar) {
        return s.c(zVar.b, "container_id") == this.n && s.b(zVar.b, "ad_session_id").equals(this.m);
    }

    void b(z zVar) {
        this.a = new HashMap<>();
        this.b = new HashMap<>();
        this.c = new HashMap<>();
        this.d = new HashMap<>();
        this.e = new HashMap<>();
        this.f = new HashMap<>();
        this.g = new HashMap<>();
        this.h = new HashMap<>();
        this.i = new HashMap<>();
        this.o = new ArrayList<>();
        this.p = new ArrayList<>();
        this.n = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        this.j = s.c(zVar.b, "width");
        this.k = s.c(zVar.b, "height");
        this.l = s.c(zVar.b, "module_id");
        this.q = this.n == 1;
        if (this.j == 0 && this.k == 0) {
            this.j = a.m.m.l();
            this.k = a.m.v.getMultiWindowEnabled() ? a.m.m.m() - au.e() : a.m.m.m();
        } else {
            setLayoutParams(new FrameLayout.LayoutParams(this.j, this.k));
        }
        this.o.add(a.a("VideoView.create", new ac() { // from class: com.adcolony.sdk.c.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.g(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("VideoView.destroy", new ac() { // from class: com.adcolony.sdk.c.5
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.h(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("WebView.create", new ac() { // from class: com.adcolony.sdk.c.6
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.i(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("WebView.destroy", new ac() { // from class: com.adcolony.sdk.c.7
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.j(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("RenderView.create", new ac() { // from class: com.adcolony.sdk.c.8
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.m(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("RenderView.destroy", new ac() { // from class: com.adcolony.sdk.c.9
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.n(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("TextView.create", new ac() { // from class: com.adcolony.sdk.c.10
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.k(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("TextView.destroy", new ac() { // from class: com.adcolony.sdk.c.11
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.l(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("ImageView.create", new ac() { // from class: com.adcolony.sdk.c.12
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.e(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("ImageView.destroy", new ac() { // from class: com.adcolony.sdk.c.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.f(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("ColorView.create", new ac() { // from class: com.adcolony.sdk.c.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.c(zVar2);
                }
            }
        }, true));
        this.o.add(a.a("ColorView.destroy", new ac() { // from class: com.adcolony.sdk.c.4
            @Override // com.adcolony.sdk.ac
            public void a(z zVar2) {
                if (c.this.a(zVar2)) {
                    c.this.d(zVar2);
                }
            }
        }, true));
        this.p.add("VideoView.create");
        this.p.add("VideoView.destroy");
        this.p.add("WebView.create");
        this.p.add("WebView.destroy");
        this.p.add("RenderView.create");
        this.p.add("RenderView.destroy");
        this.p.add("TextView.create");
        this.p.add("TextView.destroy");
        this.p.add("ImageView.create");
        this.p.add("ImageView.destroy");
        this.p.add("ColorView.create");
        this.p.add("ColorView.destroy");
        this.u = new VideoView(this.t);
        this.u.setVisibility(8);
        addView(this.u);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
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
        s.b(jSONObjectA, "view_id", -1);
        s.a(jSONObjectA, "ad_session_id", this.m);
        s.b(jSONObjectA, "container_x", x);
        s.b(jSONObjectA, "container_y", y);
        s.b(jSONObjectA, "view_x", x);
        s.b(jSONObjectA, "view_y", y);
        s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.n);
        switch (action) {
            case 0:
                new z("AdContainer.on_touch_began", this.l, jSONObjectA).b();
                break;
            case 1:
                if (!this.q) {
                    a.m.r = a.m.g.e.get(this.m);
                }
                new z("AdContainer.on_touch_ended", this.l, jSONObjectA).b();
                break;
            case 2:
                new z("AdContainer.on_touch_moved", this.l, jSONObjectA).b();
                break;
            case 3:
                new z("AdContainer.on_touch_cancelled", this.l, jSONObjectA).b();
                break;
            case 5:
                int action2 = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                s.b(jSONObjectA, "container_x", (int) event.getX(action2));
                s.b(jSONObjectA, "container_y", (int) event.getY(action2));
                s.b(jSONObjectA, "view_x", (int) event.getX(action2));
                s.b(jSONObjectA, "view_y", (int) event.getY(action2));
                new z("AdContainer.on_touch_began", this.l, jSONObjectA).b();
                break;
            case 6:
                int action3 = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                s.b(jSONObjectA, "container_x", (int) event.getX(action3));
                s.b(jSONObjectA, "container_y", (int) event.getY(action3));
                s.b(jSONObjectA, "view_x", (int) event.getX(action3));
                s.b(jSONObjectA, "view_y", (int) event.getY(action3));
                s.b(jSONObjectA, TMXConstants.TAG_OBJECT_ATTRIBUTE_X, (int) event.getX(action3));
                s.b(jSONObjectA, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, (int) event.getY(action3));
                if (!this.q) {
                    a.m.r = a.m.g.e.get(this.m);
                }
                new z("AdContainer.on_touch_ended", this.l, jSONObjectA).b();
                break;
        }
        return true;
    }

    void c(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        g gVar = new g(this.t, zVar, iC, this);
        gVar.a();
        this.e.put(Integer.valueOf(iC), gVar);
        this.i.put(Integer.valueOf(iC), gVar);
    }

    boolean d(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        View viewRemove = this.i.remove(Integer.valueOf(iC));
        g gVarRemove = this.e.remove(Integer.valueOf(iC));
        if (viewRemove == null || gVarRemove == null) {
            a.m.g.a(zVar.a, "" + iC);
            return false;
        }
        removeView(gVarRemove);
        return true;
    }

    void e(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        q qVar = new q(this.t, zVar, iC, this);
        qVar.a();
        this.g.put(Integer.valueOf(iC), qVar);
        this.i.put(Integer.valueOf(iC), qVar);
    }

    boolean f(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        View viewRemove = this.i.remove(Integer.valueOf(iC));
        q qVarRemove = this.g.remove(Integer.valueOf(iC));
        if (viewRemove == null || qVarRemove == null) {
            a.m.g.a(zVar.a, "" + iC);
            return false;
        }
        removeView(qVarRemove);
        return true;
    }

    void g(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        av avVar = new av(this.t, zVar, iC, this);
        avVar.b();
        this.a.put(Integer.valueOf(iC), avVar);
        this.i.put(Integer.valueOf(iC), avVar);
    }

    boolean h(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        View viewRemove = this.i.remove(Integer.valueOf(iC));
        av avVarRemove = this.a.remove(Integer.valueOf(iC));
        if (viewRemove == null || avVarRemove == null) {
            a.m.g.a(zVar.a, "" + iC);
            return false;
        }
        if (avVarRemove.e != null) {
            avVarRemove.e.stop();
        }
        avVarRemove.a();
        removeView(avVarRemove);
        return true;
    }

    boolean i(z zVar) {
        aw awVar;
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        if (s.d(zVar.b, "is_module")) {
            awVar = a.m.A.get(Integer.valueOf(s.c(zVar.b, "module_id")));
            if (awVar == null) {
                u.g.b("Module WebView created with invalid id");
                return false;
            }
            awVar.a(zVar, iC, this);
        } else {
            Context context = this.t;
            ab abVar = a.m.d;
            int i = abVar.c;
            abVar.c = i + 1;
            awVar = new aw(context, zVar, iC, i, this);
        }
        this.d.put(Integer.valueOf(iC), awVar);
        this.i.put(Integer.valueOf(iC), awVar);
        JSONObject jSONObjectA = s.a();
        s.b(jSONObjectA, "module_id", awVar.a);
        zVar.a(jSONObjectA).b();
        return true;
    }

    boolean j(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        View viewRemove = this.i.remove(Integer.valueOf(iC));
        aw awVarRemove = this.d.remove(Integer.valueOf(iC));
        if (awVarRemove == null || viewRemove == null) {
            a.m.g.a(zVar.a, "" + iC);
            return false;
        }
        a.m.d.a(awVarRemove.a);
        removeView(awVarRemove);
        return true;
    }

    void k(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        if (s.d(zVar.b, "editable")) {
            m mVar = new m(this.t, zVar, iC, this);
            mVar.a();
            this.f.put(Integer.valueOf(iC), mVar);
            this.i.put(Integer.valueOf(iC), mVar);
            this.h.put(Integer.valueOf(iC), true);
            return;
        }
        if (!s.d(zVar.b, "button")) {
            as asVar = new as(this.t, zVar, iC, this);
            asVar.a();
            this.b.put(Integer.valueOf(iC), asVar);
            this.i.put(Integer.valueOf(iC), asVar);
            this.h.put(Integer.valueOf(iC), false);
            return;
        }
        as asVar2 = new as(this.t, R.style.Widget.DeviceDefault.Button, zVar, iC, this);
        asVar2.a();
        this.b.put(Integer.valueOf(iC), asVar2);
        this.i.put(Integer.valueOf(iC), asVar2);
        this.h.put(Integer.valueOf(iC), false);
    }

    boolean l(z zVar) {
        as asVarRemove;
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        View viewRemove = this.i.remove(Integer.valueOf(iC));
        if (this.h.remove(Integer.valueOf(this.n)).booleanValue()) {
            asVarRemove = this.f.remove(Integer.valueOf(iC));
        } else {
            asVarRemove = this.b.remove(Integer.valueOf(iC));
        }
        if (viewRemove == null || asVarRemove == null) {
            a.m.g.a(zVar.a, "" + iC);
            return false;
        }
        removeView(asVarRemove);
        return true;
    }

    void m(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        o oVar = new o(this.t, zVar, iC, this);
        oVar.a();
        this.c.put(Integer.valueOf(iC), oVar);
        this.i.put(Integer.valueOf(iC), oVar);
    }

    boolean n(z zVar) {
        int iC = s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        View viewRemove = this.i.remove(Integer.valueOf(iC));
        o oVarRemove = this.c.remove(Integer.valueOf(iC));
        if (viewRemove == null || oVarRemove == null) {
            a.m.g.a(zVar.a, "" + iC);
            return false;
        }
        oVarRemove.b();
        removeView(oVarRemove);
        return true;
    }

    void a() {
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.m);
        new z("AdSession.on_error", this.l, jSONObjectA).b();
    }
}
