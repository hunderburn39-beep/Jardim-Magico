package com.chartboost.sdk;

import android.app.Activity;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.bc;
import com.chartboost.sdk.impl.bk;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class e {
    public final bc a;
    final com.chartboost.sdk.impl.d b;
    final c c;
    final h d;
    final AtomicReference<com.chartboost.sdk.Model.c> e;
    private com.chartboost.sdk.Model.a g;
    private bk f = null;
    private int h = -1;

    public e(com.chartboost.sdk.impl.d dVar, c cVar, h hVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference) {
        this.a = (bc) g.a().a(new bc(atomicReference, this));
        this.b = dVar;
        this.c = cVar;
        this.d = hVar;
        this.e = atomicReference;
    }

    public void a(com.chartboost.sdk.Model.a aVar) {
        switch (aVar.d) {
            case LOADING:
                if (aVar.q && i.u) {
                    f(aVar);
                    break;
                }
                break;
            default:
                e(aVar);
                break;
        }
    }

    private void e(com.chartboost.sdk.Model.a aVar) {
        if (this.f != null && this.f.h() != aVar) {
            CBLogging.b("CBViewController", "Impression already visible");
            aVar.a(CBError.CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
            return;
        }
        boolean z = aVar.d != com.chartboost.sdk.Model.a.e.DISPLAYED;
        aVar.d = com.chartboost.sdk.Model.a.e.DISPLAYED;
        Activity activityH = aVar.a.f.h();
        CBError.CBImpressionError cBImpressionErrorK = activityH == null ? CBError.CBImpressionError.NO_HOST_ACTIVITY : null;
        if (cBImpressionErrorK == null) {
            cBImpressionErrorK = aVar.k();
        }
        if (cBImpressionErrorK != null) {
            CBLogging.b("CBViewController", "Unable to create the view while trying th display the impression");
            aVar.a(cBImpressionErrorK);
            return;
        }
        if (this.f == null) {
            this.f = new bk(activityH, aVar);
            activityH.addContentView(this.f, new FrameLayout.LayoutParams(-1, -1));
        }
        CBUtility.a(activityH, aVar.b, this.e.get());
        if (com.chartboost.sdk.impl.g.a().a(11) && this.h == -1 && (aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO || aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO)) {
            this.h = activityH.getWindow().getDecorView().getSystemUiVisibility();
            CBUtility.a(activityH);
        }
        this.f.a();
        CBLogging.e("CBViewController", "Displaying the impression");
        aVar.p = this.f;
        if (z) {
            if (aVar.b == com.chartboost.sdk.Model.a.b.NATIVE) {
                this.f.e().a(this.a);
            }
            bc.b bVar = bc.b.CBAnimationTypePerspectiveRotate;
            if (aVar.b == com.chartboost.sdk.Model.a.b.WEB) {
                bVar = bc.b.CBAnimationTypeFade;
            }
            if (aVar.e == com.chartboost.sdk.Model.a.EnumC0011a.MORE_APPS) {
                bVar = bc.b.CBAnimationTypePerspectiveZoom;
            }
            bc.b bVarA = bc.b.a(aVar.w().optInt("animation"));
            if (bVarA != null) {
                bVar = bVarA;
            }
            aVar.n();
            this.a.a(bVar, aVar, new bc.a() { // from class: com.chartboost.sdk.e.1
                @Override // com.chartboost.sdk.impl.bc.a
                public void a(com.chartboost.sdk.Model.a aVar2) {
                    aVar2.o();
                }
            });
            this.b.a();
            if (i.c != null) {
                if (aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO || aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
                    i.c.willDisplayVideo(aVar.f);
                }
            }
        }
    }

    public void b(final com.chartboost.sdk.Model.a aVar) {
        CBLogging.e("CBViewController", "Dismissing impression");
        final Activity activityH = this.d.h();
        Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.e.2
            @Override // java.lang.Runnable
            public void run() {
                aVar.d = com.chartboost.sdk.Model.a.e.DISMISSING;
                bc.b bVar = bc.b.CBAnimationTypePerspectiveRotate;
                if (aVar.b == com.chartboost.sdk.Model.a.b.WEB) {
                    bVar = bc.b.CBAnimationTypeFade;
                }
                if (aVar.e == com.chartboost.sdk.Model.a.EnumC0011a.MORE_APPS) {
                    bVar = bc.b.CBAnimationTypePerspectiveZoom;
                }
                bc.b bVarA = bc.b.a(aVar.w().optInt("animation"));
                if (bVarA != null) {
                    bVar = bVarA;
                }
                e.this.a.b(bVar, aVar, new bc.a() { // from class: com.chartboost.sdk.e.2.1
                    @Override // com.chartboost.sdk.impl.bc.a
                    public void a(com.chartboost.sdk.Model.a aVar2) {
                        e.this.a(aVar2, activityH);
                    }
                });
            }
        };
        if (aVar.s) {
            aVar.a(runnable);
        } else {
            runnable.run();
        }
    }

    void a(final com.chartboost.sdk.Model.a aVar, Activity activity) {
        CBUtility.c().post(new Runnable() { // from class: com.chartboost.sdk.e.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    e.this.d(aVar);
                } catch (Exception e) {
                    com.chartboost.sdk.Tracking.a.a(e.class, "animateDismissTransitionOut Runnable.run", e);
                }
            }
        });
        aVar.m();
        CBUtility.b(activity, aVar.b, this.e.get());
        if (Build.VERSION.SDK_INT < 11 || this.h == -1) {
            return;
        }
        if (aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_VIDEO || aVar.g == com.chartboost.sdk.Model.a.c.INTERSTITIAL_REWARD_VIDEO) {
            activity.getWindow().getDecorView().setSystemUiVisibility(this.h);
            this.h = -1;
        }
    }

    private void f(com.chartboost.sdk.Model.a aVar) {
        Activity activityH = this.d.h();
        if (activityH == null) {
            CBLogging.d(this, "No host activity to display loading view");
            return;
        }
        if (this.f == null) {
            this.f = new bk(activityH, aVar);
            activityH.addContentView(this.f, new FrameLayout.LayoutParams(-1, -1));
        }
        this.f.b();
        this.g = aVar;
    }

    public void a(com.chartboost.sdk.Model.a aVar, boolean z) {
        if (aVar != null) {
            if (aVar == this.g || aVar == this.c.c()) {
                this.g = null;
                CBLogging.e("CBViewController", "Dismissing loading view");
                if (b()) {
                    this.f.c();
                    if (z && this.f != null && this.f.h() != null) {
                        d(this.f.h());
                    }
                }
            }
        }
    }

    public void c(com.chartboost.sdk.Model.a aVar) {
        CBLogging.e("CBViewController", "Removing impression silently");
        if (b()) {
            a(aVar, false);
        }
        aVar.j();
        try {
            ((ViewGroup) this.f.getParent()).removeView(this.f);
        } catch (Exception e) {
            CBLogging.b("CBViewController", "Exception removing impression silently", e);
            com.chartboost.sdk.Tracking.a.a(getClass(), "removeImpressionSilently", e);
        }
        this.f = null;
    }

    public void d(com.chartboost.sdk.Model.a aVar) {
        CBLogging.e("CBViewController", "Removing impression");
        aVar.d = com.chartboost.sdk.Model.a.e.NONE;
        aVar.i();
        this.f = null;
        this.b.b();
        aVar.q().a().c(aVar);
        if (aVar.y()) {
            aVar.q().a().b(aVar);
        }
        a();
    }

    public void a() {
        CBLogging.e("CBViewController", "Attempting to close impression activity");
        Activity activityH = this.d.h();
        if (activityH != null && (activityH instanceof CBImpressionActivity)) {
            CBLogging.e("CBViewController", "Closing impression activity");
            this.d.i();
            activityH.finish();
        }
    }

    public boolean b() {
        return this.f != null && this.f.g();
    }

    public boolean c() {
        return this.c.c() != null;
    }

    public bk d() {
        return this.f;
    }
}
