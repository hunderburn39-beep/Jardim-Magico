package com.fyber.ads.a;

import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: compiled from: AdEvent.java */
/* JADX INFO: loaded from: classes.dex */
public enum a {
    ValidationRequest("request"),
    ValidationFill("fill"),
    ValidationNoFill("no_fill"),
    ValidationError(TJAdUnitConstants.String.VIDEO_ERROR),
    ValidationTimeout("timeout"),
    ShowImpression("impression"),
    ShowRotation("rotation"),
    ShowClick("click"),
    ShowClose(TJAdUnitConstants.String.CLOSE),
    ShowError(TJAdUnitConstants.String.VIDEO_ERROR),
    NotIntegrated("no_sdk"),
    Progress("progress"),
    Interaction("interaction");

    private final String n;

    a(String str) {
        this.n = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.n;
    }
}
