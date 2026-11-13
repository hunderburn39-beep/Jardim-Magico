package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class cs {
    private static final by<com.google.android.gms.internal.d.a> ZE = new by<>(dh.lT(), true);
    private final DataLayer WK;
    private final cq.c ZF;
    private final ag ZG;
    private final Map<String, aj> ZH;
    private final Map<String, aj> ZI;
    private final Map<String, aj> ZJ;
    private final k<cq.a, by<com.google.android.gms.internal.d.a>> ZK;
    private final k<String, b> ZL;
    private final Set<cq.e> ZM;
    private final Map<String, c> ZN;
    private volatile String ZO;
    private int ZP;

    interface a {
        void a(cq.e eVar, Set<cq.a> set, Set<cq.a> set2, cm cmVar);
    }

    private static class b {
        private by<com.google.android.gms.internal.d.a> ZV;
        private com.google.android.gms.internal.d.a Zq;

        public b(by<com.google.android.gms.internal.d.a> byVar, com.google.android.gms.internal.d.a aVar) {
            this.ZV = byVar;
            this.Zq = aVar;
        }

        public int getSize() {
            return (this.Zq == null ? 0 : this.Zq.mF()) + this.ZV.getObject().mF();
        }

        public com.google.android.gms.internal.d.a lf() {
            return this.Zq;
        }

        public by<com.google.android.gms.internal.d.a> lz() {
            return this.ZV;
        }
    }

    private static class c {
        private cq.a aaa;
        private final Set<cq.e> ZM = new HashSet();
        private final Map<cq.e, List<cq.a>> ZW = new HashMap();
        private final Map<cq.e, List<String>> ZY = new HashMap();
        private final Map<cq.e, List<cq.a>> ZX = new HashMap();
        private final Map<cq.e, List<String>> ZZ = new HashMap();

        public void a(cq.e eVar, cq.a aVar) {
            List<cq.a> arrayList = this.ZW.get(eVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.ZW.put(eVar, arrayList);
            }
            arrayList.add(aVar);
        }

        public void a(cq.e eVar, String str) {
            List<String> arrayList = this.ZY.get(eVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.ZY.put(eVar, arrayList);
            }
            arrayList.add(str);
        }

        public void b(cq.e eVar) {
            this.ZM.add(eVar);
        }

        public void b(cq.e eVar, cq.a aVar) {
            List<cq.a> arrayList = this.ZX.get(eVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.ZX.put(eVar, arrayList);
            }
            arrayList.add(aVar);
        }

        public void b(cq.e eVar, String str) {
            List<String> arrayList = this.ZZ.get(eVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.ZZ.put(eVar, arrayList);
            }
            arrayList.add(str);
        }

        public void i(cq.a aVar) {
            this.aaa = aVar;
        }

        public Set<cq.e> lA() {
            return this.ZM;
        }

        public Map<cq.e, List<cq.a>> lB() {
            return this.ZW;
        }

        public Map<cq.e, List<String>> lC() {
            return this.ZY;
        }

        public Map<cq.e, List<String>> lD() {
            return this.ZZ;
        }

        public Map<cq.e, List<cq.a>> lE() {
            return this.ZX;
        }

        public cq.a lF() {
            return this.aaa;
        }
    }

    public cs(Context context, cq.c cVar, DataLayer dataLayer, s.a aVar, s.a aVar2, ag agVar) {
        if (cVar == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.ZF = cVar;
        this.ZM = new HashSet(cVar.li());
        this.WK = dataLayer;
        this.ZG = agVar;
        this.ZK = new l().a(1048576, new l.a<cq.a, by<com.google.android.gms.internal.d.a>>() { // from class: com.google.android.gms.tagmanager.cs.1
            @Override // com.google.android.gms.tagmanager.l.a
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int sizeOf(cq.a aVar3, by<com.google.android.gms.internal.d.a> byVar) {
                return byVar.getObject().mF();
            }
        });
        this.ZL = new l().a(1048576, new l.a<String, b>() { // from class: com.google.android.gms.tagmanager.cs.2
            @Override // com.google.android.gms.tagmanager.l.a
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int sizeOf(String str, b bVar) {
                return str.length() + bVar.getSize();
            }
        });
        this.ZH = new HashMap();
        b(new i(context));
        b(new s(aVar2));
        b(new w(dataLayer));
        b(new di(context, dataLayer));
        this.ZI = new HashMap();
        c(new q());
        c(new ad());
        c(new ae());
        c(new al());
        c(new am());
        c(new bd());
        c(new be());
        c(new ch());
        c(new db());
        this.ZJ = new HashMap();
        a(new com.google.android.gms.tagmanager.b(context));
        a(new com.google.android.gms.tagmanager.c(context));
        a(new e(context));
        a(new f(context));
        a(new g(context));
        a(new h(context));
        a(new m());
        a(new p(this.ZF.getVersion()));
        a(new s(aVar));
        a(new u(dataLayer));
        a(new z(context));
        a(new aa());
        a(new ac());
        a(new ah(this));
        a(new an());
        a(new ao());
        a(new ax(context));
        a(new az());
        a(new bc());
        a(new bk(context));
        a(new bz());
        a(new cb());
        a(new ce());
        a(new cg());
        a(new ci(context));
        a(new ct());
        a(new cu());
        a(new dd());
        this.ZN = new HashMap();
        for (cq.e eVar : this.ZM) {
            if (agVar.kA()) {
                a(eVar.lq(), eVar.lr(), "add macro");
                a(eVar.lv(), eVar.ls(), "remove macro");
                a(eVar.lo(), eVar.lt(), "add tag");
                a(eVar.lp(), eVar.lu(), "remove tag");
            }
            for (int i = 0; i < eVar.lq().size(); i++) {
                cq.a aVar3 = eVar.lq().get(i);
                String str = "Unknown";
                if (agVar.kA() && i < eVar.lr().size()) {
                    str = eVar.lr().get(i);
                }
                c cVarD = d(this.ZN, h(aVar3));
                cVarD.b(eVar);
                cVarD.a(eVar, aVar3);
                cVarD.a(eVar, str);
            }
            for (int i2 = 0; i2 < eVar.lv().size(); i2++) {
                cq.a aVar4 = eVar.lv().get(i2);
                String str2 = "Unknown";
                if (agVar.kA() && i2 < eVar.ls().size()) {
                    str2 = eVar.ls().get(i2);
                }
                c cVarD2 = d(this.ZN, h(aVar4));
                cVarD2.b(eVar);
                cVarD2.b(eVar, aVar4);
                cVarD2.b(eVar, str2);
            }
        }
        for (Map.Entry<String, List<cq.a>> entry : this.ZF.lj().entrySet()) {
            for (cq.a aVar5 : entry.getValue()) {
                if (!dh.n(aVar5.le().get(com.google.android.gms.internal.b.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    d(this.ZN, entry.getKey()).i(aVar5);
                }
            }
        }
    }

    private by<com.google.android.gms.internal.d.a> a(com.google.android.gms.internal.d.a aVar, Set<String> set, dj djVar) {
        if (!aVar.fX) {
            return new by<>(aVar, true);
        }
        switch (aVar.type) {
            case 2:
                com.google.android.gms.internal.d.a aVarG = cq.g(aVar);
                aVarG.fO = new com.google.android.gms.internal.d.a[aVar.fO.length];
                for (int i = 0; i < aVar.fO.length; i++) {
                    by<com.google.android.gms.internal.d.a> byVarA = a(aVar.fO[i], set, djVar.cd(i));
                    if (byVarA == ZE) {
                        return ZE;
                    }
                    aVarG.fO[i] = byVarA.getObject();
                }
                return new by<>(aVarG, false);
            case 3:
                com.google.android.gms.internal.d.a aVarG2 = cq.g(aVar);
                if (aVar.fP.length != aVar.fQ.length) {
                    bh.w("Invalid serving value: " + aVar.toString());
                    return ZE;
                }
                aVarG2.fP = new com.google.android.gms.internal.d.a[aVar.fP.length];
                aVarG2.fQ = new com.google.android.gms.internal.d.a[aVar.fP.length];
                for (int i2 = 0; i2 < aVar.fP.length; i2++) {
                    by<com.google.android.gms.internal.d.a> byVarA2 = a(aVar.fP[i2], set, djVar.ce(i2));
                    by<com.google.android.gms.internal.d.a> byVarA3 = a(aVar.fQ[i2], set, djVar.cf(i2));
                    if (byVarA2 == ZE || byVarA3 == ZE) {
                        return ZE;
                    }
                    aVarG2.fP[i2] = byVarA2.getObject();
                    aVarG2.fQ[i2] = byVarA3.getObject();
                }
                return new by<>(aVarG2, false);
            case 4:
                if (set.contains(aVar.fR)) {
                    bh.w("Macro cycle detected.  Current macro reference: " + aVar.fR + ".  Previous macro references: " + set.toString() + ".");
                    return ZE;
                }
                set.add(aVar.fR);
                by<com.google.android.gms.internal.d.a> byVarA4 = dk.a(a(aVar.fR, set, djVar.kP()), aVar.fW);
                set.remove(aVar.fR);
                return byVarA4;
            case 5:
            case 6:
            default:
                bh.w("Unknown type: " + aVar.type);
                return ZE;
            case 7:
                com.google.android.gms.internal.d.a aVarG3 = cq.g(aVar);
                aVarG3.fV = new com.google.android.gms.internal.d.a[aVar.fV.length];
                for (int i3 = 0; i3 < aVar.fV.length; i3++) {
                    by<com.google.android.gms.internal.d.a> byVarA5 = a(aVar.fV[i3], set, djVar.cg(i3));
                    if (byVarA5 == ZE) {
                        return ZE;
                    }
                    aVarG3.fV[i3] = byVarA5.getObject();
                }
                return new by<>(aVarG3, false);
        }
    }

    private by<com.google.android.gms.internal.d.a> a(String str, Set<String> set, bj bjVar) {
        cq.a next;
        this.ZP++;
        b bVar = this.ZL.get(str);
        if (bVar != null && !this.ZG.kA()) {
            a(bVar.lf(), set);
            this.ZP--;
            return bVar.lz();
        }
        c cVar = this.ZN.get(str);
        if (cVar == null) {
            bh.w(ly() + "Invalid macro: " + str);
            this.ZP--;
            return ZE;
        }
        by<Set<cq.a>> byVarA = a(str, cVar.lA(), cVar.lB(), cVar.lC(), cVar.lE(), cVar.lD(), set, bjVar.kr());
        if (byVarA.getObject().isEmpty()) {
            next = cVar.lF();
        } else {
            if (byVarA.getObject().size() > 1) {
                bh.z(ly() + "Multiple macros active for macroName " + str);
            }
            next = byVarA.getObject().iterator().next();
        }
        if (next == null) {
            this.ZP--;
            return ZE;
        }
        by<com.google.android.gms.internal.d.a> byVarA2 = a(this.ZJ, next, set, bjVar.kG());
        by<com.google.android.gms.internal.d.a> byVar = byVarA2 == ZE ? ZE : new by<>(byVarA2.getObject(), byVarA.kQ() && byVarA2.kQ());
        com.google.android.gms.internal.d.a aVarLf = next.lf();
        if (byVar.kQ()) {
            this.ZL.e(str, new b(byVar, aVarLf));
        }
        a(aVarLf, set);
        this.ZP--;
        return byVar;
    }

    private by<com.google.android.gms.internal.d.a> a(Map<String, aj> map, cq.a aVar, Set<String> set, cj cjVar) {
        boolean z;
        com.google.android.gms.internal.d.a aVar2 = aVar.le().get(com.google.android.gms.internal.b.FUNCTION.toString());
        if (aVar2 == null) {
            bh.w("No function id in properties");
            return ZE;
        }
        String str = aVar2.fS;
        aj ajVar = map.get(str);
        if (ajVar == null) {
            bh.w(str + " has no backing implementation.");
            return ZE;
        }
        by<com.google.android.gms.internal.d.a> byVar = this.ZK.get(aVar);
        if (byVar != null && !this.ZG.kA()) {
            return byVar;
        }
        HashMap map2 = new HashMap();
        boolean z2 = true;
        for (Map.Entry<String, com.google.android.gms.internal.d.a> entry : aVar.le().entrySet()) {
            by<com.google.android.gms.internal.d.a> byVarA = a(entry.getValue(), set, cjVar.bH(entry.getKey()).e(entry.getValue()));
            if (byVarA == ZE) {
                return ZE;
            }
            if (byVarA.kQ()) {
                aVar.a(entry.getKey(), byVarA.getObject());
                z = z2;
            } else {
                z = false;
            }
            map2.put(entry.getKey(), byVarA.getObject());
            z2 = z;
        }
        if (!ajVar.a(map2.keySet())) {
            bh.w("Incorrect keys for function " + str + " required " + ajVar.kC() + " had " + map2.keySet());
            return ZE;
        }
        boolean z3 = z2 && ajVar.jX();
        by<com.google.android.gms.internal.d.a> byVar2 = new by<>(ajVar.x(map2), z3);
        if (z3) {
            this.ZK.e(aVar, byVar2);
        }
        cjVar.d(byVar2.getObject());
        return byVar2;
    }

    private by<Set<cq.a>> a(Set<cq.e> set, Set<String> set2, a aVar, cr crVar) {
        Set<cq.a> hashSet = new HashSet<>();
        Set<cq.a> hashSet2 = new HashSet<>();
        boolean z = true;
        for (cq.e eVar : set) {
            cm cmVarKO = crVar.kO();
            by<Boolean> byVarA = a(eVar, set2, cmVarKO);
            if (byVarA.getObject().booleanValue()) {
                aVar.a(eVar, hashSet, hashSet2, cmVarKO);
            }
            z = z && byVarA.kQ();
        }
        hashSet.removeAll(hashSet2);
        crVar.b(hashSet);
        return new by<>(hashSet, z);
    }

    private void a(com.google.android.gms.internal.d.a aVar, Set<String> set) {
        by<com.google.android.gms.internal.d.a> byVarA;
        if (aVar == null || (byVarA = a(aVar, set, new bw())) == ZE) {
            return;
        }
        Object objO = dh.o(byVarA.getObject());
        if (objO instanceof Map) {
            this.WK.push((Map) objO);
            return;
        }
        if (!(objO instanceof List)) {
            bh.z("pushAfterEvaluate: value not a Map or List");
            return;
        }
        for (Object obj : (List) objO) {
            if (obj instanceof Map) {
                this.WK.push((Map) obj);
            } else {
                bh.z("pushAfterEvaluate: value not a Map");
            }
        }
    }

    private static void a(List<cq.a> list, List<String> list2, String str) {
        if (list.size() != list2.size()) {
            bh.x("Invalid resource: imbalance of rule names of functions for " + str + " operation. Using default rule name instead");
        }
    }

    private static void a(Map<String, aj> map, aj ajVar) {
        if (map.containsKey(ajVar.kB())) {
            throw new IllegalArgumentException("Duplicate function type name: " + ajVar.kB());
        }
        map.put(ajVar.kB(), ajVar);
    }

    private static c d(Map<String, c> map, String str) {
        c cVar = map.get(str);
        if (cVar != null) {
            return cVar;
        }
        c cVar2 = new c();
        map.put(str, cVar2);
        return cVar2;
    }

    private static String h(cq.a aVar) {
        return dh.j(aVar.le().get(com.google.android.gms.internal.b.INSTANCE_NAME.toString()));
    }

    private String ly() {
        if (this.ZP <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.ZP));
        for (int i = 2; i < this.ZP; i++) {
            sb.append(' ');
        }
        sb.append(": ");
        return sb.toString();
    }

    by<Boolean> a(cq.a aVar, Set<String> set, cj cjVar) {
        by<com.google.android.gms.internal.d.a> byVarA = a(this.ZI, aVar, set, cjVar);
        Boolean boolN = dh.n(byVarA.getObject());
        cjVar.d(dh.r(boolN));
        return new by<>(boolN, byVarA.kQ());
    }

    by<Boolean> a(cq.e eVar, Set<String> set, cm cmVar) {
        Iterator<cq.a> it = eVar.ln().iterator();
        boolean z = true;
        while (it.hasNext()) {
            by<Boolean> byVarA = a(it.next(), set, cmVar.kI());
            if (byVarA.getObject().booleanValue()) {
                cmVar.f(dh.r(false));
                return new by<>(false, byVarA.kQ());
            }
            z = z && byVarA.kQ();
        }
        Iterator<cq.a> it2 = eVar.lm().iterator();
        while (it2.hasNext()) {
            by<Boolean> byVarA2 = a(it2.next(), set, cmVar.kJ());
            if (!byVarA2.getObject().booleanValue()) {
                cmVar.f(dh.r(false));
                return new by<>(false, byVarA2.kQ());
            }
            z = z && byVarA2.kQ();
        }
        cmVar.f(dh.r(true));
        return new by<>(true, z);
    }

    by<Set<cq.a>> a(String str, Set<cq.e> set, final Map<cq.e, List<cq.a>> map, final Map<cq.e, List<String>> map2, final Map<cq.e, List<cq.a>> map3, final Map<cq.e, List<String>> map4, Set<String> set2, cr crVar) {
        return a(set, set2, new a() { // from class: com.google.android.gms.tagmanager.cs.3
            @Override // com.google.android.gms.tagmanager.cs.a
            public void a(cq.e eVar, Set<cq.a> set3, Set<cq.a> set4, cm cmVar) {
                List<cq.a> list = (List) map.get(eVar);
                List<String> list2 = (List) map2.get(eVar);
                if (list != null) {
                    set3.addAll(list);
                    cmVar.kK().b(list, list2);
                }
                List<cq.a> list3 = (List) map3.get(eVar);
                List<String> list4 = (List) map4.get(eVar);
                if (list3 != null) {
                    set4.addAll(list3);
                    cmVar.kL().b(list3, list4);
                }
            }
        }, crVar);
    }

    by<Set<cq.a>> a(Set<cq.e> set, cr crVar) {
        return a(set, new HashSet(), new a() { // from class: com.google.android.gms.tagmanager.cs.4
            @Override // com.google.android.gms.tagmanager.cs.a
            public void a(cq.e eVar, Set<cq.a> set2, Set<cq.a> set3, cm cmVar) {
                set2.addAll(eVar.lo());
                set3.addAll(eVar.lp());
                cmVar.kM().b(eVar.lo(), eVar.lt());
                cmVar.kN().b(eVar.lp(), eVar.lu());
            }
        }, crVar);
    }

    void a(aj ajVar) {
        a(this.ZJ, ajVar);
    }

    void b(aj ajVar) {
        a(this.ZH, ajVar);
    }

    public by<com.google.android.gms.internal.d.a> bR(String str) {
        this.ZP = 0;
        af afVarBA = this.ZG.bA(str);
        by<com.google.android.gms.internal.d.a> byVarA = a(str, new HashSet(), afVarBA.kx());
        afVarBA.kz();
        return byVarA;
    }

    synchronized void bS(String str) {
        this.ZO = str;
    }

    public synchronized void bp(String str) {
        bS(str);
        af afVarBB = this.ZG.bB(str);
        t tVarKy = afVarBB.ky();
        Iterator<cq.a> it = a(this.ZM, tVarKy.kr()).getObject().iterator();
        while (it.hasNext()) {
            a(this.ZH, it.next(), new HashSet(), tVarKy.kq());
        }
        afVarBB.kz();
        bS(null);
    }

    void c(aj ajVar) {
        a(this.ZI, ajVar);
    }

    public synchronized void e(List<com.google.android.gms.internal.c.i> list) {
        for (com.google.android.gms.internal.c.i iVar : list) {
            if (iVar.name == null || !iVar.name.startsWith("gaExperiment:")) {
                bh.y("Ignored supplemental: " + iVar);
            } else {
                ai.a(this.WK, iVar);
            }
        }
    }

    synchronized String lx() {
        return this.ZO;
    }
}
