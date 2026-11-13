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
    /* JADX WARN: Code duplicated, block: B:38:0x0102 A[ExcHandler: ExecutionException -> 0x0102] */
    /* JADX WARN: Code duplicated, block: B:41:0x010f A[Catch: all -> 0x0155, TryCatch #1 {all -> 0x0155, ExecutionException -> 0x0102, InterruptedException -> 0x0199, ExecutionException -> 0x0102, TimeoutException -> 0x00e7, blocks: (B:13:0x0073, B:15:0x0080, B:23:0x00b5, B:31:0x00e8, B:39:0x0103, B:41:0x010f, B:43:0x0117, B:45:0x0125, B:46:0x0129), top: B:67:0x0073 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x0117 A[Catch: all -> 0x0155, TryCatch #1 {all -> 0x0155, ExecutionException -> 0x0102, InterruptedException -> 0x0199, ExecutionException -> 0x0102, TimeoutException -> 0x00e7, blocks: (B:13:0x0073, B:15:0x0080, B:23:0x00b5, B:31:0x00e8, B:39:0x0103, B:41:0x010f, B:43:0x0117, B:45:0x0125, B:46:0x0129), top: B:67:0x0073 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x0125 A[Catch: all -> 0x0155, TryCatch #1 {all -> 0x0155, ExecutionException -> 0x0102, InterruptedException -> 0x0199, ExecutionException -> 0x0102, TimeoutException -> 0x00e7, blocks: (B:13:0x0073, B:15:0x0080, B:23:0x00b5, B:31:0x00e8, B:39:0x0103, B:41:0x010f, B:43:0x0117, B:45:0x0125, B:46:0x0129), top: B:67:0x0073 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x019c A[PHI: r4 r5
      0x019c: PHI (r4v7 java.lang.String) = (r4v6 java.lang.String), (r4v6 java.lang.String), (r4v12 java.lang.String) binds: [B:40:0x010d, B:42:0x0115, B:44:0x0123] A[DONT_GENERATE, DONT_INLINE]
      0x019c: PHI (r5v1 java.lang.String) = (r5v0 java.lang.String), (r5v3 java.lang.String), (r5v3 java.lang.String) binds: [B:40:0x010d, B:42:0x0115, B:44:0x0123] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:69:0x00f1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x00f7 A[DONT_GENERATE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:75:0x00fd A[DONT_GENERATE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x0144 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x014a A[DONT_GENERATE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0150 A[DONT_GENERATE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0014 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x0014 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x0014 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x0014 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:0x0014 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x0014 A[SYNTHETIC] */
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
                            t = futureA.get(a(), TimeUnit.SECONDS);
                        } catch (ExecutionException e) {
                            Throwable cause = e.getCause();
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
                            if (futureA != null) {
                            }
                        } catch (InterruptedException e2) {
                            a(v, com.fyber.ads.a.a.ValidationTimeout, "network");
                            if (futureA != null) {
                            }
                        } catch (TimeoutException e3) {
                            a(v, com.fyber.ads.a.a.ValidationTimeout, "network");
                            if (futureA != null) {
                            }
                        } finally {
                            if (futureA != null && !futureA.isDone() && !futureA.isCancelled()) {
                                futureA.cancel(true);
                            }
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
