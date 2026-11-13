package com.tapjoy.internal;

import android.app.Activity;
import android.opengl.GLSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public final class fm {
    private static Activity b;
    private static final cc c = new cc();
    private static final cc d = new cc();
    public static final be a = new be() { // from class: com.tapjoy.internal.fm.1
        @Override // com.tapjoy.internal.be
        public final boolean a(Runnable runnable) {
            GLSurfaceView gLSurfaceView = (GLSurfaceView) fm.c.a();
            if (gLSurfaceView == null) {
                return false;
            }
            gLSurfaceView.queueEvent(runnable);
            return true;
        }
    };

    static void a(GLSurfaceView gLSurfaceView) {
        new Object[1][0] = gLSurfaceView;
        c.a(gLSurfaceView);
        gLSurfaceView.queueEvent(new Runnable() { // from class: com.tapjoy.internal.fm.2
            @Override // java.lang.Runnable
            public final void run() {
                Thread threadCurrentThread = Thread.currentThread();
                new Object[1][0] = threadCurrentThread;
                fm.d.a(threadCurrentThread);
            }
        });
    }

    public static Activity a() {
        Activity activity = b;
        if (activity == null) {
            return d.a();
        }
        return activity;
    }

    public static Thread b() {
        return (Thread) d.a();
    }
}
