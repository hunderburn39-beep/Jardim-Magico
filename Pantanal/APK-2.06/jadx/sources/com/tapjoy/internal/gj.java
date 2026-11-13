package com.tapjoy.internal;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
abstract class gj implements fg {
    private static final String[] a;

    gj() {
    }

    static {
        String[] strArr = {"reward", "purchase", "custom_action"};
        a = strArr;
        Arrays.sort(strArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.tapjoy.internal.fg
    public final void a(fh fhVar) {
        if (this instanceof fk) {
            fk fkVar = (fk) this;
            fhVar.a(fkVar.a(), fkVar.b());
        } else if (this instanceof fl) {
            fl flVar = (fl) this;
            fhVar.a(flVar.a(), flVar.b(), flVar.c(), flVar.d());
        }
    }

    public static boolean a(String str) {
        return Arrays.binarySearch(a, str) >= 0;
    }

    public static gj a(String str, br brVar) {
        if ("reward".equals(str)) {
            return (gj) brVar.a(gu.a);
        }
        if ("purchase".equals(str)) {
            return (gj) brVar.a(gs.a);
        }
        return null;
    }
}
