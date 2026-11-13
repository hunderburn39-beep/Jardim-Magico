package com.tapjoy.internal;

import com.tapjoy.TJVideoListener;

/* JADX INFO: loaded from: classes.dex */
public class TJVideoListenerNative implements TJVideoListener {
    private final long a;

    private static native void onVideoCompleteNative(long j);

    private static native void onVideoErrorNative(long j, int i);

    private static native void onVideoStartNative(long j);

    private TJVideoListenerNative(long nativeHandle) {
        if (nativeHandle == 0) {
            throw new IllegalArgumentException();
        }
        this.a = nativeHandle;
    }

    @Override // com.tapjoy.TJVideoListener
    public void onVideoStart() {
        onVideoStartNative(this.a);
    }

    @Override // com.tapjoy.TJVideoListener
    public void onVideoError(int statusCode) {
        onVideoErrorNative(this.a, statusCode);
    }

    @Override // com.tapjoy.TJVideoListener
    public void onVideoComplete() {
        onVideoCompleteNative(this.a);
    }

    @ep
    static Object create(long nativeHandle) {
        return new TJVideoListenerNative(nativeHandle);
    }
}
