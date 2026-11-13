package com.google.android.gms.analytics;

/* JADX INFO: loaded from: classes.dex */
class z implements ad {
    private final long vm;
    private final int vn;
    private double vo;
    private long vp;
    private final Object vq;
    private final String vr;

    public z(int i, long j, String str) {
        this.vq = new Object();
        this.vn = i;
        this.vo = this.vn;
        this.vm = j;
        this.vr = str;
    }

    public z(String str) {
        this(60, 2000L, str);
    }

    @Override // com.google.android.gms.analytics.ad
    public boolean cS() {
        boolean z;
        synchronized (this.vq) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.vo < this.vn) {
                double d = (jCurrentTimeMillis - this.vp) / this.vm;
                if (d > 0.0d) {
                    this.vo = Math.min(this.vn, d + this.vo);
                }
            }
            this.vp = jCurrentTimeMillis;
            if (this.vo >= 1.0d) {
                this.vo -= 1.0d;
                z = true;
            } else {
                aa.z("Excessive " + this.vr + " detected; call ignored.");
                z = false;
            }
        }
        return z;
    }
}
