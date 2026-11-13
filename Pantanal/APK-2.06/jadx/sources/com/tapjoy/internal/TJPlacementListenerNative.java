package com.tapjoy.internal;

import com.tapjoy.TJActionRequest;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;

/* JADX INFO: loaded from: classes.dex */
public class TJPlacementListenerNative implements TJPlacementListener {
    private final long a;

    private static native void onContentDismissNative(long j, TJPlacement tJPlacement, String str);

    private static native void onContentReadyNative(long j, TJPlacement tJPlacement, String str);

    private static native void onContentShowNative(long j, TJPlacement tJPlacement, String str);

    private static native void onPurchaseRequestNative(long j, TJPlacement tJPlacement, String str, TJActionRequest tJActionRequest, String str2, String str3, String str4);

    private static native void onRequestFailureNative(long j, TJPlacement tJPlacement, String str, int i, String str2);

    private static native void onRequestSuccessNative(long j, TJPlacement tJPlacement, String str);

    private static native void onRewardRequestNative(long j, TJPlacement tJPlacement, String str, TJActionRequest tJActionRequest, String str2, String str3, String str4, int i);

    private TJPlacementListenerNative(long nativeHandle) {
        if (nativeHandle == 0) {
            throw new IllegalArgumentException();
        }
        this.a = nativeHandle;
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onRequestSuccess(TJPlacement placement) {
        onRequestSuccessNative(this.a, placement, placement.getName());
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onRequestFailure(TJPlacement placement, TJError error) {
        onRequestFailureNative(this.a, placement, placement.getName(), error.code, error.message);
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onContentReady(TJPlacement placement) {
        onContentReadyNative(this.a, placement, placement.getName());
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onContentShow(TJPlacement placement) {
        onContentShowNative(this.a, placement, placement.getName());
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onContentDismiss(TJPlacement placement) {
        onContentDismissNative(this.a, placement, placement.getName());
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
        onPurchaseRequestNative(this.a, placement, placement.getName(), request, request.getRequestId(), request.getToken(), productId);
    }

    @Override // com.tapjoy.TJPlacementListener
    public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
        onRewardRequestNative(this.a, placement, placement.getName(), request, request.getRequestId(), request.getToken(), itemId, quantity);
    }

    @ep
    static Object create(long nativeHandle) {
        return new TJPlacementListenerNative(nativeHandle);
    }
}
