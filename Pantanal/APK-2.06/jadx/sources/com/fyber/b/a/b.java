package com.fyber.b.a;

import android.support.annotation.NonNull;

/* JADX INFO: compiled from: BannerEventNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends com.fyber.b.a {
    protected b(a aVar) {
        super(aVar);
    }

    @Override // com.fyber.b.g
    protected final String a_() {
        return "BannerEventNetworkOperation";
    }

    /* JADX INFO: compiled from: BannerEventNetworkOperation.java */
    public static class a extends com.fyber.b.a.AbstractC0017a<b, a> {
        public a(com.fyber.ads.a.a aVar) {
            super(aVar);
        }

        @Override // com.fyber.b.a.AbstractC0017a
        @NonNull
        protected final String a() {
            return "0";
        }

        @Override // com.fyber.b.a.AbstractC0017a
        @NonNull
        protected final String b() {
            return "banner";
        }

        @Override // com.fyber.b.a.AbstractC0017a
        protected final String c() {
            return "banner_tracking";
        }

        @Override // com.fyber.b.a.AbstractC0017a
        protected final /* synthetic */ com.fyber.b.a d() {
            return new b(this);
        }
    }
}
