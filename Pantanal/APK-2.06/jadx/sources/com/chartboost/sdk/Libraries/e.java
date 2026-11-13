package com.chartboost.sdk.Libraries;

/* JADX INFO: loaded from: classes.dex */
public enum e {
    PORTRAIT,
    LANDSCAPE,
    PORTRAIT_REVERSE,
    LANDSCAPE_REVERSE;

    public static final e e = PORTRAIT_REVERSE;
    public static final e f = PORTRAIT;
    public static final e g = LANDSCAPE;
    public static final e h = LANDSCAPE_REVERSE;

    public boolean a() {
        return this == PORTRAIT || this == PORTRAIT_REVERSE;
    }

    public boolean b() {
        return this == LANDSCAPE || this == LANDSCAPE_REVERSE;
    }
}
