package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;

/* JADX INFO: loaded from: classes.dex */
public abstract class cv extends Cdo {
    private final cx mQ;
    private final cu.a pc;

    public static final class a extends cv {
        private final Context mContext;

        public a(Context context, cx cxVar, cu.a aVar) {
            super(cxVar, aVar);
            this.mContext = context;
        }

        @Override // com.google.android.gms.internal.cv
        public void be() {
        }

        @Override // com.google.android.gms.internal.cv
        public db bf() {
            return dc.a(this.mContext, new ax(), new bg());
        }
    }

    public static final class b extends cv implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
        private final Object li;
        private final cu.a pc;
        private final cw pd;

        public b(Context context, cx cxVar, cu.a aVar) {
            super(cxVar, aVar);
            this.li = new Object();
            this.pc = aVar;
            this.pd = new cw(context, this, this, cxVar.kK.rs);
            this.pd.connect();
        }

        @Override // com.google.android.gms.internal.cv
        public void be() {
            synchronized (this.li) {
                if (this.pd.isConnected() || this.pd.isConnecting()) {
                    this.pd.disconnect();
                }
            }
        }

        @Override // com.google.android.gms.internal.cv
        public db bf() {
            db dbVarBi;
            synchronized (this.li) {
                try {
                    dbVarBi = this.pd.bi();
                } catch (IllegalStateException e) {
                    dbVarBi = null;
                }
            }
            return dbVarBi;
        }

        @Override // com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
        public void onConnected(Bundle connectionHint) {
            start();
        }

        @Override // com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener
        public void onConnectionFailed(ConnectionResult result) {
            this.pc.a(new cz(0));
        }

        @Override // com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
        public void onDisconnected() {
            dw.v("Disconnected from remote ad request service.");
        }
    }

    public cv(cx cxVar, cu.a aVar) {
        this.mQ = cxVar;
        this.pc = aVar;
    }

    private static cz a(db dbVar, cx cxVar) {
        try {
            return dbVar.b(cxVar);
        } catch (RemoteException e) {
            dw.c("Could not fetch ad response from ad request service.", e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.Cdo
    public final void aY() {
        cz czVarA;
        try {
            db dbVarBf = bf();
            if (dbVarBf == null || (czVarA = a(dbVarBf, this.mQ)) == null) {
                czVarA = new cz(0);
            }
            be();
            this.pc.a(czVarA);
        } catch (Throwable th) {
            be();
            throw th;
        }
    }

    public abstract void be();

    public abstract db bf();

    @Override // com.google.android.gms.internal.Cdo
    public final void onStop() {
        be();
    }
}
