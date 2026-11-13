package com.fyber.b;

import android.content.Context;
import com.fyber.Fyber;
import com.fyber.ads.Ad;
import com.fyber.ads.a.c;
import com.fyber.requesters.Callback;
import com.fyber.requesters.RequestError;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.s;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: compiled from: AdFetchOperation.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class b<V extends com.fyber.ads.a.c, U extends Ad> implements Runnable {
    protected final Callback a;
    protected final s b;
    protected WeakReference<Context> c;

    protected abstract int a();

    protected abstract Future<List<V>> a(String str);

    protected abstract Future<U> a(List<V> list);

    protected abstract void a(U u);

    protected abstract void a(com.fyber.ads.a.b bVar);

    protected abstract void a(String str, com.fyber.ads.a.a aVar, String str2);

    protected abstract int b();

    protected abstract void c();

    public b(a aVar) {
        this.a = aVar.b;
        this.b = aVar.a;
    }

    public final void a(Context context) {
        this.c = new WeakReference<>(context);
        Fyber.getConfigs().a(this);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x006d A[Catch: InterruptedException -> 0x007c, all -> 0x00de, TimeoutException -> 0x00f4, ExecutionException -> 0x0143, TRY_ENTER, TRY_LEAVE, TryCatch #6 {InterruptedException -> 0x007c, ExecutionException -> 0x0143, TimeoutException -> 0x00f4, blocks: (B:5:0x0022, B:7:0x0028, B:9:0x003b, B:23:0x006d), top: B:90:0x0022, outer: #6 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0085 A[Catch: all -> 0x00de, TRY_LEAVE, TryCatch #6 {all -> 0x00de, blocks: (B:5:0x0022, B:7:0x0028, B:9:0x003b, B:23:0x006d, B:26:0x007d, B:28:0x0085, B:46:0x00d5, B:59:0x00f5), top: B:89:0x0020, inners: #6 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x00b2 A[Catch: all -> 0x0130, TRY_LEAVE, TryCatch #0 {all -> 0x0130, blocks: (B:3:0x0013, B:11:0x004b, B:13:0x0051, B:15:0x0057, B:30:0x0098, B:32:0x009e, B:34:0x00a4, B:50:0x00e1, B:52:0x00e7, B:54:0x00ed, B:55:0x00f1, B:61:0x00fc, B:63:0x0102, B:65:0x0108, B:37:0x00aa, B:39:0x00b2, B:75:0x0127, B:68:0x010f), top: B:88:0x0013, inners: #3, #7, #5 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x00d5 A[Catch: all -> 0x00de, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x00de, blocks: (B:5:0x0022, B:7:0x0028, B:9:0x003b, B:23:0x006d, B:26:0x007d, B:28:0x0085, B:46:0x00d5, B:59:0x00f5), top: B:89:0x0020, inners: #6 }] */
    /* JADX WARN: Code duplicated, block: B:75:0x0127 A[Catch: all -> 0x0130, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0130, blocks: (B:3:0x0013, B:11:0x004b, B:13:0x0051, B:15:0x0057, B:30:0x0098, B:32:0x009e, B:34:0x00a4, B:50:0x00e1, B:52:0x00e7, B:54:0x00ed, B:55:0x00f1, B:61:0x00fc, B:63:0x0102, B:65:0x0108, B:37:0x00aa, B:39:0x00b2, B:75:0x0127, B:68:0x010f), top: B:88:0x0013, inners: #3, #7, #5 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x0146  */
    /* JADX WARN: Code duplicated, block: B:9:0x003b A[Catch: InterruptedException -> 0x007c, all -> 0x00de, TimeoutException -> 0x00f4, ExecutionException -> 0x0143, TRY_LEAVE, TryCatch #6 {InterruptedException -> 0x007c, ExecutionException -> 0x0143, TimeoutException -> 0x00f4, blocks: (B:5:0x0022, B:7:0x0028, B:9:0x003b, B:23:0x006d), top: B:90:0x0022, outer: #6 }] */
    @Override // java.lang.Runnable
    public void run() {
        U u;
        Future<U> futureA = null;
        String string = UUID.randomUUID().toString();
        this.b.a(string);
        Future<List<V>> futureA2 = a(string);
        try {
            try {
                List<V> list = futureA2.get(a(), TimeUnit.SECONDS);
                try {
                    if (list != null) {
                        try {
                            if (list.isEmpty()) {
                                u = null;
                            } else {
                                futureA = a(list);
                                u = futureA.get(b(), TimeUnit.SECONDS);
                            }
                            if (u != null) {
                                a(string, com.fyber.ads.a.a.ValidationFill, (String) null);
                                a(com.fyber.ads.a.b.READY_TO_SHOW_OFFERS);
                                a(u);
                            } else {
                                a(string, com.fyber.ads.a.a.ValidationNoFill, (String) null);
                                a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
                                c();
                            }
                            if (futureA != null && !futureA.isDone() && !futureA.isCancelled()) {
                                futureA.cancel(true);
                            }
                        } catch (InterruptedException e) {
                            e = e;
                            if (e.getCause() instanceof com.fyber.exceptions.a) {
                                a(string, e.getMessage(), ((com.fyber.exceptions.a) e.getCause()).a());
                            } else {
                                a(string, e.getMessage(), (String) null);
                            }
                            if (0 != 0 && !futureA.isDone() && !futureA.isCancelled()) {
                                futureA.cancel(true);
                            }
                        } catch (ExecutionException e2) {
                            e = e2;
                            if (e.getCause() instanceof com.fyber.exceptions.a) {
                                a(string, e.getMessage(), ((com.fyber.exceptions.a) e.getCause()).a());
                            } else {
                                a(string, e.getMessage(), (String) null);
                            }
                            if (0 != 0) {
                                futureA.cancel(true);
                            }
                        } catch (TimeoutException e3) {
                            a(string, "global");
                            if (0 != 0 && !futureA.isDone() && !futureA.isCancelled()) {
                                futureA.cancel(true);
                            }
                        }
                    } else {
                        u = null;
                        if (u != null) {
                            a(string, com.fyber.ads.a.a.ValidationFill, (String) null);
                            a(com.fyber.ads.a.b.READY_TO_SHOW_OFFERS);
                            a(u);
                        } else {
                            a(string, com.fyber.ads.a.a.ValidationNoFill, (String) null);
                            a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
                            c();
                        }
                        if (futureA != null) {
                            futureA.cancel(true);
                        }
                    }
                    if (futureA2 == null || futureA2.isDone() || futureA2.isCancelled()) {
                        return;
                    }
                    futureA2.cancel(true);
                } catch (Throwable th) {
                    if (0 != 0 && !futureA.isDone() && !futureA.isCancelled()) {
                        futureA.cancel(true);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                if (futureA2 != null && !futureA2.isDone() && !futureA2.isCancelled()) {
                    futureA2.cancel(true);
                }
                throw th2;
            }
        } catch (InterruptedException e4) {
            e = e4;
            if (e.getCause() instanceof com.fyber.exceptions.a) {
                a(string, e.getMessage(), ((com.fyber.exceptions.a) e.getCause()).a());
            } else {
                a(string, e.getMessage(), (String) null);
            }
            if (futureA2 != null || futureA2.isDone() || futureA2.isCancelled()) {
                return;
            }
            futureA2.cancel(true);
        } catch (ExecutionException e5) {
            e = e5;
            if (e.getCause() instanceof com.fyber.exceptions.a) {
                a(string, e.getMessage(), ((com.fyber.exceptions.a) e.getCause()).a());
            } else {
                a(string, e.getMessage(), (String) null);
            }
            if (futureA2 != null) {
            }
        } catch (TimeoutException e6) {
            a(string, "backend");
            if (futureA2 == null || futureA2.isDone() || futureA2.isCancelled()) {
                return;
            }
            futureA2.cancel(true);
        }
    }

    private void a(String str, String str2, String str3) {
        FyberLogger.d("InterstitialFetchOperation", "An error occurred while retrieving an ad " + str2);
        a(str, com.fyber.ads.a.a.ValidationError, str3);
        this.a.onRequestError(RequestError.UNKNOWN_ERROR);
        a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
    }

    private void a(String str, String str2) {
        FyberLogger.d("InterstitialFetchOperation", "A timeout occurred while retrieving an ad");
        a(str, com.fyber.ads.a.a.ValidationTimeout, str2);
        this.a.onRequestError(RequestError.ERROR_REQUESTING_ADS);
        a(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
    }

    /* JADX INFO: compiled from: AdFetchOperation.java */
    public static abstract class a<T extends b, U> {
        protected Callback b;
        protected String c;
        protected final s a = s.a(com.fyber.utils.e.a(b()), Fyber.getConfigs().e()).b(this.c).a().b();

        protected abstract U a();

        protected abstract String b();

        public final U a(Callback callback) {
            this.b = callback;
            return a();
        }

        public final U a(Map<String, String> map) {
            this.a.a(map);
            return a();
        }

        public final U a(String str) {
            this.c = str;
            this.a.b(str);
            return a();
        }
    }
}
