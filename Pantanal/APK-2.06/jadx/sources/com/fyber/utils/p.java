package com.fyber.utils;

import android.text.TextUtils;
import com.fyber.Fyber;
import com.tapjoy.TapjoyConstants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: SDKParamsProvider.java */
/* JADX INFO: loaded from: classes.dex */
public final class p implements o {
    private static final String[] a = {"MPI", "VPL", "JUD", "BLE", "INV", "IVE"};
    private Map<String, String> b = new HashMap<String, String>() { // from class: com.fyber.utils.p.1
        {
            put("sdk_version", Fyber.RELEASE_VERSION_STRING);
            put(TapjoyConstants.TJC_PLATFORM, TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
            put("client", "sdk");
            put("sdk_features", TextUtils.join(",", p.a));
        }
    };

    @Override // com.fyber.utils.o
    public final Map<String, String> a() {
        return this.b;
    }
}
