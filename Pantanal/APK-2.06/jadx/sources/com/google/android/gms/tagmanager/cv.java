package com.google.android.gms.tagmanager;

/* JADX INFO: loaded from: classes.dex */
class cv implements cf {
    private long aab;
    private final long vm;
    private final int vn;
    private double vo;
    private final Object vq;

    public cv() {
        this(60, 2000L);
    }

    public cv(int i, long j) {
        this.vq = new Object();
        this.vn = i;
        this.vo = this.vn;
        this.vm = j;
    }

    @Override // com.google.android.gms.tagmanager.cf
    public boolean cS() {
        boolean z;
        synchronized (this.vq) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.vo < this.vn) {
                double d = (jCurrentTimeMillis - this.aab) / this.vm;
                if (d > 0.0d) {
                    this.vo = Math.min(this.vn, d + this.vo);
                }
            }
            this.aab = jCurrentTimeMillis;
            if (this.vo >= 1.0d) {
                this.vo -= 1.0d;
                z = true;
            } else {
                bh.z("No more tokens available.");
                z = false;
            }
        }
        return z;
    }
}
