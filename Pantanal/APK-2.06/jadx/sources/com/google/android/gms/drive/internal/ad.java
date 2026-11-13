package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public class ad implements Parcelable.Creator<OnListEntriesResponse> {
    static void a(OnListEntriesResponse onListEntriesResponse, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, onListEntriesResponse.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) onListEntriesResponse.FJ, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, onListEntriesResponse.Fg);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: R, reason: merged with bridge method [inline-methods] */
    public OnListEntriesResponse createFromParcel(Parcel parcel) {
        boolean zC;
        DataHolder dataHolder;
        int iG;
        boolean z = false;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        DataHolder dataHolder2 = null;
        int i = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    boolean z2 = z;
                    dataHolder = dataHolder2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    zC = z2;
                    break;
                case 2:
                    DataHolder dataHolder3 = (DataHolder) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, DataHolder.CREATOR);
                    iG = i;
                    zC = z;
                    dataHolder = dataHolder3;
                    break;
                case 3:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iN);
                    dataHolder = dataHolder2;
                    iG = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    zC = z;
                    dataHolder = dataHolder2;
                    iG = i;
                    break;
            }
            i = iG;
            dataHolder2 = dataHolder;
            z = zC;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new OnListEntriesResponse(i, dataHolder2, z);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: av, reason: merged with bridge method [inline-methods] */
    public OnListEntriesResponse[] newArray(int i) {
        return new OnListEntriesResponse[i];
    }
}
