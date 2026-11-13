package com.tapjoy.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapjoy.TJContentActivity;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class fq extends gb {
    public static fq a;
    final fu b;
    final String c;
    final gn d;
    private boolean e;
    private boolean f;
    private long g;
    private Context l;
    private hf m;
    private Activity n;
    private fv o;
    private Handler p;
    private Runnable q;

    public fq(fu fuVar, String str, gn gnVar, Context context) {
        this.b = fuVar;
        this.c = str;
        this.d = gnVar;
        this.l = context;
    }

    @Override // com.tapjoy.internal.gb
    public final void a() throws IOException {
        Iterator it = this.d.a.iterator();
        while (it.hasNext()) {
            for (gv gvVar : ((gw) it.next()).c) {
                if (gvVar.l != null) {
                    gvVar.l.a();
                }
                if (gvVar.m != null) {
                    gvVar.m.a();
                }
            }
        }
    }

    @Override // com.tapjoy.internal.gb
    public final boolean b() {
        Iterator it = this.d.a.iterator();
        boolean z = true;
        while (it.hasNext()) {
            Iterator it2 = ((gw) it.next()).c.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = true;
                    break;
                }
                gv gvVar = (gv) it2.next();
                if ((gvVar.l != null && gvVar.l.b == null) || (gvVar.m != null && gvVar.m.b == null)) {
                    z = false;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return z;
    }

    @Override // com.tapjoy.internal.gb
    public final void a(fv fvVar, eq eqVar) {
        this.o = fvVar;
        this.n = c.a(this.l);
        if (this.n != null && !this.n.isFinishing()) {
            try {
                a(this.n, fvVar, eqVar);
                new Object[1][0] = this.c;
                return;
            } catch (WindowManager.BadTokenException e) {
            }
        }
        this.n = fm.a();
        if (this.n != null && !this.n.isFinishing()) {
            try {
                a(this.n, fvVar, eqVar);
                new Object[1][0] = this.c;
                return;
            } catch (WindowManager.BadTokenException e2) {
            }
        }
        fr.b("Failed to show the content for \"{}\" caused by no registration of TJContentActivity", this.c);
        fvVar.a(this.c, this.k, null);
    }

    private void a(final Activity activity, final fv fvVar, eq eqVar) {
        cq.a(!this.e);
        this.e = true;
        this.f = true;
        a = this;
        this.m = new hf(activity, this.d, new hf.a() { // from class: com.tapjoy.internal.fq.2
            @Override // com.tapjoy.internal.hf.a
            public final void a(gv gvVar) {
                fq.this.b.a(fq.this.d.b, gvVar.k);
                if (!cr.c(gvVar.h)) {
                    fq.this.j.a(activity, gvVar.h, cr.b(gvVar.i));
                    fq.this.i = true;
                } else if (!cr.c(gvVar.g)) {
                    gb.a(activity, gvVar.g);
                }
                fvVar.a(fq.this.c, null);
                if (gvVar.j) {
                    fq.a(fq.this);
                }
            }

            @Override // com.tapjoy.internal.hf.a
            public final void a() {
                fq.a(fq.this);
            }
        });
        Window window = activity.getWindow();
        hf hfVar = this.m;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1, 17);
        Window.Callback callback = window.getCallback();
        window.setCallback(null);
        window.addContentView(hfVar, layoutParams);
        window.setCallback(callback);
        this.g = SystemClock.elapsedRealtime();
        this.b.a(this.d.b);
        eqVar.a();
        fvVar.c(this.c);
        if (this.d.c > BitmapDescriptorFactory.HUE_RED) {
            this.p = new Handler(Looper.getMainLooper());
            this.q = new Runnable() { // from class: com.tapjoy.internal.fq.3
                @Override // java.lang.Runnable
                public final void run() {
                    fq.a(fq.this);
                }
            };
            this.p.postDelayed(this.q, (long) (this.d.c * 1000.0f));
        }
    }

    static /* synthetic */ void a(fq fqVar) {
        if (!fqVar.f) {
            return;
        }
        fqVar.f = false;
        if (fqVar.p != null) {
            fqVar.p.removeCallbacks(fqVar.q);
            fqVar.q = null;
            fqVar.p = null;
        }
        if (a == fqVar) {
            a = null;
        }
        fqVar.b.a(fqVar.d.b, SystemClock.elapsedRealtime() - fqVar.g);
        if (!fqVar.i && fqVar.o != null) {
            fqVar.o.a(fqVar.c, fqVar.k, null);
            fqVar.o = null;
        }
        ViewGroup viewGroup = (ViewGroup) fqVar.m.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(fqVar.m);
        }
        fqVar.m = null;
        if (fqVar.n instanceof TJContentActivity) {
            fqVar.n.finish();
        }
        fqVar.n = null;
    }
}
