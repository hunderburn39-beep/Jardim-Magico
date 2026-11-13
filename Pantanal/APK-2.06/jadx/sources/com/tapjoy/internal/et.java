package com.tapjoy.internal;

import android.content.Context;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyConstants;
import com.tapjoy.TapjoyLog;
import java.util.Observable;
import java.util.Observer;

/* JADX INFO: loaded from: classes.dex */
abstract class et {
    volatile a b;

    protected abstract TJPlacement a(Context context, TJPlacementListener tJPlacementListener, Object obj);

    protected abstract String a(Object obj);

    et() {
    }

    public final boolean c(Object obj) {
        if (!a()) {
            return false;
        }
        a aVarB = null;
        synchronized (this) {
            if (this.b == null) {
                aVarB = b(obj);
                this.b = aVarB;
            }
        }
        if (aVarB == null) {
            return false;
        }
        aVarB.a();
        return true;
    }

    protected a b(Object obj) {
        return new a(this, obj);
    }

    protected boolean a() {
        return !TapjoyConnectCore.isFullScreenViewOpen();
    }

    protected boolean a(Observer observer) {
        if (TapjoyConnectCore.isFullScreenViewOpen()) {
            eo.e.addObserver(observer);
            if (TapjoyConnectCore.isFullScreenViewOpen()) {
                return false;
            }
            eo.e.deleteObserver(observer);
        }
        if (!fu.a().d()) {
            eo.c.addObserver(observer);
            if (!fu.a().d()) {
                return false;
            }
            eo.c.deleteObserver(observer);
        }
        return true;
    }

    class a implements TJPlacementListener, Observer {
        private final Object b;
        private final eg c;
        private volatile boolean d;
        private TJPlacement e;

        a(et etVar, Object obj) {
            this(obj, new eg(TapjoyConstants.TIMER_INCREMENT));
        }

        a(Object obj, eg egVar) {
            this.b = obj;
            this.c = egVar;
        }

        final void a() {
            synchronized (this) {
                if (!this.d) {
                    if (this.c.a()) {
                        a("Timed out");
                        return;
                    }
                    if (!TapjoyConnectCore.isConnected()) {
                        eo.a.addObserver(this);
                        if (TapjoyConnectCore.isConnected()) {
                            eo.a.deleteObserver(this);
                        } else {
                            return;
                        }
                    }
                    if (this.e == null) {
                        if (!et.this.a()) {
                            a("Cannot request");
                            return;
                        } else {
                            this.e = et.this.a(TapjoyConnectCore.getContext(), this, this.b);
                            this.e.requestContent();
                            return;
                        }
                    }
                    if (this.e.isContentReady()) {
                        if (et.this.a((Observer) this)) {
                            this.e.showContent();
                            a(null);
                        }
                    }
                }
            }
        }

        private void a(String str) {
            synchronized (this) {
                String strA = et.this.a(this.b);
                if (str == null) {
                    TapjoyLog.i("SystemPlacement", "Placement " + strA + " is presented now");
                } else {
                    TapjoyLog.i("SystemPlacement", "Cannot show placement " + strA + " now (" + str + ")");
                }
                this.d = true;
                this.e = null;
                eo.a.deleteObserver(this);
                eo.e.deleteObserver(this);
                eo.c.deleteObserver(this);
            }
            et etVar = et.this;
            synchronized (etVar) {
                if (etVar.b == this) {
                    etVar.b = null;
                }
            }
        }

        @Override // java.util.Observer
        public final void update(Observable observable, Object data) {
            a();
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onRequestSuccess(TJPlacement placement) {
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onRequestFailure(TJPlacement placement, TJError error) {
            a(error.message);
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onContentReady(TJPlacement placement) {
            a();
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onContentShow(TJPlacement placement) {
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onContentDismiss(TJPlacement placement) {
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
        }

        @Override // com.tapjoy.TJPlacementListener
        public final void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
        }
    }
}
