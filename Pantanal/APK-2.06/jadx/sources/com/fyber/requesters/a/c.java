package com.fyber.requesters.a;

import android.content.Intent;
import android.os.Handler;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.utils.d;

/* JADX INFO: compiled from: DispatchableRequestCallback.java */
/* JADX INFO: loaded from: classes.dex */
public final class c extends b implements RequestCallback {
    public c(RequestCallback requestCallback, Handler handler) {
        super(requestCallback, handler);
    }

    @Override // com.fyber.requesters.RequestCallback
    public final void onAdAvailable(final Intent intent) {
        a(new d() { // from class: com.fyber.requesters.a.c.1
            @Override // com.fyber.utils.d
            public final void a() {
                ((RequestCallback) c.this.a).onAdAvailable(intent);
            }
        });
    }

    @Override // com.fyber.requesters.RequestCallback
    public final void onAdNotAvailable(final AdFormat adFormat) {
        a(new d() { // from class: com.fyber.requesters.a.c.2
            @Override // com.fyber.utils.d
            public final void a() {
                ((RequestCallback) c.this.a).onAdNotAvailable(adFormat);
            }
        });
    }
}
