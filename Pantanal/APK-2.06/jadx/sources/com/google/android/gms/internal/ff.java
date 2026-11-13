package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class ff<T extends IInterface> implements GooglePlayServicesClient, Api.a, fg.b {
    public static final String[] Dg = {"service_esmobile", "service_googleme"};
    private final Looper AS;
    private final fg Bc;
    private T Da;
    private final ArrayList<ff<T>.b<?>> Db;
    private ff<T>.f Dc;
    private volatile int Dd;
    private final String[] De;
    boolean Df;
    private final Context mContext;
    final Handler mHandler;

    final class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1 && !ff.this.isConnecting()) {
                b bVar = (b) msg.obj;
                bVar.dx();
                bVar.unregister();
                return;
            }
            if (msg.what == 3) {
                ff.this.Bc.a(new ConnectionResult(((Integer) msg.obj).intValue(), null));
                return;
            }
            if (msg.what == 4) {
                ff.this.M(1);
                ff.this.Da = null;
                ff.this.Bc.O(((Integer) msg.obj).intValue());
            } else if (msg.what == 2 && !ff.this.isConnected()) {
                b bVar2 = (b) msg.obj;
                bVar2.dx();
                bVar2.unregister();
            } else if (msg.what == 2 || msg.what == 1) {
                ((b) msg.obj).eN();
            } else {
                Log.wtf("GmsClient", "Don't know how to handle this message.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract class b<TListener> {
        private boolean Di = false;
        private TListener mListener;

        public b(TListener tlistener) {
            this.mListener = tlistener;
        }

        protected abstract void a(TListener tlistener);

        protected abstract void dx();

        public void eN() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.mListener;
                if (this.Di) {
                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                }
            }
            if (tlistener != null) {
                try {
                    a(tlistener);
                } catch (RuntimeException e) {
                    dx();
                    throw e;
                }
            } else {
                dx();
            }
            synchronized (this) {
                this.Di = true;
            }
            unregister();
        }

        public void eO() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        public void unregister() {
            eO();
            synchronized (ff.this.Db) {
                ff.this.Db.remove(this);
            }
        }
    }

    public static final class c implements GoogleApiClient.ConnectionCallbacks {
        private final GooglePlayServicesClient.ConnectionCallbacks Dj;

        public c(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
            this.Dj = connectionCallbacks;
        }

        public boolean equals(Object other) {
            return other instanceof c ? this.Dj.equals(((c) other).Dj) : this.Dj.equals(other);
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnected(Bundle connectionHint) {
            this.Dj.onConnected(connectionHint);
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnectionSuspended(int cause) {
            this.Dj.onDisconnected();
        }
    }

    public abstract class d<TListener> extends ff<T>.b<TListener> {
        private final DataHolder BB;

        public d(TListener tlistener, DataHolder dataHolder) {
            super(tlistener);
            this.BB = dataHolder;
        }

        @Override // com.google.android.gms.internal.ff.b
        protected final void a(TListener tlistener) {
            a(tlistener, this.BB);
        }

        protected abstract void a(TListener tlistener, DataHolder dataHolder);

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
            if (this.BB != null) {
                this.BB.close();
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        public /* bridge */ /* synthetic */ void eN() {
            super.eN();
        }

        @Override // com.google.android.gms.internal.ff.b
        public /* bridge */ /* synthetic */ void eO() {
            super.eO();
        }

        @Override // com.google.android.gms.internal.ff.b
        public /* bridge */ /* synthetic */ void unregister() {
            super.unregister();
        }
    }

    public static final class e extends fl.a {
        private ff Dk;

        public e(ff ffVar) {
            this.Dk = ffVar;
        }

        @Override // com.google.android.gms.internal.fl
        public void b(int i, IBinder iBinder, Bundle bundle) {
            fq.b("onPostInitComplete can be called only once per call to getServiceFromBroker", (Object) this.Dk);
            this.Dk.a(i, iBinder, bundle);
            this.Dk = null;
        }
    }

    final class f implements ServiceConnection {
        f() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName component, IBinder binder) {
            ff.this.z(binder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName component) {
            ff.this.mHandler.sendMessage(ff.this.mHandler.obtainMessage(4, 1));
        }
    }

    public static final class g implements GoogleApiClient.OnConnectionFailedListener {
        private final GooglePlayServicesClient.OnConnectionFailedListener Dl;

        public g(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.Dl = onConnectionFailedListener;
        }

        public boolean equals(Object other) {
            return other instanceof g ? this.Dl.equals(((g) other).Dl) : this.Dl.equals(other);
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener
        public void onConnectionFailed(ConnectionResult result) {
            this.Dl.onConnectionFailed(result);
        }
    }

    protected final class h extends ff<T>.b<Boolean> {
        public final Bundle Dm;
        public final IBinder Dn;
        public final int statusCode;

        public h(int i, IBinder iBinder, Bundle bundle) {
            super(true);
            this.statusCode = i;
            this.Dn = iBinder;
            this.Dm = bundle;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(Boolean bool) {
            if (bool == null) {
                ff.this.M(1);
                return;
            }
            switch (this.statusCode) {
                case 0:
                    try {
                        if (ff.this.bh().equals(this.Dn.getInterfaceDescriptor())) {
                            ff.this.Da = ff.this.r(this.Dn);
                            if (ff.this.Da != null) {
                                ff.this.M(3);
                                ff.this.Bc.bV();
                                return;
                            }
                        }
                    } catch (RemoteException e) {
                    }
                    fh.x(ff.this.mContext).b(ff.this.bg(), ff.this.Dc);
                    ff.this.Dc = null;
                    ff.this.M(1);
                    ff.this.Da = null;
                    ff.this.Bc.a(new ConnectionResult(8, null));
                    return;
                case 10:
                    ff.this.M(1);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                default:
                    PendingIntent pendingIntent = this.Dm != null ? (PendingIntent) this.Dm.getParcelable("pendingIntent") : null;
                    if (ff.this.Dc != null) {
                        fh.x(ff.this.mContext).b(ff.this.bg(), ff.this.Dc);
                        ff.this.Dc = null;
                    }
                    ff.this.M(1);
                    ff.this.Da = null;
                    ff.this.Bc.a(new ConnectionResult(this.statusCode, pendingIntent));
                    return;
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    protected ff(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this.Db = new ArrayList<>();
        this.Dd = 1;
        this.Df = false;
        this.mContext = (Context) fq.f(context);
        this.AS = (Looper) fq.b(looper, "Looper must not be null");
        this.Bc = new fg(context, looper, this);
        this.mHandler = new a(looper);
        b(strArr);
        this.De = strArr;
        registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) fq.f(connectionCallbacks));
        registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) fq.f(onConnectionFailedListener));
    }

    protected ff(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this(context, context.getMainLooper(), new c(connectionCallbacks), new g(onConnectionFailedListener), strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void M(int i) {
        int i2 = this.Dd;
        this.Dd = i;
        if (i2 != i) {
            if (i == 3) {
                onConnected();
            } else if (i2 == 3 && i == 1) {
                onDisconnected();
            }
        }
    }

    public void N(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.valueOf(i)));
    }

    protected void a(int i, IBinder iBinder, Bundle bundle) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new h(i, iBinder, bundle)));
    }

    public final void a(ff<T>.b<?> bVar) {
        synchronized (this.Db) {
            this.Db.add(bVar);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, bVar));
    }

    protected abstract void a(fm fmVar, e eVar) throws RemoteException;

    protected void b(String... strArr) {
    }

    protected final void bT() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    protected abstract String bg();

    protected abstract String bh();

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void connect() {
        this.Df = true;
        M(2);
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
        if (iIsGooglePlayServicesAvailable != 0) {
            M(1);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(iIsGooglePlayServicesAvailable)));
            return;
        }
        if (this.Dc != null) {
            Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
            this.Da = null;
            fh.x(this.mContext).b(bg(), this.Dc);
        }
        this.Dc = new f();
        if (fh.x(this.mContext).a(bg(), this.Dc)) {
            return;
        }
        Log.e("GmsClient", "unable to connect to service: " + bg());
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, 9));
    }

    public Bundle dG() {
        return null;
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        this.Df = false;
        synchronized (this.Db) {
            int size = this.Db.size();
            for (int i = 0; i < size; i++) {
                this.Db.get(i).eO();
            }
            this.Db.clear();
        }
        M(1);
        this.Da = null;
        if (this.Dc != null) {
            fh.x(this.mContext).b(bg(), this.Dc);
            this.Dc = null;
        }
    }

    public final String[] eL() {
        return this.De;
    }

    protected final T eM() {
        bT();
        return this.Da;
    }

    @Override // com.google.android.gms.internal.fg.b
    public boolean em() {
        return this.Df;
    }

    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.google.android.gms.common.api.Api.a
    public final Looper getLooper() {
        return this.AS;
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a, com.google.android.gms.internal.fg.b
    public boolean isConnected() {
        return this.Dd == 3;
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public boolean isConnecting() {
        return this.Dd == 2;
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
        return this.Bc.isConnectionCallbacksRegistered(new c(listener));
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        return this.Bc.isConnectionFailedListenerRegistered(listener);
    }

    protected void onConnected() {
    }

    protected void onDisconnected() {
    }

    protected abstract T r(IBinder iBinder);

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.Bc.registerConnectionCallbacks(new c(listener));
    }

    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        this.Bc.registerConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.Bc.registerConnectionFailedListener(listener);
    }

    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        this.Bc.registerConnectionFailedListener(listener);
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.Bc.unregisterConnectionCallbacks(new c(listener));
    }

    @Override // com.google.android.gms.common.GooglePlayServicesClient
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.Bc.unregisterConnectionFailedListener(listener);
    }

    protected final void z(IBinder iBinder) {
        try {
            a(fm.a.C(iBinder), new e(this));
        } catch (RemoteException e2) {
            Log.w("GmsClient", "service died");
        }
    }
}
