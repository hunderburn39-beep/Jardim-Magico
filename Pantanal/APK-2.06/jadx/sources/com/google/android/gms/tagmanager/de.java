package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/* JADX INFO: loaded from: classes.dex */
class de {
    private GoogleAnalytics aaB;
    private Context mContext;
    private Tracker sB;

    static class a implements Logger {
        a() {
        }

        private static int ci(int i) {
            switch (i) {
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                default:
                    return 3;
            }
        }

        @Override // com.google.android.gms.analytics.Logger
        public void error(Exception exception) {
            bh.b("", exception);
        }

        @Override // com.google.android.gms.analytics.Logger
        public void error(String message) {
            bh.w(message);
        }

        @Override // com.google.android.gms.analytics.Logger
        public int getLogLevel() {
            return ci(bh.getLogLevel());
        }

        @Override // com.google.android.gms.analytics.Logger
        public void info(String message) {
            bh.x(message);
        }

        @Override // com.google.android.gms.analytics.Logger
        public void setLogLevel(int logLevel) {
            bh.z("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
        }

        @Override // com.google.android.gms.analytics.Logger
        public void verbose(String message) {
            bh.y(message);
        }

        @Override // com.google.android.gms.analytics.Logger
        public void warn(String message) {
            bh.z(message);
        }
    }

    de(Context context) {
        this.mContext = context;
    }

    private synchronized void bV(String str) {
        if (this.aaB == null) {
            this.aaB = GoogleAnalytics.getInstance(this.mContext);
            this.aaB.setLogger(new a());
            this.sB = this.aaB.newTracker(str);
        }
    }

    public Tracker bU(String str) {
        bV(str);
        return this.sB;
    }
}
