package com.tapjoy.internal;

import java.util.AbstractQueue;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class ax extends AbstractQueue implements bb {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new Iterator() { // from class: com.tapjoy.internal.ax.1
            private int b = 0;

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return this.b < ax.this.size();
            }

            @Override // java.util.Iterator
            public final Object next() {
                ax axVar = ax.this;
                int i = this.b;
                this.b = i + 1;
                return axVar.a(i);
            }

            @Override // java.util.Iterator
            public final void remove() {
                if (this.b == 1) {
                    ax.this.b(1);
                    this.b = 0;
                    return;
                }
                throw new UnsupportedOperationException("For the first element only");
            }
        };
    }
}
