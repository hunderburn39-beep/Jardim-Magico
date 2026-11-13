package com.google.android.gms.internal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ba {
    public static final bb mG = new bb() { // from class: com.google.android.gms.internal.ba.1
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
        }
    };
    public static final bb mH = new bb() { // from class: com.google.android.gms.internal.ba.2
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            String str = map.get("urls");
            if (TextUtils.isEmpty(str)) {
                dw.z("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] strArrSplit = str.split(",");
            HashMap map2 = new HashMap();
            PackageManager packageManager = dzVar.getContext().getPackageManager();
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split(";", 2);
                map2.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(strArrSplit2.length > 1 ? strArrSplit2[1].trim() : "android.intent.action.VIEW", Uri.parse(strArrSplit2[0].trim())), 65536) != null));
            }
            dzVar.a("openableURLs", map2);
        }
    };
    public static final bb mI = new bb() { // from class: com.google.android.gms.internal.ba.3
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            Uri uriA;
            String str = map.get("u");
            if (str == null) {
                dw.z("URL missing from click GMSG.");
                return;
            }
            Uri uri = Uri.parse(str);
            try {
                l lVarBJ = dzVar.bJ();
                uriA = (lVarBJ == null || !lVarBJ.a(uri)) ? uri : lVarBJ.a(uri, dzVar.getContext());
            } catch (m e) {
                dw.z("Unable to append parameter to URL: " + str);
            }
            new du(dzVar.getContext(), dzVar.bK().rq, uriA.toString()).start();
        }
    };
    public static final bb mJ = new bb() { // from class: com.google.android.gms.internal.ba.4
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            cc ccVarBH = dzVar.bH();
            if (ccVarBH == null) {
                dw.z("A GMSG tried to close something that wasn't an overlay.");
            } else {
                ccVarBH.close();
            }
        }
    };
    public static final bb mK = new bb() { // from class: com.google.android.gms.internal.ba.5
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            cc ccVarBH = dzVar.bH();
            if (ccVarBH == null) {
                dw.z("A GMSG tried to use a custom close button on something that wasn't an overlay.");
            } else {
                ccVarBH.i("1".equals(map.get("custom_close")));
            }
        }
    };
    public static final bb mL = new bb() { // from class: com.google.android.gms.internal.ba.6
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            String str = map.get("u");
            if (str == null) {
                dw.z("URL missing from httpTrack GMSG.");
            } else {
                new du(dzVar.getContext(), dzVar.bK().rq, str).start();
            }
        }
    };
    public static final bb mM = new bb() { // from class: com.google.android.gms.internal.ba.7
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            dw.x("Received log message: " + map.get("string"));
        }
    };
    public static final bb mN = new bb() { // from class: com.google.android.gms.internal.ba.8
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            String str = map.get("tx");
            String str2 = map.get("ty");
            String str3 = map.get("td");
            try {
                int i = Integer.parseInt(str);
                int i2 = Integer.parseInt(str2);
                int i3 = Integer.parseInt(str3);
                l lVarBJ = dzVar.bJ();
                if (lVarBJ != null) {
                    lVarBJ.y().a(i, i2, i3);
                }
            } catch (NumberFormatException e) {
                dw.z("Could not parse touch parameters from gmsg.");
            }
        }
    };
    public static final bb mO = new be();
}
