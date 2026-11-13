package com.fyber.requesters;

import android.content.Intent;
import com.fyber.ads.AdFormat;

/* JADX INFO: loaded from: classes.dex */
public interface RequestCallback extends Callback {
    void onAdAvailable(Intent intent);

    void onAdNotAvailable(AdFormat adFormat);
}
