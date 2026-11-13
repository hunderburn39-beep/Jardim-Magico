package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.kc;

/* JADX INFO: loaded from: classes.dex */
public class b extends com.google.android.gms.common.data.d<a> implements Result {
    private final Status wJ;

    public b(DataHolder dataHolder) {
        super(dataHolder);
        this.wJ = new Status(dataHolder.getStatusCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.data.d
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public a c(int i, int i2) {
        return new kc(this.BB, i, i2);
    }

    @Override // com.google.android.gms.common.data.d
    protected String getPrimaryDataMarkerColumn() {
        return "path";
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return this.wJ;
    }
}
