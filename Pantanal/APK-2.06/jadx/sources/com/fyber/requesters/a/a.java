package com.fyber.requesters.a;

import android.os.Handler;
import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.AdRequestCallback;
import com.fyber.utils.d;

/* JADX INFO: compiled from: DispatchableAdCallback.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends b implements AdRequestCallback {
    public a(AdRequestCallback adRequestCallback, Handler handler) {
        super(adRequestCallback, handler);
    }

    @Override // com.fyber.requesters.AdRequestCallback
    public final void onAdAvailable(final Ad ad) {
        a(new d() { // from class: com.fyber.requesters.a.a.1
            @Override // com.fyber.utils.d
            public final void a() {
                ((AdRequestCallback) a.this.a).onAdAvailable(ad);
            }
        });
    }

    @Override // com.fyber.requesters.AdRequestCallback
    public final void onAdNotAvailable(final AdFormat adFormat) {
        a(new d() { // from class: com.fyber.requesters.a.a.2
            @Override // com.fyber.utils.d
            public final void a() {
                ((AdRequestCallback) a.this.a).onAdNotAvailable(adFormat);
            }
        });
    }
}
