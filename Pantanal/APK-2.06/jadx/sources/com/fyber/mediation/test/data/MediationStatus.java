package com.fyber.mediation.test.data;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MediationStatus {
    public final boolean givenConfigs;
    public final List<NetworkStatus> networkStatusList;

    public MediationStatus(List<NetworkStatus> networkStatusList, boolean givenConfigs) {
        this.networkStatusList = networkStatusList;
        this.givenConfigs = givenConfigs;
    }
}
