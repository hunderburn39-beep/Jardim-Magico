package com.moat.analytics.mobile.tjy;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
class av implements Runnable {
    private static final long b = 90000;
    private final aa a;
    private final String c;
    private final ax d;
    private ar e;

    private av(String str, aa aaVar, ax axVar) {
        this.e = ar.OFF;
        this.a = aaVar;
        this.d = axVar;
        this.c = "https://z.moatads.com/" + str + "/android/" + "8ace5ca5da6b9adb3c0f055aad4a98c2aedf4bd7".substring(0, 7) + "/status.json";
    }

    /* synthetic */ av(String str, aa aaVar, ax axVar, at atVar) {
        this(str, aaVar, axVar);
    }

    /* JADX INFO: Infinite loop detected, blocks: 14, insns: 0 */
    private void a() {
        long jCurrentTimeMillis = 0;
        while (true) {
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            if (jCurrentTimeMillis2 < b) {
                try {
                    Thread.sleep((10 + b) - jCurrentTimeMillis2);
                } catch (InterruptedException e) {
                }
            }
            jCurrentTimeMillis = System.currentTimeMillis();
            ar arVarB = b();
            Handler handler = new Handler(Looper.getMainLooper());
            arVarB.equals(this.e);
            this.e = arVarB;
            handler.post(new aw(this, arVarB));
        }
    }

    private ar b() {
        com.moat.analytics.mobile.tjy.base.functional.a aVarA = this.a.a(this.c + "?ts=" + System.currentTimeMillis() + "&v=1.7.10");
        if (!aVarA.c()) {
            return ar.OFF;
        }
        u uVar = new u((String) aVarA.b());
        boolean unused = as.d = uVar.a();
        int unused2 = as.e = uVar.c();
        return uVar.b() ? ar.ON : ar.OFF;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a();
        } catch (Exception e) {
            this.e = ar.OFF;
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }
}
