package com.tapjoy.internal;

import java.util.Iterator;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class av extends au {
    private final LinkedHashMap a = new LinkedHashMap(0, 0.75f, true);
    private int b = 10;

    private void a() {
        int size = this.a.size() - this.b;
        if (size > 0) {
            Iterator it = this.a.entrySet().iterator();
            while (size > 0 && it.hasNext()) {
                size--;
                it.next();
                it.remove();
            }
        }
    }

    @Override // com.tapjoy.internal.au, com.tapjoy.internal.ar
    public final void a(Object obj, Object obj2) {
        super.a(obj, obj2);
        a();
    }

    @Override // com.tapjoy.internal.au
    protected final as a(Object obj, boolean z) {
        aq aqVar = (aq) this.a.get(obj);
        if (aqVar == null && z) {
            aq aqVar2 = new aq(obj);
            this.a.put(obj, aqVar2);
            a();
            return aqVar2;
        }
        return aqVar;
    }
}
