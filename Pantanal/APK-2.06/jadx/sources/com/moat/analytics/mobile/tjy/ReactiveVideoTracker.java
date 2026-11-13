package com.moat.analytics.mobile.tjy;

import android.view.View;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface ReactiveVideoTracker {
    void changeTargetView(View view);

    void dispatchEvent(MoatAdEvent moatAdEvent);

    void setDebug(boolean z);

    boolean trackVideoAd(Map map, Integer num, View view);
}
