package com.google.android.gms.internal;

import android.content.Context;
import android.os.Parcel;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class ak implements SafeParcelable {
    public static final al CREATOR = new al();
    public final int height;
    public final int heightPixels;
    public final String lS;
    public final boolean lT;
    public final ak[] lU;
    public final int versionCode;
    public final int width;
    public final int widthPixels;

    public ak() {
        this(2, "interstitial_mb", 0, 0, true, 0, 0, null);
    }

    ak(int i, String str, int i2, int i3, boolean z, int i4, int i5, ak[] akVarArr) {
        this.versionCode = i;
        this.lS = str;
        this.height = i2;
        this.heightPixels = i3;
        this.lT = z;
        this.width = i4;
        this.widthPixels = i5;
        this.lU = akVarArr;
    }

    public ak(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    public ak(Context context, AdSize[] adSizeArr) {
        int i;
        AdSize adSize = adSizeArr[0];
        this.versionCode = 2;
        this.lT = false;
        this.width = adSize.getWidth();
        this.height = adSize.getHeight();
        boolean z = this.width == -1;
        boolean z2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (z) {
            this.widthPixels = a(displayMetrics);
            i = (int) (this.widthPixels / displayMetrics.density);
        } else {
            int i2 = this.width;
            this.widthPixels = dv.a(displayMetrics, this.width);
            i = i2;
        }
        int iC = z2 ? c(displayMetrics) : this.height;
        this.heightPixels = dv.a(displayMetrics, iC);
        if (z || z2) {
            this.lS = i + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + iC + "_as";
        } else {
            this.lS = adSize.toString();
        }
        if (adSizeArr.length <= 1) {
            this.lU = null;
            return;
        }
        this.lU = new ak[adSizeArr.length];
        for (int i3 = 0; i3 < adSizeArr.length; i3++) {
            this.lU[i3] = new ak(context, adSizeArr[i3]);
        }
    }

    public ak(ak akVar, ak[] akVarArr) {
        this(2, akVar.lS, akVar.height, akVar.heightPixels, akVar.lT, akVar.width, akVar.widthPixels, akVarArr);
    }

    public static int a(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int b(DisplayMetrics displayMetrics) {
        return (int) (c(displayMetrics) * displayMetrics.density);
    }

    private static int c(DisplayMetrics displayMetrics) {
        int i = (int) (displayMetrics.heightPixels / displayMetrics.density);
        if (i <= 400) {
            return 32;
        }
        return i <= 720 ? 50 : 90;
    }

    public AdSize aA() {
        return com.google.android.gms.ads.a.a(this.width, this.height, this.lS);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        al.a(this, out, flags);
    }
}
