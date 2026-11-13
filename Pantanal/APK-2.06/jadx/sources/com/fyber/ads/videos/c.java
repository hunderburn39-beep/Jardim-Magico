package com.fyber.ads.videos;

/* JADX INFO: compiled from: RewardedVideoClientStatusListener.java */
/* JADX INFO: loaded from: classes.dex */
public interface c {

    /* JADX INFO: compiled from: RewardedVideoClientStatusListener.java */
    public enum a {
        STARTED,
        CLOSE_FINISHED,
        CLOSE_ABORTED,
        PENDING_CLOSE,
        ERROR
    }

    void didChangeStatus(a aVar);

    void didReceiveOffers(boolean z);
}
