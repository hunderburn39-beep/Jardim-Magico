package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.tapjoy.TapjoyConstants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ay {
    private static String Yk;
    static Map<String, String> Yl = new HashMap();

    ay() {
    }

    static void bF(String str) {
        synchronized (ay.class) {
            Yk = str;
        }
    }

    static void c(Context context, String str) {
        cy.a(context, "gtm_install_referrer", TapjoyConstants.TJC_REFERRER, str);
        e(context, str);
    }

    static String d(Context context, String str) {
        if (Yk == null) {
            synchronized (ay.class) {
                if (Yk == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        Yk = sharedPreferences.getString(TapjoyConstants.TJC_REFERRER, "");
                    } else {
                        Yk = "";
                    }
                }
            }
        }
        return m(Yk, str);
    }

    static String e(Context context, String str, String str2) {
        String string = Yl.get(str);
        if (string == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            string = sharedPreferences != null ? sharedPreferences.getString(str, "") : "";
            Yl.put(str, string);
        }
        return m(string, str2);
    }

    static void e(Context context, String str) {
        String strM = m(str, "conv");
        if (strM == null || strM.length() <= 0) {
            return;
        }
        Yl.put(strM, str);
        cy.a(context, "gtm_click_referrers", strM, str);
    }

    static String m(String str, String str2) {
        if (str2 != null) {
            return Uri.parse("http://hostname/?" + str).getQueryParameter(str2);
        }
        if (str.length() > 0) {
            return str;
        }
        return null;
    }
}
