package com.tapjoy.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class bl implements bp {
    private final StringWriter a = new StringWriter();
    private final bx b = new bx(this.a);

    public final String toString() {
        try {
            this.b.a.flush();
            return this.a.toString();
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    @Override // com.tapjoy.internal.bp
    public final void a(Writer writer) {
        try {
            this.b.a.flush();
            writer.write(this.a.toString());
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a() {
        try {
            this.b.a();
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl b() {
        try {
            this.b.b();
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl c() {
        try {
            this.b.c();
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl d() {
        try {
            this.b.d();
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a(String str) {
        try {
            this.b.a(str);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a(bp bpVar) {
        try {
            this.b.a(bpVar);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl b(String str) {
        try {
            this.b.b(str);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a(double d) {
        try {
            this.b.a(d);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a(long j) {
        try {
            this.b.a(j);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    private bl b(Object obj) {
        try {
            this.b.a(obj);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a(Collection collection) {
        try {
            this.b.a(collection);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public final bl a(Map map) {
        try {
            this.b.a(map);
            return this;
        } catch (IOException e) {
            throw cs.a(e);
        }
    }

    public static String a(Object obj) {
        return new bl().b(obj).toString();
    }
}
