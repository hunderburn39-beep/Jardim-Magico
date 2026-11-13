package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class hc extends ff<ha> {
    private final hf<ha> Ok;
    private final hb Oq;
    private final hr Or;
    private final String Os;
    private final Context mContext;
    private final String wG;

    private final class a extends ff<ha>.b<LocationClient.OnAddGeofencesResultListener> {
        private final int Ah;
        private final String[] Ot;

        public a(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, int i, String[] strArr) {
            super(onAddGeofencesResultListener);
            this.Ah = LocationStatusCodes.bz(i);
            this.Ot = strArr;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        public void a(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener) {
            if (onAddGeofencesResultListener != null) {
                onAddGeofencesResultListener.onAddGeofencesResult(this.Ah, this.Ot);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    private static final class b extends gz.a {
        private LocationClient.OnAddGeofencesResultListener Ov;
        private LocationClient.OnRemoveGeofencesResultListener Ow;
        private hc Ox;

        public b(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, hc hcVar) {
            this.Ov = onAddGeofencesResultListener;
            this.Ow = null;
            this.Ox = hcVar;
        }

        public b(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, hc hcVar) {
            this.Ow = onRemoveGeofencesResultListener;
            this.Ov = null;
            this.Ox = hcVar;
        }

        @Override // com.google.android.gms.internal.gz
        public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) throws RemoteException {
            if (this.Ox == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            hc hcVar = this.Ox;
            hc hcVar2 = this.Ox;
            hcVar2.getClass();
            hcVar.a(hcVar2.new a(this.Ov, statusCode, geofenceRequestIds));
            this.Ox = null;
            this.Ov = null;
            this.Ow = null;
        }

        @Override // com.google.android.gms.internal.gz
        public void onRemoveGeofencesByPendingIntentResult(int statusCode, PendingIntent pendingIntent) {
            if (this.Ox == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesByPendingIntentResult called multiple times");
                return;
            }
            hc hcVar = this.Ox;
            hc hcVar2 = this.Ox;
            hcVar2.getClass();
            hcVar.a(hcVar2.new d(1, this.Ow, statusCode, pendingIntent));
            this.Ox = null;
            this.Ov = null;
            this.Ow = null;
        }

        @Override // com.google.android.gms.internal.gz
        public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] geofenceRequestIds) {
            if (this.Ox == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesByRequestIdsResult called multiple times");
                return;
            }
            hc hcVar = this.Ox;
            hc hcVar2 = this.Ox;
            hcVar2.getClass();
            hcVar.a(hcVar2.new d(2, this.Ow, statusCode, geofenceRequestIds));
            this.Ox = null;
            this.Ov = null;
            this.Ow = null;
        }
    }

    private final class c implements hf<ha> {
        private c() {
        }

        @Override // com.google.android.gms.internal.hf
        public void bT() {
            hc.this.bT();
        }

        @Override // com.google.android.gms.internal.hf
        /* JADX INFO: renamed from: hR, reason: merged with bridge method [inline-methods] */
        public ha eM() {
            return (ha) hc.this.eM();
        }
    }

    private final class d extends ff<ha>.b<LocationClient.OnRemoveGeofencesResultListener> {
        private final int Ah;
        private final String[] Ot;
        private final int Oy;
        private final PendingIntent mPendingIntent;

        public d(int i, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, PendingIntent pendingIntent) {
            super(onRemoveGeofencesResultListener);
            fb.x(i == 1);
            this.Oy = i;
            this.Ah = LocationStatusCodes.bz(i2);
            this.mPendingIntent = pendingIntent;
            this.Ot = null;
        }

        public d(int i, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, String[] strArr) {
            super(onRemoveGeofencesResultListener);
            fb.x(i == 2);
            this.Oy = i;
            this.Ah = LocationStatusCodes.bz(i2);
            this.Ot = strArr;
            this.mPendingIntent = null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        public void a(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
            if (onRemoveGeofencesResultListener != null) {
                switch (this.Oy) {
                    case 1:
                        onRemoveGeofencesResultListener.onRemoveGeofencesByPendingIntentResult(this.Ah, this.mPendingIntent);
                        break;
                    case 2:
                        onRemoveGeofencesResultListener.onRemoveGeofencesByRequestIdsResult(this.Ah, this.Ot);
                        break;
                    default:
                        Log.wtf("LocationClientImpl", "Unsupported action: " + this.Oy);
                        break;
                }
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    public hc(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String str) {
        super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.Ok = new c();
        this.mContext = context;
        this.Oq = new hb(context, this.Ok);
        this.Os = str;
        this.wG = null;
        this.Or = new hr(getContext(), context.getPackageName(), this.Ok);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] */
    public ha r(IBinder iBinder) {
        return ha.a.W(iBinder);
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.Os);
        fmVar.e(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), bundle);
    }

    public void addGeofences(List<hd> geofences, PendingIntent pendingIntent, LocationClient.OnAddGeofencesResultListener listener) {
        b bVar;
        bT();
        fq.b(geofences != null && geofences.size() > 0, "At least one geofence must be specified.");
        fq.b(pendingIntent, "PendingIntent must be specified.");
        fq.b(listener, "OnAddGeofencesResultListener not provided.");
        if (listener == null) {
            bVar = null;
        } else {
            try {
                bVar = new b(listener, this);
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
        eM().a(geofences, pendingIntent, bVar, getContext().getPackageName());
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    @Override // com.google.android.gms.internal.ff, com.google.android.gms.common.GooglePlayServicesClient, com.google.android.gms.common.api.Api.a
    public void disconnect() {
        synchronized (this.Oq) {
            if (isConnected()) {
                this.Oq.removeAllListeners();
                this.Oq.hQ();
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.Oq.getLastLocation();
    }

    public void removeActivityUpdates(PendingIntent callbackIntent) {
        bT();
        fq.f(callbackIntent);
        try {
            eM().removeActivityUpdates(callbackIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeGeofences(PendingIntent pendingIntent, LocationClient.OnRemoveGeofencesResultListener listener) {
        b bVar;
        bT();
        fq.b(pendingIntent, "PendingIntent must be specified.");
        fq.b(listener, "OnRemoveGeofencesResultListener not provided.");
        if (listener == null) {
            bVar = null;
        } else {
            try {
                bVar = new b(listener, this);
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
        eM().a(pendingIntent, bVar, getContext().getPackageName());
    }

    public void removeGeofences(List<String> geofenceRequestIds, LocationClient.OnRemoveGeofencesResultListener listener) {
        b bVar;
        bT();
        fq.b(geofenceRequestIds != null && geofenceRequestIds.size() > 0, "geofenceRequestIds can't be null nor empty.");
        fq.b(listener, "OnRemoveGeofencesResultListener not provided.");
        String[] strArr = (String[]) geofenceRequestIds.toArray(new String[0]);
        if (listener == null) {
            bVar = null;
        } else {
            try {
                bVar = new b(listener, this);
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
        eM().a(strArr, bVar, getContext().getPackageName());
    }

    public void removeLocationUpdates(PendingIntent callbackIntent) {
        this.Oq.removeLocationUpdates(callbackIntent);
    }

    public void removeLocationUpdates(LocationListener listener) {
        this.Oq.removeLocationUpdates(listener);
    }

    public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
        bT();
        fq.f(callbackIntent);
        fq.b(detectionIntervalMillis >= 0, "detectionIntervalMillis must be >= 0");
        try {
            eM().a(detectionIntervalMillis, true, callbackIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
        this.Oq.requestLocationUpdates(request, callbackIntent);
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener) {
        requestLocationUpdates(request, listener, null);
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
        synchronized (this.Oq) {
            this.Oq.requestLocationUpdates(request, listener, looper);
        }
    }

    public void setMockLocation(Location mockLocation) {
        this.Oq.setMockLocation(mockLocation);
    }

    public void setMockMode(boolean isMockMode) {
        this.Oq.setMockMode(isMockMode);
    }
}
