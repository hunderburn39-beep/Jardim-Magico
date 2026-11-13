package com.tapjoy.internal;

import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class dk implements Iterable {
    public static final dk b;
    static final /* synthetic */ boolean c;

    public interface a extends Iterator {
        byte a();
    }

    public abstract int a();

    protected abstract int a(int i, int i2, int i3);

    abstract void a(OutputStream outputStream, int i, int i2);

    protected abstract void a(byte[] bArr, int i, int i2, int i3);

    protected abstract int b(int i, int i2, int i3);

    public abstract String b(String str);

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: c */
    public abstract a iterator();

    public abstract boolean f();

    protected abstract int h();

    public abstract int hashCode();

    protected abstract boolean i();

    protected abstract int j();

    static {
        c = !dk.class.desiredAssertionStatus();
        b = new dt(new byte[0]);
    }

    dk() {
    }

    public static dk a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new dt(bArr2);
    }

    public static dk a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static dk a(String str) {
        try {
            return new dt(str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public final dk a(dk dkVar) {
        int iA = a();
        int iA2 = dkVar.a();
        if (((long) iA) + ((long) iA2) >= 2147483647L) {
            throw new IllegalArgumentException(new StringBuilder(53).append("ByteString would be too long: ").append(iA).append("+").append(iA2).toString());
        }
        return dy.a(this, dkVar);
    }

    public static dk a(Iterable iterable) {
        Collection collection;
        if (!(iterable instanceof Collection)) {
            ArrayList arrayList = new ArrayList();
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add((dk) it.next());
            }
            collection = arrayList;
        } else {
            collection = (Collection) iterable;
        }
        if (collection.isEmpty()) {
            return b;
        }
        return a(collection.iterator(), collection.size());
    }

    private static dk a(Iterator it, int i) {
        if (!c && i <= 0) {
            throw new AssertionError();
        }
        if (i == 1) {
            return (dk) it.next();
        }
        int i2 = i >>> 1;
        return a(it, i2).a(a(it, i - i2));
    }

    public final void b(byte[] bArr, int i, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Source offset < 0: ").append(i).toString());
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Target offset < 0: ").append(i2).toString());
        }
        if (i3 < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(23).append("Length < 0: ").append(i3).toString());
        }
        if (i + i3 > a()) {
            throw new IndexOutOfBoundsException(new StringBuilder(34).append("Source end offset < 0: ").append(i + i3).toString());
        }
        if (i2 + i3 > bArr.length) {
            throw new IndexOutOfBoundsException(new StringBuilder(34).append("Target end offset < 0: ").append(i2 + i3).toString());
        }
        if (i3 > 0) {
            a(bArr, i, i2, i3);
        }
    }

    public final byte[] d() {
        int iA = a();
        if (iA == 0) {
            return dp.a;
        }
        byte[] bArr = new byte[iA];
        a(bArr, 0, 0, iA);
        return bArr;
    }

    public final String e() {
        try {
            return b(AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public static b g() {
        return new b();
    }

    public static final class b extends OutputStream {
        private static final byte[] a = new byte[0];
        private int d;
        private int f;
        private final int b = 128;
        private final ArrayList c = new ArrayList();
        private byte[] e = new byte[128];

        b() {
        }

        @Override // java.io.OutputStream
        public final synchronized void write(int b) {
            if (this.f == this.e.length) {
                a(1);
            }
            byte[] bArr = this.e;
            int i = this.f;
            this.f = i + 1;
            bArr[i] = (byte) b;
        }

        @Override // java.io.OutputStream
        public final synchronized void write(byte[] b, int offset, int length) {
            if (length <= this.e.length - this.f) {
                System.arraycopy(b, offset, this.e, this.f, length);
                this.f += length;
            } else {
                int length2 = this.e.length - this.f;
                System.arraycopy(b, offset, this.e, this.f, length2);
                int length3 = length - length2;
                a(length3);
                System.arraycopy(b, offset + length2, this.e, 0, length3);
                this.f = length3;
            }
        }

        public final synchronized dk a() {
            if (this.f < this.e.length) {
                if (this.f > 0) {
                    byte[] bArr = this.e;
                    int i = this.f;
                    byte[] bArr2 = new byte[i];
                    System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, i));
                    this.c.add(new dt(bArr2));
                }
            } else {
                this.c.add(new dt(this.e));
                this.e = a;
            }
            this.d += this.f;
            this.f = 0;
            return dk.a(this.c);
        }

        private synchronized int b() {
            return this.d + this.f;
        }

        public final String toString() {
            return String.format("<ByteString.Output@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(b()));
        }

        private void a(int i) {
            this.c.add(new dt(this.e));
            this.d += this.e.length;
            this.e = new byte[Math.max(this.b, Math.max(i, this.d >>> 1))];
            this.f = 0;
        }
    }

    public String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(a()));
    }
}
