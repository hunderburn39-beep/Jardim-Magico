package com.google.android.gms.internal;

import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class bd implements bb {
    private final bc mP;

    public bd(bc bcVar) {
        this.mP = bcVar;
    }

    private static boolean a(Map<String, String> map) {
        return "1".equals(map.get("custom_close"));
    }

    private static int b(Map<String, String> map) {
        String str = map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return dq.bA();
            }
            if ("l".equalsIgnoreCase(str)) {
                return dq.bz();
            }
        }
        return -1;
    }

    @Override // com.google.android.gms.internal.bb
    public void b(dz dzVar, Map<String, String> map) {
        String str = map.get("a");
        if (str == null) {
            dw.z("Action missing from an open GMSG.");
            return;
        }
        ea eaVarBI = dzVar.bI();
        if ("expand".equalsIgnoreCase(str)) {
            if (dzVar.bL()) {
                dw.z("Cannot expand WebView that is already expanded.");
                return;
            } else {
                eaVarBI.a(a(map), b(map));
                return;
            }
        }
        if ("webapp".equalsIgnoreCase(str)) {
            String str2 = map.get("u");
            if (str2 != null) {
                eaVarBI.a(a(map), b(map), str2);
                return;
            } else {
                eaVarBI.a(a(map), b(map), map.get(TJAdUnitConstants.String.HTML), map.get("baseurl"));
                return;
            }
        }
        if (!"in_app_purchase".equalsIgnoreCase(str)) {
            eaVarBI.a(new cb(map.get("i"), map.get("u"), map.get("m"), map.get("p"), map.get("c"), map.get("f"), map.get("e")));
            return;
        }
        String str3 = map.get("product_id");
        String str4 = map.get("report_urls");
        if (this.mP != null) {
            if (str4 == null || str4.isEmpty()) {
                this.mP.a(str3, new ArrayList<>());
            } else {
                this.mP.a(str3, new ArrayList<>(Arrays.asList(str4.split(" "))));
            }
        }
    }
}
