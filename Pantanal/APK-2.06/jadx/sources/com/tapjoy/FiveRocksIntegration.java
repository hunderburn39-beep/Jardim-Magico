package com.tapjoy;

import com.tapjoy.internal.bd;
import com.tapjoy.internal.fg;
import com.tapjoy.internal.fh;
import com.tapjoy.internal.fi;
import com.tapjoy.internal.fj;
import com.tapjoy.internal.fu;
import com.tapjoy.internal.fv;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class FiveRocksIntegration {
    private static bd a = new bd();

    public static void a(Hashtable hashtable) {
        String strValueOf;
        if (hashtable != null && (strValueOf = String.valueOf(hashtable.get(TapjoyConnectFlag.ENABLE_LOGGING))) != null && strValueOf.equalsIgnoreCase("true")) {
            fi.a(true);
        }
        fu fuVarA = fu.a();
        if (!fuVarA.c) {
            fuVarA.c = true;
        }
        fu.a().p = fv.a(new fj() { // from class: com.tapjoy.FiveRocksIntegration.1
            @Override // com.tapjoy.internal.fj
            public final void a(String str) {
            }

            @Override // com.tapjoy.internal.fj
            public final void b(String str) {
                TJPlacement tJPlacement;
                synchronized (FiveRocksIntegration.a) {
                    tJPlacement = (TJPlacement) FiveRocksIntegration.a.get(str);
                }
                if (tJPlacement != null && tJPlacement.a != null) {
                    tJPlacement.a.onContentReady(tJPlacement);
                }
            }

            @Override // com.tapjoy.internal.fj
            public final void c(String str) {
                TJPlacement tJPlacement;
                synchronized (FiveRocksIntegration.a) {
                    tJPlacement = (TJPlacement) FiveRocksIntegration.a.get(str);
                }
                if (tJPlacement != null && tJPlacement.a != null) {
                    tJPlacement.a.onContentShow(tJPlacement);
                }
            }

            @Override // com.tapjoy.internal.fj
            public final void d(String str) {
            }

            @Override // com.tapjoy.internal.fj
            public final void a(String str, fg fgVar) {
                if (fgVar != null) {
                    fgVar.a(e(str));
                }
            }

            @Override // com.tapjoy.internal.fj
            public final void a(String str, String str2, fg fgVar) {
                TJPlacement tJPlacement;
                if (fgVar != null) {
                    fgVar.a(e(str));
                }
                synchronized (FiveRocksIntegration.a) {
                    tJPlacement = (TJPlacement) FiveRocksIntegration.a.get(str);
                }
                if (tJPlacement != null) {
                    TapjoyConnectCore.viewDidClose(str2);
                    if (tJPlacement.a != null) {
                        tJPlacement.a.onContentDismiss(tJPlacement);
                    }
                }
            }

            private fh e(final String str) {
                return new fh() { // from class: com.tapjoy.FiveRocksIntegration.1.1
                    @Override // com.tapjoy.internal.fh
                    public final void a(final String str2, String str3) {
                        TJPlacement tJPlacement;
                        synchronized (FiveRocksIntegration.a) {
                            tJPlacement = (TJPlacement) FiveRocksIntegration.a.get(str);
                        }
                        if (tJPlacement != null && tJPlacement.a != null) {
                            tJPlacement.a.onPurchaseRequest(tJPlacement, new TJActionRequest() { // from class: com.tapjoy.FiveRocksIntegration.1.1.1
                                @Override // com.tapjoy.TJActionRequest
                                public final String getRequestId() {
                                    return str2;
                                }

                                @Override // com.tapjoy.TJActionRequest
                                public final String getToken() {
                                    return null;
                                }

                                @Override // com.tapjoy.TJActionRequest
                                public final void completed() {
                                }

                                @Override // com.tapjoy.TJActionRequest
                                public final void cancelled() {
                                }
                            }, str3);
                        }
                    }

                    @Override // com.tapjoy.internal.fh
                    public final void a(final String str2, String str3, int i, final String str4) {
                        TJPlacement tJPlacement;
                        synchronized (FiveRocksIntegration.a) {
                            tJPlacement = (TJPlacement) FiveRocksIntegration.a.get(str);
                        }
                        if (tJPlacement != null && tJPlacement.a != null) {
                            tJPlacement.a.onRewardRequest(tJPlacement, new TJActionRequest() { // from class: com.tapjoy.FiveRocksIntegration.1.1.2
                                @Override // com.tapjoy.TJActionRequest
                                public final String getRequestId() {
                                    return str2;
                                }

                                @Override // com.tapjoy.TJActionRequest
                                public final String getToken() {
                                    return str4;
                                }

                                @Override // com.tapjoy.TJActionRequest
                                public final void completed() {
                                }

                                @Override // com.tapjoy.TJActionRequest
                                public final void cancelled() {
                                }
                            }, str3, i);
                        }
                    }
                };
            }
        });
    }

    public static void addPlacementCallback(String placement, TJPlacement p) {
        synchronized (a) {
            a.put(placement, p);
        }
    }
}
