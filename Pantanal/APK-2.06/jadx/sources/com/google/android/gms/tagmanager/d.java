package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.tapjoy.TapjoyConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class d implements DataLayer.b {
    private final Context kI;

    public d(Context context) {
        this.kI = context;
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.b
    public void y(Map<String, Object> map) {
        String queryParameter;
        Object obj;
        Object obj2 = map.get("gtm.url");
        Object obj3 = (obj2 == null && (obj = map.get("gtm")) != null && (obj instanceof Map)) ? ((Map) obj).get("url") : obj2;
        if (obj3 == null || !(obj3 instanceof String) || (queryParameter = Uri.parse((String) obj3).getQueryParameter(TapjoyConstants.TJC_REFERRER)) == null) {
            return;
        }
        ay.e(this.kI, queryParameter);
    }
}
