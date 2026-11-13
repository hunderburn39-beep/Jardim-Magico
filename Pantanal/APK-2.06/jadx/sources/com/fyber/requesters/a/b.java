package com.fyber.requesters.a;

import android.os.Handler;
import com.fyber.Fyber;
import com.fyber.requesters.Callback;
import com.fyber.requesters.RequestError;
import com.fyber.utils.d;

/* JADX INFO: compiled from: DispatchableCallback.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class b implements Callback {
    protected final Callback a;
    protected final Handler b;

    public b(Callback callback, Handler handler) {
        this.a = callback;
        this.b = handler;
    }

    @Override // com.fyber.requesters.Callback
    public void onRequestError(final RequestError requestError) {
        a(new d() { // from class: com.fyber.requesters.a.b.1
            @Override // com.fyber.utils.d
            public final void a() {
                b.this.a.onRequestError(requestError);
            }
        });
    }

    protected final void a(d dVar) {
        if (this.b != null) {
            this.b.post(dVar);
        } else {
            Fyber.getConfigs();
            Fyber.a.a(dVar);
        }
    }
}
