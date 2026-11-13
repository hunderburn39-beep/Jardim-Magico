package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ai {
    private static void a(DataLayer dataLayer, com.google.android.gms.internal.c.d dVar) {
        for (com.google.android.gms.internal.d.a aVar : dVar.eS) {
            dataLayer.bv(dh.j(aVar));
        }
    }

    public static void a(DataLayer dataLayer, com.google.android.gms.internal.c.i iVar) {
        if (iVar.fI == null) {
            bh.z("supplemental missing experimentSupplemental");
            return;
        }
        a(dataLayer, iVar.fI);
        b(dataLayer, iVar.fI);
        c(dataLayer, iVar.fI);
    }

    private static void b(DataLayer dataLayer, com.google.android.gms.internal.c.d dVar) {
        for (com.google.android.gms.internal.d.a aVar : dVar.eR) {
            Map<String, Object> mapC = c(aVar);
            if (mapC != null) {
                dataLayer.push(mapC);
            }
        }
    }

    private static Map<String, Object> c(com.google.android.gms.internal.d.a aVar) {
        Object objO = dh.o(aVar);
        if (objO instanceof Map) {
            return (Map) objO;
        }
        bh.z("value: " + objO + " is not a map value, ignored.");
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0063  */
    /* JADX WARN: Code duplicated, block: B:25:0x006b  */
    /* JADX WARN: Code duplicated, block: B:29:0x009b  */
    /* JADX WARN: Code duplicated, block: B:31:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:32:0x00b3  */
    private static void c(DataLayer dataLayer, com.google.android.gms.internal.c.d dVar) {
        Map<String, Object> mapC;
        Object obj;
        for (com.google.android.gms.internal.c.C0051c c0051c : dVar.eT) {
            if (c0051c.eM == null) {
                bh.z("GaExperimentRandom: No key");
            } else {
                Object objValueOf = dataLayer.get(c0051c.eM);
                Long lValueOf = !(objValueOf instanceof Number) ? null : Long.valueOf(((Number) objValueOf).longValue());
                long j = c0051c.eN;
                long j2 = c0051c.eO;
                if (c0051c.eP && lValueOf != null && lValueOf.longValue() >= j && lValueOf.longValue() <= j2) {
                    dataLayer.bv(c0051c.eM);
                    mapC = dataLayer.c(c0051c.eM, objValueOf);
                    if (c0051c.eQ > 0) {
                        if (mapC.containsKey("gtm")) {
                            obj = mapC.get("gtm");
                            if (obj instanceof Map) {
                                ((Map) obj).put("lifetime", Long.valueOf(c0051c.eQ));
                            } else {
                                bh.z("GaExperimentRandom: gtm not a map");
                            }
                        } else {
                            mapC.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(c0051c.eQ)));
                        }
                    }
                    dataLayer.push(mapC);
                } else if (j <= j2) {
                    objValueOf = Long.valueOf(Math.round((Math.random() * (j2 - j)) + j));
                    dataLayer.bv(c0051c.eM);
                    mapC = dataLayer.c(c0051c.eM, objValueOf);
                    if (c0051c.eQ > 0) {
                        if (mapC.containsKey("gtm")) {
                            mapC.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(c0051c.eQ)));
                        } else {
                            obj = mapC.get("gtm");
                            if (obj instanceof Map) {
                                ((Map) obj).put("lifetime", Long.valueOf(c0051c.eQ));
                            } else {
                                bh.z("GaExperimentRandom: gtm not a map");
                            }
                        }
                    }
                    dataLayer.push(mapC);
                } else {
                    bh.z("GaExperimentRandom: random range invalid");
                }
            }
        }
    }
}
