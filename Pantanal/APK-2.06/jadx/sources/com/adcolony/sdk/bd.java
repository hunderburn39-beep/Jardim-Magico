package com.adcolony.sdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Pair;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class bd implements Application.ActivityLifecycleCallbacks {
    public static final String a = "YvolvActivityLifecycle";
    List<Pair<Class<?>, Activity>> b = new LinkedList();

    bd() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        bw.b(a, "onActivityCreated", true);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        bw.b(a, "onActivityStarted " + activity, true);
        bv.aN().ad();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        bw.b(a, "onActivityResumed " + activity, true);
        a(activity);
        if (!(activity instanceof b)) {
            bv.aN().c(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        bw.b(a, "onActivityPaused " + activity, true);
        bv.aN().ac();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        bw.b(a, "onActivityStopped " + activity, true);
        if (b(activity) == 0) {
            bv.aN().ab();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        bw.b(a, "onActivitySaveInstanceState " + activity, true);
        bv.aN().d(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        bw.b(a, "onDestroy " + activity, true);
        if (b(activity) == 0) {
            bw.b(a, "destroyed " + activity, true);
            bv.aN().a((Activity) null);
            bv.aN().ae();
        }
    }

    private void a(Activity activity) {
        boolean z;
        Iterator<Pair<Class<?>, Activity>> it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (((Activity) it.next().second).equals(activity)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.b.add(new Pair<>(activity.getClass(), activity));
        }
    }

    private int b(Activity activity) {
        int i;
        Class cls;
        int i2 = 0;
        Iterator<Pair<Class<?>, Activity>> it = this.b.iterator();
        while (true) {
            i = i2;
            if (!it.hasNext()) {
                cls = null;
                i = -1;
                break;
            }
            Pair<Class<?>, Activity> next = it.next();
            if (((Activity) next.second).equals(activity)) {
                cls = (Class) next.first;
                break;
            }
            i2 = i + 1;
        }
        if (i >= 0 && cls != null) {
            bw.b(a, "Removing " + cls + " from stack", true);
            this.b.remove(i);
        }
        bw.b(a, "stack size=" + this.b.size(), true);
        return this.b.size();
    }

    int a() {
        return this.b.size();
    }
}
