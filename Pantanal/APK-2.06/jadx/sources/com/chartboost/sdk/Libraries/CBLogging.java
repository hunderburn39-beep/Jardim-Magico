package com.chartboost.sdk.Libraries;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class CBLogging {
    public static Level a = Level.INTEGRATION;

    public enum Level {
        NONE,
        INTEGRATION,
        ALL
    }

    private static String a(Object obj) {
        return (String) ((obj == null || (obj instanceof String)) ? obj : obj.getClass().getName());
    }

    public static void a(Object obj, String str) {
        if (a == Level.ALL) {
            Log.d(a(obj), str);
        }
    }

    public static void a(Object obj, String str, Throwable th) {
        if (a == Level.ALL) {
            Log.d(a(obj), str, th);
        }
    }

    public static void b(Object obj, String str) {
        if (a == Level.ALL) {
            Log.e(a(obj), str);
        }
    }

    public static void b(Object obj, String str, Throwable th) {
        if (a == Level.ALL) {
            Log.e(a(obj), str, th);
        }
    }

    public static void c(Object obj, String str) {
        if (a == Level.ALL) {
            Log.v(a(obj), str);
        }
    }

    public static void c(Object obj, String str, Throwable th) {
        if (a == Level.ALL) {
            Log.v(a(obj), str, th);
        }
    }

    public static void d(Object obj, String str) {
        if (a == Level.ALL) {
            Log.w(a(obj), str);
        }
    }

    public static void d(Object obj, String str, Throwable th) {
        if (a == Level.ALL) {
            Log.w(a(obj), str, th);
        }
    }

    public static void e(Object obj, String str) {
        if (a == Level.ALL) {
            Log.i(a(obj), str);
        }
    }
}
