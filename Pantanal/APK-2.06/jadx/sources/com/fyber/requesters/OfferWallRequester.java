package com.fyber.requesters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.fyber.ads.AdFormat;
import com.fyber.ads.ofw.OfferWallActivity;
import com.fyber.utils.d;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class OfferWallRequester extends Requester<OfferWallRequester> {
    @Override // com.fyber.requesters.Requester
    protected final /* bridge */ /* synthetic */ OfferWallRequester b() {
        return this;
    }

    public static OfferWallRequester create(@NonNull RequestCallback requestCallback) {
        return new OfferWallRequester(requestCallback);
    }

    public static OfferWallRequester from(@NonNull Requester requester) {
        return new OfferWallRequester(requester);
    }

    public OfferWallRequester closeOnRedirect(boolean z) {
        c().put("CLOSE_ON_REDIRECT", Boolean.valueOf(z));
        return this;
    }

    @Override // com.fyber.requesters.Requester
    public void request(Context context) {
        if (a(context)) {
            boolean zBooleanValue = c("CLOSE_ON_REDIRECT").booleanValue();
            HashMap<String, String> mapD = d("CUSTOM_PARAMS_KEY");
            final Intent intentPutExtra = new Intent(context, (Class<?>) OfferWallActivity.class).putExtra(OfferWallActivity.EXTRA_SHOULD_CLOSE_ON_REDIRECT_KEY, zBooleanValue).putExtra(OfferWallActivity.EXTRA_KEYS_VALUES_MAP_KEY, mapD).putExtra(OfferWallActivity.EXTRA_PLACEMENT_ID_KEY, b("PLACEMENT_ID_KEY")).putExtra(Requester.EXTRA_AD_FORMAT, AdFormat.OFFER_WALL);
            a(new d() { // from class: com.fyber.requesters.OfferWallRequester.1
                @Override // com.fyber.utils.d
                public final void a() {
                    ((RequestCallback) OfferWallRequester.this.a).onAdAvailable(intentPutExtra);
                }
            });
        }
    }

    private OfferWallRequester(RequestCallback requestCallback) {
        super(requestCallback);
    }

    private OfferWallRequester(Requester requester) {
        super(requester);
    }

    @Override // com.fyber.requesters.Requester
    protected final boolean a() {
        return this.a instanceof RequestCallback;
    }
}
