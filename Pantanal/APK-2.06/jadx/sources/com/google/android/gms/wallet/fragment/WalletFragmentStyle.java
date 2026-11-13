package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class WalletFragmentStyle implements SafeParcelable {
    public static final Parcelable.Creator<WalletFragmentStyle> CREATOR = new c();
    Bundle acT;
    int acU;
    final int xH;

    public WalletFragmentStyle() {
        this.xH = 1;
        this.acT = new Bundle();
    }

    WalletFragmentStyle(int versionCode, Bundle attributes, int styleResourceId) {
        this.xH = versionCode;
        this.acT = attributes;
        this.acU = styleResourceId;
    }

    private void a(TypedArray typedArray, int i, String str) {
        TypedValue typedValuePeekValue;
        if (this.acT.containsKey(str) || (typedValuePeekValue = typedArray.peekValue(i)) == null) {
            return;
        }
        this.acT.putLong(str, Dimension.a(typedValuePeekValue));
    }

    private void a(TypedArray typedArray, int i, String str, String str2) {
        TypedValue typedValuePeekValue;
        if (this.acT.containsKey(str) || this.acT.containsKey(str2) || (typedValuePeekValue = typedArray.peekValue(i)) == null) {
            return;
        }
        if (typedValuePeekValue.type < 28 || typedValuePeekValue.type > 31) {
            this.acT.putInt(str2, typedValuePeekValue.resourceId);
        } else {
            this.acT.putInt(str, typedValuePeekValue.data);
        }
    }

    private void b(TypedArray typedArray, int i, String str) {
        TypedValue typedValuePeekValue;
        if (this.acT.containsKey(str) || (typedValuePeekValue = typedArray.peekValue(i)) == null) {
            return;
        }
        this.acT.putInt(str, typedValuePeekValue.data);
    }

    public void I(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(this.acU <= 0 ? R.style.WalletFragmentDefaultStyle : this.acU, R.styleable.WalletFragmentStyle);
        a(typedArrayObtainStyledAttributes, 1, "buyButtonWidth");
        a(typedArrayObtainStyledAttributes, 0, "buyButtonHeight");
        b(typedArrayObtainStyledAttributes, 2, "buyButtonText");
        b(typedArrayObtainStyledAttributes, 3, "buyButtonAppearance");
        b(typedArrayObtainStyledAttributes, 4, "maskedWalletDetailsTextAppearance");
        b(typedArrayObtainStyledAttributes, 5, "maskedWalletDetailsHeaderTextAppearance");
        a(typedArrayObtainStyledAttributes, 6, "maskedWalletDetailsBackgroundColor", "maskedWalletDetailsBackgroundResource");
        b(typedArrayObtainStyledAttributes, 7, "maskedWalletDetailsButtonTextAppearance");
        a(typedArrayObtainStyledAttributes, 8, "maskedWalletDetailsButtonBackgroundColor", "maskedWalletDetailsButtonBackgroundResource");
        b(typedArrayObtainStyledAttributes, 9, "maskedWalletDetailsLogoTextColor");
        b(typedArrayObtainStyledAttributes, 10, "maskedWalletDetailsLogoImageType");
        typedArrayObtainStyledAttributes.recycle();
    }

    public int a(String str, DisplayMetrics displayMetrics, int i) {
        return this.acT.containsKey(str) ? Dimension.a(this.acT.getLong(str), displayMetrics) : i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WalletFragmentStyle setBuyButtonAppearance(int buyButtonAppearance) {
        this.acT.putInt("buyButtonAppearance", buyButtonAppearance);
        return this;
    }

    public WalletFragmentStyle setBuyButtonHeight(int height) {
        this.acT.putLong("buyButtonHeight", Dimension.cz(height));
        return this;
    }

    public WalletFragmentStyle setBuyButtonHeight(int unit, float height) {
        this.acT.putLong("buyButtonHeight", Dimension.a(unit, height));
        return this;
    }

    public WalletFragmentStyle setBuyButtonText(int buyButtonText) {
        this.acT.putInt("buyButtonText", buyButtonText);
        return this;
    }

    public WalletFragmentStyle setBuyButtonWidth(int width) {
        this.acT.putLong("buyButtonWidth", Dimension.cz(width));
        return this;
    }

    public WalletFragmentStyle setBuyButtonWidth(int unit, float width) {
        this.acT.putLong("buyButtonWidth", Dimension.a(unit, width));
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsBackgroundColor(int color) {
        this.acT.remove("maskedWalletDetailsBackgroundResource");
        this.acT.putInt("maskedWalletDetailsBackgroundColor", color);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsBackgroundResource(int resourceId) {
        this.acT.remove("maskedWalletDetailsBackgroundColor");
        this.acT.putInt("maskedWalletDetailsBackgroundResource", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundColor(int color) {
        this.acT.remove("maskedWalletDetailsButtonBackgroundResource");
        this.acT.putInt("maskedWalletDetailsButtonBackgroundColor", color);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundResource(int resourceId) {
        this.acT.remove("maskedWalletDetailsButtonBackgroundColor");
        this.acT.putInt("maskedWalletDetailsButtonBackgroundResource", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonTextAppearance(int resourceId) {
        this.acT.putInt("maskedWalletDetailsButtonTextAppearance", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsHeaderTextAppearance(int resourceId) {
        this.acT.putInt("maskedWalletDetailsHeaderTextAppearance", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsLogoImageType(int imageType) {
        this.acT.putInt("maskedWalletDetailsLogoImageType", imageType);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsLogoTextColor(int color) {
        this.acT.putInt("maskedWalletDetailsLogoTextColor", color);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsTextAppearance(int resourceId) {
        this.acT.putInt("maskedWalletDetailsTextAppearance", resourceId);
        return this;
    }

    public WalletFragmentStyle setStyleResourceId(int id) {
        this.acU = id;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        c.a(this, dest, flags);
    }
}
