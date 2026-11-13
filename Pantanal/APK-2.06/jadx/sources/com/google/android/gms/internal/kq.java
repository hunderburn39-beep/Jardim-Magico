package com.google.android.gms.internal;

import com.google.android.gms.internal.kp;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class kq<M extends kp<M>, T> {
    protected final Class<T> adV;
    protected final boolean adW;
    protected final int tag;
    protected final int type;

    private kq(int i, Class<T> cls, int i2, boolean z) {
        this.type = i;
        this.adV = cls;
        this.tag = i2;
        this.adW = z;
    }

    public static <M extends kp<M>, T extends kt> kq<M, T> a(int i, Class<T> cls, int i2) {
        return new kq<>(i, cls, i2, false);
    }

    protected void a(kv kvVar, List<Object> list) {
        list.add(o(kn.n(kvVar.adZ)));
    }

    protected boolean dd(int i) {
        return i == this.tag;
    }

    final T f(List<kv> list) {
        if (list == null) {
            return null;
        }
        if (!this.adW) {
            int size = list.size() - 1;
            kv kvVar = null;
            while (kvVar == null && size >= 0) {
                kv kvVar2 = list.get(size);
                if (!dd(kvVar2.tag) || kvVar2.adZ.length == 0) {
                    kvVar2 = kvVar;
                }
                size--;
                kvVar = kvVar2;
            }
            if (kvVar == null) {
                return null;
            }
            return this.adV.cast(o(kn.n(kvVar.adZ)));
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            kv kvVar3 = list.get(i);
            if (dd(kvVar3.tag) && kvVar3.adZ.length != 0) {
                a(kvVar3, arrayList);
            }
        }
        int size2 = arrayList.size();
        if (size2 == 0) {
            return null;
        }
        T tCast = this.adV.cast(Array.newInstance(this.adV.getComponentType(), size2));
        for (int i2 = 0; i2 < size2; i2++) {
            Array.set(tCast, i2, arrayList.get(i2));
        }
        return tCast;
    }

    protected Object o(kn knVar) {
        Class componentType = this.adW ? this.adV.getComponentType() : this.adV;
        try {
            switch (this.type) {
                case 10:
                    kt ktVar = (kt) componentType.newInstance();
                    knVar.a(ktVar, kw.df(this.tag));
                    return ktVar;
                case 11:
                    kt ktVar2 = (kt) componentType.newInstance();
                    knVar.a(ktVar2);
                    return ktVar2;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading extension field", e);
        } catch (IllegalAccessException e2) {
            throw new IllegalArgumentException("Error creating instance of class " + componentType, e2);
        } catch (InstantiationException e3) {
            throw new IllegalArgumentException("Error creating instance of class " + componentType, e3);
        }
    }
}
