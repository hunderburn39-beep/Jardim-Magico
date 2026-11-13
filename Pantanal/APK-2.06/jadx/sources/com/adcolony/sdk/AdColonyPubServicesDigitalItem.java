package com.adcolony.sdk;

import android.graphics.Bitmap;
import com.google.android.gms.plus.PlusShare;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesDigitalItem {
    Map<String, Object> a;
    Bitmap b = null;

    AdColonyPubServicesDigitalItem(Map<String, Object> dictionary) {
        this.a = dictionary;
    }

    public String productId() {
        if (this.a == null || !this.a.containsKey("product_id")) {
            return null;
        }
        return (String) this.a.get("product_id");
    }

    public int quantity() {
        if (this.a == null || !this.a.containsKey("quantity")) {
            return 0;
        }
        return ((Integer) this.a.get("quantity")).intValue();
    }

    public String name() {
        if (this.a == null || !this.a.containsKey("name")) {
            return null;
        }
        return (String) this.a.get("name");
    }

    public String productDescription() {
        if (this.a == null || !this.a.containsKey(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION)) {
            return null;
        }
        return (String) this.a.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
    }

    public Map<String, Object> userParams() {
        if (this.a == null || !this.a.containsKey("user_params")) {
            return null;
        }
        return (Map) this.a.get("user_params");
    }

    public String toJson() {
        return ck.a(this.a);
    }
}
