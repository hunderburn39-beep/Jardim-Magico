package com.tapjoy.internal;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
final class dy extends dk {
    private static final int[] a;
    private final int d;
    private final dk e;
    private final dk f;
    private final int g;
    private final int h;
    private int i;

    /* synthetic */ dy(dk dkVar, dk dkVar2, byte b2) {
        this(dkVar, dkVar2);
    }

    static {
        int i = 1;
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        while (i > 0) {
            arrayList.add(Integer.valueOf(i));
            int i3 = i2 + i;
            i2 = i;
            i = i3;
        }
        arrayList.add(Integer.MAX_VALUE);
        a = new int[arrayList.size()];
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 < a.length) {
                a[i5] = ((Integer) arrayList.get(i5)).intValue();
                i4 = i5 + 1;
            } else {
                return;
            }
        }
    }

    private dy(dk dkVar, dk dkVar2) {
        this.i = 0;
        this.e = dkVar;
        this.f = dkVar2;
        this.g = dkVar.a();
        this.d = this.g + dkVar2.a();
        this.h = Math.max(dkVar.h(), dkVar2.h()) + 1;
    }

    static dk a(dk dkVar, dk dkVar2) {
        byte b2 = 0;
        dy dyVar = dkVar instanceof dy ? (dy) dkVar : null;
        if (dkVar2.a() != 0) {
            if (dkVar.a() == 0) {
                return dkVar2;
            }
            int iA = dkVar.a() + dkVar2.a();
            if (iA < 128) {
                return b(dkVar, dkVar2);
            }
            if (dyVar != null && dyVar.f.a() + dkVar2.a() < 128) {
                return new dy(dyVar.e, b(dyVar.f, dkVar2));
            }
            if (dyVar != null && dyVar.e.h() > dyVar.f.h() && dyVar.h > dkVar2.h()) {
                return new dy(dyVar.e, new dy(dyVar.f, dkVar2));
            }
            if (iA >= a[Math.max(dkVar.h(), dkVar2.h()) + 1]) {
                return new dy(dkVar, dkVar2);
            }
            a aVar = new a(b2);
            aVar.a(dkVar);
            aVar.a(dkVar2);
            dk dyVar2 = (dk) aVar.a.pop();
            while (!aVar.a.isEmpty()) {
                dyVar2 = new dy((dk) aVar.a.pop(), dyVar2, b2);
            }
            return dyVar2;
        }
        return dkVar;
    }

    private static dt b(dk dkVar, dk dkVar2) {
        int iA = dkVar.a();
        int iA2 = dkVar2.a();
        byte[] bArr = new byte[iA + iA2];
        dkVar.b(bArr, 0, 0, iA);
        dkVar2.b(bArr, 0, iA, iA2);
        return new dt(bArr);
    }

    @Override // com.tapjoy.internal.dk
    public final int a() {
        return this.d;
    }

    @Override // com.tapjoy.internal.dk
    protected final int h() {
        return this.h;
    }

    @Override // com.tapjoy.internal.dk
    protected final boolean i() {
        return this.d >= a[this.h];
    }

    @Override // com.tapjoy.internal.dk
    protected final void a(byte[] bArr, int i, int i2, int i3) {
        if (i + i3 <= this.g) {
            this.e.a(bArr, i, i2, i3);
        } else {
            if (i >= this.g) {
                this.f.a(bArr, i - this.g, i2, i3);
                return;
            }
            int i4 = this.g - i;
            this.e.a(bArr, i, i2, i4);
            this.f.a(bArr, 0, i2 + i4, i3 - i4);
        }
    }

    @Override // com.tapjoy.internal.dk
    final void a(OutputStream outputStream, int i, int i2) {
        if (i + i2 <= this.g) {
            this.e.a(outputStream, i, i2);
        } else {
            if (i >= this.g) {
                this.f.a(outputStream, i - this.g, i2);
                return;
            }
            int i3 = this.g - i;
            this.e.a(outputStream, i, i3);
            this.f.a(outputStream, 0, i2 - i3);
        }
    }

    @Override // com.tapjoy.internal.dk
    public final String b(String str) {
        return new String(d(), str);
    }

    @Override // com.tapjoy.internal.dk
    public final boolean f() {
        return this.f.a(this.e.a(0, 0, this.g), 0, this.f.a()) == 0;
    }

    @Override // com.tapjoy.internal.dk
    protected final int a(int i, int i2, int i3) {
        if (i2 + i3 <= this.g) {
            return this.e.a(i, i2, i3);
        }
        if (i2 >= this.g) {
            return this.f.a(i, i2 - this.g, i3);
        }
        int i4 = this.g - i2;
        return this.f.a(this.e.a(i, i2, i4), 0, i3 - i4);
    }

    public final boolean equals(Object other) {
        int iJ;
        byte b2 = 0;
        if (other == this) {
            return true;
        }
        if (!(other instanceof dk)) {
            return false;
        }
        dk dkVar = (dk) other;
        if (this.d != dkVar.a()) {
            return false;
        }
        if (this.d == 0) {
            return true;
        }
        if (this.i != 0 && (iJ = dkVar.j()) != 0 && this.i != iJ) {
            return false;
        }
        b bVar = new b(this, b2);
        dt dtVar = (dt) bVar.next();
        b bVar2 = new b(dkVar, b2);
        dt dtVar2 = (dt) bVar2.next();
        int i = 0;
        dt dtVar3 = dtVar;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int iA = dtVar3.a() - i2;
            int iA2 = dtVar2.a() - i;
            int iMin = Math.min(iA, iA2);
            if (!(i2 == 0 ? dtVar3.a(dtVar2, i, iMin) : dtVar2.a(dtVar3, i2, iMin))) {
                return false;
            }
            i3 += iMin;
            if (i3 >= this.d) {
                if (i3 == this.d) {
                    return true;
                }
                throw new IllegalStateException();
            }
            if (iMin == iA) {
                dtVar3 = (dt) bVar.next();
                i2 = 0;
            } else {
                i2 += iMin;
            }
            if (iMin == iA2) {
                dtVar2 = (dt) bVar2.next();
                i = 0;
            } else {
                i += iMin;
            }
        }
    }

    @Override // com.tapjoy.internal.dk
    public final int hashCode() {
        int iB = this.i;
        if (iB == 0) {
            iB = b(this.d, 0, this.d);
            if (iB == 0) {
                iB = 1;
            }
            this.i = iB;
        }
        return iB;
    }

    @Override // com.tapjoy.internal.dk
    protected final int j() {
        return this.i;
    }

    @Override // com.tapjoy.internal.dk
    protected final int b(int i, int i2, int i3) {
        if (i2 + i3 <= this.g) {
            return this.e.b(i, i2, i3);
        }
        if (i2 >= this.g) {
            return this.f.b(i, i2 - this.g, i3);
        }
        int i4 = this.g - i2;
        return this.f.b(this.e.b(i, i2, i4), 0, i3 - i4);
    }

    static class a {
        final Stack a;

        private a() {
            this.a = new Stack();
        }

        /* synthetic */ a(byte b) {
            this();
        }

        final void a(dk dkVar) {
            byte b = 0;
            dk dkVar2 = dkVar;
            while (!dkVar2.i()) {
                if (dkVar2 instanceof dy) {
                    dy dyVar = (dy) dkVar2;
                    a(dyVar.e);
                    dkVar2 = dyVar.f;
                } else {
                    String strValueOf = String.valueOf(String.valueOf(dkVar2.getClass()));
                    throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 49).append("Has a new type of ByteString been created? Found ").append(strValueOf).toString());
                }
            }
            int iA = a(dkVar2.a());
            int i = dy.a[iA + 1];
            if (!this.a.isEmpty() && ((dk) this.a.peek()).a() < i) {
                int i2 = dy.a[iA];
                dk dyVar2 = (dk) this.a.pop();
                while (!this.a.isEmpty() && ((dk) this.a.peek()).a() < i2) {
                    dyVar2 = new dy((dk) this.a.pop(), dyVar2, b);
                }
                dy dyVar3 = new dy(dyVar2, dkVar2, b);
                while (!this.a.isEmpty()) {
                    if (((dk) this.a.peek()).a() >= dy.a[a(dyVar3.a()) + 1]) {
                        break;
                    } else {
                        dyVar3 = new dy((dk) this.a.pop(), dyVar3, b);
                    }
                }
                this.a.push(dyVar3);
                return;
            }
            this.a.push(dkVar2);
        }

        private static int a(int i) {
            int iBinarySearch = Arrays.binarySearch(dy.a, i);
            if (iBinarySearch < 0) {
                return (-(iBinarySearch + 1)) - 1;
            }
            return iBinarySearch;
        }
    }

    static class b implements Iterator {
        private final Stack a;
        private dt b;

        /* synthetic */ b(dk dkVar, byte b) {
            this(dkVar);
        }

        private b(dk dkVar) {
            this.a = new Stack();
            this.b = a(dkVar);
        }

        private dt a(dk dkVar) {
            dk dkVar2 = dkVar;
            while (dkVar2 instanceof dy) {
                dy dyVar = (dy) dkVar2;
                this.a.push(dyVar);
                dkVar2 = dyVar.e;
            }
            return (dt) dkVar2;
        }

        private dt b() {
            while (!this.a.isEmpty()) {
                dt dtVarA = a(((dy) this.a.pop()).f);
                if (!(dtVarA.a() == 0)) {
                    return dtVarA;
                }
            }
            return null;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.b != null;
        }

        @Override // java.util.Iterator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final dt next() {
            if (this.b == null) {
                throw new NoSuchElementException();
            }
            dt dtVar = this.b;
            this.b = b();
            return dtVar;
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.tapjoy.internal.dk, java.lang.Iterable
    /* JADX INFO: renamed from: c */
    public final dk.a iterator() {
        return new c(this, (byte) 0);
    }

    class c implements dk.a {
        int a;
        private final b c;
        private dk.a d;

        /* synthetic */ c(dy dyVar, byte b) {
            this();
        }

        private c() {
            this.c = new b(dy.this, (byte) 0);
            this.d = this.c.next().iterator();
            this.a = dy.this.a();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.a > 0;
        }

        @Override // com.tapjoy.internal.dk.a
        public final byte a() {
            if (!this.d.hasNext()) {
                this.d = this.c.next().iterator();
            }
            this.a--;
            return this.d.a();
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator
        public final /* synthetic */ Object next() {
            return Byte.valueOf(a());
        }
    }
}
