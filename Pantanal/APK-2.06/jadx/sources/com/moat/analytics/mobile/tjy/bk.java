package com.moat.analytics.mobile.tjy;

/* JADX INFO: loaded from: classes.dex */
class bk implements bc {
    private static final com.moat.analytics.mobile.tjy.base.functional.a a;

    static {
        com.moat.analytics.mobile.tjy.base.functional.a aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a();
        try {
            aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a(WebAdTracker.class.getMethod("track", new Class[0]));
        } catch (NoSuchMethodException e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
        a = aVarA;
    }

    bk() {
    }

    @Override // com.moat.analytics.mobile.tjy.bc
    public Class a() {
        return WebAdTracker.class;
    }
}
