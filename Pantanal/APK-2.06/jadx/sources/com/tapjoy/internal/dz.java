package com.tapjoy.internal;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class dz extends RuntimeException {
    private final List a;

    public dz() {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.a = null;
    }
}
