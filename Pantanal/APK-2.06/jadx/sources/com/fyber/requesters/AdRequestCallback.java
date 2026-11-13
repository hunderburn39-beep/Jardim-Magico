package com.fyber.requesters;

import com.fyber.ads.Ad;
import com.fyber.ads.AdFormat;

/* JADX INFO: loaded from: classes.dex */
public interface AdRequestCallback extends Callback {
    void onAdAvailable(Ad ad);

    void onAdNotAvailable(AdFormat adFormat);
}
