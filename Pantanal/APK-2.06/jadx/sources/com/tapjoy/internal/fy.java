package com.tapjoy.internal;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class fy {
    private final File a;

    public fy(File file) {
        this.a = file;
    }

    public final synchronized boolean a() {
        boolean z = false;
        synchronized (this) {
            if (b() == null) {
                try {
                    bk.a(this.a, UUID.randomUUID().toString());
                    if (b() != null) {
                        z = true;
                    }
                } catch (IOException e) {
                    try {
                        this.a.delete();
                        throw e;
                    } catch (IOException e2) {
                    }
                }
            }
        }
        return z;
    }

    final String b() {
        if (this.a.exists()) {
            try {
                String strA = bk.a(this.a, ap.c);
                if (strA.length() > 0) {
                    return strA;
                }
            } catch (IOException e) {
            }
        }
        return null;
    }
}
