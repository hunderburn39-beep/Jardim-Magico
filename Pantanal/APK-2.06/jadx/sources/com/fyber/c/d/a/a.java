package com.fyber.c.d.a;

import com.fyber.utils.FyberLogger;
import com.fyber.utils.n;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: compiled from: BufferingHelper.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private final InterfaceC0021a a;
    private long e;
    private long f;
    private long i;
    private long j;
    private long b = 0;
    private boolean c = false;
    private int d = 0;
    private int g = 0;
    private long h = 0;
    private boolean k = false;

    /* JADX INFO: renamed from: com.fyber.c.d.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: BufferingHelper.java */
    public interface InterfaceC0021a {
        void a(boolean z);
    }

    public a(InterfaceC0021a interfaceC0021a) {
        if (interfaceC0021a == null) {
            throw new IllegalArgumentException("FybBufferingStateChangedListener is required");
        }
        this.a = interfaceC0021a;
        this.e = Calendar.getInstance().getTimeInMillis();
    }

    public final void a(long j) {
        this.j = j;
    }

    public final void a() {
        this.f = Calendar.getInstance().getTimeInMillis() - this.e;
    }

    public final boolean a(long j, boolean z, boolean z2) {
        if (j == this.j) {
            a(false, j, this.j);
            return this.c;
        }
        if (!z && j == this.b && !this.k) {
            if (this.d == this.g) {
                if (!z2) {
                    this.d = 0;
                    a(false, j, this.j);
                }
            } else {
                this.d++;
            }
            return this.c;
        }
        this.k = z2;
        if (this.k) {
            return this.c;
        }
        if (j >= this.b + 430 || j < 430) {
            a(false, j, this.j);
        } else {
            a(true, j, this.j);
        }
        this.b = j;
        return this.c;
    }

    private void a(boolean z, long j, long j2) {
        if ((!this.c) == z) {
            Object[] objArr = new Object[1];
            objArr[0] = z ? "" : " NOT";
            FyberLogger.d("BufferingHelper", String.format("changing to%s buffering", objArr));
            if (n.a()) {
                if (z) {
                    this.e = Calendar.getInstance().getTimeInMillis();
                    this.i = j - this.h;
                    FyberLogger.d("BufferingHelper", "mTotalPlayedTimeSinceBuffering = " + this.i);
                    long j3 = (this.i * 100) / j2;
                    FyberLogger.d("BufferingHelper", "percentage = " + j3);
                    FyberLogger.d("BufferingHelper", String.format(Locale.ENGLISH, "buffering %d percent took %d ms ", Long.valueOf(j3), Long.valueOf(this.f)));
                    if (j3 > 0) {
                        long j4 = (15 * this.f) / j3;
                        FyberLogger.d("BufferingHelper", String.format(Locale.ENGLISH, "Required buffering time for %d percent %d", 15, Long.valueOf(j4)));
                        this.g = (int) (j4 / 500);
                        FyberLogger.d("BufferingHelper", "mBufferCounter - " + this.g);
                    }
                } else {
                    this.f = Calendar.getInstance().getTimeInMillis() - this.e;
                    this.h = j;
                    this.b = j - 1000;
                }
            }
            this.a.a(z);
            this.c = z;
        }
    }
}
