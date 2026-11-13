package com.google.android.gms.analytics;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
class ai extends k<aj> {

    private static class a implements k.a<aj> {
        private final aj wg = new aj();

        @Override // com.google.android.gms.analytics.k.a
        public void a(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.wg.wj = i;
            } else {
                aa.z("int configuration name not recognized:  " + str);
            }
        }

        @Override // com.google.android.gms.analytics.k.a
        public void a(String str, String str2) {
            this.wg.wn.put(str, str2);
        }

        @Override // com.google.android.gms.analytics.k.a
        public void b(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.wg.wh = str2;
                return;
            }
            if (!"ga_sampleFrequency".equals(str)) {
                aa.z("string configuration name not recognized:  " + str);
                return;
            }
            try {
                this.wg.wi = Double.parseDouble(str2);
            } catch (NumberFormatException e) {
                aa.w("Error parsing ga_sampleFrequency value: " + str2);
            }
        }

        @Override // com.google.android.gms.analytics.k.a
        public void c(String str, boolean z) {
            if ("ga_autoActivityTracking".equals(str)) {
                this.wg.wk = z ? 1 : 0;
                return;
            }
            if ("ga_anonymizeIp".equals(str)) {
                this.wg.wl = z ? 1 : 0;
            } else if (!"ga_reportUncaughtExceptions".equals(str)) {
                aa.z("bool configuration name not recognized:  " + str);
            } else {
                this.wg.wm = z ? 1 : 0;
            }
        }

        @Override // com.google.android.gms.analytics.k.a
        /* JADX INFO: renamed from: di, reason: merged with bridge method [inline-methods] */
        public aj cg() {
            return this.wg;
        }
    }

    public ai(Context context) {
        super(context, new a());
    }
}
