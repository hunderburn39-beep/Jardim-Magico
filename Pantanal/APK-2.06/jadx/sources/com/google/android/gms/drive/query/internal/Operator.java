package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class Operator implements SafeParcelable {
    public static final Parcelable.Creator<Operator> CREATOR = new j();
    public static final Operator GU = new Operator("=");
    public static final Operator GV = new Operator("<");
    public static final Operator GW = new Operator("<=");
    public static final Operator GX = new Operator(">");
    public static final Operator GY = new Operator(">=");
    public static final Operator GZ = new Operator("and");
    public static final Operator Ha = new Operator("or");
    public static final Operator Hb = new Operator("not");
    public static final Operator Hc = new Operator("contains");
    final String mTag;
    final int xH;

    Operator(int versionCode, String tag) {
        this.xH = versionCode;
        this.mTag = tag;
    }

    private Operator(String tag) {
        this(1, tag);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Operator operator = (Operator) obj;
            if (this.mTag == null) {
                return operator.mTag == null;
            }
            return this.mTag.equals(operator.mTag);
        }
        return false;
    }

    public int hashCode() {
        return (this.mTag == null ? 0 : this.mTag.hashCode()) + 31;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        j.a(this, out, flags);
    }
}
