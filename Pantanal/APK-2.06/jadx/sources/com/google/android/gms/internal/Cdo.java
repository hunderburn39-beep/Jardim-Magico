package com.google.android.gms.internal;

/* JADX INFO: renamed from: com.google.android.gms.internal.do, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public abstract class Cdo {
    private final Runnable kW = new Runnable() { // from class: com.google.android.gms.internal.do.1
        @Override // java.lang.Runnable
        public final void run() {
            Cdo.this.qY = Thread.currentThread();
            Cdo.this.aY();
        }
    };
    private volatile Thread qY;

    public abstract void aY();

    public final void cancel() {
        onStop();
        if (this.qY != null) {
            this.qY.interrupt();
        }
    }

    public abstract void onStop();

    public final void start() {
        dp.execute(this.kW);
    }
}
