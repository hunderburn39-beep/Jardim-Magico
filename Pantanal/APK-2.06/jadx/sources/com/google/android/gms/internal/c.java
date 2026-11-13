package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.fyber.ads.banners.BannerAd;
import com.google.ads.AdSize;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.IOException;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public interface c {

    public static final class a extends kp<a> {
        public int eE;
        public int eF;
        public int level;

        public a() {
            b();
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public a b(kn knVar) throws IOException {
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
                                this.level = iMk;
                                break;
                        }
                        break;
                    case 16:
                        this.eE = knVar.mk();
                        break;
                    case TimeConstants.HOURSPERDAY /* 24 */:
                        this.eF = knVar.mk();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.level != 1) {
                koVar.i(1, this.level);
            }
            if (this.eE != 0) {
                koVar.i(2, this.eE);
            }
            if (this.eF != 0) {
                koVar.i(3, this.eF);
            }
            super.a(koVar);
        }

        public a b() {
            this.level = 1;
            this.eE = 0;
            this.eF = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c();
            if (this.level != 1) {
                iC += ko.j(1, this.level);
            }
            if (this.eE != 0) {
                iC += ko.j(2, this.eE);
            }
            if (this.eF != 0) {
                iC += ko.j(3, this.eF);
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
            if (this.level != aVar.level || this.eE != aVar.eE || this.eF != aVar.eF) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return aVar.adU == null || aVar.adU.isEmpty();
            }
            return this.adU.equals(aVar.adU);
        }

        public int hashCode() {
            return ((this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode()) + ((((((this.level + 527) * 31) + this.eE) * 31) + this.eF) * 31);
        }
    }

    public static final class b extends kp<b> {
        private static volatile b[] eG;
        public int[] eH;
        public int eI;
        public boolean eJ;
        public boolean eK;
        public int name;

        public b() {
            e();
        }

        public static b[] d() {
            if (eG == null) {
                synchronized (kr.adX) {
                    if (eG == null) {
                        eG = new b[0];
                    }
                }
            }
            return eG;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.eK) {
                koVar.a(1, this.eK);
            }
            koVar.i(2, this.eI);
            if (this.eH != null && this.eH.length > 0) {
                for (int i = 0; i < this.eH.length; i++) {
                    koVar.i(3, this.eH[i]);
                }
            }
            if (this.name != 0) {
                koVar.i(4, this.name);
            }
            if (this.eJ) {
                koVar.a(6, this.eJ);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iB;
            int iCX = 0;
            int iC = super.c();
            if (this.eK) {
                iC += ko.b(1, this.eK);
            }
            int iJ = ko.j(2, this.eI) + iC;
            if (this.eH == null || this.eH.length <= 0) {
                iB = iJ;
            } else {
                for (int i = 0; i < this.eH.length; i++) {
                    iCX += ko.cX(this.eH[i]);
                }
                iB = iJ + iCX + (this.eH.length * 1);
            }
            if (this.name != 0) {
                iB += ko.j(4, this.name);
            }
            if (this.eJ) {
                iB += ko.b(6, this.eJ);
            }
            this.adY = iB;
            return iB;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public b b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 8:
                        this.eK = knVar.ml();
                        break;
                    case 16:
                        this.eI = knVar.mk();
                        break;
                    case TimeConstants.HOURSPERDAY /* 24 */:
                        int iB = kw.b(knVar, 24);
                        int length = this.eH == null ? 0 : this.eH.length;
                        int[] iArr = new int[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.eH, 0, iArr, 0, length);
                        }
                        while (length < iArr.length - 1) {
                            iArr[length] = knVar.mk();
                            knVar.mh();
                            length++;
                        }
                        iArr[length] = knVar.mk();
                        this.eH = iArr;
                        break;
                    case 26:
                        int iCR = knVar.cR(knVar.mn());
                        int position = knVar.getPosition();
                        int i = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i++;
                        }
                        knVar.cT(position);
                        int length2 = this.eH == null ? 0 : this.eH.length;
                        int[] iArr2 = new int[i + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.eH, 0, iArr2, 0, length2);
                        }
                        while (length2 < iArr2.length) {
                            iArr2[length2] = knVar.mk();
                            length2++;
                        }
                        this.eH = iArr2;
                        knVar.cS(iCR);
                        break;
                    case 32:
                        this.name = knVar.mk();
                        break;
                    case BannerAd.POSITION_TOP /* 48 */:
                        this.eJ = knVar.ml();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public b e() {
            this.eH = kw.aea;
            this.eI = 0;
            this.name = 0;
            this.eJ = false;
            this.eK = false;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof b)) {
                return false;
            }
            b bVar = (b) o;
            if (!kr.equals(this.eH, bVar.eH) || this.eI != bVar.eI || this.name != bVar.name || this.eJ != bVar.eJ || this.eK != bVar.eK) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return bVar.adU == null || bVar.adU.isEmpty();
            }
            return this.adU.equals(bVar.adU);
        }

        public int hashCode() {
            return ((this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode()) + (((((this.eJ ? 1231 : 1237) + ((((((kr.hashCode(this.eH) + 527) * 31) + this.eI) * 31) + this.name) * 31)) * 31) + (this.eK ? 1231 : 1237)) * 31);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.internal.c$c, reason: collision with other inner class name */
    public static final class C0051c extends kp<C0051c> {
        private static volatile C0051c[] eL;
        public String eM;
        public long eN;
        public long eO;
        public boolean eP;
        public long eQ;

        public C0051c() {
            g();
        }

        public static C0051c[] f() {
            if (eL == null) {
                synchronized (kr.adX) {
                    if (eL == null) {
                        eL = new C0051c[0];
                    }
                }
            }
            return eL;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (!this.eM.equals("")) {
                koVar.b(1, this.eM);
            }
            if (this.eN != 0) {
                koVar.b(2, this.eN);
            }
            if (this.eO != 2147483647L) {
                koVar.b(3, this.eO);
            }
            if (this.eP) {
                koVar.a(4, this.eP);
            }
            if (this.eQ != 0) {
                koVar.b(5, this.eQ);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c();
            if (!this.eM.equals("")) {
                iC += ko.g(1, this.eM);
            }
            if (this.eN != 0) {
                iC += ko.d(2, this.eN);
            }
            if (this.eO != 2147483647L) {
                iC += ko.d(3, this.eO);
            }
            if (this.eP) {
                iC += ko.b(4, this.eP);
            }
            if (this.eQ != 0) {
                iC += ko.d(5, this.eQ);
            }
            this.adY = iC;
            return iC;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
        public C0051c b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 10:
                        this.eM = knVar.readString();
                        break;
                    case 16:
                        this.eN = knVar.mj();
                        break;
                    case TimeConstants.HOURSPERDAY /* 24 */:
                        this.eO = knVar.mj();
                        break;
                    case 32:
                        this.eP = knVar.ml();
                        break;
                    case 40:
                        this.eQ = knVar.mj();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C0051c)) {
                return false;
            }
            C0051c c0051c = (C0051c) o;
            if (this.eM == null) {
                if (c0051c.eM != null) {
                    return false;
                }
            } else if (!this.eM.equals(c0051c.eM)) {
                return false;
            }
            if (this.eN != c0051c.eN || this.eO != c0051c.eO || this.eP != c0051c.eP || this.eQ != c0051c.eQ) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c0051c.adU == null || c0051c.adU.isEmpty();
            }
            return this.adU.equals(c0051c.adU);
        }

        public C0051c g() {
            this.eM = "";
            this.eN = 0L;
            this.eO = 2147483647L;
            this.eP = false;
            this.eQ = 0L;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((((this.eP ? 1231 : 1237) + (((((((this.eM == null ? 0 : this.eM.hashCode()) + 527) * 31) + ((int) (this.eN ^ (this.eN >>> 32)))) * 31) + ((int) (this.eO ^ (this.eO >>> 32)))) * 31)) * 31) + ((int) (this.eQ ^ (this.eQ >>> 32)))) * 31;
            if (this.adU != null && !this.adU.isEmpty()) {
                iHashCode = this.adU.hashCode();
            }
            return iHashCode2 + iHashCode;
        }
    }

    public static final class d extends kp<d> {
        public com.google.android.gms.internal.d.a[] eR;
        public com.google.android.gms.internal.d.a[] eS;
        public C0051c[] eT;

        public d() {
            h();
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.eR != null && this.eR.length > 0) {
                for (int i = 0; i < this.eR.length; i++) {
                    com.google.android.gms.internal.d.a aVar = this.eR[i];
                    if (aVar != null) {
                        koVar.a(1, aVar);
                    }
                }
            }
            if (this.eS != null && this.eS.length > 0) {
                for (int i2 = 0; i2 < this.eS.length; i2++) {
                    com.google.android.gms.internal.d.a aVar2 = this.eS[i2];
                    if (aVar2 != null) {
                        koVar.a(2, aVar2);
                    }
                }
            }
            if (this.eT != null && this.eT.length > 0) {
                for (int i3 = 0; i3 < this.eT.length; i3++) {
                    C0051c c0051c = this.eT[i3];
                    if (c0051c != null) {
                        koVar.a(3, c0051c);
                    }
                }
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c();
            if (this.eR != null && this.eR.length > 0) {
                int iB = iC;
                for (int i = 0; i < this.eR.length; i++) {
                    com.google.android.gms.internal.d.a aVar = this.eR[i];
                    if (aVar != null) {
                        iB += ko.b(1, aVar);
                    }
                }
                iC = iB;
            }
            if (this.eS != null && this.eS.length > 0) {
                int iB2 = iC;
                for (int i2 = 0; i2 < this.eS.length; i2++) {
                    com.google.android.gms.internal.d.a aVar2 = this.eS[i2];
                    if (aVar2 != null) {
                        iB2 += ko.b(2, aVar2);
                    }
                }
                iC = iB2;
            }
            if (this.eT != null && this.eT.length > 0) {
                for (int i3 = 0; i3 < this.eT.length; i3++) {
                    C0051c c0051c = this.eT[i3];
                    if (c0051c != null) {
                        iC += ko.b(3, c0051c);
                    }
                }
            }
            this.adY = iC;
            return iC;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
        public d b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 10:
                        int iB = kw.b(knVar, 10);
                        int length = this.eR == null ? 0 : this.eR.length;
                        com.google.android.gms.internal.d.a[] aVarArr = new com.google.android.gms.internal.d.a[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.eR, 0, aVarArr, 0, length);
                        }
                        while (length < aVarArr.length - 1) {
                            aVarArr[length] = new com.google.android.gms.internal.d.a();
                            knVar.a(aVarArr[length]);
                            knVar.mh();
                            length++;
                        }
                        aVarArr[length] = new com.google.android.gms.internal.d.a();
                        knVar.a(aVarArr[length]);
                        this.eR = aVarArr;
                        break;
                    case 18:
                        int iB2 = kw.b(knVar, 18);
                        int length2 = this.eS == null ? 0 : this.eS.length;
                        com.google.android.gms.internal.d.a[] aVarArr2 = new com.google.android.gms.internal.d.a[iB2 + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.eS, 0, aVarArr2, 0, length2);
                        }
                        while (length2 < aVarArr2.length - 1) {
                            aVarArr2[length2] = new com.google.android.gms.internal.d.a();
                            knVar.a(aVarArr2[length2]);
                            knVar.mh();
                            length2++;
                        }
                        aVarArr2[length2] = new com.google.android.gms.internal.d.a();
                        knVar.a(aVarArr2[length2]);
                        this.eS = aVarArr2;
                        break;
                    case 26:
                        int iB3 = kw.b(knVar, 26);
                        int length3 = this.eT == null ? 0 : this.eT.length;
                        C0051c[] c0051cArr = new C0051c[iB3 + length3];
                        if (length3 != 0) {
                            System.arraycopy(this.eT, 0, c0051cArr, 0, length3);
                        }
                        while (length3 < c0051cArr.length - 1) {
                            c0051cArr[length3] = new C0051c();
                            knVar.a(c0051cArr[length3]);
                            knVar.mh();
                            length3++;
                        }
                        c0051cArr[length3] = new C0051c();
                        knVar.a(c0051cArr[length3]);
                        this.eT = c0051cArr;
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof d)) {
                return false;
            }
            d dVar = (d) o;
            if (!kr.equals(this.eR, dVar.eR) || !kr.equals(this.eS, dVar.eS) || !kr.equals(this.eT, dVar.eT)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return dVar.adU == null || dVar.adU.isEmpty();
            }
            return this.adU.equals(dVar.adU);
        }

        public d h() {
            this.eR = com.google.android.gms.internal.d.a.r();
            this.eS = com.google.android.gms.internal.d.a.r();
            this.eT = C0051c.f();
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public int hashCode() {
            return ((this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode()) + ((((((kr.hashCode(this.eR) + 527) * 31) + kr.hashCode(this.eS)) * 31) + kr.hashCode(this.eT)) * 31);
        }
    }

    public static final class e extends kp<e> {
        private static volatile e[] eU;
        public int key;
        public int value;

        public e() {
            j();
        }

        public static e[] i() {
            if (eU == null) {
                synchronized (kr.adX) {
                    if (eU == null) {
                        eU = new e[0];
                    }
                }
            }
            return eU;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            koVar.i(1, this.key);
            koVar.i(2, this.value);
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c() + ko.j(1, this.key) + ko.j(2, this.value);
            this.adY = iC;
            return iC;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof e)) {
                return false;
            }
            e eVar = (e) o;
            if (this.key != eVar.key || this.value != eVar.value) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return eVar.adU == null || eVar.adU.isEmpty();
            }
            return this.adU.equals(eVar.adU);
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public e b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 8:
                        this.key = knVar.mk();
                        break;
                    case 16:
                        this.value = knVar.mk();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public int hashCode() {
            return ((this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode()) + ((((this.key + 527) * 31) + this.value) * 31);
        }

        public e j() {
            this.key = 0;
            this.value = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class f extends kp<f> {
        public String[] eV;
        public String[] eW;
        public com.google.android.gms.internal.d.a[] eX;
        public e[] eY;
        public b[] eZ;
        public b[] fa;
        public b[] fb;
        public g[] fc;
        public String fd;
        public String fe;
        public String ff;
        public String fg;
        public a fh;
        public float fi;
        public boolean fj;
        public String[] fk;
        public int fl;

        public f() {
            k();
        }

        public static f a(byte[] bArr) throws ks {
            return (f) kt.a(new f(), bArr);
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.eW != null && this.eW.length > 0) {
                for (int i = 0; i < this.eW.length; i++) {
                    String str = this.eW[i];
                    if (str != null) {
                        koVar.b(1, str);
                    }
                }
            }
            if (this.eX != null && this.eX.length > 0) {
                for (int i2 = 0; i2 < this.eX.length; i2++) {
                    com.google.android.gms.internal.d.a aVar = this.eX[i2];
                    if (aVar != null) {
                        koVar.a(2, aVar);
                    }
                }
            }
            if (this.eY != null && this.eY.length > 0) {
                for (int i3 = 0; i3 < this.eY.length; i3++) {
                    e eVar = this.eY[i3];
                    if (eVar != null) {
                        koVar.a(3, eVar);
                    }
                }
            }
            if (this.eZ != null && this.eZ.length > 0) {
                for (int i4 = 0; i4 < this.eZ.length; i4++) {
                    b bVar = this.eZ[i4];
                    if (bVar != null) {
                        koVar.a(4, bVar);
                    }
                }
            }
            if (this.fa != null && this.fa.length > 0) {
                for (int i5 = 0; i5 < this.fa.length; i5++) {
                    b bVar2 = this.fa[i5];
                    if (bVar2 != null) {
                        koVar.a(5, bVar2);
                    }
                }
            }
            if (this.fb != null && this.fb.length > 0) {
                for (int i6 = 0; i6 < this.fb.length; i6++) {
                    b bVar3 = this.fb[i6];
                    if (bVar3 != null) {
                        koVar.a(6, bVar3);
                    }
                }
            }
            if (this.fc != null && this.fc.length > 0) {
                for (int i7 = 0; i7 < this.fc.length; i7++) {
                    g gVar = this.fc[i7];
                    if (gVar != null) {
                        koVar.a(7, gVar);
                    }
                }
            }
            if (!this.fd.equals("")) {
                koVar.b(9, this.fd);
            }
            if (!this.fe.equals("")) {
                koVar.b(10, this.fe);
            }
            if (!this.ff.equals("0")) {
                koVar.b(12, this.ff);
            }
            if (!this.fg.equals("")) {
                koVar.b(13, this.fg);
            }
            if (this.fh != null) {
                koVar.a(14, this.fh);
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(BitmapDescriptorFactory.HUE_RED)) {
                koVar.b(15, this.fi);
            }
            if (this.fk != null && this.fk.length > 0) {
                for (int i8 = 0; i8 < this.fk.length; i8++) {
                    String str2 = this.fk[i8];
                    if (str2 != null) {
                        koVar.b(16, str2);
                    }
                }
            }
            if (this.fl != 0) {
                koVar.i(17, this.fl);
            }
            if (this.fj) {
                koVar.a(18, this.fj);
            }
            if (this.eV != null && this.eV.length > 0) {
                for (int i9 = 0; i9 < this.eV.length; i9++) {
                    String str3 = this.eV[i9];
                    if (str3 != null) {
                        koVar.b(19, str3);
                    }
                }
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iB;
            int iC = super.c();
            if (this.eW == null || this.eW.length <= 0) {
                iB = iC;
            } else {
                int iCf = 0;
                int i = 0;
                for (int i2 = 0; i2 < this.eW.length; i2++) {
                    String str = this.eW[i2];
                    if (str != null) {
                        i++;
                        iCf += ko.cf(str);
                    }
                }
                iB = iC + iCf + (i * 1);
            }
            if (this.eX != null && this.eX.length > 0) {
                int iB2 = iB;
                for (int i3 = 0; i3 < this.eX.length; i3++) {
                    com.google.android.gms.internal.d.a aVar = this.eX[i3];
                    if (aVar != null) {
                        iB2 += ko.b(2, aVar);
                    }
                }
                iB = iB2;
            }
            if (this.eY != null && this.eY.length > 0) {
                int iB3 = iB;
                for (int i4 = 0; i4 < this.eY.length; i4++) {
                    e eVar = this.eY[i4];
                    if (eVar != null) {
                        iB3 += ko.b(3, eVar);
                    }
                }
                iB = iB3;
            }
            if (this.eZ != null && this.eZ.length > 0) {
                int iB4 = iB;
                for (int i5 = 0; i5 < this.eZ.length; i5++) {
                    b bVar = this.eZ[i5];
                    if (bVar != null) {
                        iB4 += ko.b(4, bVar);
                    }
                }
                iB = iB4;
            }
            if (this.fa != null && this.fa.length > 0) {
                int iB5 = iB;
                for (int i6 = 0; i6 < this.fa.length; i6++) {
                    b bVar2 = this.fa[i6];
                    if (bVar2 != null) {
                        iB5 += ko.b(5, bVar2);
                    }
                }
                iB = iB5;
            }
            if (this.fb != null && this.fb.length > 0) {
                int iB6 = iB;
                for (int i7 = 0; i7 < this.fb.length; i7++) {
                    b bVar3 = this.fb[i7];
                    if (bVar3 != null) {
                        iB6 += ko.b(6, bVar3);
                    }
                }
                iB = iB6;
            }
            if (this.fc != null && this.fc.length > 0) {
                int iB7 = iB;
                for (int i8 = 0; i8 < this.fc.length; i8++) {
                    g gVar = this.fc[i8];
                    if (gVar != null) {
                        iB7 += ko.b(7, gVar);
                    }
                }
                iB = iB7;
            }
            if (!this.fd.equals("")) {
                iB += ko.g(9, this.fd);
            }
            if (!this.fe.equals("")) {
                iB += ko.g(10, this.fe);
            }
            if (!this.ff.equals("0")) {
                iB += ko.g(12, this.ff);
            }
            if (!this.fg.equals("")) {
                iB += ko.g(13, this.fg);
            }
            if (this.fh != null) {
                iB += ko.b(14, this.fh);
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(BitmapDescriptorFactory.HUE_RED)) {
                iB += ko.c(15, this.fi);
            }
            if (this.fk != null && this.fk.length > 0) {
                int iCf2 = 0;
                int i9 = 0;
                for (int i10 = 0; i10 < this.fk.length; i10++) {
                    String str2 = this.fk[i10];
                    if (str2 != null) {
                        i9++;
                        iCf2 += ko.cf(str2);
                    }
                }
                iB = iB + iCf2 + (i9 * 2);
            }
            if (this.fl != 0) {
                iB += ko.j(17, this.fl);
            }
            if (this.fj) {
                iB += ko.b(18, this.fj);
            }
            if (this.eV != null && this.eV.length > 0) {
                int iCf3 = 0;
                int i11 = 0;
                for (int i12 = 0; i12 < this.eV.length; i12++) {
                    String str3 = this.eV[i12];
                    if (str3 != null) {
                        i11++;
                        iCf3 += ko.cf(str3);
                    }
                }
                iB = iB + iCf3 + (i11 * 2);
            }
            this.adY = iB;
            return iB;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof f)) {
                return false;
            }
            f fVar = (f) o;
            if (!kr.equals(this.eV, fVar.eV) || !kr.equals(this.eW, fVar.eW) || !kr.equals(this.eX, fVar.eX) || !kr.equals(this.eY, fVar.eY) || !kr.equals(this.eZ, fVar.eZ) || !kr.equals(this.fa, fVar.fa) || !kr.equals(this.fb, fVar.fb) || !kr.equals(this.fc, fVar.fc)) {
                return false;
            }
            if (this.fd == null) {
                if (fVar.fd != null) {
                    return false;
                }
            } else if (!this.fd.equals(fVar.fd)) {
                return false;
            }
            if (this.fe == null) {
                if (fVar.fe != null) {
                    return false;
                }
            } else if (!this.fe.equals(fVar.fe)) {
                return false;
            }
            if (this.ff == null) {
                if (fVar.ff != null) {
                    return false;
                }
            } else if (!this.ff.equals(fVar.ff)) {
                return false;
            }
            if (this.fg == null) {
                if (fVar.fg != null) {
                    return false;
                }
            } else if (!this.fg.equals(fVar.fg)) {
                return false;
            }
            if (this.fh == null) {
                if (fVar.fh != null) {
                    return false;
                }
            } else if (!this.fh.equals(fVar.fh)) {
                return false;
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(fVar.fi) || this.fj != fVar.fj || !kr.equals(this.fk, fVar.fk) || this.fl != fVar.fl) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return fVar.adU == null || fVar.adU.isEmpty();
            }
            return this.adU.equals(fVar.adU);
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
        public f b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 10:
                        int iB = kw.b(knVar, 10);
                        int length = this.eW == null ? 0 : this.eW.length;
                        String[] strArr = new String[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.eW, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = knVar.readString();
                            knVar.mh();
                            length++;
                        }
                        strArr[length] = knVar.readString();
                        this.eW = strArr;
                        break;
                    case 18:
                        int iB2 = kw.b(knVar, 18);
                        int length2 = this.eX == null ? 0 : this.eX.length;
                        com.google.android.gms.internal.d.a[] aVarArr = new com.google.android.gms.internal.d.a[iB2 + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.eX, 0, aVarArr, 0, length2);
                        }
                        while (length2 < aVarArr.length - 1) {
                            aVarArr[length2] = new com.google.android.gms.internal.d.a();
                            knVar.a(aVarArr[length2]);
                            knVar.mh();
                            length2++;
                        }
                        aVarArr[length2] = new com.google.android.gms.internal.d.a();
                        knVar.a(aVarArr[length2]);
                        this.eX = aVarArr;
                        break;
                    case 26:
                        int iB3 = kw.b(knVar, 26);
                        int length3 = this.eY == null ? 0 : this.eY.length;
                        e[] eVarArr = new e[iB3 + length3];
                        if (length3 != 0) {
                            System.arraycopy(this.eY, 0, eVarArr, 0, length3);
                        }
                        while (length3 < eVarArr.length - 1) {
                            eVarArr[length3] = new e();
                            knVar.a(eVarArr[length3]);
                            knVar.mh();
                            length3++;
                        }
                        eVarArr[length3] = new e();
                        knVar.a(eVarArr[length3]);
                        this.eY = eVarArr;
                        break;
                    case 34:
                        int iB4 = kw.b(knVar, 34);
                        int length4 = this.eZ == null ? 0 : this.eZ.length;
                        b[] bVarArr = new b[iB4 + length4];
                        if (length4 != 0) {
                            System.arraycopy(this.eZ, 0, bVarArr, 0, length4);
                        }
                        while (length4 < bVarArr.length - 1) {
                            bVarArr[length4] = new b();
                            knVar.a(bVarArr[length4]);
                            knVar.mh();
                            length4++;
                        }
                        bVarArr[length4] = new b();
                        knVar.a(bVarArr[length4]);
                        this.eZ = bVarArr;
                        break;
                    case 42:
                        int iB5 = kw.b(knVar, 42);
                        int length5 = this.fa == null ? 0 : this.fa.length;
                        b[] bVarArr2 = new b[iB5 + length5];
                        if (length5 != 0) {
                            System.arraycopy(this.fa, 0, bVarArr2, 0, length5);
                        }
                        while (length5 < bVarArr2.length - 1) {
                            bVarArr2[length5] = new b();
                            knVar.a(bVarArr2[length5]);
                            knVar.mh();
                            length5++;
                        }
                        bVarArr2[length5] = new b();
                        knVar.a(bVarArr2[length5]);
                        this.fa = bVarArr2;
                        break;
                    case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                        int iB6 = kw.b(knVar, 50);
                        int length6 = this.fb == null ? 0 : this.fb.length;
                        b[] bVarArr3 = new b[iB6 + length6];
                        if (length6 != 0) {
                            System.arraycopy(this.fb, 0, bVarArr3, 0, length6);
                        }
                        while (length6 < bVarArr3.length - 1) {
                            bVarArr3[length6] = new b();
                            knVar.a(bVarArr3[length6]);
                            knVar.mh();
                            length6++;
                        }
                        bVarArr3[length6] = new b();
                        knVar.a(bVarArr3[length6]);
                        this.fb = bVarArr3;
                        break;
                    case 58:
                        int iB7 = kw.b(knVar, 58);
                        int length7 = this.fc == null ? 0 : this.fc.length;
                        g[] gVarArr = new g[iB7 + length7];
                        if (length7 != 0) {
                            System.arraycopy(this.fc, 0, gVarArr, 0, length7);
                        }
                        while (length7 < gVarArr.length - 1) {
                            gVarArr[length7] = new g();
                            knVar.a(gVarArr[length7]);
                            knVar.mh();
                            length7++;
                        }
                        gVarArr[length7] = new g();
                        knVar.a(gVarArr[length7]);
                        this.fc = gVarArr;
                        break;
                    case 74:
                        this.fd = knVar.readString();
                        break;
                    case 82:
                        this.fe = knVar.readString();
                        break;
                    case 98:
                        this.ff = knVar.readString();
                        break;
                    case 106:
                        this.fg = knVar.readString();
                        break;
                    case 114:
                        if (this.fh == null) {
                            this.fh = new a();
                        }
                        knVar.a(this.fh);
                        break;
                    case 125:
                        this.fi = knVar.readFloat();
                        break;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /* 130 */:
                        int iB8 = kw.b(knVar, TransportMediator.KEYCODE_MEDIA_RECORD);
                        int length8 = this.fk == null ? 0 : this.fk.length;
                        String[] strArr2 = new String[iB8 + length8];
                        if (length8 != 0) {
                            System.arraycopy(this.fk, 0, strArr2, 0, length8);
                        }
                        while (length8 < strArr2.length - 1) {
                            strArr2[length8] = knVar.readString();
                            knVar.mh();
                            length8++;
                        }
                        strArr2[length8] = knVar.readString();
                        this.fk = strArr2;
                        break;
                    case 136:
                        this.fl = knVar.mk();
                        break;
                    case 144:
                        this.fj = knVar.ml();
                        break;
                    case 154:
                        int iB9 = kw.b(knVar, 154);
                        int length9 = this.eV == null ? 0 : this.eV.length;
                        String[] strArr3 = new String[iB9 + length9];
                        if (length9 != 0) {
                            System.arraycopy(this.eV, 0, strArr3, 0, length9);
                        }
                        while (length9 < strArr3.length - 1) {
                            strArr3[length9] = knVar.readString();
                            knVar.mh();
                            length9++;
                        }
                        strArr3[length9] = knVar.readString();
                        this.eV = strArr3;
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((((((this.fj ? 1231 : 1237) + (((((this.fh == null ? 0 : this.fh.hashCode()) + (((this.fg == null ? 0 : this.fg.hashCode()) + (((this.ff == null ? 0 : this.ff.hashCode()) + (((this.fe == null ? 0 : this.fe.hashCode()) + (((this.fd == null ? 0 : this.fd.hashCode()) + ((((((((((((((((kr.hashCode(this.eV) + 527) * 31) + kr.hashCode(this.eW)) * 31) + kr.hashCode(this.eX)) * 31) + kr.hashCode(this.eY)) * 31) + kr.hashCode(this.eZ)) * 31) + kr.hashCode(this.fa)) * 31) + kr.hashCode(this.fb)) * 31) + kr.hashCode(this.fc)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + Float.floatToIntBits(this.fi)) * 31)) * 31) + kr.hashCode(this.fk)) * 31) + this.fl) * 31;
            if (this.adU != null && !this.adU.isEmpty()) {
                iHashCode = this.adU.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        public f k() {
            this.eV = kw.aef;
            this.eW = kw.aef;
            this.eX = com.google.android.gms.internal.d.a.r();
            this.eY = e.i();
            this.eZ = b.d();
            this.fa = b.d();
            this.fb = b.d();
            this.fc = g.l();
            this.fd = "";
            this.fe = "";
            this.ff = "0";
            this.fg = "";
            this.fh = null;
            this.fi = BitmapDescriptorFactory.HUE_RED;
            this.fj = false;
            this.fk = kw.aef;
            this.fl = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class g extends kp<g> {
        private static volatile g[] fm;
        public int[] fn;
        public int[] fo;
        public int[] fp;
        public int[] fq;
        public int[] fr;
        public int[] fs;
        public int[] ft;
        public int[] fu;
        public int[] fv;
        public int[] fw;

        public g() {
            m();
        }

        public static g[] l() {
            if (fm == null) {
                synchronized (kr.adX) {
                    if (fm == null) {
                        fm = new g[0];
                    }
                }
            }
            return fm;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.fn != null && this.fn.length > 0) {
                for (int i = 0; i < this.fn.length; i++) {
                    koVar.i(1, this.fn[i]);
                }
            }
            if (this.fo != null && this.fo.length > 0) {
                for (int i2 = 0; i2 < this.fo.length; i2++) {
                    koVar.i(2, this.fo[i2]);
                }
            }
            if (this.fp != null && this.fp.length > 0) {
                for (int i3 = 0; i3 < this.fp.length; i3++) {
                    koVar.i(3, this.fp[i3]);
                }
            }
            if (this.fq != null && this.fq.length > 0) {
                for (int i4 = 0; i4 < this.fq.length; i4++) {
                    koVar.i(4, this.fq[i4]);
                }
            }
            if (this.fr != null && this.fr.length > 0) {
                for (int i5 = 0; i5 < this.fr.length; i5++) {
                    koVar.i(5, this.fr[i5]);
                }
            }
            if (this.fs != null && this.fs.length > 0) {
                for (int i6 = 0; i6 < this.fs.length; i6++) {
                    koVar.i(6, this.fs[i6]);
                }
            }
            if (this.ft != null && this.ft.length > 0) {
                for (int i7 = 0; i7 < this.ft.length; i7++) {
                    koVar.i(7, this.ft[i7]);
                }
            }
            if (this.fu != null && this.fu.length > 0) {
                for (int i8 = 0; i8 < this.fu.length; i8++) {
                    koVar.i(8, this.fu[i8]);
                }
            }
            if (this.fv != null && this.fv.length > 0) {
                for (int i9 = 0; i9 < this.fv.length; i9++) {
                    koVar.i(9, this.fv[i9]);
                }
            }
            if (this.fw != null && this.fw.length > 0) {
                for (int i10 = 0; i10 < this.fw.length; i10++) {
                    koVar.i(10, this.fw[i10]);
                }
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int length;
            int iC = super.c();
            if (this.fn == null || this.fn.length <= 0) {
                length = iC;
            } else {
                int iCX = 0;
                for (int i = 0; i < this.fn.length; i++) {
                    iCX += ko.cX(this.fn[i]);
                }
                length = iC + iCX + (this.fn.length * 1);
            }
            if (this.fo != null && this.fo.length > 0) {
                int iCX2 = 0;
                for (int i2 = 0; i2 < this.fo.length; i2++) {
                    iCX2 += ko.cX(this.fo[i2]);
                }
                length = length + iCX2 + (this.fo.length * 1);
            }
            if (this.fp != null && this.fp.length > 0) {
                int iCX3 = 0;
                for (int i3 = 0; i3 < this.fp.length; i3++) {
                    iCX3 += ko.cX(this.fp[i3]);
                }
                length = length + iCX3 + (this.fp.length * 1);
            }
            if (this.fq != null && this.fq.length > 0) {
                int iCX4 = 0;
                for (int i4 = 0; i4 < this.fq.length; i4++) {
                    iCX4 += ko.cX(this.fq[i4]);
                }
                length = length + iCX4 + (this.fq.length * 1);
            }
            if (this.fr != null && this.fr.length > 0) {
                int iCX5 = 0;
                for (int i5 = 0; i5 < this.fr.length; i5++) {
                    iCX5 += ko.cX(this.fr[i5]);
                }
                length = length + iCX5 + (this.fr.length * 1);
            }
            if (this.fs != null && this.fs.length > 0) {
                int iCX6 = 0;
                for (int i6 = 0; i6 < this.fs.length; i6++) {
                    iCX6 += ko.cX(this.fs[i6]);
                }
                length = length + iCX6 + (this.fs.length * 1);
            }
            if (this.ft != null && this.ft.length > 0) {
                int iCX7 = 0;
                for (int i7 = 0; i7 < this.ft.length; i7++) {
                    iCX7 += ko.cX(this.ft[i7]);
                }
                length = length + iCX7 + (this.ft.length * 1);
            }
            if (this.fu != null && this.fu.length > 0) {
                int iCX8 = 0;
                for (int i8 = 0; i8 < this.fu.length; i8++) {
                    iCX8 += ko.cX(this.fu[i8]);
                }
                length = length + iCX8 + (this.fu.length * 1);
            }
            if (this.fv != null && this.fv.length > 0) {
                int iCX9 = 0;
                for (int i9 = 0; i9 < this.fv.length; i9++) {
                    iCX9 += ko.cX(this.fv[i9]);
                }
                length = length + iCX9 + (this.fv.length * 1);
            }
            if (this.fw != null && this.fw.length > 0) {
                int iCX10 = 0;
                for (int i10 = 0; i10 < this.fw.length; i10++) {
                    iCX10 += ko.cX(this.fw[i10]);
                }
                length = length + iCX10 + (this.fw.length * 1);
            }
            this.adY = length;
            return length;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof g)) {
                return false;
            }
            g gVar = (g) o;
            if (!kr.equals(this.fn, gVar.fn) || !kr.equals(this.fo, gVar.fo) || !kr.equals(this.fp, gVar.fp) || !kr.equals(this.fq, gVar.fq) || !kr.equals(this.fr, gVar.fr) || !kr.equals(this.fs, gVar.fs) || !kr.equals(this.ft, gVar.ft) || !kr.equals(this.fu, gVar.fu) || !kr.equals(this.fv, gVar.fv) || !kr.equals(this.fw, gVar.fw)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return gVar.adU == null || gVar.adU.isEmpty();
            }
            return this.adU.equals(gVar.adU);
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
        public g b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 8:
                        int iB = kw.b(knVar, 8);
                        int length = this.fn == null ? 0 : this.fn.length;
                        int[] iArr = new int[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.fn, 0, iArr, 0, length);
                        }
                        while (length < iArr.length - 1) {
                            iArr[length] = knVar.mk();
                            knVar.mh();
                            length++;
                        }
                        iArr[length] = knVar.mk();
                        this.fn = iArr;
                        break;
                    case 10:
                        int iCR = knVar.cR(knVar.mn());
                        int position = knVar.getPosition();
                        int i = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i++;
                        }
                        knVar.cT(position);
                        int length2 = this.fn == null ? 0 : this.fn.length;
                        int[] iArr2 = new int[i + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.fn, 0, iArr2, 0, length2);
                        }
                        while (length2 < iArr2.length) {
                            iArr2[length2] = knVar.mk();
                            length2++;
                        }
                        this.fn = iArr2;
                        knVar.cS(iCR);
                        break;
                    case 16:
                        int iB2 = kw.b(knVar, 16);
                        int length3 = this.fo == null ? 0 : this.fo.length;
                        int[] iArr3 = new int[iB2 + length3];
                        if (length3 != 0) {
                            System.arraycopy(this.fo, 0, iArr3, 0, length3);
                        }
                        while (length3 < iArr3.length - 1) {
                            iArr3[length3] = knVar.mk();
                            knVar.mh();
                            length3++;
                        }
                        iArr3[length3] = knVar.mk();
                        this.fo = iArr3;
                        break;
                    case 18:
                        int iCR2 = knVar.cR(knVar.mn());
                        int position2 = knVar.getPosition();
                        int i2 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i2++;
                        }
                        knVar.cT(position2);
                        int length4 = this.fo == null ? 0 : this.fo.length;
                        int[] iArr4 = new int[i2 + length4];
                        if (length4 != 0) {
                            System.arraycopy(this.fo, 0, iArr4, 0, length4);
                        }
                        while (length4 < iArr4.length) {
                            iArr4[length4] = knVar.mk();
                            length4++;
                        }
                        this.fo = iArr4;
                        knVar.cS(iCR2);
                        break;
                    case TimeConstants.HOURSPERDAY /* 24 */:
                        int iB3 = kw.b(knVar, 24);
                        int length5 = this.fp == null ? 0 : this.fp.length;
                        int[] iArr5 = new int[iB3 + length5];
                        if (length5 != 0) {
                            System.arraycopy(this.fp, 0, iArr5, 0, length5);
                        }
                        while (length5 < iArr5.length - 1) {
                            iArr5[length5] = knVar.mk();
                            knVar.mh();
                            length5++;
                        }
                        iArr5[length5] = knVar.mk();
                        this.fp = iArr5;
                        break;
                    case 26:
                        int iCR3 = knVar.cR(knVar.mn());
                        int position3 = knVar.getPosition();
                        int i3 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i3++;
                        }
                        knVar.cT(position3);
                        int length6 = this.fp == null ? 0 : this.fp.length;
                        int[] iArr6 = new int[i3 + length6];
                        if (length6 != 0) {
                            System.arraycopy(this.fp, 0, iArr6, 0, length6);
                        }
                        while (length6 < iArr6.length) {
                            iArr6[length6] = knVar.mk();
                            length6++;
                        }
                        this.fp = iArr6;
                        knVar.cS(iCR3);
                        break;
                    case 32:
                        int iB4 = kw.b(knVar, 32);
                        int length7 = this.fq == null ? 0 : this.fq.length;
                        int[] iArr7 = new int[iB4 + length7];
                        if (length7 != 0) {
                            System.arraycopy(this.fq, 0, iArr7, 0, length7);
                        }
                        while (length7 < iArr7.length - 1) {
                            iArr7[length7] = knVar.mk();
                            knVar.mh();
                            length7++;
                        }
                        iArr7[length7] = knVar.mk();
                        this.fq = iArr7;
                        break;
                    case 34:
                        int iCR4 = knVar.cR(knVar.mn());
                        int position4 = knVar.getPosition();
                        int i4 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i4++;
                        }
                        knVar.cT(position4);
                        int length8 = this.fq == null ? 0 : this.fq.length;
                        int[] iArr8 = new int[i4 + length8];
                        if (length8 != 0) {
                            System.arraycopy(this.fq, 0, iArr8, 0, length8);
                        }
                        while (length8 < iArr8.length) {
                            iArr8[length8] = knVar.mk();
                            length8++;
                        }
                        this.fq = iArr8;
                        knVar.cS(iCR4);
                        break;
                    case 40:
                        int iB5 = kw.b(knVar, 40);
                        int length9 = this.fr == null ? 0 : this.fr.length;
                        int[] iArr9 = new int[iB5 + length9];
                        if (length9 != 0) {
                            System.arraycopy(this.fr, 0, iArr9, 0, length9);
                        }
                        while (length9 < iArr9.length - 1) {
                            iArr9[length9] = knVar.mk();
                            knVar.mh();
                            length9++;
                        }
                        iArr9[length9] = knVar.mk();
                        this.fr = iArr9;
                        break;
                    case 42:
                        int iCR5 = knVar.cR(knVar.mn());
                        int position5 = knVar.getPosition();
                        int i5 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i5++;
                        }
                        knVar.cT(position5);
                        int length10 = this.fr == null ? 0 : this.fr.length;
                        int[] iArr10 = new int[i5 + length10];
                        if (length10 != 0) {
                            System.arraycopy(this.fr, 0, iArr10, 0, length10);
                        }
                        while (length10 < iArr10.length) {
                            iArr10[length10] = knVar.mk();
                            length10++;
                        }
                        this.fr = iArr10;
                        knVar.cS(iCR5);
                        break;
                    case BannerAd.POSITION_TOP /* 48 */:
                        int iB6 = kw.b(knVar, 48);
                        int length11 = this.fs == null ? 0 : this.fs.length;
                        int[] iArr11 = new int[iB6 + length11];
                        if (length11 != 0) {
                            System.arraycopy(this.fs, 0, iArr11, 0, length11);
                        }
                        while (length11 < iArr11.length - 1) {
                            iArr11[length11] = knVar.mk();
                            knVar.mh();
                            length11++;
                        }
                        iArr11[length11] = knVar.mk();
                        this.fs = iArr11;
                        break;
                    case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                        int iCR6 = knVar.cR(knVar.mn());
                        int position6 = knVar.getPosition();
                        int i6 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i6++;
                        }
                        knVar.cT(position6);
                        int length12 = this.fs == null ? 0 : this.fs.length;
                        int[] iArr12 = new int[i6 + length12];
                        if (length12 != 0) {
                            System.arraycopy(this.fs, 0, iArr12, 0, length12);
                        }
                        while (length12 < iArr12.length) {
                            iArr12[length12] = knVar.mk();
                            length12++;
                        }
                        this.fs = iArr12;
                        knVar.cS(iCR6);
                        break;
                    case 56:
                        int iB7 = kw.b(knVar, 56);
                        int length13 = this.ft == null ? 0 : this.ft.length;
                        int[] iArr13 = new int[iB7 + length13];
                        if (length13 != 0) {
                            System.arraycopy(this.ft, 0, iArr13, 0, length13);
                        }
                        while (length13 < iArr13.length - 1) {
                            iArr13[length13] = knVar.mk();
                            knVar.mh();
                            length13++;
                        }
                        iArr13[length13] = knVar.mk();
                        this.ft = iArr13;
                        break;
                    case 58:
                        int iCR7 = knVar.cR(knVar.mn());
                        int position7 = knVar.getPosition();
                        int i7 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i7++;
                        }
                        knVar.cT(position7);
                        int length14 = this.ft == null ? 0 : this.ft.length;
                        int[] iArr14 = new int[i7 + length14];
                        if (length14 != 0) {
                            System.arraycopy(this.ft, 0, iArr14, 0, length14);
                        }
                        while (length14 < iArr14.length) {
                            iArr14[length14] = knVar.mk();
                            length14++;
                        }
                        this.ft = iArr14;
                        knVar.cS(iCR7);
                        break;
                    case 64:
                        int iB8 = kw.b(knVar, 64);
                        int length15 = this.fu == null ? 0 : this.fu.length;
                        int[] iArr15 = new int[iB8 + length15];
                        if (length15 != 0) {
                            System.arraycopy(this.fu, 0, iArr15, 0, length15);
                        }
                        while (length15 < iArr15.length - 1) {
                            iArr15[length15] = knVar.mk();
                            knVar.mh();
                            length15++;
                        }
                        iArr15[length15] = knVar.mk();
                        this.fu = iArr15;
                        break;
                    case 66:
                        int iCR8 = knVar.cR(knVar.mn());
                        int position8 = knVar.getPosition();
                        int i8 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i8++;
                        }
                        knVar.cT(position8);
                        int length16 = this.fu == null ? 0 : this.fu.length;
                        int[] iArr16 = new int[i8 + length16];
                        if (length16 != 0) {
                            System.arraycopy(this.fu, 0, iArr16, 0, length16);
                        }
                        while (length16 < iArr16.length) {
                            iArr16[length16] = knVar.mk();
                            length16++;
                        }
                        this.fu = iArr16;
                        knVar.cS(iCR8);
                        break;
                    case 72:
                        int iB9 = kw.b(knVar, 72);
                        int length17 = this.fv == null ? 0 : this.fv.length;
                        int[] iArr17 = new int[iB9 + length17];
                        if (length17 != 0) {
                            System.arraycopy(this.fv, 0, iArr17, 0, length17);
                        }
                        while (length17 < iArr17.length - 1) {
                            iArr17[length17] = knVar.mk();
                            knVar.mh();
                            length17++;
                        }
                        iArr17[length17] = knVar.mk();
                        this.fv = iArr17;
                        break;
                    case 74:
                        int iCR9 = knVar.cR(knVar.mn());
                        int position9 = knVar.getPosition();
                        int i9 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i9++;
                        }
                        knVar.cT(position9);
                        int length18 = this.fv == null ? 0 : this.fv.length;
                        int[] iArr18 = new int[i9 + length18];
                        if (length18 != 0) {
                            System.arraycopy(this.fv, 0, iArr18, 0, length18);
                        }
                        while (length18 < iArr18.length) {
                            iArr18[length18] = knVar.mk();
                            length18++;
                        }
                        this.fv = iArr18;
                        knVar.cS(iCR9);
                        break;
                    case BannerAd.POSITION_BOTTOM /* 80 */:
                        int iB10 = kw.b(knVar, 80);
                        int length19 = this.fw == null ? 0 : this.fw.length;
                        int[] iArr19 = new int[iB10 + length19];
                        if (length19 != 0) {
                            System.arraycopy(this.fw, 0, iArr19, 0, length19);
                        }
                        while (length19 < iArr19.length - 1) {
                            iArr19[length19] = knVar.mk();
                            knVar.mh();
                            length19++;
                        }
                        iArr19[length19] = knVar.mk();
                        this.fw = iArr19;
                        break;
                    case 82:
                        int iCR10 = knVar.cR(knVar.mn());
                        int position10 = knVar.getPosition();
                        int i10 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i10++;
                        }
                        knVar.cT(position10);
                        int length20 = this.fw == null ? 0 : this.fw.length;
                        int[] iArr20 = new int[i10 + length20];
                        if (length20 != 0) {
                            System.arraycopy(this.fw, 0, iArr20, 0, length20);
                        }
                        while (length20 < iArr20.length) {
                            iArr20[length20] = knVar.mk();
                            length20++;
                        }
                        this.fw = iArr20;
                        knVar.cS(iCR10);
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public int hashCode() {
            return ((this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode()) + ((((((((((((((((((((kr.hashCode(this.fn) + 527) * 31) + kr.hashCode(this.fo)) * 31) + kr.hashCode(this.fp)) * 31) + kr.hashCode(this.fq)) * 31) + kr.hashCode(this.fr)) * 31) + kr.hashCode(this.fs)) * 31) + kr.hashCode(this.ft)) * 31) + kr.hashCode(this.fu)) * 31) + kr.hashCode(this.fv)) * 31) + kr.hashCode(this.fw)) * 31);
        }

        public g m() {
            this.fn = kw.aea;
            this.fo = kw.aea;
            this.fp = kw.aea;
            this.fq = kw.aea;
            this.fr = kw.aea;
            this.fs = kw.aea;
            this.ft = kw.aea;
            this.fu = kw.aea;
            this.fv = kw.aea;
            this.fw = kw.aea;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class h extends kp<h> {
        public static final kq<com.google.android.gms.internal.d.a, h> fx = kq.a(11, h.class, 810);
        private static final h[] fy = new h[0];
        public int[] fA;
        public int[] fB;
        public int fC;
        public int[] fD;
        public int fE;
        public int fF;
        public int[] fz;

        public h() {
            n();
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.fz != null && this.fz.length > 0) {
                for (int i = 0; i < this.fz.length; i++) {
                    koVar.i(1, this.fz[i]);
                }
            }
            if (this.fA != null && this.fA.length > 0) {
                for (int i2 = 0; i2 < this.fA.length; i2++) {
                    koVar.i(2, this.fA[i2]);
                }
            }
            if (this.fB != null && this.fB.length > 0) {
                for (int i3 = 0; i3 < this.fB.length; i3++) {
                    koVar.i(3, this.fB[i3]);
                }
            }
            if (this.fC != 0) {
                koVar.i(4, this.fC);
            }
            if (this.fD != null && this.fD.length > 0) {
                for (int i4 = 0; i4 < this.fD.length; i4++) {
                    koVar.i(5, this.fD[i4]);
                }
            }
            if (this.fE != 0) {
                koVar.i(6, this.fE);
            }
            if (this.fF != 0) {
                koVar.i(7, this.fF);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iJ;
            int iC = super.c();
            if (this.fz == null || this.fz.length <= 0) {
                iJ = iC;
            } else {
                int iCX = 0;
                for (int i = 0; i < this.fz.length; i++) {
                    iCX += ko.cX(this.fz[i]);
                }
                iJ = iC + iCX + (this.fz.length * 1);
            }
            if (this.fA != null && this.fA.length > 0) {
                int iCX2 = 0;
                for (int i2 = 0; i2 < this.fA.length; i2++) {
                    iCX2 += ko.cX(this.fA[i2]);
                }
                iJ = iJ + iCX2 + (this.fA.length * 1);
            }
            if (this.fB != null && this.fB.length > 0) {
                int iCX3 = 0;
                for (int i3 = 0; i3 < this.fB.length; i3++) {
                    iCX3 += ko.cX(this.fB[i3]);
                }
                iJ = iJ + iCX3 + (this.fB.length * 1);
            }
            if (this.fC != 0) {
                iJ += ko.j(4, this.fC);
            }
            if (this.fD != null && this.fD.length > 0) {
                int iCX4 = 0;
                for (int i4 = 0; i4 < this.fD.length; i4++) {
                    iCX4 += ko.cX(this.fD[i4]);
                }
                iJ = iJ + iCX4 + (this.fD.length * 1);
            }
            if (this.fE != 0) {
                iJ += ko.j(6, this.fE);
            }
            if (this.fF != 0) {
                iJ += ko.j(7, this.fF);
            }
            this.adY = iJ;
            return iJ;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof h)) {
                return false;
            }
            h hVar = (h) o;
            if (!kr.equals(this.fz, hVar.fz) || !kr.equals(this.fA, hVar.fA) || !kr.equals(this.fB, hVar.fB) || this.fC != hVar.fC || !kr.equals(this.fD, hVar.fD) || this.fE != hVar.fE || this.fF != hVar.fF) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return hVar.adU == null || hVar.adU.isEmpty();
            }
            return this.adU.equals(hVar.adU);
        }

        public int hashCode() {
            return ((this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode()) + ((((((((((((((kr.hashCode(this.fz) + 527) * 31) + kr.hashCode(this.fA)) * 31) + kr.hashCode(this.fB)) * 31) + this.fC) * 31) + kr.hashCode(this.fD)) * 31) + this.fE) * 31) + this.fF) * 31);
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
        public h b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 8:
                        int iB = kw.b(knVar, 8);
                        int length = this.fz == null ? 0 : this.fz.length;
                        int[] iArr = new int[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.fz, 0, iArr, 0, length);
                        }
                        while (length < iArr.length - 1) {
                            iArr[length] = knVar.mk();
                            knVar.mh();
                            length++;
                        }
                        iArr[length] = knVar.mk();
                        this.fz = iArr;
                        break;
                    case 10:
                        int iCR = knVar.cR(knVar.mn());
                        int position = knVar.getPosition();
                        int i = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i++;
                        }
                        knVar.cT(position);
                        int length2 = this.fz == null ? 0 : this.fz.length;
                        int[] iArr2 = new int[i + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.fz, 0, iArr2, 0, length2);
                        }
                        while (length2 < iArr2.length) {
                            iArr2[length2] = knVar.mk();
                            length2++;
                        }
                        this.fz = iArr2;
                        knVar.cS(iCR);
                        break;
                    case 16:
                        int iB2 = kw.b(knVar, 16);
                        int length3 = this.fA == null ? 0 : this.fA.length;
                        int[] iArr3 = new int[iB2 + length3];
                        if (length3 != 0) {
                            System.arraycopy(this.fA, 0, iArr3, 0, length3);
                        }
                        while (length3 < iArr3.length - 1) {
                            iArr3[length3] = knVar.mk();
                            knVar.mh();
                            length3++;
                        }
                        iArr3[length3] = knVar.mk();
                        this.fA = iArr3;
                        break;
                    case 18:
                        int iCR2 = knVar.cR(knVar.mn());
                        int position2 = knVar.getPosition();
                        int i2 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i2++;
                        }
                        knVar.cT(position2);
                        int length4 = this.fA == null ? 0 : this.fA.length;
                        int[] iArr4 = new int[i2 + length4];
                        if (length4 != 0) {
                            System.arraycopy(this.fA, 0, iArr4, 0, length4);
                        }
                        while (length4 < iArr4.length) {
                            iArr4[length4] = knVar.mk();
                            length4++;
                        }
                        this.fA = iArr4;
                        knVar.cS(iCR2);
                        break;
                    case TimeConstants.HOURSPERDAY /* 24 */:
                        int iB3 = kw.b(knVar, 24);
                        int length5 = this.fB == null ? 0 : this.fB.length;
                        int[] iArr5 = new int[iB3 + length5];
                        if (length5 != 0) {
                            System.arraycopy(this.fB, 0, iArr5, 0, length5);
                        }
                        while (length5 < iArr5.length - 1) {
                            iArr5[length5] = knVar.mk();
                            knVar.mh();
                            length5++;
                        }
                        iArr5[length5] = knVar.mk();
                        this.fB = iArr5;
                        break;
                    case 26:
                        int iCR3 = knVar.cR(knVar.mn());
                        int position3 = knVar.getPosition();
                        int i3 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i3++;
                        }
                        knVar.cT(position3);
                        int length6 = this.fB == null ? 0 : this.fB.length;
                        int[] iArr6 = new int[i3 + length6];
                        if (length6 != 0) {
                            System.arraycopy(this.fB, 0, iArr6, 0, length6);
                        }
                        while (length6 < iArr6.length) {
                            iArr6[length6] = knVar.mk();
                            length6++;
                        }
                        this.fB = iArr6;
                        knVar.cS(iCR3);
                        break;
                    case 32:
                        this.fC = knVar.mk();
                        break;
                    case 40:
                        int iB4 = kw.b(knVar, 40);
                        int length7 = this.fD == null ? 0 : this.fD.length;
                        int[] iArr7 = new int[iB4 + length7];
                        if (length7 != 0) {
                            System.arraycopy(this.fD, 0, iArr7, 0, length7);
                        }
                        while (length7 < iArr7.length - 1) {
                            iArr7[length7] = knVar.mk();
                            knVar.mh();
                            length7++;
                        }
                        iArr7[length7] = knVar.mk();
                        this.fD = iArr7;
                        break;
                    case 42:
                        int iCR4 = knVar.cR(knVar.mn());
                        int position4 = knVar.getPosition();
                        int i4 = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            i4++;
                        }
                        knVar.cT(position4);
                        int length8 = this.fD == null ? 0 : this.fD.length;
                        int[] iArr8 = new int[i4 + length8];
                        if (length8 != 0) {
                            System.arraycopy(this.fD, 0, iArr8, 0, length8);
                        }
                        while (length8 < iArr8.length) {
                            iArr8[length8] = knVar.mk();
                            length8++;
                        }
                        this.fD = iArr8;
                        knVar.cS(iCR4);
                        break;
                    case BannerAd.POSITION_TOP /* 48 */:
                        this.fE = knVar.mk();
                        break;
                    case 56:
                        this.fF = knVar.mk();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public h n() {
            this.fz = kw.aea;
            this.fA = kw.aea;
            this.fB = kw.aea;
            this.fC = 0;
            this.fD = kw.aea;
            this.fE = 0;
            this.fF = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class i extends kp<i> {
        private static volatile i[] fG;
        public com.google.android.gms.internal.d.a fH;
        public d fI;
        public String name;

        public i() {
            p();
        }

        public static i[] o() {
            if (fG == null) {
                synchronized (kr.adX) {
                    if (fG == null) {
                        fG = new i[0];
                    }
                }
            }
            return fG;
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (!this.name.equals("")) {
                koVar.b(1, this.name);
            }
            if (this.fH != null) {
                koVar.a(2, this.fH);
            }
            if (this.fI != null) {
                koVar.a(3, this.fI);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c();
            if (!this.name.equals("")) {
                iC += ko.g(1, this.name);
            }
            if (this.fH != null) {
                iC += ko.b(2, this.fH);
            }
            if (this.fI != null) {
                iC += ko.b(3, this.fI);
            }
            this.adY = iC;
            return iC;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof i)) {
                return false;
            }
            i iVar = (i) o;
            if (this.name == null) {
                if (iVar.name != null) {
                    return false;
                }
            } else if (!this.name.equals(iVar.name)) {
                return false;
            }
            if (this.fH == null) {
                if (iVar.fH != null) {
                    return false;
                }
            } else if (!this.fH.equals(iVar.fH)) {
                return false;
            }
            if (this.fI == null) {
                if (iVar.fI != null) {
                    return false;
                }
            } else if (!this.fI.equals(iVar.fI)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return iVar.adU == null || iVar.adU.isEmpty();
            }
            return this.adU.equals(iVar.adU);
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((this.fI == null ? 0 : this.fI.hashCode()) + (((this.fH == null ? 0 : this.fH.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + 527) * 31)) * 31)) * 31;
            if (this.adU != null && !this.adU.isEmpty()) {
                iHashCode = this.adU.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
        public i b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 10:
                        this.name = knVar.readString();
                        break;
                    case 18:
                        if (this.fH == null) {
                            this.fH = new com.google.android.gms.internal.d.a();
                        }
                        knVar.a(this.fH);
                        break;
                    case 26:
                        if (this.fI == null) {
                            this.fI = new d();
                        }
                        knVar.a(this.fI);
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public i p() {
            this.name = "";
            this.fH = null;
            this.fI = null;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class j extends kp<j> {
        public i[] fJ;
        public f fK;
        public String fL;

        public j() {
            q();
        }

        public static j b(byte[] bArr) throws ks {
            return (j) kt.a(new j(), bArr);
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            if (this.fJ != null && this.fJ.length > 0) {
                for (int i = 0; i < this.fJ.length; i++) {
                    i iVar = this.fJ[i];
                    if (iVar != null) {
                        koVar.a(1, iVar);
                    }
                }
            }
            if (this.fK != null) {
                koVar.a(2, this.fK);
            }
            if (!this.fL.equals("")) {
                koVar.b(3, this.fL);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c();
            if (this.fJ != null && this.fJ.length > 0) {
                for (int i = 0; i < this.fJ.length; i++) {
                    i iVar = this.fJ[i];
                    if (iVar != null) {
                        iC += ko.b(1, iVar);
                    }
                }
            }
            if (this.fK != null) {
                iC += ko.b(2, this.fK);
            }
            if (!this.fL.equals("")) {
                iC += ko.g(3, this.fL);
            }
            this.adY = iC;
            return iC;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof j)) {
                return false;
            }
            j jVar = (j) o;
            if (!kr.equals(this.fJ, jVar.fJ)) {
                return false;
            }
            if (this.fK == null) {
                if (jVar.fK != null) {
                    return false;
                }
            } else if (!this.fK.equals(jVar.fK)) {
                return false;
            }
            if (this.fL == null) {
                if (jVar.fL != null) {
                    return false;
                }
            } else if (!this.fL.equals(jVar.fL)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return jVar.adU == null || jVar.adU.isEmpty();
            }
            return this.adU.equals(jVar.adU);
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((this.fL == null ? 0 : this.fL.hashCode()) + (((this.fK == null ? 0 : this.fK.hashCode()) + ((kr.hashCode(this.fJ) + 527) * 31)) * 31)) * 31;
            if (this.adU != null && !this.adU.isEmpty()) {
                iHashCode = this.adU.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
        public j b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 10:
                        int iB = kw.b(knVar, 10);
                        int length = this.fJ == null ? 0 : this.fJ.length;
                        i[] iVarArr = new i[iB + length];
                        if (length != 0) {
                            System.arraycopy(this.fJ, 0, iVarArr, 0, length);
                        }
                        while (length < iVarArr.length - 1) {
                            iVarArr[length] = new i();
                            knVar.a(iVarArr[length]);
                            knVar.mh();
                            length++;
                        }
                        iVarArr[length] = new i();
                        knVar.a(iVarArr[length]);
                        this.fJ = iVarArr;
                        break;
                    case 18:
                        if (this.fK == null) {
                            this.fK = new f();
                        }
                        knVar.a(this.fK);
                        break;
                    case 26:
                        this.fL = knVar.readString();
                        break;
                    default:
                        if (!a(knVar, iMh)) {
                        }
                        break;
                }
            }
            return this;
        }

        public j q() {
            this.fJ = i.o();
            this.fK = null;
            this.fL = "";
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }
}
