package com.chartboost.sdk.Libraries;

import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public interface a {
    public static final int a = (int) TimeUnit.DAYS.toSeconds(7);
    public static final f.a b = f.b(f.b(), new f.e() { // from class: com.chartboost.sdk.Libraries.a.1
        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            int iIntValue = ((Number) obj).intValue();
            return iIntValue >= 200 && iIntValue < 300;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "Must be a valid status code (>=200 && <300)";
        }
    });
}
