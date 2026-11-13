package com.google.android.gms.drive;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class MetadataBuffer extends DataBuffer<Metadata> {
    private static final String[] EL;
    private final String EM;
    private a EN;

    private static class a extends Metadata {
        private final DataHolder BB;
        private final int BE;
        private final int EO;

        public a(DataHolder dataHolder, int i) {
            this.BB = dataHolder;
            this.EO = i;
            this.BE = dataHolder.G(i);
        }

        @Override // com.google.android.gms.drive.Metadata
        protected <T> T a(MetadataField<T> metadataField) {
            return metadataField.a(this.BB, this.EO, this.BE);
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: fB, reason: merged with bridge method [inline-methods] */
        public Metadata freeze() {
            MetadataBundle metadataBundleFT = MetadataBundle.fT();
            Iterator<MetadataField<?>> it = com.google.android.gms.drive.metadata.internal.c.fS().iterator();
            while (it.hasNext()) {
                it.next().a(this.BB, metadataBundleFT, this.EO, this.BE);
            }
            return new b(metadataBundleFT);
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return !this.BB.isClosed();
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        Iterator<MetadataField<?>> it = com.google.android.gms.drive.metadata.internal.c.fS().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().fR());
        }
        EL = (String[]) arrayList.toArray(new String[0]);
    }

    public MetadataBuffer(DataHolder dataHolder, String nextPageToken) {
        super(dataHolder);
        this.EM = nextPageToken;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.DataBuffer
    public Metadata get(int row) {
        a aVar = this.EN;
        if (aVar != null && aVar.EO == row) {
            return aVar;
        }
        a aVar2 = new a(this.BB, row);
        this.EN = aVar2;
        return aVar2;
    }

    public String getNextPageToken() {
        return this.EM;
    }
}
