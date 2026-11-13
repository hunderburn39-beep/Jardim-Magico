package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import java.math.BigInteger;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class dn {
    private static final Object px = new Object();
    private static String qX;

    public static String b(Context context, String str, String str2) {
        String str3;
        synchronized (px) {
            if (qX == null && !TextUtils.isEmpty(str)) {
                c(context, str, str2);
            }
            str3 = qX;
        }
        return str3;
    }

    public static String bx() {
        String str;
        synchronized (px) {
            str = qX;
        }
        return str;
    }

    private static void c(Context context, String str, String str2) {
        try {
            ClassLoader classLoader = context.createPackageContext(str2, 3).getClassLoader();
            Class<?> cls = Class.forName("com.google.ads.mediation.MediationAdapter", false, classLoader);
            BigInteger bigInteger = new BigInteger(new byte[1]);
            String[] strArrSplit = str.split(",");
            BigInteger bit = bigInteger;
            for (int i = 0; i < strArrSplit.length; i++) {
                if (dq.a(classLoader, cls, strArrSplit[i])) {
                    bit = bit.setBit(i);
                }
            }
            qX = String.format(Locale.US, "%X", bit);
        } catch (Throwable th) {
            qX = "err";
        }
    }
}
