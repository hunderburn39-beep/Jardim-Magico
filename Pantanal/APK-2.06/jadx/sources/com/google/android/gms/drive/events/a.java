package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<ChangeEvent> {
    static void a(ChangeEvent changeEvent, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, changeEvent.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) changeEvent.Ew, i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, changeEvent.ER);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: A, reason: merged with bridge method [inline-methods] */
    public ChangeEvent createFromParcel(Parcel parcel) {
        int iG;
        DriveId driveId;
        int iG2;
        int i = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        DriveId driveId2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    int i3 = i;
                    driveId = driveId2;
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    iG = i3;
                    break;
                case 2:
                    DriveId driveId3 = (DriveId) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, DriveId.CREATOR);
                    iG2 = i2;
                    iG = i;
                    driveId = driveId3;
                    break;
                case 3:
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    driveId = driveId2;
                    iG2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    iG = i;
                    driveId = driveId2;
                    iG2 = i2;
                    break;
            }
            i2 = iG2;
            driveId2 = driveId;
            i = iG;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new ChangeEvent(i2, driveId2, i);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ae, reason: merged with bridge method [inline-methods] */
    public ChangeEvent[] newArray(int i) {
        return new ChangeEvent[i];
    }
}
