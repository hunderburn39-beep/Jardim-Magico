package com.google.android.gms.tagmanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
class cq {

    public static class a {
        private final Map<String, com.google.android.gms.internal.d.a> Zp;
        private final com.google.android.gms.internal.d.a Zq;

        private a(Map<String, com.google.android.gms.internal.d.a> map, com.google.android.gms.internal.d.a aVar) {
            this.Zp = map;
            this.Zq = aVar;
        }

        public static b ld() {
            return new b();
        }

        public void a(String str, com.google.android.gms.internal.d.a aVar) {
            this.Zp.put(str, aVar);
        }

        public Map<String, com.google.android.gms.internal.d.a> le() {
            return Collections.unmodifiableMap(this.Zp);
        }

        public com.google.android.gms.internal.d.a lf() {
            return this.Zq;
        }

        public String toString() {
            return "Properties: " + le() + " pushAfterEvaluate: " + this.Zq;
        }
    }

    public static class b {
        private final Map<String, com.google.android.gms.internal.d.a> Zp;
        private com.google.android.gms.internal.d.a Zq;

        private b() {
            this.Zp = new HashMap();
        }

        public b b(String str, com.google.android.gms.internal.d.a aVar) {
            this.Zp.put(str, aVar);
            return this;
        }

        public b i(com.google.android.gms.internal.d.a aVar) {
            this.Zq = aVar;
            return this;
        }

        public a lg() {
            return new a(this.Zp, this.Zq);
        }
    }

    public static class c {
        private final String Xl;
        private final List<e> Zr;
        private final Map<String, List<a>> Zs;
        private final int Zt;

        private c(List<e> list, Map<String, List<a>> map, String str, int i) {
            this.Zr = Collections.unmodifiableList(list);
            this.Zs = Collections.unmodifiableMap(map);
            this.Xl = str;
            this.Zt = i;
        }

        public static d lh() {
            return new d();
        }

        public String getVersion() {
            return this.Xl;
        }

        public List<e> li() {
            return this.Zr;
        }

        public Map<String, List<a>> lj() {
            return this.Zs;
        }

        public String toString() {
            return "Rules: " + li() + "  Macros: " + this.Zs;
        }
    }

    public static class d {
        private String Xl;
        private final List<e> Zr;
        private final Map<String, List<a>> Zs;
        private int Zt;

        private d() {
            this.Zr = new ArrayList();
            this.Zs = new HashMap();
            this.Xl = "";
            this.Zt = 0;
        }

        public d a(a aVar) {
            String strJ = dh.j(aVar.le().get(com.google.android.gms.internal.b.INSTANCE_NAME.toString()));
            List<a> arrayList = this.Zs.get(strJ);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.Zs.put(strJ, arrayList);
            }
            arrayList.add(aVar);
            return this;
        }

        public d a(e eVar) {
            this.Zr.add(eVar);
            return this;
        }

        public d bM(String str) {
            this.Xl = str;
            return this;
        }

        public d ch(int i) {
            this.Zt = i;
            return this;
        }

        public c lk() {
            return new c(this.Zr, this.Zs, this.Xl, this.Zt);
        }
    }

    public static class e {
        private final List<String> ZA;
        private final List<String> ZB;
        private final List<String> ZC;
        private final List<String> ZD;
        private final List<a> Zu;
        private final List<a> Zv;
        private final List<a> Zw;
        private final List<a> Zx;
        private final List<a> Zy;
        private final List<a> Zz;

        private e(List<a> list, List<a> list2, List<a> list3, List<a> list4, List<a> list5, List<a> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.Zu = Collections.unmodifiableList(list);
            this.Zv = Collections.unmodifiableList(list2);
            this.Zw = Collections.unmodifiableList(list3);
            this.Zx = Collections.unmodifiableList(list4);
            this.Zy = Collections.unmodifiableList(list5);
            this.Zz = Collections.unmodifiableList(list6);
            this.ZA = Collections.unmodifiableList(list7);
            this.ZB = Collections.unmodifiableList(list8);
            this.ZC = Collections.unmodifiableList(list9);
            this.ZD = Collections.unmodifiableList(list10);
        }

        public static f ll() {
            return new f();
        }

        public List<a> lm() {
            return this.Zu;
        }

        public List<a> ln() {
            return this.Zv;
        }

        public List<a> lo() {
            return this.Zw;
        }

        public List<a> lp() {
            return this.Zx;
        }

        public List<a> lq() {
            return this.Zy;
        }

        public List<String> lr() {
            return this.ZA;
        }

        public List<String> ls() {
            return this.ZB;
        }

        public List<String> lt() {
            return this.ZC;
        }

        public List<String> lu() {
            return this.ZD;
        }

        public List<a> lv() {
            return this.Zz;
        }

        public String toString() {
            return "Positive predicates: " + lm() + "  Negative predicates: " + ln() + "  Add tags: " + lo() + "  Remove tags: " + lp() + "  Add macros: " + lq() + "  Remove macros: " + lv();
        }
    }

    public static class f {
        private final List<String> ZA;
        private final List<String> ZB;
        private final List<String> ZC;
        private final List<String> ZD;
        private final List<a> Zu;
        private final List<a> Zv;
        private final List<a> Zw;
        private final List<a> Zx;
        private final List<a> Zy;
        private final List<a> Zz;

        private f() {
            this.Zu = new ArrayList();
            this.Zv = new ArrayList();
            this.Zw = new ArrayList();
            this.Zx = new ArrayList();
            this.Zy = new ArrayList();
            this.Zz = new ArrayList();
            this.ZA = new ArrayList();
            this.ZB = new ArrayList();
            this.ZC = new ArrayList();
            this.ZD = new ArrayList();
        }

        public f b(a aVar) {
            this.Zu.add(aVar);
            return this;
        }

        public f bN(String str) {
            this.ZC.add(str);
            return this;
        }

        public f bO(String str) {
            this.ZD.add(str);
            return this;
        }

        public f bP(String str) {
            this.ZA.add(str);
            return this;
        }

        public f bQ(String str) {
            this.ZB.add(str);
            return this;
        }

        public f c(a aVar) {
            this.Zv.add(aVar);
            return this;
        }

        public f d(a aVar) {
            this.Zw.add(aVar);
            return this;
        }

        public f e(a aVar) {
            this.Zx.add(aVar);
            return this;
        }

        public f f(a aVar) {
            this.Zy.add(aVar);
            return this;
        }

        public f g(a aVar) {
            this.Zz.add(aVar);
            return this;
        }

        public e lw() {
            return new e(this.Zu, this.Zv, this.Zw, this.Zx, this.Zy, this.Zz, this.ZA, this.ZB, this.ZC, this.ZD);
        }
    }

    public static class g extends Exception {
        public g(String str) {
            super(str);
        }
    }

    private static com.google.android.gms.internal.d.a a(int i, com.google.android.gms.internal.c.f fVar, com.google.android.gms.internal.d.a[] aVarArr, Set<Integer> set) throws g {
        int i2 = 0;
        if (set.contains(Integer.valueOf(i))) {
            bL("Value cycle detected.  Current value reference: " + i + ".  Previous value references: " + set + ".");
        }
        com.google.android.gms.internal.d.a aVar = (com.google.android.gms.internal.d.a) a(fVar.eX, i, "values");
        if (aVarArr[i] != null) {
            return aVarArr[i];
        }
        com.google.android.gms.internal.d.a aVarG = null;
        set.add(Integer.valueOf(i));
        switch (aVar.type) {
            case 1:
            case 5:
            case 6:
            case 8:
                aVarG = aVar;
                break;
            case 2:
                com.google.android.gms.internal.c.h hVarH = h(aVar);
                aVarG = g(aVar);
                aVarG.fO = new com.google.android.gms.internal.d.a[hVarH.fz.length];
                int[] iArr = hVarH.fz;
                int length = iArr.length;
                int i3 = 0;
                while (i2 < length) {
                    aVarG.fO[i3] = a(iArr[i2], fVar, aVarArr, set);
                    i2++;
                    i3++;
                }
                break;
            case 3:
                aVarG = g(aVar);
                com.google.android.gms.internal.c.h hVarH2 = h(aVar);
                if (hVarH2.fA.length != hVarH2.fB.length) {
                    bL("Uneven map keys (" + hVarH2.fA.length + ") and map values (" + hVarH2.fB.length + ")");
                }
                aVarG.fP = new com.google.android.gms.internal.d.a[hVarH2.fA.length];
                aVarG.fQ = new com.google.android.gms.internal.d.a[hVarH2.fA.length];
                int[] iArr2 = hVarH2.fA;
                int length2 = iArr2.length;
                int i4 = 0;
                int i5 = 0;
                while (i4 < length2) {
                    aVarG.fP[i5] = a(iArr2[i4], fVar, aVarArr, set);
                    i4++;
                    i5++;
                }
                int[] iArr3 = hVarH2.fB;
                int length3 = iArr3.length;
                int i6 = 0;
                while (i2 < length3) {
                    aVarG.fQ[i6] = a(iArr3[i2], fVar, aVarArr, set);
                    i2++;
                    i6++;
                }
                break;
            case 4:
                aVarG = g(aVar);
                aVarG.fR = dh.j(a(h(aVar).fE, fVar, aVarArr, set));
                break;
            case 7:
                aVarG = g(aVar);
                com.google.android.gms.internal.c.h hVarH3 = h(aVar);
                aVarG.fV = new com.google.android.gms.internal.d.a[hVarH3.fD.length];
                int[] iArr4 = hVarH3.fD;
                int length4 = iArr4.length;
                int i7 = 0;
                while (i2 < length4) {
                    aVarG.fV[i7] = a(iArr4[i2], fVar, aVarArr, set);
                    i2++;
                    i7++;
                }
                break;
        }
        if (aVarG == null) {
            bL("Invalid value: " + aVar);
        }
        aVarArr[i] = aVarG;
        set.remove(Integer.valueOf(i));
        return aVarG;
    }

    private static a a(com.google.android.gms.internal.c.b bVar, com.google.android.gms.internal.c.f fVar, com.google.android.gms.internal.d.a[] aVarArr, int i) throws g {
        b bVarLd = a.ld();
        for (int i2 : bVar.eH) {
            com.google.android.gms.internal.c.e eVar = (com.google.android.gms.internal.c.e) a(fVar.eY, Integer.valueOf(i2).intValue(), TMXConstants.TAG_PROPERTIES);
            String str = (String) a(fVar.eW, eVar.key, "keys");
            com.google.android.gms.internal.d.a aVar = (com.google.android.gms.internal.d.a) a(aVarArr, eVar.value, "values");
            if (com.google.android.gms.internal.b.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                bVarLd.i(aVar);
            } else {
                bVarLd.b(str, aVar);
            }
        }
        return bVarLd.lg();
    }

    private static e a(com.google.android.gms.internal.c.g gVar, List<a> list, List<a> list2, List<a> list3, com.google.android.gms.internal.c.f fVar) {
        f fVarLl = e.ll();
        for (int i : gVar.fn) {
            fVarLl.b(list3.get(Integer.valueOf(i).intValue()));
        }
        for (int i2 : gVar.fo) {
            fVarLl.c(list3.get(Integer.valueOf(i2).intValue()));
        }
        for (int i3 : gVar.fp) {
            fVarLl.d(list.get(Integer.valueOf(i3).intValue()));
        }
        for (int i4 : gVar.fr) {
            fVarLl.bN(fVar.eX[Integer.valueOf(i4).intValue()].fN);
        }
        for (int i5 : gVar.fq) {
            fVarLl.e(list.get(Integer.valueOf(i5).intValue()));
        }
        for (int i6 : gVar.fs) {
            fVarLl.bO(fVar.eX[Integer.valueOf(i6).intValue()].fN);
        }
        for (int i7 : gVar.ft) {
            fVarLl.f(list2.get(Integer.valueOf(i7).intValue()));
        }
        for (int i8 : gVar.fv) {
            fVarLl.bP(fVar.eX[Integer.valueOf(i8).intValue()].fN);
        }
        for (int i9 : gVar.fu) {
            fVarLl.g(list2.get(Integer.valueOf(i9).intValue()));
        }
        for (int i10 : gVar.fw) {
            fVarLl.bQ(fVar.eX[Integer.valueOf(i10).intValue()].fN);
        }
        return fVarLl.lw();
    }

    private static <T> T a(T[] tArr, int i, String str) throws g {
        if (i < 0 || i >= tArr.length) {
            bL("Index out of bounds detected: " + i + " in " + str);
        }
        return tArr[i];
    }

    public static c b(com.google.android.gms.internal.c.f fVar) throws g {
        com.google.android.gms.internal.d.a[] aVarArr = new com.google.android.gms.internal.d.a[fVar.eX.length];
        for (int i = 0; i < fVar.eX.length; i++) {
            a(i, fVar, aVarArr, new HashSet(0));
        }
        d dVarLh = c.lh();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < fVar.fa.length; i2++) {
            arrayList.add(a(fVar.fa[i2], fVar, aVarArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < fVar.fb.length; i3++) {
            arrayList2.add(a(fVar.fb[i3], fVar, aVarArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < fVar.eZ.length; i4++) {
            a aVarA = a(fVar.eZ[i4], fVar, aVarArr, i4);
            dVarLh.a(aVarA);
            arrayList3.add(aVarA);
        }
        for (com.google.android.gms.internal.c.g gVar : fVar.fc) {
            dVarLh.a(a(gVar, arrayList, arrayList3, arrayList2, fVar));
        }
        dVarLh.bM(fVar.fg);
        dVarLh.ch(fVar.fl);
        return dVarLh.lk();
    }

    public static void b(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    private static void bL(String str) throws g {
        bh.w(str);
        throw new g(str);
    }

    public static com.google.android.gms.internal.d.a g(com.google.android.gms.internal.d.a aVar) {
        com.google.android.gms.internal.d.a aVar2 = new com.google.android.gms.internal.d.a();
        aVar2.type = aVar.type;
        aVar2.fW = (int[]) aVar.fW.clone();
        if (aVar.fX) {
            aVar2.fX = aVar.fX;
        }
        return aVar2;
    }

    private static com.google.android.gms.internal.c.h h(com.google.android.gms.internal.d.a aVar) throws g {
        if (((com.google.android.gms.internal.c.h) aVar.a(com.google.android.gms.internal.c.h.fx)) == null) {
            bL("Expected a ServingValue and didn't get one. Value is: " + aVar);
        }
        return (com.google.android.gms.internal.c.h) aVar.a(com.google.android.gms.internal.c.h.fx);
    }
}
