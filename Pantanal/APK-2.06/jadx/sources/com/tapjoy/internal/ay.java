package com.tapjoy.internal;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public final class ay extends ax implements bb, Closeable, Flushable {
    private final bb a;
    private final LinkedList b = new LinkedList();
    private final LinkedList c = new LinkedList();
    private int d;
    private boolean e;

    public static ay a(bb bbVar) {
        return new ay(bbVar);
    }

    private ay(bb bbVar) {
        this.a = bbVar;
        this.d = bbVar.size();
        this.e = this.d == 0;
    }

    protected final void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        try {
            flush();
        } finally {
            if (this.a instanceof Closeable) {
                ((Closeable) this.a).close();
            }
        }
    }

    @Override // java.io.Flushable
    public final void flush() {
        if (!this.c.isEmpty()) {
            this.a.addAll(this.c);
            if (this.e) {
                this.b.addAll(this.c);
            }
            this.c.clear();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.d;
    }

    @Override // java.util.Queue
    public final boolean offer(Object obj) {
        this.c.add(obj);
        this.d++;
        return true;
    }

    @Override // java.util.Queue
    public final Object poll() {
        Object objRemove;
        if (this.d <= 0) {
            return null;
        }
        if (!this.b.isEmpty()) {
            objRemove = this.b.remove();
            this.a.b(1);
        } else if (this.e) {
            objRemove = this.c.remove();
        } else {
            objRemove = this.a.remove();
            if (this.d == this.c.size() + 1) {
                this.e = true;
            }
        }
        this.d--;
        return objRemove;
    }

    @Override // java.util.Queue
    public final Object peek() {
        if (this.d <= 0) {
            return null;
        }
        if (!this.b.isEmpty()) {
            return this.b.element();
        }
        if (this.e) {
            return this.c.element();
        }
        Object objPeek = this.a.peek();
        this.b.add(objPeek);
        if (this.d == this.b.size() + this.c.size()) {
            this.e = true;
            return objPeek;
        }
        return objPeek;
    }

    @Override // com.tapjoy.internal.bb
    public final Object a(int i) {
        if (i < 0 || i >= this.d) {
            throw new IndexOutOfBoundsException();
        }
        int size = this.b.size();
        if (i < size) {
            return this.b.get(i);
        }
        if (this.e) {
            return this.c.get(i - size);
        }
        if (i < this.a.size()) {
            Object obj = null;
            int i2 = size;
            while (i2 <= i) {
                Object objA = this.a.a(i2);
                this.b.add(objA);
                i2++;
                obj = objA;
            }
            if (i + 1 + this.c.size() == this.d) {
                this.e = true;
                return obj;
            }
            return obj;
        }
        return this.c.get(i - this.a.size());
    }

    @Override // com.tapjoy.internal.bb
    public final void b(int i) {
        if (i <= 0 || i > this.d) {
            throw new IndexOutOfBoundsException();
        }
        if (i <= this.b.size()) {
            ba.a(this.b, i);
            this.a.b(i);
        } else {
            this.b.clear();
            int size = (this.c.size() + i) - this.d;
            if (size < 0) {
                this.a.b(i);
            } else {
                this.a.clear();
                this.e = true;
                if (size > 0) {
                    ba.a(this.c, size);
                }
            }
        }
        this.d -= i;
    }
}
