package com.fyber.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import com.fyber.reporters.a.a;
import com.fyber.utils.FyberLogger;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String string = "";
        Bundle extras = intent.getExtras();
        if (extras != null) {
            string = extras.getString(TapjoyConstants.TJC_REFERRER);
        }
        FyberLogger.i("InstallReferrerReceiver", "Received install referrer. Persisting data. Package name: " + context.getPackageName() + ". Install referrer: " + string);
        UrlQuerySanitizer urlQuerySanitizer = new UrlQuerySanitizer();
        urlQuerySanitizer.setAllowUnregisteredParamaters(true);
        urlQuerySanitizer.parseQuery(string);
        String value = urlQuerySanitizer.getValue("utm_content");
        FyberLogger.i(getClass().getSimpleName(), "SubID extracted from received referrer: " + value);
        a aVar = new a(context);
        aVar.d(string);
        aVar.c(value);
    }
}
