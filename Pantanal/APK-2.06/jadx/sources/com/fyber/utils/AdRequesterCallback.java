package com.fyber.utils;

import android.content.Intent;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.fyber.requesters.Requester;

/* JADX INFO: loaded from: classes.dex */
public abstract class AdRequesterCallback implements RequestCallback {
    protected Requester a;

    public AdRequesterCallback(Requester requester) {
        this.a = requester;
    }

    @Override // com.fyber.requesters.RequestCallback
    public void onAdAvailable(Intent intent) {
    }

    @Override // com.fyber.requesters.RequestCallback
    public void onAdNotAvailable(AdFormat adFormat) {
    }

    @Override // com.fyber.requesters.Callback
    public void onRequestError(RequestError requestError) {
    }
}
