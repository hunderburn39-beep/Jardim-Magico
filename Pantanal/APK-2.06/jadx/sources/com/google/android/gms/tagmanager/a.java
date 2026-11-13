package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.tapjoy.TapjoyConstants;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class a {
    private static a Wx;
    private static Object sf = new Object();
    private volatile long Ws;
    private volatile long Wt;
    private volatile long Wu;
    private final gl Wv;
    private InterfaceC0130a Ww;
    private volatile boolean mClosed;
    private final Context mContext;
    private final Thread qY;
    private volatile AdvertisingIdClient.Info sh;

    /* JADX INFO: renamed from: com.google.android.gms.tagmanager.a$a, reason: collision with other inner class name */
    public interface InterfaceC0130a {
        AdvertisingIdClient.Info jW();
    }

    private a(Context context) {
        this(context, null, gn.ft());
    }

    a(Context context, InterfaceC0130a interfaceC0130a, gl glVar) {
        this.Ws = TapjoyConstants.PAID_APP_TIME;
        this.Wt = 30000L;
        this.mClosed = false;
        this.Ww = new InterfaceC0130a() { // from class: com.google.android.gms.tagmanager.a.1
            @Override // com.google.android.gms.tagmanager.a.InterfaceC0130a
            public AdvertisingIdClient.Info jW() {
                try {
                    return AdvertisingIdClient.getAdvertisingIdInfo(a.this.mContext);
                } catch (GooglePlayServicesNotAvailableException e) {
                    bh.z("GooglePlayServicesNotAvailableException getting Advertising Id Info");
                    return null;
                } catch (GooglePlayServicesRepairableException e2) {
                    bh.z("GooglePlayServicesRepairableException getting Advertising Id Info");
                    return null;
                } catch (IOException e3) {
                    bh.z("IOException getting Ad Id Info");
                    return null;
                } catch (IllegalStateException e4) {
                    bh.z("IllegalStateException getting Advertising Id Info");
                    return null;
                } catch (Exception e5) {
                    bh.z("Unknown exception. Could not get the Advertising Id Info.");
                    return null;
                }
            }
        };
        this.Wv = glVar;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (interfaceC0130a != null) {
            this.Ww = interfaceC0130a;
        }
        this.qY = new Thread(new Runnable() { // from class: com.google.android.gms.tagmanager.a.2
            @Override // java.lang.Runnable
            public void run() {
                a.this.jU();
            }
        });
    }

    static a E(Context context) {
        if (Wx == null) {
            synchronized (sf) {
                if (Wx == null) {
                    Wx = new a(context);
                    Wx.start();
                }
            }
        }
        return Wx;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jU() {
        Process.setThreadPriority(10);
        while (!this.mClosed) {
            try {
                this.sh = this.Ww.jW();
                Thread.sleep(this.Ws);
            } catch (InterruptedException e) {
                bh.x("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    private void jV() {
        if (this.Wv.currentTimeMillis() - this.Wu < this.Wt) {
            return;
        }
        interrupt();
        this.Wu = this.Wv.currentTimeMillis();
    }

    void interrupt() {
        this.qY.interrupt();
    }

    public boolean isLimitAdTrackingEnabled() {
        jV();
        if (this.sh == null) {
            return true;
        }
        return this.sh.isLimitAdTrackingEnabled();
    }

    public String jT() {
        jV();
        if (this.sh == null) {
            return null;
        }
        return this.sh.getId();
    }

    void start() {
        this.qY.start();
    }
}
