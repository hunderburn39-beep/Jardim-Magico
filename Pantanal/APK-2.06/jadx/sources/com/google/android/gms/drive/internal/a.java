package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<AddEventListenerRequest> {
    static void a(AddEventListenerRequest addEventListenerRequest, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, addEventListenerRequest.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) addEventListenerRequest.Ew, i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, addEventListenerRequest.ES);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) addEventListenerRequest.ET, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: C, reason: merged with bridge method [inline-methods] */
    public AddEventListenerRequest createFromParcel(Parcel parcel) {
        PendingIntent pendingIntent;
        int iG;
        DriveId driveId;
        int iG2;
        PendingIntent pendingIntent2 = null;
        int i = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        DriveId driveId2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    PendingIntent pendingIntent3 = pendingIntent2;
                    iG = i;
                    driveId = driveId2;
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    pendingIntent = pendingIntent3;
                    break;
                case 2:
                    iG2 = i2;
                    int i3 = i;
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, DriveId.CREATOR);
                    pendingIntent = pendingIntent2;
                    iG = i3;
                    break;
                case 3:
                    driveId = driveId2;
                    iG2 = i2;
                    PendingIntent pendingIntent4 = pendingIntent2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    pendingIntent = pendingIntent4;
                    break;
                case 4:
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, PendingIntent.CREATOR);
                    iG = i;
                    driveId = driveId2;
                    iG2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    pendingIntent = pendingIntent2;
                    iG = i;
                    driveId = driveId2;
                    iG2 = i2;
                    break;
            }
            i2 = iG2;
            driveId2 = driveId;
            i = iG;
            pendingIntent2 = pendingIntent;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new AddEventListenerRequest(i2, driveId2, i, pendingIntent2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ag, reason: merged with bridge method [inline-methods] */
    public AddEventListenerRequest[] newArray(int i) {
        return new AddEventListenerRequest[i];
    }
}
