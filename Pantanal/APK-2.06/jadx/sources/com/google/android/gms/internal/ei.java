package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appstate.AppStateBuffer;
import com.google.android.gms.appstate.AppStateManager;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class ei extends ff<ek> {
    private final String wG;

    final class a extends eh {
        private final com.google.android.gms.common.api.a.d<AppStateManager.StateDeletedResult> wH;

        public a(com.google.android.gms.common.api.a.d<AppStateManager.StateDeletedResult> dVar) {
            this.wH = (com.google.android.gms.common.api.a.d) fq.b(dVar, "Result holder must not be null");
        }

        @Override // com.google.android.gms.internal.eh, com.google.android.gms.internal.ej
        public void b(int i, int i2) {
            ei.this.a(ei.this.new b(this.wH, new Status(i), i2));
        }
    }

    final class b extends ff<ek>.b<com.google.android.gms.common.api.a.d<AppStateManager.StateDeletedResult>> implements AppStateManager.StateDeletedResult {
        private final Status wJ;
        private final int wK;

        public b(com.google.android.gms.common.api.a.d<AppStateManager.StateDeletedResult> dVar, Status status, int i) {
            super(dVar);
            this.wJ = status;
            this.wK = i;
        }

        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateDeletedResult> dVar) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateDeletedResult
        public int getStateKey() {
            return this.wK;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class c extends eh {
        private final com.google.android.gms.common.api.a.d<AppStateManager.StateListResult> wH;

        public c(com.google.android.gms.common.api.a.d<AppStateManager.StateListResult> dVar) {
            this.wH = (com.google.android.gms.common.api.a.d) fq.b(dVar, "Result holder must not be null");
        }

        @Override // com.google.android.gms.internal.eh, com.google.android.gms.internal.ej
        public void a(DataHolder dataHolder) {
            ei.this.a(ei.this.new d(this.wH, new Status(dataHolder.getStatusCode()), dataHolder));
        }
    }

    final class d extends ff<ek>.d<com.google.android.gms.common.api.a.d<AppStateManager.StateListResult>> implements AppStateManager.StateListResult {
        private final Status wJ;
        private final AppStateBuffer wL;

        public d(com.google.android.gms.common.api.a.d<AppStateManager.StateListResult> dVar, Status status, DataHolder dataHolder) {
            super(dVar, dataHolder);
            this.wJ = status;
            this.wL = new AppStateBuffer(dataHolder);
        }

        @Override // com.google.android.gms.internal.ff.d
        public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateListResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateListResult
        public AppStateBuffer getStateBuffer() {
            return this.wL;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class e extends eh {
        private final com.google.android.gms.common.api.a.d<AppStateManager.StateResult> wH;

        public e(com.google.android.gms.common.api.a.d<AppStateManager.StateResult> dVar) {
            this.wH = (com.google.android.gms.common.api.a.d) fq.b(dVar, "Result holder must not be null");
        }

        @Override // com.google.android.gms.internal.eh, com.google.android.gms.internal.ej
        public void a(int i, DataHolder dataHolder) {
            ei.this.a(ei.this.new f(this.wH, i, dataHolder));
        }
    }

    final class f extends ff<ek>.d<com.google.android.gms.common.api.a.d<AppStateManager.StateResult>> implements AppStateManager.StateConflictResult, AppStateManager.StateLoadedResult, AppStateManager.StateResult {
        private final Status wJ;
        private final int wK;
        private final AppStateBuffer wL;

        public f(com.google.android.gms.common.api.a.d<AppStateManager.StateResult> dVar, int i, DataHolder dataHolder) {
            super(dVar, dataHolder);
            this.wK = i;
            this.wJ = new Status(dataHolder.getStatusCode());
            this.wL = new AppStateBuffer(dataHolder);
        }

        private boolean dy() {
            return this.wJ.getStatusCode() == 2000;
        }

        @Override // com.google.android.gms.internal.ff.d
        public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateResult> dVar, DataHolder dataHolder) {
            dVar.b(this);
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateResult
        public AppStateManager.StateConflictResult getConflictResult() {
            if (dy()) {
                return this;
            }
            return null;
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateResult
        public AppStateManager.StateLoadedResult getLoadedResult() {
            if (dy()) {
                return null;
            }
            return this;
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateConflictResult, com.google.android.gms.appstate.AppStateManager.StateLoadedResult
        public byte[] getLocalData() {
            if (this.wL.getCount() == 0) {
                return null;
            }
            return this.wL.get(0).getLocalData();
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateConflictResult
        public String getResolvedVersion() {
            if (this.wL.getCount() == 0) {
                return null;
            }
            return this.wL.get(0).getConflictVersion();
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateConflictResult
        public byte[] getServerData() {
            if (this.wL.getCount() == 0) {
                return null;
            }
            return this.wL.get(0).getConflictData();
        }

        @Override // com.google.android.gms.appstate.AppStateManager.StateConflictResult, com.google.android.gms.appstate.AppStateManager.StateLoadedResult
        public int getStateKey() {
            return this.wK;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            this.wL.close();
        }
    }

    final class g extends eh {
        com.google.android.gms.common.api.a.d<Status> wH;

        public g(com.google.android.gms.common.api.a.d<Status> dVar) {
            this.wH = (com.google.android.gms.common.api.a.d) fq.b(dVar, "Holder must not be null");
        }

        @Override // com.google.android.gms.internal.eh, com.google.android.gms.internal.ej
        public void du() {
            ei.this.a(ei.this.new h(this.wH, new Status(0)));
        }
    }

    final class h extends ff<ek>.b<com.google.android.gms.common.api.a.d<Status>> {
        private final Status wJ;

        public h(com.google.android.gms.common.api.a.d<Status> dVar, Status status) {
            super(dVar);
            this.wJ = status;
        }

        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(com.google.android.gms.common.api.a.d<Status> dVar) {
            dVar.b(this.wJ);
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    public ei(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, strArr);
        this.wG = (String) fq.f(str);
    }

    public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateListResult> dVar) {
        try {
            eM().a(new c(dVar));
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateDeletedResult> dVar, int i) {
        try {
            eM().b(new a(dVar), i);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateResult> dVar, int i, String str, byte[] bArr) {
        try {
            eM().a(new e(dVar), i, str, bArr);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void a(com.google.android.gms.common.api.a.d<AppStateManager.StateResult> dVar, int i, byte[] bArr) {
        e eVar;
        if (dVar == null) {
            eVar = null;
        } else {
            try {
                eVar = new e(dVar);
            } catch (RemoteException e2) {
                Log.w("AppStateClient", "service died");
                return;
            }
        }
        eM().a(eVar, i, bArr);
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        fmVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.wG, eL());
    }

    public void b(com.google.android.gms.common.api.a.d<Status> dVar) {
        try {
            eM().b(new g(dVar));
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void b(com.google.android.gms.common.api.a.d<AppStateManager.StateResult> dVar, int i) {
        try {
            eM().a(new e(dVar), i);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void b(String... strArr) {
        boolean z = false;
        for (String str : strArr) {
            if (str.equals(Scopes.APP_STATE)) {
                z = true;
            }
        }
        fq.a(z, String.format("App State APIs requires %s to function.", Scopes.APP_STATE));
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.appstate.service.START";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.appstate.internal.IAppStateService";
    }

    public int dv() {
        try {
            return eM().dv();
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    public int dw() {
        try {
            return eM().dw();
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: u, reason: merged with bridge method [inline-methods] */
    public ek r(IBinder iBinder) {
        return ek.a.w(iBinder);
    }
}
