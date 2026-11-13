package com.heyzap.sdk.ads;

import android.app.Activity;
import android.content.Intent;
import com.fyber.ads.AdFormat;
import com.fyber.requesters.OfferWallRequester;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.heyzap.internal.Constants;
import com.heyzap.internal.Logger;

/* JADX INFO: loaded from: classes.dex */
public class WrappedOfferWall {
    static HeyzapAds.OnStatusListener statusListener = HeyzapAds.stubStatusListener;

    public static void display(final Activity activity) {
        OfferWallRequester.create(new RequestCallback() { // from class: com.heyzap.sdk.ads.WrappedOfferWall.1
            @Override // com.fyber.requesters.RequestCallback
            public void onAdAvailable(Intent intent) {
                Intent realIntent = new Intent(activity, (Class<?>) WrappedOfferwallActivity.class);
                realIntent.setFlags(intent.getFlags());
                realIntent.putExtras(intent.getExtras());
                activity.startActivity(realIntent);
                WrappedOfferWall.statusListener.onShow(Constants.DEFAULT_TAG);
                WrappedOfferWall.statusListener.onAudioStarted();
            }

            @Override // com.fyber.requesters.RequestCallback
            public void onAdNotAvailable(AdFormat adFormat) {
                WrappedOfferWall.statusListener.onFailedToShow(Constants.DEFAULT_TAG);
                Logger.debug("OfferWall request error: Not available");
            }

            @Override // com.fyber.requesters.Callback
            public void onRequestError(RequestError requestError) {
                WrappedOfferWall.statusListener.onFailedToShow(Constants.DEFAULT_TAG);
                Logger.debug("OfferWall request error: " + requestError.getDescription());
            }
        }).request(activity);
    }

    public static void setOnStatusListener(HeyzapAds.OnStatusListener newStatusListener) {
        if (newStatusListener == null) {
            newStatusListener = HeyzapAds.stubStatusListener;
        }
        statusListener = newStatusListener;
    }
}
