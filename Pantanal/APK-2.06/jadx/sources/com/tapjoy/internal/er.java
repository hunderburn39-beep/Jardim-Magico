package com.tapjoy.internal;

import android.content.Context;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJPlacementManager;
import com.tapjoy.TapjoyConnectCore;
import java.util.Observer;

/* JADX INFO: loaded from: classes.dex */
public final class er extends gf {
    private final et b = new et() { // from class: com.tapjoy.internal.er.1
        @Override // com.tapjoy.internal.et
        protected final /* synthetic */ TJPlacement a(Context context, TJPlacementListener tJPlacementListener, Object obj) {
            gf.a aVar = (gf.a) obj;
            TJPlacement tJPlacementCreatePlacement = TJPlacementManager.createPlacement(TapjoyConnectCore.getContext(), aVar.b, false, tJPlacementListener);
            tJPlacementCreatePlacement.pushId = aVar.a;
            return tJPlacementCreatePlacement;
        }

        @Override // com.tapjoy.internal.et
        protected final /* bridge */ /* synthetic */ String a(Object obj) {
            gf.a aVar = (gf.a) obj;
            if (aVar != null) {
                return aVar.b;
            }
            return null;
        }

        @Override // com.tapjoy.internal.et
        protected final /* synthetic */ et.a b(Object obj) {
            gf.a aVar = (gf.a) obj;
            return new et.a(aVar, aVar.d);
        }

        @Override // com.tapjoy.internal.et
        protected final boolean a() {
            return true;
        }

        @Override // com.tapjoy.internal.et
        protected final boolean a(Observer observer) {
            if (TapjoyConnectCore.isViewOpen()) {
                TJPlacementManager.dismissContentShowing(true);
            }
            return super.a(observer);
        }
    };

    static {
        gf.a(new er());
    }

    public static void a() {
    }

    private er() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.gf
    public final boolean b() {
        return this.b.b != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.gf
    public final void a(gf.a aVar) {
        this.b.c(aVar);
    }
}
