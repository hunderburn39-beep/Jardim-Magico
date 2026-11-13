package com.google.android.gms.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class fe implements SafeParcelable {
    private static final Object CW = new Object();
    private static ClassLoader CX = null;
    private static Integer CY = null;
    private boolean CZ = false;

    private static boolean a(Class<?> cls) {
        try {
            return SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (IllegalAccessException e) {
            return false;
        } catch (NoSuchFieldException e2) {
            return false;
        }
    }

    protected static boolean al(String str) {
        ClassLoader classLoaderEI = eI();
        if (classLoaderEI == null) {
            return true;
        }
        try {
            return a(classLoaderEI.loadClass(str));
        } catch (Exception e) {
            return false;
        }
    }

    protected static ClassLoader eI() {
        ClassLoader classLoader;
        synchronized (CW) {
            classLoader = CX;
        }
        return classLoader;
    }

    protected static Integer eJ() {
        Integer num;
        synchronized (CW) {
            num = CY;
        }
        return num;
    }

    protected boolean eK() {
        return this.CZ;
    }
}
