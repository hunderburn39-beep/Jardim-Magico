package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.query.Query;

/* JADX INFO: loaded from: classes.dex */
public class aj implements Parcelable.Creator<QueryRequest> {
    static void a(QueryRequest queryRequest, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, queryRequest.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) queryRequest.FL, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] */
    public QueryRequest createFromParcel(Parcel parcel) {
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG = 0;
        Query query = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    query = (Query) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Query.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new QueryRequest(iG, query);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: aB, reason: merged with bridge method [inline-methods] */
    public QueryRequest[] newArray(int i) {
        return new QueryRequest[i];
    }
}
