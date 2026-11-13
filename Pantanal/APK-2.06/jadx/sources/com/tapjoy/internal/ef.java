package com.tapjoy.internal;

import android.content.Context;
import com.tapjoy.TJConnectListener;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
final class ef extends ee {
    private final ev b = new ev() { // from class: com.tapjoy.internal.ef.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tapjoy.internal.ev
        public final boolean a(Context context, String str, Hashtable hashtable, TJConnectListener tJConnectListener) {
            return ef.super.a(context, str, hashtable, tJConnectListener);
        }
    };

    ef() {
    }

    @Override // com.tapjoy.internal.ee, com.tapjoy.internal.ed
    public final boolean a(Context context, String str, Hashtable hashtable, TJConnectListener tJConnectListener) {
        return this.b.b(context, str, hashtable, tJConnectListener);
    }
}
