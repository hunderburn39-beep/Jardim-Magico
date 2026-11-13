package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class fd {
    public static final fd a = new fd(0, 0, 0, 0.0d);
    public final long b;
    public final long c;
    public final double d;
    public long e;
    private final long f;

    public fd(long j, long j2, long j3, double d) {
        this.f = j;
        this.b = j2;
        this.c = j3;
        this.d = d;
        this.e = j;
    }

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        fd fdVar = (fd) o;
        return this.f == fdVar.f && this.b == fdVar.b && this.c == fdVar.c && this.d == fdVar.d && this.e == fdVar.e;
    }
}
