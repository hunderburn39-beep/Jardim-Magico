package com.tapjoy.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public final class ey {
    public String a;
    public boolean b;

    public final boolean a(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if ("unknown".equals(Build.DEVICE)) {
                return false;
            }
            throw new IllegalStateException("Calling this from your main thread can lead to deadlock");
        }
        try {
            try {
                try {
                    a aVarA = a.a(context);
                    if (aVarA.a) {
                        throw new IllegalStateException();
                    }
                    aVarA.a = true;
                    ez ezVarA = ez.a.a((IBinder) aVarA.b.take());
                    this.a = ezVarA.a();
                    try {
                        this.b = ezVarA.a(true);
                    } catch (SecurityException e) {
                        new Object[1][0] = e;
                    }
                    try {
                        context.unbindService(aVarA);
                    } catch (IllegalArgumentException e2) {
                        new Object[1][0] = e2;
                    } catch (Exception e3) {
                    }
                    return true;
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            context.unbindService(null);
                        } catch (IllegalArgumentException e4) {
                            new Object[1][0] = e4;
                        } catch (Exception e5) {
                        }
                    }
                    throw th;
                }
            } catch (SecurityException e6) {
                if (0 != 0) {
                    try {
                        context.unbindService(null);
                    } catch (IllegalArgumentException e7) {
                        new Object[1][0] = e7;
                    } catch (Exception e8) {
                    }
                }
                return false;
            }
        } catch (RemoteException e9) {
            if (0 != 0) {
                try {
                    context.unbindService(null);
                } catch (IllegalArgumentException e10) {
                    new Object[1][0] = e10;
                } catch (Exception e11) {
                }
            }
            return false;
        } catch (IOException e12) {
            new Object[1][0] = e12;
            if (0 != 0) {
                try {
                    context.unbindService(null);
                } catch (IllegalArgumentException e13) {
                    new Object[1][0] = e13;
                } catch (Exception e14) {
                }
            }
            return false;
        } catch (InterruptedException e15) {
            new Object[1][0] = e15;
            if (0 != 0) {
                try {
                    context.unbindService(null);
                } catch (IllegalArgumentException e16) {
                    new Object[1][0] = e16;
                } catch (Exception e17) {
                }
            }
            return false;
        } catch (Exception e18) {
            if (0 != 0) {
                try {
                    context.unbindService(null);
                } catch (IllegalArgumentException e19) {
                    new Object[1][0] = e19;
                } catch (Exception e20) {
                }
            }
            return false;
        }
    }

    static class a implements ServiceConnection {
        boolean a = false;
        final BlockingQueue b = new LinkedBlockingQueue();

        private a() {
        }

        public static a a(Context context) throws IOException {
            if (ae.b(context.getPackageManager(), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE) < 4000000) {
                throw new IOException("Google Play services not available.");
            }
            a aVar = new a();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
            if (context.bindService(intent, aVar, 1)) {
                return aVar;
            }
            throw new IOException("Connection failure");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName name, IBinder service) {
            try {
                this.b.put(service);
            } catch (InterruptedException e) {
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName name) {
        }
    }
}
