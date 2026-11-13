package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public class d implements Parcelable.Creator<CloseContentsAndUpdateMetadataRequest> {
    static void a(CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, closeContentsAndUpdateMetadataRequest.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) closeContentsAndUpdateMetadataRequest.EV, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, (Parcelable) closeContentsAndUpdateMetadataRequest.EW, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) closeContentsAndUpdateMetadataRequest.EX, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: E, reason: merged with bridge method [inline-methods] */
    public CloseContentsAndUpdateMetadataRequest createFromParcel(Parcel parcel) {
        Contents contents;
        MetadataBundle metadataBundle;
        DriveId driveId;
        int iG;
        Contents contents2 = null;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int i = 0;
        MetadataBundle metadataBundle2 = null;
        DriveId driveId2 = null;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    Contents contents3 = contents2;
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    contents = contents3;
                    break;
                case 2:
                    iG = i;
                    MetadataBundle metadataBundle3 = metadataBundle2;
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, DriveId.CREATOR);
                    contents = contents2;
                    metadataBundle = metadataBundle3;
                    break;
                case 3:
                    driveId = driveId2;
                    iG = i;
                    Contents contents4 = contents2;
                    metadataBundle = (MetadataBundle) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, MetadataBundle.CREATOR);
                    contents = contents4;
                    break;
                case 4:
                    contents = (Contents) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, Contents.CREATOR);
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    iG = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    contents = contents2;
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    iG = i;
                    break;
            }
            i = iG;
            driveId2 = driveId;
            metadataBundle2 = metadataBundle;
            contents2 = contents;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new CloseContentsAndUpdateMetadataRequest(i, driveId2, metadataBundle2, contents2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ai, reason: merged with bridge method [inline-methods] */
    public CloseContentsAndUpdateMetadataRequest[] newArray(int i) {
        return new CloseContentsAndUpdateMetadataRequest[i];
    }
}
