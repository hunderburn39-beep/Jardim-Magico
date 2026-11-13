package com.google.android.gms.common.api;

import android.os.Looper;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class Batch extends a.AbstractC0032a<BatchResult> {
    private int AM;
    private boolean AN;
    private boolean AO;
    private final PendingResult<?>[] AP;
    private final Object li;

    public static final class Builder {
        private List<PendingResult<?>> AR = new ArrayList();
        private Looper AS;

        public Builder(GoogleApiClient googleApiClient) {
            this.AS = googleApiClient.getLooper();
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.AR.size());
            this.AR.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.AR, this.AS);
        }
    }

    private Batch(List<PendingResult<?>> pendingResultList, Looper looper) {
        super(new a.c(looper));
        this.li = new Object();
        this.AM = pendingResultList.size();
        this.AP = new PendingResult[this.AM];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= pendingResultList.size()) {
                return;
            }
            PendingResult<?> pendingResult = pendingResultList.get(i2);
            this.AP[i2] = pendingResult;
            pendingResult.a(new PendingResult.a() { // from class: com.google.android.gms.common.api.Batch.1
                @Override // com.google.android.gms.common.api.PendingResult.a
                public void l(Status status) {
                    synchronized (Batch.this.li) {
                        if (Batch.this.isCanceled()) {
                            return;
                        }
                        if (status.isCanceled()) {
                            Batch.this.AO = true;
                        } else if (!status.isSuccess()) {
                            Batch.this.AN = true;
                        }
                        Batch.b(Batch.this);
                        if (Batch.this.AM == 0) {
                            if (Batch.this.AO) {
                                Batch.super.cancel();
                            } else {
                                Batch.this.b(new BatchResult(Batch.this.AN ? new Status(13) : Status.Bv, Batch.this.AP));
                            }
                        }
                    }
                }
            });
            i = i2 + 1;
        }
    }

    static /* synthetic */ int b(Batch batch) {
        int i = batch.AM;
        batch.AM = i - 1;
        return i;
    }

    @Override // com.google.android.gms.common.api.a.AbstractC0032a, com.google.android.gms.common.api.PendingResult
    public void cancel() {
        super.cancel();
        for (PendingResult<?> pendingResult : this.AP) {
            pendingResult.cancel();
        }
    }

    @Override // com.google.android.gms.common.api.a.AbstractC0032a
    /* JADX INFO: renamed from: createFailedResult, reason: merged with bridge method [inline-methods] */
    public BatchResult d(Status status) {
        return new BatchResult(status, this.AP);
    }
}
