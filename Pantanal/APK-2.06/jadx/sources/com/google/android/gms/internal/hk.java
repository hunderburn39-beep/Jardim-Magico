package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.tapjoy.TJAdUnitConstants;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class hk implements SafeParcelable {
    public static final hl CREATOR = new hl();
    static final long OF = TimeUnit.HOURS.toMillis(1);
    private final hg OG;
    private final long Oc;
    private final int mPriority;
    final int xH;

    public hk(int i, hg hgVar, long j, int i2) {
        this.xH = i;
        this.OG = hgVar;
        this.Oc = j;
        this.mPriority = i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        hl hlVar = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof hk)) {
            return false;
        }
        hk hkVar = (hk) object;
        return this.OG.equals(hkVar.OG) && this.Oc == hkVar.Oc && this.mPriority == hkVar.mPriority;
    }

    public long getInterval() {
        return this.Oc;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public hg hZ() {
        return this.OG;
    }

    public int hashCode() {
        return fo.hashCode(this.OG, Long.valueOf(this.Oc), Integer.valueOf(this.mPriority));
    }

    public String toString() {
        return fo.e(this).a("filter", this.OG).a(TJAdUnitConstants.String.INTERVAL, Long.valueOf(this.Oc)).a("priority", Integer.valueOf(this.mPriority)).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        hl hlVar = CREATOR;
        hl.a(this, parcel, flags);
    }
}
