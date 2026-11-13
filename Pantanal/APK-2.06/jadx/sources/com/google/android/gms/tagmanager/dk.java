package com.google.android.gms.tagmanager;

import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
class dk {
    private static by<com.google.android.gms.internal.d.a> a(by<com.google.android.gms.internal.d.a> byVar) {
        try {
            return new by<>(dh.r(cd(dh.j(byVar.getObject()))), byVar.kQ());
        } catch (UnsupportedEncodingException e) {
            bh.b("Escape URI: unsupported encoding", e);
            return byVar;
        }
    }

    private static by<com.google.android.gms.internal.d.a> a(by<com.google.android.gms.internal.d.a> byVar, int i) {
        if (!q(byVar.getObject())) {
            bh.w("Escaping can only be applied to strings.");
            return byVar;
        }
        switch (i) {
            case 12:
                return a(byVar);
            default:
                bh.w("Unsupported Value Escaping: " + i);
                return byVar;
        }
    }

    static by<com.google.android.gms.internal.d.a> a(by<com.google.android.gms.internal.d.a> byVar, int... iArr) {
        for (int i : iArr) {
            byVar = a(byVar, i);
        }
        return byVar;
    }

    static String cd(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, AsyncHttpResponseHandler.DEFAULT_CHARSET).replaceAll("\\+", "%20");
    }

    private static boolean q(com.google.android.gms.internal.d.a aVar) {
        return dh.o(aVar) instanceof String;
    }
}
