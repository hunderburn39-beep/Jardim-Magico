package com.fyber.utils;

import android.util.Log;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class FyberLogger {
    private static boolean a = false;
    private static FyberLogger b = new FyberLogger();
    private Set<LoggerListener> c = new HashSet();

    public enum Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    public static boolean toggleLogging() {
        boolean z = !a;
        a = z;
        return z;
    }

    public static boolean isLogging() {
        return a;
    }

    public static boolean enableLogging(boolean z) {
        a = z;
        return z;
    }

    public static void e(String str, String str2) {
        if (a()) {
            Log.e("[FYB] " + str, StringUtils.nullToEmpty(str2));
            b.log(Level.ERROR, str, str2, null);
        }
    }

    public static void e(String str, String str2, Exception exc) {
        if (a()) {
            Log.w("[FYB] " + str, StringUtils.nullToEmpty(str2), exc);
            b.log(Level.ERROR, str, str2, exc);
        }
    }

    public static void d(String str, String str2) {
        if (a()) {
            Log.d("[FYB] " + str, StringUtils.nullToEmpty(str2));
            b.log(Level.DEBUG, str, str2, null);
        }
    }

    public static void i(String str, String str2) {
        if (a()) {
            Log.i("[FYB] " + str, StringUtils.nullToEmpty(str2));
            b.log(Level.INFO, str, str2, null);
        }
    }

    public static void v(String str, String str2) {
        if (a()) {
            Log.v("[FYB] " + str, StringUtils.nullToEmpty(str2));
            b.log(Level.VERBOSE, str, str2, null);
        }
    }

    public static void w(String str, String str2) {
        if (a()) {
            Log.w("[FYB] " + str, StringUtils.nullToEmpty(str2));
            b.log(Level.WARNING, str, str2, null);
        }
    }

    public static void w(String str, String str2, Exception exc) {
        if (a()) {
            Log.w("[FYB] " + str, StringUtils.nullToEmpty(str2), exc);
            b.log(Level.WARNING, str, str2, exc);
        }
    }

    private static boolean a() {
        return a || Log.isLoggable("Fyber", 2);
    }

    private FyberLogger() {
    }

    public void log(final Level level, final String str, final String str2, final Exception exc) {
        if (!this.c.isEmpty()) {
            new Thread(new Runnable() { // from class: com.fyber.utils.FyberLogger.1
                @Override // java.lang.Runnable
                public final void run() {
                    Iterator it = FyberLogger.this.c.iterator();
                    while (it.hasNext()) {
                        ((LoggerListener) it.next()).log(level, str, str2, exc);
                    }
                }
            }).start();
        }
    }

    public static boolean addLoggerListener(LoggerListener loggerListener) {
        return b.c.add(loggerListener);
    }

    public static boolean removeLoggerListener(LoggerListener loggerListener) {
        return b.c.remove(loggerListener);
    }
}
