package com.google.android.gms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.ef;
import com.google.android.gms.internal.eg;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class c implements com.google.android.gms.analytics.b {
    private Context mContext;
    private ServiceConnection sj;
    private b sk;
    private InterfaceC0030c sl;
    private eg sm;

    final class a implements ServiceConnection {
        a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName component, IBinder binder) {
            aa.y("service connected, binder: " + binder);
            try {
                if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(binder.getInterfaceDescriptor())) {
                    aa.y("bound to service");
                    c.this.sm = eg.a.t(binder);
                    c.this.bU();
                    return;
                }
            } catch (RemoteException e) {
            }
            c.this.mContext.unbindService(this);
            c.this.sj = null;
            c.this.sl.a(2, null);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName component) {
            aa.y("service disconnected: " + component);
            c.this.sj = null;
            c.this.sk.onDisconnected();
        }
    }

    public interface b {
        void onConnected();

        void onDisconnected();
    }

    /* JADX INFO: renamed from: com.google.android.gms.analytics.c$c, reason: collision with other inner class name */
    public interface InterfaceC0030c {
        void a(int i, Intent intent);
    }

    public c(Context context, b bVar, InterfaceC0030c interfaceC0030c) {
        this.mContext = context;
        if (bVar == null) {
            throw new IllegalArgumentException("onConnectedListener cannot be null");
        }
        this.sk = bVar;
        if (interfaceC0030c == null) {
            throw new IllegalArgumentException("onConnectionFailedListener cannot be null");
        }
        this.sl = interfaceC0030c;
    }

    private eg bS() {
        bT();
        return this.sm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bU() {
        bV();
    }

    private void bV() {
        this.sk.onConnected();
    }

    @Override // com.google.android.gms.analytics.b
    public void a(Map<String, String> map, long j, String str, List<ef> list) {
        try {
            bS().a(map, j, str, list);
        } catch (RemoteException e) {
            aa.w("sendHit failed: " + e);
        }
    }

    @Override // com.google.android.gms.analytics.b
    public void bR() {
        try {
            bS().bR();
        } catch (RemoteException e) {
            aa.w("clear hits failed: " + e);
        }
    }

    protected void bT() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @Override // com.google.android.gms.analytics.b
    public void connect() {
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.setComponent(new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.analytics.service.AnalyticsService"));
        intent.putExtra("app_package_name", this.mContext.getPackageName());
        if (this.sj != null) {
            aa.w("Calling connect() while still connected, missing disconnect().");
            return;
        }
        this.sj = new a();
        boolean zBindService = this.mContext.bindService(intent, this.sj, 129);
        aa.y("connect: bindService returned " + zBindService + " for " + intent);
        if (zBindService) {
            return;
        }
        this.sj = null;
        this.sl.a(1, null);
    }

    @Override // com.google.android.gms.analytics.b
    public void disconnect() {
        this.sm = null;
        if (this.sj != null) {
            try {
                this.mContext.unbindService(this.sj);
            } catch (IllegalArgumentException e) {
            } catch (IllegalStateException e2) {
            }
            this.sj = null;
            this.sk.onDisconnected();
        }
    }

    public boolean isConnected() {
        return this.sm != null;
    }
}
