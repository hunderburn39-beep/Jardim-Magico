package com.tapjoy.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class dn {
    private static volatile boolean a = false;
    private static final dn c = new dn((byte) 0);
    private final Map b;

    public static dn a() {
        return c;
    }

    dn() {
        this.b = new HashMap();
    }

    private dn(byte b) {
        this.b = Collections.emptyMap();
    }
}
