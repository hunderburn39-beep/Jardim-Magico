package com.heyzap.wrapper.credentials;

import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class Credentials {
    public final String appId;
    public final Set<String> disabledTags;
    public final String securityToken;

    public Credentials(String appId, String securityToken, Set<String> disabledTags) {
        this.appId = appId;
        this.securityToken = securityToken;
        this.disabledTags = disabledTags;
    }
}
