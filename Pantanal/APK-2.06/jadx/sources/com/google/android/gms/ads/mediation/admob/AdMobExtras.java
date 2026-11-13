package com.google.android.gms.ads.mediation.admob;

import android.os.Bundle;
import com.google.ads.mediation.NetworkExtras;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class AdMobExtras implements NetworkExtras {
    private final Bundle rP;

    public AdMobExtras(Bundle extras) {
        this.rP = extras != null ? new Bundle(extras) : null;
    }

    public Bundle getExtras() {
        return this.rP;
    }
}
