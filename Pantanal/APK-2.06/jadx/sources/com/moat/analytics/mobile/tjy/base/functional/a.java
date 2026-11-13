package com.moat.analytics.mobile.tjy.base.functional;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final a a = new a();
    private final Object b;

    private a() {
        this.b = null;
    }

    private a(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Optional of null value.");
        }
        this.b = obj;
    }

    public static a a() {
        return a;
    }

    public static a a(Object obj) {
        return new a(obj);
    }

    public static a b(Object obj) {
        return obj == null ? a() : a(obj);
    }

    public final Object b() {
        if (this.b == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.b;
    }

    public final Object c(Object obj) {
        return this.b != null ? this.b : obj;
    }

    public final boolean c() {
        return this.b != null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        if (this.b == aVar.b) {
            return true;
        }
        if (this.b == null || aVar.b == null) {
            return false;
        }
        return this.b.equals(aVar.b);
    }

    public final int hashCode() {
        if (this.b == null) {
            return 0;
        }
        return this.b.hashCode();
    }

    public final String toString() {
        return this.b != null ? String.format("Optional[%s]", this.b) : "Optional.empty";
    }
}
