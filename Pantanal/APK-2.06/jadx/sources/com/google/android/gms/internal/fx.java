package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class fx implements SafeParcelable, ga.b<String, Integer> {
    public static final fy CREATOR = new fy();
    private final HashMap<String, Integer> DT;
    private final HashMap<Integer, String> DU;
    private final ArrayList<a> DV;
    private final int xH;

    public static final class a implements SafeParcelable {
        public static final fz CREATOR = new fz();
        final String DW;
        final int DX;
        final int versionCode;

        a(int i, String str, int i2) {
            this.versionCode = i;
            this.DW = str;
            this.DX = i2;
        }

        a(String str, int i) {
            this.versionCode = 1;
            this.DW = str;
            this.DX = i;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            fz fzVar = CREATOR;
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            fz fzVar = CREATOR;
            fz.a(this, out, flags);
        }
    }

    public fx() {
        this.xH = 1;
        this.DT = new HashMap<>();
        this.DU = new HashMap<>();
        this.DV = null;
    }

    fx(int i, ArrayList<a> arrayList) {
        this.xH = i;
        this.DT = new HashMap<>();
        this.DU = new HashMap<>();
        this.DV = null;
        a(arrayList);
    }

    private void a(ArrayList<a> arrayList) {
        for (a aVar : arrayList) {
            f(aVar.DW, aVar.DX);
        }
    }

    @Override // com.google.android.gms.internal.ga.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public String g(Integer num) {
        String str = this.DU.get(num);
        return (str == null && this.DT.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        fy fyVar = CREATOR;
        return 0;
    }

    ArrayList<a> eV() {
        ArrayList<a> arrayList = new ArrayList<>();
        for (String str : this.DT.keySet()) {
            arrayList.add(new a(str, this.DT.get(str).intValue()));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.internal.ga.b
    public int eW() {
        return 7;
    }

    @Override // com.google.android.gms.internal.ga.b
    public int eX() {
        return 0;
    }

    public fx f(String str, int i) {
        this.DT.put(str, Integer.valueOf(i));
        this.DU.put(Integer.valueOf(i), str);
        return this;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        fy fyVar = CREATOR;
        fy.a(this, out, flags);
    }
}
