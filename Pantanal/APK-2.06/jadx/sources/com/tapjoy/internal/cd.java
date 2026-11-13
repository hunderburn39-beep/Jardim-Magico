package com.tapjoy.internal;

import com.tapjoy.TJAdUnitConstants;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class cd extends ce {
    public abstract Object a(br brVar);

    @Override // com.tapjoy.internal.ce
    public final Map a() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Accept", "application/json");
        return linkedHashMap;
    }

    @Override // com.tapjoy.internal.ce
    public final Object a(URI uri, InputStream inputStream) {
        Object objA = null;
        br brVarA = br.a(inputStream);
        brVarA.a("BASE_URI", uri);
        try {
            brVarA.h();
            int iR = 0;
            String strM = null;
            while (brVarA.j()) {
                String strL = brVarA.l();
                if ("status".equals(strL)) {
                    iR = brVarA.r();
                } else if (TJAdUnitConstants.String.MESSAGE.equals(strL)) {
                    strM = brVarA.m();
                } else if ("data".equals(strL)) {
                    objA = a(brVarA);
                } else {
                    brVarA.s();
                }
            }
            brVarA.i();
            if (iR != 200) {
                throw new cf(iR, strM);
            }
            brVarA.close();
            return objA;
        } catch (Throwable th) {
            brVarA.close();
            throw th;
        }
    }
}
