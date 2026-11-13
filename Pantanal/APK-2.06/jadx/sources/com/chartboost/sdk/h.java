package com.chartboost.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.af;
import com.chartboost.sdk.impl.ai;
import com.chartboost.sdk.impl.aj;
import com.chartboost.sdk.impl.ak;
import com.chartboost.sdk.impl.al;
import com.chartboost.sdk.impl.am;
import com.chartboost.sdk.impl.y;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class h {
    private static h q;
    final c a;
    public final com.chartboost.sdk.InPlay.a b;
    final com.chartboost.sdk.impl.h c;
    final y d;
    final com.chartboost.sdk.impl.e e;
    public final ak f;
    final com.chartboost.sdk.impl.i g;
    final AtomicReference<com.chartboost.sdk.Model.c> h;
    final Application.ActivityLifecycleCallbacks n;
    Runnable o;
    protected j p;
    private final com.chartboost.sdk.impl.b r;
    private final com.chartboost.sdk.impl.d s;
    private final ai t;
    private final SharedPreferences u;
    private final com.chartboost.sdk.Tracking.a v;
    private final e w;
    CBImpressionActivity i = null;
    private com.chartboost.sdk.Model.a x = null;
    private boolean y = false;
    private final HashSet<Integer> z = new HashSet<>();
    protected boolean j = true;
    protected boolean k = false;
    boolean l = true;
    boolean m = false;
    private boolean A = false;
    private final Runnable B = new Runnable() { // from class: com.chartboost.sdk.h.4
        @Override // java.lang.Runnable
        public void run() {
            h.this.d();
        }
    };

    public static h a() {
        return q;
    }

    static void a(h hVar) {
        q = hVar;
    }

    h(Activity activity, String str, String str2, com.chartboost.sdk.impl.b bVar, com.chartboost.sdk.impl.d dVar, af afVar, com.chartboost.sdk.impl.e eVar, ai aiVar, ak akVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, SharedPreferences sharedPreferences, com.chartboost.sdk.Tracking.a aVar) {
        this.p = null;
        g gVarA = g.a();
        this.a = (c) gVarA.a(new c(akVar, this, aVar, (al) gVarA.a(new al((c.b) gVarA.a(new c.b()), aiVar))));
        this.b = (com.chartboost.sdk.InPlay.a) gVarA.a(new com.chartboost.sdk.InPlay.a(this.a, afVar, akVar, atomicReference));
        this.w = (e) gVarA.a(new e(dVar, this.a, this, atomicReference));
        am amVar = (am) gVarA.a(new am(afVar, akVar, new com.chartboost.sdk.Libraries.g(true)));
        this.c = (com.chartboost.sdk.impl.h) gVarA.a(new com.chartboost.sdk.impl.h(bVar, dVar, this.a, afVar, eVar, aiVar, akVar, this, atomicReference, sharedPreferences, aVar, this.w, amVar));
        this.d = (y) gVarA.a(new y(bVar, dVar, this.a, afVar, aiVar, akVar, this, atomicReference, sharedPreferences, aVar, this.w, amVar));
        this.g = (com.chartboost.sdk.impl.i) gVarA.a(new com.chartboost.sdk.impl.i(bVar, dVar, this.a, afVar, eVar, aiVar, akVar, this, atomicReference, sharedPreferences, aVar, this.w, amVar));
        this.r = bVar;
        this.s = dVar;
        this.e = eVar;
        this.t = aiVar;
        this.f = akVar;
        this.h = atomicReference;
        this.u = sharedPreferences;
        this.v = aVar;
        if (com.chartboost.sdk.impl.g.a().a(14)) {
            this.n = new a();
        } else {
            this.n = null;
        }
        i.m = activity.getApplicationContext();
        i.k = str;
        i.l = str2;
        this.p = j.a(activity);
        aiVar.a(i.m);
        this.o = new b();
    }

    void a(final Activity activity) {
        if (com.chartboost.sdk.b.c() && com.chartboost.sdk.b.a(activity)) {
            a(new Runnable() { // from class: com.chartboost.sdk.h.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        h.this.b(activity);
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(h.class, "onCreateCallback Runnable.run", e);
                    }
                }
            });
        }
    }

    void b(Activity activity) {
        if (this.p != null && !this.p.b(activity) && l()) {
            c(this.p);
            a(this.p, false);
        }
        com.chartboost.sdk.impl.g.a().a.removeCallbacks(this.o);
        this.p = j.a(activity);
    }

    void c(final Activity activity) {
        if (com.chartboost.sdk.b.c() && com.chartboost.sdk.b.a(activity)) {
            final com.chartboost.sdk.impl.b bVar = this.r;
            final com.chartboost.sdk.impl.d dVar = this.s;
            final e eVar = this.w;
            final com.chartboost.sdk.impl.h hVar = this.c;
            final com.chartboost.sdk.impl.i iVar = this.g;
            final y yVar = this.d;
            a(new Runnable() { // from class: com.chartboost.sdk.h.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        com.chartboost.sdk.impl.g.a().a.removeCallbacks(h.this.o);
                        if (h.this.p != null && !h.this.p.b(activity) && h.this.l()) {
                            h.this.c(h.this.p);
                            h.this.a(h.this.p, false);
                        }
                        h.this.a(activity, true);
                        h.this.p = j.a(activity);
                        h.this.b();
                        if (com.chartboost.sdk.impl.g.a().a(23)) {
                            com.chartboost.sdk.b.b(activity);
                        }
                        if (!h.this.l) {
                            if (!eVar.c()) {
                                dVar.c();
                            } else {
                                bVar.e();
                            }
                            hVar.c();
                            iVar.c();
                            hVar.g();
                            iVar.g();
                            yVar.g();
                        }
                        h.this.d(activity);
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(h.class, "onStartCallback Runnable.run", e);
                    }
                }
            });
        }
    }

    protected void d(Activity activity) {
        this.t.b(i.m);
        i.m = activity.getApplicationContext();
        if (!(activity instanceof CBImpressionActivity)) {
            this.p = j.a(activity);
            a(this.p, true);
        } else {
            a((CBImpressionActivity) activity);
        }
        com.chartboost.sdk.impl.g.a().a.removeCallbacks(this.o);
        boolean z = i.d != null && i.d.doesWrapperUseCustomBackgroundingBehavior();
        if (activity != null) {
            if (z || j(activity)) {
                b(j.a(activity), true);
                if (activity instanceof CBImpressionActivity) {
                    this.A = false;
                }
                if (this.a.a(activity, this.x)) {
                    this.x = null;
                }
                com.chartboost.sdk.Model.a aVarC = this.a.c();
                if (aVarC != null) {
                    aVarC.u();
                }
            }
        }
    }

    protected void b() {
        if (i.m == null) {
            CBLogging.b("Sdk", "The context must be set through the Chartboost method onCreate() before calling startSession().");
        } else {
            p();
        }
    }

    protected void c() {
        com.chartboost.sdk.impl.g.a().a.postDelayed(this.B, 500L);
    }

    private void p() {
        this.v.c();
        this.v.a();
        if (!this.l) {
            final com.chartboost.sdk.impl.e eVar = this.e;
            final ak akVar = this.f;
            SharedPreferences sharedPreferences = this.u;
            com.chartboost.sdk.b.a(this, new com.chartboost.sdk.b.a() { // from class: com.chartboost.sdk.h.5
                @Override // com.chartboost.sdk.b.a
                public void a() {
                    aj ajVar = new aj("api/install", akVar);
                    ajVar.a(com.chartboost.sdk.Libraries.f.a("status", com.chartboost.sdk.Libraries.a.b));
                    ajVar.j();
                    eVar.a();
                }
            }, akVar, this.h, sharedPreferences);
        }
    }

    protected void d() {
        this.v.b();
    }

    void e(final Activity activity) {
        if (com.chartboost.sdk.b.c() && com.chartboost.sdk.b.a(activity)) {
            if (!this.k) {
                if (i.c != null) {
                    i.c.didInitialize();
                }
                this.k = true;
            }
            a(new Runnable() { // from class: com.chartboost.sdk.h.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        j jVarA = j.a(activity);
                        if (h.this.e(jVarA)) {
                            h.this.a(jVarA);
                        } else if (CBUtility.a(Chartboost.CBFramework.CBFrameworkUnity)) {
                            h.this.b();
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(h.class, "onResumeCallback Runnable.run", e);
                    }
                }
            });
        }
    }

    protected void a(j jVar) {
        com.chartboost.sdk.Model.a aVarC = this.a.c();
        if (CBUtility.a(Chartboost.CBFramework.CBFrameworkUnity)) {
            b();
        }
        if (aVarC != null) {
            aVarC.t();
        }
    }

    void f(final Activity activity) {
        if (com.chartboost.sdk.b.c() && com.chartboost.sdk.b.a(activity)) {
            a(new Runnable() { // from class: com.chartboost.sdk.h.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        j jVarA = j.a(activity);
                        if (h.this.e(jVarA)) {
                            h.this.b(jVarA);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(h.class, "onPauseCallback Runnable.run", e);
                    }
                }
            });
        }
    }

    protected void b(j jVar) {
        com.chartboost.sdk.Model.a aVarC = this.a.c();
        if (aVarC != null) {
            aVarC.v();
        }
    }

    void g(final Activity activity) {
        if (com.chartboost.sdk.b.c() && com.chartboost.sdk.b.a(activity)) {
            a(new Runnable() { // from class: com.chartboost.sdk.h.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        j jVarA = j.a(activity);
                        if (h.this.e(jVarA)) {
                            h.this.c(jVarA);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(h.class, "onStopCallback", e);
                    }
                }
            });
        }
    }

    void c(j jVar) {
        if (!(jVar.get() instanceof CBImpressionActivity)) {
            a(jVar, false);
        }
        c();
    }

    protected void d(j jVar) {
        com.chartboost.sdk.Model.a aVarC = this.a.c();
        if (aVarC != null && aVarC.b == com.chartboost.sdk.Model.a.b.NATIVE) {
            e eVarM = m();
            if (f(jVar) && eVarM != null) {
                eVarM.c(aVarC);
                this.x = aVarC;
                b(jVar, false);
            }
            if (!(jVar.get() instanceof CBImpressionActivity)) {
                a(jVar, false);
            }
        }
        this.t.c(i.m);
    }

    boolean e() {
        if (!com.chartboost.sdk.b.c()) {
            return false;
        }
        if (this.p == null) {
            CBLogging.b("Sdk", "The Chartboost methods onCreate(), onStart(), onStop(), and onDestroy() must be called in the corresponding methods of your activity in order for Chartboost to function properly.");
            return false;
        }
        if (!this.A) {
            return false;
        }
        this.A = false;
        f();
        return true;
    }

    protected boolean f() {
        return g();
    }

    protected boolean g() {
        final c cVar = this.a;
        com.chartboost.sdk.Model.a aVarC = cVar.c();
        if (aVarC != null && aVarC.d == com.chartboost.sdk.Model.a.e.DISPLAYED) {
            if (aVarC.s()) {
                return true;
            }
            a(new Runnable() { // from class: com.chartboost.sdk.h.9
                @Override // java.lang.Runnable
                public void run() {
                    cVar.b();
                }
            });
            return true;
        }
        final e eVarM = m();
        if (eVarM != null && eVarM.b()) {
            a(new Runnable() { // from class: com.chartboost.sdk.h.10
                @Override // java.lang.Runnable
                public void run() {
                    eVarM.a(cVar.c(), true);
                }
            });
            return true;
        }
        return false;
    }

    void h(final Activity activity) {
        if (com.chartboost.sdk.b.c() && com.chartboost.sdk.b.a(activity)) {
            a(new Runnable() { // from class: com.chartboost.sdk.h.2
                @Override // java.lang.Runnable
                public void run() {
                    if (h.this.p == null || h.this.p.b(activity)) {
                        h.this.o = new b();
                        h.this.o.run();
                    }
                    h.this.i(activity);
                }
            });
        }
    }

    protected void i(Activity activity) {
        b(j.a(activity), false);
        com.chartboost.sdk.Model.a aVarC = this.a.c();
        if (aVarC == null && activity == this.i && this.x != null) {
            aVarC = this.x;
        }
        e eVarM = m();
        if (eVarM != null && aVarC != null) {
            eVarM.d(aVarC);
        }
        this.x = null;
    }

    protected Activity h() {
        return this.i;
    }

    private boolean j(Activity activity) {
        return this.i == activity;
    }

    private boolean f(j jVar) {
        if (jVar == null) {
            return this.i == null;
        }
        return jVar.b(this.i);
    }

    protected void a(CBImpressionActivity cBImpressionActivity) {
        if (!this.y) {
            i.m = cBImpressionActivity.getApplicationContext();
            this.i = cBImpressionActivity;
            this.y = true;
        }
        com.chartboost.sdk.impl.g.a().a.removeCallbacks(this.o);
    }

    protected void i() {
        if (this.y) {
            this.i = null;
            this.y = false;
        }
    }

    protected void a(com.chartboost.sdk.Model.a aVar) {
        boolean z = false;
        e eVarM = m();
        if (eVarM != null && eVarM.c()) {
            aVar.a(CBError.CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
            return;
        }
        if (this.y) {
            if (h() != null && eVarM != null) {
                eVarM.a(aVar);
                return;
            } else if (h() == null) {
                CBLogging.b("Sdk", "Activity not found. Cannot display the view");
                aVar.a(CBError.CBImpressionError.NO_HOST_ACTIVITY);
                return;
            } else {
                CBLogging.b("Sdk", "Missing view controller to manage the impression activity");
                aVar.a(CBError.CBImpressionError.ERROR_DISPLAYING_VIEW);
                return;
            }
        }
        if (!l()) {
            aVar.a(CBError.CBImpressionError.NO_HOST_ACTIVITY);
            return;
        }
        Activity activityJ = j();
        if (activityJ == null) {
            CBLogging.b("Sdk", "Failed to display impression as the host activity reference has been lost!");
            aVar.a(CBError.CBImpressionError.NO_HOST_ACTIVITY);
            return;
        }
        if (this.x != null && this.x != aVar) {
            aVar.a(CBError.CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
            return;
        }
        this.x = aVar;
        Intent intent = new Intent(activityJ, (Class<?>) CBImpressionActivity.class);
        boolean z2 = (activityJ.getWindow().getAttributes().flags & 1024) != 0;
        boolean z3 = (activityJ.getWindow().getAttributes().flags & 2048) != 0;
        if (z2 && !z3) {
            z = true;
        }
        intent.putExtra("paramFullscreen", z);
        intent.putExtra("isChartboost", true);
        try {
            activityJ.startActivity(intent);
            this.A = true;
        } catch (ActivityNotFoundException e) {
            CBLogging.b("Sdk", "Chartboost impression activity not declared in manifest. Please add the following inside your manifest's <application> tag: \n<activity android:name=\"com.chartboost.sdk.CBImpressionActivity\" android:theme=\"@android:style/Theme.Translucent.NoTitleBar\" android:excludeFromRecents=\"true\" />");
            this.x = null;
            CBLogging.b("Sdk", "CBImpression Activity is missing in the manifest");
            aVar.a(CBError.CBImpressionError.ACTIVITY_MISSING_IN_MANIFEST);
            this.v.a(aVar.q().e(), aVar.f, aVar.p(), CBError.CBImpressionError.ACTIVITY_MISSING_IN_MANIFEST);
        }
    }

    protected Activity j() {
        if (this.p != null) {
            return (Activity) this.p.get();
        }
        return null;
    }

    protected static void a(Runnable runnable) {
        com.chartboost.sdk.impl.g gVarA = com.chartboost.sdk.impl.g.a();
        if (!gVarA.f()) {
            gVarA.a.post(runnable);
        } else {
            runnable.run();
        }
    }

    protected Context k() {
        return this.p != null ? this.p.b() : i.m;
    }

    private static void b(j jVar, boolean z) {
    }

    private class b implements Runnable {
        private final int b;
        private final int c;
        private final int d;

        private com.chartboost.sdk.a a() {
            return i.c;
        }

        private b() {
            com.chartboost.sdk.a aVarA = a();
            this.b = h.this.i == null ? -1 : h.this.i.hashCode();
            this.c = h.this.p == null ? -1 : h.this.p.hashCode();
            this.d = aVarA != null ? aVarA.hashCode() : -1;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.chartboost.sdk.a aVarA = a();
            if (h.this.p != null && h.this.p.hashCode() == this.c) {
                h.this.p = null;
            }
            if (aVarA != null && aVarA.hashCode() == this.d) {
                i.c = null;
            }
        }
    }

    boolean l() {
        return e(this.p);
    }

    boolean e(j jVar) {
        if (jVar == null) {
            return false;
        }
        return this.z.contains(Integer.valueOf(jVar.a()));
    }

    void a(Activity activity, boolean z) {
        if (activity != null) {
            a(activity.hashCode(), z);
        }
    }

    void a(j jVar, boolean z) {
        if (jVar != null) {
            a(jVar.a(), z);
        }
    }

    private void a(int i, boolean z) {
        if (z) {
            this.z.add(Integer.valueOf(i));
        } else {
            this.z.remove(Integer.valueOf(i));
        }
    }

    protected e m() {
        if (h() == null) {
            return null;
        }
        return this.w;
    }

    static boolean n() {
        h hVarA = a();
        if (hVarA == null || !hVarA.h.get().c) {
            return true;
        }
        try {
            throw new Exception("Chartboost Integration Warning: your account has been set to advertiser only. This function has been disabled. Please contact support if you expect this call to function.");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean o() {
        return this.m;
    }

    @TargetApi(14)
    class a implements Application.ActivityLifecycleCallbacks {
        a() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            CBLogging.a("Sdk", "######## onActivityCreated callback called");
            if (!(activity instanceof CBImpressionActivity)) {
                h.this.a(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            if (!(activity instanceof CBImpressionActivity)) {
                CBLogging.a("Sdk", "######## onActivityStarted callback called from developer side");
                h.this.c(activity);
            } else {
                CBLogging.a("Sdk", "######## onActivityStarted callback called from CBImpressionactivity");
                h.this.d(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            if (!(activity instanceof CBImpressionActivity)) {
                CBLogging.a("Sdk", "######## onActivityResumed callback called from developer side");
                h.this.e(activity);
            } else {
                CBLogging.a("Sdk", "######## onActivityResumed callback called from CBImpressionactivity");
                h.this.a(j.a(activity));
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            if (!(activity instanceof CBImpressionActivity)) {
                CBLogging.a("Sdk", "######## onActivityPaused callback called from developer side");
                h.this.f(activity);
            } else {
                CBLogging.a("Sdk", "######## onActivityPaused callback called from CBImpressionactivity");
                h.this.b(j.a(activity));
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            if (!(activity instanceof CBImpressionActivity)) {
                CBLogging.a("Sdk", "######## onActivityStopped callback called from developer side");
                h.this.g(activity);
            } else {
                CBLogging.a("Sdk", "######## onActivityStopped callback called from CBImpressionactivity");
                h.this.d(j.a(activity));
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            if (!(activity instanceof CBImpressionActivity)) {
                CBLogging.a("Sdk", "######## onActivityDestroyed callback called from developer side");
                h.this.h(activity);
            } else {
                CBLogging.a("Sdk", "######## onActivityDestroyed callback called from CBImpressionactivity");
                h.this.i(activity);
            }
        }
    }
}
