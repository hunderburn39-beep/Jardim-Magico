package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class en extends ff<ep> {
    private static final er zb = new er("CastClientImpl");
    private static final Object zs = new Object();
    private static final Object zt = new Object();
    private final Handler mHandler;
    private final Cast.Listener xX;
    private double yC;
    private boolean yD;
    private ApplicationMetadata zc;
    private final CastDevice zd;
    private final eq ze;
    private final Map<String, Cast.MessageReceivedCallback> zf;
    private final long zg;
    private String zh;
    private boolean zi;
    private boolean zj;
    private final AtomicLong zk;
    private String zl;
    private String zm;
    private Bundle zn;
    private Map<Long, com.google.android.gms.common.api.a.d<Status>> zo;
    private b zp;
    private com.google.android.gms.common.api.a.d<Cast.ApplicationConnectionResult> zq;
    private com.google.android.gms.common.api.a.d<Status> zr;

    private static final class a implements Cast.ApplicationConnectionResult {
        private final String qL;
        private final Status wJ;
        private final String zA;
        private final boolean zB;
        private final ApplicationMetadata zz;

        public a(Status status) {
            this(status, null, null, null, false);
        }

        public a(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
            this.wJ = status;
            this.zz = applicationMetadata;
            this.zA = str;
            this.qL = str2;
            this.zB = z;
        }

        @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
        public ApplicationMetadata getApplicationMetadata() {
            return this.zz;
        }

        @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
        public String getApplicationStatus() {
            return this.zA;
        }

        @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
        public String getSessionId() {
            return this.qL;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.cast.Cast.ApplicationConnectionResult
        public boolean getWasLaunched() {
            return this.zB;
        }
    }

    private class b implements GoogleApiClient.OnConnectionFailedListener {
        private b() {
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener
        public void onConnectionFailed(ConnectionResult result) {
            en.this.dJ();
        }
    }

    public en(Context context, Looper looper, CastDevice castDevice, long j, Cast.Listener listener, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[]) null);
        this.zd = castDevice;
        this.xX = listener;
        this.zg = j;
        this.mHandler = new Handler(looper);
        this.zf = new HashMap();
        this.zj = false;
        this.zc = null;
        this.zh = null;
        this.yC = 0.0d;
        this.yD = false;
        this.zk = new AtomicLong(0L);
        this.zo = new HashMap();
        this.zp = new b();
        registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this.zp);
        this.ze = new eq.a() { // from class: com.google.android.gms.internal.en.1
            private boolean D(int i) {
                synchronized (en.zt) {
                    if (en.this.zr == null) {
                        return false;
                    }
                    en.this.zr.b(new Status(i));
                    en.this.zr = null;
                    return true;
                }
            }

            private void b(long j2, int i) {
                com.google.android.gms.common.api.a.d dVar;
                synchronized (en.this.zo) {
                    dVar = (com.google.android.gms.common.api.a.d) en.this.zo.remove(Long.valueOf(j2));
                }
                if (dVar != null) {
                    dVar.b(new Status(i));
                }
            }

            @Override // com.google.android.gms.internal.eq
            public void A(int i) {
                synchronized (en.zs) {
                    if (en.this.zq != null) {
                        en.this.zq.b(new a(new Status(i)));
                        en.this.zq = null;
                    }
                }
            }

            @Override // com.google.android.gms.internal.eq
            public void B(int i) {
                D(i);
            }

            @Override // com.google.android.gms.internal.eq
            public void C(int i) {
                D(i);
            }

            @Override // com.google.android.gms.internal.eq
            public void a(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
                en.this.zc = applicationMetadata;
                en.this.zl = applicationMetadata.getApplicationId();
                en.this.zm = str2;
                synchronized (en.zs) {
                    if (en.this.zq != null) {
                        en.this.zq.b(new a(new Status(0), applicationMetadata, str, str2, z));
                        en.this.zq = null;
                    }
                }
            }

            @Override // com.google.android.gms.internal.eq
            public void a(String str, long j2) {
                b(j2, 0);
            }

            @Override // com.google.android.gms.internal.eq
            public void a(String str, long j2, int i) {
                b(j2, i);
            }

            @Override // com.google.android.gms.internal.eq
            public void b(final String str, final double d, final boolean z) {
                en.this.mHandler.post(new Runnable() { // from class: com.google.android.gms.internal.en.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        en.this.a(str, d, z);
                    }
                });
            }

            @Override // com.google.android.gms.internal.eq
            public void b(String str, byte[] bArr) {
                en.zb.b("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", str, Integer.valueOf(bArr.length));
            }

            @Override // com.google.android.gms.internal.eq
            public void d(final String str, final String str2) {
                en.zb.b("Receive (type=text, ns=%s) %s", str, str2);
                en.this.mHandler.post(new Runnable() { // from class: com.google.android.gms.internal.en.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Cast.MessageReceivedCallback messageReceivedCallback;
                        synchronized (en.this.zf) {
                            messageReceivedCallback = (Cast.MessageReceivedCallback) en.this.zf.get(str);
                        }
                        if (messageReceivedCallback != null) {
                            messageReceivedCallback.onMessageReceived(en.this.zd, str, str2);
                        } else {
                            en.zb.b("Discarded message for unknown namespace '%s'", str);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.internal.eq
            public void onApplicationDisconnected(final int statusCode) {
                en.this.zl = null;
                en.this.zm = null;
                if (D(statusCode) || en.this.xX == null) {
                    return;
                }
                en.this.mHandler.post(new Runnable() { // from class: com.google.android.gms.internal.en.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (en.this.xX != null) {
                            en.this.xX.onApplicationDisconnected(statusCode);
                        }
                    }
                });
            }

            @Override // com.google.android.gms.internal.eq
            public void z(int i) {
                en.zb.b("ICastDeviceControllerListener.onDisconnected: %d", Integer.valueOf(i));
                en.this.zj = false;
                en.this.zc = null;
                if (i != 0) {
                    en.this.N(2);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, double d, boolean z) {
        boolean z2;
        boolean z3;
        if (eo.a(str, this.zh)) {
            z2 = false;
        } else {
            this.zh = str;
            z2 = true;
        }
        if (this.xX != null && (z2 || this.zi)) {
            this.xX.onApplicationStatusChanged();
        }
        if (d != this.yC) {
            this.yC = d;
            z3 = true;
        } else {
            z3 = false;
        }
        if (z != this.yD) {
            this.yD = z;
            z3 = true;
        }
        zb.b("hasChange=%b, mFirstStatusUpdate=%b", Boolean.valueOf(z3), Boolean.valueOf(this.zi));
        if (this.xX != null && (z3 || this.zi)) {
            this.xX.onVolumeChanged();
        }
        this.zi = false;
    }

    private void d(com.google.android.gms.common.api.a.d<Cast.ApplicationConnectionResult> dVar) {
        synchronized (zs) {
            if (this.zq != null) {
                this.zq.b(new a(new Status(2002)));
            }
            this.zq = dVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dJ() {
        zb.b("removing all MessageReceivedCallbacks", new Object[0]);
        synchronized (this.zf) {
            this.zf.clear();
        }
    }

    private void dK() throws IllegalStateException {
        if (!this.zj) {
            throw new IllegalStateException("not connected to a device");
        }
    }

    private void f(com.google.android.gms.common.api.a.d<Status> dVar) {
        synchronized (zt) {
            if (this.zr != null) {
                dVar.b(new Status(2001));
            } else {
                this.zr = dVar;
            }
        }
    }

    public void V(String str) throws RemoteException, IllegalArgumentException {
        Cast.MessageReceivedCallback messageReceivedCallbackRemove;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        synchronized (this.zf) {
            messageReceivedCallbackRemove = this.zf.remove(str);
        }
        if (messageReceivedCallbackRemove != null) {
            try {
                eM().aa(str);
            } catch (IllegalStateException e) {
                zb.a(e, "Error unregistering namespace (%s): %s", str, e.getMessage());
            }
        }
    }

    public void a(double d) throws IllegalStateException, RemoteException, IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        eM().a(d, this.yC, this.yD);
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 || i == 1001) {
            this.zj = true;
            this.zi = true;
        } else {
            this.zj = false;
        }
        if (i == 1001) {
            this.zn = new Bundle();
            this.zn.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
            i = 0;
        }
        super.a(i, iBinder, bundle);
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        Bundle bundle = new Bundle();
        zb.b("getServiceFromBroker(): mLastApplicationId=%s, mLastSessionId=%s", this.zl, this.zm);
        this.zd.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.zg);
        if (this.zl != null) {
            bundle.putString("last_application_id", this.zl);
            if (this.zm != null) {
                bundle.putString("last_session_id", this.zm);
            }
        }
        fmVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.ze.asBinder(), bundle);
    }

    public void a(String str, Cast.MessageReceivedCallback messageReceivedCallback) throws IllegalStateException, RemoteException, IllegalArgumentException {
        eo.W(str);
        V(str);
        if (messageReceivedCallback != null) {
            synchronized (this.zf) {
                this.zf.put(str, messageReceivedCallback);
            }
            eM().Z(str);
        }
    }

    public void a(String str, com.google.android.gms.common.api.a.d<Status> dVar) throws IllegalStateException, RemoteException {
        f(dVar);
        eM().Y(str);
    }

    public void a(String str, String str2, com.google.android.gms.common.api.a.d<Status> dVar) throws IllegalStateException, RemoteException, IllegalArgumentException {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        }
        if (str2.length() > 65536) {
            throw new IllegalArgumentException("Message exceeds maximum size");
        }
        eo.W(str);
        dK();
        long jIncrementAndGet = this.zk.incrementAndGet();
        eM().a(str, str2, jIncrementAndGet);
        this.zo.put(Long.valueOf(jIncrementAndGet), dVar);
    }

    public void a(String str, boolean z, com.google.android.gms.common.api.a.d<Cast.ApplicationConnectionResult> dVar) throws IllegalStateException, RemoteException {
        d(dVar);
        eM().e(str, z);
    }

    public void b(String str, String str2, com.google.android.gms.common.api.a.d<Cast.ApplicationConnectionResult> dVar) throws IllegalStateException, RemoteException {
        d(dVar);
        eM().e(str, str2);
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.internal.fg.b
    public Bundle dG() {
        if (this.zn == null) {
            return super.dG();
        }
        Bundle bundle = this.zn;
        this.zn = null;
        return bundle;
    }

    public void dH() throws IllegalStateException, RemoteException {
        eM().dH();
    }

    public double dI() throws IllegalStateException {
        dK();
        return this.yC;
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        dJ();
        try {
            if (isConnected()) {
                eM().disconnect();
            }
        } catch (RemoteException e) {
            zb.b("Error while disconnecting the controller interface: %s", e.getMessage());
        } finally {
            super.disconnect();
        }
    }

    public void e(com.google.android.gms.common.api.a.d<Status> dVar) throws IllegalStateException, RemoteException {
        f(dVar);
        eM().dO();
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        dK();
        return this.zc;
    }

    public String getApplicationStatus() throws IllegalStateException {
        dK();
        return this.zh;
    }

    public boolean isMute() throws IllegalStateException {
        dK();
        return this.yD;
    }

    public void v(boolean z) throws IllegalStateException, RemoteException {
        eM().a(z, this.yC, this.yD);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: x, reason: merged with bridge method [inline-methods] */
    public ep r(IBinder iBinder) {
        return ep.a.y(iBinder);
    }
}
