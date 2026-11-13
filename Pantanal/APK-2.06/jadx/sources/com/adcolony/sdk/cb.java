package com.adcolony.sdk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
class cb {
    static final String b = "logEvent.ymf";
    private static final String f = "type";
    private static final String g = "time";
    private static final String h = "client_time";
    private static final String i = "nonce";
    private static final String j = "data";
    private static final String k = "server_time_valid";
    private static final String l = "meta";
    int a;
    int c = 0;
    boolean d = false;
    Map<String, Object> e = new TreeMap();

    cb() {
    }

    private String d() {
        return "AdColonyPubServices";
    }

    void a(final String str, final Map<String, Object> map) {
        if (map != null && str.length() != 0 && !str.equals("")) {
            if (bv.aN().au() == null) {
                bw.b(d(), "deviceData is null", true);
            }
            new bp(bv.aN().u) { // from class: com.adcolony.sdk.cb.1
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    Map<String, Object> mapL = ck.l(cb.b);
                    TreeMap treeMap = new TreeMap();
                    if (mapL != null) {
                        treeMap = new TreeMap(mapL);
                    }
                    int iE = bv.aN().aw().E();
                    int size = treeMap.size();
                    if (size < iE) {
                        cb.this.a = bv.aN().S();
                        cb.this.a++;
                        bv.aN().a(cb.this.a);
                        cb.this.e.put("LogEventMessage" + String.format("%06d", Integer.valueOf(cb.this.a)), cb.this.c(str, map));
                        treeMap.putAll(cb.this.e);
                        cb.this.e.clear();
                        ck.a(treeMap, cb.b);
                        bv.aN().aA().a(false);
                    } else {
                        HashMap map2 = new HashMap();
                        map2.put("limit", Integer.valueOf(iE));
                        map2.put("current_amount", Integer.valueOf(size));
                        cb.this.b("overflow_log", map2);
                    }
                    b();
                }
            };
        }
    }

    void b(final String str, final Map<String, Object> map) {
        if (map != null && str != null && !str.isEmpty()) {
            if (bv.aN().au() == null) {
                bw.b(d(), "deviceData is null", true);
            }
            new bp(bv.aN().u) { // from class: com.adcolony.sdk.cb.2
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    Map<String, Object> mapL = ck.l(cb.b);
                    if (mapL != null) {
                        Object[] array = mapL.keySet().toArray();
                        for (int length = array.length - 1; length >= 0; length--) {
                            String str2 = (String) array[length];
                            String strA = cb.this.a((Map<String, Object>) mapL.get(str2));
                            if (strA != null && strA.equals(str)) {
                                mapL.remove(str2);
                                break;
                            }
                        }
                    }
                    TreeMap treeMap = new TreeMap();
                    if (mapL != null) {
                        treeMap = new TreeMap(mapL);
                    }
                    cb.this.a = bv.aN().S();
                    cb.this.a++;
                    bv.aN().a(cb.this.a);
                    cb.this.e.put("LogEventMessage" + String.format("%06d", Integer.valueOf(cb.this.a)), cb.this.c(str, map));
                    treeMap.putAll(cb.this.e);
                    cb.this.e.clear();
                    ck.a(treeMap, cb.b);
                    bv.aN().aA().a(false);
                    b();
                }
            };
        }
    }

    String a(Map<String, Object> map) {
        if (map != null) {
            return (String) map.get("type");
        }
        return null;
    }

    Map<String, Object> c(String str, Map<String, Object> map) {
        String strJ;
        HashMap map2 = new HashMap(map);
        map2.put("sdkVersion", "3.0.4");
        map2.put("appVersion", bv.aN().au().a());
        String strI = ck.i();
        boolean z = false;
        if (ck.h() != 0) {
            z = true;
            strJ = ck.j();
        } else {
            strJ = strI;
        }
        HashMap map3 = new HashMap();
        map3.put("type", str);
        map3.put(g, strJ);
        map3.put(h, strI);
        map3.put(i, UUID.randomUUID());
        map3.put("data", map2);
        map3.put(k, Boolean.valueOf(z));
        map3.put(l, new HashMap(ce.l()));
        return map3;
    }

    Map<String, ?> a() {
        Map<String, Object> mapL = ck.l(b);
        if (mapL == null) {
            return null;
        }
        return new TreeMap(mapL);
    }

    /* JADX WARN: Multi-variable type inference failed */
    TreeMap<String, Object> a(long j2) {
        Map<String, Object> mapL = ck.l(b);
        if (mapL == null) {
            return null;
        }
        TreeMap treeMap = new TreeMap(mapL);
        TreeMap<String, Object> treeMap2 = new TreeMap<>();
        Iterator it = treeMap.entrySet().iterator();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            int i4 = Integer.parseInt(((String) entry.getKey()).substring(15));
            if (i4 > j2 || i3 >= bv.aN().aw().x()) {
                break;
            }
            treeMap2.put(entry.getKey(), entry.getValue());
            i2 = i3 + 1;
            this.c = i4;
        }
        return treeMap2;
    }

    void d(String str, final Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            new bp(bv.aN().u) { // from class: com.adcolony.sdk.cb.3
                @Override // com.adcolony.sdk.bp
                public synchronized void a() {
                    Map<String, Object> mapL = ck.l(cb.b);
                    mapL.put("LogEventMessage" + cb.this.a, map);
                    ck.a(mapL, cb.b);
                    b();
                }
            };
        }
    }

    void b(long j2) {
        TreeMap treeMap = new TreeMap(ck.l(b));
        bw.b(d(), "removeMsgsUpToMsgId lastMessageId=" + j2, true);
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (Integer.parseInt(((String) entry.getKey()).substring(15)) <= j2) {
                it.remove();
                bw.b(d(), "removing message: " + ((String) entry.getKey()), true);
            }
        }
        ck.a(treeMap, b);
        if (a().size() == 0) {
            this.a = 0;
            bv.aN().a(this.a);
        }
    }

    boolean b() {
        return bv.aN().l().getFileStreamPath(b).delete();
    }

    void c() {
        b();
    }
}
