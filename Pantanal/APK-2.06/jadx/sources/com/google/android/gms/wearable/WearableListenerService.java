package com.google.android.gms.wearable;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.kh;
import com.google.android.gms.internal.ki;
import com.google.android.gms.internal.kk;

/* JADX INFO: loaded from: classes.dex */
public abstract class WearableListenerService extends Service {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private IBinder DB;
    private volatile int adu = -1;
    private String adv;
    private Handler adw;

    private class a extends kh.a {
        private a() {
        }

        @Override // com.google.android.gms.internal.kh
        public void M(final DataHolder dataHolder) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onDataItemChanged: " + WearableListenerService.this.adv + ": " + dataHolder);
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable() { // from class: com.google.android.gms.wearable.WearableListenerService.a.1
                @Override // java.lang.Runnable
                public void run() {
                    b bVar = new b(dataHolder);
                    try {
                        WearableListenerService.this.onDataChanged(bVar);
                    } finally {
                        bVar.close();
                    }
                }
            });
        }

        @Override // com.google.android.gms.internal.kh
        public void a(final ki kiVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onMessageReceived: " + kiVar);
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable() { // from class: com.google.android.gms.wearable.WearableListenerService.a.2
                @Override // java.lang.Runnable
                public void run() {
                    WearableListenerService.this.onMessageReceived(kiVar);
                }
            });
        }

        @Override // com.google.android.gms.internal.kh
        public void a(final kk kkVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerConnected: " + WearableListenerService.this.adv + ": " + kkVar);
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable() { // from class: com.google.android.gms.wearable.WearableListenerService.a.3
                @Override // java.lang.Runnable
                public void run() {
                    WearableListenerService.this.onPeerConnected(kkVar);
                }
            });
        }

        @Override // com.google.android.gms.internal.kh
        public void b(final kk kkVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerDisconnected: " + WearableListenerService.this.adv + ": " + kkVar);
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable() { // from class: com.google.android.gms.wearable.WearableListenerService.a.4
                @Override // java.lang.Runnable
                public void run() {
                    WearableListenerService.this.onPeerDisconnected(kkVar);
                }
            });
        }
    }

    private boolean cM(int i) {
        String[] packagesForUid = getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        for (String str : packagesForUid) {
            if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void md() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (callingUid == this.adu) {
            return;
        }
        if (!GooglePlayServicesUtil.b(getPackageManager(), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE) || !cM(callingUid)) {
            throw new SecurityException("Caller is not GooglePlayServices");
        }
        this.adu = callingUid;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (BIND_LISTENER_INTENT_ACTION.equals(intent.getAction())) {
            return this.DB;
        }
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", "onCreate: " + getPackageName());
        }
        this.adv = getPackageName();
        HandlerThread handlerThread = new HandlerThread("WearableListenerService");
        handlerThread.start();
        this.adw = new Handler(handlerThread.getLooper());
        this.DB = new a();
    }

    public void onDataChanged(b dataEvents) {
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.adw.getLooper().quit();
        super.onDestroy();
    }

    public void onMessageReceived(e messageEvent) {
    }

    public void onPeerConnected(f peer) {
    }

    public void onPeerDisconnected(f peer) {
    }
}
