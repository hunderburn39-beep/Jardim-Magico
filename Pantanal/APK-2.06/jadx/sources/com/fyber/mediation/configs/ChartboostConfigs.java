package com.fyber.mediation.configs;

import com.fyber.mediation.annotations.ConfigKey;
import com.fyber.mediation.chartboost.ChartboostMediationAdapter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@ConfigKey(name = ChartboostMediationAdapter.ADAPTER_NAME)
@Retention(RetentionPolicy.SOURCE)
public @interface ChartboostConfigs {
    @ConfigKey(name = ChartboostMediationAdapter.APP_ID_KEY)
    String appId() default "";

    @ConfigKey(name = ChartboostMediationAdapter.APP_SIGNATURE_KEY)
    String appSignature() default "";

    @ConfigKey(name = ChartboostMediationAdapter.INT_CACHE_KEY)
    boolean cacheInterstitials() default true;

    @ConfigKey(name = ChartboostMediationAdapter.RV_CACHE_KEY)
    boolean cacheRewardedVideo() default true;

    @ConfigKey(name = ChartboostMediationAdapter.LOG_LEVEL_KEY)
    String logLevel() default "";
}
