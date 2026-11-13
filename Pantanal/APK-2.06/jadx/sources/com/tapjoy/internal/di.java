package com.tapjoy.internal;

import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class di implements dw {
    private static final dn a = dn.a();

    private static du a(du duVar) throws dq {
        dz dzVar;
        if (duVar == null || duVar.c()) {
            return duVar;
        }
        if (!(duVar instanceof dh)) {
            dzVar = new dz();
        } else {
            dzVar = new dz();
        }
        dq dqVar = new dq(dzVar.getMessage());
        dqVar.a = duVar;
        throw dqVar;
    }

    private du a(byte[] bArr, int i, dn dnVar) throws dq {
        try {
            dl dlVarA = dl.a(bArr, i);
            du duVar = (du) a(dlVarA, dnVar);
            try {
                dlVarA.a(0);
                return duVar;
            } catch (dq e) {
                e.a = duVar;
                throw e;
            }
        } catch (dq e2) {
            throw e2;
        }
    }

    private du a(InputStream inputStream, dn dnVar) throws dq {
        dl dlVar = new dl(inputStream);
        du duVar = (du) a(dlVar, dnVar);
        try {
            dlVar.a(0);
            return duVar;
        } catch (dq e) {
            e.a = duVar;
            throw e;
        }
    }

    @Override // com.tapjoy.internal.dw
    public final /* bridge */ /* synthetic */ Object a(InputStream inputStream) {
        return a(a(inputStream, a));
    }

    @Override // com.tapjoy.internal.dw
    public final /* bridge */ /* synthetic */ Object a(byte[] bArr) {
        return a(a(bArr, bArr.length, a));
    }
}
