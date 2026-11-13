package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
public class kd implements com.google.android.gms.wearable.d {
    private final String Xy;
    private final String wp;

    public kd(com.google.android.gms.wearable.d dVar) {
        this.wp = dVar.getId();
        this.Xy = dVar.mc();
    }

    @Override // com.google.android.gms.wearable.d
    public String getId() {
        return this.wp;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.wearable.d
    public String mc() {
        return this.Xy;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: mf, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.wearable.d freeze() {
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataItemAssetEntity[");
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        if (this.wp == null) {
            sb.append(",noid");
        } else {
            sb.append(",");
            sb.append(this.wp);
        }
        sb.append(", key=");
        sb.append(this.Xy);
        sb.append("]");
        return sb.toString();
    }
}
