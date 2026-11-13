package com.google.android.gms.internal;

import com.tapjoy.TJAdUnitConstants;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class df {
    private String pN;
    private String pO;
    private String pP;
    private List<String> pQ;
    private String pR;
    private String pS;
    private List<String> pT;
    private List<String> pX;
    private long pU = -1;
    private boolean pV = false;
    private final long pW = -1;
    private long pY = -1;
    private int mOrientation = -1;

    private static String a(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private static long b(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list != null && !list.isEmpty()) {
            String str2 = list.get(0);
            try {
                return (long) (Float.parseFloat(str2) * 1000.0f);
            } catch (NumberFormatException e) {
                dw.z("Could not parse float from " + str + " header: " + str2);
            }
        }
        return -1L;
    }

    private static List<String> c(Map<String, List<String>> map, String str) {
        String str2;
        List<String> list = map.get(str);
        if (list == null || list.isEmpty() || (str2 = list.get(0)) == null) {
            return null;
        }
        return Arrays.asList(str2.trim().split("\\s+"));
    }

    private void f(Map<String, List<String>> map) {
        this.pN = a(map, "X-Afma-Ad-Size");
    }

    private void g(Map<String, List<String>> map) {
        List<String> listC = c(map, "X-Afma-Click-Tracking-Urls");
        if (listC != null) {
            this.pQ = listC;
        }
    }

    private void h(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Debug-Dialog");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.pR = list.get(0);
    }

    private void i(Map<String, List<String>> map) {
        List<String> listC = c(map, "X-Afma-Tracking-Urls");
        if (listC != null) {
            this.pT = listC;
        }
    }

    private void j(Map<String, List<String>> map) {
        long jB = b(map, "X-Afma-Interstitial-Timeout");
        if (jB != -1) {
            this.pU = jB;
        }
    }

    private void k(Map<String, List<String>> map) {
        this.pS = a(map, "X-Afma-ActiveView");
    }

    private void l(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Mediation");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.pV = Boolean.valueOf(list.get(0)).booleanValue();
    }

    private void m(Map<String, List<String>> map) {
        List<String> listC = c(map, "X-Afma-Manual-Tracking-Urls");
        if (listC != null) {
            this.pX = listC;
        }
    }

    private void n(Map<String, List<String>> map) {
        long jB = b(map, "X-Afma-Refresh-Rate");
        if (jB != -1) {
            this.pY = jB;
        }
    }

    private void o(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Orientation");
        if (list == null || list.isEmpty()) {
            return;
        }
        String str = list.get(0);
        if ("portrait".equalsIgnoreCase(str)) {
            this.mOrientation = dq.bA();
        } else if (TJAdUnitConstants.String.LANDSCAPE.equalsIgnoreCase(str)) {
            this.mOrientation = dq.bz();
        }
    }

    public void a(String str, Map<String, List<String>> map, String str2) {
        this.pO = str;
        this.pP = str2;
        e(map);
    }

    public void e(Map<String, List<String>> map) {
        f(map);
        g(map);
        h(map);
        i(map);
        j(map);
        l(map);
        m(map);
        n(map);
        o(map);
        k(map);
    }

    public cz g(long j) {
        return new cz(this.pO, this.pP, this.pQ, this.pT, this.pU, this.pV, -1L, this.pX, this.pY, this.mOrientation, this.pN, j, this.pR, this.pS);
    }
}
