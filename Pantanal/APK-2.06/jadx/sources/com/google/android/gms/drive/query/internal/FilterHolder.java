package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;

/* JADX INFO: loaded from: classes.dex */
public class FilterHolder implements SafeParcelable {
    public static final Parcelable.Creator<FilterHolder> CREATOR = new d();
    final ComparisonFilter<?> GK;
    final FieldOnlyFilter GL;
    final LogicalFilter GM;
    final NotFilter GN;
    final InFilter<?> GO;
    final MatchAllFilter GP;
    private final Filter GQ;
    final int xH;

    FilterHolder(int versionCode, ComparisonFilter<?> comparisonField, FieldOnlyFilter fieldOnlyFilter, LogicalFilter logicalFilter, NotFilter notFilter, InFilter<?> containsFilter, MatchAllFilter matchAllFilter) {
        this.xH = versionCode;
        this.GK = comparisonField;
        this.GL = fieldOnlyFilter;
        this.GM = logicalFilter;
        this.GN = notFilter;
        this.GO = containsFilter;
        this.GP = matchAllFilter;
        if (this.GK != null) {
            this.GQ = this.GK;
            return;
        }
        if (this.GL != null) {
            this.GQ = this.GL;
            return;
        }
        if (this.GM != null) {
            this.GQ = this.GM;
            return;
        }
        if (this.GN != null) {
            this.GQ = this.GN;
        } else if (this.GO != null) {
            this.GQ = this.GO;
        } else {
            if (this.GP == null) {
                throw new IllegalArgumentException("At least one filter must be set.");
            }
            this.GQ = this.GP;
        }
    }

    public FilterHolder(Filter filter) {
        this.xH = 1;
        this.GK = filter instanceof ComparisonFilter ? (ComparisonFilter) filter : null;
        this.GL = filter instanceof FieldOnlyFilter ? (FieldOnlyFilter) filter : null;
        this.GM = filter instanceof LogicalFilter ? (LogicalFilter) filter : null;
        this.GN = filter instanceof NotFilter ? (NotFilter) filter : null;
        this.GO = filter instanceof InFilter ? (InFilter) filter : null;
        this.GP = filter instanceof MatchAllFilter ? (MatchAllFilter) filter : null;
        if (this.GK == null && this.GL == null && this.GM == null && this.GN == null && this.GO == null && this.GP == null) {
            throw new IllegalArgumentException("Invalid filter type or null filter.");
        }
        this.GQ = filter;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        d.a(this, out, flags);
    }
}
