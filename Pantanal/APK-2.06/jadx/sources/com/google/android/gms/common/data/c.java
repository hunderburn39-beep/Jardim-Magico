package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class c<T extends SafeParcelable> extends DataBuffer<T> {
    private static final String[] BF = {"data"};
    private final Parcelable.Creator<T> BG;

    public c(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.BG = creator;
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    /* JADX INFO: renamed from: F, reason: merged with bridge method [inline-methods] */
    public T get(int i) {
        byte[] byteArray = this.BB.getByteArray("data", i, 0);
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(byteArray, 0, byteArray.length);
        parcelObtain.setDataPosition(0);
        T tCreateFromParcel = this.BG.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return tCreateFromParcel;
    }
}
