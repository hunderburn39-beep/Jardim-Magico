package com.google.android.gms.internal;

import com.tapjoy.TJAdUnitConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ay implements bb {
    private final az mF;

    public ay(az azVar) {
        this.mF = azVar;
    }

    @Override // com.google.android.gms.internal.bb
    public void b(dz dzVar, Map<String, String> map) {
        String str = map.get("name");
        if (str == null) {
            dw.z("App event with no name parameter.");
        } else {
            this.mF.onAppEvent(str, map.get(TJAdUnitConstants.String.VIDEO_INFO));
        }
    }
}
