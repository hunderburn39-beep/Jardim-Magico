package com.r7games.slotpantanal;

import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class GoogleAnalyticsApp extends Application {
    public static int GENERAL_TRACKER = 0;
    private static final String PROPERTY_ID = "UA-62820610-1";
    public HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER,
        ECOMMERCE_TRACKER;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static TrackerName[] valuesCustom() {
            TrackerName[] trackerNameArrValuesCustom = values();
            int length = trackerNameArrValuesCustom.length;
            TrackerName[] trackerNameArr = new TrackerName[length];
            System.arraycopy(trackerNameArrValuesCustom, 0, trackerNameArr, 0, length);
            return trackerNameArr;
        }
    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        Tracker t;
        if (!this.mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            if (appTracker == TrackerName.APP_TRACKER) {
                t = analytics.newTracker(PROPERTY_ID);
            } else {
                t = appTracker == TrackerName.GLOBAL_TRACKER ? analytics.newTracker(R.xml.global_tracker) : analytics.newTracker(R.xml.ecommerce_tracker);
            }
            this.mTrackers.put(appTracker, t);
        }
        return this.mTrackers.get(appTracker);
    }
}
