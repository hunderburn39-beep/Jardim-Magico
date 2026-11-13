package com.tapjoy.internal;

import java.io.IOException;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
public final class bq implements bp {
    public final String a;

    public bq(String str) {
        this.a = str;
    }

    @Override // com.tapjoy.internal.bp
    public final void a(Writer writer) throws IOException {
        writer.write(this.a);
    }

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof bq) {
            return this.a.equals(((bq) o).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a;
    }
}
