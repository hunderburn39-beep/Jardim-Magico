package com.fyber.reporters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.fyber.a.a;
import com.fyber.b.i;
import com.fyber.reporters.a.c;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.e;
import com.fyber.utils.h;
import com.fyber.utils.m;
import com.fyber.utils.n;
import com.fyber.utils.s;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class Reporter {
    protected final String b;
    protected Map<String, String> c;

    protected abstract s a(s sVar);

    protected abstract a b();

    protected abstract String c();

    protected abstract String d();

    protected abstract c e();

    public Reporter addParameters(Map<String, String> map) {
        if (m.b(map)) {
            if (this.c == null) {
                this.c = new HashMap(map);
            } else {
                this.c.putAll(map);
            }
        }
        return this;
    }

    public Reporter addParameter(String str, String str2) {
        if (StringUtils.notNullNorEmpty(str)) {
            if (this.c == null) {
                this.c = new HashMap();
            }
            this.c.put(str, str2);
        }
        return this;
    }

    public boolean report(Context context) {
        if (!n.b(10)) {
            h.a(context);
            new Thread(new i(a(s.a(e.a(c()), b()).a(this.c).a()), e())).start();
            return true;
        }
        if (FyberLogger.isLogging()) {
            FyberLogger.i(d(), "Only devices running Android API level 10 and above are able to report");
        } else {
            Log.i(d(), "Only devices running Android API level 10 and above are able to report");
        }
        return false;
    }

    protected Reporter(@NonNull String str) {
        if (StringUtils.nullOrEmpty(str)) {
            throw new IllegalArgumentException("App id cannot be null nor empty.");
        }
        this.b = str;
    }
}
