package com.fyber.mediation.configs;

import com.fyber.mediation.adcolony.AdColonyMediationAdapter;
import com.fyber.mediation.annotations.ConfigKey;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@ConfigKey(name = "AdColony")
@Retention(RetentionPolicy.SOURCE)
public @interface AdColonyConfigs {
    @ConfigKey(name = AdColonyMediationAdapter.APP_ID)
    String appId() default "";

    @ConfigKey(name = AdColonyMediationAdapter.CLIENT_OPTIONS)
    String clientOptions() default "";

    @ConfigKey(name = AdColonyMediationAdapter.ZONE_IDS_INTERSTITIAL)
    String[] zoneIdsInterstitial() default {""};

    @ConfigKey(name = AdColonyMediationAdapter.ZONE_IDS_REWARDED_VIDEO)
    String[] zoneIdsRewardedVideo() default {""};
}
