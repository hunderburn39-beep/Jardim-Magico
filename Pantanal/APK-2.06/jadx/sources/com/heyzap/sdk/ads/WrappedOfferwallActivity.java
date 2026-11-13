package com.heyzap.sdk.ads;

import com.fyber.ads.ofw.OfferWallActivity;
import com.heyzap.internal.Constants;

/* JADX INFO: loaded from: classes.dex */
public final class WrappedOfferwallActivity extends OfferWallActivity {
    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        WrappedOfferWall.statusListener.onAudioFinished();
        WrappedOfferWall.statusListener.onHide(Constants.DEFAULT_TAG);
    }
}
