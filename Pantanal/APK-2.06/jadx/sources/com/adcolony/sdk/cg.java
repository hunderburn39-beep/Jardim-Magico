package com.adcolony.sdk;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"DefaultLocale"})
class cg {
    private Semaphore a = new Semaphore(1);
    private long b = 0;
    private long c = 0;
    private Queue<Map<String, Object>> d = new ConcurrentLinkedQueue();
    private ArrayList<HashMap<String, Object>> e = null;
    private final String f = "STATS";
    private Runnable g = new Runnable() { // from class: com.adcolony.sdk.cg.1
        @Override // java.lang.Runnable
        public void run() {
            cg.this.f();
        }
    };

    cg() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String i() {
        return "AdColonyPubServices";
    }

    synchronized void a(ArrayList<HashMap<String, Object>> arrayList) {
        bw.b(i(), "cachedStats()", true);
        if (this.e == null) {
            this.e = new ArrayList<>();
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.e.clear();
            this.e.addAll(arrayList);
            bw.b(i(), "making statsRefreshed callback", true);
            bv.aN().aE().c();
        }
        if (arrayList != null) {
            bw.b(i(), "cachedStats: " + arrayList.size() + "items, _statsList has " + this.e.size() + " items.", true);
        } else {
            bw.b(i(), "cachedStats: null", true);
        }
    }

    synchronized int a() {
        return this.d != null ? this.d.size() : 0;
    }

    synchronized ArrayList<HashMap<String, Object>> b() {
        ArrayList<HashMap<String, Object>> arrayList;
        if (this.e != null) {
            bw.b(i(), "getStats: _statsList has " + this.e.size() + " items.", true);
            arrayList = (ArrayList) this.e.clone();
        } else {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    long a(String str) throws IllegalArgumentException {
        HashMap<String, Object> mapB = b(str);
        if (mapB == null) {
            bw.b(i(), "Error: Stat " + str + " not found");
            throw new IllegalArgumentException();
        }
        Long l = (Long) mapB.get(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE);
        if (l == null) {
            bw.b(i(), "Stat value is not of type long");
            throw new IllegalArgumentException();
        }
        return l.longValue();
    }

    HashMap<String, Object> b(String str) {
        for (HashMap<String, Object> map : this.e) {
            if (map.containsValue(str)) {
                return map;
            }
        }
        bw.b(i(), "Error: Stat " + str + " not found");
        return null;
    }

    boolean a(String str, long j, int i) {
        return a(str, Long.toString(j), i);
    }

    boolean a(String str, String str2, int i) {
        boolean z;
        String strJ;
        boolean z2;
        int iIntValue;
        String lowerCase = str.toLowerCase();
        if (!bv.aN().z()) {
            bw.b(i(), "Stats are not available at this time.");
            return false;
        }
        if (this.e == null || this.e.isEmpty()) {
            bw.b(i(), "Stats are not currently available for this app.");
            return false;
        }
        bw.b(i(), "name: " + lowerCase + " value: " + str2 + " command: " + i, true);
        boolean z3 = (i & 112) != 0;
        int i2 = 0;
        boolean z4 = false;
        HashMap<String, Object> map = null;
        for (HashMap<String, Object> map2 : this.e) {
            if (map2.containsValue(lowerCase)) {
                ((Integer) map2.get("ops")).intValue();
                iIntValue = ((Integer) map2.get("type")).intValue();
                z2 = map2.containsKey("promo") ? true : z4;
            } else {
                map2 = map;
                z2 = z4;
                iIntValue = i2;
            }
            z4 = z2;
            i2 = iIntValue;
            map = map2;
        }
        if (!z3 && map == null) {
            bw.b(i(), "Error: Stat " + lowerCase + ", not found.");
            return false;
        }
        int i3 = Integer.parseInt(str2);
        if ((i & 6) != 0 && i3 == 0) {
            bw.b(i(), "Zero value used for " + lowerCase + " stat command");
            return false;
        }
        String string = UUID.randomUUID().toString();
        String strI = ck.i();
        if (ck.h() == 0) {
            z = false;
            strJ = ck.i();
        } else {
            z = true;
            strJ = ck.j();
        }
        HashMap map3 = new HashMap();
        map3.put("op", Integer.valueOf(i));
        map3.put(bn.as, lowerCase);
        map3.put(bn.at, Integer.valueOf(i2));
        map3.put(bn.au, Integer.valueOf(i3));
        String strG = bv.aN().g(lowerCase + string + i3);
        map3.put("time", strJ);
        map3.put(bn.ad, strI);
        map3.put("stat_transaction_id", string);
        map3.put("checksum", strG);
        map3.put("server_time_valid", Boolean.valueOf(z));
        this.d.add(map3);
        bw.b(i(), "stat " + map3 + " was queued", true);
        a(false);
        int iK = bv.aN().aw().k();
        bw.b(i(), "stat freq: " + iK, true);
        if (((i & 120) != 0) || z4) {
            int iL = bv.aN().aw().l();
            bw.b(i(), "promo stat freq: " + iL, true);
            iK = Math.min(iL, iK);
        }
        a(iK);
        bw.b(i(), "scheduleUpdateFromNowMs was called: " + iK, true);
        bw.b(i(), "statQueueSize: " + bv.aN().ay().d.size(), true);
        return true;
    }

    void a(long j) {
        bw.b(i(), "scheduleStatsUpdateFromNowMs was called", true);
        try {
            this.a.acquire();
        } catch (InterruptedException e) {
            bw.a(i(), "Caught Exception->" + e.getMessage(), e);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = jCurrentTimeMillis + j;
        if (j2 < this.c || this.c <= jCurrentTimeMillis) {
            this.c = j2;
            bv.aN().az().removeCallbacks(this.g);
            bv.aN().az().postDelayed(this.g, j);
        }
        this.a.release();
    }

    void c() {
        bv.aN().az().post(new Runnable() { // from class: com.adcolony.sdk.cg.2
            @Override // java.lang.Runnable
            public void run() {
                cg.this.d.clear();
            }
        });
        ck.i("STATS");
    }

    void a(boolean z) {
        long jP = bv.aN().aw().p();
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (z || jCurrentTimeMillis - this.b > jP) {
            bw.b(i(), "Saving stats", true);
            if (this.d.size() >= 1) {
                new bp(bv.aN().u) { // from class: com.adcolony.sdk.cg.3
                    @Override // com.adcolony.sdk.bp
                    public synchronized void a() {
                        Map<String, Object> mapA = ck.a("STATS", true);
                        for (Map map : cg.this.d) {
                            mapA.put((String) map.get("stat_transaction_id"), map);
                        }
                        ck.a("STATS", mapA);
                        b();
                    }
                };
                this.b = jCurrentTimeMillis;
            }
        }
    }

    void d() {
        a("", 0L, 16);
        a(0L);
    }

    void a(Collection<Map<String, Object>> collection) {
        this.d.removeAll(collection);
    }

    void b(final Collection<Map<String, Object>> collection) {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cg.4
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                Map<String, Object> mapA = ck.a("STATS", false);
                if (mapA != null) {
                    ArrayList arrayList = new ArrayList();
                    collection.iterator();
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        arrayList.add((String) ((Map) it.next()).get("stat_transaction_id"));
                    }
                    Iterator<Map.Entry<String, Object>> it2 = mapA.entrySet().iterator();
                    while (it2.hasNext()) {
                        String key = it2.next().getKey();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (((String) arrayList.get(i)).equals(key)) {
                                it2.remove();
                                break;
                            }
                        }
                    }
                    ck.a("STATS", mapA);
                }
                b();
            }
        };
    }

    void e() {
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cg.5
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                cg.this.d.clear();
                Map<String, Object> mapA = ck.a("STATS", false);
                if (mapA != null) {
                    Iterator<Map.Entry<String, Object>> it = mapA.entrySet().iterator();
                    while (it.hasNext()) {
                        cg.this.d.add((Map) it.next().getValue());
                    }
                    cg.this.a(0L);
                }
                b();
            }
        };
    }

    boolean f() {
        bw.b(i(), "flushStats()", true);
        if (!bv.aN().x()) {
            return false;
        }
        if (this.d.size() <= 0) {
            bw.b(i(), "stats queue is empty", true);
            return false;
        }
        new bp(bv.aN().u) { // from class: com.adcolony.sdk.cg.6
            @Override // com.adcolony.sdk.bp
            public synchronized void a() {
                JSONArray jSONArray = new JSONArray();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                try {
                    bw.b(cg.this.i(), "pre send queue:" + cg.this.d, true);
                    int i = 0;
                    for (Map map : cg.this.d) {
                        if (i >= bv.aN().aw().y()) {
                            break;
                        }
                        int i2 = i + 1;
                        arrayList.add(map);
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(bn.as, map.get(bn.as));
                        jSONObject.put(bn.at, map.get(bn.at));
                        jSONObject.put(bn.au, map.get(bn.au));
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("op", map.get("op"));
                        jSONObject2.put("stat", jSONObject);
                        jSONObject2.put("time", map.get("time"));
                        jSONObject2.put(bn.ad, map.get(bn.ad));
                        jSONObject2.put("stat_transaction_id", map.get("stat_transaction_id"));
                        jSONObject2.put("checksum", map.get("checksum"));
                        if (bv.aN().g(map.get(bn.as) + ((String) map.get("stat_transaction_id")) + map.get(bn.au)).equals(map.get("checksum"))) {
                            jSONArray.put(jSONObject2);
                        } else {
                            arrayList2.add(map);
                        }
                        i = i2;
                    }
                    if (jSONArray.length() == 0) {
                        bw.b(cg.this.i(), "no stats in array", true);
                        b();
                    } else {
                        String string = jSONArray.toString();
                        bw.b(cg.this.i(), "statsParam: " + string, true);
                        HashMap map2 = new HashMap();
                        map2.put("data", string);
                        HashMap map3 = new HashMap();
                        map3.put("success", arrayList);
                        map3.put("stats", jSONArray);
                        map3.put("failed", arrayList2);
                        ca.c().a(bv.aN().aw().a(bn.aJ), (Map<String, String>) map2, "stats", false, (Map<String, Object>) map3, 1, new bk() { // from class: com.adcolony.sdk.cg.6.1
                            @Override // com.adcolony.sdk.bk
                            public void a(bn.a aVar) {
                                a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                            }
                        });
                    }
                } catch (JSONException e) {
                    bs bsVar = new bs(bn.a.YVOLVER_ERROR_JSON, "Error occurred while flushing STAT queue->" + e.getMessage());
                    bv.aN().a((Exception) e, bn.a.YVOLVER_ERROR_JSON, "Error occurred while flushing STAT queue->" + e.getMessage(), true);
                    a(bsVar);
                }
            }
        };
        return true;
    }

    boolean a(String str, long j) {
        return a(str, j, 4);
    }

    boolean b(String str, long j) {
        return a(str, j, 2);
    }

    boolean c(String str, long j) {
        return a(str, j, 1);
    }

    boolean c(String str) {
        return a(str, 0L, 8);
    }

    boolean g() {
        return a("", 0L, 32);
    }

    boolean h() {
        return a("", 0L, 64);
    }
}
