package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.MatchAllFilter;
import com.google.android.gms.drive.query.internal.Operator;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Query implements SafeParcelable {
    public static final Parcelable.Creator<Query> CREATOR = new a();
    final LogicalFilter GA;
    final String GB;
    final SortOrder GC;
    final int xH;

    public static class Builder {
        private String GB;
        private SortOrder GC;
        private final List<Filter> GD = new ArrayList();

        public Builder a(SortOrder sortOrder) {
            this.GC = sortOrder;
            return this;
        }

        public Builder addFilter(Filter filter) {
            if (!(filter instanceof MatchAllFilter)) {
                this.GD.add(filter);
            }
            return this;
        }

        public Query build() {
            return new Query(new LogicalFilter(Operator.GZ, this.GD), this.GB, this.GC);
        }

        public Builder setPageToken(String token) {
            this.GB = token;
            return this;
        }
    }

    Query(int versionCode, LogicalFilter clause, String pageToken, SortOrder sortOrder) {
        this.xH = versionCode;
        this.GA = clause;
        this.GB = pageToken;
        this.GC = sortOrder;
    }

    Query(LogicalFilter clause, String pageToken, SortOrder sortOrder) {
        this(1, clause, pageToken, sortOrder);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SortOrder fV() {
        return this.GC;
    }

    public Filter getFilter() {
        return this.GA;
    }

    public String getPageToken() {
        return this.GB;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        a.a(this, out, flags);
    }
}
