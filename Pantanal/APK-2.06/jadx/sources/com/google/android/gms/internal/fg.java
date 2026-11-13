package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class fg {
    private final b Do;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> Dp = new ArrayList<>();
    final ArrayList<GoogleApiClient.ConnectionCallbacks> Dq = new ArrayList<>();
    private boolean Dr = false;
    private final ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> Ds = new ArrayList<>();
    private final Handler mHandler;

    final class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what != 1) {
                Log.wtf("GmsClientEvents", "Don't know how to handle this message.");
                return;
            }
            synchronized (fg.this.Dp) {
                if (fg.this.Do.em() && fg.this.Do.isConnected() && fg.this.Dp.contains(msg.obj)) {
                    ((GoogleApiClient.ConnectionCallbacks) msg.obj).onConnected(fg.this.Do.dG());
                }
            }
        }
    }

    public interface b {
        Bundle dG();

        boolean em();

        boolean isConnected();
    }

    public fg(Context context, Looper looper, b bVar) {
        this.Do = bVar;
        this.mHandler = new a(looper);
    }

    public void O(int i) {
        this.mHandler.removeMessages(1);
        synchronized (this.Dp) {
            this.Dr = true;
            for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : new ArrayList(this.Dp)) {
                if (!this.Do.em()) {
                    break;
                } else if (this.Dp.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.Dr = false;
        }
    }

    public void a(ConnectionResult connectionResult) {
        this.mHandler.removeMessages(1);
        synchronized (this.Ds) {
            for (GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener : new ArrayList(this.Ds)) {
                if (!this.Do.em()) {
                    return;
                }
                if (this.Ds.contains(onConnectionFailedListener)) {
                    onConnectionFailedListener.onConnectionFailed(connectionResult);
                }
            }
        }
    }

    public void b(Bundle bundle) {
        synchronized (this.Dp) {
            fq.x(!this.Dr);
            this.mHandler.removeMessages(1);
            this.Dr = true;
            fq.x(this.Dq.size() == 0);
            for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : new ArrayList(this.Dp)) {
                if (!this.Do.em() || !this.Do.isConnected()) {
                    break;
                } else if (!this.Dq.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.Dq.clear();
            this.Dr = false;
        }
    }

    protected void bV() {
        synchronized (this.Dp) {
            b(this.Do.dG());
        }
    }

    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks listener) {
        boolean zContains;
        fq.f(listener);
        synchronized (this.Dp) {
            zContains = this.Dp.contains(listener);
        }
        return zContains;
    }

    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        boolean zContains;
        fq.f(listener);
        synchronized (this.Ds) {
            zContains = this.Ds.contains(listener);
        }
        return zContains;
    }

    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        fq.f(listener);
        synchronized (this.Dp) {
            if (this.Dp.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + listener + " is already registered");
            } else {
                this.Dp.add(listener);
            }
        }
        if (this.Do.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, listener));
        }
    }

    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        fq.f(listener);
        synchronized (this.Ds) {
            if (this.Ds.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + listener + " is already registered");
            } else {
                this.Ds.add(listener);
            }
        }
    }

    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        fq.f(listener);
        synchronized (this.Dp) {
            if (this.Dp != null) {
                if (!this.Dp.remove(listener)) {
                    Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + listener + " not found");
                } else if (this.Dr) {
                    this.Dq.add(listener);
                }
            }
        }
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        fq.f(listener);
        synchronized (this.Ds) {
            if (this.Ds != null && !this.Ds.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + listener + " not found");
            }
        }
    }
}
