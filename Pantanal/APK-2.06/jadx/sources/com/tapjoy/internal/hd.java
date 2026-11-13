package com.tapjoy.internal;

import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class hd implements bh {
    private final dw a;

    @Override // com.tapjoy.internal.bj
    public final /* bridge */ /* synthetic */ void a(OutputStream outputStream, Object obj) {
        ((du) obj).a(outputStream);
    }

    public hd(dw dwVar) {
        this.a = dwVar;
    }

    @Override // com.tapjoy.internal.bi
    public final /* synthetic */ Object b(InputStream inputStream) {
        return (du) this.a.a(inputStream);
    }
}
