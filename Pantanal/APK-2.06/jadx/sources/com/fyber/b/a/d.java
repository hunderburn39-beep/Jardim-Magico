package com.fyber.b.a;

import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.utils.s;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: BannerRequesterNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class d extends com.fyber.b.c<com.fyber.ads.banners.a.b> {
    @Override // com.fyber.b.g
    protected final /* bridge */ /* synthetic */ Object a(IOException iOException) {
        return null;
    }

    public static Future<List<com.fyber.ads.banners.a.b>> a(s sVar, String str) {
        return Fyber.getConfigs().a((Callable) new d(sVar, str));
    }

    private d(s sVar, String str) {
        super(sVar, str);
    }

    @Override // com.fyber.b.g
    protected final String a_() {
        return "BannerRequesterNetworkOperation";
    }

    @Override // com.fyber.b.c
    @NonNull
    protected final /* synthetic */ com.fyber.ads.a.c a(String str, String str2) {
        return new com.fyber.ads.banners.a.b(str, str2, this.b);
    }
}
