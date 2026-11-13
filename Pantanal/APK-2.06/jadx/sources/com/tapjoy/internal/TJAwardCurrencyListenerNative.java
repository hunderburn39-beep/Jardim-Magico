package com.tapjoy.internal;

import com.tapjoy.TJAwardCurrencyListener;

/* JADX INFO: loaded from: classes.dex */
public class TJAwardCurrencyListenerNative implements TJAwardCurrencyListener {
    private final long a;

    private static native void onAwardCurrencyResponseFailureNative(long j, String str);

    private static native void onAwardCurrencyResponseNative(long j, String str, int i);

    private TJAwardCurrencyListenerNative(long nativeHandle) {
        if (nativeHandle == 0) {
            throw new IllegalArgumentException();
        }
        this.a = nativeHandle;
    }

    @Override // com.tapjoy.TJAwardCurrencyListener
    public void onAwardCurrencyResponse(String currencyName, int balance) {
        onAwardCurrencyResponseNative(this.a, currencyName, balance);
    }

    @Override // com.tapjoy.TJAwardCurrencyListener
    public void onAwardCurrencyResponseFailure(String error) {
        onAwardCurrencyResponseFailureNative(this.a, error);
    }

    @ep
    static Object create(long nativeHandle) {
        return new TJAwardCurrencyListenerNative(nativeHandle);
    }
}
