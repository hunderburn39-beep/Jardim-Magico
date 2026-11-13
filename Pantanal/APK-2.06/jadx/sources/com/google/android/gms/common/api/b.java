package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fg;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
final class b implements GoogleApiClient {
    private final Looper AS;
    private final fg Bc;
    private ConnectionResult Be;
    private int Bf;
    private int Bj;
    final Handler Bl;
    private boolean Bo;
    private final Lock Ba = new ReentrantLock();
    private final Condition Bb = this.Ba.newCondition();
    final Queue<c<?>> Bd = new LinkedList();
    private int Bg = 4;
    private int Bh = 0;
    private boolean Bi = false;
    private long Bk = 5000;
    private final Bundle Bm = new Bundle();
    private final Map<Api.c<?>, Api.a> Bn = new HashMap();
    final Set<c<?>> Bp = new HashSet();
    private final a AL = new a() { // from class: com.google.android.gms.common.api.b.1
        @Override // com.google.android.gms.common.api.b.a
        public void b(c<?> cVar) {
            b.this.Ba.lock();
            try {
                b.this.Bp.remove(cVar);
            } finally {
                b.this.Ba.unlock();
            }
        }
    };
    final GoogleApiClient.ConnectionCallbacks Bq = new GoogleApiClient.ConnectionCallbacks() { // from class: com.google.android.gms.common.api.b.2
        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnected(Bundle connectionHint) {
            b.this.Ba.lock();
            try {
                if (b.this.Bg == 1) {
                    if (connectionHint != null) {
                        b.this.Bm.putAll(connectionHint);
                    }
                    b.this.ei();
                }
            } finally {
                b.this.Ba.unlock();
            }
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnectionSuspended(int cause) {
            b.this.Ba.lock();
            try {
                b.this.E(cause);
                switch (cause) {
                    case 1:
                        if (b.this.ek()) {
                            return;
                        }
                        b.this.Bh = 2;
                        b.this.Bl.sendMessageDelayed(b.this.Bl.obtainMessage(1), b.this.Bk);
                        break;
                    case 2:
                        b.this.connect();
                        break;
                }
            } finally {
                b.this.Ba.unlock();
            }
        }
    };
    private final fg.b Br = new fg.b() { // from class: com.google.android.gms.common.api.b.3
        @Override // com.google.android.gms.internal.fg.b
        public Bundle dG() {
            return null;
        }

        @Override // com.google.android.gms.internal.fg.b
        public boolean em() {
            return b.this.Bo;
        }

        @Override // com.google.android.gms.internal.fg.b
        public boolean isConnected() {
            return b.this.isConnected();
        }
    };

    interface a {
        void b(c<?> cVar);
    }

    /* JADX INFO: renamed from: com.google.android.gms.common.api.b$b, reason: collision with other inner class name */
    class HandlerC0033b extends Handler {
        HandlerC0033b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what != 1) {
                Log.wtf("GoogleApiClientImpl", "Don't know how to handle this message.");
                return;
            }
            b.this.Ba.lock();
            try {
                if (!b.this.isConnected() && !b.this.isConnecting()) {
                    b.this.connect();
                }
            } finally {
                b.this.Ba.unlock();
            }
        }
    }

    interface c<A extends Api.a> {
        void a(a aVar);

        void b(A a) throws DeadObjectException;

        void cancel();

        Api.c<A> ea();

        int ef();

        void k(Status status);
    }

    public b(Context context, Looper looper, fc fcVar, Map<Api<?>, Api.ApiOptions> map, Set<GoogleApiClient.ConnectionCallbacks> set, Set<GoogleApiClient.OnConnectionFailedListener> set2) {
        this.Bc = new fg(context, looper, this.Br);
        this.AS = looper;
        this.Bl = new HandlerC0033b(looper);
        Iterator<GoogleApiClient.ConnectionCallbacks> it = set.iterator();
        while (it.hasNext()) {
            this.Bc.registerConnectionCallbacks(it.next());
        }
        Iterator<GoogleApiClient.OnConnectionFailedListener> it2 = set2.iterator();
        while (it2.hasNext()) {
            this.Bc.registerConnectionFailedListener(it2.next());
        }
        for (Api<?> api : map.keySet()) {
            final Api.b<?, O> bVarDY = api.dY();
            this.Bn.put(api.ea(), a(bVarDY, map.get(api), context, looper, fcVar, this.Bq, new GoogleApiClient.OnConnectionFailedListener() { // from class: com.google.android.gms.common.api.b.4
                @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener
                public void onConnectionFailed(ConnectionResult result) {
                    b.this.Ba.lock();
                    try {
                        if (b.this.Be == null || bVarDY.getPriority() < b.this.Bf) {
                            b.this.Be = result;
                            b.this.Bf = bVarDY.getPriority();
                        }
                        b.this.ei();
                    } finally {
                        b.this.Ba.unlock();
                    }
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void E(int i) {
        this.Ba.lock();
        try {
            if (this.Bg != 3) {
                if (i == -1) {
                    if (isConnecting()) {
                        Iterator<c<?>> it = this.Bd.iterator();
                        while (it.hasNext()) {
                            c<?> next = it.next();
                            if (next.ef() != 1) {
                                next.cancel();
                                it.remove();
                            }
                        }
                    } else {
                        this.Bd.clear();
                    }
                    Iterator<c<?>> it2 = this.Bp.iterator();
                    while (it2.hasNext()) {
                        it2.next().cancel();
                    }
                    this.Bp.clear();
                    if (this.Be == null && !this.Bd.isEmpty()) {
                        this.Bi = true;
                        this.Ba.unlock();
                        return;
                    }
                }
                boolean zIsConnecting = isConnecting();
                boolean zIsConnected = isConnected();
                this.Bg = 3;
                if (zIsConnecting) {
                    if (i == -1) {
                        this.Be = null;
                    }
                    this.Bb.signalAll();
                }
                this.Bo = false;
                for (Api.a aVar : this.Bn.values()) {
                    if (aVar.isConnected()) {
                        aVar.disconnect();
                    }
                }
                this.Bo = true;
                this.Bg = 4;
                if (zIsConnected) {
                    if (i != -1) {
                        this.Bc.O(i);
                    }
                    this.Bo = false;
                }
            }
            this.Ba.unlock();
        } catch (Throwable th) {
            this.Ba.unlock();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <C extends Api.a, O> C a(Api.b<C, O> bVar, Object obj, Context context, Looper looper, fc fcVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return (C) bVar.a(context, looper, fcVar, obj, connectionCallbacks, onConnectionFailedListener);
    }

    private <A extends Api.a> void a(c<A> cVar) throws DeadObjectException {
        this.Ba.lock();
        try {
            fq.a(isConnected() || ek(), "GoogleApiClient is not connected yet.");
            fq.b(cVar.ea() != null, "This task can not be executed or enqueued (it's probably a Batch or malformed)");
            this.Bp.add(cVar);
            cVar.a(this.AL);
            if (ek()) {
                cVar.k(new Status(8));
            } else {
                cVar.b(a(cVar.ea()));
            }
        } finally {
            this.Ba.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ei() {
        this.Ba.lock();
        try {
            this.Bj--;
            if (this.Bj == 0) {
                if (this.Be != null) {
                    this.Bi = false;
                    E(3);
                    if (ek()) {
                        this.Bh--;
                    }
                    if (ek()) {
                        this.Bl.sendMessageDelayed(this.Bl.obtainMessage(1), this.Bk);
                    } else {
                        this.Bc.a(this.Be);
                    }
                    this.Bo = false;
                } else {
                    this.Bg = 2;
                    el();
                    this.Bb.signalAll();
                    ej();
                    if (this.Bi) {
                        this.Bi = false;
                        E(-1);
                    } else {
                        this.Bc.b(this.Bm.isEmpty() ? null : this.Bm);
                    }
                }
            }
        } finally {
            this.Ba.unlock();
        }
    }

    private void ej() {
        fq.a(isConnected() || ek(), "GoogleApiClient is not connected yet.");
        this.Ba.lock();
        while (!this.Bd.isEmpty()) {
            try {
                try {
                    a(this.Bd.remove());
                } catch (DeadObjectException e) {
                    Log.w("GoogleApiClientImpl", "Service died while flushing queue", e);
                }
            } catch (Throwable th) {
                this.Ba.unlock();
                throw th;
            }
        }
        this.Ba.unlock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean ek() {
        this.Ba.lock();
        try {
            return this.Bh != 0;
        } finally {
            this.Ba.unlock();
        }
    }

    private void el() {
        this.Ba.lock();
        try {
            this.Bh = 0;
            this.Bl.removeMessages(1);
        } finally {
            this.Ba.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <C extends Api.a> C a(Api.c<C> cVar) {
        C c2 = (C) this.Bn.get(cVar);
        fq.b(c2, "Appropriate Api was not requested.");
        return c2;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <A extends Api.a, T extends com.google.android.gms.common.api.a.b<? extends Result, A>> T a(T t) {
        this.Ba.lock();
        try {
            if (isConnected()) {
                b(t);
            } else {
                this.Bd.add(t);
            }
            return t;
        } finally {
            this.Ba.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <A extends Api.a, T extends com.google.android.gms.common.api.a.b<? extends Result, A>> T b(T t) {
        fq.a(isConnected() || ek(), "GoogleApiClient is not connected yet.");
        ej();
        try {
            a((c) t);
        } catch (DeadObjectException e) {
            E(1);
        }
        return t;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public ConnectionResult blockingConnect(long timeout, TimeUnit unit) {
        ConnectionResult connectionResult;
        fq.a(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.Ba.lock();
        try {
            connect();
            long nanos = unit.toNanos(timeout);
            while (isConnecting()) {
                try {
                    nanos = this.Bb.awaitNanos(nanos);
                    if (nanos <= 0) {
                        connectionResult = new ConnectionResult(14, null);
                        this.Ba.unlock();
                        return connectionResult;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    connectionResult = new ConnectionResult(15, null);
                    this.Ba.unlock();
                }
            }
            if (isConnected()) {
                connectionResult = ConnectionResult.Ag;
                this.Ba.unlock();
            } else if (this.Be != null) {
                connectionResult = this.Be;
                this.Ba.unlock();
            } else {
                connectionResult = new ConnectionResult(13, null);
                this.Ba.unlock();
            }
            return connectionResult;
        } catch (Throwable th) {
            this.Ba.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void connect() {
        this.Ba.lock();
        try {
            this.Bi = false;
            if (isConnected() || isConnecting()) {
                this.Ba.unlock();
                return;
            }
            this.Bo = true;
            this.Be = null;
            this.Bg = 1;
            this.Bm.clear();
            this.Bj = this.Bn.size();
            Iterator<Api.a> it = this.Bn.values().iterator();
            while (it.hasNext()) {
                it.next().connect();
            }
            this.Ba.unlock();
        } catch (Throwable th) {
            this.Ba.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void disconnect() {
        el();
        E(-1);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public Looper getLooper() {
        return this.AS;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnected() {
        this.Ba.lock();
        try {
            return this.Bg == 2;
        } finally {
            this.Ba.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnecting() {
        this.Ba.lock();
        try {
            return this.Bg == 1;
        } finally {
            this.Ba.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks listener) {
        return this.Bc.isConnectionCallbacksRegistered(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener listener) {
        return this.Bc.isConnectionFailedListenerRegistered(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void reconnect() {
        disconnect();
        connect();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        this.Bc.registerConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        this.Bc.registerConnectionFailedListener(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        this.Bc.unregisterConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        this.Bc.unregisterConnectionFailedListener(listener);
    }
}
