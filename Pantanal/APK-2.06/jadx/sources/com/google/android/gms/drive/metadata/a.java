package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fq;
import com.tapjoy.TJAdUnitConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class a<T> implements MetadataField<T> {
    private final String FM;
    private final Set<String> FN;
    private final int FO;

    protected a(String str, int i) {
        this.FM = (String) fq.b(str, (Object) "fieldName");
        this.FN = Collections.singleton(str);
        this.FO = i;
    }

    protected a(String str, Collection<String> collection, int i) {
        this.FM = (String) fq.b(str, (Object) "fieldName");
        this.FN = Collections.unmodifiableSet(new HashSet(collection));
        this.FO = i;
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final T a(DataHolder dataHolder, int i, int i2) {
        Iterator<String> it = this.FN.iterator();
        while (it.hasNext()) {
            if (dataHolder.hasNull(it.next(), i, i2)) {
                return null;
            }
        }
        return b(dataHolder, i, i2);
    }

    protected abstract void a(Bundle bundle, T t);

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final void a(DataHolder dataHolder, MetadataBundle metadataBundle, int i, int i2) {
        fq.b(dataHolder, "dataHolder");
        fq.b(metadataBundle, TJAdUnitConstants.String.BUNDLE);
        metadataBundle.b(this, a(dataHolder, i, i2));
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final void a(T t, Bundle bundle) {
        fq.b(bundle, TJAdUnitConstants.String.BUNDLE);
        if (t == null) {
            bundle.putString(getName(), null);
        } else {
            a(bundle, t);
        }
    }

    protected abstract T b(DataHolder dataHolder, int i, int i2);

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final T d(Bundle bundle) {
        fq.b(bundle, TJAdUnitConstants.String.BUNDLE);
        if (bundle.get(getName()) != null) {
            return e(bundle);
        }
        return null;
    }

    protected abstract T e(Bundle bundle);

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final Collection<String> fR() {
        return this.FN;
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final String getName() {
        return this.FM;
    }

    public String toString() {
        return this.FM;
    }
}
