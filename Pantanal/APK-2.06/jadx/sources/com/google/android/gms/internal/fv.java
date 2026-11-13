package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class fv implements SafeParcelable {
    public static final fw CREATOR = new fw();
    private final fx DS;
    private final int xH;

    fv(int i, fx fxVar) {
        this.xH = i;
        this.DS = fxVar;
    }

    private fv(fx fxVar) {
        this.xH = 1;
        this.DS = fxVar;
    }

    public static fv a(ga.b<?, ?> bVar) {
        if (bVar instanceof fx) {
            return new fv((fx) bVar);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        fw fwVar = CREATOR;
        return 0;
    }

    fx eT() {
        return this.DS;
    }

    public ga.b<?, ?> eU() {
        if (this.DS != null) {
            return this.DS;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        fw fwVar = CREATOR;
        fw.a(this, out, flags);
    }
}
