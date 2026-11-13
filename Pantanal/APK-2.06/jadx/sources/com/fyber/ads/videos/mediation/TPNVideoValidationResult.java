package com.fyber.ads.videos.mediation;

import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: loaded from: classes.dex */
public enum TPNVideoValidationResult {
    AdapterNotIntegrated("no_sdk"),
    NoVideoAvailable("no_video"),
    Timeout("timeout"),
    NetworkError("network_error"),
    DiskError("disk_error"),
    Error(TJAdUnitConstants.String.VIDEO_ERROR),
    Success("success");

    private final String text;

    TPNVideoValidationResult(String str) {
        this.text = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.text;
    }
}
