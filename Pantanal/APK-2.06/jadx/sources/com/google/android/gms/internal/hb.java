package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class hb {
    private final hf<ha> Ok;
    private ContentProviderClient Ol = null;
    private boolean Om = false;
    private HashMap<LocationListener, b> On = new HashMap<>();
    private final Context mContext;

    private static class a extends Handler {
        private final LocationListener Oo;

        public a(LocationListener locationListener) {
            this.Oo = locationListener;
        }

        public a(LocationListener locationListener, Looper looper) {
            super(looper);
            this.Oo = locationListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.Oo.onLocationChanged(new Location((Location) msg.obj));
                    break;
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
                    break;
            }
        }
    }

    private static class b extends com.google.android.gms.location.a.AbstractBinderC0085a {
        private Handler Op;

        b(LocationListener locationListener, Looper looper) {
            this.Op = looper == null ? new a(locationListener) : new a(locationListener, looper);
        }

        @Override // com.google.android.gms.location.a
        public void onLocationChanged(Location location) {
            if (this.Op == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message messageObtain = Message.obtain();
            messageObtain.what = 1;
            messageObtain.obj = location;
            this.Op.sendMessage(messageObtain);
        }

        public void release() {
            this.Op = null;
        }
    }

    public hb(Context context, hf<ha> hfVar) {
        this.mContext = context;
        this.Ok = hfVar;
    }

    public Location getLastLocation() {
        this.Ok.bT();
        try {
            return ((ha) this.Ok.eM()).aW(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void hQ() {
        if (this.Om) {
            setMockMode(false);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.On) {
                for (b bVar : this.On.values()) {
                    if (bVar != null) {
                        ((ha) this.Ok.eM()).a(bVar);
                    }
                }
                this.On.clear();
            }
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(PendingIntent callbackIntent) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).a(callbackIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(LocationListener listener) {
        this.Ok.bT();
        fq.b(listener, "Invalid null listener");
        synchronized (this.On) {
            b bVarRemove = this.On.remove(listener);
            if (this.Ol != null && this.On.isEmpty()) {
                this.Ol.release();
                this.Ol = null;
            }
            if (bVarRemove != null) {
                bVarRemove.release();
                try {
                    ((ha) this.Ok.eM()).a(bVarRemove);
                } catch (RemoteException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).a(request, callbackIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
        this.Ok.bT();
        if (looper == null) {
            fq.b(Looper.myLooper(), "Can't create handler inside thread that has not called Looper.prepare()");
        }
        synchronized (this.On) {
            b bVar = this.On.get(listener);
            b bVar2 = bVar == null ? new b(listener, looper) : bVar;
            this.On.put(listener, bVar2);
            try {
                ((ha) this.Ok.eM()).a(request, bVar2, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void setMockLocation(Location mockLocation) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).setMockLocation(mockLocation);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setMockMode(boolean isMockMode) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).setMockMode(isMockMode);
            this.Om = isMockMode;
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
