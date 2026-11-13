package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public final class b extends Metadata {
    private final MetadataBundle ED;

    public b(MetadataBundle metadataBundle) {
        this.ED = metadataBundle;
    }

    @Override // com.google.android.gms.drive.Metadata
    protected <T> T a(MetadataField<T> metadataField) {
        return (T) this.ED.a(metadataField);
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: fB, reason: merged with bridge method [inline-methods] */
    public Metadata freeze() {
        return new b(MetadataBundle.a(this.ED));
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return this.ED != null;
    }

    public String toString() {
        return "Metadata [mImpl=" + this.ED + "]";
    }
}
