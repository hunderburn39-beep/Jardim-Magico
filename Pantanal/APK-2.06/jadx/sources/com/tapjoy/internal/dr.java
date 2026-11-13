package com.tapjoy.internal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes.dex */
public final class dr extends AbstractList implements ds, RandomAccess {
    public static final ds a = new dr().b();
    private final List b;

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ void add(int x0, Object x1) {
        this.b.add(x0, (String) x1);
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int x0, Object x1) {
        return a(this.b.set(x0, (String) x1));
    }

    public dr() {
        this.b = new ArrayList();
    }

    public dr(ds dsVar) {
        this.b = new ArrayList(dsVar.size());
        addAll(dsVar);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.b.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int index, Collection collection) {
        if (collection instanceof ds) {
            collection = ((ds) collection).a();
        }
        boolean zAddAll = this.b.addAll(index, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.b.clear();
        this.modCount++;
    }

    @Override // com.tapjoy.internal.ds
    public final void a(dk dkVar) {
        this.b.add(dkVar);
        this.modCount++;
    }

    @Override // com.tapjoy.internal.ds
    public final dk a(int i) {
        dk dkVarA;
        Object obj = this.b.get(i);
        if (obj instanceof dk) {
            dkVarA = (dk) obj;
        } else if (obj instanceof String) {
            dkVarA = dk.a((String) obj);
        } else {
            dkVarA = dk.a((byte[]) obj);
        }
        if (dkVarA != obj) {
            this.b.set(i, dkVarA);
        }
        return dkVarA;
    }

    private static String a(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof dk) {
            return ((dk) obj).e();
        }
        return dp.b((byte[]) obj);
    }

    @Override // com.tapjoy.internal.ds
    public final List a() {
        return Collections.unmodifiableList(this.b);
    }

    @Override // com.tapjoy.internal.ds
    public final ds b() {
        return new ea(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int x0) {
        Object objRemove = this.b.remove(x0);
        this.modCount++;
        return a(objRemove);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int x0) {
        Object obj = this.b.get(x0);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof dk) {
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.b.set(x0, strE);
            }
            return strE;
        }
        byte[] bArr = (byte[]) obj;
        String strB = dp.b(bArr);
        if (dp.a(bArr)) {
            this.b.set(x0, strB);
        }
        return strB;
    }
}
