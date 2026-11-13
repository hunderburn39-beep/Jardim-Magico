package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class cx implements SafeParcelable {
    public static final cy CREATOR = new cy();
    public final ApplicationInfo applicationInfo;
    public final String kH;
    public final dx kK;
    public final ak kN;
    public final Bundle pf;
    public final ah pg;
    public final PackageInfo ph;
    public final String pi;
    public final String pj;
    public final String pk;
    public final Bundle pl;
    public final int versionCode;

    public static final class a {
        public final ApplicationInfo applicationInfo;
        public final String kH;
        public final dx kK;
        public final ak kN;
        public final Bundle pf;
        public final ah pg;
        public final PackageInfo ph;
        public final String pj;
        public final String pk;
        public final Bundle pl;

        public a(Bundle bundle, ah ahVar, ak akVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, dx dxVar, Bundle bundle2) {
            this.pf = bundle;
            this.pg = ahVar;
            this.kN = akVar;
            this.kH = str;
            this.applicationInfo = applicationInfo;
            this.ph = packageInfo;
            this.pj = str2;
            this.pk = str3;
            this.kK = dxVar;
            this.pl = bundle2;
        }
    }

    cx(int i, Bundle bundle, ah ahVar, ak akVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, dx dxVar, Bundle bundle2) {
        this.versionCode = i;
        this.pf = bundle;
        this.pg = ahVar;
        this.kN = akVar;
        this.kH = str;
        this.applicationInfo = applicationInfo;
        this.ph = packageInfo;
        this.pi = str2;
        this.pj = str3;
        this.pk = str4;
        this.kK = dxVar;
        this.pl = bundle2;
    }

    public cx(Bundle bundle, ah ahVar, ak akVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, dx dxVar, Bundle bundle2) {
        this(2, bundle, ahVar, akVar, str, applicationInfo, packageInfo, str2, str3, str4, dxVar, bundle2);
    }

    public cx(a aVar, String str) {
        this(aVar.pf, aVar.pg, aVar.kN, aVar.kH, aVar.applicationInfo, aVar.ph, str, aVar.pj, aVar.pk, aVar.kK, aVar.pl);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        cy.a(this, out, flags);
    }
}
