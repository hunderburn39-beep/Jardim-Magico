package com.heyzap.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.drive.DriveFile;
import com.heyzap.sdk.ads.MediationTestActivity;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.Header;

/* JADX INFO: loaded from: classes.dex */
public class HeaderActions {
    private final Context context;
    private final AtomicBoolean debugShown = new AtomicBoolean(false);

    public HeaderActions(Context c) {
        this.context = c;
    }

    public void onHeaders(Header[] headers) {
        if (headers != null) {
            boolean showDebug = false;
            int debugDelay = 0;
            for (Header h : headers) {
                try {
                    if ("heyzapLogging".equals(h.getName())) {
                        Utils.setDebug(true);
                        Logger.debug("heyzapLogging header found, enabling verbose output");
                    }
                    if ("showMediationDebugSuite".equals(h.getName()) || "FYBHZShowTestSuite".equals(h.getName())) {
                        showDebug = true;
                        Logger.debug("showMediationDebugSuite header found, showing debug suite");
                    }
                    if ("showMediationDebugSuiteDelay".equals(h.getName()) || "FYBHZShowTestSuiteDelay".equals(h.getName())) {
                        try {
                            Logger.debug("showMediationDebugSuiteDelay header found, delaying debug suite by " + h.getValue() + " seconds");
                            debugDelay = Integer.parseInt(h.getValue());
                        } catch (NumberFormatException e) {
                            Logger.debug("Invalid value for showMediationDebugSuiteDelay: " + h.getValue());
                        }
                    }
                } catch (Exception e2) {
                    Logger.debug("Exception in HeaderActions", e2);
                    return;
                }
            }
            if (showDebug && this.context != null && this.debugShown.compareAndSet(false, true)) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.heyzap.internal.HeaderActions.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Intent i = new Intent(HeaderActions.this.context, (Class<?>) MediationTestActivity.class);
                        i.addFlags(DriveFile.MODE_READ_ONLY);
                        HeaderActions.this.context.startActivity(i);
                    }
                }, debugDelay * 1000);
            }
        }
    }
}
