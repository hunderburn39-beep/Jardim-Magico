package com.adcolony.sdk;

import android.util.Log;
import com.tapjoy.TJAdUnitConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.anddev.andengine.level.util.constants.LevelConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ag {
    t a;
    r b;
    ScheduledExecutorService c;
    List<v> d;
    HashMap<String, Object> e;

    ag(t tVar, r rVar, ScheduledExecutorService scheduledExecutorService, ArrayList<v> arrayList, HashMap<String, Object> map) {
        this.a = tVar;
        this.b = rVar;
        this.c = scheduledExecutorService;
        this.d = arrayList;
        this.e = map;
    }

    void a(String str) {
        if (w.j != null) {
            w.j.e.put("controllerVersion", str);
        }
    }

    void b(String str) {
        if (w.j != null) {
            w.j.e.put("sessionId", str);
        }
    }

    void a(long j, TimeUnit timeUnit) {
        this.c.scheduleAtFixedRate(new Runnable() { // from class: com.adcolony.sdk.ag.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ag.this.b();
                } catch (RuntimeException e) {
                    Log.e("ADCLogPOC", "RuntimeException thrown from {}#report. Exception was suppressed.", e);
                }
            }
        }, j, j, timeUnit);
    }

    void a() {
        this.c.shutdown();
        try {
            if (!this.c.awaitTermination(1L, TimeUnit.SECONDS)) {
                this.c.shutdownNow();
                if (!this.c.awaitTermination(1L, TimeUnit.SECONDS)) {
                    System.err.println(getClass().getSimpleName() + ": ScheduledExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            this.c.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    void b() {
        synchronized (this) {
            try {
                if (this.d.size() > 0) {
                    this.a.a(a(this.b, this.d));
                    this.d.clear();
                }
            } catch (IOException e) {
            } catch (JSONException e2) {
            }
        }
    }

    void c(String str) {
        a(new v(new Date(), 3, this.b.d(), str));
    }

    void d(String str) {
        a(new v(new Date(), 4, this.b.d(), str));
    }

    void e(String str) {
        a(new v(new Date(), 5, this.b.d(), str));
    }

    void f(String str) {
        a(new v(new Date(), 6, this.b.d(), str));
    }

    synchronized void a(v vVar) {
        this.d.add(vVar);
    }

    String a(r rVar, List<v> list) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("index", rVar.b());
        jSONObject.put("environment", rVar.d());
        jSONObject.put("version", rVar.c());
        JSONArray jSONArray = new JSONArray();
        Iterator<v> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(b(it.next()));
        }
        jSONObject.put("logs", jSONArray);
        return jSONObject.toString();
    }

    JSONObject b(v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject(this.e);
        jSONObject.put("index", this.b.b());
        jSONObject.put("environment", this.b.d());
        jSONObject.put("version", this.b.c());
        jSONObject.put(LevelConstants.TAG_LEVEL, vVar.a());
        jSONObject.put("timestamp", vVar.e().toString());
        jSONObject.put(LevelConstants.TAG_LEVEL, vVar.a());
        jSONObject.put("tag", vVar.d());
        jSONObject.put(TJAdUnitConstants.String.MESSAGE, vVar.c());
        return jSONObject;
    }
}
