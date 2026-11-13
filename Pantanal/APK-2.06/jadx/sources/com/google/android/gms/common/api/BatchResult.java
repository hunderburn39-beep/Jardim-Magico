package com.google.android.gms.common.api;

import com.google.android.gms.internal.fq;

/* JADX INFO: loaded from: classes.dex */
public final class BatchResult implements Result {
    private final PendingResult<?>[] AP;
    private final Status wJ;

    BatchResult(Status status, PendingResult<?>[] pendingResults) {
        this.wJ = status;
        this.AP = pendingResults;
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return this.wJ;
    }

    public <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        fq.b(batchResultToken.mId < this.AP.length, "The result token does not belong to this batch");
        PendingResult<?> pendingResult = this.AP[batchResultToken.mId];
        this.AP[batchResultToken.mId] = null;
        return (R) pendingResult.await();
    }
}
