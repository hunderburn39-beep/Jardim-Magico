package com.fyber.cache.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.fyber.cache.CacheVideoDownloadService;
import com.fyber.utils.FyberLogger;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class ConfigurationBroadcastReceiver extends BroadcastReceiver {
    private static AlarmManager a;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (a == null) {
            a = (AlarmManager) context.getSystemService("alarm");
        }
        PendingIntent service = PendingIntent.getService(context, 0, new Intent(context, (Class<?>) CacheVideoDownloadService.class), 134217728);
        int intExtra = intent.getIntExtra("refresh.interval", TimeConstants.SECONDSPERHOUR);
        int i = intExtra * 1000;
        if (i <= 0) {
            i = 3600000;
        }
        FyberLogger.i("ConfigurationBroadcastReceiver", "Next precache query will occur in " + intExtra + " seconds");
        a.cancel(service);
        a.set(3, SystemClock.elapsedRealtime() + ((long) i), service);
    }
}
