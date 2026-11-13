package com.adcolony.sdk;

import android.os.Bundle;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class af {
    static final int a = 5;
    static final int b = 1;
    static final int c = 3;
    static final int d = 0;
    static final int e = 1;
    private static int f;
    private static HashMap<String, Integer> g = new HashMap<>();
    private static HashMap<String, Integer> h = new HashMap<>();

    af() {
    }

    static boolean a(int i, Bundle bundle) {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        switch (i) {
            case 0:
                if (iCurrentTimeMillis - f < 5) {
                    return true;
                }
                f = iCurrentTimeMillis;
                return false;
            case 1:
                if (bundle == null) {
                    return false;
                }
                String string = bundle.getString("zone_id");
                if (g.get(string) == null) {
                    g.put(string, Integer.valueOf(iCurrentTimeMillis));
                }
                if (h.get(string) == null) {
                    h.put(string, 0);
                }
                if (iCurrentTimeMillis - g.get(string).intValue() > 1) {
                    h.put(string, 1);
                    g.put(string, Integer.valueOf(iCurrentTimeMillis));
                    return false;
                }
                int iIntValue = h.get(string).intValue() + 1;
                h.put(string, Integer.valueOf(iIntValue));
                return iIntValue > 3;
            default:
                return false;
        }
    }
}
