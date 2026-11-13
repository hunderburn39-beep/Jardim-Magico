package com.heyzap.wrapper;

import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.FutureHandler;
import com.heyzap.common.concurrency.FutureUtils;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.internal.Logger;
import com.heyzap.sdk.ads.HeyzapAds;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractFetchWrapper<V> {
    private AtomicReference<Boolean> fetchQueued = new AtomicReference<>(false);
    private SettableFuture<Void> fetchReadyFuture = SettableFuture.create();
    private SettableFuture<CachedAd<V>> cachedAdFuture = SettableFuture.create();
    protected ExecutorService executorService = ExecutorPool.getInstance();

    protected abstract SettableFuture<CachedAd<V>> fetch();

    public SettableFuture<CachedAd<V>> ensureFetchQueued() {
        Logger.debug(getClass() + " ensureFetchQueued called");
        if (this.fetchQueued.compareAndSet(false, true)) {
            Logger.debug(getClass() + " ensureFetchQueued queuing new fetch");
            FutureUtils.allOf(Arrays.asList(HeyzapAds.adaptersReadyFuture, this.fetchReadyFuture), ExecutorPool.getInstance()).addListener(new Runnable() { // from class: com.heyzap.wrapper.AbstractFetchWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    Logger.debug(AbstractFetchWrapper.this.getClass() + " ensureFetchQueued fetch opportunity found, fetching");
                    AbstractFetchWrapper.this.fetchReadyFuture = SettableFuture.create();
                    AbstractFetchWrapper.this.fetchQueued.set(false);
                    SettableFuture<CachedAd<V>> settableFutureFetch = AbstractFetchWrapper.this.fetch();
                    FutureUtils.bind(settableFutureFetch, AbstractFetchWrapper.this.cachedAdFuture, ExecutorPool.getInstance());
                    settableFutureFetch.addHandler(new FutureHandler<CachedAd<V>, Void>() { // from class: com.heyzap.wrapper.AbstractFetchWrapper.1.1
                        @Override // com.heyzap.common.concurrency.FutureHandler
                        public Void handle(CachedAd value, Exception e) {
                            if (value == null) {
                                Logger.debug(AbstractFetchWrapper.this.getClass() + " ensureFetchQueued fetch failed too many times, awaiting next manual fetch");
                                AbstractFetchWrapper.this.cachedAdFuture = SettableFuture.create();
                                AbstractFetchWrapper.this.fetchReadyFuture.set(null);
                            }
                            return null;
                        }
                    }, AbstractFetchWrapper.this.executorService);
                }
            }, this.executorService);
        }
        return this.cachedAdFuture;
    }

    public CachedAd getCachedAd() {
        return (CachedAd) FutureUtils.getImmediatelyOrDefault(this.cachedAdFuture, null);
    }

    public CachedAd<V> consumeCachedAd() {
        CachedAd<V> cachedAd = getCachedAd();
        if (cachedAd != null) {
            this.cachedAdFuture = SettableFuture.create();
        }
        return cachedAd;
    }

    public void onStart() {
        Logger.debug(getClass() + " onStart");
        this.fetchReadyFuture.set(null);
    }

    public void onDisplayStarted() {
        Logger.debug(getClass() + " onDisplayStarted");
        final SettableFuture<Void> settableFuture = this.fetchReadyFuture;
        ExecutorPool.getInstance().schedule(new Runnable() { // from class: com.heyzap.wrapper.AbstractFetchWrapper.2
            @Override // java.lang.Runnable
            public void run() {
                Logger.debug(getClass() + " onDisplayStarted timeout hit");
                settableFuture.set(null);
            }
        }, 90L, TimeUnit.SECONDS);
    }

    public void onDisplayCompleted() {
        Logger.debug(getClass() + " onDisplayCompleted");
        final SettableFuture<Void> settableFuture = this.fetchReadyFuture;
        ExecutorPool.getInstance().schedule(new Runnable() { // from class: com.heyzap.wrapper.AbstractFetchWrapper.3
            @Override // java.lang.Runnable
            public void run() {
                settableFuture.set(null);
            }
        }, 2L, TimeUnit.SECONDS);
    }
}
