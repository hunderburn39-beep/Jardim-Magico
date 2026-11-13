package com.fyber.c.d;

import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: compiled from: VideoPlayerEvent.java */
/* JADX INFO: loaded from: classes.dex */
public enum a {
    PlayingEvent(TJAdUnitConstants.String.VIDEO_PLAYING),
    TimeUpdateEvent("timeupdate"),
    EndedEvent("ended"),
    ClickThroughEvent("clickThrough"),
    CancelEvent("cancel");

    private final String f;

    a(String str) {
        this.f = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f;
    }
}
