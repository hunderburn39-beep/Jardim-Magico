package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public class ke extends com.google.android.gms.common.data.b implements com.google.android.gms.wearable.d {
    public ke(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    @Override // com.google.android.gms.wearable.d
    public String getId() {
        return getString("asset_id");
    }

    @Override // com.google.android.gms.wearable.d
    public String mc() {
        return getString("asset_key");
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: mf, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.wearable.d freeze() {
        return new kd(this);
    }
}
