package com.fyber.requesters;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.ads.interstitials.b.b;
import com.fyber.requesters.a.a;
import com.fyber.requesters.a.c;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class InterstitialRequester extends Requester<InterstitialRequester> {
    @Override // com.fyber.requesters.Requester
    protected final /* bridge */ /* synthetic */ InterstitialRequester b() {
        return this;
    }

    public static InterstitialRequester create(@NonNull RequestCallback requestCallback) {
        return new InterstitialRequester(requestCallback);
    }

    public static InterstitialRequester from(@NonNull Requester requester) {
        return new InterstitialRequester(requester);
    }

    @Override // com.fyber.requesters.Requester
    public void request(Context context) {
        Callback cVar;
        if (a(context)) {
            if (b.a().b()) {
                b.a(com.fyber.ads.a.b.REQUESTING_OFFERS);
                HashMap<String, String> mapD = d("CUSTOM_PARAMS_KEY");
                String strB = b("PLACEMENT_ID_KEY");
                if (this.a instanceof AdRequestCallback) {
                    cVar = new a((AdRequestCallback) this.a, this.c);
                } else {
                    cVar = new c((RequestCallback) this.a, this.c);
                }
                new com.fyber.b.b.c(new com.fyber.b.b.c.a().a(cVar).a(mapD).a(strB)).a(context);
                return;
            }
            a(RequestError.UNABLE_TO_REQUEST_ADS);
        }
    }

    private InterstitialRequester(RequestCallback requestCallback) {
        super(requestCallback);
    }

    private InterstitialRequester(Requester requester) {
        super(requester);
    }

    @Override // com.fyber.requesters.Requester
    protected final boolean a() {
        return (this.a instanceof RequestCallback) || (this.a instanceof AdRequestCallback);
    }
}
