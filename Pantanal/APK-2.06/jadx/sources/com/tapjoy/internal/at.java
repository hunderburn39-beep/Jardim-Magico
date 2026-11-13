package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class at {

    public static final class a implements aw {
        private final au a;

        public a(au auVar) {
            this.a = auVar;
        }

        @Override // com.tapjoy.internal.ar
        public final Object a(Object obj) {
            as asVarA;
            Object objA;
            synchronized (this.a) {
                asVarA = this.a.a(obj, false);
            }
            if (asVarA != null) {
                synchronized (asVarA) {
                    objA = asVarA.a();
                }
                return objA;
            }
            return null;
        }

        @Override // com.tapjoy.internal.ar
        public final void a(Object obj, Object obj2) {
            as asVarA;
            synchronized (this.a) {
                asVarA = this.a.a(obj, true);
            }
            synchronized (asVarA) {
                asVarA.a(obj2);
            }
        }
    }
}
