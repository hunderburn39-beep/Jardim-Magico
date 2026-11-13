package com.fyber.utils;

/* JADX INFO: compiled from: UrlSchemeHelper.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class u {
    public static boolean a(String str) {
        return StringUtils.notNullNorEmpty(str) && (str.startsWith("http://") || str.startsWith("https://"));
    }
}
