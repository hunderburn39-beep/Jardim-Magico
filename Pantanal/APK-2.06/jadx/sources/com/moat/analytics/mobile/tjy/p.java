package com.moat.analytics.mobile.tjy;

import android.util.Log;
import android.webkit.ValueCallback;

/* JADX INFO: loaded from: classes.dex */
class p implements ValueCallback {
    final /* synthetic */ n a;

    p(n nVar) {
        this.a = nVar;
    }

    @Override // android.webkit.ValueCallback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onReceiveValue(String str) {
        if (str == null || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("false")) {
            if (this.a.d.b()) {
                Log.d("MoatJavaScriptBridge", "Received value is:" + (str == null ? "null" : "(String)" + str));
            }
            if (this.a.e == -1 || this.a.e == 50) {
                this.a.g();
            }
            n.e(this.a);
            return;
        }
        if (str.equalsIgnoreCase("true")) {
            this.a.e = -1;
            this.a.e();
        } else if (this.a.d.b()) {
            Log.d("MoatJavaScriptBridge", "Received unusual value from Javascript:" + str);
        }
    }
}
