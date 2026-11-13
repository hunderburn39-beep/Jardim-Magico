package com.tapjoy.internal;

import com.tapjoy.TapjoyConstants;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class es extends fe {
    static final Map a = Collections.unmodifiableMap(new HashMap());
    private final fe.a c = a("BuildConfig");
    private final fe.a d = a("ServerFinal");
    private final fe.a e = a("AppRuntime");
    private final fe.a f;
    private final fe.a g;

    es() {
        this.e.b = new ConcurrentHashMap();
        this.f = a("ConnectFlags");
        this.g = a("ServerDefault");
        fe.a aVarA = a("SDKDefault");
        if (!"".isEmpty()) {
            try {
                this.c.b = br.b("").d();
            } catch (IOException e) {
                throw new Error("BuildConfig.TJC_CONFIGURATION malformed", e);
            }
        }
        HashMap map = new HashMap();
        map.put("placement_request_content_retry_timeout", -1);
        map.put("placement_request_content_retry_backoff", Arrays.asList(0L, 500L, Long.valueOf(TapjoyConstants.TIMER_INCREMENT), Double.valueOf(2.0d)));
        aVarA.b = map;
    }

    public final void a(Map map) {
        Map map2;
        Map map3 = null;
        if (map != null) {
            map2 = (Map) map.get("final");
            map3 = (Map) map.get("default");
        } else {
            map2 = null;
        }
        this.d.b = map2;
        this.g.b = map3;
        setChanged();
    }

    public final void a(Hashtable hashtable) {
        HashMap map = new HashMap();
        for (Map.Entry entry : hashtable.entrySet()) {
            String str = (String) a.get(entry.getKey());
            if (str == null) {
                str = (String) entry.getKey();
            }
            this.e.b.remove(str);
            map.put(str, entry.getValue());
        }
        this.f.b = map;
        setChanged();
    }
}
