package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class kc extends com.google.android.gms.common.data.b implements com.google.android.gms.wearable.a {
    private final int LE;

    public kc(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.LE = i2;
    }

    @Override // com.google.android.gms.wearable.a
    public int getType() {
        return getInteger("event_type");
    }

    @Override // com.google.android.gms.wearable.a
    public com.google.android.gms.wearable.c lZ() {
        return new kg(this.BB, this.BD, this.LE);
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: me, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.wearable.a freeze() {
        return new kb(this);
    }
}
