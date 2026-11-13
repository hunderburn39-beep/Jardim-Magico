package com.google.android.gms.drive.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Map;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class n extends ff<u> {
    private DriveId Fh;
    private DriveId Fi;
    final GoogleApiClient.ConnectionCallbacks Fj;
    Map<DriveId, Map<DriveEvent.Listener<?>, s<?>>> Fk;
    private final String wG;

    public n(Context context, Looper looper, fc fcVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String[] strArr) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, strArr);
        this.Fk = new HashMap();
        this.wG = (String) fq.b(fcVar.eC(), (Object) "Must call Api.ClientBuilder.setAccountName()");
        this.Fj = connectionCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: F, reason: merged with bridge method [inline-methods] */
    public u r(IBinder iBinder) {
        return u.a.G(iBinder);
    }

    <C extends DriveEvent> PendingResult<Status> a(GoogleApiClient googleApiClient, final DriveId driveId, final int i, DriveEvent.Listener<C> listener) {
        PendingResult<Status> pendingResultB;
        fq.b(com.google.android.gms.drive.events.c.a(i, driveId), TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        fq.b(listener, "listener");
        fq.a(isConnected(), "Client must be connected");
        synchronized (this.Fk) {
            Map<DriveEvent.Listener<?>, s<?>> map = this.Fk.get(driveId);
            if (map == null) {
                map = new HashMap<>();
                this.Fk.put(driveId, map);
            }
            if (map.containsKey(listener)) {
                pendingResultB = new l.k(googleApiClient, Status.Bv);
            } else {
                final s<?> sVar = new s<>(getLooper(), i, listener);
                map.put(listener, sVar);
                pendingResultB = googleApiClient.b(new l.j() { // from class: com.google.android.gms.drive.internal.n.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.google.android.gms.common.api.a.b
                    public void a(n nVar) throws RemoteException {
                        nVar.fE().a(new AddEventListenerRequest(driveId, i, null), sVar, (String) null, new al(this));
                    }
                });
            }
        }
        return pendingResultB;
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.Fh = (DriveId) bundle.getParcelable("com.google.android.gms.drive.root_id");
            this.Fi = (DriveId) bundle.getParcelable("com.google.android.gms.drive.appdata_id");
        }
        super.a(i, iBinder, bundle);
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        String packageName = getContext().getPackageName();
        fq.f(eVar);
        fq.f(packageName);
        fq.f(eL());
        fmVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, packageName, eL(), this.wG, new Bundle());
    }

    PendingResult<Status> b(GoogleApiClient googleApiClient, final DriveId driveId, final int i, DriveEvent.Listener<?> listener) {
        PendingResult<Status> pendingResultB;
        fq.b(com.google.android.gms.drive.events.c.a(i, driveId), TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        fq.b(listener, "listener");
        fq.a(isConnected(), "Client must be connected");
        synchronized (this.Fk) {
            Map<DriveEvent.Listener<?>, s<?>> map = this.Fk.get(driveId);
            if (map == null) {
                pendingResultB = new l.k(googleApiClient, Status.Bv);
            } else {
                final s<?> sVarRemove = map.remove(listener);
                if (sVarRemove == null) {
                    pendingResultB = new l.k(googleApiClient, Status.Bv);
                } else {
                    if (map.isEmpty()) {
                        this.Fk.remove(driveId);
                    }
                    pendingResultB = googleApiClient.b(new l.j() { // from class: com.google.android.gms.drive.internal.n.2
                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // com.google.android.gms.common.api.a.b
                        public void a(n nVar) throws RemoteException {
                            nVar.fE().a(new RemoveEventListenerRequest(driveId, i), sVarRemove, (String) null, new al(this));
                        }
                    });
                }
            }
        }
        return pendingResultB;
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.drive.ApiService.START";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.drive.internal.IDriveService";
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        u uVarEM = eM();
        if (uVarEM != null) {
            try {
                uVarEM.a(new DisconnectRequest());
            } catch (RemoteException e) {
            }
        }
        super.disconnect();
        this.Fk.clear();
    }

    public u fE() {
        return eM();
    }

    public DriveId fF() {
        return this.Fh;
    }

    public DriveId fG() {
        return this.Fi;
    }
}
