package com.fyber.b;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.ads.Ad;
import com.fyber.ads.a.c;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.utils.FyberLogger;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: compiled from: AdsProcessorOperation.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class d<T, U extends Ad, V extends com.fyber.ads.a.c<V>> implements Callable<U> {
    protected List<V> a;
    protected WeakReference<Context> b;

    protected abstract int a();

    protected abstract U a(T t, V v);

    protected abstract Future<T> a(V v);

    protected abstract void a(com.fyber.ads.a.c cVar, com.fyber.ads.a.a aVar, String str);

    protected abstract String b();

    @NonNull
    protected abstract int c();

    protected d(List<V> list) {
        this.a = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:65:0x019c A[PHI: r4 r5
      0x019c: PHI (r4v7 java.lang.String) = (r4v6 java.lang.String), (r4v6 java.lang.String), (r4v12 java.lang.String) binds: [B:40:0x010d, B:42:0x0115, B:44:0x0123] A[DONT_GENERATE, DONT_INLINE]
      0x019c: PHI (r5v1 java.lang.String) = (r5v0 java.lang.String), (r5v3 java.lang.String), (r5v3 java.lang.String) binds: [B:40:0x010d, B:42:0x0115, B:44:0x0123] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public U call() throws Exception {
        T t;
        com.fyber.ads.a.a aVar;
        String str;
        String str2;
        if (this.a != null && !this.a.isEmpty()) {
            for (V v : this.a) {
                FyberLogger.d(b(), "Processing ad from " + v.b());
                if (com.fyber.mediation.b.a.a(v.b(), c())) {
                    FyberLogger.d(b(), v.b() + " is available, proceeding...");
                    a(v, com.fyber.ads.a.a.ValidationRequest);
                    Future<T> futureA = a(v);
                    if (futureA != null) {
                        try {
                            try {
                                t = futureA.get(a(), TimeUnit.SECONDS);
                            } catch (InterruptedException e) {
                                a(v, com.fyber.ads.a.a.ValidationTimeout, "network");
                                if (futureA == null && !futureA.isDone() && !futureA.isCancelled()) {
                                    futureA.cancel(true);
                                }
                            } catch (ExecutionException e2) {
                                Throwable cause = e2.getCause();
                                String message = "";
                                String strA = "";
                                com.fyber.ads.a.a aVar2 = com.fyber.ads.a.a.ValidationError;
                                if (cause != null) {
                                    message = cause.getMessage();
                                    if (cause instanceof com.fyber.exceptions.a) {
                                        strA = ((com.fyber.exceptions.a) cause).a();
                                        if (InterstitialMediationAdapter.ERROR_NO_PLACEMENT_ID.equals(strA)) {
                                            aVar = com.fyber.ads.a.a.NotIntegrated;
                                            str = strA;
                                            str2 = message;
                                        } else {
                                            aVar = aVar2;
                                            str = strA;
                                            str2 = message;
                                        }
                                    } else {
                                        aVar = aVar2;
                                        str = strA;
                                        str2 = message;
                                    }
                                } else {
                                    aVar = aVar2;
                                    str = strA;
                                    str2 = message;
                                }
                                FyberLogger.d(b(), "Error requesting ads - " + str2);
                                a(v, aVar, str);
                                if (futureA != null && !futureA.isDone() && !futureA.isCancelled()) {
                                    futureA.cancel(true);
                                }
                            } catch (TimeoutException e3) {
                                a(v, com.fyber.ads.a.a.ValidationTimeout, "network");
                                if (futureA == null) {
                                }
                            }
                        } catch (Throwable th) {
                            if (futureA != null && !futureA.isDone() && !futureA.isCancelled()) {
                                futureA.cancel(true);
                            }
                            throw th;
                        }
                    } else {
                        t = null;
                    }
                    if (t != null) {
                        FyberLogger.d(b(), "Ad is available from " + v.b());
                        a(v, com.fyber.ads.a.a.ValidationFill);
                        U u = (U) a(t, v);
                        if (futureA == null || futureA.isDone() || futureA.isCancelled()) {
                            return u;
                        }
                        futureA.cancel(true);
                        return u;
                    }
                    FyberLogger.d(b(), "No ad available from " + v.b());
                    a(v, com.fyber.ads.a.a.ValidationNoFill);
                    if (futureA != null && !futureA.isDone() && !futureA.isCancelled()) {
                        futureA.cancel(true);
                    }
                } else {
                    FyberLogger.d(b(), v.b() + " is not integrated");
                    a(v, com.fyber.ads.a.a.NotIntegrated);
                }
            }
        }
        FyberLogger.d(b(), "There are no ads available currently.");
        return null;
    }

    public final Future<U> a(WeakReference<Context> weakReference) {
        this.b = weakReference;
        return Fyber.getConfigs().a(this);
    }

    private void a(com.fyber.ads.a.c cVar, com.fyber.ads.a.a aVar) {
        a(cVar, aVar, "");
    }
}
