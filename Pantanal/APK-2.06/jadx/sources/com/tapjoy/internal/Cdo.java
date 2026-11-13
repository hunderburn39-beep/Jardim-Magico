package com.tapjoy.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.tapjoy.internal.do, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public abstract class Cdo extends dh implements Serializable {
    public Cdo() {
    }

    public Cdo(byte b) {
    }

    /* JADX INFO: renamed from: com.tapjoy.internal.do$a */
    public static abstract class a extends dh.a {
        public dk a = dk.b;

        @Override // com.tapjoy.internal.dh.a
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public a clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }
    }
}
