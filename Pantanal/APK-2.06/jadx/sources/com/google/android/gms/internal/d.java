package com.google.android.gms.internal;

import com.fyber.ads.banners.BannerAd;
import com.google.ads.AdSize;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface d {

    public static final class a extends kp<a> {
        private static volatile a[] fM;
        public String fN;
        public a[] fO;
        public a[] fP;
        public a[] fQ;
        public String fR;
        public String fS;
        public long fT;
        public boolean fU;
        public a[] fV;
        public int[] fW;
        public boolean fX;
        public int type;

        public a() {
            s();
        }

        public static a[] r() {
            if (fM == null) {
                synchronized (kr.adX) {
                    if (fM == null) {
                        fM = new a[0];
                    }
                }
            }
            return fM;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            koVar.i(1, this.type);
            if (!this.fN.equals("")) {
                koVar.b(2, this.fN);
            }
            if (this.fO != null && this.fO.length > 0) {
                for (int i = 0; i < this.fO.length; i++) {
                    a aVar = this.fO[i];
                    if (aVar != null) {
                        koVar.a(3, aVar);
                    }
                }
            }
            if (this.fP != null && this.fP.length > 0) {
                for (int i2 = 0; i2 < this.fP.length; i2++) {
                    a aVar2 = this.fP[i2];
                    if (aVar2 != null) {
                        koVar.a(4, aVar2);
                    }
                }
            }
            if (this.fQ != null && this.fQ.length > 0) {
                for (int i3 = 0; i3 < this.fQ.length; i3++) {
                    a aVar3 = this.fQ[i3];
                    if (aVar3 != null) {
                        koVar.a(5, aVar3);
                    }
                }
            }
            if (!this.fR.equals("")) {
                koVar.b(6, this.fR);
            }
            if (!this.fS.equals("")) {
                koVar.b(7, this.fS);
            }
            if (this.fT != 0) {
                koVar.b(8, this.fT);
            }
            if (this.fX) {
                koVar.a(9, this.fX);
            }
            if (this.fW != null && this.fW.length > 0) {
                for (int i4 = 0; i4 < this.fW.length; i4++) {
                    koVar.i(10, this.fW[i4]);
                }
            }
            if (this.fV != null && this.fV.length > 0) {
                for (int i5 = 0; i5 < this.fV.length; i5++) {
                    a aVar4 = this.fV[i5];
                    if (aVar4 != null) {
                        koVar.a(11, aVar4);
                    }
                }
            }
            if (this.fU) {
                koVar.a(12, this.fU);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c() + ko.j(1, this.type);
            if (!this.fN.equals("")) {
                iC += ko.g(2, this.fN);
            }
            if (this.fO != null && this.fO.length > 0) {
                int iB = iC;
                for (int i = 0; i < this.fO.length; i++) {
                    a aVar = this.fO[i];
                    if (aVar != null) {
                        iB += ko.b(3, aVar);
                    }
                }
                iC = iB;
            }
            if (this.fP != null && this.fP.length > 0) {
                int iB2 = iC;
                for (int i2 = 0; i2 < this.fP.length; i2++) {
                    a aVar2 = this.fP[i2];
                    if (aVar2 != null) {
                        iB2 += ko.b(4, aVar2);
                    }
                }
                iC = iB2;
            }
            if (this.fQ != null && this.fQ.length > 0) {
                int iB3 = iC;
                for (int i3 = 0; i3 < this.fQ.length; i3++) {
                    a aVar3 = this.fQ[i3];
                    if (aVar3 != null) {
                        iB3 += ko.b(5, aVar3);
                    }
                }
                iC = iB3;
            }
            if (!this.fR.equals("")) {
                iC += ko.g(6, this.fR);
            }
            if (!this.fS.equals("")) {
                iC += ko.g(7, this.fS);
            }
            if (this.fT != 0) {
                iC += ko.d(8, this.fT);
            }
            if (this.fX) {
                iC += ko.b(9, this.fX);
            }
            if (this.fW != null && this.fW.length > 0) {
                int iCX = 0;
                for (int i4 = 0; i4 < this.fW.length; i4++) {
                    iCX += ko.cX(this.fW[i4]);
                }
                iC = iC + iCX + (this.fW.length * 1);
            }
            if (this.fV != null && this.fV.length > 0) {
                for (int i5 = 0; i5 < this.fV.length; i5++) {
                    a aVar4 = this.fV[i5];
                    if (aVar4 != null) {
                        iC += ko.b(11, aVar4);
                    }
                }
            }
            if (this.fU) {
                iC += ko.b(12, this.fU);
            }
            this.adY = iC;
            return iC;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof a)) {
                return false;
            }
            a aVar = (a) o;
            if (this.type != aVar.type) {
                return false;
            }
            if (this.fN == null) {
                if (aVar.fN != null) {
                    return false;
                }
            } else if (!this.fN.equals(aVar.fN)) {
                return false;
            }
            if (!kr.equals(this.fO, aVar.fO) || !kr.equals(this.fP, aVar.fP) || !kr.equals(this.fQ, aVar.fQ)) {
                return false;
            }
            if (this.fR == null) {
                if (aVar.fR != null) {
                    return false;
                }
            } else if (!this.fR.equals(aVar.fR)) {
                return false;
            }
            if (this.fS == null) {
                if (aVar.fS != null) {
                    return false;
                }
            } else if (!this.fS.equals(aVar.fS)) {
                return false;
            }
            if (this.fT != aVar.fT || this.fU != aVar.fU || !kr.equals(this.fV, aVar.fV) || !kr.equals(this.fW, aVar.fW) || this.fX != aVar.fX) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return aVar.adU == null || aVar.adU.isEmpty();
            }
            return this.adU.equals(aVar.adU);
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((((((((this.fU ? 1231 : 1237) + (((((this.fS == null ? 0 : this.fS.hashCode()) + (((this.fR == null ? 0 : this.fR.hashCode()) + (((((((((this.fN == null ? 0 : this.fN.hashCode()) + ((this.type + 527) * 31)) * 31) + kr.hashCode(this.fO)) * 31) + kr.hashCode(this.fP)) * 31) + kr.hashCode(this.fQ)) * 31)) * 31)) * 31) + ((int) (this.fT ^ (this.fT >>> 32)))) * 31)) * 31) + kr.hashCode(this.fV)) * 31) + kr.hashCode(this.fW)) * 31) + (this.fX ? 1231 : 1237)) * 31;
            if (this.adU != null && !this.adU.isEmpty()) {
                iHashCode = this.adU.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
        public a b(kn knVar) throws IOException {
            int i;
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 8:
                        int iMk = knVar.mk();
                        switch (iMk) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                this.type = iMk;
                                break;
                        }
                        break;
                    case 18:
                        this.fN = knVar.readString();
                        break;
                    case 26:
                        int iB = kw.b(knVar, 26);
                        int length = this.fO == null ? 0 : this.fO.length;
                        a[] aVarArr = new a[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.fO, 0, aVarArr, 0, length);
                        }
                        while (length < aVarArr.length - 1) {
                            aVarArr[length] = new a();
                            knVar.a(aVarArr[length]);
                            knVar.mh();
                            length++;
                        }
                        aVarArr[length] = new a();
                        knVar.a(aVarArr[length]);
                        this.fO = aVarArr;
                        break;
                    case 34:
                        int iB2 = kw.b(knVar, 34);
                        int length2 = this.fP == null ? 0 : this.fP.length;
                        a[] aVarArr2 = new a[iB2 + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.fP, 0, aVarArr2, 0, length2);
                        }
                        while (length2 < aVarArr2.length - 1) {
                            aVarArr2[length2] = new a();
                            knVar.a(aVarArr2[length2]);
                            knVar.mh();
                            length2++;
                        }
                        aVarArr2[length2] = new a();
                        knVar.a(aVarArr2[length2]);
                        this.fP = aVarArr2;
                        break;
                    case 42:
                        int iB3 = kw.b(knVar, 42);
                        int length3 = this.fQ == null ? 0 : this.fQ.length;
                        a[] aVarArr3 = new a[iB3 + length3];
                        if (length3 != 0) {
                            System.arraycopy(this.fQ, 0, aVarArr3, 0, length3);
                        }
                        while (length3 < aVarArr3.length - 1) {
                            aVarArr3[length3] = new a();
                            knVar.a(aVarArr3[length3]);
                            knVar.mh();
                            length3++;
                        }
                        aVarArr3[length3] = new a();
                        knVar.a(aVarArr3[length3]);
                        this.fQ = aVarArr3;
                        break;
                    case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                        this.fR = knVar.readString();
                        break;
                    case 58:
                        this.fS = knVar.readString();
                        break;
                    case 64:
                        this.fT = knVar.mj();
                        break;
                    case 72:
                        this.fX = knVar.ml();
                        break;
                    case BannerAd.POSITION_BOTTOM /* 80 */:
                        int iB4 = kw.b(knVar, 80);
                        int[] iArr = new int[iB4];
                        int i2 = 0;
                        int i3 = 0;
                        while (i2 < iB4) {
                            if (i2 != 0) {
                                knVar.mh();
                            }
                            int iMk2 = knVar.mk();
                            switch (iMk2) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    i = i3 + 1;
                                    iArr[i3] = iMk2;
                                    break;
                                default:
                                    i = i3;
                                    break;
                            }
                            i2++;
                            i3 = i;
                        }
                        if (i3 != 0) {
                            int length4 = this.fW == null ? 0 : this.fW.length;
                            if (length4 == 0 && i3 == iArr.length) {
                                this.fW = iArr;
                            } else {
                                int[] iArr2 = new int[length4 + i3];
                                if (length4 != 0) {
                                    System.arraycopy(this.fW, 0, iArr2, 0, length4);
                                }
                                System.arraycopy(iArr, 0, iArr2, length4, i3);
                                this.fW = iArr2;
                            }
                        }
                        break;
                    case 82:
                        int iCR = knVar.cR(knVar.mn());
                        int position = knVar.getPosition();
                        int i4 = 0;
                        while (knVar.ms() > 0) {
                            switch (knVar.mk()) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    i4++;
                                    break;
                            }
                        }
                        if (i4 != 0) {
                            knVar.cT(position);
                            int length5 = this.fW == null ? 0 : this.fW.length;
                            int[] iArr3 = new int[i4 + length5];
                            if (length5 != 0) {
                                System.arraycopy(this.fW, 0, iArr3, 0, length5);
                            }
                            while (knVar.ms() > 0) {
                                int iMk3 = knVar.mk();
                                switch (iMk3) {
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                    case 7:
                                    case 8:
                                    case 9:
                                    case 10:
                                    case 11:
                                    case 12:
                                    case 13:
                                    case 14:
                                    case 15:
                                    case 16:
                                    case 17:
                                        iArr3[length5] = iMk3;
                                        length5++;
                                        break;
                                }
                            }
                            this.fW = iArr3;
                        }
                        knVar.cS(iCR);
                        break;
                    case AdSize.LARGE_AD_HEIGHT /* 90 */:
                        int iB5 = kw.b(knVar, 90);
                        int length6 = this.fV == null ? 0 : this.fV.length;
                        a[] aVarArr4 = new a[iB5 + length6];
                        if (length6 != 0) {
                            System.arraycopy(this.fV, 0, aVarArr4, 0, length6);
                        }
                        while (length6 < aVarArr4.length - 1) {
                            aVarArr4[length6] = new a();
                            knVar.a(aVarArr4[length6]);
                            knVar.mh();
                            length6++;
                        }
                        aVarArr4[length6] = new a();
                        knVar.a(aVarArr4[length6]);
                        this.fV = aVarArr4;
                        break;
                    case 96:
                        this.fU = knVar.ml();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public a s() {
            this.type = 1;
            this.fN = "";
            this.fO = r();
            this.fP = r();
            this.fQ = r();
            this.fR = "";
            this.fS = "";
            this.fT = 0L;
            this.fU = false;
            this.fV = r();
            this.fW = kw.aea;
            this.fX = false;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }
}
