package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
class as extends Thread implements ar {
    private static as Ya;
    private final LinkedBlockingQueue<Runnable> XZ;
    private volatile at Yb;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile boolean tx;

    private as(Context context) {
        super("GAThread");
        this.XZ = new LinkedBlockingQueue<>();
        this.tx = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static as H(Context context) {
        if (Ya == null) {
            Ya = new as(context);
        }
        return Ya;
    }

    private String a(Throwable th) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    @Override // com.google.android.gms.tagmanager.ar
    public void a(Runnable runnable) {
        this.XZ.add(runnable);
    }

    void b(final String str, final long j) {
        a(new Runnable() { // from class: com.google.android.gms.tagmanager.as.1
            @Override // java.lang.Runnable
            public void run() {
                if (as.this.Yb == null) {
                    cx cxVarLG = cx.lG();
                    cxVarLG.a(as.this.mContext, this);
                    as.this.Yb = cxVarLG.lH();
                }
                as.this.Yb.e(j, str);
            }
        });
    }

    @Override // com.google.android.gms.tagmanager.ar
    public void bC(String str) {
        b(str, System.currentTimeMillis());
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (!this.mClosed) {
            try {
                try {
                    Runnable runnableTake = this.XZ.take();
                    if (!this.tx) {
                        runnableTake.run();
                    }
                } catch (InterruptedException e) {
                    bh.x(e.toString());
                }
            } catch (Throwable th) {
                bh.w("Error on GAThread: " + a(th));
                bh.w("Google Analytics is shutting down.");
                this.tx = true;
            }
        }
    }
}
