package com.google.android.gms.drive.internal;

import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import java.io.IOException;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public final class y extends kp<y> {
    public String FC;
    public long FD;
    public long FE;
    public int versionCode;

    public y() {
        fH();
    }

    public static y g(byte[] bArr) throws ks {
        return (y) kt.a(new y(), bArr);
    }

    @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
    public void a(ko koVar) throws IOException {
        koVar.i(1, this.versionCode);
        koVar.b(2, this.FC);
        koVar.c(3, this.FD);
        koVar.c(4, this.FE);
        super.a(koVar);
    }

    @Override // com.google.android.gms.internal.kt
    public int c() {
        int iC = super.c() + ko.j(1, this.versionCode) + ko.g(2, this.FC) + ko.e(3, this.FD) + ko.e(4, this.FE);
        this.adY = iC;
        return iC;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof y)) {
            return false;
        }
        y yVar = (y) o;
        if (this.versionCode != yVar.versionCode) {
            return false;
        }
        if (this.FC == null) {
            if (yVar.FC != null) {
                return false;
            }
        } else if (!this.FC.equals(yVar.FC)) {
            return false;
        }
        if (this.FD != yVar.FD || this.FE != yVar.FE) {
            return false;
        }
        if (this.adU == null || this.adU.isEmpty()) {
            return yVar.adU == null || yVar.adU.isEmpty();
        }
        return this.adU.equals(yVar.adU);
    }

    public y fH() {
        this.versionCode = 1;
        this.FC = "";
        this.FD = -1L;
        this.FE = -1L;
        this.adU = null;
        this.adY = -1;
        return this;
    }

    public int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((((((this.FC == null ? 0 : this.FC.hashCode()) + ((this.versionCode + 527) * 31)) * 31) + ((int) (this.FD ^ (this.FD >>> 32)))) * 31) + ((int) (this.FE ^ (this.FE >>> 32)))) * 31;
        if (this.adU != null && !this.adU.isEmpty()) {
            iHashCode = this.adU.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.kt
    /* JADX INFO: renamed from: m, reason: merged with bridge method [inline-methods] */
    public y b(kn knVar) throws IOException {
        while (true) {
            int iMh = knVar.mh();
            switch (iMh) {
                case 0:
                    break;
                case 8:
                    this.versionCode = knVar.mk();
                    break;
                case 18:
                    this.FC = knVar.readString();
                    break;
                case TimeConstants.HOURSPERDAY /* 24 */:
                    this.FD = knVar.mm();
                    break;
                case 32:
                    this.FE = knVar.mm();
                    break;
                default:
                    if (!a(knVar, iMh)) {
                    }
                    break;
            }
        }
        return this;
    }
}
