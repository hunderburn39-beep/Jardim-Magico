package org.anddev.andengine.util;

import android.util.Log;
import org.anddev.andengine.util.constants.Constants;

/* JADX INFO: loaded from: classes.dex */
public class Debug implements Constants {
    private static String sDebugTag = Constants.DEBUGTAG;
    private static DebugLevel sDebugLevel = DebugLevel.VERBOSE;

    public static String getDebugTag() {
        return sDebugTag;
    }

    public static void setDebugTag(String pDebugTag) {
        sDebugTag = pDebugTag;
    }

    public static DebugLevel getDebugLevel() {
        return sDebugLevel;
    }

    public static void setDebugLevel(DebugLevel pDebugLevel) {
        if (pDebugLevel == null) {
            throw new IllegalArgumentException("pDebugLevel must not be null!");
        }
        sDebugLevel = pDebugLevel;
    }

    public static void v(String pMessage) {
        v(pMessage, null);
    }

    public static void v(String pMessage, Throwable pThrowable) {
        if (sDebugLevel.isSameOrLessThan(DebugLevel.VERBOSE)) {
            Log.v(sDebugTag, pMessage, pThrowable);
        }
    }

    public static void d(String pMessage) {
        d(pMessage, null);
    }

    public static void d(String pMessage, Throwable pThrowable) {
        if (sDebugLevel.isSameOrLessThan(DebugLevel.DEBUG)) {
            Log.d(sDebugTag, pMessage, pThrowable);
        }
    }

    public static void i(String pMessage) {
        i(pMessage, null);
    }

    public static void i(String pMessage, Throwable pThrowable) {
        if (sDebugLevel.isSameOrLessThan(DebugLevel.INFO)) {
            Log.i(sDebugTag, pMessage, pThrowable);
        }
    }

    public static void w(String pMessage) {
        w(pMessage, null);
    }

    public static void w(Throwable pThrowable) {
        w("", pThrowable);
    }

    public static void w(String pMessage, Throwable pThrowable) {
        if (sDebugLevel.isSameOrLessThan(DebugLevel.WARNING)) {
            if (pThrowable == null) {
                Log.w(sDebugTag, pMessage, new Exception());
            } else {
                Log.w(sDebugTag, pMessage, pThrowable);
            }
        }
    }

    public static void e(String pMessage) {
        e(pMessage, null);
    }

    public static void e(Throwable pThrowable) {
        e(sDebugTag, pThrowable);
    }

    public static void e(String pMessage, Throwable pThrowable) {
        if (sDebugLevel.isSameOrLessThan(DebugLevel.ERROR)) {
            if (pThrowable == null) {
                Log.e(sDebugTag, pMessage, new Exception());
            } else {
                Log.e(sDebugTag, pMessage, pThrowable);
            }
        }
    }

    public enum DebugLevel implements Comparable<DebugLevel> {
        NONE,
        ERROR,
        WARNING,
        INFO,
        DEBUG,
        VERBOSE;

        public static DebugLevel ALL = VERBOSE;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static DebugLevel[] valuesCustom() {
            DebugLevel[] debugLevelArrValuesCustom = values();
            int length = debugLevelArrValuesCustom.length;
            DebugLevel[] debugLevelArr = new DebugLevel[length];
            System.arraycopy(debugLevelArrValuesCustom, 0, debugLevelArr, 0, length);
            return debugLevelArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isSameOrLessThan(DebugLevel pDebugLevel) {
            return compareTo(pDebugLevel) >= 0;
        }
    }
}
