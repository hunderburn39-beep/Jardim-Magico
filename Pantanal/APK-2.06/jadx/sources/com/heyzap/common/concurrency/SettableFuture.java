package com.heyzap.common.concurrency;

/* JADX INFO: loaded from: classes.dex */
public final class SettableFuture<V> extends AbstractFuture<V> {
    public static <V> SettableFuture<V> create() {
        return new SettableFuture<>();
    }

    private SettableFuture() {
    }

    @Override // com.heyzap.common.concurrency.AbstractFuture
    public boolean set(V value) {
        return super.set(value);
    }

    @Override // com.heyzap.common.concurrency.AbstractFuture
    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }
}
