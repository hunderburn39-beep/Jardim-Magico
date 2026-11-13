package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
public class kb implements com.google.android.gms.wearable.a {
    private int LF;
    private com.google.android.gms.wearable.c adC;

    public kb(com.google.android.gms.wearable.a aVar) {
        this.LF = aVar.getType();
        this.adC = aVar.lZ().freeze();
    }

    @Override // com.google.android.gms.wearable.a
    public int getType() {
        return this.LF;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.wearable.a
    public com.google.android.gms.wearable.c lZ() {
        return this.adC;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: me, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.wearable.a freeze() {
        return this;
    }
}
