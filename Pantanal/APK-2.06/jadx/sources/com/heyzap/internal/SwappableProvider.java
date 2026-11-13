package com.heyzap.internal;

/* JADX INFO: loaded from: classes.dex */
public class SwappableProvider<T> implements Provider<T> {
    private T ref;

    private SwappableProvider(T in) {
        this.ref = in;
    }

    @Override // com.heyzap.internal.Provider
    public T get() {
        return this.ref;
    }

    public void set(T in) {
        this.ref = in;
    }

    public static <L> SwappableProvider<L> of(L in) {
        return new SwappableProvider<>(in);
    }
}
