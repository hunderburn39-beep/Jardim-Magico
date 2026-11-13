package com.fyber.b;

import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.q;
import com.fyber.utils.s;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: SignedResponseNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class j<T, V> extends g<V> {
    protected String a;

    protected abstract T a(int i, String str, String str2);

    protected abstract V a(T t);

    protected abstract T a(String str);

    public j(s sVar, String str) {
        super(sVar);
        if (StringUtils.nullOrEmpty(str)) {
            throw new IllegalArgumentException("Security token must be set");
        }
        this.a = str;
    }

    @Override // com.fyber.b.g
    protected final V a(com.fyber.utils.i iVar) throws IOException {
        T tA;
        int iB = iVar.b();
        String strC = iVar.c();
        List<String> listA = iVar.a("X-Sponsorpay-Response-Signature");
        String str = (listA == null || listA.size() <= 0) ? "" : listA.get(0);
        FyberLogger.d(a_(), String.format(Locale.ENGLISH, "Server Response, status code: %d, response body: %s, signature: %s", Integer.valueOf(iB), strC, str));
        if (!a(iB) && a(strC, str)) {
            tA = a(strC);
        } else {
            tA = a(iB, strC, str);
        }
        return a(tA);
    }

    protected final boolean a(String str, String str2) {
        return q.a(str, this.a).equals(str2);
    }

    protected static boolean a(int i) {
        return i < 200 || i > 299;
    }
}
