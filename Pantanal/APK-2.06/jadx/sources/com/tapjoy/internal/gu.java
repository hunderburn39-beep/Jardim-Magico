package com.tapjoy.internal;

import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
final class gu extends gj implements fl {
    public static final bm a = new bm() { // from class: com.tapjoy.internal.gu.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            String strM = null;
            int iR = 1;
            brVar.h();
            String strM2 = null;
            String strM3 = null;
            while (brVar.j()) {
                String strL = brVar.l();
                if (TMXConstants.TAG_TILE_ATTRIBUTE_ID.equals(strL)) {
                    strM3 = brVar.m();
                } else if ("name".equals(strL)) {
                    strM2 = brVar.m();
                } else if ("quantity".equals(strL)) {
                    iR = brVar.r();
                } else if ("token".equals(strL)) {
                    strM = brVar.m();
                } else {
                    brVar.s();
                }
            }
            brVar.i();
            return new gu(strM3, strM2, iR, strM);
        }
    };
    private final String b;
    private final String c;
    private final int d;
    private final String e;

    gu(String str, String str2, int i, String str3) {
        this.b = str;
        this.c = str2;
        this.d = i;
        this.e = str3;
    }

    @Override // com.tapjoy.internal.fl
    public final String a() {
        return this.b;
    }

    @Override // com.tapjoy.internal.fl
    public final String b() {
        return this.c;
    }

    @Override // com.tapjoy.internal.fl
    public final int c() {
        return this.d;
    }

    @Override // com.tapjoy.internal.fl
    public final String d() {
        return this.e;
    }
}
