package com.tapjoy.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.fyber.ads.banners.BannerAd;
import com.google.ads.AdSize;
import com.google.android.gms.location.LocationRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public final class gl {

    public interface aa extends dv {
    }

    public interface b extends dv {
    }

    public interface e extends dv {
    }

    public interface g extends dv {
    }

    public interface h extends dv {
    }

    public interface k extends dv {
    }

    public interface m extends dv {
    }

    public interface o extends dv {
    }

    public interface q extends dv {
    }

    public interface s extends dv {
    }

    public interface v extends dv {
    }

    public interface w extends dv {
    }

    public interface y extends dv {
    }

    public enum i implements dp.a {
        APP(0),
        CAMPAIGN(1),
        CUSTOM(2),
        USAGES(3);

        private static dp.b f = new dp.b() { // from class: com.tapjoy.internal.gl.i.1
        };
        final int e;

        @Override // com.tapjoy.internal.dp.a
        public final int a() {
            return this.e;
        }

        public static i a(int i) {
            switch (i) {
                case 0:
                    return APP;
                case 1:
                    return CAMPAIGN;
                case 2:
                    return CUSTOM;
                case 3:
                    return USAGES;
                default:
                    return null;
            }
        }

        i(int i) {
            this.e = i;
        }
    }

    public static final class l extends Cdo implements m {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.l.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new l(dlVar, (byte) 0);
            }
        };
        private static final l f;
        public int c;
        public int d;
        public int e;
        private final dk g;
        private int h;
        private Object i;
        private Object j;
        private Object k;
        private Object l;
        private Object m;
        private Object n;
        private Object o;
        private Object p;
        private Object q;
        private Object r;
        private Object s;
        private Object t;
        private Object u;
        private Object v;
        private byte w;
        private int x;

        /* synthetic */ l(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ l(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private l(Cdo.a aVar) {
            super((byte) 0);
            this.w = (byte) -1;
            this.x = -1;
            this.g = aVar.a;
        }

        private l() {
            this.w = (byte) -1;
            this.x = -1;
            this.g = dk.b;
        }

        public static l d() {
            return f;
        }

        private l(dl dlVar) {
            this.w = (byte) -1;
            this.x = -1;
            Y();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                this.h |= 1;
                                this.i = dkVarE;
                                break;
                            case 18:
                                dk dkVarE2 = dlVar.e();
                                this.h |= 2;
                                this.j = dkVarE2;
                                break;
                            case 26:
                                dk dkVarE3 = dlVar.e();
                                this.h |= 4;
                                this.k = dkVarE3;
                                break;
                            case 34:
                                dk dkVarE4 = dlVar.e();
                                this.h |= 8;
                                this.l = dkVarE4;
                                break;
                            case 42:
                                dk dkVarE5 = dlVar.e();
                                this.h |= 16;
                                this.m = dkVarE5;
                                break;
                            case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                                dk dkVarE6 = dlVar.e();
                                this.h |= 32;
                                this.n = dkVarE6;
                                break;
                            case 56:
                                this.h |= 64;
                                this.c = dlVar.f();
                                break;
                            case 64:
                                this.h |= 128;
                                this.d = dlVar.f();
                                break;
                            case 72:
                                this.h |= 256;
                                this.e = dlVar.f();
                                break;
                            case 82:
                                dk dkVarE7 = dlVar.e();
                                this.h |= 512;
                                this.o = dkVarE7;
                                break;
                            case AdSize.LARGE_AD_HEIGHT /* 90 */:
                                dk dkVarE8 = dlVar.e();
                                this.h |= 1024;
                                this.p = dkVarE8;
                                break;
                            case 98:
                                dk dkVarE9 = dlVar.e();
                                this.h |= 2048;
                                this.q = dkVarE9;
                                break;
                            case 106:
                                dk dkVarE10 = dlVar.e();
                                this.h |= 4096;
                                this.r = dkVarE10;
                                break;
                            case 114:
                                dk dkVarE11 = dlVar.e();
                                this.h |= 8192;
                                this.s = dkVarE11;
                                break;
                            case 122:
                                dk dkVarE12 = dlVar.e();
                                this.h |= 16384;
                                this.t = dkVarE12;
                                break;
                            case TransportMediator.KEYCODE_MEDIA_RECORD /* 130 */:
                                dk dkVarE13 = dlVar.e();
                                this.h |= 32768;
                                this.u = dkVarE13;
                                break;
                            case 138:
                                dk dkVarE14 = dlVar.e();
                                this.h |= 65536;
                                this.v = dkVarE14;
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (Throwable th) {
                        try {
                            dmVarA.a();
                        } catch (IOException e) {
                        } finally {
                            this.g = bVarG.a();
                        }
                        throw th;
                    }
                } catch (dq e2) {
                    e2.a = this;
                    throw e2;
                } catch (IOException e3) {
                    dq dqVar = new dq(e3.getMessage());
                    dqVar.a = this;
                    throw dqVar;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.g = bVarG.a();
            }
        }

        static {
            l lVar = new l();
            f = lVar;
            lVar.Y();
        }

        public final boolean e() {
            return (this.h & 1) == 1;
        }

        public final String f() {
            Object obj = this.i;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.i = strE;
            }
            return strE;
        }

        private dk K() {
            Object obj = this.i;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.i = dkVarA;
            return dkVarA;
        }

        public final boolean g() {
            return (this.h & 2) == 2;
        }

        public final String h() {
            Object obj = this.j;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.j = strE;
            }
            return strE;
        }

        private dk L() {
            Object obj = this.j;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.j = dkVarA;
            return dkVarA;
        }

        public final boolean i() {
            return (this.h & 4) == 4;
        }

        public final String j() {
            Object obj = this.k;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.k = strE;
            }
            return strE;
        }

        private dk M() {
            Object obj = this.k;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.k = dkVarA;
            return dkVarA;
        }

        public final boolean k() {
            return (this.h & 8) == 8;
        }

        public final String l() {
            Object obj = this.l;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.l = strE;
            }
            return strE;
        }

        private dk N() {
            Object obj = this.l;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.l = dkVarA;
            return dkVarA;
        }

        public final boolean m() {
            return (this.h & 16) == 16;
        }

        public final String n() {
            Object obj = this.m;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.m = strE;
            }
            return strE;
        }

        private dk O() {
            Object obj = this.m;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.m = dkVarA;
            return dkVarA;
        }

        public final boolean o() {
            return (this.h & 32) == 32;
        }

        public final String p() {
            Object obj = this.n;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.n = strE;
            }
            return strE;
        }

        private dk P() {
            Object obj = this.n;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.n = dkVarA;
            return dkVarA;
        }

        public final boolean q() {
            return (this.h & 64) == 64;
        }

        public final boolean r() {
            return (this.h & 128) == 128;
        }

        public final boolean s() {
            return (this.h & 256) == 256;
        }

        public final boolean t() {
            return (this.h & 512) == 512;
        }

        public final String u() {
            Object obj = this.o;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.o = strE;
            }
            return strE;
        }

        private dk Q() {
            Object obj = this.o;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.o = dkVarA;
            return dkVarA;
        }

        public final boolean v() {
            return (this.h & 1024) == 1024;
        }

        public final String w() {
            Object obj = this.p;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.p = strE;
            }
            return strE;
        }

        private dk R() {
            Object obj = this.p;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.p = dkVarA;
            return dkVarA;
        }

        public final boolean x() {
            return (this.h & 2048) == 2048;
        }

        public final String y() {
            Object obj = this.q;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.q = strE;
            }
            return strE;
        }

        private dk S() {
            Object obj = this.q;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.q = dkVarA;
            return dkVarA;
        }

        public final boolean z() {
            return (this.h & 4096) == 4096;
        }

        public final String A() {
            Object obj = this.r;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.r = strE;
            }
            return strE;
        }

        private dk T() {
            Object obj = this.r;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.r = dkVarA;
            return dkVarA;
        }

        public final boolean B() {
            return (this.h & 8192) == 8192;
        }

        public final String C() {
            Object obj = this.s;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.s = strE;
            }
            return strE;
        }

        private dk U() {
            Object obj = this.s;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.s = dkVarA;
            return dkVarA;
        }

        public final boolean D() {
            return (this.h & 16384) == 16384;
        }

        public final String E() {
            Object obj = this.t;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.t = strE;
            }
            return strE;
        }

        private dk V() {
            Object obj = this.t;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.t = dkVarA;
            return dkVarA;
        }

        public final boolean F() {
            return (this.h & 32768) == 32768;
        }

        public final String G() {
            Object obj = this.u;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.u = strE;
            }
            return strE;
        }

        private dk W() {
            Object obj = this.u;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.u = dkVarA;
            return dkVarA;
        }

        public final boolean H() {
            return (this.h & 65536) == 65536;
        }

        public final String I() {
            Object obj = this.v;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.v = strE;
            }
            return strE;
        }

        private dk X() {
            Object obj = this.v;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.v = dkVarA;
            return dkVarA;
        }

        private void Y() {
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = "";
            this.m = "";
            this.n = "";
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.o = "";
            this.p = "";
            this.q = "";
            this.r = "";
            this.s = "";
            this.t = "";
            this.u = "";
            this.v = "";
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.w;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.w = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.h & 1) == 1) {
                dmVar.a(1, K());
            }
            if ((this.h & 2) == 2) {
                dmVar.a(2, L());
            }
            if ((this.h & 4) == 4) {
                dmVar.a(3, M());
            }
            if ((this.h & 8) == 8) {
                dmVar.a(4, N());
            }
            if ((this.h & 16) == 16) {
                dmVar.a(5, O());
            }
            if ((this.h & 32) == 32) {
                dmVar.a(6, P());
            }
            if ((this.h & 64) == 64) {
                dmVar.a(7, this.c);
            }
            if ((this.h & 128) == 128) {
                dmVar.a(8, this.d);
            }
            if ((this.h & 256) == 256) {
                dmVar.a(9, this.e);
            }
            if ((this.h & 512) == 512) {
                dmVar.a(10, Q());
            }
            if ((this.h & 1024) == 1024) {
                dmVar.a(11, R());
            }
            if ((this.h & 2048) == 2048) {
                dmVar.a(12, S());
            }
            if ((this.h & 4096) == 4096) {
                dmVar.a(13, T());
            }
            if ((this.h & 8192) == 8192) {
                dmVar.a(14, U());
            }
            if ((this.h & 16384) == 16384) {
                dmVar.a(15, V());
            }
            if ((this.h & 32768) == 32768) {
                dmVar.a(16, W());
            }
            if ((this.h & 65536) == 65536) {
                dmVar.a(17, X());
            }
            dmVar.c(this.g);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.x;
            if (i == -1) {
                int iB = 0;
                if ((this.h & 1) == 1) {
                    iB = dm.b(1, K()) + 0;
                }
                if ((this.h & 2) == 2) {
                    iB += dm.b(2, L());
                }
                if ((this.h & 4) == 4) {
                    iB += dm.b(3, M());
                }
                if ((this.h & 8) == 8) {
                    iB += dm.b(4, N());
                }
                if ((this.h & 16) == 16) {
                    iB += dm.b(5, O());
                }
                if ((this.h & 32) == 32) {
                    iB += dm.b(6, P());
                }
                if ((this.h & 64) == 64) {
                    iB += dm.b(7, this.c);
                }
                if ((this.h & 128) == 128) {
                    iB += dm.b(8, this.d);
                }
                if ((this.h & 256) == 256) {
                    iB += dm.b(9, this.e);
                }
                if ((this.h & 512) == 512) {
                    iB += dm.b(10, Q());
                }
                if ((this.h & 1024) == 1024) {
                    iB += dm.b(11, R());
                }
                if ((this.h & 2048) == 2048) {
                    iB += dm.b(12, S());
                }
                if ((this.h & 4096) == 4096) {
                    iB += dm.b(13, T());
                }
                if ((this.h & 8192) == 8192) {
                    iB += dm.b(14, U());
                }
                if ((this.h & 16384) == 16384) {
                    iB += dm.b(15, V());
                }
                if ((this.h & 32768) == 32768) {
                    iB += dm.b(16, W());
                }
                if ((this.h & 65536) == 65536) {
                    iB += dm.b(17, X());
                }
                int iA = iB + this.g.a();
                this.x = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof l)) {
                return super.equals(obj);
            }
            l lVar = (l) obj;
            boolean z = e() == lVar.e();
            if (e()) {
                z = z && f().equals(lVar.f());
            }
            boolean z2 = z && g() == lVar.g();
            if (g()) {
                z2 = z2 && h().equals(lVar.h());
            }
            boolean z3 = z2 && i() == lVar.i();
            if (i()) {
                z3 = z3 && j().equals(lVar.j());
            }
            boolean z4 = z3 && k() == lVar.k();
            if (k()) {
                z4 = z4 && l().equals(lVar.l());
            }
            boolean z5 = z4 && m() == lVar.m();
            if (m()) {
                z5 = z5 && n().equals(lVar.n());
            }
            boolean z6 = z5 && o() == lVar.o();
            if (o()) {
                z6 = z6 && p().equals(lVar.p());
            }
            boolean z7 = z6 && q() == lVar.q();
            if (q()) {
                z7 = z7 && this.c == lVar.c;
            }
            boolean z8 = z7 && r() == lVar.r();
            if (r()) {
                z8 = z8 && this.d == lVar.d;
            }
            boolean z9 = z8 && s() == lVar.s();
            if (s()) {
                z9 = z9 && this.e == lVar.e;
            }
            boolean z10 = z9 && t() == lVar.t();
            if (t()) {
                z10 = z10 && u().equals(lVar.u());
            }
            boolean z11 = z10 && v() == lVar.v();
            if (v()) {
                z11 = z11 && w().equals(lVar.w());
            }
            boolean z12 = z11 && x() == lVar.x();
            if (x()) {
                z12 = z12 && y().equals(lVar.y());
            }
            boolean z13 = z12 && z() == lVar.z();
            if (z()) {
                z13 = z13 && A().equals(lVar.A());
            }
            boolean z14 = z13 && B() == lVar.B();
            if (B()) {
                z14 = z14 && C().equals(lVar.C());
            }
            boolean z15 = z14 && D() == lVar.D();
            if (D()) {
                z15 = z15 && E().equals(lVar.E());
            }
            boolean z16 = z15 && F() == lVar.F();
            if (F()) {
                z16 = z16 && G().equals(lVar.G());
            }
            boolean z17 = z16 && H() == lVar.H();
            if (H()) {
                return z17 && I().equals(lVar.I());
            }
            return z17;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = l.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + f().hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + h().hashCode();
            }
            if (i()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + j().hashCode();
            }
            if (k()) {
                iHashCode = (((iHashCode * 37) + 4) * 53) + l().hashCode();
            }
            if (m()) {
                iHashCode = (((iHashCode * 37) + 5) * 53) + n().hashCode();
            }
            if (o()) {
                iHashCode = (((iHashCode * 37) + 6) * 53) + p().hashCode();
            }
            if (q()) {
                iHashCode = (((iHashCode * 37) + 7) * 53) + this.c;
            }
            if (r()) {
                iHashCode = (((iHashCode * 37) + 8) * 53) + this.d;
            }
            if (s()) {
                iHashCode = (((iHashCode * 37) + 9) * 53) + this.e;
            }
            if (t()) {
                iHashCode = (((iHashCode * 37) + 10) * 53) + u().hashCode();
            }
            if (v()) {
                iHashCode = (((iHashCode * 37) + 11) * 53) + w().hashCode();
            }
            if (x()) {
                iHashCode = (((iHashCode * 37) + 12) * 53) + y().hashCode();
            }
            if (z()) {
                iHashCode = (((iHashCode * 37) + 13) * 53) + A().hashCode();
            }
            if (B()) {
                iHashCode = (((iHashCode * 37) + 14) * 53) + C().hashCode();
            }
            if (D()) {
                iHashCode = (((iHashCode * 37) + 15) * 53) + E().hashCode();
            }
            if (F()) {
                iHashCode = (((iHashCode * 37) + 16) * 53) + G().hashCode();
            }
            if (H()) {
                iHashCode = (((iHashCode * 37) + 17) * 53) + I().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.g.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a J() {
            return a.e();
        }

        public static final class a extends Cdo.a implements m {
            public int b;
            private int n;
            private int o;
            private int p;
            public Object c = "";
            public Object d = "";
            public Object e = "";
            public Object f = "";
            public Object g = "";
            public Object h = "";
            private Object q = "";
            private Object r = "";
            public Object i = "";
            public Object j = "";
            private Object s = "";
            public Object k = "";
            public Object l = "";
            public Object m = "";

            private a() {
            }

            public final l d() {
                l lVar = new l((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                lVar.i = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                lVar.j = this.d;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                lVar.k = this.e;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                lVar.l = this.f;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                lVar.m = this.g;
                if ((i & 32) == 32) {
                    i2 |= 32;
                }
                lVar.n = this.h;
                if ((i & 64) == 64) {
                    i2 |= 64;
                }
                lVar.c = this.n;
                if ((i & 128) == 128) {
                    i2 |= 128;
                }
                lVar.d = this.o;
                if ((i & 256) == 256) {
                    i2 |= 256;
                }
                lVar.e = this.p;
                if ((i & 512) == 512) {
                    i2 |= 512;
                }
                lVar.o = this.q;
                if ((i & 1024) == 1024) {
                    i2 |= 1024;
                }
                lVar.p = this.r;
                if ((i & 2048) == 2048) {
                    i2 |= 2048;
                }
                lVar.q = this.i;
                if ((i & 4096) == 4096) {
                    i2 |= 4096;
                }
                lVar.r = this.j;
                if ((i & 8192) == 8192) {
                    i2 |= 8192;
                }
                lVar.s = this.s;
                if ((i & 16384) == 16384) {
                    i2 |= 16384;
                }
                lVar.t = this.k;
                if ((i & 32768) == 32768) {
                    i2 |= 32768;
                }
                lVar.u = this.l;
                if ((i & 65536) == 65536) {
                    i2 |= 65536;
                }
                lVar.v = this.m;
                lVar.h = i2;
                return lVar;
            }

            public final a a(l lVar) {
                if (lVar != l.d()) {
                    if (lVar.e()) {
                        this.b |= 1;
                        this.c = lVar.i;
                    }
                    if (lVar.g()) {
                        this.b |= 2;
                        this.d = lVar.j;
                    }
                    if (lVar.i()) {
                        this.b |= 4;
                        this.e = lVar.k;
                    }
                    if (lVar.k()) {
                        this.b |= 8;
                        this.f = lVar.l;
                    }
                    if (lVar.m()) {
                        this.b |= 16;
                        this.g = lVar.m;
                    }
                    if (lVar.o()) {
                        this.b |= 32;
                        this.h = lVar.n;
                    }
                    if (lVar.q()) {
                        a(lVar.c);
                    }
                    if (lVar.r()) {
                        b(lVar.d);
                    }
                    if (lVar.s()) {
                        c(lVar.e);
                    }
                    if (lVar.t()) {
                        this.b |= 512;
                        this.q = lVar.o;
                    }
                    if (lVar.v()) {
                        this.b |= 1024;
                        this.r = lVar.p;
                    }
                    if (lVar.x()) {
                        this.b |= 2048;
                        this.i = lVar.q;
                    }
                    if (lVar.z()) {
                        this.b |= 4096;
                        this.j = lVar.r;
                    }
                    if (lVar.B()) {
                        this.b |= 8192;
                        this.s = lVar.s;
                    }
                    if (lVar.D()) {
                        this.b |= 16384;
                        this.k = lVar.t;
                    }
                    if (lVar.F()) {
                        this.b |= 32768;
                        this.l = lVar.u;
                    }
                    if (lVar.H()) {
                        this.b |= 65536;
                        this.m = lVar.v;
                    }
                    this.a = this.a.a(lVar.g);
                }
                return this;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                return true;
            }

            public final a a(int i) {
                this.b |= 64;
                this.n = i;
                return this;
            }

            public final a b(int i) {
                this.b |= 128;
                this.o = i;
                return this;
            }

            public final a c(int i) {
                this.b |= 256;
                this.p = i;
                return this;
            }

            public final a a(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 512;
                this.q = str;
                return this;
            }

            public final a b(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 1024;
                this.r = str;
                return this;
            }

            public final a c(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 8192;
                this.s = str;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                return new a().a(d());
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }

        public static a a(l lVar) {
            return a.e().a(lVar);
        }
    }

    public static final class a extends Cdo implements b {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.a.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new a(dlVar, (byte) 0);
            }
        };
        private static final a d;
        public int c;
        private final dk e;
        private int f;
        private Object g;
        private Object h;
        private Object i;
        private Object j;
        private byte k;
        private int l;

        /* synthetic */ a(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ a(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private a(Cdo.a aVar) {
            super((byte) 0);
            this.k = (byte) -1;
            this.l = -1;
            this.e = aVar.a;
        }

        private a() {
            this.k = (byte) -1;
            this.l = -1;
            this.e = dk.b;
        }

        public static a d() {
            return d;
        }

        private a(dl dlVar) {
            this.k = (byte) -1;
            this.l = -1;
            t();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                this.f |= 1;
                                this.g = dkVarE;
                                break;
                            case 16:
                                this.f |= 2;
                                this.c = dlVar.f();
                                break;
                            case 26:
                                dk dkVarE2 = dlVar.e();
                                this.f |= 4;
                                this.h = dkVarE2;
                                break;
                            case 34:
                                dk dkVarE3 = dlVar.e();
                                this.f |= 8;
                                this.i = dkVarE3;
                                break;
                            case 42:
                                dk dkVarE4 = dlVar.e();
                                this.f |= 16;
                                this.j = dkVarE4;
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (dq e) {
                        e.a = this;
                        throw e;
                    } catch (IOException e2) {
                        dq dqVar = new dq(e2.getMessage());
                        dqVar.a = this;
                        throw dqVar;
                    }
                } catch (Throwable th) {
                    try {
                        dmVarA.a();
                    } catch (IOException e3) {
                    } finally {
                        this.e = bVarG.a();
                    }
                    throw th;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.e = bVarG.a();
            }
        }

        static {
            a aVar = new a();
            d = aVar;
            aVar.t();
        }

        public final boolean e() {
            return (this.f & 1) == 1;
        }

        public final String f() {
            Object obj = this.g;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.g = strE;
            }
            return strE;
        }

        private dk p() {
            Object obj = this.g;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.g = dkVarA;
            return dkVarA;
        }

        public final boolean g() {
            return (this.f & 2) == 2;
        }

        public final boolean h() {
            return (this.f & 4) == 4;
        }

        public final String i() {
            Object obj = this.h;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.h = strE;
            }
            return strE;
        }

        private dk q() {
            Object obj = this.h;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.h = dkVarA;
            return dkVarA;
        }

        public final boolean j() {
            return (this.f & 8) == 8;
        }

        public final String k() {
            Object obj = this.i;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.i = strE;
            }
            return strE;
        }

        private dk r() {
            Object obj = this.i;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.i = dkVarA;
            return dkVarA;
        }

        public final boolean l() {
            return (this.f & 16) == 16;
        }

        public final String m() {
            Object obj = this.j;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.j = strE;
            }
            return strE;
        }

        private dk s() {
            Object obj = this.j;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.j = dkVarA;
            return dkVarA;
        }

        private void t() {
            this.g = "";
            this.c = 0;
            this.h = "";
            this.i = "";
            this.j = "";
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.k;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.k = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.f & 1) == 1) {
                dmVar.a(1, p());
            }
            if ((this.f & 2) == 2) {
                dmVar.a(2, this.c);
            }
            if ((this.f & 4) == 4) {
                dmVar.a(3, q());
            }
            if ((this.f & 8) == 8) {
                dmVar.a(4, r());
            }
            if ((this.f & 16) == 16) {
                dmVar.a(5, s());
            }
            dmVar.c(this.e);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.l;
            if (i == -1) {
                int iB = 0;
                if ((this.f & 1) == 1) {
                    iB = dm.b(1, p()) + 0;
                }
                if ((this.f & 2) == 2) {
                    iB += dm.b(2, this.c);
                }
                if ((this.f & 4) == 4) {
                    iB += dm.b(3, q());
                }
                if ((this.f & 8) == 8) {
                    iB += dm.b(4, r());
                }
                if ((this.f & 16) == 16) {
                    iB += dm.b(5, s());
                }
                int iA = iB + this.e.a();
                this.l = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return super.equals(obj);
            }
            a aVar = (a) obj;
            boolean z = e() == aVar.e();
            if (e()) {
                z = z && f().equals(aVar.f());
            }
            boolean z2 = z && g() == aVar.g();
            if (g()) {
                z2 = z2 && this.c == aVar.c;
            }
            boolean z3 = z2 && h() == aVar.h();
            if (h()) {
                z3 = z3 && i().equals(aVar.i());
            }
            boolean z4 = z3 && j() == aVar.j();
            if (j()) {
                z4 = z4 && k().equals(aVar.k());
            }
            boolean z5 = z4 && l() == aVar.l();
            if (l()) {
                return z5 && m().equals(aVar.m());
            }
            return z5;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = a.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + f().hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + this.c;
            }
            if (h()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + i().hashCode();
            }
            if (j()) {
                iHashCode = (((iHashCode * 37) + 4) * 53) + k().hashCode();
            }
            if (l()) {
                iHashCode = (((iHashCode * 37) + 5) * 53) + m().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.e.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static C0148a n() {
            return C0148a.g();
        }

        /* JADX INFO: renamed from: com.tapjoy.internal.gl$a$a, reason: collision with other inner class name */
        public static final class C0148a extends Cdo.a implements b {
            public int b;
            public Object c = "";
            public Object d = "";
            public Object e = "";
            public Object f = "";
            private int g;

            private C0148a() {
            }

            public final a d() {
                a aVar = new a((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                aVar.g = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                aVar.c = this.g;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                aVar.h = this.d;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                aVar.i = this.e;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                aVar.j = this.f;
                aVar.f = i2;
                return aVar;
            }

            public final C0148a a(a aVar) {
                if (aVar != a.d()) {
                    if (aVar.e()) {
                        this.b |= 1;
                        this.c = aVar.g;
                    }
                    if (aVar.g()) {
                        a(aVar.c);
                    }
                    if (aVar.h()) {
                        this.b |= 4;
                        this.d = aVar.h;
                    }
                    if (aVar.j()) {
                        this.b |= 8;
                        this.e = aVar.i;
                    }
                    if (aVar.l()) {
                        this.b |= 16;
                        this.f = aVar.j;
                    }
                    this.a = this.a.a(aVar.e);
                }
                return this;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                return true;
            }

            public final C0148a a(int i) {
                this.b |= 2;
                this.g = i;
                return this;
            }

            public final boolean e() {
                return (this.b & 4) == 4;
            }

            public final C0148a a(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 4;
                this.d = str;
                return this;
            }

            public final C0148a f() {
                this.b &= -5;
                this.d = a.d().i();
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
            public C0148a clone() {
                return new C0148a().a(d());
            }

            static /* synthetic */ C0148a g() {
                return new C0148a();
            }
        }

        public static C0148a a(a aVar) {
            return C0148a.g().a(aVar);
        }

        public final C0148a o() {
            return C0148a.g().a(this);
        }
    }

    public static final class t extends Cdo implements w {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.t.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new t(dlVar, (byte) 0);
            }
        };
        private static final t e;
        public long c;
        long d;
        private final dk f;
        private int g;
        private Object h;
        private byte i;
        private int j;

        /* synthetic */ t(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ t(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private t(Cdo.a aVar) {
            super((byte) 0);
            this.i = (byte) -1;
            this.j = -1;
            this.f = aVar.a;
        }

        private t() {
            this.i = (byte) -1;
            this.j = -1;
            this.f = dk.b;
        }

        public static t d() {
            return e;
        }

        private t(dl dlVar) {
            this.i = (byte) -1;
            this.j = -1;
            k();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                this.g |= 1;
                                this.h = dkVarE;
                                break;
                            case 16:
                                this.g |= 2;
                                this.c = dlVar.g();
                                break;
                            case TimeConstants.HOURSPERDAY /* 24 */:
                                this.g |= 4;
                                this.d = dlVar.g();
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (Throwable th) {
                        try {
                            dmVarA.a();
                        } catch (IOException e2) {
                        } finally {
                            this.f = bVarG.a();
                        }
                        throw th;
                    }
                } catch (dq e3) {
                    e3.a = this;
                    throw e3;
                } catch (IOException e4) {
                    dq dqVar = new dq(e4.getMessage());
                    dqVar.a = this;
                    throw dqVar;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e5) {
            } finally {
                this.f = bVarG.a();
            }
        }

        static {
            t tVar = new t();
            e = tVar;
            tVar.k();
        }

        public final boolean e() {
            return (this.g & 1) == 1;
        }

        public final String f() {
            Object obj = this.h;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.h = strE;
            }
            return strE;
        }

        private dk j() {
            Object obj = this.h;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.h = dkVarA;
            return dkVarA;
        }

        public final boolean g() {
            return (this.g & 2) == 2;
        }

        public final boolean h() {
            return (this.g & 4) == 4;
        }

        private void k() {
            this.h = "";
            this.c = 0L;
            this.d = 0L;
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.i;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            if (!e()) {
                this.i = (byte) 0;
                return false;
            }
            if (!g()) {
                this.i = (byte) 0;
                return false;
            }
            this.i = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.g & 1) == 1) {
                dmVar.a(1, j());
            }
            if ((this.g & 2) == 2) {
                dmVar.a(2, this.c);
            }
            if ((this.g & 4) == 4) {
                dmVar.a(3, this.d);
            }
            dmVar.c(this.f);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.j;
            if (i == -1) {
                int iB = 0;
                if ((this.g & 1) == 1) {
                    iB = dm.b(1, j()) + 0;
                }
                if ((this.g & 2) == 2) {
                    iB += dm.b(2, this.c);
                }
                if ((this.g & 4) == 4) {
                    iB += dm.b(3, this.d);
                }
                int iA = iB + this.f.a();
                this.j = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof t)) {
                return super.equals(obj);
            }
            t tVar = (t) obj;
            boolean z = e() == tVar.e();
            if (e()) {
                z = z && f().equals(tVar.f());
            }
            boolean z2 = z && g() == tVar.g();
            if (g()) {
                z2 = z2 && this.c == tVar.c;
            }
            boolean z3 = z2 && h() == tVar.h();
            if (h()) {
                return z3 && this.d == tVar.d;
            }
            return z3;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = t.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + f().hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + dp.a(this.c);
            }
            if (h()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + dp.a(this.d);
            }
            int iHashCode2 = (iHashCode * 29) + this.f.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a i() {
            return a.e();
        }

        public static final class a extends Cdo.a implements w {
            public int b;
            public Object c = "";
            private long d;
            private long e;

            private a() {
            }

            public final t d() {
                t tVarG = g();
                if (!tVarG.c()) {
                    throw new dz();
                }
                return tVarG;
            }

            private t g() {
                t tVar = new t((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                tVar.h = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                tVar.c = this.d;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                tVar.d = this.e;
                tVar.g = i2;
                return tVar;
            }

            public final a a(t tVar) {
                if (tVar != t.d()) {
                    if (tVar.e()) {
                        this.b |= 1;
                        this.c = tVar.h;
                    }
                    if (tVar.g()) {
                        a(tVar.c);
                    }
                    if (tVar.h()) {
                        b(tVar.d);
                    }
                    this.a = this.a.a(tVar.f);
                }
                return this;
            }

            public final a a(long j) {
                this.b |= 2;
                this.d = j;
                return this;
            }

            public final a b(long j) {
                this.b |= 4;
                this.e = j;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                return new a().a(g());
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                if ((this.b & 1) == 1) {
                    return (this.b & 2) == 2;
                }
                return false;
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }

        public static a a(t tVar) {
            return a.e().a(tVar);
        }
    }

    public static final class u extends Cdo implements v {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.u.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new u(dlVar, dnVar, (byte) 0);
            }
        };
        private static final u d;
        public List c;
        private final dk e;
        private byte f;
        private int g;

        /* synthetic */ u(dl dlVar, dn dnVar, byte b2) {
            this(dlVar, dnVar);
        }

        /* synthetic */ u(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private u(Cdo.a aVar) {
            super((byte) 0);
            this.f = (byte) -1;
            this.g = -1;
            this.e = aVar.a;
        }

        private u() {
            this.f = (byte) -1;
            this.g = -1;
            this.e = dk.b;
        }

        public static u d() {
            return d;
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0053  */
        private u(dl dlVar, dn dnVar) throws Throwable {
            Throwable th;
            boolean z = false;
            this.f = (byte) -1;
            this.g = -1;
            this.c = Collections.emptyList();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        try {
                            int iA = dlVar.a();
                            switch (iA) {
                                case 0:
                                    z2 = true;
                                    break;
                                case 10:
                                    if (!(z & true)) {
                                        this.c = new ArrayList();
                                        z |= true;
                                    }
                                    this.c.add(dlVar.a(t.b, dnVar));
                                    break;
                                default:
                                    if (!dlVar.a(iA, dmVarA)) {
                                        z2 = true;
                                    }
                                    break;
                            }
                        } catch (IOException e) {
                            dq dqVar = new dq(e.getMessage());
                            dqVar.a = this;
                            throw dqVar;
                        }
                    } catch (dq e2) {
                        e2.a = this;
                        throw e2;
                    } catch (Throwable th2) {
                        z2 = z;
                        th = th2;
                        if (z2 & true) {
                            this.c = Collections.unmodifiableList(this.c);
                        }
                        try {
                            dmVarA.a();
                        } catch (IOException e3) {
                        } finally {
                            this.e = bVarG.a();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (z2 & true) {
                        this.c = Collections.unmodifiableList(this.c);
                    }
                    dmVarA.a();
                    throw th;
                }
            }
            if (z & true) {
                this.c = Collections.unmodifiableList(this.c);
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.e = bVarG.a();
            }
        }

        static {
            u uVar = new u();
            d = uVar;
            uVar.c = Collections.emptyList();
        }

        private int f() {
            return this.c.size();
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.f;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            for (int i = 0; i < f(); i++) {
                if (!((t) this.c.get(i)).c()) {
                    this.f = (byte) 0;
                    return false;
                }
            }
            this.f = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.c.size()) {
                    dmVar.a(1, (du) this.c.get(i2));
                    i = i2 + 1;
                } else {
                    dmVar.c(this.e);
                    return;
                }
            }
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.g;
            if (i != -1) {
                return i;
            }
            int iB = 0;
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                iB += dm.b(1, (du) this.c.get(i2));
            }
            int iA = this.e.a() + iB;
            this.g = iA;
            return iA;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof u) {
                return this.c.equals(((u) obj).c);
            }
            return super.equals(obj);
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = u.class.hashCode() + 779;
            if (f() > 0) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + this.c.hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.e.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static u a(byte[] bArr) {
            return (u) b.a(bArr);
        }

        public static a e() {
            return a.f();
        }

        public static final class a extends Cdo.a implements v {
            public List b = Collections.emptyList();
            private int c;

            private a() {
            }

            public final u d() {
                u uVar = new u((Cdo.a) this, (byte) 0);
                if ((this.c & 1) == 1) {
                    this.b = Collections.unmodifiableList(this.b);
                    this.c &= -2;
                }
                uVar.c = this.b;
                return uVar;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                for (int i = 0; i < this.b.size(); i++) {
                    if (!((t) this.b.get(i)).c()) {
                        return false;
                    }
                }
                return true;
            }

            public final void e() {
                if ((this.c & 1) != 1) {
                    this.b = new ArrayList(this.b);
                    this.c |= 1;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
            public a clone() {
                a aVar = new a();
                u uVarD = d();
                if (uVarD != u.d()) {
                    if (!uVarD.c.isEmpty()) {
                        if (aVar.b.isEmpty()) {
                            aVar.b = uVarD.c;
                            aVar.c &= -2;
                        } else {
                            aVar.e();
                            aVar.b.addAll(uVarD.c);
                        }
                    }
                    aVar.a = aVar.a.a(uVarD.e);
                }
                return aVar;
            }

            static /* synthetic */ a f() {
                return new a();
            }
        }
    }

    public static final class z extends Cdo implements aa {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.z.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new z(dlVar, dnVar, (byte) 0);
            }
        };
        private static final z t;
        private Object A;
        private Object B;
        private Object C;
        private Object D;
        private Object E;
        private byte F;
        private int G;
        public long c;
        public int d;
        public int e;
        List f;
        public int g;
        public long h;
        public long i;
        public long j;
        public int k;
        public double l;
        public long m;
        public double n;
        boolean o;
        public int p;
        public int q;
        public ds r;
        boolean s;
        private final dk u;
        private int v;
        private Object w;
        private Object x;
        private Object y;
        private Object z;

        /* synthetic */ z(dl dlVar, dn dnVar, byte b2) {
            this(dlVar, dnVar);
        }

        /* synthetic */ z(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private z(Cdo.a aVar) {
            super((byte) 0);
            this.F = (byte) -1;
            this.G = -1;
            this.u = aVar.a;
        }

        private z() {
            this.F = (byte) -1;
            this.G = -1;
            this.u = dk.b;
        }

        public static z d() {
            return t;
        }

        private z(dl dlVar, dn dnVar) throws Throwable {
            int i;
            Throwable th;
            IOException e;
            dq e2;
            int i2 = 0;
            this.F = (byte) -1;
            this.G = -1;
            X();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    int iA = dlVar.a();
                    switch (iA) {
                        case 0:
                            z = true;
                            break;
                        case 8:
                            this.v |= 1;
                            this.c = dlVar.g();
                            break;
                        case 18:
                            dk dkVarE = dlVar.e();
                            this.v |= 2;
                            this.w = dkVarE;
                            break;
                        case TimeConstants.HOURSPERDAY /* 24 */:
                            this.v |= 512;
                            this.k = dlVar.f();
                            break;
                        case 32:
                            this.v |= 2048;
                            this.m = dlVar.g();
                            break;
                        case 42:
                            dk dkVarE2 = dlVar.e();
                            this.v |= 32768;
                            this.z = dkVarE2;
                            break;
                        case BannerAd.POSITION_TOP /* 48 */:
                            this.v |= 65536;
                            this.p = dlVar.f();
                            break;
                        case 56:
                            this.v |= 131072;
                            this.q = dlVar.f();
                            break;
                        case 66:
                            dk dkVarE3 = dlVar.e();
                            this.v |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
                            this.A = dkVarE3;
                            break;
                        case 74:
                            dk dkVarE4 = dlVar.e();
                            this.v |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                            this.B = dkVarE4;
                            break;
                        case 82:
                            dk dkVarE5 = dlVar.e();
                            this.v |= 1048576;
                            this.C = dkVarE5;
                            break;
                        case AdSize.LARGE_AD_HEIGHT /* 90 */:
                            dk dkVarE6 = dlVar.e();
                            this.v |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                            this.D = dkVarE6;
                            break;
                        case 98:
                            dk dkVarE7 = dlVar.e();
                            this.v |= 4194304;
                            this.E = dkVarE7;
                            break;
                        case LocationRequest.PRIORITY_LOW_POWER /* 104 */:
                            this.v |= 4;
                            this.d = dlVar.f();
                            break;
                        case 112:
                            this.v |= 8;
                            this.e = dlVar.f();
                            break;
                        case 122:
                            if ((i2 & 16) != 16) {
                                this.f = new ArrayList();
                                i = i2 | 16;
                            } else {
                                i = i2;
                            }
                            try {
                                try {
                                    this.f.add(dlVar.a(t.b, dnVar));
                                    i2 = i;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if ((i & 16) == 16) {
                                        this.f = Collections.unmodifiableList(this.f);
                                    }
                                    if ((i & 16777216) == 16777216) {
                                        this.r = this.r.b();
                                    }
                                    try {
                                        dmVarA.a();
                                    } catch (IOException e3) {
                                    } finally {
                                        this.u = bVarG.a();
                                    }
                                    throw th;
                                }
                            } catch (dq e4) {
                                e2 = e4;
                                e2.a = this;
                                throw e2;
                            } catch (IOException e5) {
                                e = e5;
                                dq dqVar = new dq(e.getMessage());
                                dqVar.a = this;
                                throw dqVar;
                            }
                            break;
                        case 128:
                            this.v |= 16;
                            this.g = dlVar.c();
                            break;
                        case 136:
                            this.v |= 32;
                            this.h = dlVar.b();
                            break;
                        case 144:
                            this.v |= 64;
                            this.i = dlVar.b();
                            break;
                        case 152:
                            this.v |= 128;
                            this.j = dlVar.b();
                            break;
                        case 162:
                            dk dkVarE8 = dlVar.e();
                            this.v |= 256;
                            this.x = dkVarE8;
                            break;
                        case 169:
                            this.v |= 1024;
                            this.l = Double.longBitsToDouble(dlVar.h());
                            break;
                        case 177:
                            this.v |= 4096;
                            this.n = Double.longBitsToDouble(dlVar.h());
                            break;
                        case 186:
                            dk dkVarE9 = dlVar.e();
                            this.v |= 8192;
                            this.y = dkVarE9;
                            break;
                        case 192:
                            this.v |= 16384;
                            this.o = dlVar.d();
                            break;
                        case 200:
                            this.v |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                            this.s = dlVar.d();
                            break;
                        case 210:
                            dk dkVarE10 = dlVar.e();
                            if ((i2 & 16777216) != 16777216) {
                                this.r = new dr();
                                i2 |= 16777216;
                            }
                            this.r.a(dkVarE10);
                            break;
                        default:
                            if (!dlVar.a(iA, dmVarA)) {
                                z = true;
                            }
                            break;
                    }
                } catch (dq e6) {
                    e2 = e6;
                } catch (IOException e7) {
                    e = e7;
                } catch (Throwable th3) {
                    i = i2;
                    th = th3;
                }
            }
            if ((i2 & 16) == 16) {
                this.f = Collections.unmodifiableList(this.f);
            }
            if ((i2 & 16777216) == 16777216) {
                this.r = this.r.b();
            }
            try {
                dmVarA.a();
            } catch (IOException e8) {
            } finally {
                this.u = bVarG.a();
            }
        }

        static {
            z zVar = new z();
            t = zVar;
            zVar.X();
        }

        public final boolean e() {
            return (this.v & 1) == 1;
        }

        public final boolean f() {
            return (this.v & 2) == 2;
        }

        public final String g() {
            Object obj = this.w;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.w = strE;
            }
            return strE;
        }

        private dk O() {
            Object obj = this.w;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.w = dkVarA;
            return dkVarA;
        }

        public final boolean h() {
            return (this.v & 4) == 4;
        }

        public final boolean i() {
            return (this.v & 8) == 8;
        }

        public final int j() {
            return this.f.size();
        }

        public final boolean k() {
            return (this.v & 16) == 16;
        }

        public final boolean l() {
            return (this.v & 32) == 32;
        }

        public final boolean m() {
            return (this.v & 64) == 64;
        }

        public final boolean n() {
            return (this.v & 128) == 128;
        }

        public final boolean o() {
            return (this.v & 256) == 256;
        }

        public final String p() {
            Object obj = this.x;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.x = strE;
            }
            return strE;
        }

        private dk P() {
            Object obj = this.x;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.x = dkVarA;
            return dkVarA;
        }

        public final boolean q() {
            return (this.v & 512) == 512;
        }

        public final boolean r() {
            return (this.v & 1024) == 1024;
        }

        public final boolean s() {
            return (this.v & 2048) == 2048;
        }

        public final boolean t() {
            return (this.v & 4096) == 4096;
        }

        public final boolean u() {
            return (this.v & 8192) == 8192;
        }

        public final String v() {
            Object obj = this.y;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.y = strE;
            }
            return strE;
        }

        private dk Q() {
            Object obj = this.y;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.y = dkVarA;
            return dkVarA;
        }

        public final boolean w() {
            return (this.v & 16384) == 16384;
        }

        public final boolean x() {
            return (this.v & 32768) == 32768;
        }

        public final String y() {
            Object obj = this.z;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.z = strE;
            }
            return strE;
        }

        private dk R() {
            Object obj = this.z;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.z = dkVarA;
            return dkVarA;
        }

        public final boolean z() {
            return (this.v & 65536) == 65536;
        }

        public final boolean A() {
            return (this.v & 131072) == 131072;
        }

        public final boolean B() {
            return (this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144;
        }

        public final String C() {
            Object obj = this.A;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.A = strE;
            }
            return strE;
        }

        private dk S() {
            Object obj = this.A;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.A = dkVarA;
            return dkVarA;
        }

        public final boolean D() {
            return (this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288;
        }

        public final String E() {
            Object obj = this.B;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.B = strE;
            }
            return strE;
        }

        private dk T() {
            Object obj = this.B;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.B = dkVarA;
            return dkVarA;
        }

        public final boolean F() {
            return (this.v & 1048576) == 1048576;
        }

        public final String G() {
            Object obj = this.C;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.C = strE;
            }
            return strE;
        }

        private dk U() {
            Object obj = this.C;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.C = dkVarA;
            return dkVarA;
        }

        public final boolean H() {
            return (this.v & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152;
        }

        public final String I() {
            Object obj = this.D;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.D = strE;
            }
            return strE;
        }

        private dk V() {
            Object obj = this.D;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.D = dkVarA;
            return dkVarA;
        }

        public final boolean J() {
            return (this.v & 4194304) == 4194304;
        }

        public final String K() {
            Object obj = this.E;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.E = strE;
            }
            return strE;
        }

        private dk W() {
            Object obj = this.E;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.E = dkVarA;
            return dkVarA;
        }

        public final int L() {
            return this.r.size();
        }

        public final boolean M() {
            return (this.v & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608;
        }

        private void X() {
            this.c = 0L;
            this.w = "";
            this.d = 0;
            this.e = 0;
            this.f = Collections.emptyList();
            this.g = 0;
            this.h = 0L;
            this.i = 0L;
            this.j = 0L;
            this.x = "";
            this.k = 0;
            this.l = 0.0d;
            this.m = 0L;
            this.n = 0.0d;
            this.y = "";
            this.o = false;
            this.z = "";
            this.p = 0;
            this.q = 0;
            this.A = "";
            this.B = "";
            this.C = "";
            this.D = "";
            this.E = "";
            this.r = dr.a;
            this.s = false;
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.F;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            for (int i = 0; i < j(); i++) {
                if (!((t) this.f.get(i)).c()) {
                    this.F = (byte) 0;
                    return false;
                }
            }
            this.F = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.v & 1) == 1) {
                dmVar.a(1, this.c);
            }
            if ((this.v & 2) == 2) {
                dmVar.a(2, O());
            }
            if ((this.v & 512) == 512) {
                dmVar.a(3, this.k);
            }
            if ((this.v & 2048) == 2048) {
                dmVar.a(4, this.m);
            }
            if ((this.v & 32768) == 32768) {
                dmVar.a(5, R());
            }
            if ((this.v & 65536) == 65536) {
                dmVar.a(6, this.p);
            }
            if ((this.v & 131072) == 131072) {
                dmVar.a(7, this.q);
            }
            if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144) {
                dmVar.a(8, S());
            }
            if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288) {
                dmVar.a(9, T());
            }
            if ((this.v & 1048576) == 1048576) {
                dmVar.a(10, U());
            }
            if ((this.v & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152) {
                dmVar.a(11, V());
            }
            if ((this.v & 4194304) == 4194304) {
                dmVar.a(12, W());
            }
            if ((this.v & 4) == 4) {
                dmVar.a(13, this.d);
            }
            if ((this.v & 8) == 8) {
                dmVar.a(14, this.e);
            }
            for (int i = 0; i < this.f.size(); i++) {
                dmVar.a(15, (du) this.f.get(i));
            }
            if ((this.v & 16) == 16) {
                dmVar.a(16, this.g);
            }
            if ((this.v & 32) == 32) {
                dmVar.a(17, this.h);
            }
            if ((this.v & 64) == 64) {
                dmVar.a(18, this.i);
            }
            if ((this.v & 128) == 128) {
                dmVar.a(19, this.j);
            }
            if ((this.v & 256) == 256) {
                dmVar.a(20, P());
            }
            if ((this.v & 1024) == 1024) {
                dmVar.a(21, this.l);
            }
            if ((this.v & 4096) == 4096) {
                dmVar.a(22, this.n);
            }
            if ((this.v & 8192) == 8192) {
                dmVar.a(23, Q());
            }
            if ((this.v & 16384) == 16384) {
                dmVar.a(24, this.o);
            }
            if ((this.v & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608) {
                dmVar.a(25, this.s);
            }
            for (int i2 = 0; i2 < this.r.size(); i2++) {
                dmVar.a(26, this.r.a(i2));
            }
            dmVar.c(this.u);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.G;
            if (i == -1) {
                int iB = (this.v & 1) == 1 ? dm.b(1, this.c) + 0 : 0;
                if ((this.v & 2) == 2) {
                    iB += dm.b(2, O());
                }
                if ((this.v & 512) == 512) {
                    iB += dm.b(3, this.k);
                }
                if ((this.v & 2048) == 2048) {
                    iB += dm.b(4, this.m);
                }
                if ((this.v & 32768) == 32768) {
                    iB += dm.b(5, R());
                }
                if ((this.v & 65536) == 65536) {
                    iB += dm.b(6, this.p);
                }
                if ((this.v & 131072) == 131072) {
                    iB += dm.b(7, this.q);
                }
                if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144) {
                    iB += dm.b(8, S());
                }
                if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288) {
                    iB += dm.b(9, T());
                }
                if ((this.v & 1048576) == 1048576) {
                    iB += dm.b(10, U());
                }
                if ((this.v & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152) {
                    iB += dm.b(11, V());
                }
                if ((this.v & 4194304) == 4194304) {
                    iB += dm.b(12, W());
                }
                if ((this.v & 4) == 4) {
                    iB += dm.b(13, this.d);
                }
                if ((this.v & 8) == 8) {
                    iB += dm.b(14, this.e);
                }
                int iD = iB;
                for (int i2 = 0; i2 < this.f.size(); i2++) {
                    iD += dm.b(15, (du) this.f.get(i2));
                }
                if ((this.v & 16) == 16) {
                    iD += dm.b(16, this.g);
                }
                if ((this.v & 32) == 32) {
                    iD += dm.b(17, this.h);
                }
                if ((this.v & 64) == 64) {
                    iD += dm.b(18, this.i);
                }
                if ((this.v & 128) == 128) {
                    iD += dm.b(19, this.j);
                }
                if ((this.v & 256) == 256) {
                    iD += dm.b(20, P());
                }
                if ((this.v & 1024) == 1024) {
                    iD += dm.d(21) + 8;
                }
                if ((this.v & 4096) == 4096) {
                    iD += dm.d(22) + 8;
                }
                if ((this.v & 8192) == 8192) {
                    iD += dm.b(23, Q());
                }
                if ((this.v & 16384) == 16384) {
                    iD += dm.d(24) + 1;
                }
                if ((this.v & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608) {
                    iD += dm.d(25) + 1;
                }
                int iB2 = 0;
                for (int i3 = 0; i3 < this.r.size(); i3++) {
                    iB2 += dm.b(this.r.a(i3));
                }
                int size = iB2 + iD + (this.r.size() * 2) + this.u.a();
                this.G = size;
                return size;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof z)) {
                return super.equals(obj);
            }
            z zVar = (z) obj;
            boolean z = e() == zVar.e();
            if (e()) {
                z = z && this.c == zVar.c;
            }
            boolean z2 = z && f() == zVar.f();
            if (f()) {
                z2 = z2 && g().equals(zVar.g());
            }
            boolean z3 = z2 && h() == zVar.h();
            if (h()) {
                z3 = z3 && this.d == zVar.d;
            }
            boolean z4 = z3 && i() == zVar.i();
            if (i()) {
                z4 = z4 && this.e == zVar.e;
            }
            boolean z5 = (z4 && this.f.equals(zVar.f)) && k() == zVar.k();
            if (k()) {
                z5 = z5 && this.g == zVar.g;
            }
            boolean z6 = z5 && l() == zVar.l();
            if (l()) {
                z6 = z6 && this.h == zVar.h;
            }
            boolean z7 = z6 && m() == zVar.m();
            if (m()) {
                z7 = z7 && this.i == zVar.i;
            }
            boolean z8 = z7 && n() == zVar.n();
            if (n()) {
                z8 = z8 && this.j == zVar.j;
            }
            boolean z9 = z8 && o() == zVar.o();
            if (o()) {
                z9 = z9 && p().equals(zVar.p());
            }
            boolean z10 = z9 && q() == zVar.q();
            if (q()) {
                z10 = z10 && this.k == zVar.k;
            }
            boolean z11 = z10 && r() == zVar.r();
            if (r()) {
                z11 = z11 && Double.doubleToLongBits(this.l) == Double.doubleToLongBits(zVar.l);
            }
            boolean z12 = z11 && s() == zVar.s();
            if (s()) {
                z12 = z12 && this.m == zVar.m;
            }
            boolean z13 = z12 && t() == zVar.t();
            if (t()) {
                z13 = z13 && Double.doubleToLongBits(this.n) == Double.doubleToLongBits(zVar.n);
            }
            boolean z14 = z13 && u() == zVar.u();
            if (u()) {
                z14 = z14 && v().equals(zVar.v());
            }
            boolean z15 = z14 && w() == zVar.w();
            if (w()) {
                z15 = z15 && this.o == zVar.o;
            }
            boolean z16 = z15 && x() == zVar.x();
            if (x()) {
                z16 = z16 && y().equals(zVar.y());
            }
            boolean z17 = z16 && z() == zVar.z();
            if (z()) {
                z17 = z17 && this.p == zVar.p;
            }
            boolean z18 = z17 && A() == zVar.A();
            if (A()) {
                z18 = z18 && this.q == zVar.q;
            }
            boolean z19 = z18 && B() == zVar.B();
            if (B()) {
                z19 = z19 && C().equals(zVar.C());
            }
            boolean z20 = z19 && D() == zVar.D();
            if (D()) {
                z20 = z20 && E().equals(zVar.E());
            }
            boolean z21 = z20 && F() == zVar.F();
            if (F()) {
                z21 = z21 && G().equals(zVar.G());
            }
            boolean z22 = z21 && H() == zVar.H();
            if (H()) {
                z22 = z22 && I().equals(zVar.I());
            }
            boolean z23 = z22 && J() == zVar.J();
            if (J()) {
                z23 = z23 && K().equals(zVar.K());
            }
            boolean z24 = (z23 && this.r.equals(zVar.r)) && M() == zVar.M();
            if (M()) {
                return z24 && this.s == zVar.s;
            }
            return z24;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = z.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + dp.a(this.c);
            }
            if (f()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + g().hashCode();
            }
            if (h()) {
                iHashCode = (((iHashCode * 37) + 13) * 53) + this.d;
            }
            if (i()) {
                iHashCode = (((iHashCode * 37) + 14) * 53) + this.e;
            }
            if (j() > 0) {
                iHashCode = (((iHashCode * 37) + 15) * 53) + this.f.hashCode();
            }
            if (k()) {
                iHashCode = (((iHashCode * 37) + 16) * 53) + this.g;
            }
            if (l()) {
                iHashCode = (((iHashCode * 37) + 17) * 53) + dp.a(this.h);
            }
            if (m()) {
                iHashCode = (((iHashCode * 37) + 18) * 53) + dp.a(this.i);
            }
            if (n()) {
                iHashCode = (((iHashCode * 37) + 19) * 53) + dp.a(this.j);
            }
            if (o()) {
                iHashCode = (((iHashCode * 37) + 20) * 53) + p().hashCode();
            }
            if (q()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + this.k;
            }
            if (r()) {
                iHashCode = (((iHashCode * 37) + 21) * 53) + dp.a(Double.doubleToLongBits(this.l));
            }
            if (s()) {
                iHashCode = (((iHashCode * 37) + 4) * 53) + dp.a(this.m);
            }
            if (t()) {
                iHashCode = (((iHashCode * 37) + 22) * 53) + dp.a(Double.doubleToLongBits(this.n));
            }
            if (u()) {
                iHashCode = (((iHashCode * 37) + 23) * 53) + v().hashCode();
            }
            if (w()) {
                iHashCode = (((iHashCode * 37) + 24) * 53) + dp.a(this.o);
            }
            if (x()) {
                iHashCode = (((iHashCode * 37) + 5) * 53) + y().hashCode();
            }
            if (z()) {
                iHashCode = (((iHashCode * 37) + 6) * 53) + this.p;
            }
            if (A()) {
                iHashCode = (((iHashCode * 37) + 7) * 53) + this.q;
            }
            if (B()) {
                iHashCode = (((iHashCode * 37) + 8) * 53) + C().hashCode();
            }
            if (D()) {
                iHashCode = (((iHashCode * 37) + 9) * 53) + E().hashCode();
            }
            if (F()) {
                iHashCode = (((iHashCode * 37) + 10) * 53) + G().hashCode();
            }
            if (H()) {
                iHashCode = (((iHashCode * 37) + 11) * 53) + I().hashCode();
            }
            if (J()) {
                iHashCode = (((iHashCode * 37) + 12) * 53) + K().hashCode();
            }
            if (L() > 0) {
                iHashCode = (((iHashCode * 37) + 26) * 53) + this.r.hashCode();
            }
            if (M()) {
                iHashCode = (((iHashCode * 37) + 25) * 53) + dp.a(this.s);
            }
            int iHashCode2 = (iHashCode * 29) + this.u.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a N() {
            return a.z();
        }

        public static final class a extends Cdo.a implements aa {
            private boolean B;
            public int b;
            public long d;
            public double e;
            public int g;
            public int h;
            public boolean o;
            private long p;
            private int r;
            private int s;
            private int t;
            private long u;
            private long v;
            private long w;
            private int y;
            private double z;
            private Object q = "";
            public List c = Collections.emptyList();
            private Object x = "";
            private Object A = "";
            public Object f = "";
            public Object i = "";
            public Object j = "";
            public Object k = "";
            public Object l = "";
            public Object m = "";
            public ds n = dr.a;

            private a() {
            }

            public final z d() {
                z zVar = new z((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = 0;
                if ((i & 1) == 1) {
                    i2 = 1;
                }
                zVar.c = this.p;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                zVar.w = this.q;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                zVar.d = this.r;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                zVar.e = this.s;
                if ((this.b & 16) == 16) {
                    this.c = Collections.unmodifiableList(this.c);
                    this.b &= -17;
                }
                zVar.f = this.c;
                if ((i & 32) == 32) {
                    i2 |= 16;
                }
                zVar.g = this.t;
                if ((i & 64) == 64) {
                    i2 |= 32;
                }
                zVar.h = this.u;
                if ((i & 128) == 128) {
                    i2 |= 64;
                }
                zVar.i = this.v;
                if ((i & 256) == 256) {
                    i2 |= 128;
                }
                zVar.j = this.w;
                if ((i & 512) == 512) {
                    i2 |= 256;
                }
                zVar.x = this.x;
                if ((i & 1024) == 1024) {
                    i2 |= 512;
                }
                zVar.k = this.y;
                if ((i & 2048) == 2048) {
                    i2 |= 1024;
                }
                zVar.l = this.z;
                if ((i & 4096) == 4096) {
                    i2 |= 2048;
                }
                zVar.m = this.d;
                if ((i & 8192) == 8192) {
                    i2 |= 4096;
                }
                zVar.n = this.e;
                if ((i & 16384) == 16384) {
                    i2 |= 8192;
                }
                zVar.y = this.A;
                if ((i & 32768) == 32768) {
                    i2 |= 16384;
                }
                zVar.o = this.B;
                if ((i & 65536) == 65536) {
                    i2 |= 32768;
                }
                zVar.z = this.f;
                if ((i & 131072) == 131072) {
                    i2 |= 65536;
                }
                zVar.p = this.g;
                if ((i & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144) {
                    i2 |= 131072;
                }
                zVar.q = this.h;
                if ((i & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288) {
                    i2 |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
                }
                zVar.A = this.i;
                if ((1048576 & i) == 1048576) {
                    i2 |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                }
                zVar.B = this.j;
                if ((2097152 & i) == 2097152) {
                    i2 |= 1048576;
                }
                zVar.C = this.k;
                if ((4194304 & i) == 4194304) {
                    i2 |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                }
                zVar.D = this.l;
                if ((8388608 & i) == 8388608) {
                    i2 |= 4194304;
                }
                zVar.E = this.m;
                if ((this.b & 16777216) == 16777216) {
                    this.n = this.n.b();
                    this.b &= -16777217;
                }
                zVar.r = this.n;
                if ((i & 33554432) == 33554432) {
                    i2 |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                }
                zVar.s = this.o;
                zVar.v = i2;
                return zVar;
            }

            public final a a(z zVar) {
                if (zVar != z.d()) {
                    if (zVar.e()) {
                        a(zVar.c);
                    }
                    if (zVar.f()) {
                        this.b |= 2;
                        this.q = zVar.w;
                    }
                    if (zVar.h()) {
                        a(zVar.d);
                    }
                    if (zVar.i()) {
                        b(zVar.e);
                    }
                    if (!zVar.f.isEmpty()) {
                        if (this.c.isEmpty()) {
                            this.c = zVar.f;
                            this.b &= -17;
                        } else {
                            e();
                            this.c.addAll(zVar.f);
                        }
                    }
                    if (zVar.k()) {
                        d(zVar.g);
                    }
                    if (zVar.l()) {
                        b(zVar.h);
                    }
                    if (zVar.m()) {
                        c(zVar.i);
                    }
                    if (zVar.n()) {
                        d(zVar.j);
                    }
                    if (zVar.o()) {
                        this.b |= 512;
                        this.x = zVar.x;
                    }
                    if (zVar.q()) {
                        e(zVar.k);
                    }
                    if (zVar.r()) {
                        a(zVar.l);
                    }
                    if (zVar.s()) {
                        e(zVar.m);
                    }
                    if (zVar.t()) {
                        b(zVar.n);
                    }
                    if (zVar.u()) {
                        this.b |= 16384;
                        this.A = zVar.y;
                    }
                    if (zVar.w()) {
                        a(zVar.o);
                    }
                    if (zVar.x()) {
                        this.b |= 65536;
                        this.f = zVar.z;
                    }
                    if (zVar.z()) {
                        f(zVar.p);
                    }
                    if (zVar.A()) {
                        g(zVar.q);
                    }
                    if (zVar.B()) {
                        this.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                        this.i = zVar.A;
                    }
                    if (zVar.D()) {
                        this.b |= 1048576;
                        this.j = zVar.B;
                    }
                    if (zVar.F()) {
                        this.b |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                        this.k = zVar.C;
                    }
                    if (zVar.H()) {
                        this.b |= 4194304;
                        this.l = zVar.D;
                    }
                    if (zVar.J()) {
                        this.b |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                        this.m = zVar.E;
                    }
                    if (!zVar.r.isEmpty()) {
                        if (this.n.isEmpty()) {
                            this.n = zVar.r;
                            this.b &= -16777217;
                        } else {
                            B();
                            this.n.addAll(zVar.r);
                        }
                    }
                    if (zVar.M()) {
                        b(zVar.s);
                    }
                    this.a = this.a.a(zVar.u);
                }
                return this;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                for (int i = 0; i < f(); i++) {
                    if (!c(i).c()) {
                        return false;
                    }
                }
                return true;
            }

            public final a a(long j) {
                this.b |= 1;
                this.p = j;
                return this;
            }

            public final a a(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 2;
                this.q = str;
                return this;
            }

            public final a a(int i) {
                this.b |= 4;
                this.r = i;
                return this;
            }

            public final a b(int i) {
                this.b |= 8;
                this.s = i;
                return this;
            }

            public final void e() {
                if ((this.b & 16) != 16) {
                    this.c = new ArrayList(this.c);
                    this.b |= 16;
                }
            }

            public final int f() {
                return this.c.size();
            }

            public final t c(int i) {
                return (t) this.c.get(i);
            }

            public final a d(int i) {
                this.b |= 32;
                this.t = i;
                return this;
            }

            public final a b(long j) {
                this.b |= 64;
                this.u = j;
                return this;
            }

            public final a c(long j) {
                this.b |= 128;
                this.v = j;
                return this;
            }

            public final a d(long j) {
                this.b |= 256;
                this.w = j;
                return this;
            }

            public final a b(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 512;
                this.x = str;
                return this;
            }

            public final a e(int i) {
                this.b |= 1024;
                this.y = i;
                return this;
            }

            public final a a(double d) {
                this.b |= 2048;
                this.z = d;
                return this;
            }

            public final a e(long j) {
                this.b |= 4096;
                this.d = j;
                return this;
            }

            public final a b(double d) {
                this.b |= 8192;
                this.e = d;
                return this;
            }

            public final a c(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 16384;
                this.A = str;
                return this;
            }

            public final a g() {
                this.b &= -16385;
                this.A = z.d().v();
                return this;
            }

            public final a a(boolean z) {
                this.b |= 32768;
                this.B = z;
                return this;
            }

            public final a h() {
                this.b &= -32769;
                this.B = false;
                return this;
            }

            public final boolean i() {
                return (this.b & 65536) == 65536;
            }

            public final a d(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 65536;
                this.f = str;
                return this;
            }

            public final a j() {
                this.b &= -65537;
                this.f = z.d().y();
                return this;
            }

            public final boolean k() {
                return (this.b & 131072) == 131072;
            }

            public final a f(int i) {
                this.b |= 131072;
                this.g = i;
                return this;
            }

            public final a l() {
                this.b &= -131073;
                this.g = 0;
                return this;
            }

            public final boolean m() {
                return (this.b & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144;
            }

            public final a g(int i) {
                this.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
                this.h = i;
                return this;
            }

            public final a n() {
                this.b &= -262145;
                this.h = 0;
                return this;
            }

            public final boolean o() {
                return (this.b & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288;
            }

            public final a e(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                this.i = str;
                return this;
            }

            public final a p() {
                this.b &= -524289;
                this.i = z.d().C();
                return this;
            }

            public final boolean q() {
                return (this.b & 1048576) == 1048576;
            }

            public final a f(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 1048576;
                this.j = str;
                return this;
            }

            public final a r() {
                this.b &= -1048577;
                this.j = z.d().E();
                return this;
            }

            public final boolean s() {
                return (this.b & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152;
            }

            public final a g(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                this.k = str;
                return this;
            }

            public final a t() {
                this.b &= -2097153;
                this.k = z.d().G();
                return this;
            }

            public final boolean u() {
                return (this.b & 4194304) == 4194304;
            }

            public final a h(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 4194304;
                this.l = str;
                return this;
            }

            public final a v() {
                this.b &= -4194305;
                this.l = z.d().I();
                return this;
            }

            public final boolean w() {
                return (this.b & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608;
            }

            public final a i(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                this.m = str;
                return this;
            }

            public final a x() {
                this.b &= -8388609;
                this.m = z.d().K();
                return this;
            }

            private void B() {
                if ((this.b & 16777216) != 16777216) {
                    this.n = new dr(this.n);
                    this.b |= 16777216;
                }
            }

            public final a a(Iterable iterable) {
                B();
                dh.a.a(iterable, this.n);
                return this;
            }

            public final a y() {
                this.n = dr.a;
                this.b &= -16777217;
                return this;
            }

            public final a b(boolean z) {
                this.b |= 33554432;
                this.o = z;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: A, reason: merged with bridge method [inline-methods] */
            public a clone() {
                return new a().a(d());
            }

            static /* synthetic */ a z() {
                return new a();
            }
        }

        public static a a(z zVar) {
            return a.z().a(zVar);
        }
    }

    public static final class x extends Cdo implements y {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.x.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new x(dlVar, (byte) 0);
            }
        };
        private static final x d;
        public ds c;
        private final dk e;
        private byte f;
        private int g;

        /* synthetic */ x(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ x(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private x(Cdo.a aVar) {
            super((byte) 0);
            this.f = (byte) -1;
            this.g = -1;
            this.e = aVar.a;
        }

        private x() {
            this.f = (byte) -1;
            this.g = -1;
            this.e = dk.b;
        }

        public static x d() {
            return d;
        }

        private x(dl dlVar) throws Throwable {
            boolean z = false;
            this.f = (byte) -1;
            this.g = -1;
            this.c = dr.a;
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z2 = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                if (!(z & true)) {
                                    this.c = new dr();
                                    z |= true;
                                }
                                this.c.a(dkVarE);
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z2 = true;
                                }
                                break;
                        }
                    } catch (dq e) {
                        e.a = this;
                        throw e;
                    } catch (IOException e2) {
                        dq dqVar = new dq(e2.getMessage());
                        dqVar.a = this;
                        throw dqVar;
                    } catch (Throwable th) {
                        z2 = z;
                        th = th;
                        if (z2 & true) {
                            this.c = this.c.b();
                        }
                        try {
                            dmVarA.a();
                        } catch (IOException e3) {
                        } finally {
                            this.e = bVarG.a();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            if (z & true) {
                this.c = this.c.b();
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.e = bVarG.a();
            }
        }

        static {
            x xVar = new x();
            d = xVar;
            xVar.c = dr.a;
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.f;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.f = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            for (int i = 0; i < this.c.size(); i++) {
                dmVar.a(1, this.c.a(i));
            }
            dmVar.c(this.e);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.g;
            if (i != -1) {
                return i;
            }
            int iB = 0;
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                iB += dm.b(this.c.a(i2));
            }
            int size = iB + 0 + (this.c.size() * 1) + this.e.a();
            this.g = size;
            return size;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof x) {
                return this.c.equals(((x) obj).c);
            }
            return super.equals(obj);
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = x.class.hashCode() + 779;
            if (this.c.size() > 0) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + this.c.hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.e.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static x a(byte[] bArr) {
            return (x) b.a(bArr);
        }

        public static a e() {
            return a.f();
        }

        public static final class a extends Cdo.a implements y {
            public ds b = dr.a;
            private int c;

            private a() {
            }

            public final x d() {
                x xVar = new x((Cdo.a) this, (byte) 0);
                if ((this.c & 1) == 1) {
                    this.b = this.b.b();
                    this.c &= -2;
                }
                xVar.c = this.b;
                return xVar;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                return true;
            }

            public final void e() {
                if ((this.c & 1) != 1) {
                    this.b = new dr(this.b);
                    this.c |= 1;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
            public a clone() {
                a aVar = new a();
                x xVarD = d();
                if (xVarD != x.d()) {
                    if (!xVarD.c.isEmpty()) {
                        if (aVar.b.isEmpty()) {
                            aVar.b = xVarD.c;
                            aVar.c &= -2;
                        } else {
                            aVar.e();
                            aVar.b.addAll(xVarD.c);
                        }
                    }
                    aVar.a = aVar.a.a(xVarD.e);
                }
                return aVar;
            }

            static /* synthetic */ a f() {
                return new a();
            }
        }
    }

    public static final class n extends Cdo implements o {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.n.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new n(dlVar, dnVar, (byte) 0);
            }
        };
        private static final n f;
        public l c;
        public a d;
        public z e;
        private final dk g;
        private int h;
        private byte i;
        private int j;

        /* synthetic */ n(dl dlVar, dn dnVar, byte b2) {
            this(dlVar, dnVar);
        }

        /* synthetic */ n(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private n(Cdo.a aVar) {
            super((byte) 0);
            this.i = (byte) -1;
            this.j = -1;
            this.g = aVar.a;
        }

        private n() {
            this.i = (byte) -1;
            this.j = -1;
            this.g = dk.b;
        }

        public static n d() {
            return f;
        }

        private n(dl dlVar, dn dnVar) {
            this.i = (byte) -1;
            this.j = -1;
            i();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                l.a aVarA = (this.h & 1) == 1 ? l.a.e().a(this.c) : null;
                                this.c = (l) dlVar.a(l.b, dnVar);
                                if (aVarA != null) {
                                    aVarA.a(this.c);
                                    this.c = aVarA.d();
                                }
                                this.h |= 1;
                                break;
                            case 18:
                                a.C0148a c0148aO = (this.h & 2) == 2 ? this.d.o() : null;
                                this.d = (a) dlVar.a(a.b, dnVar);
                                if (c0148aO != null) {
                                    c0148aO.a(this.d);
                                    this.d = c0148aO.d();
                                }
                                this.h |= 2;
                                break;
                            case 26:
                                z.a aVarA2 = (this.h & 4) == 4 ? z.a(this.e) : null;
                                this.e = (z) dlVar.a(z.b, dnVar);
                                if (aVarA2 != null) {
                                    aVarA2.a(this.e);
                                    this.e = aVarA2.d();
                                }
                                this.h |= 4;
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (Throwable th) {
                        try {
                            dmVarA.a();
                        } catch (IOException e) {
                        } finally {
                            this.g = bVarG.a();
                        }
                        throw th;
                    }
                } catch (dq e2) {
                    e2.a = this;
                    throw e2;
                } catch (IOException e3) {
                    dq dqVar = new dq(e3.getMessage());
                    dqVar.a = this;
                    throw dqVar;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.g = bVarG.a();
            }
        }

        static {
            n nVar = new n();
            f = nVar;
            nVar.i();
        }

        public final boolean e() {
            return (this.h & 1) == 1;
        }

        public final boolean f() {
            return (this.h & 2) == 2;
        }

        public final boolean g() {
            return (this.h & 4) == 4;
        }

        private void i() {
            this.c = l.d();
            this.d = a.d();
            this.e = z.d();
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.i;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            if (g() && !this.e.c()) {
                this.i = (byte) 0;
                return false;
            }
            this.i = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.h & 1) == 1) {
                dmVar.a(1, this.c);
            }
            if ((this.h & 2) == 2) {
                dmVar.a(2, this.d);
            }
            if ((this.h & 4) == 4) {
                dmVar.a(3, this.e);
            }
            dmVar.c(this.g);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.j;
            if (i == -1) {
                int iB = 0;
                if ((this.h & 1) == 1) {
                    iB = dm.b(1, this.c) + 0;
                }
                if ((this.h & 2) == 2) {
                    iB += dm.b(2, this.d);
                }
                if ((this.h & 4) == 4) {
                    iB += dm.b(3, this.e);
                }
                int iA = iB + this.g.a();
                this.j = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof n)) {
                return super.equals(obj);
            }
            n nVar = (n) obj;
            boolean z = e() == nVar.e();
            if (e()) {
                z = z && this.c.equals(nVar.c);
            }
            boolean z2 = z && f() == nVar.f();
            if (f()) {
                z2 = z2 && this.d.equals(nVar.d);
            }
            boolean z3 = z2 && g() == nVar.g();
            if (g()) {
                return z3 && this.e.equals(nVar.e);
            }
            return z3;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = n.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + this.c.hashCode();
            }
            if (f()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + this.d.hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + this.e.hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.g.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a h() {
            return a.e();
        }

        public static final class a extends Cdo.a implements o {
            public int b;
            public l c = l.d();
            public a d = a.d();
            public z e = z.d();

            private a() {
            }

            public final n d() {
                n nVar = new n((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                nVar.c = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                nVar.d = this.d;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                nVar.e = this.e;
                nVar.h = i2;
                return nVar;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                a aVar = new a();
                n nVarD = d();
                if (nVarD != n.d()) {
                    if (nVarD.e()) {
                        l lVar = nVarD.c;
                        if ((aVar.b & 1) == 1 && aVar.c != l.d()) {
                            aVar.c = l.a(aVar.c).a(lVar).d();
                        } else {
                            aVar.c = lVar;
                        }
                        aVar.b |= 1;
                    }
                    if (nVarD.f()) {
                        a aVar2 = nVarD.d;
                        if ((aVar.b & 2) == 2 && aVar.d != a.d()) {
                            aVar.d = a.a(aVar.d).a(aVar2).d();
                        } else {
                            aVar.d = aVar2;
                        }
                        aVar.b |= 2;
                    }
                    if (nVarD.g()) {
                        z zVar = nVarD.e;
                        if ((aVar.b & 4) == 4 && aVar.e != z.d()) {
                            aVar.e = z.a(aVar.e).a(zVar).d();
                        } else {
                            aVar.e = zVar;
                        }
                        aVar.b |= 4;
                    }
                    aVar.a = aVar.a.a(nVarD.g);
                }
                return aVar;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                return !((this.b & 4) == 4) || this.e.c();
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }
    }

    public static final class r extends Cdo implements s {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.r.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new r(dlVar, (byte) 0);
            }
        };
        private static final r g;
        int c;
        double d;
        int e;
        long f;
        private final dk h;
        private int i;
        private Object j;
        private Object k;
        private Object l;
        private Object m;
        private Object n;
        private Object o;
        private Object p;
        private Object q;
        private Object r;
        private Object s;
        private byte t;
        private int u;

        /* synthetic */ r(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ r(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private r(Cdo.a aVar) {
            super((byte) 0);
            this.t = (byte) -1;
            this.u = -1;
            this.h = aVar.a;
        }

        private r() {
            this.t = (byte) -1;
            this.u = -1;
            this.h = dk.b;
        }

        public static r d() {
            return g;
        }

        private r(dl dlVar) {
            this.t = (byte) -1;
            this.u = -1;
            N();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                this.i |= 1;
                                this.j = dkVarE;
                                break;
                            case 16:
                                this.i |= 2;
                                this.c = dlVar.f();
                                break;
                            case 25:
                                this.i |= 4;
                                this.d = Double.longBitsToDouble(dlVar.h());
                                break;
                            case 34:
                                dk dkVarE2 = dlVar.e();
                                this.i |= 8;
                                this.k = dkVarE2;
                                break;
                            case 42:
                                dk dkVarE3 = dlVar.e();
                                this.i |= 16;
                                this.l = dkVarE3;
                                break;
                            case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                                dk dkVarE4 = dlVar.e();
                                this.i |= 32;
                                this.m = dkVarE4;
                                break;
                            case 58:
                                dk dkVarE5 = dlVar.e();
                                this.i |= 64;
                                this.n = dkVarE5;
                                break;
                            case 66:
                                dk dkVarE6 = dlVar.e();
                                this.i |= 128;
                                this.o = dkVarE6;
                                break;
                            case 72:
                                this.i |= 256;
                                this.e = dlVar.f();
                                break;
                            case BannerAd.POSITION_BOTTOM /* 80 */:
                                this.i |= 512;
                                this.f = dlVar.g();
                                break;
                            case AdSize.LARGE_AD_HEIGHT /* 90 */:
                                dk dkVarE7 = dlVar.e();
                                this.i |= 1024;
                                this.p = dkVarE7;
                                break;
                            case 98:
                                dk dkVarE8 = dlVar.e();
                                this.i |= 2048;
                                this.q = dkVarE8;
                                break;
                            case 106:
                                dk dkVarE9 = dlVar.e();
                                this.i |= 4096;
                                this.r = dkVarE9;
                                break;
                            case 114:
                                dk dkVarE10 = dlVar.e();
                                this.i |= 8192;
                                this.s = dkVarE10;
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (dq e) {
                        e.a = this;
                        throw e;
                    } catch (IOException e2) {
                        dq dqVar = new dq(e2.getMessage());
                        dqVar.a = this;
                        throw dqVar;
                    }
                } catch (Throwable th) {
                    try {
                        dmVarA.a();
                    } catch (IOException e3) {
                    } finally {
                        this.h = bVarG.a();
                    }
                    throw th;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.h = bVarG.a();
            }
        }

        static {
            r rVar = new r();
            g = rVar;
            rVar.N();
        }

        public final boolean e() {
            return (this.i & 1) == 1;
        }

        public final String f() {
            Object obj = this.j;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.j = strE;
            }
            return strE;
        }

        private dk D() {
            Object obj = this.j;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.j = dkVarA;
            return dkVarA;
        }

        public final boolean g() {
            return (this.i & 2) == 2;
        }

        public final boolean h() {
            return (this.i & 4) == 4;
        }

        public final boolean i() {
            return (this.i & 8) == 8;
        }

        public final String j() {
            Object obj = this.k;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.k = strE;
            }
            return strE;
        }

        private dk E() {
            Object obj = this.k;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.k = dkVarA;
            return dkVarA;
        }

        public final boolean k() {
            return (this.i & 16) == 16;
        }

        public final String l() {
            Object obj = this.l;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.l = strE;
            }
            return strE;
        }

        private dk F() {
            Object obj = this.l;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.l = dkVarA;
            return dkVarA;
        }

        public final boolean m() {
            return (this.i & 32) == 32;
        }

        public final String n() {
            Object obj = this.m;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.m = strE;
            }
            return strE;
        }

        private dk G() {
            Object obj = this.m;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.m = dkVarA;
            return dkVarA;
        }

        public final boolean o() {
            return (this.i & 64) == 64;
        }

        public final String p() {
            Object obj = this.n;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.n = strE;
            }
            return strE;
        }

        private dk H() {
            Object obj = this.n;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.n = dkVarA;
            return dkVarA;
        }

        public final boolean q() {
            return (this.i & 128) == 128;
        }

        public final String r() {
            Object obj = this.o;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.o = strE;
            }
            return strE;
        }

        private dk I() {
            Object obj = this.o;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.o = dkVarA;
            return dkVarA;
        }

        public final boolean s() {
            return (this.i & 256) == 256;
        }

        public final boolean t() {
            return (this.i & 512) == 512;
        }

        public final boolean u() {
            return (this.i & 1024) == 1024;
        }

        public final String v() {
            Object obj = this.p;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.p = strE;
            }
            return strE;
        }

        private dk J() {
            Object obj = this.p;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.p = dkVarA;
            return dkVarA;
        }

        public final boolean w() {
            return (this.i & 2048) == 2048;
        }

        public final String x() {
            Object obj = this.q;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.q = strE;
            }
            return strE;
        }

        private dk K() {
            Object obj = this.q;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.q = dkVarA;
            return dkVarA;
        }

        public final boolean y() {
            return (this.i & 4096) == 4096;
        }

        public final String z() {
            Object obj = this.r;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.r = strE;
            }
            return strE;
        }

        private dk L() {
            Object obj = this.r;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.r = dkVarA;
            return dkVarA;
        }

        public final boolean A() {
            return (this.i & 8192) == 8192;
        }

        public final String B() {
            Object obj = this.s;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.s = strE;
            }
            return strE;
        }

        private dk M() {
            Object obj = this.s;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.s = dkVarA;
            return dkVarA;
        }

        private void N() {
            this.j = "";
            this.c = 1;
            this.d = 0.0d;
            this.k = "";
            this.l = "";
            this.m = "";
            this.n = "";
            this.o = "";
            this.e = 0;
            this.f = 0L;
            this.p = "";
            this.q = "";
            this.r = "";
            this.s = "";
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.t;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            if (!e()) {
                this.t = (byte) 0;
                return false;
            }
            this.t = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.i & 1) == 1) {
                dmVar.a(1, D());
            }
            if ((this.i & 2) == 2) {
                dmVar.a(2, this.c);
            }
            if ((this.i & 4) == 4) {
                dmVar.a(3, this.d);
            }
            if ((this.i & 8) == 8) {
                dmVar.a(4, E());
            }
            if ((this.i & 16) == 16) {
                dmVar.a(5, F());
            }
            if ((this.i & 32) == 32) {
                dmVar.a(6, G());
            }
            if ((this.i & 64) == 64) {
                dmVar.a(7, H());
            }
            if ((this.i & 128) == 128) {
                dmVar.a(8, I());
            }
            if ((this.i & 256) == 256) {
                dmVar.a(9, this.e);
            }
            if ((this.i & 512) == 512) {
                dmVar.a(10, this.f);
            }
            if ((this.i & 1024) == 1024) {
                dmVar.a(11, J());
            }
            if ((this.i & 2048) == 2048) {
                dmVar.a(12, K());
            }
            if ((this.i & 4096) == 4096) {
                dmVar.a(13, L());
            }
            if ((this.i & 8192) == 8192) {
                dmVar.a(14, M());
            }
            dmVar.c(this.h);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.u;
            if (i == -1) {
                int iB = 0;
                if ((this.i & 1) == 1) {
                    iB = dm.b(1, D()) + 0;
                }
                if ((this.i & 2) == 2) {
                    iB += dm.b(2, this.c);
                }
                if ((this.i & 4) == 4) {
                    iB += dm.d(3) + 8;
                }
                if ((this.i & 8) == 8) {
                    iB += dm.b(4, E());
                }
                if ((this.i & 16) == 16) {
                    iB += dm.b(5, F());
                }
                if ((this.i & 32) == 32) {
                    iB += dm.b(6, G());
                }
                if ((this.i & 64) == 64) {
                    iB += dm.b(7, H());
                }
                if ((this.i & 128) == 128) {
                    iB += dm.b(8, I());
                }
                if ((this.i & 256) == 256) {
                    iB += dm.b(9, this.e);
                }
                if ((this.i & 512) == 512) {
                    iB += dm.b(10, this.f);
                }
                if ((this.i & 1024) == 1024) {
                    iB += dm.b(11, J());
                }
                if ((this.i & 2048) == 2048) {
                    iB += dm.b(12, K());
                }
                if ((this.i & 4096) == 4096) {
                    iB += dm.b(13, L());
                }
                if ((this.i & 8192) == 8192) {
                    iB += dm.b(14, M());
                }
                int iA = iB + this.h.a();
                this.u = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof r)) {
                return super.equals(obj);
            }
            r rVar = (r) obj;
            boolean z = e() == rVar.e();
            if (e()) {
                z = z && f().equals(rVar.f());
            }
            boolean z2 = z && g() == rVar.g();
            if (g()) {
                z2 = z2 && this.c == rVar.c;
            }
            boolean z3 = z2 && h() == rVar.h();
            if (h()) {
                z3 = z3 && Double.doubleToLongBits(this.d) == Double.doubleToLongBits(rVar.d);
            }
            boolean z4 = z3 && i() == rVar.i();
            if (i()) {
                z4 = z4 && j().equals(rVar.j());
            }
            boolean z5 = z4 && k() == rVar.k();
            if (k()) {
                z5 = z5 && l().equals(rVar.l());
            }
            boolean z6 = z5 && m() == rVar.m();
            if (m()) {
                z6 = z6 && n().equals(rVar.n());
            }
            boolean z7 = z6 && o() == rVar.o();
            if (o()) {
                z7 = z7 && p().equals(rVar.p());
            }
            boolean z8 = z7 && q() == rVar.q();
            if (q()) {
                z8 = z8 && r().equals(rVar.r());
            }
            boolean z9 = z8 && s() == rVar.s();
            if (s()) {
                z9 = z9 && this.e == rVar.e;
            }
            boolean z10 = z9 && t() == rVar.t();
            if (t()) {
                z10 = z10 && this.f == rVar.f;
            }
            boolean z11 = z10 && u() == rVar.u();
            if (u()) {
                z11 = z11 && v().equals(rVar.v());
            }
            boolean z12 = z11 && w() == rVar.w();
            if (w()) {
                z12 = z12 && x().equals(rVar.x());
            }
            boolean z13 = z12 && y() == rVar.y();
            if (y()) {
                z13 = z13 && z().equals(rVar.z());
            }
            boolean z14 = z13 && A() == rVar.A();
            if (A()) {
                return z14 && B().equals(rVar.B());
            }
            return z14;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = r.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + f().hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + this.c;
            }
            if (h()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + dp.a(Double.doubleToLongBits(this.d));
            }
            if (i()) {
                iHashCode = (((iHashCode * 37) + 4) * 53) + j().hashCode();
            }
            if (k()) {
                iHashCode = (((iHashCode * 37) + 5) * 53) + l().hashCode();
            }
            if (m()) {
                iHashCode = (((iHashCode * 37) + 6) * 53) + n().hashCode();
            }
            if (o()) {
                iHashCode = (((iHashCode * 37) + 7) * 53) + p().hashCode();
            }
            if (q()) {
                iHashCode = (((iHashCode * 37) + 8) * 53) + r().hashCode();
            }
            if (s()) {
                iHashCode = (((iHashCode * 37) + 9) * 53) + this.e;
            }
            if (t()) {
                iHashCode = (((iHashCode * 37) + 10) * 53) + dp.a(this.f);
            }
            if (u()) {
                iHashCode = (((iHashCode * 37) + 11) * 53) + v().hashCode();
            }
            if (w()) {
                iHashCode = (((iHashCode * 37) + 12) * 53) + x().hashCode();
            }
            if (y()) {
                iHashCode = (((iHashCode * 37) + 13) * 53) + z().hashCode();
            }
            if (A()) {
                iHashCode = (((iHashCode * 37) + 14) * 53) + B().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.h.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a C() {
            return a.e();
        }

        public static final class a extends Cdo.a implements s {
            public int b;
            private double i;
            private int n;
            private long o;
            public Object c = "";
            private int h = 1;
            public Object d = "";
            private Object j = "";
            private Object k = "";
            private Object l = "";
            private Object m = "";
            public Object e = "";
            private Object p = "";
            public Object f = "";
            public Object g = "";

            private a() {
            }

            public final r d() {
                r rVar = new r((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                rVar.j = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                rVar.c = this.h;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                rVar.d = this.i;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                rVar.k = this.d;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                rVar.l = this.j;
                if ((i & 32) == 32) {
                    i2 |= 32;
                }
                rVar.m = this.k;
                if ((i & 64) == 64) {
                    i2 |= 64;
                }
                rVar.n = this.l;
                if ((i & 128) == 128) {
                    i2 |= 128;
                }
                rVar.o = this.m;
                if ((i & 256) == 256) {
                    i2 |= 256;
                }
                rVar.e = this.n;
                if ((i & 512) == 512) {
                    i2 |= 512;
                }
                rVar.f = this.o;
                if ((i & 1024) == 1024) {
                    i2 |= 1024;
                }
                rVar.p = this.e;
                if ((i & 2048) == 2048) {
                    i2 |= 2048;
                }
                rVar.q = this.p;
                if ((i & 4096) == 4096) {
                    i2 |= 4096;
                }
                rVar.r = this.f;
                if ((i & 8192) == 8192) {
                    i2 |= 8192;
                }
                rVar.s = this.g;
                rVar.i = i2;
                return rVar;
            }

            public final a a(r rVar) {
                if (rVar != r.d()) {
                    if (rVar.e()) {
                        this.b |= 1;
                        this.c = rVar.j;
                    }
                    if (rVar.g()) {
                        int i = rVar.c;
                        this.b |= 2;
                        this.h = i;
                    }
                    if (rVar.h()) {
                        a(rVar.d);
                    }
                    if (rVar.i()) {
                        this.b |= 8;
                        this.d = rVar.k;
                    }
                    if (rVar.k()) {
                        this.b |= 16;
                        this.j = rVar.l;
                    }
                    if (rVar.m()) {
                        this.b |= 32;
                        this.k = rVar.m;
                    }
                    if (rVar.o()) {
                        this.b |= 64;
                        this.l = rVar.n;
                    }
                    if (rVar.q()) {
                        this.b |= 128;
                        this.m = rVar.o;
                    }
                    if (rVar.s()) {
                        int i2 = rVar.e;
                        this.b |= 256;
                        this.n = i2;
                    }
                    if (rVar.t()) {
                        long j = rVar.f;
                        this.b |= 512;
                        this.o = j;
                    }
                    if (rVar.u()) {
                        this.b |= 1024;
                        this.e = rVar.p;
                    }
                    if (rVar.w()) {
                        this.b |= 2048;
                        this.p = rVar.q;
                    }
                    if (rVar.y()) {
                        this.b |= 4096;
                        this.f = rVar.r;
                    }
                    if (rVar.A()) {
                        this.b |= 8192;
                        this.g = rVar.s;
                    }
                    this.a = this.a.a(rVar.h);
                }
                return this;
            }

            public final a a(double d) {
                this.b |= 4;
                this.i = d;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                return new a().a(d());
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                return (this.b & 1) == 1;
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }

        public static a a(r rVar) {
            return a.e().a(rVar);
        }
    }

    public static final class j extends Cdo implements k {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.j.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new j(dlVar, (byte) 0);
            }
        };
        private static final j d;
        long c;
        private final dk e;
        private int f;
        private Object g;
        private byte h;
        private int i;

        /* synthetic */ j(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ j(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private j(Cdo.a aVar) {
            super((byte) 0);
            this.h = (byte) -1;
            this.i = -1;
            this.e = aVar.a;
        }

        private j() {
            this.h = (byte) -1;
            this.i = -1;
            this.e = dk.b;
        }

        public static j d() {
            return d;
        }

        private j(dl dlVar) {
            this.h = (byte) -1;
            this.i = -1;
            j();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                this.f |= 1;
                                this.g = dkVarE;
                                break;
                            case 16:
                                this.f |= 2;
                                this.c = dlVar.g();
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (Throwable th) {
                        try {
                            dmVarA.a();
                        } catch (IOException e) {
                        } finally {
                            this.e = bVarG.a();
                        }
                        throw th;
                    }
                } catch (dq e2) {
                    e2.a = this;
                    throw e2;
                } catch (IOException e3) {
                    dq dqVar = new dq(e3.getMessage());
                    dqVar.a = this;
                    throw dqVar;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.e = bVarG.a();
            }
        }

        static {
            j jVar = new j();
            d = jVar;
            jVar.j();
        }

        public final boolean e() {
            return (this.f & 1) == 1;
        }

        public final String f() {
            Object obj = this.g;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.g = strE;
            }
            return strE;
        }

        private dk i() {
            Object obj = this.g;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.g = dkVarA;
            return dkVarA;
        }

        public final boolean g() {
            return (this.f & 2) == 2;
        }

        private void j() {
            this.g = "";
            this.c = 0L;
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.h;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            if (!e()) {
                this.h = (byte) 0;
                return false;
            }
            if (!g()) {
                this.h = (byte) 0;
                return false;
            }
            this.h = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.f & 1) == 1) {
                dmVar.a(1, i());
            }
            if ((this.f & 2) == 2) {
                dmVar.a(2, this.c);
            }
            dmVar.c(this.e);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.i;
            if (i == -1) {
                int iB = 0;
                if ((this.f & 1) == 1) {
                    iB = dm.b(1, i()) + 0;
                }
                if ((this.f & 2) == 2) {
                    iB += dm.b(2, this.c);
                }
                int iA = iB + this.e.a();
                this.i = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof j)) {
                return super.equals(obj);
            }
            j jVar = (j) obj;
            boolean z = e() == jVar.e();
            if (e()) {
                z = z && f().equals(jVar.f());
            }
            boolean z2 = z && g() == jVar.g();
            if (g()) {
                return z2 && this.c == jVar.c;
            }
            return z2;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = j.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + f().hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + dp.a(this.c);
            }
            int iHashCode2 = (iHashCode * 29) + this.e.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a h() {
            return a.e();
        }

        public static final class a extends Cdo.a implements k {
            private int b;
            private Object c = "";
            private long d;

            private a() {
            }

            final j d() {
                j jVar = new j((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                jVar.g = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                jVar.c = this.d;
                jVar.f = i2;
                return jVar;
            }

            public final a a(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 1;
                this.c = str;
                return this;
            }

            public final a a(long j) {
                this.b |= 2;
                this.d = j;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                a aVar = new a();
                j jVarD = d();
                if (jVarD != j.d()) {
                    if (jVarD.e()) {
                        aVar.b |= 1;
                        aVar.c = jVarD.g;
                    }
                    if (jVarD.g()) {
                        aVar.a(jVarD.c);
                    }
                    aVar.a = aVar.a.a(jVarD.e);
                }
                return aVar;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                if ((this.b & 1) == 1) {
                    return (this.b & 2) == 2;
                }
                return false;
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }
    }

    public static final class f extends Cdo implements g {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.f.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new f(dlVar, (byte) 0);
            }
        };
        private static final f d;
        i c;
        private final dk e;
        private int f;
        private Object g;
        private Object h;
        private byte i;
        private int j;

        /* synthetic */ f(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ f(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private f(Cdo.a aVar) {
            super((byte) 0);
            this.i = (byte) -1;
            this.j = -1;
            this.e = aVar.a;
        }

        private f() {
            this.i = (byte) -1;
            this.j = -1;
            this.e = dk.b;
        }

        public static f d() {
            return d;
        }

        private f(dl dlVar) {
            this.i = (byte) -1;
            this.j = -1;
            m();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 8:
                                int iF = dlVar.f();
                                i iVarA = i.a(iF);
                                if (iVarA == null) {
                                    dmVarA.e(iA);
                                    dmVarA.e(iF);
                                } else {
                                    this.f |= 1;
                                    this.c = iVarA;
                                }
                                break;
                            case 18:
                                dk dkVarE = dlVar.e();
                                this.f |= 2;
                                this.g = dkVarE;
                                break;
                            case 26:
                                dk dkVarE2 = dlVar.e();
                                this.f |= 4;
                                this.h = dkVarE2;
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (dq e) {
                        e.a = this;
                        throw e;
                    } catch (IOException e2) {
                        dq dqVar = new dq(e2.getMessage());
                        dqVar.a = this;
                        throw dqVar;
                    }
                } catch (Throwable th) {
                    try {
                        dmVarA.a();
                    } catch (IOException e3) {
                    } finally {
                        this.e = bVarG.a();
                    }
                    throw th;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.e = bVarG.a();
            }
        }

        static {
            f fVar = new f();
            d = fVar;
            fVar.m();
        }

        public final boolean e() {
            return (this.f & 1) == 1;
        }

        public final boolean f() {
            return (this.f & 2) == 2;
        }

        public final String g() {
            Object obj = this.g;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.g = strE;
            }
            return strE;
        }

        private dk k() {
            Object obj = this.g;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.g = dkVarA;
            return dkVarA;
        }

        public final boolean h() {
            return (this.f & 4) == 4;
        }

        public final String i() {
            Object obj = this.h;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.h = strE;
            }
            return strE;
        }

        private dk l() {
            Object obj = this.h;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.h = dkVarA;
            return dkVarA;
        }

        private void m() {
            this.c = i.APP;
            this.g = "";
            this.h = "";
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.i;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            if (!e()) {
                this.i = (byte) 0;
                return false;
            }
            if (!f()) {
                this.i = (byte) 0;
                return false;
            }
            this.i = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.f & 1) == 1) {
                dmVar.a(this.c.e);
            }
            if ((this.f & 2) == 2) {
                dmVar.a(2, k());
            }
            if ((this.f & 4) == 4) {
                dmVar.a(3, l());
            }
            dmVar.c(this.e);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.j;
            if (i == -1) {
                int iB = 0;
                if ((this.f & 1) == 1) {
                    iB = dm.b(this.c.e) + 0;
                }
                if ((this.f & 2) == 2) {
                    iB += dm.b(2, k());
                }
                if ((this.f & 4) == 4) {
                    iB += dm.b(3, l());
                }
                int iA = iB + this.e.a();
                this.j = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof f)) {
                return super.equals(obj);
            }
            f fVar = (f) obj;
            boolean z = e() == fVar.e();
            if (e()) {
                z = z && this.c == fVar.c;
            }
            boolean z2 = z && f() == fVar.f();
            if (f()) {
                z2 = z2 && g().equals(fVar.g());
            }
            boolean z3 = z2 && h() == fVar.h();
            if (h()) {
                return z3 && i().equals(fVar.i());
            }
            return z3;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = f.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + dp.a(this.c);
            }
            if (f()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + g().hashCode();
            }
            if (h()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + i().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.e.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a j() {
            return a.e();
        }

        public static final class a extends Cdo.a implements g {
            public int b;
            private i e = i.APP;
            public Object c = "";
            public Object d = "";

            private a() {
            }

            public final f d() {
                f fVar = new f((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                fVar.c = this.e;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                fVar.g = this.c;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                fVar.h = this.d;
                fVar.f = i2;
                return fVar;
            }

            public final a a(f fVar) {
                if (fVar != f.d()) {
                    if (fVar.e()) {
                        a(fVar.c);
                    }
                    if (fVar.f()) {
                        this.b |= 2;
                        this.c = fVar.g;
                    }
                    if (fVar.h()) {
                        this.b |= 4;
                        this.d = fVar.h;
                    }
                    this.a = this.a.a(fVar.e);
                }
                return this;
            }

            public final a a(i iVar) {
                if (iVar == null) {
                    throw new NullPointerException();
                }
                this.b |= 1;
                this.e = iVar;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                return new a().a(d());
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                if ((this.b & 1) == 1) {
                    return (this.b & 2) == 2;
                }
                return false;
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }

        public static a a(f fVar) {
            return a.e().a(fVar);
        }
    }

    public static final class p extends Cdo implements q {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.p.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new p(dlVar, (byte) 0);
            }
        };
        private static final p c;
        private final dk d;
        private int e;
        private Object f;
        private Object g;
        private Object h;
        private byte i;
        private int j;

        /* synthetic */ p(dl dlVar, byte b2) {
            this(dlVar);
        }

        /* synthetic */ p(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private p(Cdo.a aVar) {
            super((byte) 0);
            this.i = (byte) -1;
            this.j = -1;
            this.d = aVar.a;
        }

        private p() {
            this.i = (byte) -1;
            this.j = -1;
            this.d = dk.b;
        }

        public static p d() {
            return c;
        }

        private p(dl dlVar) {
            this.i = (byte) -1;
            this.j = -1;
            o();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 10:
                                dk dkVarE = dlVar.e();
                                this.e |= 1;
                                this.f = dkVarE;
                                break;
                            case 18:
                                dk dkVarE2 = dlVar.e();
                                this.e |= 2;
                                this.g = dkVarE2;
                                break;
                            case 26:
                                dk dkVarE3 = dlVar.e();
                                this.e |= 4;
                                this.h = dkVarE3;
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (Throwable th) {
                        try {
                            dmVarA.a();
                        } catch (IOException e) {
                        } finally {
                            this.d = bVarG.a();
                        }
                        throw th;
                    }
                } catch (dq e2) {
                    e2.a = this;
                    throw e2;
                } catch (IOException e3) {
                    dq dqVar = new dq(e3.getMessage());
                    dqVar.a = this;
                    throw dqVar;
                }
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.d = bVarG.a();
            }
        }

        static {
            p pVar = new p();
            c = pVar;
            pVar.o();
        }

        public final boolean e() {
            return (this.e & 1) == 1;
        }

        public final String f() {
            Object obj = this.f;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.f = strE;
            }
            return strE;
        }

        private dk l() {
            Object obj = this.f;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.f = dkVarA;
            return dkVarA;
        }

        public final boolean g() {
            return (this.e & 2) == 2;
        }

        public final String h() {
            Object obj = this.g;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.g = strE;
            }
            return strE;
        }

        private dk m() {
            Object obj = this.g;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.g = dkVarA;
            return dkVarA;
        }

        public final boolean i() {
            return (this.e & 4) == 4;
        }

        public final String j() {
            Object obj = this.h;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.h = strE;
            }
            return strE;
        }

        private dk n() {
            Object obj = this.h;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.h = dkVarA;
            return dkVarA;
        }

        private void o() {
            this.f = "";
            this.g = "";
            this.h = "";
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.i;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.i = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.e & 1) == 1) {
                dmVar.a(1, l());
            }
            if ((this.e & 2) == 2) {
                dmVar.a(2, m());
            }
            if ((this.e & 4) == 4) {
                dmVar.a(3, n());
            }
            dmVar.c(this.d);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.j;
            if (i == -1) {
                int iB = 0;
                if ((this.e & 1) == 1) {
                    iB = dm.b(1, l()) + 0;
                }
                if ((this.e & 2) == 2) {
                    iB += dm.b(2, m());
                }
                if ((this.e & 4) == 4) {
                    iB += dm.b(3, n());
                }
                int iA = iB + this.d.a();
                this.j = iA;
                return iA;
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof p)) {
                return super.equals(obj);
            }
            p pVar = (p) obj;
            boolean z = e() == pVar.e();
            if (e()) {
                z = z && f().equals(pVar.f());
            }
            boolean z2 = z && g() == pVar.g();
            if (g()) {
                z2 = z2 && h().equals(pVar.h());
            }
            boolean z3 = z2 && i() == pVar.i();
            if (i()) {
                return z3 && j().equals(pVar.j());
            }
            return z3;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = p.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + f().hashCode();
            }
            if (g()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + h().hashCode();
            }
            if (i()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + j().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.d.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a k() {
            return a.f();
        }

        public static final class a extends Cdo.a implements q {
            private int b;
            private Object c = "";
            private Object d = "";
            private Object e = "";

            private a() {
            }

            public final p d() {
                p pVarE = e();
                if (!pVarE.c()) {
                    throw new dz();
                }
                return pVarE;
            }

            public final p e() {
                p pVar = new p((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = (i & 1) != 1 ? 0 : 1;
                pVar.f = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                pVar.g = this.d;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                pVar.h = this.e;
                pVar.e = i2;
                return pVar;
            }

            public final a a(p pVar) {
                if (pVar != p.d()) {
                    if (pVar.e()) {
                        this.b |= 1;
                        this.c = pVar.f;
                    }
                    if (pVar.g()) {
                        this.b |= 2;
                        this.d = pVar.g;
                    }
                    if (pVar.i()) {
                        this.b |= 4;
                        this.e = pVar.h;
                    }
                    this.a = this.a.a(pVar.d);
                }
                return this;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                return true;
            }

            public final a a(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 1;
                this.c = str;
                return this;
            }

            public final a b(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 2;
                this.d = str;
                return this;
            }

            public final a c(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 4;
                this.e = str;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
            public a clone() {
                return new a().a(e());
            }

            static /* synthetic */ a f() {
                return new a();
            }
        }

        public static a a(p pVar) {
            return a.f().a(pVar);
        }
    }

    public static final class c extends Cdo implements h {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.c.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new c(dlVar, dnVar, (byte) 0);
            }
        };
        private static final c t;
        private Object A;
        private Object B;
        private Object C;
        private Object D;
        private byte E;
        private int F;
        public i c;
        public long d;
        long e;
        long f;
        long g;
        l h;
        a i;
        public z j;
        int k;
        int l;
        f m;
        r n;
        p o;
        List p;
        int q;
        long r;
        long s;
        private final dk u;
        private int v;
        private Object w;
        private Object x;
        private Object y;
        private Object z;

        /* synthetic */ c(dl dlVar, dn dnVar, byte b2) {
            this(dlVar, dnVar);
        }

        /* synthetic */ c(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private c(Cdo.a aVar) {
            super((byte) 0);
            this.E = (byte) -1;
            this.F = -1;
            this.u = aVar.a;
        }

        private c() {
            this.E = (byte) -1;
            this.F = -1;
            this.u = dk.b;
        }

        public static c d() {
            return t;
        }

        private c(dl dlVar, dn dnVar) {
            this.E = (byte) -1;
            this.F = -1;
            U();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z = false;
            int i = 0;
            while (!z) {
                try {
                    try {
                        int iA = dlVar.a();
                        switch (iA) {
                            case 0:
                                z = true;
                                break;
                            case 8:
                                int iF = dlVar.f();
                                i iVarA = i.a(iF);
                                if (iVarA == null) {
                                    dmVarA.e(iA);
                                    dmVarA.e(iF);
                                } else {
                                    this.v |= 1;
                                    this.c = iVarA;
                                }
                                break;
                            case 18:
                                dk dkVarE = dlVar.e();
                                this.v |= 2;
                                this.w = dkVarE;
                                break;
                            case TimeConstants.HOURSPERDAY /* 24 */:
                                this.v |= 4;
                                this.d = dlVar.g();
                                break;
                            case 32:
                                this.v |= 64;
                                this.g = dlVar.g();
                                break;
                            case 42:
                                l.a aVarA = (this.v & 128) == 128 ? l.a(this.h) : null;
                                this.h = (l) dlVar.a(l.b, dnVar);
                                if (aVarA != null) {
                                    aVarA.a(this.h);
                                    this.h = aVarA.d();
                                }
                                this.v |= 128;
                                break;
                            case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                                a.C0148a c0148aO = (this.v & 256) == 256 ? this.i.o() : null;
                                this.i = (a) dlVar.a(a.b, dnVar);
                                if (c0148aO != null) {
                                    c0148aO.a(this.i);
                                    this.i = c0148aO.d();
                                }
                                this.v |= 256;
                                break;
                            case 58:
                                z.a aVarA2 = (this.v & 512) == 512 ? z.a(this.j) : null;
                                this.j = (z) dlVar.a(z.b, dnVar);
                                if (aVarA2 != null) {
                                    aVarA2.a(this.j);
                                    this.j = aVarA2.d();
                                }
                                this.v |= 512;
                                break;
                            case 64:
                                this.v |= 1024;
                                this.k = dlVar.f();
                                break;
                            case 72:
                                this.v |= 2048;
                                this.l = dlVar.f();
                                break;
                            case 82:
                                f.a aVarA3 = (this.v & 4096) == 4096 ? f.a(this.m) : null;
                                this.m = (f) dlVar.a(f.b, dnVar);
                                if (aVarA3 != null) {
                                    aVarA3.a(this.m);
                                    this.m = aVarA3.d();
                                }
                                this.v |= 4096;
                                break;
                            case AdSize.LARGE_AD_HEIGHT /* 90 */:
                                r.a aVarA4 = (this.v & 8192) == 8192 ? r.a(this.n) : null;
                                this.n = (r) dlVar.a(r.b, dnVar);
                                if (aVarA4 != null) {
                                    aVarA4.a(this.n);
                                    this.n = aVarA4.d();
                                }
                                this.v |= 8192;
                                break;
                            case 98:
                                dk dkVarE2 = dlVar.e();
                                this.v |= 16384;
                                this.y = dkVarE2;
                                break;
                            case 106:
                                dk dkVarE3 = dlVar.e();
                                this.v |= 32768;
                                this.z = dkVarE3;
                                break;
                            case 114:
                                dk dkVarE4 = dlVar.e();
                                this.v |= 131072;
                                this.A = dkVarE4;
                                break;
                            case 122:
                                dk dkVarE5 = dlVar.e();
                                this.v |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
                                this.B = dkVarE5;
                                break;
                            case TransportMediator.KEYCODE_MEDIA_RECORD /* 130 */:
                                dk dkVarE6 = dlVar.e();
                                this.v |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                                this.C = dkVarE6;
                                break;
                            case 138:
                                if ((i & 1048576) != 1048576) {
                                    this.p = new ArrayList();
                                    i |= 1048576;
                                }
                                this.p.add(dlVar.a(j.b, dnVar));
                                break;
                            case 146:
                                p.a aVarA5 = (this.v & 65536) == 65536 ? p.a(this.o) : null;
                                this.o = (p) dlVar.a(p.b, dnVar);
                                if (aVarA5 != null) {
                                    aVarA5.a(this.o);
                                    this.o = aVarA5.e();
                                }
                                this.v |= 65536;
                                break;
                            case 152:
                                this.v |= 8;
                                this.e = dlVar.b();
                                break;
                            case 162:
                                dk dkVarE7 = dlVar.e();
                                this.v |= 16;
                                this.x = dkVarE7;
                                break;
                            case 168:
                                this.v |= 32;
                                this.f = dlVar.b();
                                break;
                            case 178:
                                dk dkVarE8 = dlVar.e();
                                this.v |= 1048576;
                                this.D = dkVarE8;
                                break;
                            case 184:
                                this.v |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                                this.q = dlVar.c();
                                break;
                            case 192:
                                this.v |= 4194304;
                                this.r = dlVar.b();
                                break;
                            case 200:
                                this.v |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                                this.s = dlVar.b();
                                break;
                            default:
                                if (!dlVar.a(iA, dmVarA)) {
                                    z = true;
                                }
                                break;
                        }
                    } catch (dq e) {
                        e.a = this;
                        throw e;
                    } catch (IOException e2) {
                        dq dqVar = new dq(e2.getMessage());
                        dqVar.a = this;
                        throw dqVar;
                    }
                } catch (Throwable th) {
                    if ((i & 1048576) == 1048576) {
                        this.p = Collections.unmodifiableList(this.p);
                    }
                    try {
                        dmVarA.a();
                    } catch (IOException e3) {
                    } finally {
                        this.u = bVarG.a();
                    }
                    throw th;
                }
            }
            if ((i & 1048576) == 1048576) {
                this.p = Collections.unmodifiableList(this.p);
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.u = bVarG.a();
            }
        }

        static {
            c cVar = new c();
            t = cVar;
            cVar.U();
        }

        public final boolean e() {
            return (this.v & 1) == 1;
        }

        public final boolean f() {
            return (this.v & 2) == 2;
        }

        public final String g() {
            Object obj = this.w;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.w = strE;
            }
            return strE;
        }

        private dk M() {
            Object obj = this.w;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.w = dkVarA;
            return dkVarA;
        }

        public final boolean h() {
            return (this.v & 4) == 4;
        }

        public final boolean i() {
            return (this.v & 8) == 8;
        }

        public final boolean j() {
            return (this.v & 16) == 16;
        }

        public final String k() {
            Object obj = this.x;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.x = strE;
            }
            return strE;
        }

        private dk N() {
            Object obj = this.x;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.x = dkVarA;
            return dkVarA;
        }

        public final boolean l() {
            return (this.v & 32) == 32;
        }

        public final boolean m() {
            return (this.v & 64) == 64;
        }

        public final boolean n() {
            return (this.v & 128) == 128;
        }

        public final boolean o() {
            return (this.v & 256) == 256;
        }

        public final boolean p() {
            return (this.v & 512) == 512;
        }

        public final boolean q() {
            return (this.v & 1024) == 1024;
        }

        public final boolean r() {
            return (this.v & 2048) == 2048;
        }

        public final boolean s() {
            return (this.v & 4096) == 4096;
        }

        public final boolean t() {
            return (this.v & 8192) == 8192;
        }

        public final boolean u() {
            return (this.v & 16384) == 16384;
        }

        public final String v() {
            Object obj = this.y;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.y = strE;
            }
            return strE;
        }

        private dk O() {
            Object obj = this.y;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.y = dkVarA;
            return dkVarA;
        }

        public final boolean w() {
            return (this.v & 32768) == 32768;
        }

        public final String x() {
            Object obj = this.z;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.z = strE;
            }
            return strE;
        }

        private dk P() {
            Object obj = this.z;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.z = dkVarA;
            return dkVarA;
        }

        public final boolean y() {
            return (this.v & 65536) == 65536;
        }

        public final boolean z() {
            return (this.v & 131072) == 131072;
        }

        public final String A() {
            Object obj = this.A;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.A = strE;
            }
            return strE;
        }

        private dk Q() {
            Object obj = this.A;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.A = dkVarA;
            return dkVarA;
        }

        public final boolean B() {
            return (this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144;
        }

        public final String C() {
            Object obj = this.B;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.B = strE;
            }
            return strE;
        }

        private dk R() {
            Object obj = this.B;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.B = dkVarA;
            return dkVarA;
        }

        public final boolean D() {
            return (this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288;
        }

        public final String E() {
            Object obj = this.C;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.C = strE;
            }
            return strE;
        }

        private dk S() {
            Object obj = this.C;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.C = dkVarA;
            return dkVarA;
        }

        public final int F() {
            return this.p.size();
        }

        public final boolean G() {
            return (this.v & 1048576) == 1048576;
        }

        public final String H() {
            Object obj = this.D;
            if (obj instanceof String) {
                return (String) obj;
            }
            dk dkVar = (dk) obj;
            String strE = dkVar.e();
            if (dkVar.f()) {
                this.D = strE;
            }
            return strE;
        }

        private dk T() {
            Object obj = this.D;
            if (!(obj instanceof String)) {
                return (dk) obj;
            }
            dk dkVarA = dk.a((String) obj);
            this.D = dkVarA;
            return dkVarA;
        }

        public final boolean I() {
            return (this.v & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152;
        }

        public final boolean J() {
            return (this.v & 4194304) == 4194304;
        }

        public final boolean K() {
            return (this.v & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608;
        }

        private void U() {
            this.c = i.APP;
            this.w = "";
            this.d = 0L;
            this.e = 0L;
            this.x = "";
            this.f = 0L;
            this.g = 0L;
            this.h = l.d();
            this.i = a.d();
            this.j = z.d();
            this.k = 0;
            this.l = 0;
            this.m = f.d();
            this.n = r.d();
            this.y = "";
            this.z = "";
            this.o = p.d();
            this.A = "";
            this.B = "";
            this.C = "";
            this.p = Collections.emptyList();
            this.D = "";
            this.q = 0;
            this.r = 0L;
            this.s = 0L;
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.E;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            if (!e()) {
                this.E = (byte) 0;
                return false;
            }
            if (!f()) {
                this.E = (byte) 0;
                return false;
            }
            if (!h()) {
                this.E = (byte) 0;
                return false;
            }
            if (p() && !this.j.c()) {
                this.E = (byte) 0;
                return false;
            }
            if (s() && !this.m.c()) {
                this.E = (byte) 0;
                return false;
            }
            if (t() && !this.n.c()) {
                this.E = (byte) 0;
                return false;
            }
            for (int i = 0; i < F(); i++) {
                if (!((j) this.p.get(i)).c()) {
                    this.E = (byte) 0;
                    return false;
                }
            }
            this.E = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            if ((this.v & 1) == 1) {
                dmVar.a(this.c.e);
            }
            if ((this.v & 2) == 2) {
                dmVar.a(2, M());
            }
            if ((this.v & 4) == 4) {
                dmVar.a(3, this.d);
            }
            if ((this.v & 64) == 64) {
                dmVar.a(4, this.g);
            }
            if ((this.v & 128) == 128) {
                dmVar.a(5, this.h);
            }
            if ((this.v & 256) == 256) {
                dmVar.a(6, this.i);
            }
            if ((this.v & 512) == 512) {
                dmVar.a(7, this.j);
            }
            if ((this.v & 1024) == 1024) {
                dmVar.a(8, this.k);
            }
            if ((this.v & 2048) == 2048) {
                dmVar.a(9, this.l);
            }
            if ((this.v & 4096) == 4096) {
                dmVar.a(10, this.m);
            }
            if ((this.v & 8192) == 8192) {
                dmVar.a(11, this.n);
            }
            if ((this.v & 16384) == 16384) {
                dmVar.a(12, O());
            }
            if ((this.v & 32768) == 32768) {
                dmVar.a(13, P());
            }
            if ((this.v & 131072) == 131072) {
                dmVar.a(14, Q());
            }
            if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144) {
                dmVar.a(15, R());
            }
            if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288) {
                dmVar.a(16, S());
            }
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.p.size()) {
                    break;
                }
                dmVar.a(17, (du) this.p.get(i2));
                i = i2 + 1;
            }
            if ((this.v & 65536) == 65536) {
                dmVar.a(18, this.o);
            }
            if ((this.v & 8) == 8) {
                dmVar.a(19, this.e);
            }
            if ((this.v & 16) == 16) {
                dmVar.a(20, N());
            }
            if ((this.v & 32) == 32) {
                dmVar.a(21, this.f);
            }
            if ((this.v & 1048576) == 1048576) {
                dmVar.a(22, T());
            }
            if ((this.v & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152) {
                dmVar.a(23, this.q);
            }
            if ((this.v & 4194304) == 4194304) {
                dmVar.a(24, this.r);
            }
            if ((this.v & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608) {
                dmVar.a(25, this.s);
            }
            dmVar.c(this.u);
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int iB;
            int i = 0;
            int i2 = this.F;
            if (i2 == -1) {
                int iB2 = (this.v & 1) == 1 ? dm.b(this.c.e) + 0 : 0;
                if ((this.v & 2) == 2) {
                    iB2 += dm.b(2, M());
                }
                if ((this.v & 4) == 4) {
                    iB2 += dm.b(3, this.d);
                }
                if ((this.v & 64) == 64) {
                    iB2 += dm.b(4, this.g);
                }
                if ((this.v & 128) == 128) {
                    iB2 += dm.b(5, this.h);
                }
                if ((this.v & 256) == 256) {
                    iB2 += dm.b(6, this.i);
                }
                if ((this.v & 512) == 512) {
                    iB2 += dm.b(7, this.j);
                }
                if ((this.v & 1024) == 1024) {
                    iB2 += dm.b(8, this.k);
                }
                if ((this.v & 2048) == 2048) {
                    iB2 += dm.b(9, this.l);
                }
                if ((this.v & 4096) == 4096) {
                    iB2 += dm.b(10, this.m);
                }
                if ((this.v & 8192) == 8192) {
                    iB2 += dm.b(11, this.n);
                }
                if ((this.v & 16384) == 16384) {
                    iB2 += dm.b(12, O());
                }
                if ((this.v & 32768) == 32768) {
                    iB2 += dm.b(13, P());
                }
                if ((this.v & 131072) == 131072) {
                    iB2 += dm.b(14, Q());
                }
                if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144) {
                    iB2 += dm.b(15, R());
                }
                if ((this.v & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288) {
                    iB2 += dm.b(16, S());
                }
                while (true) {
                    iB = iB2;
                    if (i >= this.p.size()) {
                        break;
                    }
                    iB2 = dm.b(17, (du) this.p.get(i)) + iB;
                    i++;
                }
                if ((this.v & 65536) == 65536) {
                    iB += dm.b(18, this.o);
                }
                if ((this.v & 8) == 8) {
                    iB += dm.b(19, this.e);
                }
                if ((this.v & 16) == 16) {
                    iB += dm.b(20, N());
                }
                if ((this.v & 32) == 32) {
                    iB += dm.b(21, this.f);
                }
                if ((this.v & 1048576) == 1048576) {
                    iB += dm.b(22, T());
                }
                if ((this.v & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END) == 2097152) {
                    iB += dm.b(23, this.q);
                }
                if ((this.v & 4194304) == 4194304) {
                    iB += dm.b(24, this.r);
                }
                if ((this.v & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == 8388608) {
                    iB += dm.b(25, this.s);
                }
                int iA = this.u.a() + iB;
                this.F = iA;
                return iA;
            }
            return i2;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof c)) {
                return super.equals(obj);
            }
            c cVar = (c) obj;
            boolean z = e() == cVar.e();
            if (e()) {
                z = z && this.c == cVar.c;
            }
            boolean z2 = z && f() == cVar.f();
            if (f()) {
                z2 = z2 && g().equals(cVar.g());
            }
            boolean z3 = z2 && h() == cVar.h();
            if (h()) {
                z3 = z3 && this.d == cVar.d;
            }
            boolean z4 = z3 && i() == cVar.i();
            if (i()) {
                z4 = z4 && this.e == cVar.e;
            }
            boolean z5 = z4 && j() == cVar.j();
            if (j()) {
                z5 = z5 && k().equals(cVar.k());
            }
            boolean z6 = z5 && l() == cVar.l();
            if (l()) {
                z6 = z6 && this.f == cVar.f;
            }
            boolean z7 = z6 && m() == cVar.m();
            if (m()) {
                z7 = z7 && this.g == cVar.g;
            }
            boolean z8 = z7 && n() == cVar.n();
            if (n()) {
                z8 = z8 && this.h.equals(cVar.h);
            }
            boolean z9 = z8 && o() == cVar.o();
            if (o()) {
                z9 = z9 && this.i.equals(cVar.i);
            }
            boolean z10 = z9 && p() == cVar.p();
            if (p()) {
                z10 = z10 && this.j.equals(cVar.j);
            }
            boolean z11 = z10 && q() == cVar.q();
            if (q()) {
                z11 = z11 && this.k == cVar.k;
            }
            boolean z12 = z11 && r() == cVar.r();
            if (r()) {
                z12 = z12 && this.l == cVar.l;
            }
            boolean z13 = z12 && s() == cVar.s();
            if (s()) {
                z13 = z13 && this.m.equals(cVar.m);
            }
            boolean z14 = z13 && t() == cVar.t();
            if (t()) {
                z14 = z14 && this.n.equals(cVar.n);
            }
            boolean z15 = z14 && u() == cVar.u();
            if (u()) {
                z15 = z15 && v().equals(cVar.v());
            }
            boolean z16 = z15 && w() == cVar.w();
            if (w()) {
                z16 = z16 && x().equals(cVar.x());
            }
            boolean z17 = z16 && y() == cVar.y();
            if (y()) {
                z17 = z17 && this.o.equals(cVar.o);
            }
            boolean z18 = z17 && z() == cVar.z();
            if (z()) {
                z18 = z18 && A().equals(cVar.A());
            }
            boolean z19 = z18 && B() == cVar.B();
            if (B()) {
                z19 = z19 && C().equals(cVar.C());
            }
            boolean z20 = z19 && D() == cVar.D();
            if (D()) {
                z20 = z20 && E().equals(cVar.E());
            }
            boolean z21 = (z20 && this.p.equals(cVar.p)) && G() == cVar.G();
            if (G()) {
                z21 = z21 && H().equals(cVar.H());
            }
            boolean z22 = z21 && I() == cVar.I();
            if (I()) {
                z22 = z22 && this.q == cVar.q;
            }
            boolean z23 = z22 && J() == cVar.J();
            if (J()) {
                z23 = z23 && this.r == cVar.r;
            }
            boolean z24 = z23 && K() == cVar.K();
            if (K()) {
                return z24 && this.s == cVar.s;
            }
            return z24;
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = c.class.hashCode() + 779;
            if (e()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + dp.a(this.c);
            }
            if (f()) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + g().hashCode();
            }
            if (h()) {
                iHashCode = (((iHashCode * 37) + 3) * 53) + dp.a(this.d);
            }
            if (i()) {
                iHashCode = (((iHashCode * 37) + 19) * 53) + dp.a(this.e);
            }
            if (j()) {
                iHashCode = (((iHashCode * 37) + 20) * 53) + k().hashCode();
            }
            if (l()) {
                iHashCode = (((iHashCode * 37) + 21) * 53) + dp.a(this.f);
            }
            if (m()) {
                iHashCode = (((iHashCode * 37) + 4) * 53) + dp.a(this.g);
            }
            if (n()) {
                iHashCode = (((iHashCode * 37) + 5) * 53) + this.h.hashCode();
            }
            if (o()) {
                iHashCode = (((iHashCode * 37) + 6) * 53) + this.i.hashCode();
            }
            if (p()) {
                iHashCode = (((iHashCode * 37) + 7) * 53) + this.j.hashCode();
            }
            if (q()) {
                iHashCode = (((iHashCode * 37) + 8) * 53) + this.k;
            }
            if (r()) {
                iHashCode = (((iHashCode * 37) + 9) * 53) + this.l;
            }
            if (s()) {
                iHashCode = (((iHashCode * 37) + 10) * 53) + this.m.hashCode();
            }
            if (t()) {
                iHashCode = (((iHashCode * 37) + 11) * 53) + this.n.hashCode();
            }
            if (u()) {
                iHashCode = (((iHashCode * 37) + 12) * 53) + v().hashCode();
            }
            if (w()) {
                iHashCode = (((iHashCode * 37) + 13) * 53) + x().hashCode();
            }
            if (y()) {
                iHashCode = (((iHashCode * 37) + 18) * 53) + this.o.hashCode();
            }
            if (z()) {
                iHashCode = (((iHashCode * 37) + 14) * 53) + A().hashCode();
            }
            if (B()) {
                iHashCode = (((iHashCode * 37) + 15) * 53) + C().hashCode();
            }
            if (D()) {
                iHashCode = (((iHashCode * 37) + 16) * 53) + E().hashCode();
            }
            if (F() > 0) {
                iHashCode = (((iHashCode * 37) + 17) * 53) + this.p.hashCode();
            }
            if (G()) {
                iHashCode = (((iHashCode * 37) + 22) * 53) + H().hashCode();
            }
            if (I()) {
                iHashCode = (((iHashCode * 37) + 23) * 53) + this.q;
            }
            if (J()) {
                iHashCode = (((iHashCode * 37) + 24) * 53) + dp.a(this.r);
            }
            if (K()) {
                iHashCode = (((iHashCode * 37) + 25) * 53) + dp.a(this.s);
            }
            int iHashCode2 = (iHashCode * 29) + this.u.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a L() {
            return a.e();
        }

        public static final class a extends Cdo.a implements h {
            private long A;
            public int b;
            public long e;
            private long q;
            private long r;
            private long s;
            private int t;
            private int u;
            private int y;
            private long z;
            public i c = i.APP;
            public Object d = "";
            public Object f = "";
            public l g = l.d();
            public a h = a.d();
            public z i = z.d();
            public f j = f.d();
            public r k = r.d();
            private Object v = "";
            private Object w = "";
            public p l = p.d();
            public Object m = "";
            public Object n = "";
            public Object o = "";
            private List x = Collections.emptyList();
            public Object p = "";

            private a() {
            }

            public final c d() {
                c cVar = new c((Cdo.a) this, (byte) 0);
                int i = this.b;
                int i2 = 0;
                if ((i & 1) == 1) {
                    i2 = 1;
                }
                cVar.c = this.c;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                cVar.w = this.d;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                cVar.d = this.e;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                cVar.e = this.q;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                cVar.x = this.f;
                if ((i & 32) == 32) {
                    i2 |= 32;
                }
                cVar.f = this.r;
                if ((i & 64) == 64) {
                    i2 |= 64;
                }
                cVar.g = this.s;
                if ((i & 128) == 128) {
                    i2 |= 128;
                }
                cVar.h = this.g;
                if ((i & 256) == 256) {
                    i2 |= 256;
                }
                cVar.i = this.h;
                if ((i & 512) == 512) {
                    i2 |= 512;
                }
                cVar.j = this.i;
                if ((i & 1024) == 1024) {
                    i2 |= 1024;
                }
                cVar.k = this.t;
                if ((i & 2048) == 2048) {
                    i2 |= 2048;
                }
                cVar.l = this.u;
                if ((i & 4096) == 4096) {
                    i2 |= 4096;
                }
                cVar.m = this.j;
                if ((i & 8192) == 8192) {
                    i2 |= 8192;
                }
                cVar.n = this.k;
                if ((i & 16384) == 16384) {
                    i2 |= 16384;
                }
                cVar.y = this.v;
                if ((i & 32768) == 32768) {
                    i2 |= 32768;
                }
                cVar.z = this.w;
                if ((i & 65536) == 65536) {
                    i2 |= 65536;
                }
                cVar.o = this.l;
                if ((i & 131072) == 131072) {
                    i2 |= 131072;
                }
                cVar.A = this.m;
                if ((i & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START) == 262144) {
                    i2 |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
                }
                cVar.B = this.n;
                if ((i & AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END) == 524288) {
                    i2 |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                }
                cVar.C = this.o;
                if ((this.b & 1048576) == 1048576) {
                    this.x = Collections.unmodifiableList(this.x);
                    this.b &= -1048577;
                }
                cVar.p = this.x;
                if ((2097152 & i) == 2097152) {
                    i2 |= 1048576;
                }
                cVar.D = this.p;
                if ((4194304 & i) == 4194304) {
                    i2 |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                }
                cVar.q = this.y;
                if ((8388608 & i) == 8388608) {
                    i2 |= 4194304;
                }
                cVar.r = this.z;
                if ((i & 16777216) == 16777216) {
                    i2 |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                }
                cVar.s = this.A;
                cVar.v = i2;
                return cVar;
            }

            public final a a(i iVar) {
                if (iVar == null) {
                    throw new NullPointerException();
                }
                this.b |= 1;
                this.c = iVar;
                return this;
            }

            public final a a(long j) {
                this.b |= 4;
                this.e = j;
                return this;
            }

            public final a b(long j) {
                this.b |= 8;
                this.q = j;
                return this;
            }

            public final a c(long j) {
                this.b |= 32;
                this.r = j;
                return this;
            }

            public final a d(long j) {
                this.b |= 64;
                this.s = j;
                return this;
            }

            public final a a(int i) {
                this.b |= 2048;
                this.u = i;
                return this;
            }

            public final a a(String str) {
                if (str == null) {
                    throw new NullPointerException();
                }
                this.b |= 32768;
                this.w = str;
                return this;
            }

            public final a a(p.a aVar) {
                this.l = aVar.d();
                this.b |= 65536;
                return this;
            }

            private void g() {
                if ((this.b & 1048576) != 1048576) {
                    this.x = new ArrayList(this.x);
                    this.b |= 1048576;
                }
            }

            public final a a(j.a aVar) {
                g();
                List list = this.x;
                j jVarD = aVar.d();
                if (jVarD.c()) {
                    list.add(jVarD);
                    return this;
                }
                throw new dz();
            }

            public final a b(int i) {
                this.b |= 4194304;
                this.y = i;
                return this;
            }

            public final a e(long j) {
                this.b |= GravityCompat.RELATIVE_LAYOUT_DIRECTION;
                this.z = j;
                return this;
            }

            public final a f(long j) {
                this.b |= 16777216;
                this.A = j;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
            public a clone() {
                a aVar = new a();
                c cVarD = d();
                if (cVarD != c.d()) {
                    if (cVarD.e()) {
                        aVar.a(cVarD.c);
                    }
                    if (cVarD.f()) {
                        aVar.b |= 2;
                        aVar.d = cVarD.w;
                    }
                    if (cVarD.h()) {
                        aVar.a(cVarD.d);
                    }
                    if (cVarD.i()) {
                        aVar.b(cVarD.e);
                    }
                    if (cVarD.j()) {
                        aVar.b |= 16;
                        aVar.f = cVarD.x;
                    }
                    if (cVarD.l()) {
                        aVar.c(cVarD.f);
                    }
                    if (cVarD.m()) {
                        aVar.d(cVarD.g);
                    }
                    if (cVarD.n()) {
                        l lVar = cVarD.h;
                        if ((aVar.b & 128) == 128 && aVar.g != l.d()) {
                            aVar.g = l.a(aVar.g).a(lVar).d();
                        } else {
                            aVar.g = lVar;
                        }
                        aVar.b |= 128;
                    }
                    if (cVarD.o()) {
                        a aVar2 = cVarD.i;
                        if ((aVar.b & 256) == 256 && aVar.h != a.d()) {
                            aVar.h = a.a(aVar.h).a(aVar2).d();
                        } else {
                            aVar.h = aVar2;
                        }
                        aVar.b |= 256;
                    }
                    if (cVarD.p()) {
                        z zVar = cVarD.j;
                        if ((aVar.b & 512) == 512 && aVar.i != z.d()) {
                            aVar.i = z.a(aVar.i).a(zVar).d();
                        } else {
                            aVar.i = zVar;
                        }
                        aVar.b |= 512;
                    }
                    if (cVarD.q()) {
                        int i = cVarD.k;
                        aVar.b |= 1024;
                        aVar.t = i;
                    }
                    if (cVarD.r()) {
                        aVar.a(cVarD.l);
                    }
                    if (cVarD.s()) {
                        f fVar = cVarD.m;
                        if ((aVar.b & 4096) == 4096 && aVar.j != f.d()) {
                            aVar.j = f.a(aVar.j).a(fVar).d();
                        } else {
                            aVar.j = fVar;
                        }
                        aVar.b |= 4096;
                    }
                    if (cVarD.t()) {
                        r rVar = cVarD.n;
                        if ((aVar.b & 8192) == 8192 && aVar.k != r.d()) {
                            aVar.k = r.a(aVar.k).a(rVar).d();
                        } else {
                            aVar.k = rVar;
                        }
                        aVar.b |= 8192;
                    }
                    if (cVarD.u()) {
                        aVar.b |= 16384;
                        aVar.v = cVarD.y;
                    }
                    if (cVarD.w()) {
                        aVar.b |= 32768;
                        aVar.w = cVarD.z;
                    }
                    if (cVarD.y()) {
                        p pVar = cVarD.o;
                        if ((aVar.b & 65536) == 65536 && aVar.l != p.d()) {
                            aVar.l = p.a(aVar.l).a(pVar).e();
                        } else {
                            aVar.l = pVar;
                        }
                        aVar.b |= 65536;
                    }
                    if (cVarD.z()) {
                        aVar.b |= 131072;
                        aVar.m = cVarD.A;
                    }
                    if (cVarD.B()) {
                        aVar.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
                        aVar.n = cVarD.B;
                    }
                    if (cVarD.D()) {
                        aVar.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
                        aVar.o = cVarD.C;
                    }
                    if (!cVarD.p.isEmpty()) {
                        if (aVar.x.isEmpty()) {
                            aVar.x = cVarD.p;
                            aVar.b &= -1048577;
                        } else {
                            aVar.g();
                            aVar.x.addAll(cVarD.p);
                        }
                    }
                    if (cVarD.G()) {
                        aVar.b |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                        aVar.p = cVarD.D;
                    }
                    if (cVarD.I()) {
                        aVar.b(cVarD.q);
                    }
                    if (cVarD.J()) {
                        aVar.e(cVarD.r);
                    }
                    if (cVarD.K()) {
                        aVar.f(cVarD.s);
                    }
                    aVar.a = aVar.a.a(cVarD.u);
                }
                return aVar;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                if (!((this.b & 1) == 1)) {
                    return false;
                }
                if (!((this.b & 2) == 2)) {
                    return false;
                }
                if (!((this.b & 4) == 4)) {
                    return false;
                }
                if (((this.b & 512) == 512) && !this.i.c()) {
                    return false;
                }
                if (((this.b & 4096) == 4096) && !this.j.c()) {
                    return false;
                }
                if (((this.b & 8192) == 8192) && !this.k.c()) {
                    return false;
                }
                for (int i = 0; i < this.x.size(); i++) {
                    if (!((j) this.x.get(i)).c()) {
                        return false;
                    }
                }
                return true;
            }

            static /* synthetic */ a e() {
                return new a();
            }
        }
    }

    public static final class d extends Cdo implements e {
        public static dw b = new di() { // from class: com.tapjoy.internal.gl.d.1
            @Override // com.tapjoy.internal.dw
            public final /* synthetic */ Object a(dl dlVar, dn dnVar) {
                return new d(dlVar, dnVar, (byte) 0);
            }
        };
        private static final d d;
        List c;
        private final dk e;
        private byte f;
        private int g;

        /* synthetic */ d(dl dlVar, dn dnVar, byte b2) {
            this(dlVar, dnVar);
        }

        /* synthetic */ d(Cdo.a aVar, byte b2) {
            this(aVar);
        }

        private d(Cdo.a aVar) {
            super((byte) 0);
            this.f = (byte) -1;
            this.g = -1;
            this.e = aVar.a;
        }

        private d() {
            this.f = (byte) -1;
            this.g = -1;
            this.e = dk.b;
        }

        public static d d() {
            return d;
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0053  */
        private d(dl dlVar, dn dnVar) throws Throwable {
            Throwable th;
            boolean z = false;
            this.f = (byte) -1;
            this.g = -1;
            this.c = Collections.emptyList();
            dk.b bVarG = dk.g();
            dm dmVarA = dm.a(bVarG, 4096);
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        try {
                            int iA = dlVar.a();
                            switch (iA) {
                                case 0:
                                    z2 = true;
                                    break;
                                case 10:
                                    if (!(z & true)) {
                                        this.c = new ArrayList();
                                        z |= true;
                                    }
                                    this.c.add(dlVar.a(c.b, dnVar));
                                    break;
                                default:
                                    if (!dlVar.a(iA, dmVarA)) {
                                        z2 = true;
                                    }
                                    break;
                            }
                        } catch (IOException e) {
                            dq dqVar = new dq(e.getMessage());
                            dqVar.a = this;
                            throw dqVar;
                        }
                    } catch (dq e2) {
                        e2.a = this;
                        throw e2;
                    } catch (Throwable th2) {
                        z2 = z;
                        th = th2;
                        if (z2 & true) {
                            this.c = Collections.unmodifiableList(this.c);
                        }
                        try {
                            dmVarA.a();
                        } catch (IOException e3) {
                        } finally {
                            this.e = bVarG.a();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (z2 & true) {
                        this.c = Collections.unmodifiableList(this.c);
                    }
                    dmVarA.a();
                    throw th;
                }
            }
            if (z & true) {
                this.c = Collections.unmodifiableList(this.c);
            }
            try {
                dmVarA.a();
            } catch (IOException e4) {
            } finally {
                this.e = bVarG.a();
            }
        }

        static {
            d dVar = new d();
            d = dVar;
            dVar.c = Collections.emptyList();
        }

        private int f() {
            return this.c.size();
        }

        @Override // com.tapjoy.internal.dv
        public final boolean c() {
            byte b2 = this.f;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            for (int i = 0; i < f(); i++) {
                if (!((c) this.c.get(i)).c()) {
                    this.f = (byte) 0;
                    return false;
                }
            }
            this.f = (byte) 1;
            return true;
        }

        @Override // com.tapjoy.internal.du
        public final void a(dm dmVar) throws IOException {
            b();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.c.size()) {
                    dmVar.a(1, (du) this.c.get(i2));
                    i = i2 + 1;
                } else {
                    dmVar.c(this.e);
                    return;
                }
            }
        }

        @Override // com.tapjoy.internal.du
        public final int b() {
            int i = this.g;
            if (i != -1) {
                return i;
            }
            int iB = 0;
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                iB += dm.b(1, (du) this.c.get(i2));
            }
            int iA = this.e.a() + iB;
            this.g = iA;
            return iA;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof d) {
                return this.c.equals(((d) obj).c);
            }
            return super.equals(obj);
        }

        public final int hashCode() {
            if (this.a != 0) {
                return this.a;
            }
            int iHashCode = d.class.hashCode() + 779;
            if (f() > 0) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + this.c.hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.e.hashCode();
            this.a = iHashCode2;
            return iHashCode2;
        }

        public static a e() {
            return a.g();
        }

        public static final class a extends Cdo.a implements e {
            public List b = Collections.emptyList();
            private int c;

            private a() {
            }

            public final d d() {
                d dVarI = i();
                if (!dVarI.c()) {
                    throw new dz();
                }
                return dVarI;
            }

            private d i() {
                d dVar = new d((Cdo.a) this, (byte) 0);
                if ((this.c & 1) == 1) {
                    this.b = Collections.unmodifiableList(this.b);
                    this.c &= -2;
                }
                dVar.c = this.b;
                return dVar;
            }

            @Override // com.tapjoy.internal.dv
            public final boolean c() {
                for (int i = 0; i < f(); i++) {
                    if (!((c) this.b.get(i)).c()) {
                        return false;
                    }
                }
                return true;
            }

            public final void e() {
                if ((this.c & 1) != 1) {
                    this.b = new ArrayList(this.b);
                    this.c |= 1;
                }
            }

            public final int f() {
                return this.b.size();
            }

            /* JADX INFO: Access modifiers changed from: private */
            @Override // com.tapjoy.internal.Cdo.a, com.tapjoy.internal.dh.a
            /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
            public a clone() {
                a aVar = new a();
                d dVarI = i();
                if (dVarI != d.d()) {
                    if (!dVarI.c.isEmpty()) {
                        if (aVar.b.isEmpty()) {
                            aVar.b = dVarI.c;
                            aVar.c &= -2;
                        } else {
                            aVar.e();
                            aVar.b.addAll(dVarI.c);
                        }
                    }
                    aVar.a = aVar.a.a(dVarI.e);
                }
                return aVar;
            }

            static /* synthetic */ a g() {
                return new a();
            }
        }
    }
}
