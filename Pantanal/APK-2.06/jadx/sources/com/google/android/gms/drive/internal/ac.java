package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;

/* JADX INFO: loaded from: classes.dex */
public class ac implements Parcelable.Creator<OnEventResponse> {
    static void a(OnEventResponse onEventResponse, Parcel parcel, int i) {
        int iP = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, onEventResponse.xH);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, onEventResponse.ES);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, (Parcelable) onEventResponse.FH, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, (Parcelable) onEventResponse.FI, i, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, iP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: Q, reason: merged with bridge method [inline-methods] */
    public OnEventResponse createFromParcel(Parcel parcel) {
        ConflictEvent conflictEvent;
        ChangeEvent changeEvent;
        int iG;
        int iG2;
        ConflictEvent conflictEvent2 = null;
        int i = 0;
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        ChangeEvent changeEvent2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(iN)) {
                case 1:
                    ConflictEvent conflictEvent3 = conflictEvent2;
                    changeEvent = changeEvent2;
                    iG = i;
                    iG2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    conflictEvent = conflictEvent3;
                    break;
                case 2:
                    iG2 = i2;
                    ChangeEvent changeEvent3 = changeEvent2;
                    iG = com.google.android.gms.common.internal.safeparcel.a.g(parcel, iN);
                    conflictEvent = conflictEvent2;
                    changeEvent = changeEvent3;
                    break;
                case 3:
                    iG = i;
                    iG2 = i2;
                    ConflictEvent conflictEvent4 = conflictEvent2;
                    changeEvent = (ChangeEvent) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ChangeEvent.CREATOR);
                    conflictEvent = conflictEvent4;
                    break;
                case 4:
                    conflictEvent = (ConflictEvent) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iN, ConflictEvent.CREATOR);
                    changeEvent = changeEvent2;
                    iG = i;
                    iG2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iN);
                    conflictEvent = conflictEvent2;
                    changeEvent = changeEvent2;
                    iG = i;
                    iG2 = i2;
                    break;
            }
            i2 = iG2;
            i = iG;
            changeEvent2 = changeEvent;
            conflictEvent2 = conflictEvent;
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        return new OnEventResponse(i2, i, changeEvent2, conflictEvent2);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: au, reason: merged with bridge method [inline-methods] */
    public OnEventResponse[] newArray(int i) {
        return new OnEventResponse[i];
    }
}
