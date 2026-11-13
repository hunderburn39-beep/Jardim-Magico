package com.moat.analytics.mobile.tjy;

import android.media.MediaPlayer;
import android.view.View;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ai implements bc {
    private static final com.moat.analytics.mobile.tjy.base.functional.a a;
    private static final com.moat.analytics.mobile.tjy.base.functional.a b;
    private static final com.moat.analytics.mobile.tjy.base.functional.a c;
    private static final com.moat.analytics.mobile.tjy.base.functional.a d;
    private static final com.moat.analytics.mobile.tjy.base.functional.a e;

    static {
        com.moat.analytics.mobile.tjy.base.functional.a aVarA;
        NoSuchMethodException e2;
        com.moat.analytics.mobile.tjy.base.functional.a aVarA2 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA3 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA4 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA5 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        com.moat.analytics.mobile.tjy.base.functional.a aVarA6 = com.moat.analytics.mobile.tjy.base.functional.a.a();
        try {
            Method method = NativeVideoTracker.class.getMethod("setDebug", Boolean.TYPE);
            Method method2 = NativeVideoTracker.class.getMethod("trackVideoAd", Map.class, MediaPlayer.class, View.class);
            Method method3 = NativeVideoTracker.class.getMethod("changeTargetView", View.class);
            Method method4 = NativeVideoTracker.class.getMethod("dispatchEvent", Map.class);
            Method method5 = NativeVideoTracker.class.getMethod("dispatchEvent", Map.class);
            aVarA2 = com.moat.analytics.mobile.tjy.base.functional.a.a(method);
            aVarA3 = com.moat.analytics.mobile.tjy.base.functional.a.a(method2);
            aVarA4 = com.moat.analytics.mobile.tjy.base.functional.a.a(method3);
            aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a(method4);
            try {
                aVarA5 = com.moat.analytics.mobile.tjy.base.functional.a.a(method5);
            } catch (NoSuchMethodException e3) {
                e2 = e3;
                com.moat.analytics.mobile.tjy.base.exception.a.a(e2);
            }
        } catch (NoSuchMethodException e4) {
            aVarA = aVarA6;
            e2 = e4;
        }
        a = aVarA2;
        b = aVarA3;
        c = aVarA4;
        d = aVarA;
        e = aVarA5;
    }

    ai() {
    }

    @Override // com.moat.analytics.mobile.tjy.bc
    public Class a() {
        return NativeVideoTracker.class;
    }
}
