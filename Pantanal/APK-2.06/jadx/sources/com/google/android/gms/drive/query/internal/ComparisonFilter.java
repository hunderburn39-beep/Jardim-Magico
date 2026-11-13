package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;

/* JADX INFO: loaded from: classes.dex */
public class ComparisonFilter<T> implements SafeParcelable, Filter {
    public static final a CREATOR = new a();
    final Operator GG;
    final MetadataBundle GH;
    final MetadataField<T> GI;
    final int xH;

    ComparisonFilter(int i, Operator operator, MetadataBundle metadataBundle) {
        this.xH = i;
        this.GG = operator;
        this.GH = metadataBundle;
        this.GI = (MetadataField<T>) e.b(metadataBundle);
    }

    public ComparisonFilter(Operator operator, SearchableMetadataField<T> field, T value) {
        this(1, operator, MetadataBundle.a(field, value));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        a.a(this, out, flags);
    }
}
