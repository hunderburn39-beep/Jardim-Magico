package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class fp {
    public static String a(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim == null || strTrim.length() == 0) {
            return null;
        }
        return strTrim;
    }

    public static String b(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.length() != 0) {
            return strTrim;
        }
        return null;
    }

    public static String a(String str, String str2, String str3) {
        if (str == null) {
            fr.a(str2, str3, "must not be null");
            return null;
        }
        if (str.length() == 0) {
            fr.a(str2, str3, "must not be empty");
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.length() != 0) {
            return strTrim;
        }
        fr.a(str2, str3, "must not be blank");
        return null;
    }
}
