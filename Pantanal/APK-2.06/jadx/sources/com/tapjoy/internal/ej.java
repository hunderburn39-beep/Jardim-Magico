package com.tapjoy.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyUtil;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public final class ej {
    private static final ej a = new ej();
    private Application b;
    private Application.ActivityLifecycleCallbacks c;

    public static void a(Context context) {
        if (Build.VERSION.SDK_INT >= 14 && context != null) {
            ej ejVar = a;
            Context applicationContext = context.getApplicationContext();
            if (ejVar.b == null) {
                try {
                    if (applicationContext instanceof Application) {
                        ejVar.b = (Application) applicationContext;
                    } else {
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        TapjoyUtil.runOnMainThread(new Runnable() { // from class: com.tapjoy.internal.ej.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    ej.this.b = ej.b();
                                } catch (Exception e) {
                                    TapjoyLog.w("Tapjoy.ActivityLifecycleTracker", Log.getStackTraceString(e));
                                } finally {
                                    countDownLatch.countDown();
                                }
                            }
                        });
                        countDownLatch.await();
                    }
                } catch (Exception e) {
                    TapjoyLog.w("Tapjoy.ActivityLifecycleTracker", Log.getStackTraceString(e));
                }
                if (ejVar.b == null) {
                    return;
                }
            }
            synchronized (ejVar) {
                if (ejVar.c == null) {
                    ejVar.c = new Application.ActivityLifecycleCallbacks() { // from class: com.tapjoy.internal.ej.2
                        private final HashSet b = new HashSet();

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivityStarted(Activity activity) {
                            this.b.add(a(activity));
                            if (this.b.size() == 1) {
                                fi.a();
                            }
                            d.a(activity);
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivityResumed(Activity activity) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivityPaused(Activity activity) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivityStopped(Activity activity) {
                            this.b.remove(a(activity));
                            if (this.b.size() <= 0) {
                                fi.b();
                            }
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public final void onActivityDestroyed(Activity activity) {
                        }

                        private static String a(Activity activity) {
                            return activity.getClass().getName() + "@" + System.identityHashCode(activity);
                        }
                    };
                    ejVar.b.registerActivityLifecycleCallbacks(ejVar.c);
                    fi.a();
                }
            }
        }
    }

    public static void a() {
        if (Build.VERSION.SDK_INT >= 14) {
            ej ejVar = a;
            if (ejVar.b != null) {
                synchronized (ejVar) {
                    if (ejVar.c != null) {
                        ejVar.b.unregisterActivityLifecycleCallbacks(ejVar.c);
                        ejVar.c = null;
                    }
                }
            }
        }
    }

    static /* synthetic */ Application b() {
        return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, null);
    }
}
