package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<Contents> {
    static void a(Contents contents, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, contents.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) contents.Cj, i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, contents.Eu);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, contents.Ev);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, (Parcelable) contents.Ew, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ac, reason: merged with bridge method [inline-methods] */
    public Contents[] newArray(int i) {
        return new Contents[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: y, reason: merged with bridge method [inline-methods] */
    public Contents createFromParcel(Parcel parcel) {
        DriveId driveId = null;
        int iG = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int iG2 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        int iG3 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    iG3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 2:
                    parcelFileDescriptor = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ParcelFileDescriptor.CREATOR);
                    break;
                case 3:
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 4:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    break;
                case 5:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, DriveId.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    break;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new Contents(iG3, parcelFileDescriptor, iG2, iG, driveId);
    }
}
