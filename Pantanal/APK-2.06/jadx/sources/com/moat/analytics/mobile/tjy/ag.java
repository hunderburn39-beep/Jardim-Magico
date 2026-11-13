package com.moat.analytics.mobile.tjy;

import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ag implements bc {
    private static final com.moat.analytics.mobile.tjy.base.functional.a a;
    private static final com.moat.analytics.mobile.tjy.base.functional.a b;

    static {
        com.moat.analytics.mobile.tjy.base.functional.a aVarA;
        NoSuchMethodException e;
        com.moat.analytics.mobile.tjy.base.functional.a aVarA2 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA3 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        try {
            Method method = NativeDisplayTracker.class.getMethod("track", Map.class);
            Method method2 = NativeDisplayTracker.class.getMethod("stopTracking", new Class[0]);
            aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a(method);
            try {
                aVarA3 = com.moat.analytics.mobile.tjy.base.functional.a.a(method2);
            } catch (NoSuchMethodException e2) {
                e = e2;
                com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            }
        } catch (NoSuchMethodException e3) {
            aVarA = aVarA2;
            e = e3;
        }
        a = aVarA;
        b = aVarA3;
    }

    ag() {
    }

    @Override // com.moat.analytics.mobile.tjy.bc
    public Class a() {
        return NativeDisplayTracker.class;
    }
}
