package com.fyber.mediation.chartboost.rv;

import com.fyber.ads.videos.mediation.TPNVideoValidationResult;

/* JADX INFO: loaded from: classes.dex */
public interface IFyberChartboostRV {
    void fyberNotifyCloseEngagement();

    void fyberNotifyVideoLoadError();

    void fyberNotifyVideoStarted();

    void fyberSendValidationEvent(TPNVideoValidationResult tPNVideoValidationResult);

    void fyberSetVideoPlayed();
}
