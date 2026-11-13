package com.tapjoy.internal;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import com.tapjoy.FiveRocksIntegration;
import com.tapjoy.TJAdUnit;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TJAwardCurrencyListener;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJCurrency;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJEventOptimizer;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJPlacementManager;
import com.tapjoy.TJSetUserIDListener;
import com.tapjoy.TJSpendCurrencyListener;
import com.tapjoy.TJVideoListener;
import com.tapjoy.TapjoyAppSettings;
import com.tapjoy.TapjoyCache;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyErrorMessage;
import com.tapjoy.TapjoyException;
import com.tapjoy.TapjoyIntegrationException;
import com.tapjoy.TapjoyLog;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class ee extends ed {
    private boolean b = false;
    private String c = "";
    private TJCurrency d = null;
    private TapjoyCache e = null;

    ee() {
    }

    @Override // com.tapjoy.internal.ed
    public final String b() {
        return "11.9.0";
    }

    @Override // com.tapjoy.internal.ed
    public final void a(boolean z) {
        TapjoyLog.setDebugEnabled(z);
    }

    @Override // com.tapjoy.internal.ed
    public final boolean a(Context context, String str) {
        return a(context, str, (Hashtable) null, (TJConnectListener) null);
    }

    @Override // com.tapjoy.internal.ed
    public synchronized boolean a(final Context context, String str, Hashtable hashtable, final TJConnectListener tJConnectListener) {
        String strValueOf;
        boolean z = false;
        synchronized (this) {
            try {
                TapjoyConnectCore.setSDKType("event");
                if (context == null) {
                    TapjoyLog.e("TapjoyAPI", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, "The application context is NULL"));
                    if (tJConnectListener != null) {
                        tJConnectListener.onConnectFailure();
                    }
                } else {
                    FiveRocksIntegration.a(hashtable);
                    try {
                        TapjoyAppSettings.init(context);
                        TapjoyConnectCore.requestTapjoyConnect(context, str, hashtable, new TJConnectListener() { // from class: com.tapjoy.internal.ee.1
                            @Override // com.tapjoy.TJConnectListener
                            public final void onConnectSuccess() {
                                ee.this.d = new TJCurrency(context);
                                ee.this.e = new TapjoyCache(context);
                                try {
                                    TJEventOptimizer.init(context);
                                    ee.this.a = true;
                                    if (tJConnectListener != null) {
                                        tJConnectListener.onConnectSuccess();
                                    }
                                } catch (InterruptedException e) {
                                    onConnectFailure();
                                } catch (RuntimeException e2) {
                                    TapjoyLog.w("TapjoyAPI", e2.getMessage());
                                    onConnectFailure();
                                }
                            }

                            @Override // com.tapjoy.TJConnectListener
                            public final void onConnectFailure() {
                                if (tJConnectListener != null) {
                                    tJConnectListener.onConnectFailure();
                                }
                            }
                        });
                        this.b = true;
                        if (Build.VERSION.SDK_INT < 14) {
                            TapjoyLog.i("TapjoyAPI", "Automatic session tracking is not available on this device.");
                        } else {
                            if (hashtable != null && (strValueOf = String.valueOf(hashtable.get(TapjoyConnectFlag.DISABLE_AUTOMATIC_SESSION_TRACKING))) != null && strValueOf.equalsIgnoreCase("true")) {
                                z = true;
                            }
                            if (!z) {
                                ej.a(context);
                            } else {
                                TapjoyLog.i("TapjoyAPI", "Automatic session tracking is disabled.");
                            }
                        }
                        z = true;
                    } catch (TapjoyIntegrationException e) {
                        TapjoyLog.e("TapjoyAPI", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, e.getMessage()));
                        if (tJConnectListener != null) {
                            tJConnectListener.onConnectFailure();
                        }
                    } catch (TapjoyException e2) {
                        TapjoyLog.e("TapjoyAPI", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, e2.getMessage()));
                        if (tJConnectListener != null) {
                            tJConnectListener.onConnectFailure();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    @Override // com.tapjoy.internal.ed
    public final TJPlacement a(String str, TJPlacementListener tJPlacementListener) {
        return TJPlacementManager.a(str, "", "", tJPlacementListener);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(Activity activity) {
        if (activity != null) {
            d.a(activity);
        } else {
            TapjoyLog.e("TapjoyAPI", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, "Cannot set activity to NULL"));
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void e(String str) {
        if (h("enablePaidAppWithActionID")) {
            TapjoyConnectCore.getInstance().enablePaidAppWithActionID(str);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(float f) {
        if (i("setCurrencyMultiplier")) {
            TapjoyConnectCore.getInstance().setCurrencyMultiplier(f);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final float c() {
        if (i("getCurrencyMultiplier")) {
            return TapjoyConnectCore.getInstance().getCurrencyMultiplier();
        }
        return 1.0f;
    }

    @Override // com.tapjoy.internal.ed
    public final void f(String str) {
        if (h("actionComplete")) {
            TapjoyConnectCore.getInstance().actionComplete(str);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(TJGetCurrencyBalanceListener tJGetCurrencyBalanceListener) {
        if (this.d != null && h("getCurrencyBalance")) {
            this.d.getCurrencyBalance(tJGetCurrencyBalanceListener);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(int i, TJSpendCurrencyListener tJSpendCurrencyListener) {
        if (this.d != null && h("spendCurrency")) {
            this.d.spendCurrency(i, tJSpendCurrencyListener);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(int i, TJAwardCurrencyListener tJAwardCurrencyListener) {
        if (this.d != null && h("awardCurrency")) {
            this.d.awardCurrency(i, tJAwardCurrencyListener);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(TJEarnedCurrencyListener tJEarnedCurrencyListener) {
        if (this.d != null && h("setEarnedCurrencyListener")) {
            this.d.setEarnedCurrencyListener(tJEarnedCurrencyListener);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(TJVideoListener tJVideoListener) {
        if (i("setVideoListener")) {
            TJAdUnit.publisherVideoListener = tJVideoListener;
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, String str3, String str4) {
        fi.a(str, str2, str3, str4);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2) {
        fi.a(str, null, null, str2);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str) {
        fi.a(null, str, null, null, 0L);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, long j) {
        fi.a(null, str, null, null, j);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, long j) {
        fi.a(str, str2, null, null, j);
    }

    @Override // com.tapjoy.internal.ed
    public final void b(String str, String str2, String str3, String str4) {
        fi.a(str, str2, str3, str4, 0L);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, String str3, String str4, long j) {
        fi.a(str, str2, str3, str4, j);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, String str3, String str4, String str5, long j) {
        fi.a(str, str2, str3, str4, str5, j, null, 0L, null, 0L);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, String str3, String str4, String str5, long j, String str6, long j2) {
        fi.a(str, str2, str3, str4, str5, j, str6, j2, null, 0L);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, String str3, String str4, String str5, long j, String str6, long j2, String str7, long j3) {
        fi.a(str, str2, str3, str4, str5, j, str6, j2, str7, j3);
    }

    @Override // com.tapjoy.internal.ed
    public final void d() {
        if (i("startSession")) {
            if (Build.VERSION.SDK_INT >= 14) {
                ej.a();
            }
            TapjoyConnectCore.getInstance().appResume();
            fi.a();
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void e() {
        if (i("endSession")) {
            if (Build.VERSION.SDK_INT >= 14) {
                ej.a();
            }
            fu.a().n = false;
            TapjoyConnectCore.getInstance().appPause();
            fi.b();
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void b(Activity activity) {
        if (Build.VERSION.SDK_INT >= 14) {
            ej.a();
        }
        fu.a().n = true;
        fi.a(activity);
    }

    @Override // com.tapjoy.internal.ed
    public final void c(Activity activity) {
        if (Build.VERSION.SDK_INT >= 14) {
            ej.a();
        }
        fi.b(activity);
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, TJSetUserIDListener tJSetUserIDListener) {
        if (i("setUserID")) {
            TapjoyConnectCore.setUserID(str, tJSetUserIDListener);
            fu fuVarA = fu.a();
            if (!fuVarA.c("setUserId")) {
                return;
            }
            fuVarA.f.b(fp.a(str));
            return;
        }
        if (tJSetUserIDListener != null) {
            tJSetUserIDListener.onSetUserIDFailure(this.c);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final Set f() {
        return fu.a().c();
    }

    @Override // com.tapjoy.internal.ed
    public final void a(Set set) {
        fu.a().a(set);
    }

    @Override // com.tapjoy.internal.ed
    public final void g() {
        fu.a().a((Set) null);
    }

    @Override // com.tapjoy.internal.ed
    public final void c(String str) {
        if (!cr.c(str)) {
            fu fuVarA = fu.a();
            Set setC = fuVarA.c();
            if (setC.add(str)) {
                fuVarA.a(setC);
            }
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void d(String str) {
        if (!cr.c(str)) {
            fu fuVarA = fu.a();
            Set setC = fuVarA.c();
            if (setC.remove(str)) {
                fuVarA.a(setC);
            }
        }
    }

    @Override // com.tapjoy.internal.ed
    public final boolean h() {
        fu fuVarA = fu.a();
        if (!fuVarA.c("isPushNotificationDisabled")) {
            return false;
        }
        boolean z = fuVarA.f.b.o;
        fr.a("isPushNotificationDisabled = {}", Boolean.valueOf(z));
        return z;
    }

    @Override // com.tapjoy.internal.ed
    public final void b(boolean z) {
        String str;
        Object[] objArr;
        Object objValueOf;
        Object[] objArr2;
        String strB;
        char c = 0;
        fu fuVarA = fu.a();
        if (!fuVarA.c("setPushNotificationDisabled")) {
            return;
        }
        boolean zA = fuVarA.f.a(z);
        if (zA) {
            objArr2 = new Object[1];
            objValueOf = Boolean.valueOf(z);
            str = "setPushNotificationDisabled({}) called";
            objArr = objArr2;
        } else {
            str = "setPushNotificationDisabled({}) called, but it is already {}";
            objArr = new Object[2];
            objArr[0] = Boolean.valueOf(z);
            if (z) {
                objValueOf = "disabled";
                c = 1;
                objArr2 = objArr;
            } else {
                objValueOf = TJAdUnitConstants.String.ENABLED;
                c = 1;
                objArr2 = objArr;
            }
        }
        objArr2[c] = objValueOf;
        fr.a(str, objArr);
        if (!zA || !fuVarA.k || cr.c(fuVarA.d)) {
            return;
        }
        if (fuVarA.o != null) {
            strB = null;
        } else {
            fw fwVarB = fw.b(fuVarA.e);
            strB = cr.b(fwVarB.b.b(fwVarB.a));
        }
        fuVarA.a(strB);
    }

    @Override // com.tapjoy.internal.ed
    public final boolean i() {
        return this.a;
    }

    private boolean h(String str) {
        if (this.a) {
            return true;
        }
        TapjoyLog.w("TapjoyAPI", "Can not call " + str + " because Tapjoy SDK has not successfully connected.");
        return false;
    }

    private boolean i(String str) {
        if (this.b) {
            return true;
        }
        this.c = "Can not call " + str + " because Tapjoy SDK is not initialized.";
        TapjoyLog.e("TapjoyAPI", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.INTEGRATION_ERROR, this.c));
        return false;
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, double d, String str3) {
        String strA;
        String strA2;
        fu fuVarA = fu.a();
        if (fuVarA.b("trackPurchase") && (strA = fp.a(str, "trackPurchase", "productId")) != null && (strA2 = fp.a(str2, "trackPurchase", "currencyCode")) != null) {
            if (strA2.length() != 3) {
                fr.a("trackPurchase", "currencyCode", "invalid currency code");
            } else {
                fuVarA.g.a(strA, strA2.toUpperCase(Locale.US), d, (String) null, (String) null, fp.b(str3));
                fr.a("trackPurchase called");
            }
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(String str, String str2, String str3, String str4, Map map) {
        fu fuVarA = fu.a();
        if (fuVarA.b("trackEvent") && !cr.c(str2)) {
            LinkedHashMap linkedHashMapB = cv.b();
            if (map != null && map.size() > 0) {
                for (Map.Entry entry : map.entrySet()) {
                    Object key = entry.getKey();
                    if (key != null) {
                        if (key instanceof String) {
                            String strA = fp.a((String) key, "trackEvent", "key in values map");
                            if (strA != null) {
                                Object value = entry.getValue();
                                if (value instanceof Number) {
                                    linkedHashMapB.put(strA, Long.valueOf(((Number) value).longValue()));
                                } else {
                                    fr.a("trackEvent", "value in values map", "must be a long");
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                    } else {
                        if (!fr.a) {
                            return;
                        }
                        ac.a("Tapjoy", "{}: {} must not be null", "trackEvent", "key in values map");
                        return;
                    }
                }
            }
            fuVarA.g.a(str, str2, str3, str4, linkedHashMapB);
            fr.a("trackEvent category:{}, name:{}, p1:{}, p2:{}, values:{} called", str, str2, str3, str4, linkedHashMapB);
        }
    }

    @Override // com.tapjoy.internal.ed
    public final void a(int i) {
        fu fuVarA = fu.a();
        if (!fuVarA.c("setUserLevel")) {
            return;
        }
        if (i < -1) {
            i = -1;
        }
        fr.a("setUserLevel({}) called", Integer.valueOf(i));
        fuVarA.f.a(i);
    }

    @Override // com.tapjoy.internal.ed
    public final void b(int i) {
        fu fuVarA = fu.a();
        if (!fuVarA.c("setUserFriendCount")) {
            return;
        }
        fr.a("setUserFriendCount({}) called", Integer.valueOf(i));
        if (i < -1) {
            i = -1;
        }
        fuVarA.f.b(i);
    }

    @Override // com.tapjoy.internal.ed
    public final void b(String str) {
        fu fuVarA = fu.a();
        if (!fuVarA.c("setAppDataVersion")) {
            return;
        }
        fuVarA.f.a(fp.a(str));
    }

    @Override // com.tapjoy.internal.ed
    public final void a(int i, String str) {
        fu fuVarA = fu.a();
        if (!fuVarA.c("setUserCohortVariable")) {
            return;
        }
        boolean z = i > 0 && i <= 5;
        if (fr.a && !z) {
            fr.b("setCohortVariable: variableIndex is out of range");
        }
        if (!z) {
            return;
        }
        fr.a("setUserCohortVariable({}, {}) called", Integer.valueOf(i), str);
        fuVarA.f.a(i, fp.a(str));
    }

    @Override // com.tapjoy.internal.ed
    public final void g(String str) {
        fu fuVarA = fu.a();
        fr.a("setGcmSender({}) called", str);
        fuVarA.d = cr.a(str);
        fuVarA.b();
    }

    @Override // com.tapjoy.internal.ed
    public final void a(GLSurfaceView gLSurfaceView) {
        fu.a();
        fu.a(gLSurfaceView);
    }
}
