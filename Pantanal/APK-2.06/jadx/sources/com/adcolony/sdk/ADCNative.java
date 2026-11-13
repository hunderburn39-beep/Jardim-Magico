package com.adcolony.sdk;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class ADCNative {
    static ADCNative a = new ADCNative();

    static native void nativeCreateSceneController(int i, int i2);

    static native void nativeCreateTexture(int i, int i2, int i3, String str, ByteBuffer byteBuffer, int i4, int i5, int i6, int i7);

    static native void nativeDeleteSceneController(int i, int i2);

    static native void nativeHelloWorld();

    static native boolean nativeIsArm();

    static native boolean nativeNeonSupported();

    static native void nativeRender(int i, int i2, int i3, int i4);

    ADCNative() {
    }
}
