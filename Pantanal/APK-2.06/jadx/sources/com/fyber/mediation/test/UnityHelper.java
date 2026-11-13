package com.fyber.mediation.test;

import android.content.Intent;
import com.heyzap.internal.DevLogger;
import com.heyzap.internal.DeveloperErrorMessages;
import com.heyzap.internal.Utils;
import com.heyzap.sdk.ads.MediationTestActivity;
import com.unity3d.player.UnityPlayer;

/* JADX INFO: loaded from: classes.dex */
public class UnityHelper {
    private UnityHelper() {
    }

    public static void showNetworkActivity() {
        if (!Utils.activityExistsInPackage(UnityPlayer.currentActivity, MediationTestActivity.class)) {
            DevLogger.error(DeveloperErrorMessages.TEST_ACTIVITY_NOT_PRESENT);
        } else {
            UnityPlayer.currentActivity.startActivity(new Intent(UnityPlayer.currentActivity, (Class<?>) MediationTestActivity.class));
        }
    }
}
