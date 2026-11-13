package com.moat.analytics.mobile.tjy;

import android.view.View;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class be implements bc {
    private static final com.moat.analytics.mobile.tjy.base.functional.a a;
    private static final com.moat.analytics.mobile.tjy.base.functional.a b;
    private static final com.moat.analytics.mobile.tjy.base.functional.a c;
    private static final com.moat.analytics.mobile.tjy.base.functional.a d;

    static {
        com.moat.analytics.mobile.tjy.base.functional.a aVarA;
        NoSuchMethodException e;
        com.moat.analytics.mobile.tjy.base.functional.a aVarA2 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA3 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA4 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA5 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        try {
            Method method = ReactiveVideoTracker.class.getMethod("setDebug", Boolean.TYPE);
            Method method2 = ReactiveVideoTracker.class.getMethod("trackVideoAd", Map.class, View.class, View.class);
            Method method3 = ReactiveVideoTracker.class.getMethod("changeTargetView", View.class);
            Method method4 = ReactiveVideoTracker.class.getMethod("dispatchEvent", MoatAdEvent.class);
            aVarA2 = com.moat.analytics.mobile.tjy.base.functional.a.a(method);
            aVarA3 = com.moat.analytics.mobile.tjy.base.functional.a.a(method2);
            aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a(method3);
            try {
                aVarA5 = com.moat.analytics.mobile.tjy.base.functional.a.a(method4);
            } catch (NoSuchMethodException e2) {
                e = e2;
                com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            }
        } catch (NoSuchMethodException e3) {
            aVarA = aVarA4;
            e = e3;
        }
        a = aVarA2;
        b = aVarA3;
        c = aVarA;
        d = aVarA5;
    }

    be() {
    }

    @Override // com.moat.analytics.mobile.tjy.bc
    public Class a() {
        return ReactiveVideoTracker.class;
    }
}
