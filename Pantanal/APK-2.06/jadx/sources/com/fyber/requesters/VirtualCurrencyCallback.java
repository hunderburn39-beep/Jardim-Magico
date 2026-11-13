package com.fyber.requesters;

import com.fyber.currency.VirtualCurrencyErrorResponse;
import com.fyber.currency.VirtualCurrencyResponse;

/* JADX INFO: loaded from: classes.dex */
public interface VirtualCurrencyCallback extends Callback {
    void onError(VirtualCurrencyErrorResponse virtualCurrencyErrorResponse);

    void onSuccess(VirtualCurrencyResponse virtualCurrencyResponse);
}
