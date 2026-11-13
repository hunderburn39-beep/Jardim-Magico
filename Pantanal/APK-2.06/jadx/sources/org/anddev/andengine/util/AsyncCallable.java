package org.anddev.andengine.util;

/* JADX INFO: loaded from: classes.dex */
public interface AsyncCallable<T> {
    void call(Callback<T> callback, Callback<Exception> callback2);
}
