package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class cx extends cw {
    private static cx aam;
    private static final Object sF = new Object();
    private Context aac;
    private at aad;
    private volatile ar aae;
    private bn aak;
    private Handler handler;
    private int aaf = 1800000;
    private boolean aag = true;
    private boolean aah = false;
    private boolean connected = true;
    private boolean aai = true;
    private au aaj = new au() { // from class: com.google.android.gms.tagmanager.cx.1
        @Override // com.google.android.gms.tagmanager.au
        public void r(boolean z) {
            cx.this.a(z, cx.this.connected);
        }
    };
    private boolean aal = false;

    private cx() {
    }

    private void cj() {
        this.aak = new bn(this);
        this.aak.o(this.aac);
    }

    private void ck() {
        this.handler = new Handler(this.aac.getMainLooper(), new Handler.Callback() { // from class: com.google.android.gms.tagmanager.cx.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message msg) {
                if (1 == msg.what && cx.sF.equals(msg.obj)) {
                    cx.this.bW();
                    if (cx.this.aaf > 0 && !cx.this.aal) {
                        cx.this.handler.sendMessageDelayed(cx.this.handler.obtainMessage(1, cx.sF), cx.this.aaf);
                    }
                }
                return true;
            }
        });
        if (this.aaf > 0) {
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1, sF), this.aaf);
        }
    }

    public static cx lG() {
        if (aam == null) {
            aam = new cx();
        }
        return aam;
    }

    synchronized void a(Context context, ar arVar) {
        if (this.aac == null) {
            this.aac = context.getApplicationContext();
            if (this.aae == null) {
                this.aae = arVar;
            }
        }
    }

    synchronized void a(boolean z, boolean z2) {
        if (this.aal != z || this.connected != z2) {
            if ((z || !z2) && this.aaf > 0) {
                this.handler.removeMessages(1, sF);
            }
            if (!z && z2 && this.aaf > 0) {
                this.handler.sendMessageDelayed(this.handler.obtainMessage(1, sF), this.aaf);
            }
            bh.y("PowerSaveMode " + ((z || !z2) ? "initiated." : "terminated."));
            this.aal = z;
            this.connected = z2;
        }
    }

    public synchronized void bW() {
        if (this.aah) {
            this.aae.a(new Runnable() { // from class: com.google.android.gms.tagmanager.cx.3
                @Override // java.lang.Runnable
                public void run() {
                    cx.this.aad.bW();
                }
            });
        } else {
            bh.y("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.aag = true;
        }
    }

    @Override // com.google.android.gms.tagmanager.cw
    synchronized void cm() {
        if (!this.aal && this.connected && this.aaf > 0) {
            this.handler.removeMessages(1, sF);
            this.handler.sendMessage(this.handler.obtainMessage(1, sF));
        }
    }

    synchronized at lH() {
        if (this.aad == null) {
            if (this.aac == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.aad = new ca(this.aaj, this.aac);
        }
        if (this.handler == null) {
            ck();
        }
        this.aah = true;
        if (this.aag) {
            bW();
            this.aag = false;
        }
        if (this.aak == null && this.aai) {
            cj();
        }
        return this.aad;
    }

    @Override // com.google.android.gms.tagmanager.cw
    synchronized void s(boolean z) {
        a(this.aal, z);
    }
}
