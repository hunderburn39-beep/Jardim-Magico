package com.tapjoy.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class gc implements cj {
    public final fu a;
    private final Map c = Collections.synchronizedMap(new HashMap());
    private final Map d = cv.a();
    Set b = null;

    public gc(fu fuVar) {
        this.a = fuVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.tapjoy.internal.cj
    public void a(ce ceVar, gz.a aVar) {
        if (ceVar instanceof gz) {
            if (aVar.b != null) {
                List list = aVar.b;
                synchronized (this) {
                    this.b = list instanceof Collection ? new HashSet(ct.a(list)) : cw.a(list.iterator());
                }
            }
            gz gzVar = (gz) ceVar;
            String str = gzVar.c;
            boolean z = gzVar.d;
            this.d.remove(str);
            if (!z) {
                this.c.put(str, aVar.a);
            }
            gb gbVar = aVar.a;
            fv fvVar = this.a.p;
            if (gbVar instanceof ga) {
                fr.a("No content for \"{}\"", str);
                fvVar.a(str);
                return;
            }
            fr.a("New content for \"{}\" is ready", str);
            if (z) {
                gbVar.a(fvVar, new eq());
                return;
            } else {
                fvVar.b(str);
                return;
            }
        }
        throw new IllegalStateException(ceVar.getClass().getName());
    }

    @Override // com.tapjoy.internal.cj
    public final void a(ce ceVar) {
        a(ceVar, new gz.a(new ga(), null));
    }
}
