package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class InFilter<T> implements SafeParcelable, Filter {
    public static final f CREATOR = new f();
    final MetadataBundle GH;
    private final com.google.android.gms.drive.metadata.b<T> GR;
    final int xH;

    InFilter(int versionCode, MetadataBundle value) {
        this.xH = versionCode;
        this.GH = value;
        this.GR = (com.google.android.gms.drive.metadata.b) e.b(value);
    }

    public InFilter(SearchableCollectionMetadataField<T> field, T value) {
        this(1, MetadataBundle.a(field, Collections.singleton(value)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        f.a(this, out, flags);
    }
}
