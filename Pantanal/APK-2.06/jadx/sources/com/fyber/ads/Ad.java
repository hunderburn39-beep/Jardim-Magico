package com.fyber.ads;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public abstract class Ad {
    public abstract AdFormat getAdFormat();

    public abstract void start(Activity activity);

    public String getPlacementId() {
        return "";
    }

    public boolean canStart() {
        return true;
    }
}
