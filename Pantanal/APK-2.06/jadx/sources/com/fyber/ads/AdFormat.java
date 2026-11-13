package com.fyber.ads;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.fyber.requesters.Requester;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public enum AdFormat {
    OFFER_WALL,
    REWARDED_VIDEO,
    INTERSTITIAL,
    BANNER,
    UNKNOWN;

    public static AdFormat fromIntent(@NonNull Intent intent) {
        Serializable serializableExtra;
        return (intent == null || (serializableExtra = intent.getSerializableExtra(Requester.EXTRA_AD_FORMAT)) == null) ? UNKNOWN : (AdFormat) serializableExtra;
    }
}
