package com.google.android.gms.analytics;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
class v extends k<w> {

    private static class a implements k.a<w> {
        private final w uU = new w();

        @Override // com.google.android.gms.analytics.k.a
        public void a(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.uU.uW = i;
            } else {
                aa.z("int configuration name not recognized:  " + str);
            }
        }

        @Override // com.google.android.gms.analytics.k.a
        public void a(String str, String str2) {
        }

        @Override // com.google.android.gms.analytics.k.a
        public void b(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.uU.so = str2;
                return;
            }
            if ("ga_appVersion".equals(str)) {
                this.uU.sp = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.uU.uV = str2;
            } else {
                aa.z("string configuration name not recognized:  " + str);
            }
        }

        @Override // com.google.android.gms.analytics.k.a
        public void c(String str, boolean z) {
            if (!"ga_dryRun".equals(str)) {
                aa.z("bool configuration name not recognized:  " + str);
            } else {
                this.uU.uX = z ? 1 : 0;
            }
        }

        @Override // com.google.android.gms.analytics.k.a
        /* JADX INFO: renamed from: cB, reason: merged with bridge method [inline-methods] */
        public w cg() {
            return this.uU;
        }
    }

    public v(Context context) {
        super(context, new a());
    }
}
