package com.fyber.requesters;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fyber.ads.a.b;
import com.fyber.ads.banners.BannerSize;
import com.fyber.ads.banners.NetworkBannerSize;
import com.fyber.ads.banners.a.a;
import com.fyber.b.a.c;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class BannerRequester extends Requester<BannerRequester> {
    private HashMap<String, BannerSize> d;

    @Override // com.fyber.requesters.Requester
    protected final /* bridge */ /* synthetic */ BannerRequester b() {
        return this;
    }

    public static BannerRequester create(@NonNull AdRequestCallback adRequestCallback) {
        return new BannerRequester(adRequestCallback);
    }

    public static BannerRequester from(@NonNull Requester requester) {
        return new BannerRequester(requester);
    }

    private BannerRequester(Requester requester) {
        super(requester);
        this.d = new HashMap<>();
    }

    private BannerRequester(@NonNull Callback callback) {
        super(callback);
        this.d = new HashMap<>();
    }

    @Override // com.fyber.requesters.Requester
    public void request(Context context) {
        if (a(context)) {
            if (a.a().b()) {
                a.a(b.REQUESTING_OFFERS);
                HashMap<String, String> mapD = d("CUSTOM_PARAMS_KEY");
                new c(new c.a().a(new com.fyber.requesters.a.a((AdRequestCallback) this.a, this.c)).a(mapD).a(b("PLACEMENT_ID_KEY")).a(new ArrayList<>(this.d.values()))).a(context);
                return;
            }
            a(RequestError.UNABLE_TO_REQUEST_ADS);
        }
    }

    @Override // com.fyber.requesters.Requester
    protected final boolean a() {
        return this.a instanceof AdRequestCallback;
    }

    public BannerRequester withNetworkSize(@NonNull NetworkBannerSize networkBannerSize) {
        this.d.put(networkBannerSize.getNetwork(), networkBannerSize.getSize());
        return this;
    }
}
