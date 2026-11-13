package com.heyzap.common.concurrency;

import com.heyzap.internal.DevLogger;
import com.heyzap.internal.Utils;
import com.heyzap.sdk.ads.HeyzapAds;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
public class WrappedRunnable<V> implements Runnable {
    private ExecutorService executorService;
    private final Runnable inner;

    public WrappedRunnable(Runnable inner, ExecutorService executorService) {
        this.inner = inner;
        this.executorService = executorService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.inner.run();
        } catch (Throwable e) {
            DevLogger.error(e, "Heyzap has encountered an error and is shutting down.");
            this.executorService.shutdown();
            HeyzapAds.shutdown();
            if (Utils.isDebug(HeyzapAds.applicationContext).booleanValue()) {
                System.exit(0);
            }
        }
    }
}
