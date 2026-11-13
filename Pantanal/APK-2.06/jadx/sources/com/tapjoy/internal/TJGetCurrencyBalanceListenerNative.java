package com.tapjoy.internal;

import com.tapjoy.TJGetCurrencyBalanceListener;

/* JADX INFO: loaded from: classes.dex */
public class TJGetCurrencyBalanceListenerNative implements TJGetCurrencyBalanceListener {
    private final long a;

    private static native void onGetCurrencyBalanceResponseFailureNative(long j, String str);

    private static native void onGetCurrencyBalanceResponseNative(long j, String str, int i);

    private TJGetCurrencyBalanceListenerNative(long nativeHandle) {
        if (nativeHandle == 0) {
            throw new IllegalArgumentException();
        }
        this.a = nativeHandle;
    }

    @Override // com.tapjoy.TJGetCurrencyBalanceListener
    public void onGetCurrencyBalanceResponse(String currencyName, int balance) {
        onGetCurrencyBalanceResponseNative(this.a, currencyName, balance);
    }

    @Override // com.tapjoy.TJGetCurrencyBalanceListener
    public void onGetCurrencyBalanceResponseFailure(String error) {
        onGetCurrencyBalanceResponseFailureNative(this.a, error);
    }

    @ep
    static Object create(long nativeHandle) {
        return new TJGetCurrencyBalanceListenerNative(nativeHandle);
    }
}
