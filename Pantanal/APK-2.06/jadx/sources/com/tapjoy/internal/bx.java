package com.tapjoy.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class bx implements Closeable {
    final Writer a;
    private final List b = new ArrayList();
    private String c;
    private String d;
    private boolean e;

    public bx(Writer writer) {
        this.b.add(bu.EMPTY_DOCUMENT);
        this.d = ":";
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.a = writer;
    }

    public final bx a() {
        return a(bu.EMPTY_ARRAY, "[");
    }

    public final bx b() {
        return a(bu.EMPTY_ARRAY, bu.NONEMPTY_ARRAY, "]");
    }

    public final bx c() {
        return a(bu.EMPTY_OBJECT, "{");
    }

    public final bx d() {
        return a(bu.EMPTY_OBJECT, bu.NONEMPTY_OBJECT, "}");
    }

    private bx a(bu buVar, String str) throws IOException {
        a(true);
        this.b.add(buVar);
        this.a.write(str);
        return this;
    }

    private bx a(bu buVar, bu buVar2, String str) throws IOException {
        bu buVarE = e();
        if (buVarE != buVar2 && buVarE != buVar) {
            throw new IllegalStateException("Nesting problem: " + this.b);
        }
        this.b.remove(this.b.size() - 1);
        if (buVarE == buVar2) {
            g();
        }
        this.a.write(str);
        return this;
    }

    private bu e() {
        return (bu) this.b.get(this.b.size() - 1);
    }

    private void a(bu buVar) {
        this.b.set(this.b.size() - 1, buVar);
    }

    public final bx a(String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        bu buVarE = e();
        if (buVarE == bu.NONEMPTY_OBJECT) {
            this.a.write(44);
        } else if (buVarE != bu.EMPTY_OBJECT) {
            throw new IllegalStateException("Nesting problem: " + this.b);
        }
        g();
        a(bu.DANGLING_NAME);
        c(str);
        return this;
    }

    public final bx b(String str) throws IOException {
        if (str == null) {
            return f();
        }
        a(false);
        c(str);
        return this;
    }

    private bx f() throws IOException {
        a(false);
        this.a.write("null");
        return this;
    }

    public final bx a(double d) throws IOException {
        if (!this.e && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
        a(false);
        this.a.append((CharSequence) Double.toString(d));
        return this;
    }

    public final bx a(long j) throws IOException {
        a(false);
        this.a.write(Long.toString(j));
        return this;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.a.close();
        if (e() != bu.NONEMPTY_DOCUMENT) {
            throw new IOException("Incomplete document");
        }
    }

    private void c(String str) throws IOException {
        this.a.write("\"");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            switch (cCharAt) {
                case '\b':
                    this.a.write("\\b");
                    continue;
                    break;
                case '\t':
                    this.a.write("\\t");
                    continue;
                    break;
                case '\n':
                    this.a.write("\\n");
                    continue;
                    break;
                case '\f':
                    this.a.write("\\f");
                    continue;
                    break;
                case '\r':
                    this.a.write("\\r");
                    continue;
                    break;
                case '\"':
                case '\\':
                    this.a.write(92);
                    break;
                case 8232:
                case 8233:
                    this.a.write(String.format("\\u%04x", Integer.valueOf(cCharAt)));
                    continue;
                    break;
                default:
                    if (cCharAt <= 31) {
                        this.a.write(String.format("\\u%04x", Integer.valueOf(cCharAt)));
                    }
                    break;
            }
            this.a.write(cCharAt);
        }
        this.a.write("\"");
    }

    private void g() throws IOException {
        if (this.c != null) {
            this.a.write("\n");
            for (int i = 1; i < this.b.size(); i++) {
                this.a.write(this.c);
            }
        }
    }

    private void a(boolean z) throws IOException {
        switch (e()) {
            case EMPTY_DOCUMENT:
                if (!this.e && !z) {
                    throw new IllegalStateException("JSON must start with an array or an object.");
                }
                a(bu.NONEMPTY_DOCUMENT);
                return;
            case EMPTY_ARRAY:
                a(bu.NONEMPTY_ARRAY);
                g();
                return;
            case NONEMPTY_ARRAY:
                this.a.append(',');
                g();
                return;
            case DANGLING_NAME:
                this.a.append((CharSequence) this.d);
                a(bu.NONEMPTY_OBJECT);
                return;
            case NONEMPTY_DOCUMENT:
                throw new IllegalStateException("JSON must have only one top-level value.");
            default:
                throw new IllegalStateException("Nesting problem: " + this.b);
        }
    }

    public final bx a(Object obj) throws IOException {
        if (obj == null) {
            return f();
        }
        if (obj instanceof bv) {
            if (this.b.size() != this.b.size()) {
                throw new IllegalStateException(obj.getClass().getName() + ".writeToJson(JsonWriter) wrote incomplete value");
            }
            return this;
        }
        if (obj instanceof Boolean) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            a(false);
            this.a.write(zBooleanValue ? "true" : "false");
            return this;
        }
        if (obj instanceof Number) {
            if (obj instanceof Long) {
                return a(((Number) obj).longValue());
            }
            if (obj instanceof Double) {
                return a(((Number) obj).doubleValue());
            }
            Number number = (Number) obj;
            if (number == null) {
                return f();
            }
            String string = number.toString();
            if (!this.e && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
                throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
            }
            a(false);
            this.a.append((CharSequence) string);
            return this;
        }
        if (obj instanceof String) {
            return b((String) obj);
        }
        if (obj instanceof bp) {
            return a((bp) obj);
        }
        if (obj instanceof Collection) {
            return a((Collection) obj);
        }
        if (obj instanceof Map) {
            return a((Map) obj);
        }
        if (obj instanceof Date) {
            Date date = (Date) obj;
            if (date == null) {
                return f();
            }
            return b(z.a(date));
        }
        if (obj instanceof Object[]) {
            return a((Object[]) obj);
        }
        throw new IllegalArgumentException("Unknown type: " + obj.getClass().getName());
    }

    private bx a(Object[] objArr) throws IOException {
        if (objArr == null) {
            return f();
        }
        a();
        for (Object obj : objArr) {
            a(obj);
        }
        b();
        return this;
    }

    public final bx a(bp bpVar) throws IOException {
        a(false);
        bpVar.a(this.a);
        return this;
    }

    public final bx a(Collection collection) throws IOException {
        if (collection == null) {
            return f();
        }
        a();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        b();
        return this;
    }

    public final bx a(Map map) throws IOException {
        if (map == null) {
            return f();
        }
        c();
        for (Map.Entry entry : map.entrySet()) {
            a(String.valueOf(entry.getKey()));
            a(entry.getValue());
        }
        d();
        return this;
    }
}
