package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
final class gs extends gj implements fk {
    public static final bm a = new bm() { // from class: com.tapjoy.internal.gs.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            String strC = "";
            String strC2 = "";
            brVar.h();
            while (brVar.j()) {
                String strL = brVar.l();
                if ("campaign_id".equals(strL)) {
                    strC = brVar.c("");
                } else if ("product_id".equals(strL)) {
                    strC2 = brVar.c("");
                } else {
                    brVar.s();
                }
            }
            brVar.i();
            return new gs(strC, strC2);
        }
    };
    private final String b;
    private final String c;

    gs(String str, String str2) {
        this.b = str;
        this.c = str2;
    }

    @Override // com.tapjoy.internal.fk
    public final String a() {
        return this.b;
    }

    @Override // com.tapjoy.internal.fk
    public final String b() {
        return this.c;
    }
}
