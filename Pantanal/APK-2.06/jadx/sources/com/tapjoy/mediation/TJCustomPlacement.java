package com.tapjoy.mediation;

import android.content.Context;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public interface TJCustomPlacement {
    void requestContentWithCustomPlacementParams(Context context, TJCustomPlacementListener tJCustomPlacementListener, Bundle bundle);

    void showContent();
}
