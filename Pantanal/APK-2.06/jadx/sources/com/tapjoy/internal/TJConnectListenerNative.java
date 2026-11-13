package com.tapjoy.internal;

import com.tapjoy.TJConnectListener;

/* JADX INFO: loaded from: classes.dex */
public class TJConnectListenerNative implements TJConnectListener {
    private final long a;

    private static native void onConnectFailureNative(long j);

    private static native void onConnectSuccessNative(long j);

    private TJConnectListenerNative(long nativeHandle) {
        if (nativeHandle == 0) {
            throw new IllegalArgumentException();
        }
        this.a = nativeHandle;
    }

    @Override // com.tapjoy.TJConnectListener
    public void onConnectSuccess() {
        onConnectSuccessNative(this.a);
    }

    @Override // com.tapjoy.TJConnectListener
    public void onConnectFailure() {
        onConnectFailureNative(this.a);
    }

    @ep
    static Object create(long nativeHandle) {
        return new TJConnectListenerNative(nativeHandle);
    }
}
