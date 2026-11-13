package com.tapjoy.internal;

import com.tapjoy.TJSpendCurrencyListener;

/* JADX INFO: loaded from: classes.dex */
public class TJSpendCurrencyListenerNative implements TJSpendCurrencyListener {
    private final long a;

    private static native void onSpendCurrencyResponseFailureNative(long j, String str);

    private static native void onSpendCurrencyResponseNative(long j, String str, int i);

    private TJSpendCurrencyListenerNative(long nativeHandle) {
        if (nativeHandle == 0) {
            throw new IllegalArgumentException();
        }
        this.a = nativeHandle;
    }

    @Override // com.tapjoy.TJSpendCurrencyListener
    public void onSpendCurrencyResponse(String currencyName, int balance) {
        onSpendCurrencyResponseNative(this.a, currencyName, balance);
    }

    @Override // com.tapjoy.TJSpendCurrencyListener
    public void onSpendCurrencyResponseFailure(String error) {
        onSpendCurrencyResponseFailureNative(this.a, error);
    }

    @ep
    static Object create(long nativeHandle) {
        return new TJSpendCurrencyListenerNative(nativeHandle);
    }
}
