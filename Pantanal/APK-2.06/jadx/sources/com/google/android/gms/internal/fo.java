package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class fo {

    public static final class a {
        private final List<String> DI;
        private final Object DJ;

        private a(Object obj) {
            this.DJ = fq.f(obj);
            this.DI = new ArrayList();
        }

        public a a(String str, Object obj) {
            this.DI.add(((String) fq.f(str)) + "=" + String.valueOf(obj));
            return this;
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder(100).append(this.DJ.getClass().getSimpleName()).append('{');
            int size = this.DI.size();
            for (int i = 0; i < size; i++) {
                sbAppend.append(this.DI.get(i));
                if (i < size - 1) {
                    sbAppend.append(", ");
                }
            }
            return sbAppend.append('}').toString();
        }
    }

    public static a e(Object obj) {
        return new a(obj);
    }

    public static boolean equal(Object a2, Object b) {
        return a2 == b || (a2 != null && a2.equals(b));
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }
}
