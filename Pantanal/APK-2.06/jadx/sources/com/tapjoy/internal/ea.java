package com.tapjoy.internal;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes.dex */
public final class ea extends AbstractList implements ds, RandomAccess {
    private final ds a;

    public ea(ds dsVar) {
        this.a = dsVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.a.size();
    }

    @Override // com.tapjoy.internal.ds
    public final dk a(int i) {
        return this.a.a(i);
    }

    @Override // com.tapjoy.internal.ds
    public final void a(dk dkVar) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(final int index) {
        return new ListIterator() { // from class: com.tapjoy.internal.ea.1
            ListIterator a;

            {
                this.a = ea.this.a.listIterator(index);
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final boolean hasNext() {
                return this.a.hasNext();
            }

            @Override // java.util.ListIterator
            public final boolean hasPrevious() {
                return this.a.hasPrevious();
            }

            @Override // java.util.ListIterator
            public final int nextIndex() {
                return this.a.nextIndex();
            }

            @Override // java.util.ListIterator
            public final int previousIndex() {
                return this.a.previousIndex();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator
            public final /* synthetic */ void add(Object x0) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator
            public final /* synthetic */ void set(Object x0) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator
            public final /* bridge */ /* synthetic */ Object previous() {
                return (String) this.a.previous();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final /* bridge */ /* synthetic */ Object next() {
                return (String) this.a.next();
            }
        };
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new Iterator() { // from class: com.tapjoy.internal.ea.2
            Iterator a;

            {
                this.a = ea.this.a.iterator();
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return this.a.hasNext();
            }

            @Override // java.util.Iterator
            public final void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.Iterator
            public final /* bridge */ /* synthetic */ Object next() {
                return (String) this.a.next();
            }
        };
    }

    @Override // com.tapjoy.internal.ds
    public final List a() {
        return this.a.a();
    }

    @Override // com.tapjoy.internal.ds
    public final ds b() {
        return this;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object get(int x0) {
        return (String) this.a.get(x0);
    }
}
