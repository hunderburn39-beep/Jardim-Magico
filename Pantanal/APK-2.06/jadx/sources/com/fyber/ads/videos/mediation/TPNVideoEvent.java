package com.fyber.ads.videos.mediation;

import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: loaded from: classes.dex */
public enum TPNVideoEvent {
    Started("started"),
    Aborted("aborted"),
    Finished("finished"),
    Closed("closed"),
    NoVideo("no_video"),
    Timeout("timeout"),
    Error(TJAdUnitConstants.String.VIDEO_ERROR),
    AdapterNotIntegrated("no_sdk");

    private final String text;

    TPNVideoEvent(String str) {
        this.text = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.text;
    }
}
