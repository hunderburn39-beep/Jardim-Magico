package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class hg implements SafeParcelable {
    public static final hh CREATOR = new hh();
    final List<hm> OA;
    private final String OB;
    private final boolean OC;
    private final Set<hm> OD;
    final int xH;

    hg(int i, List<hm> list, String str, boolean z) {
        this.xH = i;
        this.OA = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.OB = str == null ? "" : str;
        this.OC = z;
        if (this.OA.isEmpty()) {
            this.OD = Collections.emptySet();
        } else {
            this.OD = Collections.unmodifiableSet(new HashSet(this.OA));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        hh hhVar = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof hg)) {
            return false;
        }
        hg hgVar = (hg) object;
        return this.OD.equals(hgVar.OD) && this.OC == hgVar.OC;
    }

    @Deprecated
    public String hW() {
        return this.OB;
    }

    public boolean hX() {
        return this.OC;
    }

    public int hashCode() {
        return fo.hashCode(this.OD, Boolean.valueOf(this.OC));
    }

    public String toString() {
        return fo.e(this).a("types", this.OD).a("requireOpenNow", Boolean.valueOf(this.OC)).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        hh hhVar = CREATOR;
        hh.a(this, parcel, flags);
    }
}
