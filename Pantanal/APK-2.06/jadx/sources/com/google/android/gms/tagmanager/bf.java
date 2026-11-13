package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gl;

/* JADX INFO: loaded from: classes.dex */
class bf implements cf {
    private final gl Wv;
    private final long Yx;
    private final long vm;
    private final int vn;
    private double vo;
    private long vp;
    private final Object vq = new Object();
    private final String vr;

    public bf(int i, long j, long j2, String str, gl glVar) {
        this.vn = i;
        this.vo = this.vn;
        this.vm = j;
        this.Yx = j2;
        this.vr = str;
        this.Wv = glVar;
    }

    @Override // com.google.android.gms.tagmanager.cf
    public boolean cS() {
        boolean z = false;
        synchronized (this.vq) {
            long jCurrentTimeMillis = this.Wv.currentTimeMillis();
            if (jCurrentTimeMillis - this.vp < this.Yx) {
                bh.z("Excessive " + this.vr + " detected; call ignored.");
            } else {
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
                    bh.z("Excessive " + this.vr + " detected; call ignored.");
                }
            }
        }
        return z;
    }
}
