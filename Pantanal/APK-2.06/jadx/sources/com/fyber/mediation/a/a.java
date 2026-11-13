package com.fyber.mediation.a;

import android.app.Activity;
import com.fyber.Fyber;
import com.fyber.ads.banners.mediation.BannerMediationAdapter;
import com.fyber.ads.interstitials.a.b;
import com.fyber.ads.interstitials.mediation.InterstitialMediationAdapter;
import com.fyber.ads.videos.mediation.RewardedVideoMediationAdapter;
import com.fyber.mediation.MediationAdapter;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: ExchangeAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends MediationAdapter {
    private b a = new b(this);

    @Override // com.fyber.mediation.MediationAdapter
    public final boolean startAdapter(Activity activity, Map<String, Object> map) {
        return true;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public final String getName() {
        return "Fyber";
    }

    @Override // com.fyber.mediation.MediationAdapter
    public final String getVersion() {
        return Fyber.RELEASE_VERSION_STRING;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public final RewardedVideoMediationAdapter<a> getVideoMediationAdapter() {
        return null;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public final BannerMediationAdapter<a> getBannerMediationAdapter() {
        return null;
    }

    @Override // com.fyber.mediation.MediationAdapter
    protected final Set<?> getListeners() {
        return null;
    }

    @Override // com.fyber.mediation.MediationAdapter
    public final /* bridge */ /* synthetic */ InterstitialMediationAdapter getInterstitialMediationAdapter() {
        return this.a;
    }
}
