package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class fh implements Handler.Callback {
    private static final Object Du = new Object();
    private static fh Dv;
    private final HashMap<String, a> Dw = new HashMap<>();
    private final Context lp;
    private final Handler mHandler;

    final class a {
        private boolean DA;
        private IBinder DB;
        private ComponentName DC;
        private final String Dx;
        private final ServiceConnectionC0064a Dy = new ServiceConnectionC0064a();
        private final HashSet<ff<?>.f> Dz = new HashSet<>();
        private int mState = 0;

        /* JADX INFO: renamed from: com.google.android.gms.internal.fh$a$a, reason: collision with other inner class name */
        public class ServiceConnectionC0064a implements ServiceConnection {
            public ServiceConnectionC0064a() {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName component, IBinder binder) {
                synchronized (fh.this.Dw) {
                    a.this.DB = binder;
                    a.this.DC = component;
                    Iterator it = a.this.Dz.iterator();
                    while (it.hasNext()) {
                        ((ff.f) it.next()).onServiceConnected(component, binder);
                    }
                    a.this.mState = 1;
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName component) {
                synchronized (fh.this.Dw) {
                    a.this.DB = null;
                    a.this.DC = component;
                    Iterator it = a.this.Dz.iterator();
                    while (it.hasNext()) {
                        ((ff.f) it.next()).onServiceDisconnected(component);
                    }
                    a.this.mState = 2;
                }
            }
        }

        public a(String str) {
            this.Dx = str;
        }

        public void a(ff<?>.f fVar) {
            this.Dz.add(fVar);
        }

        public void b(ff<?>.f fVar) {
            this.Dz.remove(fVar);
        }

        public boolean c(ff<?>.f fVar) {
            return this.Dz.contains(fVar);
        }

        public ServiceConnectionC0064a eP() {
            return this.Dy;
        }

        public String eQ() {
            return this.Dx;
        }

        public boolean eR() {
            return this.Dz.isEmpty();
        }

        public IBinder getBinder() {
            return this.DB;
        }

        public ComponentName getComponentName() {
            return this.DC;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.DA;
        }

        public void y(boolean z) {
            this.DA = z;
        }
    }

    private fh(Context context) {
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.lp = context.getApplicationContext();
    }

    public static fh x(Context context) {
        synchronized (Du) {
            if (Dv == null) {
                Dv = new fh(context.getApplicationContext());
            }
        }
        return Dv;
    }

    public boolean a(String str, ff<?>.f fVar) {
        boolean zIsBound;
        synchronized (this.Dw) {
            a aVar = this.Dw.get(str);
            if (aVar != null) {
                this.mHandler.removeMessages(0, aVar);
                if (!aVar.c(fVar)) {
                    aVar.a(fVar);
                    switch (aVar.getState()) {
                        case 1:
                            fVar.onServiceConnected(aVar.getComponentName(), aVar.getBinder());
                            break;
                        case 2:
                            aVar.y(this.lp.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), aVar.eP(), 129));
                            break;
                    }
                } else {
                    throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  startServiceAction=" + str);
                }
            } else {
                aVar = new a(str);
                aVar.a(fVar);
                aVar.y(this.lp.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), aVar.eP(), 129));
                this.Dw.put(str, aVar);
            }
            zIsBound = aVar.isBound();
        }
        return zIsBound;
    }

    public void b(String str, ff<?>.f fVar) {
        synchronized (this.Dw) {
            a aVar = this.Dw.get(str);
            if (aVar == null) {
                throw new IllegalStateException("Nonexistent connection status for service action: " + str);
            }
            if (!aVar.c(fVar)) {
                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  startServiceAction=" + str);
            }
            aVar.b(fVar);
            if (aVar.eR()) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, aVar), 5000L);
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                a aVar = (a) msg.obj;
                synchronized (this.Dw) {
                    if (aVar.eR()) {
                        this.lp.unbindService(aVar.eP());
                        this.Dw.remove(aVar.eQ());
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }
}
