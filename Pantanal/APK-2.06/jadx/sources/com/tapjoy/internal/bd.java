package com.tapjoy.internal;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class bd extends AbstractMap {
    private final HashMap a = new HashMap();
    private final cb b = new cb();

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        b();
        return this.a.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.a.clear();
        while (this.b.a() != null) {
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object key) {
        b();
        return this.a.containsKey(key);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object value) {
        b();
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            if (value.equals(((ca) it.next()).get())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object key) {
        b();
        return a((ca) this.a.get(key));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        b();
        return a((ca) this.a.put(obj, new ca(obj, obj2, this.b)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object key) {
        b();
        return a((ca) this.a.remove(key));
    }

    private static Object a(ca caVar) {
        if (caVar != null) {
            return caVar.get();
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        b();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        b();
        return this.a.keySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        b();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object object) {
        b();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        b();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap
    public final String toString() {
        b();
        throw new UnsupportedOperationException();
    }

    private void b() {
        while (true) {
            ca caVarA = this.b.a();
            if (caVarA != null) {
                this.a.remove(caVarA.a);
            } else {
                return;
            }
        }
    }

    public static bd a() {
        return new bd();
    }
}
