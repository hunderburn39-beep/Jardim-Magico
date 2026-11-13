package com.tapjoy.internal;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tapjoy.TJConnectListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public abstract class ev {
    b c;
    a e;
    private a h;
    private final ReentrantLock a = new ReentrantLock();
    private final Condition f = this.a.newCondition();
    volatile int b = c.a;
    private final LinkedList g = new LinkedList();
    long d = 1000;

    public abstract boolean a(Context context, String str, Hashtable hashtable, TJConnectListener tJConnectListener);

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static final class c {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        public static final int d = 4;
        public static final int e = 5;
        private static final /* synthetic */ int[] f = {a, b, c, d, e};

        public static int[] a() {
            return (int[]) f.clone();
        }
    }

    public final boolean b(Context context, String str, Hashtable hashtable, TJConnectListener tJConnectListener) {
        this.a.lock();
        if (tJConnectListener != null) {
            try {
                this.g.addLast(ek.a(tJConnectListener, TJConnectListener.class));
            } finally {
                this.a.unlock();
            }
        }
        a aVar = new a(context, str, hashtable);
        switch (AnonymousClass3.a[this.b - 1]) {
            case 1:
                a(true);
                return true;
            case 2:
                this.e = aVar;
                eo.b.addObserver(new Observer() { // from class: com.tapjoy.internal.ev.1
                    @Override // java.util.Observer
                    public final void update(Observable observable, Object data) {
                        eo.b.deleteObserver(this);
                        if (!Boolean.valueOf(Boolean.TRUE.equals(data)).booleanValue() && ev.this.e != null && ev.this.e.a != null) {
                            ev.this.c = new b(ev.this, (byte) 0);
                            ev.this.c.e();
                        }
                    }
                });
                if (!a(aVar.a, aVar.b, aVar.c, new TJConnectListener() { // from class: com.tapjoy.internal.ev.2
                    @Override // com.tapjoy.TJConnectListener
                    public final void onConnectSuccess() {
                        ev evVar = ev.this;
                        int i = c.e;
                        int i2 = c.b;
                        evVar.a(i);
                        ev.this.a(true);
                    }

                    @Override // com.tapjoy.TJConnectListener
                    public final void onConnectFailure() {
                        ev.this.a(false);
                    }
                })) {
                    this.g.clear();
                    return false;
                }
                int i = c.b;
                int i2 = c.a;
                a(i);
                return true;
            case 3:
            case 4:
                this.h = aVar;
                return true;
            case 5:
                this.h = aVar;
                b();
                return true;
            default:
                a(c.a);
                return false;
        }
    }

    /* JADX INFO: renamed from: com.tapjoy.internal.ev$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[c.a().length];

        static {
            try {
                a[c.e - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[c.a - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[c.b - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[c.c - 1] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[c.d - 1] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    final void a(int i) {
        this.a.lock();
        try {
            int i2 = this.b;
            this.b = i;
        } finally {
            this.a.unlock();
        }
    }

    final a a() {
        this.a.lock();
        try {
            if (this.h != null) {
                this.e = this.h;
                this.h = null;
            }
            return this.e;
        } finally {
            this.a.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(boolean z) {
        this.a.lock();
        try {
            if (this.g.size() != 0) {
                ArrayList<TJConnectListener> arrayList = new ArrayList(this.g);
                this.g.clear();
                this.a.unlock();
                for (TJConnectListener tJConnectListener : arrayList) {
                    if (z) {
                        tJConnectListener.onConnectSuccess();
                    } else {
                        tJConnectListener.onConnectFailure();
                    }
                }
                return;
            }
            this.a.unlock();
        } catch (Throwable th) {
            this.a.unlock();
            throw th;
        }
    }

    final void b() {
        this.a.lock();
        try {
            this.d = 1000L;
            this.f.signal();
        } finally {
            this.a.unlock();
        }
    }

    final boolean a(long j) {
        this.a.lock();
        try {
            int i = c.d;
            int i2 = c.c;
            a(i);
            if (this.f.await(j, TimeUnit.MILLISECONDS)) {
                this.d = 1000L;
            }
        } catch (InterruptedException e) {
        } finally {
            int i3 = c.c;
            int i4 = c.d;
            a(i3);
            this.a.unlock();
        }
        return false;
    }

    class a {
        public final Context a;
        public final String b;
        public final Hashtable c;

        public a(Context context, String str, Hashtable hashtable) {
            Context applicationContext = null;
            if (context != null) {
                applicationContext = context instanceof Application ? context : context.getApplicationContext();
            }
            this.a = applicationContext != null ? applicationContext : context;
            this.b = str;
            this.c = hashtable;
        }
    }

    class b extends db {
        private boolean b;
        private boolean c;
        private Context d;
        private BroadcastReceiver e;

        private b() {
            this.e = new BroadcastReceiver() { // from class: com.tapjoy.internal.ev.b.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ev.this.b();
                }
            };
        }

        /* synthetic */ b(ev evVar, byte b) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tapjoy.internal.db
        public final void d() {
            this.b = true;
            ev.this.b();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tapjoy.internal.db
        public final void a() {
            ev evVar = ev.this;
            int i = c.c;
            int i2 = c.b;
            evVar.a(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tapjoy.internal.db
        public final void c() {
            if (ev.this.c == this) {
                ev.this.c = null;
            }
            if (ev.this.b == c.c) {
                ev evVar = ev.this;
                int i = c.a;
                int i2 = c.c;
                evVar.a(i);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tapjoy.internal.db
        public final void b() {
            this.d = ev.this.a().a;
            this.d.registerReceiver(this.e, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            while (!this.b) {
                try {
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    eo.b.addObserver(new Observer() { // from class: com.tapjoy.internal.ev.b.1
                        @Override // java.util.Observer
                        public final void update(Observable observable, Object data) {
                            eo.b.deleteObserver(this);
                            b.this.c = Boolean.TRUE.equals(data);
                            countDownLatch.countDown();
                        }
                    });
                    a aVarA = ev.this.a();
                    if (!ev.this.a(aVarA.a, aVarA.b, aVarA.c, null)) {
                        ev.this.a(false);
                        h();
                        return;
                    }
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                    }
                    if (!this.c) {
                        ev.this.a(false);
                        long jMax = Math.max(ev.this.d, 1000L);
                        ev.this.d = Math.min(jMax << 2, 3600000L);
                        ev.this.a(jMax);
                    } else {
                        ev evVar = ev.this;
                        int i = c.e;
                        int i2 = c.c;
                        evVar.a(i);
                        ev.this.a(true);
                        h();
                        return;
                    }
                } catch (Throwable th) {
                    h();
                    throw th;
                }
            }
            h();
        }

        private void h() {
            this.d.unregisterReceiver(this.e);
        }
    }
}
