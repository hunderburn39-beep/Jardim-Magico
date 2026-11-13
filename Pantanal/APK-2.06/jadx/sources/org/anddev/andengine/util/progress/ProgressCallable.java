package org.anddev.andengine.util.progress;

/* JADX INFO: loaded from: classes.dex */
public interface ProgressCallable<T> {
    T call(IProgressListener iProgressListener) throws Exception;
}
