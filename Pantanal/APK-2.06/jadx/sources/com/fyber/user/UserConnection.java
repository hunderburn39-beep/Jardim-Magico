package com.fyber.user;

import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public enum UserConnection {
    wifi(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI),
    three_g("3g");

    public final String connection;

    UserConnection(String str) {
        this.connection = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.connection;
    }
}
