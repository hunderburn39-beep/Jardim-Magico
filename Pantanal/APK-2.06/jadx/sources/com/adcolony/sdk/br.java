package com.adcolony.sdk;

import com.fyber.ads.videos.RewardedVideoActivity;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class br {
    private long b = 0;
    private boolean c = false;
    a a = new a();

    br() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a() {
        return "AdColonyPubServices";
    }

    String a(bn.b bVar) {
        switch (bVar) {
            case YVOLVER_SEVERITY_ERROR:
                return RewardedVideoActivity.REQUEST_STATUS_PARAMETER_ERROR;
            case YVOLVER_SEVERITY_WARNING:
                return "WARNING";
            case YVOLVER_SEVERITY_NOTICE:
                return "NOTICE";
            case YVOLVER_SEVERITY_NONE:
                return "NONE";
            default:
                return "INVALID";
        }
    }

    void a(String str, bn.b bVar, boolean z, Map<String, Object> map) {
        HashMap map2 = new HashMap();
        map2.put("msg", str);
        map2.put("severity", a(bVar));
        map2.put("data", map);
        bv.aN().aF().a("log", map2);
        a(z);
    }

    void a(String str, Map<String, Object> map) {
        bv.aN().aF().a(str, map);
    }

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    synchronized void a(boolean z) {
        if (bv.aN().x()) {
            bw.b(a(), "Send Logs..", true);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((z || jCurrentTimeMillis - this.b > bv.aN().aw().q()) && !this.c) {
                new bp(bv.aN().u) { // from class: com.adcolony.sdk.br.1
                    @Override // com.adcolony.sdk.bp
                    public synchronized void a() {
                        br.this.c = true;
                        br.this.b = System.currentTimeMillis();
                        int iS = bv.aN().S();
                        TreeMap<String, Object> treeMapA = bv.aN().aF().a(iS);
                        bw.b(br.this.a(), "requested sendLogs with lastMessageId: " + iS, true);
                        bw.b(br.this.a(), "sending logs with lastMessageId: " + bv.aN().aF().c, true);
                        if (treeMapA.isEmpty()) {
                            bw.b(br.this.a(), "No logs to send", true);
                            b();
                            br.this.c = false;
                        } else {
                            JSONObject jSONObject = new JSONObject();
                            new JSONArray();
                            try {
                                jSONObject.put("events", ck.f(treeMapA));
                                HashMap map = new HashMap();
                                map.put("events", jSONObject.toString());
                                HashMap map2 = new HashMap();
                                map2.put("lastQueuedMessageId", Integer.valueOf(bv.aN().aF().c));
                                map2.put("latestMessageLogged", Integer.valueOf(iS));
                                ca.c().a(bv.aN().aw().a(bn.aC), (Map<String, String>) map, "logs", false, (Map<String, Object>) map2, 1, new bk() { // from class: com.adcolony.sdk.br.1.1
                                    @Override // com.adcolony.sdk.bk
                                    public void a(bn.a aVar) {
                                        bv.aN().aA().c = false;
                                        a(aVar == bn.a.YVOLVER_ERROR_NONE, aVar);
                                    }
                                });
                            } catch (JSONException e) {
                                bs bsVar = new bs(bn.a.YVOLVER_ERROR_JSON, "Error occurred when sending logs ->" + e.getMessage());
                                bv.aN().a((Exception) e, "Error occurred when sending logs ->" + e.getMessage(), true);
                                a(bsVar);
                            }
                        }
                    }
                };
            }
            bw.b(a(), "not time to send logs yet.", true);
        }
    }

    public void b(boolean z) {
        this.c = z;
    }
}
