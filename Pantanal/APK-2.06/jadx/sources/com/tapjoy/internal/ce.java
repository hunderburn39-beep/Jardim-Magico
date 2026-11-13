package com.tapjoy.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public abstract class ce {
    public static ExecutorService a;
    public static ch b;
    private Future c;

    public abstract Object a(URI uri, InputStream inputStream);

    public abstract String b();

    public abstract String c();

    public Map a() {
        return Collections.emptyMap();
    }

    public String d() {
        return null;
    }

    public Map e() {
        return new LinkedHashMap();
    }

    public final synchronized void a(cj cjVar, ExecutorService executorService) {
        if (this.c == null || this.c.isDone()) {
            this.c = executorService.submit(new cg(this, cjVar));
        } else {
            throw new IllegalStateException(String.valueOf("Call has not completed"));
        }
    }

    public Object f() {
        return b.a(this);
    }
}
