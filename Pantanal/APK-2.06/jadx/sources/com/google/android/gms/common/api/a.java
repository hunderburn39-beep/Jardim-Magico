package com.google.android.gms.common.api;

import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: com.google.android.gms.common.api.a$a, reason: collision with other inner class name */
    public static abstract class AbstractC0032a<R extends Result> implements PendingResult<R>, d<R> {
        private c<R> AC;
        private ResultCallback<R> AF;
        private volatile R AG;
        private volatile boolean AH;
        private boolean AI;
        private boolean AJ;
        private fk AK;
        private final Object AB = new Object();
        private final CountDownLatch AD = new CountDownLatch(1);
        private final ArrayList<PendingResult.a> AE = new ArrayList<>();

        AbstractC0032a() {
        }

        public AbstractC0032a(Looper looper) {
            this.AC = new c<>(looper);
        }

        public AbstractC0032a(c<R> cVar) {
            this.AC = cVar;
        }

        private void b(R r) {
            this.AG = r;
            this.AK = null;
            this.AD.countDown();
            Status status = this.AG.getStatus();
            if (this.AF != null) {
                this.AC.eg();
                if (!this.AI) {
                    this.AC.a(this.AF, eb());
                }
            }
            Iterator<PendingResult.a> it = this.AE.iterator();
            while (it.hasNext()) {
                it.next().l(status);
            }
            this.AE.clear();
        }

        private void c(Result result) {
            if (result instanceof Releasable) {
                try {
                    ((Releasable) result).release();
                } catch (Exception e) {
                    Log.w("AbstractPendingResult", "Unable to release " + this, e);
                }
            }
        }

        private R eb() {
            R r;
            synchronized (this.AB) {
                fq.a(!this.AH, "Result has already been consumed.");
                fq.a(isReady(), "Result is not ready.");
                r = this.AG;
                ec();
            }
            return r;
        }

        private void ed() {
            synchronized (this.AB) {
                if (!isReady()) {
                    b(d(Status.Bw));
                    this.AJ = true;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void ee() {
            synchronized (this.AB) {
                if (!isReady()) {
                    b(d(Status.By));
                    this.AJ = true;
                }
            }
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public final void a(PendingResult.a aVar) {
            fq.a(!this.AH, "Result has already been consumed.");
            synchronized (this.AB) {
                if (isReady()) {
                    aVar.l(this.AG.getStatus());
                } else {
                    this.AE.add(aVar);
                }
            }
        }

        @Override // com.google.android.gms.common.api.a.d
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final void b(R r) {
            synchronized (this.AB) {
                if (this.AJ || this.AI) {
                    c(r);
                    return;
                }
                fq.a(!isReady(), "Results have already been set");
                fq.a(this.AH ? false : true, "Result has already been consumed");
                b((Result) r);
            }
        }

        protected void a(c<R> cVar) {
            this.AC = cVar;
        }

        protected final void a(fk fkVar) {
            synchronized (this.AB) {
                this.AK = fkVar;
            }
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public final R await() {
            fq.a(!this.AH, "Result has already been consumed");
            fq.a(isReady() || Looper.myLooper() != Looper.getMainLooper(), "await must not be called on the UI thread");
            try {
                this.AD.await();
            } catch (InterruptedException e) {
                ed();
            }
            fq.a(isReady(), "Result is not ready.");
            return (R) eb();
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public final R await(long j, TimeUnit timeUnit) {
            fq.a(!this.AH, "Result has already been consumed.");
            fq.a(isReady() || Looper.myLooper() != Looper.getMainLooper(), "await must not be called on the UI thread");
            try {
                if (!this.AD.await(j, timeUnit)) {
                    ee();
                }
            } catch (InterruptedException e) {
                ed();
            }
            fq.a(isReady(), "Result is not ready.");
            return (R) eb();
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public void cancel() {
            synchronized (this.AB) {
                if (this.AI) {
                    return;
                }
                if (this.AK != null) {
                    try {
                        this.AK.cancel();
                    } catch (RemoteException e) {
                    }
                }
                c(this.AG);
                this.AF = null;
                this.AI = true;
                b(d(Status.Bz));
            }
        }

        protected abstract R d(Status status);

        protected void ec() {
            this.AH = true;
            this.AG = null;
            this.AF = null;
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public boolean isCanceled() {
            boolean z;
            synchronized (this.AB) {
                z = this.AI;
            }
            return z;
        }

        public final boolean isReady() {
            return this.AD.getCount() == 0;
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public final void setResultCallback(ResultCallback<R> callback) {
            fq.a(!this.AH, "Result has already been consumed.");
            synchronized (this.AB) {
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.AC.a(callback, eb());
                } else {
                    this.AF = callback;
                }
            }
        }

        @Override // com.google.android.gms.common.api.PendingResult
        public final void setResultCallback(ResultCallback<R> callback, long time, TimeUnit units) {
            fq.a(!this.AH, "Result has already been consumed.");
            synchronized (this.AB) {
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.AC.a(callback, eb());
                } else {
                    this.AF = callback;
                    this.AC.a(this, units.toMillis(time));
                }
            }
        }
    }

    public static abstract class b<R extends Result, A extends Api.a> extends AbstractC0032a<R> implements com.google.android.gms.common.api.b.c<A> {
        private com.google.android.gms.common.api.b.a AL;
        private final Api.c<A> Az;

        protected b(Api.c<A> cVar) {
            this.Az = (Api.c) fq.f(cVar);
        }

        private void a(RemoteException remoteException) {
            k(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        protected abstract void a(A a) throws RemoteException;

        @Override // com.google.android.gms.common.api.b.c
        public void a(com.google.android.gms.common.api.b.a aVar) {
            this.AL = aVar;
        }

        @Override // com.google.android.gms.common.api.b.c
        public final void b(A a) throws DeadObjectException {
            a(new c<>(a.getLooper()));
            try {
                a(a);
            } catch (DeadObjectException e) {
                a(e);
                throw e;
            } catch (RemoteException e2) {
                a(e2);
            }
        }

        @Override // com.google.android.gms.common.api.b.c
        public final Api.c<A> ea() {
            return this.Az;
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        protected void ec() {
            super.ec();
            if (this.AL != null) {
                this.AL.b(this);
                this.AL = null;
            }
        }

        @Override // com.google.android.gms.common.api.b.c
        public int ef() {
            return 0;
        }

        @Override // com.google.android.gms.common.api.b.c
        public final void k(Status status) {
            fq.b(!status.isSuccess(), "Failed result must not be success");
            b(d(status));
        }
    }

    public static class c<R extends Result> extends Handler {
        public c() {
            this(Looper.getMainLooper());
        }

        public c(Looper looper) {
            super(looper);
        }

        public void a(ResultCallback<R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        public void a(AbstractC0032a<R> abstractC0032a, long j) {
            sendMessageDelayed(obtainMessage(2, abstractC0032a), j);
        }

        protected void b(ResultCallback<R> resultCallback, R r) {
            resultCallback.onResult(r);
        }

        public void eg() {
            removeMessages(2);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Pair pair = (Pair) msg.obj;
                    b((ResultCallback) pair.first, (Result) pair.second);
                    break;
                case 2:
                    ((AbstractC0032a) msg.obj).ee();
                    break;
                default:
                    Log.wtf("GoogleApi", "Don't know how to handle this message.");
                    break;
            }
        }
    }

    public interface d<R> {
        void b(R r);
    }
}
