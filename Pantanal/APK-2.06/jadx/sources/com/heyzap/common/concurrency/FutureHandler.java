package com.heyzap.common.concurrency;

/* JADX INFO: loaded from: classes.dex */
public interface FutureHandler<V, O> {
    O handle(V v, Exception exc);
}
