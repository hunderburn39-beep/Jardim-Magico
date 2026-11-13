package com.tapjoy.internal;

import android.content.Context;
import com.tapjoy.TJPlacementListener;

/* JADX INFO: loaded from: classes.dex */
@ep
public class TapjoyNative {
    @ep
    public static Object createPlacement(Context context, String placementName, TJPlacementListener listener) {
        return ew.a().a(context, placementName, listener);
    }
}
