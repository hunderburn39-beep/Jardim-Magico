package com.fyber.requesters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.ads.AdFormat;
import com.fyber.ads.videos.RewardedVideoActivity;
import com.fyber.ads.videos.b;
import com.fyber.ads.videos.c;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.d;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class RewardedVideoRequester extends Requester<RewardedVideoRequester> {
    private Context d;
    private a e;

    @Override // com.fyber.requesters.Requester
    protected final /* bridge */ /* synthetic */ RewardedVideoRequester b() {
        return this;
    }

    public static RewardedVideoRequester create(@NonNull RequestCallback requestCallback) {
        return new RewardedVideoRequester(requestCallback);
    }

    public static RewardedVideoRequester from(@NonNull Requester requester) {
        return new RewardedVideoRequester(requester);
    }

    public RewardedVideoRequester notifyUserOnCompletion(boolean z) {
        c().put("SHOULD_NOTIFY_ON_USER_ENGAGED", Boolean.valueOf(z));
        return this;
    }

    public RewardedVideoRequester withVirtualCurrencyRequester(VirtualCurrencyRequester virtualCurrencyRequester) {
        c().put("CURRENCY_REQUESTER", virtualCurrencyRequester);
        return this;
    }

    @Override // com.fyber.requesters.Requester
    public void request(Context context) {
        if (a(context)) {
            boolean zB = b.a.b();
            this.d = context;
            if (zB) {
                HashMap<String, String> mapD = d("CUSTOM_PARAMS_KEY");
                String strB = b("PLACEMENT_ID_KEY");
                boolean zBooleanValue = c("SHOULD_NOTIFY_ON_USER_ENGAGED").booleanValue();
                VirtualCurrencyRequester virtualCurrencyRequester = (VirtualCurrencyRequester) a("CURRENCY_REQUESTER");
                b.a.a(zBooleanValue);
                b.a.a(mapD);
                b.a.a(virtualCurrencyRequester);
                b.a.a(strB);
                b bVar = b.a;
                if (this.e == null) {
                    this.e = new a(this, (byte) 0);
                }
                bVar.a(this.e);
                try {
                    b.a.a(Fyber.getConfigs().e(), context);
                    return;
                } catch (Exception e) {
                    FyberLogger.e("RewardedVideoRequester", "something went wrong with the video request: " + e.getStackTrace());
                    a(RequestError.UNKNOWN_ERROR);
                    return;
                }
            }
            a(RequestError.UNABLE_TO_REQUEST_ADS);
        }
    }

    private RewardedVideoRequester(Callback callback) {
        super(callback);
    }

    private RewardedVideoRequester(Requester requester) {
        super(requester);
    }

    @Override // com.fyber.requesters.Requester
    protected final boolean a() {
        return this.a instanceof RequestCallback;
    }

    private class a implements c {
        private a() {
        }

        /* synthetic */ a(RewardedVideoRequester rewardedVideoRequester, byte b) {
            this();
        }

        @Override // com.fyber.ads.videos.c
        public final void didReceiveOffers(final boolean z) {
            RewardedVideoRequester.this.a(new d() { // from class: com.fyber.requesters.RewardedVideoRequester.a.1
                @Override // com.fyber.utils.d
                public final void a() {
                    Intent intent;
                    if (z) {
                        RequestCallback requestCallback = (RequestCallback) RewardedVideoRequester.this.a;
                        a aVar = a.this;
                        if (b.a.c()) {
                            intent = new Intent(RewardedVideoRequester.this.d, (Class<?>) RewardedVideoActivity.class);
                            intent.putExtra(Requester.EXTRA_AD_FORMAT, AdFormat.REWARDED_VIDEO);
                        } else {
                            FyberLogger.d("RewardedVideoRequester", "Undefined error");
                            intent = null;
                        }
                        requestCallback.onAdAvailable(intent);
                        return;
                    }
                    ((RequestCallback) RewardedVideoRequester.this.a).onAdNotAvailable(AdFormat.REWARDED_VIDEO);
                }
            });
        }

        @Override // com.fyber.ads.videos.c
        public final void didChangeStatus(c.a aVar) {
            if (aVar == c.a.ERROR) {
                RewardedVideoRequester.this.a(RequestError.ERROR_REQUESTING_ADS);
            }
        }
    }
}
