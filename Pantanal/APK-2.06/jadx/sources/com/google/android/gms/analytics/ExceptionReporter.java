package com.google.android.gms.analytics;

import android.content.Context;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ExceptionReporter implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    private final Thread.UncaughtExceptionHandler sA;
    private final Tracker sB;
    private ExceptionParser sC;

    public ExceptionReporter(Tracker tracker, Thread.UncaughtExceptionHandler originalHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        }
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.sA = originalHandler;
        this.sB = tracker;
        this.sC = new StandardExceptionParser(context, new ArrayList());
        this.mContext = context.getApplicationContext();
        aa.y("ExceptionReporter created, original handler is " + (originalHandler == null ? "null" : originalHandler.getClass().getName()));
    }

    public ExceptionParser getExceptionParser() {
        return this.sC;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.sC = exceptionParser;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread t, Throwable e) {
        String description = "UncaughtException";
        if (this.sC != null) {
            description = this.sC.getDescription(t != null ? t.getName() : null, e);
        }
        aa.y("Tracking Exception: " + description);
        this.sB.send(new HitBuilders.ExceptionBuilder().setDescription(description).setFatal(true).build());
        GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
        if (this.sA != null) {
            aa.y("Passing exception to original handler.");
            this.sA.uncaughtException(t, e);
        }
    }
}
