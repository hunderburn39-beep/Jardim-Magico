package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jm;
import com.google.android.gms.internal.jo;
import com.google.android.gms.internal.js;
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jy;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class LoyaltyWalletObject implements SafeParcelable {
    public static final Parcelable.Creator<LoyaltyWalletObject> CREATOR = new j();
    String abA;
    String abB;
    String abC;
    String abD;
    String abE;
    String abF;
    String abG;
    String abH;
    ArrayList<jy> abI;
    ju abJ;
    ArrayList<LatLng> abK;
    String abL;
    String abM;
    ArrayList<jm> abN;
    boolean abO;
    ArrayList<jw> abP;
    ArrayList<js> abQ;
    ArrayList<jw> abR;
    jo abS;
    String abz;
    String eC;
    int state;
    private final int xH;

    LoyaltyWalletObject() {
        this.xH = 4;
        this.abI = gi.fs();
        this.abK = gi.fs();
        this.abN = gi.fs();
        this.abP = gi.fs();
        this.abQ = gi.fs();
        this.abR = gi.fs();
    }

    LoyaltyWalletObject(int versionCode, String id, String accountId, String issuerName, String programName, String accountName, String barcodeAlternateText, String barcodeType, String barcodeValue, String barcodeLabel, String classId, int state, ArrayList<jy> messages, ju validTimeInterval, ArrayList<LatLng> locations, String infoModuleDataHexFontColor, String infoModuleDataHexBackgroundColor, ArrayList<jm> infoModuleDataLabelValueRows, boolean infoModuleDataShowLastUpdateTime, ArrayList<jw> imageModuleDataMainImageUris, ArrayList<js> textModulesData, ArrayList<jw> linksModuleDataUris, jo loyaltyPoints) {
        this.xH = versionCode;
        this.eC = id;
        this.abz = accountId;
        this.abA = issuerName;
        this.abB = programName;
        this.abC = accountName;
        this.abD = barcodeAlternateText;
        this.abE = barcodeType;
        this.abF = barcodeValue;
        this.abG = barcodeLabel;
        this.abH = classId;
        this.state = state;
        this.abI = messages;
        this.abJ = validTimeInterval;
        this.abK = locations;
        this.abL = infoModuleDataHexFontColor;
        this.abM = infoModuleDataHexBackgroundColor;
        this.abN = infoModuleDataLabelValueRows;
        this.abO = infoModuleDataShowLastUpdateTime;
        this.abP = imageModuleDataMainImageUris;
        this.abQ = textModulesData;
        this.abR = linksModuleDataUris;
        this.abS = loyaltyPoints;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAccountId() {
        return this.abz;
    }

    public String getAccountName() {
        return this.abC;
    }

    public String getBarcodeAlternateText() {
        return this.abD;
    }

    public String getBarcodeType() {
        return this.abE;
    }

    public String getBarcodeValue() {
        return this.abF;
    }

    public String getId() {
        return this.eC;
    }

    public String getIssuerName() {
        return this.abA;
    }

    public String getProgramName() {
        return this.abB;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        j.a(this, dest, flags);
    }
}
