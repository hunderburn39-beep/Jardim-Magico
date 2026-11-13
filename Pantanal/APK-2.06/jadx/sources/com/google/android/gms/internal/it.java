package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface it {

    public static final class a extends kp<a> {
        public long aaY;
        public c.j aaZ;
        public c.f fK;

        public a() {
            lV();
        }

        public static a l(byte[] bArr) throws ks {
            return (a) kt.a(new a(), bArr);
        }

        @Override // com.google.android.gms.internal.kp, com.google.android.gms.internal.kt
        public void a(ko koVar) throws IOException {
            koVar.b(1, this.aaY);
            if (this.fK != null) {
                koVar.a(2, this.fK);
            }
            if (this.aaZ != null) {
                koVar.a(3, this.aaZ);
            }
            super.a(koVar);
        }

        @Override // com.google.android.gms.internal.kt
        public int c() {
            int iC = super.c() + ko.d(1, this.aaY);
            if (this.fK != null) {
                iC += ko.b(2, this.fK);
            }
            if (this.aaZ != null) {
                iC += ko.b(3, this.aaZ);
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
            if (this.aaY != aVar.aaY) {
                return false;
            }
            if (this.fK == null) {
                if (aVar.fK != null) {
                    return false;
                }
            } else if (!this.fK.equals(aVar.fK)) {
                return false;
            }
            if (this.aaZ == null) {
                if (aVar.aaZ != null) {
                    return false;
                }
            } else if (!this.aaZ.equals(aVar.aaZ)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return aVar.adU == null || aVar.adU.isEmpty();
            }
            return this.adU.equals(aVar.adU);
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((this.aaZ == null ? 0 : this.aaZ.hashCode()) + (((this.fK == null ? 0 : this.fK.hashCode()) + ((((int) (this.aaY ^ (this.aaY >>> 32))) + 527) * 31)) * 31)) * 31;
            if (this.adU != null && !this.adU.isEmpty()) {
                iHashCode = this.adU.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        public a lV() {
            this.aaY = 0L;
            this.fK = null;
            this.aaZ = null;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        @Override // com.google.android.gms.internal.kt
        /* JADX INFO: renamed from: n, reason: merged with bridge method [inline-methods] */
        public a b(kn knVar) throws IOException {
            while (true) {
                int iMh = knVar.mh();
                switch (iMh) {
                    case 0:
                        break;
                    case 8:
                        this.aaY = knVar.mj();
                        break;
                    case 18:
                        if (this.fK == null) {
                            this.fK = new c.f();
                        }
                        knVar.a(this.fK);
                        break;
                    case 26:
                        if (this.aaZ == null) {
                            this.aaZ = new c.j();
                        }
                        knVar.a(this.aaZ);
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
}
