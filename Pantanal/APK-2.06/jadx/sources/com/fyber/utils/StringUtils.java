package com.fyber.utils;

/* JADX INFO: loaded from: classes.dex */
public class StringUtils {
    public static final String EMPTY_STRING = "";

    public static final boolean nullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static final boolean notNullNorEmpty(String str) {
        return !nullOrEmpty(str);
    }

    public static String trim(String str) {
        if (str != null) {
            return str.trim();
        }
        return null;
    }

    public static final String nullToEmpty(String str) {
        return str != null ? str : "";
    }
}
